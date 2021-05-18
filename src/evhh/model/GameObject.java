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
    //region Fields
    private boolean staticObj;
    private boolean controllable;
    private int x, y;
    private ArrayList<GameComponent> componentList;
    private Grid grid;
    private long id = 0;
    private ObjectPrefab creator;
    private Sprite sprite;
    //endregion

    public GameObject(Grid grid, boolean staticObj, int x, int y, ArrayList<GameComponent> componentList)
    {
        this.staticObj = staticObj;
        this.x = x;
        this.y = y;
        this.componentList = componentList;
        controllable = componentList.stream().anyMatch(c -> c instanceof ControllableComponent);
        this.grid = grid;
        if (controllable)
            grid.addControllableObject(this);
    }

    public GameObject(Grid grid, boolean staticObj, int x, int y)
    {
        this.staticObj = staticObj;
        this.x = x;
        this.y = y;
        componentList = new ArrayList<>();
        this.grid = grid;
        controllable = false;
    }

    //region Start/Update/Exit
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
    //endregion

    //region Field getters
    public boolean isStatic()
    {
        return staticObj;
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public ObjectPrefab getCreator()
    {
        return creator;
    }

    public Sprite getSprite()
    {
        return sprite;
    }

    public long getId()
    {
        return id;
    }

    public Grid getGrid()
    {
        return grid;
    }
    //endregion

    //region Components
    public ArrayList<GameComponent> getComponentList()
    {
        return componentList;
    }

    public void addComponent(GameComponent component)
    {
        assert component != null;
        if (component.getClass() == Sprite.class)
            sprite = (Sprite) component;
        if (component instanceof ControllableComponent)
        {
            grid.addControllableObject(this);
            controllable = true;
        }
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
    //endregion

    //region Field setters
    public void setPosition(int x, int y)
    {
        int x1 = this.x;
        int y1 = this.y;
        this.x = x;
        this.y = y;
        if (grid.get(x, y) != this)
            grid.moveGameObject(x1, y1, x, y);


    }

    public void setGrid(Grid grid)
    {
        this.grid = grid;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public void setCreator(ObjectPrefab creator)
    {
        this.creator = creator;
    }
    //endregion

    //region Controller
    public boolean isControllable()
    {
        return controllable;
    }

    public ControllableComponent getController()
    {
        assert controllable : "This GameObject:{ " + this.toString() + " } is not controllable";
        Optional<ControllableComponent> controller = componentList.stream().filter(c -> c instanceof ControllableComponent).map(c -> (ControllableComponent) c).findFirst();
        assert controller.isPresent() : "Controllable object is tagged controllable but has no controller";
        return controller.get();
    }
    //endregion
}
