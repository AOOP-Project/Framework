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
    public ControllableComponent(GameObject parent)
    {
        super(parent);
        assert !parent.isStatic() : "Controllable componenet can't be added to a static GameObject";
    }
    public abstract void onUIMRefresh(UserInputManager uIM);

}
