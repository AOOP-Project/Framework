package functional.testingprefabs;

import evhh.annotations.UniqueSerializableField;
import evhh.controller.InputManager.UserInputManager;
import evhh.model.GameObject;
import evhh.model.Grid;
import evhh.model.ObjectPrefab;
import functional.testingcomponents.TestPlayerComponent;

import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.Objects;

/***********************************************************************************************************************
 * @project: MainProject
 * @package: evhh.model.prefabs
 * ---------------------------------------------------------------------------------------------------------------------
 * @authors: Hamed Haghjo & Elias Vahlberg
 * @date: 2021-05-13
 * @time: 15:38
 **********************************************************************************************************************/
public class TestPlayerPrefab extends ObjectPrefab
{

    private final int DEFAULT_UP_KEY    = KeyEvent.VK_W;
    private final int DEFAULT_DOWN_KEY  = KeyEvent.VK_S;
    private final int DEFAULT_RIGHT_KEY = KeyEvent.VK_D;
    private final int DEFAULT_LEFT_KEY  = KeyEvent.VK_A;

    transient private UserInputManager uIM;
    @UniqueSerializableField
    private int upKey                   = DEFAULT_UP_KEY;
    @UniqueSerializableField
    private int downKey                 = DEFAULT_DOWN_KEY;
    @UniqueSerializableField
    private int rightKey                = DEFAULT_RIGHT_KEY;
    @UniqueSerializableField
    private int leftKey                 = DEFAULT_LEFT_KEY;

    public TestPlayerPrefab(BufferedImage playerIcon, String textureRef, int id, UserInputManager uIM)
    {
        super(playerIcon,textureRef,false,id);
        this.uIM = uIM;
    }

    @Override
    public GameObject getInstance(Grid grid, int x, int y)
    {
        GameObject instance = super.getInstance(grid,x,y);
        instance.addComponent(new TestPlayerComponent(instance,uIM ,upKey ,downKey, rightKey, leftKey));
        instance.setCreator(this);
        return instance;
    }


    public void setUpKey(int upKey)
    {
        this.upKey = upKey;
    }

    public void setDownKey(int downKey)
    {
        this.downKey = downKey;
    }

    public void setRightKey(int rightKey)
    {
        this.rightKey = rightKey;
    }

    public void setLeftKey(int leftKey)
    {
        this.leftKey = leftKey;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TestPlayerPrefab that = (TestPlayerPrefab) o;
        return upKey == that.upKey && downKey == that.downKey && rightKey == that.rightKey && leftKey == that.leftKey && textureRef.equals(that.textureRef);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(textureRef, upKey, downKey, rightKey, leftKey);
    }
}
