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
    /**
     * Where the GameObject where this component resides
     */
    protected GameObject parent;

    /**
     * Constructs the minimal variant of a component which does nothing
     *
     * @param parent The GameObject where this component resides
     */
    public GameComponent(GameObject parent)
    {
        this.parent = parent;
    }

    /**
     * @return The active parent GameObject, this GameObject does not have to be visible to the current mainGrid but either one or
     * more of the components must accessible otherwise this GameObject must be.
     */
    public GameObject getGameObject()
    {
        return parent;
    }

    /**
     * @return The current x position corresponding to a valid cell in the grid saved in the parent GameObject
     */
    public int getX()
    {
        return parent.getX();
    }

    /**
     * @return The current x position corresponding to a valid cell in the grid saved in the parent GameObject
     */
    public int getY()
    {
        return parent.getY();
    }


    /**
     * Automatically called when:
     * 1. The corresponding GameInstance starts for the
     * 2. A new grid is set (mainly from deserialization)
     * Part of the Start/Update/Exit loop
     */
    abstract public void onStart();

    /**
     * Called at fixed time intervals determined by the delay in the updateTimer in the corresponding GameInstance.
     * Part of the Start/Update/Exit loop
     */
    abstract public void update();

    /**
     * Called when the GameInstance is stopped.
     * Part of the Start/Update/Exit loop
     */
    abstract public void onExit();

}
