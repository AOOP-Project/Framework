package evhh.model;

import evhh.model.gamecomponents.Sprite;

import java.awt.*;
import java.io.Serializable;
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
public class GameObject implements Serializable
{
    private boolean staticObj;
    private int x, y;
    private ArrayList<GameComponent> componentList;
    private Grid grid;
    private long id = 0;
    private ObjectPrefab creator;
    private Sprite sprite;

    public GameObject(Grid grid, boolean staticObj, int x, int y, ArrayList<GameComponent> componentList)
    {
        this.staticObj = staticObj;
        this.x = x;
        this.y = y;
        this.componentList = componentList;
        this.grid = grid;
    }

    public GameObject(Grid grid, boolean staticObj, int x, int y)
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
        if(component.getClass()==Sprite.class)
            sprite = (Sprite) component;
        componentList.add(component);
    }


    public GameComponent getComponent(Class<? extends GameComponent> componentClass)
    {

        Optional<GameComponent> obj = componentList.stream().filter(c -> c.getClass() == componentClass).findFirst();
        return obj.orElse(null);
    }

    public boolean hasComponent(Class<? extends GameComponent> componentClass)
    {
        return componentList.stream().anyMatch(c -> c.getClass() == componentClass);
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
        int x1 = this.x;
        int y1 = this.y;
        this.x = x;
        this.y = y;
        if (grid.get(x, y) != this)
            grid.moveGameObject(x1, y1, x, y);


    }

    public Grid getGrid()
    {
        return grid;
    }

    public void setGrid(Grid grid)
    {
        this.grid = grid;
    }

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public void onStart()
    {
        componentList.forEach(GameComponent::onStart);
    }

    public void update()
    {
        componentList.forEach(GameComponent::update);
    }

    public void onExit()
    {
        componentList.forEach(GameComponent::onExit);
    }

    public ObjectPrefab getCreator()
    {
        return creator;
    }

    public void setCreator(ObjectPrefab creator)
    {
        this.creator = creator;
    }

    public ArrayList<GameComponent> getComponentList()
    {
        return componentList;
    }

    public Sprite getSprite()
    {
        return sprite;
    }
}
