package evhh.model.mapeditor;

import evhh.model.Grid;
import evhh.model.ObjectPrefab;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.AbstractMap;
import java.util.LinkedList;

/***********************************************************************************************************************
 * @project: MainProject
 * @package: evhh.model.mapeditor
 * ---------------------------------------------------------------------------------------------------------------------
 * @authors: Hamed Haghjo & Elias Vahlberg
 * @date: 2021-05-14
 * @time: 22:52
 **********************************************************************************************************************/
public class MapEditor
{
    private Grid workingGrid;
    private ObjectPrefab[] availablePrefabs;
    private ObjectPrefab selectedPrefab;
    private MapEditorFrame frame;
    private int cellSize;
    private LinkedList<AbstractMap.SimpleEntry<BufferedImage, Point>> addedPrefabTextures;
    private String workingGridLoadPath;
    private String workingGridSavePath;


    public MapEditor(Grid workingGrid, int cellSize, ObjectPrefab[] availablePrefabs)
    {
        this.workingGrid = workingGrid;
        this.availablePrefabs = availablePrefabs;
        this.cellSize = cellSize;
        frame = new MapEditorFrame(this, workingGrid, cellSize, availablePrefabs);
        frame.setVisible(true);

    }

    public ObjectPrefab getSelectedPrefab()
    {
        return selectedPrefab;
    }

    public void setSelectedPrefab(ObjectPrefab selectedPrefab)
    {
        this.selectedPrefab = selectedPrefab;
        frame.updateSelectedPrefabPanel();
    }

    public Grid getWorkingGrid()
    {
        return workingGrid;
    }

    public void setWorkingGrid(Grid workingGrid)
    {
        this.workingGrid = workingGrid;
    }

    public ObjectPrefab[] getAvailablePrefabs()
    {
        return availablePrefabs;
    }

    public MapEditorFrame getFrame()
    {
        return frame;
    }

    public LinkedList<AbstractMap.SimpleEntry<BufferedImage, Point>> getAddedPrefabTextures()
    {
        return addedPrefabTextures;
    }

    public void setAddedPrefabTextures(LinkedList<AbstractMap.SimpleEntry<BufferedImage, Point>> addedPrefabTextures)
    {
        this.addedPrefabTextures = addedPrefabTextures;
    }

    public String getWorkingGridLoadPath()
    {
        return workingGridLoadPath;
    }

    public void setWorkingGridLoadPath(String workingGridLoadPath)
    {
        this.workingGridLoadPath = workingGridLoadPath;
    }

    public String getWorkingGridSavePath()
    {
        return workingGridSavePath;
    }

    public void setWorkingGridSavePath(String workingGridSavePath)
    {
        this.workingGridSavePath = workingGridSavePath;
    }
}
