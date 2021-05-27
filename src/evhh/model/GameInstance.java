package evhh.model;

import evhh.common.assetloading.AssetLoader;
import evhh.controller.InputManager.KeyboardInput;
import evhh.controller.InputManager.MouseInput;
import evhh.controller.InputManager.UserInputManager;
import evhh.model.gamecomponents.Sprite;
import evhh.view.renderers.FrameRenderer;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;
import java.util.stream.Stream;

/***********************************************************************************************************************
 **********************************************************************************************************************/
public class GameInstance implements ActionListener
{

    //region Constants
    private final long GAMEOBJECT_ID_START = 0x100;
    private final long GAMEOBJECT_ID_INCREMENT = 0x10;
    private final String[] DEFAULT_ALLOWED_TEXTURE_FILE_EXTENSIONS = {".jpeg", ".png", ".bmp", ".webmp", ".gif"};
    //endregion


    //region Fields
    /**
     * Grid currently linked to this GameInstance, can be changed by
     * either manually setting a new Grid or by loading a serialized Grid.
     * @Serializeable
     */
    private Grid mainGrid;

    /**
     * Container for all the mapped user input
     * @NonSerializeable
     */
    private UserInputManager userInputManager;

    /**
     * Housing for main JFrame GameFrame and other different Swing components.
     * Updates content in the swing components based on the renderTimer.
     * @NonSerializeable
     */
    private FrameRenderer frameRenderer;

    /**
     * Timer responsible for periodically updating the internal game model.
     * @Serializeable but is preferably reconstructed.
     */
    private Timer updateTimer;

    /**
     * Timer responsible for periodically updating the external game view.
     * @Serializeable but is preferably reconstructed.
     */
    private Timer renderTimer;

    /**
     * Name of the current GameInstance used as a reference to the user.
     * @Serializeable
     */
    private String gameInstanceName;

    /**
     * Internal id counter responsible for creating unique GameObject id's
     * uses basic incrementation of GAMEOBJECT_ID_INCREMENT to the base value GAMEOBJECT_ID_START
     * @Serializeable
     */
    private long gObjectId = GAMEOBJECT_ID_START;

    /**
     * Array of allowed file extension for the texture assets, used in  AssetLoader
     * default value of DEFAULT_ALLOWED_TEXTURE_FILE_EXTENSIONS
     * @Serializeable
     */
    private String[] allowedTextureFileExtension = DEFAULT_ALLOWED_TEXTURE_FILE_EXTENSIONS;

    /**
     * Map between the string reference and the corresponding BufferedImage
     * @NonSerializeable BufferedImage is inherantly NonSerializeable.
     */
    private HashMap<String, BufferedImage> textures;

    /**
     * If the GameInstance is currently updating, value is changed by calling start()/exit()
     * @Serializeable
     */
    private boolean running = false;

    /**
     * Path to the current mainGrid, set during grid Serialization
     * @Serializeable yes... believe it or not.
     */
    private String gridSavePath;

    /**
     * List of events added to this GameInstance, is null until the first event is added.
     * @NonSerializeable
     */
    private ArrayList<EventTrigger> events;

    /**
     * If event status is check during update, at least one event must be set before this can be true.
     * Can be set by calling startPeriodicEventChecking()/stopPeriodicEventChecking
     * @Serializeable but should not be
     */
    private boolean checkEventsOnUpdate = false;

    /**
     * Saved grids that can be queued upp and switched between during runtime by calling switchGrid()-
     * Appending paths to this list is done by calling addSavedGridPath().
     * Removing paths from this list is done by calling removeSavedGridPath().
     * Is null until one or more paths have been added.
     * @Serializeable
     */
    private ArrayList<String> savedGridPaths;

    /**
     * Index in SavedGridPaths for the current MainGrid if the grid is not loaded using this system then this value is -1.
     * @Serializeable
     */
    private int currentGridIndex = -1;

    /**
     * Internal field used to lock asynchronous operations done during the removing of Listeners from Swing.
     * @Serializeable
     */
    private boolean removeLock = false;
    //endregion

