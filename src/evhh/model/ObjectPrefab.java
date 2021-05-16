package evhh.model;

import evhh.model.gamecomponents.Sprite;

import java.io.Serializable;

/***********************************************************************************************************************
 **********************************************************************************************************************/
public interface ObjectPrefab extends Serializable
{
    /**
     * @param x X position in grid
     * @param y Y position in grid
     * @return instantiated Game object
     */
    public GameObject getInstance(int x, int y);

    /**
     * When creating the sprite the (@param GameObject parent) should be null
     * @return A reference sprite used in map editing
     */
    public Sprite getSprite();
}
