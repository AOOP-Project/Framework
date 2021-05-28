package functional.testingprefabs;

import evhh.annotations.UniqueSerializableField;
import evhh.model.GameObject;
import evhh.model.Grid;
import evhh.model.ObjectPrefab;
import evhh.model.gamecomponents.Sprite;

import java.awt.image.BufferedImage;
import java.util.Objects;

/***********************************************************************************************************************
 * @project: MainProject
 * @package: evhh.model.prefabs
 * ---------------------------------------------------------------------------------------------------------------------
 * @authors: Hamed Haghjo & Elias Vahlberg
 * @date: 2021-05-13
 * @time: 14:38
 **********************************************************************************************************************/
public class WallPrefab extends ObjectPrefab
{

    public WallPrefab(BufferedImage wallTexture, String textureRef, int id)
    {
        super(wallTexture, textureRef, true, id);
    }

    @Override
    public GameObject getInstance(Grid grid, int x, int y)
    {
        GameObject instance = super.getInstance(grid, x, y);
        instance.setCreator(this);
        return instance;
    }
}