    /**
     * Constructs an empty/incomplete GameInstance, before starting the following must set:
     * FrameRenderer ,MainGrid ,UpdateTimer ,RenderTimer
     * @param gameInstanceName Name of the GameInstance only  used as reference to the user
     */
    public GameInstance(String gameInstanceName)
    {
        this.gameInstanceName = gameInstanceName;
        textures = new HashMap<>();
    }

    /**
     * @return Name of this GameInstance
     * Used as user reference only.
     */
    public String getGameInstanceName()
    {
        return gameInstanceName;
    }

    //region Start/Update/Exit

    /**
     * Starts the GameInstance calling FrameRenderer.start(), MainGrid.start(), UpdateTimer.start()
     *  the start methods in all active GameObjects in the MainGrid and refreshes the sprites in the render.
     *  Presupposes that the following are already set: FrameRenderer ,MainGrid ,UpdateTimer ,RenderTimer.
     *  Called automatically when a new grid is loaded from deserialization/manually.
     *  Part of the Start/Update/Exit loop
     *  @precondition frameRenderer != null
     *  @precondition mainGrid != null
     *  @precondition updateTimer != null
     *  @precondition renderTimer != null
     */
    public void start()
    {
        if (running)
            return;
        assert (frameRenderer != null) : "Cant't start while frame renderer is null";
        assert (mainGrid != null) : "Cant't start while mainGrid is null";
        assert (updateTimer != null) : "Cant't start while updateTimer is null";
        assert (renderTimer != null) : "Cant't start while renderTimer is null";
        running = true;
        frameRenderer.start();
        mainGrid.onStart();
        mainGrid.getDynamicObjects().forEach(GameObject::onStart);
        mainGrid.getStaticObjects().forEach(GameObject::onStart);
        updateTimer.start();
        refreshSpritesInRenderer();
    }

    /**
     * Is called internally at fixed intervals based on the delay set in the UpdateTimer.
     * Part of the Start/Update/Exit loop
     */
    private void update()
    {
        new ArrayList<>(mainGrid.getDynamicObjects()).forEach(GameObject::update);
        if (checkEventsOnUpdate)
            checkEvents();
    }

    /**
     * Stops the updates from executing until start is called again, calls the onExit methods for each GameObject in the MainGrid.
     * Also stops the FrameRenderer.
     * Part of the Start/Update/Exit loop
     */
    public void exit()
    {
        running = false;
        frameRenderer.stop();
        mainGrid.getDynamicObjects().forEach(GameObject::onExit);
        mainGrid.getStaticObjects().forEach(GameObject::onExit);
    }

    /**
     * @return If the GameInstance is currently updating
     */
    public boolean isRunning()
    {
        return running;
    }
    //endregion

    //region Internal Timers

    /**
     * @param updateTimer Timer with a set delay between updating.
     */
    public void setUpdateTimer(Timer updateTimer)
    {
        this.updateTimer = updateTimer;
    }

    /**
     * @param timeDelta Time delay used to construct the update timer
     */
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
    //endregion

    //region Assets

    /**
     * @param allowedTextureFileExtension @see #allowedTextureFileExtension
     */
    public void setAllowedTextureFileExtension(String[] allowedTextureFileExtension)
    {
        assert allowedTextureFileExtension != null;
        this.allowedTextureFileExtension = allowedTextureFileExtension;
    }

    /**
     * Loads texture assets by opening a JFileChooser window and allowing the user to select the path.
     * With this path the loadTextureAssets() method is called
     */
    public void loadTextureAssets()
    {
        String path = AssetLoader.getPathToDir();
        if (!path.equals(""))
            loadTextureAssets(path);
    }

    /**
     * @param path The directory path containing ALL used asset textures used by the FrameRenderer
     */
    public void loadTextureAssets(String path)
    {
        HashMap<String, BufferedImage> map = AssetLoader.LoadImageAssets(path, allowedTextureFileExtension);
        assert map != null;
        map.forEach((k, v) -> textures.put(k, v));
    }

    /**
     * @return Map of String references to BufferedImage textures
     * String reference is by default set to the filename (without file extension) of the loaded asset without any exten
     */
    public HashMap<String, BufferedImage> getTextures()
    {
        return textures;
    }

