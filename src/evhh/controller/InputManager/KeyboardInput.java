package evhh.controller.InputManager;

import evhh.common.RunnableArg;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;
import java.util.Optional;
import java.util.OptionalInt;

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
    RunnableArg<KeyEvent> runnable;
    int[] keyCode;
    int[] keyEventType;


    public KeyboardInput(RunnableArg<KeyEvent> runnable, int[] keyCode, int[] keyEventType)
    {
        this.runnable = runnable;
        this.keyCode = keyCode;
        this.keyEventType = keyEventType;
    }

    @Override
    public void keyTyped(KeyEvent e)
    {

        for (int i = 0; i < keyCode.length;i++)
            if(keyCode[i]==e.getKeyCode())
                if(keyEventType[i]==KeyEvent.KEY_TYPED)
                    runnable.run(e);


    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        for (int i = 0; i < keyCode.length;i++)
            if(keyCode[i]==e.getKeyCode())
                if(keyEventType[i]==KeyEvent.KEY_PRESSED)
                    runnable.run(e);
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
        for (int i = 0; i < keyCode.length;i++)
            if(keyCode[i]==e.getKeyCode())
                if(keyEventType[i]==KeyEvent.KEY_RELEASED)
                    runnable.run(e);
    }
}
