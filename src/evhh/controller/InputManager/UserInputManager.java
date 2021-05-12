package evhh.controller.InputManager;

import evhh.model.GameInstance;

import java.io.File;
import java.util.ArrayList;

/***********************************************************************************************************************
 * @project: MainProject
 * @package: evhh.controller.InputManager
 * ---------------------------------------------------------------------------------------------------------------------
 * @authors: Hamed Haghjo & Elias Vahlberg
 * @date: 2021-05-12
 * @time: 12:30
 **********************************************************************************************************************/
public class UserInputManager
{
    ArrayList<MouseInput> mouseInputs;
    ArrayList<KeyboardInput> keyboardInputs;
    GameInstance gameInstance;

    public UserInputManager(GameInstance gameInstance)
    {
        this.gameInstance = gameInstance;
        mouseInputs = new ArrayList<>();
        keyboardInputs = new ArrayList<>();
    }


    public void addInputs(KeyboardInput keyboardInput, MouseInput mouseInput)
    {
        mouseInputs.add(mouseInput);
        keyboardInputs.add(keyboardInput);
    }
}
