package evhh;

import evhh.controller.InputManager.UserInputManager;
import evhh.model.GameInstance;
import evhh.model.Grid;
import evhh.model.prefabs.MovingSquare;
import evhh.model.prefabs.TestPlayerPrefab;
import evhh.model.prefabs.WallPrefab;
import evhh.view.renderers.FrameRenderer;
import evhh.view.renderers.GameFrame;

import javax.swing.*;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/***********************************************************************************************************************
 * @project: MainProject
 * @package: PACKAGE_NAME
 * -----------------------------------------------------
 * @authors: Hamed Haghjo & Elias Vahlberg
 * @date: 2021-05-12
 * @time: 11:18
 **********************************************************************************************************************/
public class Test
{
    public static final int DEFAULT_GRID_WIDTH = 16;
    public static final int DEFAULT_GRID_HEIGHT = 16;
    public static final int DEFAULT_CELL_SIZE = 32;
    public static final String GRID_SAVE_PATH = System.getProperty("user.dir")+"\\SavedData\\grid1.ser";
    public static void main(String[] args)
    {
        GameInstance game1 = new GameInstance("Game1");
        FrameRenderer frameRenderer = new FrameRenderer(
                new GameFrame(
                     DEFAULT_GRID_WIDTH*DEFAULT_CELL_SIZE,
                     DEFAULT_GRID_WIDTH*DEFAULT_CELL_SIZE,
                        game1.getGameInstanceName()),
                        DEFAULT_GRID_WIDTH,
                        DEFAULT_GRID_HEIGHT,
                        DEFAULT_CELL_SIZE);
        game1.setFrameRenderer(frameRenderer);
        game1.addRendererTimer(100);
        game1.loadTextureAssets(System.getProperty("user.dir")+"\\Assets\\Images");
        game1.setUpdateTimer(100);

        /*********/
        boolean loadingSave;
        int reply = JOptionPane.showConfirmDialog(null, "Do you want to load saved grid?", "Load Save", JOptionPane.YES_NO_OPTION);
        if (reply == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(null, "Loading saved grid");
            loadingSave = true;
        } else {
            JOptionPane.showMessageDialog(null, "Creating new Grid");
            loadingSave = false;
        }
        /*********/
        if(loadingSave)
        {
            try
            {
                game1.loadGridFromSave(GRID_SAVE_PATH);
            } catch (IOException | ClassNotFoundException e)
            {
                System.err.println(e);
                createTestInstance(game1);
            }
        }
        else
        {
            createTestInstance(game1);
            saveGameWithDelay(5000,game1);
        }


        game1.refreshMappedUserInput();

        game1.refreshSpritesInRenderer();
        game1.startRenderer();

        game1.start();
        game1.refreshSpritesInRenderer();
    }


    public static void createTestInstance(GameInstance game1)
    {
        game1.setUserInputManager(new UserInputManager(game1));
        game1.setMainGrid(new Grid(DEFAULT_GRID_WIDTH, DEFAULT_GRID_HEIGHT));
        WallPrefab wallPrefab = new WallPrefab(game1.getTexture("wall"), "wall",100);
        MovingSquare movingSquare = new MovingSquare( game1.getTexture("blank"), "blank", 120,500);

        for (int i = 5; i < 14; i++)
            game1.addGameObject(wallPrefab.getInstance(game1.getMainGrid(),i, i), i, i);

        game1.addGameObject(movingSquare.getInstance(game1.getMainGrid(),1, 1), 1, 1);
        movingSquare.setDeltaTime(400);
        game1.addGameObject(movingSquare.getInstance(game1.getMainGrid(),2, 2), 2, 2);
        movingSquare.setDeltaTime(300);
        game1.addGameObject(movingSquare.getInstance(game1.getMainGrid(),3, 3), 3, 3);
        TestPlayerPrefab playerPrefab = new TestPlayerPrefab(game1.getTexture("player"), "player", 150,game1.getUserInputManager());
        game1.addGameObject(playerPrefab.getInstance(game1.getMainGrid(),15, 15), 15, 15);
    }
    public static void saveGameWithDelay(int delay, GameInstance game1)
    {
        TimerTask timerTask = new TimerTask()
        {
            @Override
            public void run()
            {
                try
                {
                    Grid.serializeGrid(game1.getMainGrid(), GRID_SAVE_PATH);
                } catch (IOException e)
                {
                    System.err.println(e.getMessage());
                    e.printStackTrace();
                    System.err.println("File not saved");
                    return;
                }
            }
        };

        Timer timer = new Timer();
        timer.schedule(timerTask,delay);
    }

}
