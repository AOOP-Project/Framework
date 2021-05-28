package evhh.model;

import java.io.*;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

/***********************************************************************************************************************
 * @project: MainProject
 * @package: evhh.model
 * ---------------------------------------------------------------------------------------------------------------------
 * @authors: Hamed Haghjo & Elias Vahlberg
 * @date: 2021-05-12
 * @time: 12:26
 **********************************************************************************************************************/
public class Grid  implements Serializable
{
    //region Fields
    private int numGameObjects = 0;
    private GameObject[][] grid;
    private int gridWidth, gridHeight;
    private ArrayList<GameObject> staticObjects;
    private ArrayList<GameObject> dynamicObjects;
    private ArrayList<GameObject> controllableObjects;
    private transient GameInstance gameInstance;
    //endregion

    public Grid(int gridWidth, int gridHeight)
    {
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
        grid = new GameObject[gridWidth][gridHeight];
        staticObjects = new ArrayList<>();
        dynamicObjects = new ArrayList<>();
        controllableObjects = new ArrayList<>();
    }

    public void onStart()
    {
        dynamicObjects.forEach(g -> g.setGrid(this));
        staticObjects.forEach(g -> g.setGrid(this));
    }

    //region Field getters
    synchronized public GameObject get(int x, int y)
    {
        if (!isValidCoordinates(x, y))
            throw new NoSuchElementException("The coordinates :{x=" + x + " ,y=" + y + "} Are not valid coordinates");
        return grid[x][y];

    }

    public boolean isValidCoordinates(int x, int y)
    {
        return !(x >= gridWidth || y >= gridHeight || x < 0 || y < 0);
    }

    public int getGridWidth()
    {
        return gridWidth;
    }

    public int getGridHeight()
    {
        return gridHeight;
    }

    public ArrayList<GameObject> getStaticObjects()
    {
        return staticObjects;
    }

    public ArrayList<GameObject> getDynamicObjects()
    {
        return dynamicObjects;
    }

    public GameObject get(long id)
    {
        Optional<GameObject> gameObject = dynamicObjects.stream().filter(g -> g.getId() == id).findFirst();
        if (gameObject.isPresent())
            return gameObject.get();
        gameObject = staticObjects.stream().filter(g -> g.getId() == id).findFirst();
        return gameObject.orElse(null);

    }

    public GameInstance getGameInstance()
    {
        return gameInstance;
    }

    public int getNumGameObjects()
    {
        return numGameObjects;
    }

    //endregion

    //region GameObject manipulation
    public GameObject addGameObject(GameObject gObj, int x, int y)
    {
        if (!isValidCoordinates(x, y))
            throw new IndexOutOfBoundsException("The coordinates :{x=" + x + " ,y=" + y + "} Are not valid coordinates");
        grid[x][y] = gObj;
        gObj.setGrid(this);
        gObj.setPosition(x, y);
        numGameObjects++;
        if (gObj.isStatic())
            staticObjects.add(gObj);
        else
            dynamicObjects.add(gObj);
        if (gObj.getComponentList().stream().anyMatch(c -> c instanceof ControllableComponent))
            addControllableObject(gObj);
        return gObj;
    }

    public GameObject addGameObject(int x, int y, boolean isStatic)
    {
        if (!isValidCoordinates(x, y))
            throw new IndexOutOfBoundsException("The coordinates :{x=" + x + " ,y=" + y + "} Are not valid coordinates");
        grid[x][y] = new GameObject(this, isStatic, x, y);
        numGameObjects++;
        if (grid[x][y].isStatic())
            staticObjects.add(grid[x][y]);
        else
            dynamicObjects.add(grid[x][y]);
        return grid[x][y];
    }

    public void moveGameObject(int x1, int y1, int x2, int y2)
    {
        if (!isValidCoordinates(x1, y1))
            throw new NoSuchElementException("The coordinates :{x=" + x1 + " ,y=" + y1 + "} Are not valid coordinates");
        if (!isValidCoordinates(x2, y2))
            throw new IndexOutOfBoundsException("The coordinates :{x=" + x2 + " ,y=" + y2 + "} Are not valid coordinates");
        if (grid[x1][y1] == null)
            throw new NoSuchElementException("The coordinates :{x=" + x1 + " ,y=" + y1 + "} do not contain a game object");

        if (isValidCoordinates(x2, y2))
        {
            grid[x2][y2] = grid[x1][y1];
            grid[x1][y1] = null;
            if (grid[x2][y2].getX() != x2 || grid[x2][y2].getY() != y2)
                grid[x2][y2].setPosition(x2, y2);
        }
    }

