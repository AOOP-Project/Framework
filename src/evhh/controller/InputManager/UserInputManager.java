package evhh.controller.InputManager;

import evhh.model.GameInstance;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

/***********************************************************************************************************************
 * @project: MainProject
 * @package: evhh.controller.InputManager
 * ---------------------------------------------------------------------------------------------------------------------
 * @authors: Hamed Haghjo & Elias Vahlberg
 * @date: 2021-05-12
 * @time: 12:30
 **********************************************************************************************************************/
public class UserInputManager implements Serializable
{
    private ArrayList<MouseInput> mouseInputs;
    private ArrayList<KeyboardInput> keyboardInputs;

    public UserInputManager(GameInstance gameInstance)
    {
        mouseInputs = new ArrayList<>();
        keyboardInputs = new ArrayList<>();
    }


    public void addInputs(KeyboardInput keyboardInput, MouseInput mouseInput)
    {
        if(!keyboardInputs.contains(keyboardInput))
            keyboardInputs.add(keyboardInput);
        if(!mouseInputs.contains(mouseInput))
            mouseInputs.add(mouseInput);
    }

    public void addKeyInput(KeyboardInput keyboardInput)
    {
        if(!keyboardInputs.contains(keyboardInput))
            keyboardInputs.add(keyboardInput);
    }

    public void addMouseInput(MouseInput mouseInput)
    {
        if(!mouseInputs.contains(mouseInput))
            mouseInputs.add(mouseInput);
    }

    public ArrayList<KeyboardInput> getKeyboardInputs()
    {
        return keyboardInputs;
    }

    public ArrayList<MouseInput> getMouseInputs()
    {
        return mouseInputs;
    }
}
