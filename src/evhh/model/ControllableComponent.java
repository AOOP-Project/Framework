package evhh.model;

import evhh.controller.InputManager.UserInputManager;

/***********************************************************************************************************************
 * @project: MainProject
 * @package: evhh.model
 * ---------------------------------------------------------------------------------------------------------------------
 * @authors: Hamed Haghjo & Elias Vahlberg
 * @date: 2021-05-14
 * @time: 11:59
 **********************************************************************************************************************/
public abstract class ControllableComponent extends GameComponent
{
    /**
     * Must be recreated when a new grid is serialized to avoid mismatched between mapped gameobjects/grid and input
     */
    transient protected UserInputManager uIM;

    /**
     * @param parent  Parent GameObject passed to GameComponent default constructor
     * @param uIM The UserInputManager where KeyboardInput/MouseInput is stored
     */
    public ControllableComponent(GameObject parent,UserInputManager uIM)
    {
        super(parent);
        assert !parent.isStatic() : "Controllable componenet can't be added to a static GameObject";
        this.uIM = uIM;
    }


    /**
     * Called from GameInstance after deserialization of a new grid or if a clear connection between the mapped input and component it is attached to.
     *      This function must reconstruct the non-serializable types from serializable types.
     *      Framework specific non-serializable types include: KeyboardInput, MouseInput, RunnableArg, ImageTiler
     * @param uIM New user input manager passed from the game instance
     *
     */
    public abstract void onUIMRefresh(UserInputManager uIM);

}
