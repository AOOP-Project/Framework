package evhh.model.prefabs;

import evhh.controller.InputManager.UserInputManager;
import evhh.model.GameObject;
import evhh.model.Grid;
import evhh.model.ObjectPrefab;
import evhh.model.gamecomponents.PlayerComponent;
import evhh.model.gamecomponents.Sprite;

import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

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
    Grid grid;
    GameObject playerObject;
    BufferedImage playerIcon;
    UserInputManager uIM;

    private final int DEFAULT_UP_KEY    = KeyEvent.VK_W;
    private final int DEFAULT_DOWN_KEY  = KeyEvent.VK_S;
    private final int DEFAULT_RIGHT_KEY = KeyEvent.VK_D;
    private final int DEFAULT_LEFT_KEY  = KeyEvent.VK_A;

    private int upKey                   = DEFAULT_UP_KEY;
    private int downKey                 = DEFAULT_DOWN_KEY;
    private int rightKey                = DEFAULT_RIGHT_KEY;
    private int leftKey                 = DEFAULT_LEFT_KEY;

    public PlayerPrefab(Grid grid, BufferedImage playerIcon, UserInputManager uIM)
    {
        this.grid = grid;
        this.playerIcon = playerIcon;
        this.uIM = uIM;
    }

    @Override
    public GameObject getInstance(int x, int y)
    {
        playerObject = new GameObject(grid, false, x,y);
        playerObject.addComponent(new Sprite(playerObject, playerIcon));
        playerObject.addComponent(new PlayerComponent(playerObject,uIM ,upKey ,downKey, rightKey, leftKey));
        playerObject.setCreator(this);
        return playerObject;
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
}
