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
    private int upKeyCode, downKeyCode,rightKeyCode, leftKeyCode;
    private KeyboardInput keyboardInput;
    private UserInputManager uIM;

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
        int x2 = 0,y2 = 0;
        if(action == upKeyCode)
        {
            if(!parent.getGrid().isValidCoordinates(getX(),getY()+1))
                return;
            x2 = getX();
            y2 = getY()+1;
        }
        else if(action == downKeyCode)
        {

            if(!parent.getGrid().isValidCoordinates(getX(),getY()-1))
                return;
            x2 = getX();
            y2 = getY()-1;
        }
        else if(action == rightKeyCode)
        {
            if(!parent.getGrid().isValidCoordinates(getX()+1,getY()))
                return;
            x2 = getX()+1;
            y2 = getY();
        }
        else if(action == leftKeyCode)
        {
            if(!parent.getGrid().isValidCoordinates(getX()-1,getY()))
                return;
            x2 = getX()-1;
            y2 = getY();
        }
        if(parent.getGrid().isEmpty(x2,y2))
            parent.setPosition(x2,y2);
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

    public UserInputManager getuIM()
    {
        return uIM;
    }
}
