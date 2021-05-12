package evhh.controller.InputManager;

import evhh.common.RunnableArg;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;

/***********************************************************************************************************************
 * @project: MainProject
 * @package: evhh.controller.InputManager
 * ---------------------------------------------------------------------------------------------------------------------
 * @authors: Hamed Haghjo & Elias Vahlberg
 * @date: 2021-05-12
 * @time: 12:31
 *
 * A class that manages mouse events, such as mouse being clicked, pressed, released, entered and exited.
 * The parameter buttonEventMap is required to map the button and the MouseEvent
 **********************************************************************************************************************/
public class MouseInput implements MouseListener
{
    private RunnableArg<MouseEvent> action;
    private HashMap<Integer,Integer> buttonEventMap;

    public MouseInput(RunnableArg<MouseEvent> action, HashMap<Integer, Integer> buttonEventMap)
    {
        this.action = action;
        this.buttonEventMap = buttonEventMap;
    }

    @Override
    public void mouseClicked(MouseEvent e)
    {
        buttonEventMap.forEach((button,event)->
        {
            if(button == e.getButton())
                if(event==MouseEvent.MOUSE_CLICKED)
                    action.run(e);
        });
    }

    @Override
    public void mousePressed(MouseEvent e)
    {
        buttonEventMap.forEach((button,event)->
        {
            if(button == e.getButton())
                if(event==MouseEvent.MOUSE_PRESSED)
                    action.run(e);
        });
    }

    @Override
    public void mouseReleased(MouseEvent e)
    {
        buttonEventMap.forEach((button,event)->
        {
            if(button == e.getButton())
                if(event==MouseEvent.MOUSE_RELEASED)
                    action.run(e);
        });
    }

    @Override
    public void mouseEntered(MouseEvent e)
    {
        buttonEventMap.forEach((button,event)->
        {
            if(button == e.getButton())
                if(event==MouseEvent.MOUSE_ENTERED)
                    action.run(e);
        });
    }

    @Override
    public void mouseExited(MouseEvent e)
    {
        buttonEventMap.forEach((button,event)->
        {
            if(button == e.getButton())
                if(event==MouseEvent.MOUSE_EXITED)
                    action.run(e);
        });
    }
}