    public void removeGameObject(int x, int y)
    {
        synchronized (getGameInstance())
        {
            if (!isValidCoordinates(x, y))
                throw new NoSuchElementException("The coordinates :{x=" + x + " ,y=" + y + "} Are not valid coordinates");
            if (isEmpty(x, y))
                return;
            if (grid[x][y].isStatic())
                staticObjects.remove(grid[x][y]);
            else
                dynamicObjects.remove(grid[x][y]);
            int controllIndex = controllableObjects.indexOf(grid[x][y]);
            if (controllIndex != -1)
            {
                controllableObjects.set(controllIndex, null);
                gameInstance.removeAllMappedUserInputFromFrame();
                gameInstance.getFrameRenderer().getGridRenderer().removeSprite(grid[x][y].getSprite());
                grid[x][y].onExit();
                grid[x][y] = null;
                gameInstance.refreshMappedUserInput();
                numGameObjects--;
                return;
            }
            gameInstance.getFrameRenderer().getGridRenderer().removeSprite(grid[x][y].getSprite());
            grid[x][y].onExit();
            grid[x][y] = null;
            numGameObjects--;
        }
    }

    //endregion

    //region GameObject state
    public boolean isEmpty(int x, int y)
    {
        if (!isValidCoordinates(x, y))
            throw new NoSuchElementException("The coordinates :{x=" + x + " ,y=" + y + "} Are not valid coordinates");
        return grid[x][y] == null;
    }

    public boolean isStatic(int x, int y)
    {
        if (!isValidCoordinates(x, y))
            throw new NoSuchElementException("The coordinates :{x=" + x + " ,y=" + y + "} Are not valid coordinates");
        return grid[x][y].isStatic();
    }

    public boolean isDynamic(int x, int y)
    {
        if (!isValidCoordinates(x, y))
            throw new NoSuchElementException("The coordinates :{x=" + x + " ,y=" + y + "} Are not valid coordinates");
        return !grid[x][y].isStatic();
    }
    //endregion

    public GameObject[] getGameObjectsWithComponent(Class<? extends GameComponent> containedComponent)
    {
        Stream<GameObject> stream1 = getStaticObjects().stream();
        Stream<GameObject> stream2 = getDynamicObjects().stream();
        return Stream.concat(stream1, stream2).
                filter(Objects::nonNull).filter(g -> g.hasComponent(containedComponent)).toArray(GameObject[]::new);
    }

    //region Serialization
    public static void serializeGrid(Grid grid, String path) throws IOException
    {
        try (FileOutputStream fileOut = new FileOutputStream(path); ObjectOutputStream out = new ObjectOutputStream(fileOut))
        {
            out.writeObject(grid);
            out.close();
            fileOut.close();
        }

    }

    public static Grid deserializeGrid(String path) throws IOException, ClassNotFoundException
    {
        Grid grid = null;
        try (FileInputStream fileIn = new FileInputStream(path); ObjectInputStream in = new ObjectInputStream(fileIn))
        {
            grid = (Grid) in.readObject();
        } catch (IOException e1)
        {
            throw new IOException(e1.getMessage());
        } catch (ClassNotFoundException e2)
        {
            throw new ClassNotFoundException("Grid file wrong version or not found!");
        }
        return grid;
    }
    //endregion


    public void setGameInstance(GameInstance gameInstance)
    {
        this.gameInstance = gameInstance;
    }


    //region Controllers
    public void addControllableObject(GameObject gameObject)
    {
        assert gameObject != null;
        controllableObjects.add(gameObject);
    }

    public boolean removeControllableObject(GameObject gameObject)
    {
        assert gameObject != null;
        return controllableObjects.remove(gameObject);
    }

    public ArrayList<GameObject> getControllableObjects()
    {
        return controllableObjects;
    }

    //endregion
}
