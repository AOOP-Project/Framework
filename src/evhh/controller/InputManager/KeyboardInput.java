package evhh.controller.InputManager;

import evhh.common.RunnableArg;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.util.HashMap;

/***********************************************************************************************************************
 * @project: MainProject
 * @package: evhh.controller.InputManager
 * ---------------------------------------------------------------------------------------------------------------------
 * @authors: Hamed Haghjo & Elias Vahlberg
 * @date: 2021-05-12
 * @time: 12:31
 **********************************************************************************************************************/
public class KeyboardInput implements KeyListener
{
    RunnableArg<KeyEvent> action;
    int[] keyCode;
    int[] keyEventType;
    private HashMap<Integer,Integer> keyEventMap;


    public KeyboardInput(RunnableArg<KeyEvent> action, HashMap<Integer, Integer> keyEventMap)
    {
        this.action = action;
        this.keyEventMap = keyEventMap;
    }

    @Override
    public void keyTyped(KeyEvent e)
    {
        keyEventMap.forEach((button,event)->
        {
            if(button == e.getKeyCode())
                if(event== KeyEvent.KEY_TYPED)
                    action.run(e);
        });

    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        System.out.println(e.getKeyCode());
        keyEventMap.forEach((button,event)->
        {
            if(button == e.getKeyCode())
                if(event== KeyEvent.KEY_PRESSED)
                    action.run(e);
        });
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
        keyEventMap.forEach((button,event)->
        {
            if(button == e.getKeyCode())
                if(event== KeyEvent.KEY_RELEASED)
                    action.run(e);
        });
    }
}
