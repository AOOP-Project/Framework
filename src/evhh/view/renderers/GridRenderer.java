package evhh.view.renderers;

import evhh.model.gamecomponents.Sprite;

import javax.swing.*;
import java.util.ArrayList;

/***********************************************************************************************************************
 * @project: MainProject
 * @package: evhh.view.renderers
 * ---------------------------------------------------------------------------------------------------------------------
 * @authors: Hamed Haghjo & Elias Vahlberg
 * @date: 2021-05-12
 * @time: 14:06
 **********************************************************************************************************************/
public class GridRenderer
{
    private GridPanel gridPanel;
    private Timer timer;
    private ArrayList<Sprite> sprites;

    public ArrayList<Sprite> getSprites()
    {
        return sprites;
    }
}
