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
public class MapEditorFrame extends JFrame
{
    private JPanel workingGridPanel;
    private PrefabPanel[] prefabPanels;
    int gridWidth, gridHeight, cellSize;
    MapEditor mapEditor;
    ObjectPrefab[] prefabs;

    public MapEditorFrame(MapEditor mapEditor, Grid workingGrid, int cellSize, ObjectPrefab[] prefabs) throws HeadlessException
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
        workingGridPanel.setPreferredSize(new Dimension(gridWidth * cellSize, gridHeight * cellSize));
        workingGridPanel.setBorder(new LineBorder(Color.BLACK, 1));
        add(workingGridPanel);
        prefabPanels = new PrefabPanel[prefabs.length];
        int i = 0;
        for (ObjectPrefab prefab : prefabs)
        {
            Sprite sprite = (Sprite) prefab.getInstance(0, 0).getComponent(Sprite.class);
            prefabPanels[i] = new PrefabPanel(prefab,sprite.getTexture(),new Dimension(cellSize, cellSize),mapEditor);
            add(prefabPanels[i]);
            i++;
        }
        pack();


    }
}
