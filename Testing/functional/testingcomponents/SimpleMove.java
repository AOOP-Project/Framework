package functional.testingcomponents;

import evhh.common.TimeReference;
import evhh.model.GameComponent;
import evhh.model.GameObject;

/***********************************************************************************************************************
 * @project: MainProject
 * @package: evhh.model.gamecomponents
 * ---------------------------------------------------------------------------------------------------------------------
 * @authors: Hamed Haghjo & Elias Vahlberg
 * @date: 2021-05-12
 * @time: 16:22
 **********************************************************************************************************************/
public class SimpleMove extends GameComponent
{
    transient private TimeReference timeReference;
    private long deltaTime;

    public SimpleMove(GameObject parent, long deltaTime)
    {
        super(parent);
        this.deltaTime = deltaTime;
        this.timeReference = new TimeReference();
    }


    /**
     * @precondition deltaTime!=0
     */
    public void move()
    {
        //Since it is transient
        if (timeReference == null)
            timeReference = new TimeReference();
        assert deltaTime != 0 : "Division by 0";
        long n = timeReference.getDeltaTime() / deltaTime;
        timeReference.incrementStartTime(n * deltaTime);
        for (int i = 0; i < n; i++)
        {
            int w = getGameObject().getGrid().getGridWidth();
            if (parent.getGrid().isEmpty((getX() + 1) % w, getY()))
                getGameObject().setPosition((getX() + 1) % w, getY());
        }
    }

    @Override
    public void onStart()
    {
        if (timeReference == null)
            timeReference = new TimeReference();
        timeReference.start();
    }

    @Override
    public void update()
    {
        if (timeReference == null)
            timeReference = new TimeReference();
        move();
    }

    @Override
    public void onExit()
    {
        if (timeReference == null)
            timeReference = new TimeReference();

    }
}
