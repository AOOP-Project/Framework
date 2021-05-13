package evhh.model;

import java.io.Serializable;

/***********************************************************************************************************************
 * @project: MainProject
 * @package: evhh.model
 * ---------------------------------------------------------------------------------------------------------------------
 * @authors: Hamed Haghjo & Elias Vahlberg
 * @date: 2021-05-12
 * @time: 12:25
 **********************************************************************************************************************/
public abstract class GameComponent implements Serializable
{
    protected GameObject parent;

    public GameComponent(GameObject parent)
    {
        this.parent = parent;
    }

    public GameObject getGameObject()
    {
        return parent;
    }

    public int getX()
    {
        return parent.getX();
    }
    public int getY()
    {
        return parent.getY();
    }

    abstract public void onStart();
    abstract public void update();
    abstract public void onExit();

}
