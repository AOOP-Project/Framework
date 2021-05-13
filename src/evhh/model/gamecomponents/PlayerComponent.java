package evhh.model.gamecomponents;

import evhh.common.RunnableArg;
import evhh.controller.InputManager.KeyboardInput;
import evhh.controller.InputManager.UserInputManager;
import evhh.model.GameComponent;
import evhh.model.GameObject;

import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.stream.Stream;

/***********************************************************************************************************************
 * @project: MainProject
 * @package: evhh.model.gamecomponents
 * ---------------------------------------------------------------------------------------------------------------------
 * @authors: Hamed Haghjo & Elias Vahlberg
 * @date: 2021-05-13
 * @time: 15:21
 **********************************************************************************************************************/
public class PlayerComponent extends GameComponent
{
    int upKeyCode, downKeyCode,rightKeyCode, leftKeyCode;
    KeyboardInput keyboardInput;
    UserInputManager uIM;

    public PlayerComponent(GameObject parent, UserInputManager uIM, int upKeyCode, int downKeyCode, int rightKeyCode, int leftKeyCode)
    {
        super(parent);
        this.uIM = uIM;
        this.upKeyCode = upKeyCode;
        this.downKeyCode = downKeyCode;
        this.rightKeyCode = rightKeyCode;
        this.leftKeyCode = leftKeyCode;
        RunnableArg<KeyEvent> keyInputEvent = keyEvent -> move(keyEvent.getKeyCode());
        HashMap<Integer,Integer> map = new HashMap<>();

        map.put(upKeyCode,KeyEvent.KEY_PRESSED);
        map.put(downKeyCode,KeyEvent.KEY_PRESSED);
        map.put(rightKeyCode,KeyEvent.KEY_PRESSED);
        map.put(leftKeyCode,KeyEvent.KEY_PRESSED);
        keyboardInput = new KeyboardInput(keyInputEvent,map);
        uIM.addKeyInput(keyboardInput);

    }
    public void move(int action)
    {
        System.out.println("MOVING: " + action);
        if(action == upKeyCode)
        {
            if(parent.getGrid().isValidCoordinates(getX(),getY()+1))
                parent.setPosition(getX(),getY()+1);
        }
        else if(action == downKeyCode)
        {
            if(parent.getGrid().isValidCoordinates(getX(),getY()-1))
                parent.setPosition(getX(),getY()-1);
        }
        else if(action == rightKeyCode)
        {
            if(parent.getGrid().isValidCoordinates(getX()+1,getY()))
                parent.setPosition(getX()+1,getY());
        }
        else if(action == leftKeyCode)
        {
            if(parent.getGrid().isValidCoordinates(getX()-1,getY()))
                parent.setPosition(getX()-1,getY());
        }
    }
    @Override
    public void onStart()
    {

    }

    @Override
    public void update()
    {

    }

    @Override
    public void onExit()
    {

    }
}
