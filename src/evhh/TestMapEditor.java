package evhh;

import evhh.common.assetloading.AssetLoader;
import evhh.model.Grid;
import evhh.model.ObjectPrefab;
import evhh.model.mapeditor.MapEditor;
import evhh.model.prefabs.MovingSquare;
import evhh.model.prefabs.WallPrefab;

import java.awt.image.BufferedImage;
import java.util.HashMap;

/***********************************************************************************************************************
 * @project: MainProject
 * @package: evhh
 * ---------------------------------------------------------------------------------------------------------------------
 * @authors: Hamed Haghjo & Elias Vahlberg
 * @date: 2021-05-14
 * @time: 23:19
 **********************************************************************************************************************/
public class TestMapEditor
{

    public static void main(String[] args)
    {
        Grid grid1 = new Grid(16,16);

        HashMap<String, BufferedImage> map = AssetLoader.LoadImageAssets(System.getProperty("user.dir")+"\\Assets\\Images", new String[]{"png,jpg"});

        WallPrefab wallPrefab = new WallPrefab(grid1, map.get("wall"), "wall");
        MovingSquare movingSquare = new MovingSquare(grid1, map.get("blank"), "blank", 500);

        ObjectPrefab[] prefabs = {wallPrefab,movingSquare};
        MapEditor mapEditor = new MapEditor(grid1,32,prefabs);

    }
}
