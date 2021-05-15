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
        MovingSquare movingSquare2 = new MovingSquare(grid1, map.get("blankmarked"), "blankmarked", 400);
        MovingSquare movingSquare3 = new MovingSquare(grid1, map.get("player"), "player", 300);
        MovingSquare movingSquare4 = new MovingSquare(grid1, map.get("specialcrate"), "specialcrate", 200);

        ObjectPrefab[] prefabs = {wallPrefab,movingSquare,movingSquare2 ,movingSquare3,movingSquare4,};
        MapEditor mapEditor = new MapEditor(grid1,32,prefabs);

    }
}