    /**
     * @param name String reference a BufferedImage texture located in the Textures HashMap
     * @return BufferedImage texture with the corresponding reference
     * @precondition textures != null
     */
    public BufferedImage getTexture(String name)
    {
        assert textures != null : "Texture map is null!";
        return textures.get(name);
    }
    //endregion

    //region Grid

    /**
     * @param mainGrid new MainGrid for this GameInstance
     * If the MainGrid has been set before the previous mapped UserInput listeners are removed from the corresponding swing components.
     * Successive calls to this method will cause this thread to repeatedly synchronize with the AWT event queue, this can slow other game aspects.
     */
    public synchronized void setMainGrid(Grid mainGrid)
    {
        assert mainGrid !=null;
        if (this.mainGrid != null && userInputManager != null)
        {
            removeAllMappedUserInputFromFrame();
        }
        this.mainGrid = mainGrid;
        mainGrid.setGameInstance(this);
        mainGrid.getDynamicObjects().forEach(g -> g.setGrid(mainGrid));
        mainGrid.getStaticObjects().forEach(g -> g.setGrid(mainGrid));
        if (userInputManager != null)
            refreshMappedUserInput();
        if (frameRenderer != null)
            refreshSpritesInRenderer();
        if (isRunning())
        {
            running = false;
            start();
        }
    }

    /**
     * @return Current active main grid
     */
    public Grid getMainGrid()
    {
        return mainGrid;
    }

    /**
     * Adds the GameObject to the grid and sets an instance-unique  id
     * @param gameObject
     * @param x X position in the grid
     * @param y Y position in the grid
     * @return The updated GameObject used for method-chaining
     * @precondition mainGrid != null
     */
    public GameObject addGameObject(GameObject gameObject, int x, int y)
    {
        assert mainGrid != null;
        mainGrid.addGameObject(gameObject, x, y).setId(gObjectId);
        gObjectId += GAMEOBJECT_ID_INCREMENT;
        if (gameObject.getSprite() != null)
            synchronized (frameRenderer.getGridRenderer())
            {
                frameRenderer.getGridRenderer().addSprite(gameObject.getSprite());
            }
        return gameObject;
    }

    /**
     * Adds a new GameObject to the grid and sets an instance-unique  id.
     * NOT recommended since no default sprite can be set.
     * @param x
     * @param y
     * @param isStatic
     * @return
     * @precondition mainGrid != null
     */
    public GameObject addGameObject(int x, int y, boolean isStatic)
    {
        assert mainGrid != null;
        GameObject gameObject = mainGrid.addGameObject(x, y, isStatic);
        gameObject.setId(gObjectId);
        gObjectId += GAMEOBJECT_ID_INCREMENT;
        return gameObject;
    }

    /**
     * @param x X position in the grid
     * @param y Y position in the grid
     * @return GameObject at that coordinate if it is not empty otherwise null
     * @precondition mainGrid != null
     * @precondition mainGrid.isValidCoordinates(x,y)
     */
    public GameObject getGameObject(int x, int y)
    {
        assert mainGrid != null;
        assert mainGrid.isValidCoordinates(x,y);
        return mainGrid.get(x, y);
    }

    /**
     * @param id unique id of the requested GameObject
     * @return requested GameObject
     */
    public GameObject getGameObject(long id)
    {
        assert mainGrid != null;
        return mainGrid.get(id);
    }

    /**
     * @param containedComponent Type of the specific component
     * @return  All GameObjects in the scene that house a component of the provided type
     */
    public GameObject[] getGameObjects(Class<? extends GameComponent> containedComponent)
    {
        assert mainGrid != null;
        return mainGrid.getGameObjectsWithComponent(containedComponent);
    }

