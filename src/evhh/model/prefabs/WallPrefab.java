package evhh.model.prefabs;

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
public class WallPrefab implements ObjectPrefab
{
    private Grid grid;
    private GameObject wallObject;
    transient private BufferedImage wallTexture;
    @UniqueSerializableField
    private String textureRef;

    public WallPrefab(Grid grid, BufferedImage wallTexture,String textureRef)
    {
        this.textureRef = textureRef;
        this.grid = grid;
        this.wallTexture = wallTexture;
    }

    @Override
    public GameObject getInstance(int x, int y)
    {
        wallObject = new GameObject(grid,true,x,y);
        wallObject.addComponent(new Sprite(wallObject,wallTexture,textureRef));
        wallObject.setCreator(this);
        return wallObject;
    }

    @Override
    public Sprite getSprite()
    {
        return new Sprite(null,wallTexture,textureRef);
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WallPrefab that = (WallPrefab) o;
        return this.hashCode()==that.hashCode();
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(textureRef);
    }
}