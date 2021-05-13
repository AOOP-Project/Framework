package evhh.model.gamecomponents;

import evhh.annotations.Precondition;
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
    private TimeReference timeReference;
    long deltaTime;
    public SimpleMove(GameObject parent, long deltaTime)
    {
        super(parent);
        this.deltaTime = deltaTime;
        this.timeReference = new TimeReference();
    }



    /**
     @precondition deltaTime!=0
     */
    public void move()
    {
            assert deltaTime!=0 : "Division by 0";
            long n = timeReference.getDeltaTime() / deltaTime;
            timeReference.incrementStartTime(n*deltaTime);
        for (int i = 0; i < n; i++)
        {
            int w = getGameObject().getGrid().getGridWidth();
            if(parent.getGrid().isEmpty((getX()+1)%w, getY()))
                getGameObject().setPosition((getX()+1)%w, getY());
        }
    }

    @Override
    public void onStart()
    {
        timeReference.start();
    }

    @Override
    public void update()
    {
        move();
    }

    @Override
    public void onExit()
    {

    }
}
