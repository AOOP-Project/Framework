package evhh.model.gamecomponents;

import evhh.model.GameComponent;
import evhh.model.GameObject;
import java.util.TimerTask;
import java.util.Timer;

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
    private TimerTask timerTask;
    private Timer timer;
    public SimpleMove(GameObject parent, int delay)
    {
        super(parent);
        timerTask = new TimerTask()
        {
            @Override
            public void run()
            {
                move();
            }
        };
        timer = new Timer();
        timer.scheduleAtFixedRate(timerTask,1000,300);

    }

    public void move()
    {
        int w = getGameObject().getGrid().getGridWidth();
        getGameObject().setPosition((getX()+1)%w, getY());
    }
}
