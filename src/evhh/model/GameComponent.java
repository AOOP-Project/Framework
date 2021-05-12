package evhh.model;

/***********************************************************************************************************************
 * @project: MainProject
 * @package: evhh.model
 * ---------------------------------------------------------------------------------------------------------------------
 * @authors: Hamed Haghjo & Elias Vahlberg
 * @date: 2021-05-12
 * @time: 12:25
 **********************************************************************************************************************/
public class GameComponent
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

}
