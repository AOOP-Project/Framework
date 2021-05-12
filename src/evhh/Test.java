package evhh;

import evhh.common.assetloading.AssetLoader;
import evhh.model.GameObject;
import evhh.model.Grid;
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
        FrameRenderer frameRenderer = new FrameRenderer(new GameFrame(16*32,16*32,"YEE"), 16,16,32);
        frameRenderer.addTimer(100);
        //HashMap<String, BufferedImage> images = AssetLoader.LoadImageAssets(AssetLoader.getPathToDir(), new String[]{"jpg", "png"});
        HashMap<String, BufferedImage> images = AssetLoader.LoadImageAssets("C:\\Users\\elias\\IdeaProjects\\AOOP_Project_05_11\\Assets", new String[]{"jpg", "png"});
        System.out.println(images.keySet());
        Grid grid = new Grid(16,16);
        System.out.println(frameRenderer.getGameFrame().getWidth());
        System.out.println(frameRenderer.getGameFrame().getHeight());

        //frameRenderer.getGameFrame().get


        GameObject spriteObj1 = new GameObject(grid, false, 1, 1);
        GameObject spriteObj2 = new GameObject(grid, false, 2, 2);
        spriteObj1.addComponent(new Sprite(spriteObj1, images.get("blank")));
        spriteObj2.addComponent(new Sprite(spriteObj2, images.get("crate")));

        frameRenderer.getGridRenderer().addSprite((Sprite) spriteObj1.getComponent(Sprite.class));
        frameRenderer.getGridRenderer().addSprite((Sprite) spriteObj2.getComponent(Sprite.class));

        frameRenderer.start();
    }
    public static void test1()
    {

    }
}
