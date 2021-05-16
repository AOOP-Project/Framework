package evhh.model.mapeditor;

import evhh.model.Grid;
import evhh.model.ObjectPrefab;

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
    private static final int REPAINT_REPEAT_DELAY = 20;
    private MapEditor mapEditor;
    private int cellSize,gridWidth,gridHeight;
    private Grid workingGrid;
    private LinkedList<SimpleEntry<BufferedImage,Point>> addedPrefabTextures;
    volatile private boolean mouseDown = false;

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
        mapEditor.setAddedPrefabTextures(addedPrefabTextures);

    }

    public Grid getWorkingGrid()
    {
        return workingGrid;
    }

    public void setWorkingGrid(Grid workingGrid)
    {
        this.workingGrid = workingGrid;
    }

    public void loadNewGrid()
    {
        addedPrefabTextures = new LinkedList<>();
        workingGrid = mapEditor.getWorkingGrid();
        Stream.concat(workingGrid.getStaticObjects().stream(),workingGrid.getDynamicObjects().stream()).forEach(g-> {
            Optional<ObjectPrefab> prefab = Arrays.stream(mapEditor.getAvailablePrefabs()).filter(p->g.getCreator().equals(p)).findFirst();
            prefab.ifPresent(objectPrefab -> addedPrefabTextures.add(new SimpleEntry<>(objectPrefab.getSprite().getTexture(), new Point(g.getX()*cellSize, g.getY()*cellSize))));
        });
        mapEditor.setAddedPrefabTextures(addedPrefabTextures);



    }
    @Override
    protected void paintComponent(Graphics g)
    {
        addedPrefabTextures = mapEditor.getAddedPrefabTextures();
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

    }

    @Override
    public void mouseClicked(MouseEvent e)
    {

    }

    @Override
    public void mousePressed(MouseEvent e)
    {

        if(e.getButton() == MouseEvent.BUTTON1)
        {
            mouseDown = true;
            Thread duringMouseDown = new Thread(() ->
            {
                try
                {
                    mouseHeldDown();
                } catch (InterruptedException interruptedException)
                {
                    mouseDown = false;
                }
            });
            duringMouseDown.start();
        }



    }
    public void mouseHeldDown() throws InterruptedException
    {
        try
        {
            do{
                Point p = MouseInfo.getPointerInfo().getLocation();
                SwingUtilities.convertPointFromScreen(p,this);
                int x = p.x/cellSize;
                int y = (gridHeight-1)-p.y/cellSize;
                if (mapEditor.getSelectedPrefab() != null)
                {
                    if(workingGrid.isEmpty(x,y))
                    {
                        workingGrid.addGameObject(mapEditor.getSelectedPrefab().getInstance(x,y),x,y);
                        addedPrefabTextures.add(new SimpleEntry<>(mapEditor.getSelectedPrefab().getSprite().getTexture(), new Point(cellSize * x, cellSize * y)));
                        repaint();
                    }

                }
                else
                {
                    if(!workingGrid.isEmpty(x,y))
                    {
                        for (int i = 0; i < addedPrefabTextures.size(); i++)
                        {
                            int x2 = addedPrefabTextures.get(i).getValue().x/cellSize;
                            int y2 = addedPrefabTextures.get(i).getValue().y/cellSize;
                            if (x==x2&&y==y2)
                            {
                                addedPrefabTextures.remove(i);
                            }
                        }
                        workingGrid.removeGameObject(x,y);
                    }
                    repaint();
                }
                Thread.sleep(REPAINT_REPEAT_DELAY);
            }while (mouseDown);
        } catch (Exception ignored)
        {

        }

    }

    @Override
    public void mouseReleased(MouseEvent e)
    {
        if(e.getButton() == MouseEvent.BUTTON1)
            mouseDown = false;
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
