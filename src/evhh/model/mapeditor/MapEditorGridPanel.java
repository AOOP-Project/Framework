package evhh.model.mapeditor;

import evhh.model.GameObject;
import evhh.model.Grid;
import evhh.model.ObjectPrefab;
import evhh.model.gamecomponents.Sprite;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.AbstractMap.SimpleEntry;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Optional;
import java.util.stream.Stream;

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
    LinkedList<SimpleEntry<BufferedImage,Point>> addedPrefabTextures;

    public MapEditorGridPanel(MapEditor mapEditor, int cellSize)
    {
        super();
        this.mapEditor = mapEditor;
        this.cellSize = cellSize;
        this.workingGrid = mapEditor.getWorkingGrid();
        this.gridWidth = workingGrid.getGridWidth();
        this.gridHeight = workingGrid.getGridHeight();
        addedPrefabTextures = new LinkedList<>();
        setPreferredSize(new Dimension(gridWidth*cellSize,gridHeight*cellSize));
        addMouseListener(this);
        setBorder(new LineBorder(Color.BLACK, 1));
    }

    public void loadNewGrid()
    {
        addedPrefabTextures = new LinkedList<>();
        System.out.println(workingGrid.getStaticObjects().size());
        System.out.println(workingGrid.getDynamicObjects().size());
        Stream.concat(workingGrid.getStaticObjects().stream(),workingGrid.getDynamicObjects().stream()).forEach(g-> {
            Optional<ObjectPrefab> prefab = Arrays.stream(mapEditor.getAvailablePrefabs()).filter(p->g.getCreator().equals(p)).findFirst();
            prefab.ifPresent(objectPrefab -> addedPrefabTextures.add(new SimpleEntry<>(objectPrefab.getSprite().getTexture(), new Point(g.getX(), g.getY()))));
            if(prefab.isPresent())
                System.out.println("FOUND: " + prefab.get().getClass().getSimpleName());
        });
        System.out.println(addedPrefabTextures.size());
        repaint();
    }
    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        for (int i = 0; i < gridWidth; i++)
        {
            g2d.drawLine(i*cellSize,0,i*cellSize,getHeight());
        }
        for (int i = 0; i < gridHeight; i++)
        {
         g2d.drawLine(0,i*cellSize,getWidth(),i*cellSize);
        }

        addedPrefabTextures.forEach(sE->{g2d.drawImage(sE.getKey(), sE.getValue().x, (gridHeight-1)*cellSize-sE.getValue().y,this);});
        System.out.println("Repaint");

    }

    @Override
    public void mouseClicked(MouseEvent e)
    {
        if (e.getSource()==this && mapEditor.getSelectedPrefab() != null)
        {
            int x = e.getX()/cellSize;
            int y = (gridHeight-1)-e.getY()/cellSize;
            if(workingGrid.isEmpty(x,y))
            {
                workingGrid.addGameObject(mapEditor.getSelectedPrefab().getInstance(x,y),x,y);
                addedPrefabTextures.add(new SimpleEntry<>(mapEditor.getSelectedPrefab().getSprite().getTexture(), new Point(cellSize * x, cellSize * y)));
                repaint();
            }
            System.out.println(x + ", " + y);

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
