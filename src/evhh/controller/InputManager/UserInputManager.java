package evhh.controller.InputManager;

import evhh.model.GameInstance;
import evhh.model.Grid;

import java.io.*;
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
        if (!keyboardInputs.contains(keyboardInput))
            keyboardInputs.add(keyboardInput);
        if (!mouseInputs.contains(mouseInput))
            mouseInputs.add(mouseInput);
    }

    public void addKeyInput(KeyboardInput keyboardInput)
    {
        if (!keyboardInputs.contains(keyboardInput))
        {
            keyboardInputs.add(keyboardInput);
        }
    }

    public void addMouseInput(MouseInput mouseInput)
    {
        if (!mouseInputs.contains(mouseInput))
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

    @Deprecated
    public static void serializeUIM(UserInputManager userInputManager, String path)
    {

        try
        {
            FileOutputStream fileOut = new FileOutputStream(path);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(userInputManager);
            out.close();
            fileOut.close();
        } catch (IOException e)
        {
            e.printStackTrace();
        }

    }
    @Deprecated
    public static UserInputManager deserializeUIM(String path)
    {
        UserInputManager userInputManager = null;
        try (FileInputStream fileIn = new FileInputStream(path); ObjectInputStream in = new ObjectInputStream(fileIn))
        {
            userInputManager = (UserInputManager) in.readObject();
        } catch (IOException | ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        return userInputManager;
    }
}
