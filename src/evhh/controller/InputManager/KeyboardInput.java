package evhh.controller.InputManager;

import evhh.common.RunnableArg;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

/***********************************************************************************************************************
 * @project: MainProject
 * @package: evhh.controller.InputManager
 * ---------------------------------------------------------------------------------------------------------------------
 * @authors: Hamed Haghjo & Elias Vahlberg
 * @date: 2021-05-12
 * @time: 12:31
 **********************************************************************************************************************/
public class KeyboardInput implements KeyListener //Removed Serializeable
{
    RunnableArg<KeyEvent> action;
    private HashMap<Integer,Integer> keyEventMap;

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KeyboardInput that = (KeyboardInput) o;
        return that.hashCode()==this.hashCode();
    }

    @Override
    public int hashCode()
    {
        int result =  keyEventMap.hashCode();
        return result;
    }

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
