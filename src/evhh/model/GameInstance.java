package evhh.model;

import evhh.common.assetloading.AssetLoader;
import evhh.controller.InputManager.UserInputManager;
import evhh.model.gamecomponents.Sprite;
import evhh.view.renderers.FrameRenderer;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;
import java.util.stream.Stream;

/***********************************************************************************************************************
 * @project: MainProject
 * @package: evhh.model
 * ---------------------------------------------------------------------------------------------------------------------
 * @authors: Hamed Haghjo & Elias Vahlberg
 * @date: 2021-05-12
 * @time: 12:26
 **********************************************************************************************************************/
public class GameInstance implements ActionListener
{

    //region Constants
    private final long GAMEOBJECT_ID_START = 1000;
    private final long GAMEOBJECT_ID_INCREMENT = 10;
    private final String[] DEFAULT_ALLOWED_TEXTURE_FILE_EXTENSIONS = {"JPEG", "PNG", "BMP", "WEBMP", "GIF"};
    //endregion


    //region Fields
    private Grid mainGrid;
    private UserInputManager userInputManager;
    private FrameRenderer frameRenderer;
    private Timer updateTimer;
    private Timer renderTimer;
    private String gameInstanceName;
    private long gObjectId = GAMEOBJECT_ID_START;
    private String[] allowedTextureFileExtension = DEFAULT_ALLOWED_TEXTURE_FILE_EXTENSIONS;
    private HashMap<String, BufferedImage> textures;
    private boolean running = false;
    private String gridSavePath;
    private ArrayList<EventTrigger> events;
    //endregion

    public GameInstance(String gameInstanceName)
    {
        this.gameInstanceName = gameInstanceName;
        textures = new HashMap<>();
    }

    public String getGameInstanceName()
    {
        return gameInstanceName;
    }

    public void start()
    {
        if (running)
            return;
        assert (frameRenderer != null) : "Cant't start while frame renderer is null";
        assert (mainGrid != null) : "Cant't start while mainGrid is null";
        assert (updateTimer != null) : "Cant't start while updateTimer is null";
        assert (renderTimer != null) : "Cant't start while renderTimer is null";
        running = true;
        refreshSpritesInRenderer();
        frameRenderer.start();
        updateTimer.start();
        mainGrid.getDynamicObjects().forEach(GameObject::onStart);
        mainGrid.getStaticObjects().forEach(GameObject::onStart);
    }

    private void update()
    {
        mainGrid.getDynamicObjects().forEach(GameObject::update);
    }

    public void exit()
    {
        running = false;
        frameRenderer.stop();
        mainGrid.getDynamicObjects().forEach(GameObject::onExit);
        mainGrid.getStaticObjects().forEach(GameObject::onExit);
    }


    public void setUpdateTimer(Timer updateTimer)
    {
        this.updateTimer = updateTimer;
    }

