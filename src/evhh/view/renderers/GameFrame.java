package evhh.view.renderers;

import javax.swing.*;

/***********************************************************************************************************************
 * @project: MainProject
 * @package: evhh.view.renderers
 * ---------------------------------------------------------------------------------------------------------------------
 * @authors: Hamed Haghjo & Elias Vahlberg
 * @date: 2021-05-12
 * @time: 14:08
 **********************************************************************************************************************/
public class GameFrame extends JFrame
{

    public GameFrame(int w, int h, String gameTitle)
    {
        super();
        this.setTitle(gameTitle);
        this.setSize(w,h);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
