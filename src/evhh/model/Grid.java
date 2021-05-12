package evhh.model;

import java.util.NoSuchElementException;

/***********************************************************************************************************************
 * @project: MainProject
 * @package: evhh.model
 * ---------------------------------------------------------------------------------------------------------------------
 * @authors: Hamed Haghjo & Elias Vahlberg
 * @date: 2021-05-12
 * @time: 12:26
 **********************************************************************************************************************/
public class Grid
{
    GameObject[][] grid;
    int gridWidth,gridHeight;

    public Grid(int gridWidth, int gridHeight)
    {
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
        grid = new GameObject[gridWidth][gridHeight];
    }

    public void addGameObject(GameObject gObj, int x, int y)
    {
        if(isValidCoordinates(x,y))
            throw new IndexOutOfBoundsException("The coordinates :{x=" +x+" ,y=" +y + "} Are not valid coordinates");
        grid[x][y] = gObj;
        gObj.setGrid(this);
    }

    public void addGameObject(int x, int y, boolean isStatic)
    {
        if(isValidCoordinates(x,y))
            throw new IndexOutOfBoundsException("The coordinates :{x=" +x+" ,y=" +y + "} Are not valid coordinates");
        grid[x][y] = new GameObject(this,isStatic,x,y);
    }

    public void moveGameObject(int x1, int y1, int x2, int y2)
    {
        if(isValidCoordinates(x1,y1))
            throw new NoSuchElementException("The coordinates :{x=" +x1+" ,y=" +y1 + "} Are not valid coordinates");
        if(isValidCoordinates(x2,y2))
            throw new IndexOutOfBoundsException("The coordinates :{x=" +x2+" ,y=" +y2 + "} Are not valid coordinates");

        if (isValidCoordinates(x2,y2))
        {
            grid[x2][y2] = grid[x1][x2];
            grid[x1][y1] = null;
        }
    }

    public void removeGameObject(int x, int y)
    {
        if(isValidCoordinates(x,y))
            throw new NoSuchElementException("The coordinates :{x=" +x+" ,y=" +y + "} Are not valid coordinates");
        grid[x][y] = null;
    }

    public boolean isEmpty(int x, int y)
    {
        if(isValidCoordinates(x,y))
            throw new NoSuchElementException("The coordinates :{x=" +x+" ,y=" +y + "} Are not valid coordinates");
        return grid[x][y] == null;
    }

    public boolean isStatic(int x, int y)
    {
        if(isValidCoordinates(x,y))
            throw new NoSuchElementException("The coordinates :{x=" +x+" ,y=" +y + "} Are not valid coordinates");
        return grid[x][y].isStatic();
    }

    public boolean isDynamic(int x, int y)
    {
        if(isValidCoordinates(x,y))
            throw new NoSuchElementException("The coordinates :{x=" +x+" ,y=" +y + "} Are not valid coordinates");
        return !grid[x][y].isStatic();
    }

    public boolean isValidCoordinates(int x, int y)
    {
        return !(x >= gridWidth || y >= gridHeight || x < 0 || y < 0);
    }
}
