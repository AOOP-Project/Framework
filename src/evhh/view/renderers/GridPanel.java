package evhh.view.renderers;

import javax.swing.*;
import java.awt.*;

/***********************************************************************************************************************
 * @project: MainProject
 * @package: evhh.view
 * ---------------------------------------------------------------------------------------------------------------------
 * @authors: Hamed Haghjo & Elias Vahlberg
 * @date: 2021-05-12
 * @time: 11:51
 * (*TABORT*) Här har vi implementerat på sådan sätt att vi baserar grid med vanliga x-y-koordinatsystemet
 **********************************************************************************************************************/
public class GridPanel extends JPanel
{
    private int gridWidth;
    private int gridHeight;
    private int cellSize;
    private GridRenderer gridRenderer;

    public GridPanel(int gridWidth, int gridHeight, int cellSize,GridRenderer gridRenderer)
    {
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
        this.cellSize = cellSize;
        this.gridRenderer = gridRenderer;
        setPreferredSize(new Dimension(gridWidth*cellSize,gridHeight*cellSize));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        gridRenderer.getSprites().forEach(s->
                g2d.drawImage(
                        s.getTexture(),
                        (int) s.getX()*cellSize,
                        (int) gridHeight*cellSize-((s.getY()+1)*cellSize),
                        this
                )
        );

        Toolkit.getDefaultToolkit().sync();
    }
}