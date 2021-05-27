package functional.testingcomponents;

import evhh.model.GameComponent;
import evhh.model.GameObject;

/***********************************************************************************************************************
 * @project: MainProject
 * @package: evhh.model
 * ---------------------------------------------------------------------------------------------------------------------
 * @authors: Hamed Haghjo & Elias Vahlberg
 * @date: 2021-05-27
 * @time: 11:35
 **********************************************************************************************************************/
 public class TestComponent extends GameComponent
{

    public boolean ranStart = true;
    public int numUpdates = 0;
    public boolean ranExit = false;

    public TestComponent(GameObject parent)
    {
        super(parent);
    }

    @Override
    public void onStart()
    {
        ranStart = true;
    }

    @Override
    public void update()
    {
        numUpdates++;
    }

    @Override
    public void onExit()
    {
        ranExit = true;
    }
}