    public void setUpdateTimer(int timeDelta)
    {
        this.updateTimer = new Timer(timeDelta, this);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == updateTimer)
            update();
    }

    public boolean isRunning()
    {
        return running;
    }

    //region Assets

    public void setAllowedTextureFileExtension(String[] allowedTextureFileExtension)
    {
        assert allowedTextureFileExtension != null;
        this.allowedTextureFileExtension = allowedTextureFileExtension;
    }

    public void loadTextureAssets()
    {
        String path = AssetLoader.getPathToDir();
        if (!path.equals(""))
            loadTextureAssets(path);
    }

    public void loadTextureAssets(String path)
    {
        HashMap<String, BufferedImage> map = AssetLoader.LoadImageAssets(path, allowedTextureFileExtension);
        assert map != null;
        map.forEach((k, v) -> textures.put(k, v));
    }

    public HashMap<String, BufferedImage> getTextures()
    {
        return textures;
    }

    public BufferedImage getTexture(String name)
    {
        assert textures != null : "Texture map is null!";
        return textures.get(name);
    }
    //endregion

    //region Grid
    public void setMainGrid(Grid mainGrid)
    {
        this.mainGrid = mainGrid;
        mainGrid.setGameInstance(this);
    }

    public Grid getMainGrid()
    {
        return mainGrid;
    }

    public GameObject addGameObject(GameObject gameObject, int x, int y)
    {
        assert mainGrid != null;
        mainGrid.addGameObject(gameObject, x, y).setId(gObjectId);
        gObjectId += GAMEOBJECT_ID_INCREMENT;
        return gameObject;
    }

    public GameObject addGameObject(int x, int y, boolean isStatic)
    {
        assert mainGrid != null;
        GameObject gameObject = mainGrid.addGameObject(x, y, isStatic);
        gameObject.setId(gObjectId);
        gObjectId += GAMEOBJECT_ID_INCREMENT;
        return gameObject;
    }

    public GameObject getGameObject(int x, int y)
    {
        assert mainGrid != null;
        return mainGrid.get(x, y);
    }

    public GameObject getGameObject(long id)
    {
        assert mainGrid != null;
        return mainGrid.get(id);
    }

    public GameObject[] getGameObjects(Class<? extends GameComponent> containedComponent)
    {
        assert mainGrid != null;
        return mainGrid.getGameObjectsWithComponent(containedComponent);
    }
    //endregion

    //region Renderer

    public FrameRenderer getFrameRenderer()
    {
        return frameRenderer;
    }

    public void setFrameRenderer(FrameRenderer frameRenderer)
    {
        this.frameRenderer = frameRenderer;
    }

    public void refreshSpritesInRenderer()
    {
        assert frameRenderer != null;
        ArrayList<GameObject> staticObjects = (ArrayList<GameObject>) mainGrid.getStaticObjects().clone();
        ArrayList<GameObject> dynamicObjects = (ArrayList<GameObject>) mainGrid.getDynamicObjects().clone();

        frameRenderer.getGridRenderer().getSprites().clear();
        Stream.concat(staticObjects.stream(), dynamicObjects.stream()).
                map(g -> g.getComponent(Sprite.class)).
                filter(Objects::nonNull).map(c -> (Sprite) c).
                forEach(c -> frameRenderer.getGridRenderer().addSprite(c));
    }

    public void addSprite(Sprite sprite)
    {
        assert frameRenderer != null;
        frameRenderer.getGridRenderer().addSprite(sprite);
    }

    public void startRenderer()
    {
        assert frameRenderer != null;
        frameRenderer.start();
    }

    public void stopRenderer()
    {
        assert frameRenderer != null;
        frameRenderer.stop();
    }

    public void addRendererTimer(Timer timer)
    {
        assert frameRenderer != null;
        renderTimer = timer;
        frameRenderer.addTimer(timer);
    }

    public void addRendererTimer(int delay)
    {
        frameRenderer.addTimer(delay);
    }

    public boolean isRendererStarted()
    {
        assert frameRenderer != null;
        return frameRenderer.isStarted();
    }


    //endregion


    public void setUserInputManager(UserInputManager userInputManager)
    {
        this.userInputManager = userInputManager;
    }

    public UserInputManager getUserInputManager()
    {
        return userInputManager;
    }

    public void refreshMappedUserInput()
    {
        assert userInputManager != null : "The user input manager is null!";
        synchronized (this)
        {
            getMainGrid().
                    getDynamicObjects().
                    forEach(g -> g.getComponentList().
                            stream().
                            filter(c -> c instanceof ControllableComponent).
                            forEach(c -> ((ControllableComponent) c).onUIMRefresh(userInputManager)));
            userInputManager.
                    getKeyboardInputs().
                    stream().
                    filter(key1 -> !Arrays.asList(frameRenderer.
                            getGameFrame().
                            getKeyListeners()).
                            contains(key1)).
                    forEach(k -> frameRenderer.getGameFrame().addKeyListener(k));

            userInputManager.
                    getMouseInputs().
                    stream().
                    filter(m1 -> !Arrays.asList(frameRenderer.
                            getGameFrame().
                            getMouseListeners()).
                            contains(m1)).
                    forEach(m -> frameRenderer.getGameFrame().addMouseListener(m));
        }
    }

    public void loadGridFromSave(String gridSavePath) throws IOException, ClassNotFoundException
    {
        setMainGrid(Grid.deserializeGrid(gridSavePath));
        refreshSpritesInRenderer();
        if (userInputManager == null)
            setUserInputManager(new UserInputManager(this));
        refreshMappedUserInput();
    }

    public void loadGridFromSave() throws IOException, ClassNotFoundException
    {
        assert gridSavePath != null;
        setMainGrid(Grid.deserializeGrid(gridSavePath));
        refreshSpritesInRenderer();
        if (userInputManager == null)
            setUserInputManager(new UserInputManager(this));
        refreshMappedUserInput();
    }

    public void saveMainGrid(String gridSavePath) throws IOException
    {
        Grid.serializeGrid(mainGrid, gridSavePath);
    }

    public void saveMainGrid() throws IOException
    {
        assert gridSavePath != null;
        Grid.serializeGrid(mainGrid, gridSavePath);
    }

    public String getGridSavePath()
    {
        return gridSavePath;
    }

    public void setGridSavePath(String gridSavePath)
    {
        this.gridSavePath = gridSavePath;
    }

    public void setGridSavePath()
    {
        this.gridSavePath = AssetLoader.setPathToSaveData();
    }

    public boolean removeEvent(EventTrigger eventTrigger)
    {
        assert events!=null;
        return events.remove(eventTrigger);
    }
    public void addEvent(EventTrigger eventTrigger)
    {
        if(events==null)
            events = new ArrayList<>();
        events.add(eventTrigger);
    }
    public ArrayList<EventTrigger> getEvents()
    {
        ArrayList<EventTrigger> eventsCopy = new ArrayList<>();
                Collections.copy(eventsCopy,events);
        return eventsCopy;
    }
    public void checkEvents()
    {
        if(events==null)
            return;
        for (EventTrigger eT:events)
        {
            if(eT.checkTrigger())
                eT.runEvent();
        }
    }


}
