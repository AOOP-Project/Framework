package evhh.model;

import evhh.model.gamecomponents.Sprite;

import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.Objects;

/***********************************************************************************************************************
 **********************************************************************************************************************/
public abstract class ObjectPrefab implements Serializable
{
    protected  transient BufferedImage texture;
    protected  String textureRef;
    protected  int id;
    protected  boolean isStatic;

    /**
     * @param texture Texture used in sprite
     * @param textureRef Reference to the image file
     * @param isStatic If objects created using this prefab should be static or dynamic
     * @param id Unique id used for hash
     */
    public ObjectPrefab(BufferedImage texture, String textureRef, boolean isStatic,int id)
    {
        this.texture = texture;
        this.textureRef  = textureRef ;
        this.isStatic  = isStatic ;
        this.id  = id ;
    }
    /**
     * @param grid the current grid
     * @param x X position in grid
     * @param y Y position in grid
     * @return instantiated Game object
     */

    public GameObject getInstance(Grid grid, int x, int y)
    {
        GameObject instance = new GameObject(grid,isStatic,x,y);
        instance.addComponent(new Sprite(instance,texture,textureRef));
        return instance;
    }

    /**
     * When creating the sprite the (@param GameObject parent) should be null
     * @return A reference sprite used in map editing
     */
    public Sprite getSprite()
    {
        return new Sprite(null,texture,textureRef);
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ObjectPrefab that = (ObjectPrefab) o;
        return id == that.id && textureRef.equals(that.textureRef);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(textureRef, id);
    }
}