    /**
     * @param gridSavePath path to the serialized grid
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public synchronized void loadGridFromSave(String gridSavePath) throws IOException, ClassNotFoundException
    {
        setMainGrid(Grid.deserializeGrid(gridSavePath));
    }

    /**
     * Loads serialized grid and sets it using gridSavePath
     * @throws IOException
     * @throws ClassNotFoundException
     * @precondition gridSavePath != null
     */
    public void loadGridFromSave() throws IOException, ClassNotFoundException
    {
        assert gridSavePath != null;
        setMainGrid(Grid.deserializeGrid(gridSavePath));
        refreshSpritesInRenderer();
        if (userInputManager == null)
            setUserInputManager(new UserInputManager(this));
        refreshMappedUserInput();
    }

    /**
     * @param gridSavePath Path where the current MainGrid grid will be saved
     * @throws IOException if invalid path
     */
    public void saveMainGrid(String gridSavePath) throws IOException
    {
        Grid.serializeGrid(mainGrid, gridSavePath);
    }

    /**
     * same as above but uses gridSavePath
     * @throws IOException if invalid path
     * @precondition gridSavePath != null
     */
    public synchronized void saveMainGrid() throws IOException
    {
        assert gridSavePath != null;
        Grid.serializeGrid(mainGrid, gridSavePath);
    }

    /**
     * @return current set grid save/load path
     */
    public String getGridSavePath()
    {
        return gridSavePath;
    }

    /**
     * @param gridSavePath new MainGrid save/load path
     */
    public void setGridSavePath(String gridSavePath)
    {
        this.gridSavePath = gridSavePath;
    }

    /**
     * Same as above but uses the JFileChooser system
     */
    public void setGridSavePath()
    {
        this.gridSavePath = AssetLoader.setPathToSaveData();
    }


    public void addSavedGridPath(String path)
    {
        if (savedGridPaths == null)
            savedGridPaths = new ArrayList<>();
        savedGridPaths.add(path);
    }

    public boolean removeSavedGridPath(String path)
    {
        if (savedGridPaths == null)
            return false;
        return savedGridPaths.remove(path);
    }

    public boolean removeSavedGridPath(int index)
    {
        if (savedGridPaths == null)
            return false;
        if (index >= savedGridPaths.size() || index < 0)
            return false;
        savedGridPaths.remove(index);
        return true;
    }

    public void switchGrid(int index)
    {
        assert savedGridPaths != null;
        assert savedGridPaths.size() > index;
        assert index > 0;
        try
        {
            loadGridFromSave(savedGridPaths.get(index));
            currentGridIndex = index;
        } catch (IOException | ClassNotFoundException e)
        {
            e.printStackTrace();
        }

    }

