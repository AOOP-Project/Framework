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
    private ArrayList<Sprite> sprites;
    private int gridWidth;
    private int gridHeight;
    private int cellSize;

    public GridRenderer(int gridWidth, int gridHeight, int cellSize)
    {
        this.gridWidth =gridWidth;
        this.gridHeight =gridHeight;
        this.cellSize =cellSize;
        gridPanel = new GridPanel(gridWidth,gridHeight,cellSize,this);
        sprites = new ArrayList<>();
    }

    public void renderFrame()
    {
        gridPanel.repaint();
    }
    public ArrayList<Sprite> getSprites()
    {
        return sprites;
    }
    public void addSprite(Sprite sprite)
    {
        sprites.add(sprite);
    }
    public void removeSprite(Sprite sprite)
    {
        sprites.remove(sprite);
    }

    public GridPanel getGridPanel()
    {
        return gridPanel;
    }
}
