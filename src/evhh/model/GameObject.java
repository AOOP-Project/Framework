package evhh.model;

import java.awt.*;
import java.util.ArrayList;
import java.util.Optional;

/***********************************************************************************************************************
 * @project: MainProject
 * @package: evhh.model
 * ---------------------------------------------------------------------------------------------------------------------
 * @authors: Hamed Haghjo & Elias Vahlberg
 * @date: 2021-05-12
 * @time: 12:25
 **********************************************************************************************************************/
public class GameObject
{
    private boolean staticObj;
    private int x, y;
    private ArrayList<GameComponent> componentList;
    private Grid grid;

    public GameObject(Grid grid,boolean staticObj, int x, int y, ArrayList<GameComponent> componentList)
    {
        this.staticObj = staticObj;
        this.x = x;
        this.y = y;
        this.componentList = componentList;
        this.grid = grid;
    }

    public GameObject(Grid grid,boolean staticObj, int x, int y)
    {
        this.staticObj = staticObj;
        this.x = x;
        this.y = y;
        componentList = new ArrayList<>();
        this.grid = grid;
    }

    public boolean isStatic()
    {
        return staticObj;
    }

    public void addComponent(GameComponent component)
    {
        componentList.add(component);
    }


    public GameComponent getComponent(Class componentClass)
    {
        Optional<GameComponent> obj = componentList.stream().filter(c-> c.getClass()==componentClass).findFirst();
        return obj.orElse(null);

    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }
    public void setPosition(int x, int y)
    {
        grid.moveGameObject(this.x,this.y,x,y);
        this.x = x;
        this.y = y;
    }

    public Grid getGrid()
    {
        return grid;
    }

    public void setGrid(Grid grid)
    {
        this.grid = grid;
    }
}