    public void reloadGrid()
    {
        assert !(currentGridIndex < 0 || savedGridPaths == null);
        try
        {
            loadGridFromSave(savedGridPaths.get(currentGridIndex));
        } catch (IOException | ClassNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    public int getCurrentGridIndex()
    {
        return currentGridIndex;
    }

    public int numSavedGrids()
    {
        if (savedGridPaths == null)
            return 0;
        else
            return savedGridPaths.size();
    }


    //endregion

    //region Renderer

    /**
     * @return Active FrameRenderer of this GameInstance
     */
    public FrameRenderer getFrameRenderer()
    {
        return frameRenderer;
    }

    /**
     * Assigns a new FrameRenderer for this GameInstance, should be done once before starting the GameInstance.
     * After this the frameRenderer should not be reassigned or this could cause loose AWT event threads.
     * @param frameRenderer new frameRenderer
     */
    public void setFrameRenderer(FrameRenderer frameRenderer)
    {
        this.frameRenderer = frameRenderer;
    }

    public synchronized void refreshSpritesInRenderer()
    {
        assert frameRenderer != null;
        synchronized (frameRenderer.getGridRenderer())
        {
            ArrayList<GameObject> staticObjects = new ArrayList<>(mainGrid.getStaticObjects());
            ArrayList<GameObject> dynamicObjects = new ArrayList<>(mainGrid.getDynamicObjects());

            frameRenderer.getGridRenderer().getSprites().clear();
            Stream.concat(staticObjects.stream(), dynamicObjects.stream()).
                    map(GameObject::getSprite).
                    filter(Objects::nonNull).
                    forEach(c ->
                    {
                        if (c.getTexture() == null)
                            c.onStart();
                        frameRenderer.getGridRenderer().addSprite(c);
                    });
        }
    }

    /**
     * Adds a new sprite to the current FrameRenderer, changes to the sprite after it has been passed to the FrameRenderer
     * will also affect the visuals of the frame.
     * @param sprite Sprite housing positional information via the object parent and the texture to be rendered at this position
     * @precondition frameRenderer != null
     */
    public void addSprite(Sprite sprite)
    {
        assert frameRenderer != null;
        frameRenderer.getGridRenderer().addSprite(sprite);
    }

    /**
     * Starts the the assigned FrameRender, the GameFrame can still be visible before this but will not be updated.
     * @precondition frameRenderer != null
     */
    public void startRenderer()
    {
        assert frameRenderer != null;
        frameRenderer.start();
    }

    /**
     * Stops updating of the FrameRenderer but does not close the frame this can be done by setting GameFrame.setVisible(false)
     * @precondition frameRenderer != null
     */
    public void stopRenderer()
    {
        assert frameRenderer != null;
        frameRenderer.stop();
    }

    /**
     * @param timer Timer responsible for calling the rendering of each frame, the delay value should not be less than that of the updateTimer.
     *              Can be the same as the update timer but this can in some instance cause threading issues.
     * @precondition frameRenderer != null
     */
    public void addRendererTimer(Timer timer)
    {
        assert frameRenderer != null;
        renderTimer = timer;
        frameRenderer.addTimer(timer);
    }

    /**
     * @param delay Delay of internally constructed Timer in the FrameRenderer, delay value should be greater than that of the updateTimer.
     * @precondition frameRenderer != null
     */
    public void addRendererTimer(int delay)
    {
        assert frameRenderer != null;
        frameRenderer.addTimer(delay);
        renderTimer = frameRenderer.getRenderTimer();
    }

    /**
     * @return whether the renderer is started, this is not allways the same as isRunning() since the renderer
     * can run independently from the GameInstance but the state of the model will only be updated manually.
     * @precondition frameRenderer != null
     */
    public boolean isRendererStarted()
    {
        assert frameRenderer != null;
        return frameRenderer.isStarted();
    }


    //endregion

    //region UI

    /**
     * @param userInputManager Assigns a new UserInputManager to this GameInstance, @see #userInputManager
     */
    public void setUserInputManager(UserInputManager userInputManager)
    {
        this.userInputManager = userInputManager;
    }

    /**
     * @return The currently active UserInputManager to this GameInstance
     */
    public UserInputManager getUserInputManager()
    {
        return userInputManager;
    }

    /**
     * Retrieves all MouseInput and KeyInput controllers from the mainGrid and adds it to the userInputManager and then maps the Listeners it to the gridPanel.
     * Does not remove unused mapped input due to thread access limitations with the AWT event queue. To remove mapped user input Remove the responsible GameObject/GameComponent
     * then call the removeAllMappedUserInputFromFrame() after that to remap the rest of the user input call refreshMappedUserInput(). Doing it this way ensures that all
     * operations are thread safe.
     * @precondition mainGrid != null
     * @precondition userInputManager != null
     * @precondition removeLock == false
     *
     */
    public synchronized void refreshMappedUserInput()
    {
        assert mainGrid != null : "Main grid is null!";
        assert userInputManager != null : "The user input manager is null!";
        assert !removeLock;
        synchronized (frameRenderer.getGridRenderer().getGridPanel())
        {

            ArrayList<GameObject> dynamicObjects = new ArrayList<>(mainGrid.getDynamicObjects());
            dynamicObjects.stream().
                    filter(GameObject::isControllable).
                    map(GameObject::getController).
                    forEach(c -> c.onUIMRefresh(userInputManager));

            ArrayList<KeyboardInput> keyboardInputs = new ArrayList<>(userInputManager.getKeyboardInputs());
            keyboardInputs.stream().
                    filter(key1 -> !Arrays.asList(frameRenderer.getGridRenderer().getGridPanel().getKeyListeners()).contains(key1)).
                    forEach(k -> frameRenderer.getGridRenderer().getGridPanel().addKeyListener(k));

            ArrayList<MouseInput> mouseInputs = new ArrayList<>(userInputManager.getMouseInputs());
            mouseInputs.stream().
                    filter(m1 -> !Arrays.asList(frameRenderer.getGridRenderer().getGridPanel().getMouseListeners()).contains(m1)).
                    forEach(m -> frameRenderer.getGridRenderer().getGridPanel().addMouseListener(m));
        }

    }

    /**
     * Removes all added listeners to the GridPanel refreshing the AWT event queue.
     * Synchronizes with the GridPanel threads providing access to threaded swing/AWT components.
     * Does not guarantee thread safety but minimizes threading issues in most basic thread configuration.
     * Creates shallow copies of the keyboardInputs and mouseInputs in the user input manager.
     * Blocks any calls to refreshMappedUserInput() by setting the removeLock.
     * The thread calling this function should have a high priority compared to the Timer threads
     * updating the renderer and gameInstance. Preferably this should be the main thread though this can
     * be dangerous if there is current modifications to the userInputManager.
     * @precondition userInputManager != null
     */
    public synchronized void removeAllMappedUserInputFromFrame()
    {
        assert userInputManager != null : "The user input manager is null!";

        synchronized (frameRenderer.getGridRenderer().getGridPanel())
        {
            removeLock = true;
            ArrayList<KeyboardInput> keyboardInputs = new ArrayList<>(userInputManager.getKeyboardInputs());
            for (KeyboardInput kI : keyboardInputs)
            {
                frameRenderer.getGridRenderer().getGridPanel().removeKeyListener(kI);
            }

            ArrayList<MouseInput> mouseInputs = new ArrayList<>(userInputManager.getMouseInputs());
            for (MouseInput mI : mouseInputs)
            {
                frameRenderer.getGridRenderer().getGridPanel().removeMouseListener(mI);
            }
            userInputManager.getMouseInputs().clear();
            userInputManager.getKeyboardInputs().clear();
            removeLock = false;
        }
    }
    //endregion

    //region Events

    /**
     * Removes and event from the events list. If an event should not be checked on update but should be preserved this must
     * be done externally since GameInstance does not house events that should not be checked if the field
     * checkEventsOnUpdate is set.
     * @param eventTrigger Event that should be removed from the event list
     * @return if the event was removed, if the event is not contained in the event list this will return false
     *
     */
    public boolean removeEvent(EventTrigger eventTrigger)
    {
        assert events != null;
        return events.remove(eventTrigger);
    }

    /**
     * @param eventTrigger EventTrigger housing the condition and the action of the event
     */
    public void addEvent(EventTrigger eventTrigger)
    {
        if (events == null)
            events = new ArrayList<>();
        events.add(eventTrigger);
    }

    /**
     * @return A shallow copy of the events list
     */
    public ArrayList<EventTrigger> getEvents()
    {
        ArrayList<EventTrigger> eventsCopy = new ArrayList<>();
        Collections.copy(eventsCopy, events);
        return eventsCopy;
    }

    /**
     * Checks all the events in the event list. If an event is triggered then the action of that event is preformed.
     * This can be done periodically by calling startPeriodicEventChecking() which is done on the update thread after
     * the game objects in the scene have been updated.
     * @precondition events != null && events.size() > 0
     */
    public void checkEvents()
    {
        assert events != null && events.size() > 0;
        for (EventTrigger eT : events)
        {
            if (eT.checkTrigger())
                eT.runEvent();
        }
    }

    /**
     * Adds event checking of each added event at the end of the GameInstance.update(),
     * if synchronization of the renderer and checking of these events is required then
     * the updateTimer and the renderTimer should be the same.
     * @precondition events != null && events.size() > 0
     */
    public void startPeriodicEventChecking()
    {
        assert events != null && events.size() > 0;
        checkEventsOnUpdate = true;
    }

    /**
     * Removes event checking of each added event at the end of the GameInstance.update().
     */
    public void stopPeriodicEventChecking()
    {
        checkEventsOnUpdate = false;
    }
    //endregion

}
