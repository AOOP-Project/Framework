package functional.testingprefabs;

import evhh.annotations.UniqueSerializableField;
import evhh.model.GameObject;
import evhh.model.Grid;
import evhh.model.ObjectPrefab;
import functional.testingcomponents.SimpleMove;

import java.awt.image.BufferedImage;

/***********************************************************************************************************************
 * @project: MainProject
 * @package: evhh.model.prefabs
 * ---------------------------------------------------------------------------------------------------------------------
 * @authors: Hamed Haghjo & Elias Vahlberg
 * @date: 2021-05-13
 * @time: 14:56
 **********************************************************************************************************************/
public class MovingSquare extends ObjectPrefab
{

    @UniqueSerializableField
    private int deltaTime;

    public MovingSquare(BufferedImage squareTexture, String textureRef, int id, int deltaTime)
    {
        super(squareTexture, textureRef, false, id);
        this.deltaTime = deltaTime;
    }

    public void setDeltaTime(int deltaTime)
    {
        this.deltaTime = deltaTime;
    }

    @Override
    public GameObject getInstance(Grid grid, int x, int y)
    {
        GameObject instance = super.getInstance(grid, x, y);
        instance.addComponent(new SimpleMove(instance, deltaTime));
        instance.setCreator(this);
        return instance;
    }
}
