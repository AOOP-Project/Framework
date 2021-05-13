package evhh.model.prefabs;

import evhh.model.GameObject;
import evhh.model.Grid;
import evhh.model.ObjectPrefab;
import evhh.model.gamecomponents.Sprite;

import java.awt.image.BufferedImage;

/***********************************************************************************************************************
 * @project: MainProject
 * @package: evhh.model.prefabs
 * ---------------------------------------------------------------------------------------------------------------------
 * @authors: Hamed Haghjo & Elias Vahlberg
 * @date: 2021-05-13
 * @time: 14:38
 **********************************************************************************************************************/
public class WallPrefab implements ObjectPrefab
{

    Grid grid;
    GameObject wallObject;
    BufferedImage wallTexture;

    public WallPrefab(Grid grid, BufferedImage wallTexture)
    {
        this.grid = grid;
        this.wallTexture = wallTexture;
    }

    @Override
    public GameObject getInstance(int x, int y)
    {
        wallObject = new GameObject(grid,true,x,y);
        wallObject.addComponent(new Sprite(wallObject,wallTexture));
        wallObject.setCreator(this);
        return wallObject;
    }
}