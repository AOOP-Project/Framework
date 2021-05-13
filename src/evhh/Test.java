package evhh;

import evhh.common.assetloading.AssetLoader;
import evhh.model.GameObject;
import evhh.model.Grid;
import evhh.model.gamecomponents.SimpleMove;
import evhh.model.gamecomponents.Sprite;
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
    public static void main(String[] args)
    {
        //frameRenderer.renderFrame();
        //HashMap<String, BufferedImage> images = AssetLoader.LoadImageAssets(AssetLoader.getPathToDir(), new String[]{"jpg", "png"});
        FrameRenderer frameRenderer = new FrameRenderer(new GameFrame(16*32,16*32,"YEE"), 16,16,32);
        HashMap<String, BufferedImage> images = AssetLoader.LoadImageAssets(System.getProperty("user.dir")+"\\Assets", new String[]{"jpg", "png"});
        System.out.println(images.keySet());
        Grid grid = new Grid(16,16);
        frameRenderer.addTimer(100);
        System.out.println(frameRenderer.getGameFrame().getWidth());
        System.out.println(frameRenderer.getGameFrame().getHeight());

        //frameRenderer.getGameFrame().get

        GameObject spriteObj1 = new GameObject(grid, false, 1, 1);
        GameObject spriteObj2 = new GameObject(grid, false, 2, 2);
        grid.addGameObject(spriteObj1,spriteObj1.getX(),spriteObj1.getY());
        grid.addGameObject(spriteObj2,spriteObj2.getX(),spriteObj2.getY());
        spriteObj1.addComponent(new Sprite(spriteObj1, images.get("blank")));
        spriteObj2.addComponent(new Sprite(spriteObj2, images.get("crate")));
        spriteObj1.addComponent(new SimpleMove(spriteObj1,300));

        frameRenderer.getGridRenderer().addSprite((Sprite) spriteObj1.getComponent(Sprite.class));
        frameRenderer.getGridRenderer().addSprite((Sprite) spriteObj2.getComponent(Sprite.class));

        frameRenderer.start();
    }
}
