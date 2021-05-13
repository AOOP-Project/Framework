package evhh;

import evhh.common.assetloading.AssetLoader;
import evhh.controller.InputManager.UserInputManager;
import evhh.model.GameInstance;
import evhh.model.GameObject;
import evhh.model.Grid;
import evhh.model.ObjectPrefab;
import evhh.model.gamecomponents.SimpleMove;
import evhh.model.gamecomponents.Sprite;
import evhh.model.prefabs.MovingSquare;
import evhh.model.prefabs.PlayerPrefab;
import evhh.model.prefabs.WallPrefab;
import evhh.view.renderers.FrameRenderer;
import evhh.view.renderers.GameFrame;

import java.awt.image.BufferedImage;
import java.util.HashMap;

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
        game1.setMainGrid(new Grid(DEFAULT_GRID_WIDTH,DEFAULT_GRID_HEIGHT));
        game1.setUserInputManager(new UserInputManager(game1));

        game1.setUpdateTimer(100);

        GameObject gameObject1 = game1.addGameObject(1,1,false);
        GameObject gameObject2 = game1.addGameObject(4,4,false);
        WallPrefab wallPrefab = new WallPrefab(game1.getMainGrid(),game1.getTexture("wall"));
        MovingSquare movingSquare = new MovingSquare(game1.getMainGrid(),game1.getTexture("blank"),500);

        for (int i = 5; i < 14; i++)
            game1.addGameObject(wallPrefab.getInstance(i,i),i,i);

        game1.addGameObject(movingSquare.getInstance(1,1),1,1);
        movingSquare.setDeltaTime(400);
        game1.addGameObject(movingSquare.getInstance(2,2),2,2);
        movingSquare.setDeltaTime(300);
        game1.addGameObject(movingSquare.getInstance(3,3),3,3);
        PlayerPrefab playerPrefab = new PlayerPrefab(game1.getMainGrid(), game1.getTexture("player"),game1.getUserInputManager());
        game1.addGameObject(playerPrefab.getInstance(15,15),15,15);

        game1.refreshMappedUserInput();

        game1.refreshSpritesInRenderer();
        game1.startRenderer();
        game1.start();

        if(false)
        {
            //frameRenderer.renderFrame();
            //HashMap<String, BufferedImage> images = AssetLoader.LoadImageAssets(AssetLoader.getPathToDir(), new String[]{"jpg", "png"});
            HashMap<String, BufferedImage> images = AssetLoader.LoadImageAssets(System.getProperty("user.dir") + "\\Assets\\Images", new String[]{"jpg", "png"});
            System.out.println(images.keySet());
            Grid grid = new Grid(16, 16);
            frameRenderer.addTimer(100);
            System.out.println(frameRenderer.getGameFrame().getWidth());
            System.out.println(frameRenderer.getGameFrame().getHeight());

            //frameRenderer.getGameFrame().get

            GameObject spriteObj1 = new GameObject(grid, false, 1, 1);
            GameObject spriteObj2 = new GameObject(grid, false, 2, 2);
            grid.addGameObject(spriteObj1, spriteObj1.getX(), spriteObj1.getY());
            grid.addGameObject(spriteObj2, spriteObj2.getX(), spriteObj2.getY());
            //
            spriteObj1.addComponent(new Sprite(spriteObj1, images.get("blank")));
            spriteObj2.addComponent(new Sprite(spriteObj2, images.get("crate")));
            spriteObj1.addComponent(new SimpleMove(spriteObj1, 300));

            frameRenderer.getGridRenderer().addSprite((Sprite) spriteObj1.getComponent(Sprite.class));
            frameRenderer.getGridRenderer().addSprite((Sprite) spriteObj2.getComponent(Sprite.class));

            frameRenderer.start();
        }
    }

}
