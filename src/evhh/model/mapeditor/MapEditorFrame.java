package evhh.model.mapeditor;

import evhh.common.assetloading.AssetLoader;
import evhh.model.Grid;
import evhh.model.ObjectPrefab;
import evhh.model.gamecomponents.Sprite;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.LinkedList;
import java.util.stream.IntStream;

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
    private MapEditorGridPanel workingGridPanel;
    private PrefabPanel[] prefabPanels;
    private JPanel prefabContainerPanel;
    private JPanel selectedPrefabPanel;
    private JLabel selectedPrefabLabel;
    private JLabel selectedPrefabIcon;
    private JPanel saveLoadPanel;
    private JButton saveButton;
    private JButton loadButton;
    private String eraserPath = System.getProperty("user.dir") +"/Assets/Images/eraser.png";
    BufferedImage eraser;

    private int gridWidth, gridHeight, cellSize;
    private MapEditor mapEditor;
    private ObjectPrefab[] prefabs;
    private Grid workingGrid;

    public MapEditorFrame(MapEditor mapEditor, Grid workingGrid, int cellSize, ObjectPrefab[] prefabs) throws HeadlessException
    {
        super();
        this.workingGrid = workingGrid;
        this.mapEditor = mapEditor;
        this.prefabs = prefabs;
        setLayout(new FlowLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.gridWidth = workingGrid.getGridWidth();
        this.gridHeight = workingGrid.getGridHeight();
        this.cellSize = cellSize;
        createSelectedPrefabPanel();
        workingGridPanel = new MapEditorGridPanel(mapEditor,cellSize);
        add(workingGridPanel,FlowLayout.CENTER);
        createAvailablePrefabsPanel();
        createSaveLoadButtons();

        pack();


    }
    public void createSelectedPrefabPanel()
    {
        selectedPrefabPanel = new JPanel();
        selectedPrefabPanel.setLayout(new BoxLayout(selectedPrefabPanel, BoxLayout.Y_AXIS));

        selectedPrefabLabel = new JLabel("NONE",SwingConstants.LEFT);
        selectedPrefabIcon= new JLabel(new ImageIcon());
        JLabel label = new JLabel("<html>Selected<br/>ObjectPrefab</html>",SwingConstants.LEFT);

        selectedPrefabLabel.setFont(new Font("Courier New", Font.BOLD, 18));
        selectedPrefabLabel.setForeground(new Color(23, 103, 138));

        label.setFont(new Font("Courier New", Font.BOLD, 18));
        label.setForeground(new Color(23, 103, 138));

        selectedPrefabPanel.add(label);
        selectedPrefabPanel.add(selectedPrefabLabel);
        selectedPrefabPanel.add(selectedPrefabIcon);

        add(selectedPrefabPanel,FlowLayout.LEFT);
    }
    public void createAvailablePrefabsPanel()
    {
        GridLayout layout = new GridLayout(gridHeight/2,2*(prefabs.length+1)/gridHeight+1,1,cellSize/2);
        prefabContainerPanel = new JPanel(layout);
        prefabContainerPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        prefabPanels = new PrefabPanel[prefabs.length];
        try
        {
            eraser = ImageIO.read(new File(eraserPath));
        }
        catch (IOException e)
        {
            System.err.println("Eraser picture not found :< ");
            eraser = new BufferedImage(cellSize,cellSize,BufferedImage.TYPE_INT_RGB);
            IntStream.range(cellSize/4,cellSize*3/4).forEach(x->IntStream.range(cellSize/4,cellSize*3/4).forEach(y->eraser.setRGB(x,y,	255)));
        }
        prefabContainerPanel.add(new PrefabPanel(null,eraser,new Dimension(cellSize, cellSize),mapEditor));
        int i = 0;
        for (ObjectPrefab prefab : prefabs)
        {
            prefabPanels[i] = new PrefabPanel(prefab,prefab.getSprite().getTexture(),new Dimension(cellSize, cellSize),mapEditor);
            prefabContainerPanel.add(prefabPanels[i]);
            i++;
        }
        add(prefabContainerPanel);
    }
    public void updateSelectedPrefabPanel()
    {
        if(mapEditor.getSelectedPrefab()==null)
        {
            selectedPrefabLabel.setText("Eraser");
            ((ImageIcon)selectedPrefabIcon.getIcon()).setImage(eraser);
        }
        else
        {
            selectedPrefabLabel.setText(mapEditor.getSelectedPrefab().getClass().getSimpleName());
            ((ImageIcon) selectedPrefabIcon.getIcon()).setImage(mapEditor.getSelectedPrefab().getSprite().getTexture());
        }
        pack();
        selectedPrefabPanel.repaint();
    }
    public void createSaveLoadButtons()
    {
        saveLoadPanel = new JPanel();
        saveLoadPanel.setLayout(new BoxLayout(saveLoadPanel, BoxLayout.Y_AXIS));
        saveButton = new JButton("Save");
        loadButton = new JButton("Load");
        ActionListener saveListener = e ->
        {
            try
            {
                String path = AssetLoader.getPathToSavedData();
                if(!path.equals(""))
                    Grid.serializeGrid(workingGrid,path);
                mapEditor.setWorkingGridSavePath(path);
            } catch (IOException ioException)
            {
                JOptionPane.showMessageDialog(new JFrame(),
                        "Error occurred during grid save. \n " +
                                "Pleas try again and select a valid file destination.",
                        "Saving Error",
                        JOptionPane.ERROR_MESSAGE);
                ioException.printStackTrace();
            }
        };
        ActionListener loadListener = e ->
        {
            try
            {
                String path = AssetLoader.getPathToSavedData();
                if(!path.equals(""))
                {
                    setWorkingGrid(Grid.deserializeGrid(path));
                    mapEditor.setWorkingGridLoadPath(path);
                    this.workingGrid = mapEditor.getWorkingGrid();
                    workingGridPanel.loadNewGrid();
                }
                repaint();
            } catch (IOException | ClassNotFoundException ioException)
            {
                JOptionPane.showMessageDialog(new JFrame(),
                        "Error occurred during loading of grid. \n" +
                                "Pleas try again and select a valid file path. \n" +
                                "And make sure there is no class version mismatch.",
                        "Loading Error",
                        JOptionPane.ERROR_MESSAGE);
                ioException.printStackTrace();
            }
        };
        saveButton.addActionListener(saveListener);
        loadButton.addActionListener(loadListener);
        saveLoadPanel.add(saveButton);
        saveLoadPanel.add(loadButton);
        add(saveLoadPanel);


    }

    public void setWorkingGrid(Grid workingGrid)
    {
        this.workingGrid = workingGrid;
        workingGridPanel.setWorkingGrid(workingGrid);
        mapEditor.setWorkingGrid(workingGrid);
        this.gridWidth = workingGrid.getGridWidth();
        this.gridHeight = workingGrid.getGridHeight();
        workingGridPanel = new MapEditorGridPanel(mapEditor,cellSize);
        validate();
        repaint();
    }


}
