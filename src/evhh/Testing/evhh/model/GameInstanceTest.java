
import evhh.controller.InputManager.UserInputManager;
import evhh.model.GameInstance;
import evhh.model.GameObject;
import evhh.model.Grid;
import evhh.model.gamecomponents.Sprite;
import evhh.view.renderers.FrameRenderer;
import evhh.view.renderers.GameFrame;
import functional.testingcomponents.TestComponent;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

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

    private final int DEFAULT_GRID_WIDTH = 16;
    private final int DEFAULT_GRID_HEIGHT = 16;
    private final int DEFAULT_CELL_SIZE = 32;
    private final String GRID_SAVE_PATH = System.getProperty("user.dir") + "/src/evhh/Testing/TestAssets/TestGridSave.ser";
    private GameInstance game1;
    private FrameRenderer frameRenderer;
    private Grid mainGrid;
    private UserInputManager userInputManager;
    private GameObject testObject;
    private TestComponent testComponent;
    private BufferedImage img1,img2,img3;
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
        //game1.loadTextureAssets(System.getProperty("user.dir")+"\\Assets\\Images");
        game1.setUpdateTimer(100);

        testObject = new GameObject(game1.getMainGrid(),false,0,0);
        testComponent = new TestComponent(testObject);
        testObject.addComponent(testComponent);
        // /src/evhh/Testing/TestAssets/img1.png


        try
        {
            img1 = ImageIO.read(new File(System.getProperty("user.dir") + "/src/evhh/Testing/TestAssets/img1.png"));
            img2 = ImageIO.read(new File(System.getProperty("user.dir") + "/src/evhh/Testing/TestAssets/img2.png"));
            img3 = ImageIO.read(new File(System.getProperty("user.dir") + "/src/evhh/Testing/TestAssets/img3.png"));

        } catch (IOException e)
        {
            e.printStackTrace();
            fail();
        }

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
            game1.addGameObject(testObject,1,1);
            game1.setUpdateTimer(10);
            game1.start();
            Thread.sleep(100);
            game1.exit();
            assertNotEquals(0,testComponent.numUpdates);
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
            game1.loadTextureAssets(System.getProperty("user.dir") + "/src/evhh/Testing/TestAssets");
            assertNotNull(game1.getTextures());
            assertNotEquals(0,game1.getTextures().size());

        }catch (Exception e)
        {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void getTextures()
    {
        try{
            game1.loadTextureAssets(System.getProperty("user.dir") + "/src/evhh/Testing/TestAssets");
            HashMap<String, BufferedImage> textures = game1.getTextures();
            assertNotNull(textures);
            assertNotEquals(0, textures.size());
            assertEquals(3,textures.size());
            assertArrayEquals(textures.keySet().toArray(), new String[]{"img3","img2","img1"});

        }catch (Exception e)
        {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void getTexture()
    {
        try{
            game1.loadTextureAssets(System.getProperty("user.dir") + "/src/evhh/Testing/TestAssets");
            assertNotNull(game1.getTexture("img1"));
            assertTrue(compareImages(img1,game1.getTexture("img1")));

        }catch (Exception e)
        {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void setMainGrid()
    {
        try{
            Grid prevGrid = game1.getMainGrid();
            prevGrid.addGameObject(testObject,1,1);

            game1.setMainGrid(new Grid(prevGrid.getGridWidth(),prevGrid.getGridHeight()));
            assertNotEquals(prevGrid,game1.getMainGrid());
            assertTrue(game1.getMainGrid().isEmpty(1,1));
            assertEquals(0, game1.getMainGrid().getDynamicObjects().size());

            game1.setMainGrid(new Grid(prevGrid.getGridWidth()*2,prevGrid.getGridHeight()*2));
            assertNotEquals(prevGrid,game1.getMainGrid());
            assertTrue(game1.getMainGrid().isEmpty(1,1));
            assertEquals(0, game1.getMainGrid().getDynamicObjects().size());
            game1.setMainGrid(new Grid(prevGrid.getGridWidth()*2,prevGrid.getGridHeight()*2));

            assertThrows(AssertionError.class,()->{game1.setMainGrid(null);});

        }catch (Exception e)
        {
            e.printStackTrace();
            fail();
        }

    }

    @Test
    void addGameObject()
    {
        try
        {
         game1.addGameObject(testObject,1,1);
         assertThrows(IndexOutOfBoundsException.class,()->game1.addGameObject(testObject,-1,-1));
         assertEquals(testObject,game1.getGameObject(1,1));
         assertEquals(testObject,mainGrid.get(1,1));
         assertTrue(game1.getMainGrid().getDynamicObjects().contains(testObject));
        }
        catch (Exception e)
        {
            e.printStackTrace();
            fail();
        }
    }


    @Test
    void getGameObject()
    {
        try
        {
            game1.addGameObject(testObject,1,1);
            assertEquals(testObject,game1.getGameObject(1,1));
            assertThrows(AssertionError.class,()->game1.getGameObject(-1,-1));
            assertNotEquals(testObject,mainGrid.get(2,2));
        }
        catch (Exception e)
        {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void getGameObjects()
    {
        try
        {

            GameObject[] constObjs = new GameObject[Math.min(mainGrid.getGridWidth(),mainGrid.getGridHeight())];
            for (int i = 0; i <Math.min(mainGrid.getGridWidth(),mainGrid.getGridHeight()); i++)
            {
                constObjs[i] = getTestObject(game1.getMainGrid(), true, i, i);
                game1.addGameObject(constObjs[i],i,i);
            }
            GameObject[] retObjects = game1.getGameObjects(TestComponent.class);
            assertNotNull(retObjects);
            assertEquals(constObjs.length,retObjects.length);
            assertTrue(Arrays.stream(retObjects).allMatch(o1-> Arrays.asList(constObjs).contains(o1)));

            assertEquals(0,game1.getGameObjects(Sprite.class).length);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void loadGridFromSave()
    {
        try
        {

            GameObject[] constObjs = new GameObject[Math.min(mainGrid.getGridWidth(),mainGrid.getGridHeight())];
            for (int i = 0; i <Math.min(mainGrid.getGridWidth(),mainGrid.getGridHeight()); i++)
            {
                constObjs[i] = getTestObject(game1.getMainGrid(), true, i, i);
                game1.addGameObject(constObjs[i],i,i);
            }
            game1.saveMainGrid(GRID_SAVE_PATH);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void testLoadGridFromSave()
    {
        try
        {

            GameObject[] constObjs = new GameObject[Math.min(mainGrid.getGridWidth(),mainGrid.getGridHeight())];
            for (int i = 0; i <Math.min(mainGrid.getGridWidth(),mainGrid.getGridHeight()); i++)
            {
                constObjs[i] = getTestObject(game1.getMainGrid(), true, i, i);
                game1.addGameObject(constObjs[i],i,i);
            }
            game1.saveMainGrid(GRID_SAVE_PATH);

            game1.loadGridFromSave(GRID_SAVE_PATH);
            assertNotEquals(mainGrid, game1.getMainGrid());
            assertEquals(constObjs.length ,game1.getMainGrid().getStaticObjects().size());
            assertEquals(constObjs.length ,game1.getGameObjects(TestComponent.class).length);

        }
        catch (Exception e)
        {
            e.printStackTrace();
            fail();
        }
    }


    @Test
    void addSavedGridPath()
    {
        try
        {

            GameObject[] constObjs = new GameObject[Math.min(mainGrid.getGridWidth(),mainGrid.getGridHeight())];
            for (int i = 0; i <Math.min(mainGrid.getGridWidth(),mainGrid.getGridHeight()); i++)
            {
                constObjs[i] = getTestObject(game1.getMainGrid(), true, i, i);
                game1.addGameObject(constObjs[i],i,i);
            }
            game1.saveMainGrid(GRID_SAVE_PATH);

            game1.addSavedGridPath(GRID_SAVE_PATH);
            game1.switchGrid(0);
            assertNotEquals(mainGrid, game1.getMainGrid());
            assertEquals(constObjs.length ,game1.getMainGrid().getStaticObjects().size());
            assertEquals(constObjs.length ,game1.getGameObjects(TestComponent.class).length);

        }
        catch (Exception e)
        {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void switchGrid()
    {
        try
        {

            GameObject[] constObjs = new GameObject[Math.min(mainGrid.getGridWidth(),mainGrid.getGridHeight())];
            for (int i = 0; i <Math.min(mainGrid.getGridWidth(),mainGrid.getGridHeight()); i++)
            {
                constObjs[i] = getTestObject(game1.getMainGrid(), true, i, i);
                game1.addGameObject(constObjs[i],i,i);
            }
            game1.saveMainGrid(GRID_SAVE_PATH);

            game1.addSavedGridPath(GRID_SAVE_PATH);
            game1.addSavedGridPath(GRID_SAVE_PATH);
            game1.addSavedGridPath(GRID_SAVE_PATH);
            game1.addSavedGridPath(GRID_SAVE_PATH);
            game1.addSavedGridPath(GRID_SAVE_PATH);
            for (int i = 0; i < 4; i++)
            {
            game1.switchGrid(i);

            assertNotEquals(mainGrid, game1.getMainGrid());
            assertEquals(constObjs.length ,game1.getMainGrid().getStaticObjects().size());
            assertEquals(constObjs.length ,game1.getGameObjects(TestComponent.class).length);
            }
            assertThrows(AssertionError.class,()->game1.switchGrid(5));
        }
        catch (Exception e)
        {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void reloadGrid()
    {
        try
        {

            GameObject[] constObjs = new GameObject[Math.min(mainGrid.getGridWidth(),mainGrid.getGridHeight())];
            for (int i = 0; i <Math.min(mainGrid.getGridWidth(),mainGrid.getGridHeight()); i++)
            {
                constObjs[i] = getTestObject(game1.getMainGrid(), true, i, i);
                game1.addGameObject(constObjs[i],i,i);
            }
            game1.saveMainGrid(GRID_SAVE_PATH);

            game1.addSavedGridPath(GRID_SAVE_PATH);
            game1.switchGrid(0);
            for (int i = 0; i < 100; i++)
            {
                Grid prevGrid = game1.getMainGrid();
                game1.reloadGrid();
                assertNotEquals(prevGrid, game1.getMainGrid());
                assertEquals(constObjs.length ,game1.getMainGrid().getStaticObjects().size());
                assertEquals(constObjs.length ,game1.getGameObjects(TestComponent.class).length);
            }
            assertThrows(AssertionError.class,()->game1.switchGrid(5));
        }
        catch (Exception e)
        {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void refreshSpritesInRenderer()
    {
        try
        {
            game1.loadTextureAssets(System.getProperty("user.dir")+"\\Assets\\Images");
            GameObject[] gameObjects = new GameObject[Math.min(game1.getMainGrid().getGridWidth(),game1.getMainGrid().getGridHeight())];
            for (int i = 0; i < Math.min(game1.getMainGrid().getGridWidth(),game1.getMainGrid().getGridHeight()); i++)
            {
                gameObjects[i] = getTestObject(game1.getMainGrid(),false,i,i);
                gameObjects[i].addComponent(new Sprite(gameObjects[i],game1.getTexture("img1"),"img1"));
                game1.addGameObject(gameObjects[i],i,i);

            }
            game1.refreshSpritesInRenderer();
            assertTrue(game1.getFrameRenderer().getGridRenderer().getSprites().stream().allMatch(sprite -> Arrays.stream(gameObjects).anyMatch(gameObject -> gameObject.getSprite().equals(sprite))));

        }
        catch (Exception e)
        {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void addSprite()
    {
        try
        {
            game1.loadTextureAssets(System.getProperty("user.dir")+"\\Assets\\Images");
            GameObject[] gameObjects = new GameObject[Math.min(game1.getMainGrid().getGridWidth(),game1.getMainGrid().getGridHeight())];
            for (int i = 0; i < Math.min(game1.getMainGrid().getGridWidth(),game1.getMainGrid().getGridHeight()); i++)
            {
                gameObjects[i] = getTestObject(game1.getMainGrid(),false,i,i);
                gameObjects[i].addComponent(new Sprite(gameObjects[i],game1.getTexture("img1"),"img1"));
                game1.addGameObject(gameObjects[i],i,i);
                game1.addSprite(gameObjects[i].getSprite());

            }
            assertTrue(game1.getFrameRenderer().getGridRenderer().getSprites().stream().allMatch(sprite -> Arrays.stream(gameObjects).anyMatch(gameObject -> gameObject.getSprite().equals(sprite))));
        }
        catch (Exception e)
        {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void refreshMappedUserInput()
    {
        try
        {
            GameObject[] gameObjects = new GameObject[Math.min(game1.getMainGrid().getGridWidth(),game1.getMainGrid().getGridHeight())];
            for (int i = 0; i < Math.min(game1.getMainGrid().getGridWidth(),game1.getMainGrid().getGridHeight()); i++)
            {
                gameObjects[i] = getTestObject(game1.getMainGrid(),false,i,i);
                gameObjects[i].addComponent(new TestControllableComponent(gameObjects[i],game1.getUserInputManager(),i));
                game1.addGameObject(gameObjects[i],i,i);

            }
            refreshMappedUserInput();
            assertTrue(game1.getFrameRenderer().getGridRenderer().getSprites().stream().allMatch(sprite -> Arrays.stream(gameObjects).anyMatch(gameObject -> gameObject.getSprite().equals(sprite))));
        }
        catch (Exception e)
        {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void removeAllMappedUserInputFromFrame()
    {
        try
        {
        }
        catch (Exception e)
        {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void removeEvent()
    {
        try
        {
        }
        catch (Exception e)
        {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void addEvent()
    {
        try
        {
        }
        catch (Exception e)
        {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void checkEvents()
    {
        try
        {
        }
        catch (Exception e)
        {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void startPeriodicEventChecking()
    {
    }

    /**
     * Compares two images pixel by pixel.
     * All credit goes to https://stackoverflow.com/users/1762224/mr-polywhirl
     * @param imgA the first image.
     * @param imgB the second image.
     * @return whether the images are both the same or not.
     */
    private static boolean compareImages(BufferedImage imgA, BufferedImage imgB) {
        // The images must be the same size.
        if (imgA.getWidth() != imgB.getWidth() || imgA.getHeight() != imgB.getHeight()) {
            return false;
        }

        int width  = imgA.getWidth();
        int height = imgA.getHeight();

        // Loop over every pixel.
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                // Compare the pixels for equality.
                if (imgA.getRGB(x, y) != imgB.getRGB(x, y)) {
                    return false;
                }
            }
        }

        return true;
    }
    private static GameObject getTestObject(Grid grid,boolean isStatic,int x, int y)
    {
        GameObject testObject = new GameObject(grid,isStatic,x,y);
        testObject.addComponent(new TestComponent(testObject));
        return testObject;
    }
}