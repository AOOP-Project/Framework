package evhh.model;

import evhh.controller.InputManager.UserInputManager;
import evhh.view.renderers.FrameRenderer;

import javax.swing.*;

/***********************************************************************************************************************
 * @project: MainProject
 * @package: evhh.model
 * ---------------------------------------------------------------------------------------------------------------------
 * @authors: Hamed Haghjo & Elias Vahlberg
 * @date: 2021-05-12
 * @time: 12:26
 **********************************************************************************************************************/
public class GameInstance
{
    private Grid mainGrid;
    private UserInputManager userInputManager;
    private FrameRenderer frameRenderer;
    private Timer renderTimer;

    public void startRenderer()
    {
        frameRenderer.start();
    }

    public void stopRenderer()
    {
        frameRenderer.stop();
    }

}
