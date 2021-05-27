package evhh.model;

import evhh.controller.InputManager.UserInputManager;
import evhh.view.renderers.FrameRenderer;
import evhh.view.renderers.GameFrame;
import functional.testingcomponents.TestComponent;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/***********************************************************************************************************************
 * @project: MainProject
 * @package: evhh.model
 * ---------------------------------------------------------------------------------------------------------------------
 * @authors: Hamed Haghjo & Elias Vahlberg
 * @date: 2021-05-24
 * @time: 09:08
 **********************************************************************************************************************/
class GameInstanceTest
{

    private int DEFAULT_GRID_WIDTH = 16;
    private int DEFAULT_GRID_HEIGHT = 16;
    private int DEFAULT_CELL_SIZE = 32;
    private GameInstance game1;
    private FrameRenderer frameRenderer;
    private Grid mainGrid;
    private UserInputManager userInputManager;
    private GameObject testObject;
    private TestComponent testComponent;
    @BeforeEach
    void setUp()
    {
        game1 = new GameInstance("Test1");
        frameRenderer = new FrameRenderer(
                new GameFrame(
                        DEFAULT_GRID_WIDTH*DEFAULT_CELL_SIZE,
                        DEFAULT_GRID_WIDTH*DEFAULT_CELL_SIZE,
                        game1.getGameInstanceName()),
                DEFAULT_GRID_WIDTH,
                DEFAULT_GRID_HEIGHT,
                DEFAULT_CELL_SIZE);
        mainGrid = new Grid(DEFAULT_GRID_WIDTH,DEFAULT_GRID_HEIGHT);
        userInputManager = new UserInputManager(game1);
        game1.setFrameRenderer(frameRenderer);
        game1.setMainGrid(mainGrid);
        game1.setUserInputManager(userInputManager);
        game1.addRendererTimer(100);
        game1.loadTextureAssets(System.getProperty("user.dir")+"\\Assets\\Images");
        game1.setUpdateTimer(100);

        testObject = new GameObject(game1.getMainGrid(),false,0,0);
        testComponent = new TestComponent(testObject);
        testObject.addComponent(testComponent);

    }

    @AfterEach
    void tearDown()
    {
        game1 = null;
        mainGrid = null;
        userInputManager = null;
        frameRenderer.getGameFrame().setVisible(false);
        frameRenderer = null;
    }

    @Test
    void setUpdateTimer()
    {

        try
        {
            game1.setUpdateTimer(1);
            game1.start();
            Thread.sleep(100);
            game1.exit();
            assertEquals(100,testComponent.numUpdates);
            assertTrue(testComponent.ranStart);
            assertTrue(testComponent.ranExit);

        } catch (InterruptedException e)
        {
            fail();
        }
    }


    @Test
    void setAllowedTextureFileExtension()
    {
    }

    @Test
    void loadTextureAssets()
    {
        try{
            game1.loadTextureAssets(System.getProperty("user.dir") + "/Assets/Images");

        }catch (Exception e)
        {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void testLoadTextureAssets()
    {
    }

    @Test
    void getTextures()
    {
    }

    @Test
    void getTexture()
    {
    }

    @Test
    void setMainGrid()
    {
    }

    @Test
    void addGameObject()
    {
    }

    @Test
    void testAddGameObject()
    {
    }

    @Test
    void getGameObject()
    {
    }

    @Test
    void testGetGameObject()
    {
    }

    @Test
    void getGameObjects()
    {
    }

    @Test
    void loadGridFromSave()
    {
    }

    @Test
    void testLoadGridFromSave()
    {
    }

    @Test
    void saveMainGrid()
    {
    }

    @Test
    void testSaveMainGrid()
    {
    }

    @Test
    void getGridSavePath()
    {
    }

    @Test
    void addSavedGridPath()
    {
    }

    @Test
    void switchGrid()
    {
    }

    @Test
    void reloadGrid()
    {
    }

    @Test
    void refreshSpritesInRenderer()
    {
    }

    @Test
    void addSprite()
    {
    }

    @Test
    void addRendererTimer()
    {
    }

    @Test
    void testAddRendererTimer()
    {
    }

    @Test
    void refreshMappedUserInput()
    {
    }

    @Test
    void removeAllMappedUserInputFromFrame()
    {
    }

    @Test
    void removeEvent()
    {
    }

    @Test
    void addEvent()
    {
    }

    @Test
    void checkEvents()
    {
    }

    @Test
    void startPeriodicEventChecking()
    {
    }

    @Test
    void stopPeriodicEventChecking()
    {
    }
}