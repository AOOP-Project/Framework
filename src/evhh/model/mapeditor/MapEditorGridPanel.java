package evhh.model.mapeditor;

import evhh.model.Grid;
import evhh.model.gamecomponents.Sprite;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.AbstractMap.SimpleEntry;
import java.util.LinkedList;

/***********************************************************************************************************************
 * @project: MainProject
 * @package: evhh.model.mapeditor
 * ---------------------------------------------------------------------------------------------------------------------
 * @authors: Hamed Haghjo & Elias Vahlberg
 * @date: 2021-05-15
 * @time: 10:53
 **********************************************************************************************************************/
public class MapEditorGridPanel extends JPanel implements MouseListener
{
    private MapEditor mapEditor;
    private int cellSize,gridWidth,gridHeight;
    private Grid workingGrid;
    LinkedList<SimpleEntry<BufferedImage,Point>> graphics;

    public MapEditorGridPanel(MapEditor mapEditor, int cellSize, int gridWidth, int gridHeight, Grid workingGrid)
    {
        super();
        this.mapEditor = mapEditor;
        this.cellSize = cellSize;
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
        this.workingGrid = workingGrid;
        setPreferredSize(new Dimension(gridWidth*cellSize,gridHeight*cellSize));
        addMouseListener(this);
    }


    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

    }

    @Override
    public void mouseClicked(MouseEvent e)
    {
        if (e.getSource()==this && mapEditor.getSelectedPrefab() != null)
        {
            int x2 = e.getX();
            int y2 = e.getY();
            int x1 = getX();
            int y1 = getY();
            int x3 = (x2 - x1) / cellSize;
            int y3 = (y2 - y1) / cellSize;
            Sprite sprite = mapEditor.selectedPrefab.getInstance(0, 0).getComponentList().stream().filter(c -> c.getClass() == Sprite.class).map(c -> (Sprite) c).findFirst().get();
            JLabel label = new JLabel(new ImageIcon(sprite.getTexture()));
            label.setLocation(x3 * cellSize, y3 * cellSize);

        }
    }

    @Override
    public void mousePressed(MouseEvent e)
    {

    }

    @Override
    public void mouseReleased(MouseEvent e)
    {

    }

    @Override
    public void mouseEntered(MouseEvent e)
    {

    }

    @Override
    public void mouseExited(MouseEvent e)
    {

    }
}
