package evhh.model.prefabs;

import evhh.model.GameObject;
import evhh.model.Grid;
import evhh.model.ObjectPrefab;
import evhh.model.gamecomponents.SimpleMove;
import evhh.model.gamecomponents.Sprite;

import java.awt.image.BufferedImage;

/***********************************************************************************************************************
 * @project: MainProject
 * @package: evhh.model.prefabs
 * ---------------------------------------------------------------------------------------------------------------------
 * @authors: Hamed Haghjo & Elias Vahlberg
 * @date: 2021-05-13
 * @time: 14:56
 **********************************************************************************************************************/
public class MovingSquare implements ObjectPrefab
{
    Grid grid;
    BufferedImage squareTexture;
    int deltaTime;
    String textureRef;
    public MovingSquare(Grid grid, BufferedImage squareTexture,String textureRef,int deltaTime)
    {
        this.grid = grid;
        this.squareTexture = squareTexture;
        this.textureRef = textureRef;
        this.deltaTime = deltaTime;
    }

    public void setDeltaTime(int deltaTime)
    {
        this.deltaTime = deltaTime;
    }

    @Override
    public GameObject getInstance(int x, int y)
    {
        GameObject gameObject = new GameObject(grid,false,x,y);
        gameObject.addComponent(new SimpleMove(gameObject,deltaTime));
        gameObject.addComponent(new Sprite(gameObject,squareTexture,textureRef));
        return gameObject;
    }
}
