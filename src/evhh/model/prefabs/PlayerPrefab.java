package evhh.model.prefabs;

import evhh.annotations.UniqueSerializableField;
import evhh.controller.InputManager.UserInputManager;
import evhh.model.GameObject;
import evhh.model.Grid;
import evhh.model.ObjectPrefab;
import evhh.model.gamecomponents.PlayerComponent;
import evhh.model.gamecomponents.Sprite;

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
public class PlayerPrefab implements ObjectPrefab
{
    private Grid grid;
    private GameObject playerObject;
    transient  private BufferedImage playerIcon;
    private UserInputManager uIM;
    @UniqueSerializableField
    private String textureRef;

    private final int DEFAULT_UP_KEY    = KeyEvent.VK_W;
    private final int DEFAULT_DOWN_KEY  = KeyEvent.VK_S;
    private final int DEFAULT_RIGHT_KEY = KeyEvent.VK_D;
    private final int DEFAULT_LEFT_KEY  = KeyEvent.VK_A;

    @UniqueSerializableField
    private int upKey                   = DEFAULT_UP_KEY;
    @UniqueSerializableField
    private int downKey                 = DEFAULT_DOWN_KEY;
    @UniqueSerializableField
    private int rightKey                = DEFAULT_RIGHT_KEY;
    @UniqueSerializableField
    private int leftKey                 = DEFAULT_LEFT_KEY;

    public PlayerPrefab(Grid grid, BufferedImage playerIcon,String textureRef, UserInputManager uIM)
    {
        this.textureRef = textureRef;
        this.grid = grid;
        this.playerIcon = playerIcon;
        this.uIM = uIM;
    }

    @Override
    public GameObject getInstance(int x, int y)
    {
        playerObject = new GameObject(grid, false, x,y);
        playerObject.addComponent(new Sprite(playerObject, playerIcon,textureRef));
        playerObject.addComponent(new PlayerComponent(playerObject,uIM ,upKey ,downKey, rightKey, leftKey));
        playerObject.setCreator(this);
        return playerObject;
    }

    @Override
    public Sprite getSprite()
    {
        return new Sprite(null,playerIcon,textureRef);
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
        PlayerPrefab that = (PlayerPrefab) o;
        return upKey == that.upKey && downKey == that.downKey && rightKey == that.rightKey && leftKey == that.leftKey && textureRef.equals(that.textureRef);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(textureRef, upKey, downKey, rightKey, leftKey);
    }
}
