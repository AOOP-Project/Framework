package functional.testingcomponents;

import evhh.common.RunnableArg;
import evhh.controller.InputManager.KeyboardInput;
import evhh.controller.InputManager.UserInputManager;
import evhh.model.ControllableComponent;
import evhh.model.GameObject;

import java.awt.event.KeyEvent;
import java.util.HashMap;

/***********************************************************************************************************************
 * @project: MainProject
 * @package: PACKAGE_NAME
 * ---------------------------------------------------------------------------------------------------------------------
 * @authors: Hamed Haghjo & Elias Vahlberg
 * @date: 2021-05-27
 * @time: 19:15
 **********************************************************************************************************************/
public class TestControllableComponent extends ControllableComponent
{
    private int keyCode;
    transient KeyboardInput keyboardInput;
    public boolean hasBeenPressed = false;
    public int numberOfTimesPressed = 0;


    /**
     * @param parent Parent GameObject passed to GameComponent default constructor
     * @param uIM    The UserInputManager where KeyboardInput/MouseInput is stored
     */
    public TestControllableComponent(GameObject parent, UserInputManager uIM, int keyCode)
    {
        super(parent, uIM);
        this.uIM = uIM;
        this.keyCode = keyCode;
        RunnableArg<KeyEvent> keyInputEvent = keyEvent -> pressed(keyEvent.getKeyCode());
        HashMap<Integer, Integer> map = new HashMap<>();

        map.put(keyCode, KeyEvent.KEY_PRESSED);
        keyboardInput = new KeyboardInput(keyInputEvent, map);
        uIM.addKeyInput(keyboardInput);
    }

    @Override
    public void onUIMRefresh(UserInputManager uIM)
    {
        this.uIM = uIM;
        RunnableArg<KeyEvent> keyInputEvent = keyEvent -> pressed(keyEvent.getKeyCode());
        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(keyCode, KeyEvent.KEY_PRESSED);
        keyboardInput = new KeyboardInput(keyInputEvent, map);
        uIM.addKeyInput(keyboardInput);
    }

    public void pressed(int key)
    {
        hasBeenPressed = true;
        numberOfTimesPressed++;
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
