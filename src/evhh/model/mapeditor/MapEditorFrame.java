package evhh.model.mapeditor;

import evhh.model.Grid;
import evhh.model.ObjectPrefab;
import evhh.model.gamecomponents.Sprite;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/***********************************************************************************************************************
 * @project: MainProject
 * @package: evhh.model.mapeditor
 * ---------------------------------------------------------------------------------------------------------------------
 * @authors: Hamed Haghjo & Elias Vahlberg
 * @date: 2021-05-14
 * @time: 22:52
 **********************************************************************************************************************/
public class MapEditorFrame extends JFrame implements MouseListener
{
    private JPanel workingGridPanel;
    private JPanel[] prefabPanels ;
    int gridWidth,gridHeight,cellSize;
    MapEditor mapEditor;
    ObjectPrefab[] prefabs;

    public MapEditorFrame(MapEditor mapEditor,Grid workingGrid, int cellSize, ObjectPrefab[] prefabs) throws HeadlessException
    {
        super();
        this.mapEditor = mapEditor;
        this.prefabs = prefabs;
        setLayout(new FlowLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.gridWidth = workingGrid.getGridWidth();
        this.gridHeight = workingGrid.getGridHeight();
        this.cellSize = cellSize;
        workingGridPanel = new JPanel();
        workingGridPanel.setPreferredSize(new Dimension(gridWidth*cellSize,gridHeight*cellSize));
        workingGridPanel.setBorder(new LineBorder(Color.BLACK,1));
        add(workingGridPanel);
        prefabPanels = new JPanel[prefabs.length];
        int i = 0;
        for (ObjectPrefab prefab : prefabs)
        {
            Sprite sprite = (Sprite) prefab.getInstance(0,0).getComponent(Sprite.class);
            prefabPanels[i] = new JPanel();
            prefabPanels[i].setPreferredSize(new Dimension(cellSize,cellSize));
            if(sprite!=null)
            {
                prefabPanels[i].add(new JLabel(new ImageIcon(sprite.getTexture())));
                System.out.println(sprite.getTexture().toString());
            }
            add(prefabPanels[i]);
            i++;
        }
        addMouseListener(this);
        pack();


    }

    @Override
    public void mouseClicked(MouseEvent e)
    {
        Object o = e.getSource();
        System.out.println("Mouse Clicked");
        System.out.println(e.getSource());
        System.out.println(e.getComponent());
        for (int i = 0; i < prefabPanels.length; i++)
        {
            if(o.equals(prefabPanels[i]))
            {
                mapEditor.setSelectedPrefab(prefabs[i]);
                System.out.println(prefabs[i].getClass().getName());
                return;
            }

        }
        if(e.getSource().equals(workingGridPanel)&&mapEditor.getSelectedPrefab()!=null)
        {
            int x2 = e.getX();
            int y2 = e.getY();
            int x1 = workingGridPanel.getX();
            int y1 = workingGridPanel.getY();
            int x3 = (x2-x1)/cellSize;
            int y3 = (y2-y1)/cellSize;
            Sprite sprite = mapEditor.selectedPrefab.getInstance(0,0).getComponentList().stream().filter(c->c.getClass()==Sprite.class).map(c-> (Sprite) c).findFirst().get();
            JLabel label = new JLabel(new ImageIcon(sprite.getTexture()));
            label.setLocation(x3*cellSize,y3*cellSize);
            workingGridPanel.add(label);
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
