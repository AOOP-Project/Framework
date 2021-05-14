package evhh.model.mapeditor;

import evhh.model.Grid;
import evhh.model.ObjectPrefab;

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
    Grid workingGrid;
    ObjectPrefab[] availablePrefabs;
    ObjectPrefab selectedPrefab;
    MapEditorFrame frame;
    int cellSize;

    public MapEditor(Grid workingGrid,int cellSize, ObjectPrefab[] availablePrefabs)
    {
        this.workingGrid = workingGrid;
        this.availablePrefabs = availablePrefabs;
        this.cellSize = cellSize;
        frame = new MapEditorFrame(this,workingGrid,cellSize,availablePrefabs);
        frame.setVisible(true);

    }

    public ObjectPrefab getSelectedPrefab()
    {
        return selectedPrefab;
    }

    public void setSelectedPrefab(ObjectPrefab selectedPrefab)
    {
        this.selectedPrefab = selectedPrefab;
    }
}
