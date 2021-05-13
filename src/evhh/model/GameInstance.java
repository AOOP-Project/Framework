package evhh.model;

import evhh.controller.InputManager.UserInputManager;
import evhh.model.gamecomponents.Sprite;
import evhh.view.renderers.FrameRenderer;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Objects;
import java.util.stream.Stream;

/***********************************************************************************************************************
 * @project: MainProject
 * @package: evhh.model
 * ---------------------------------------------------------------------------------------------------------------------
 * @authors: Hamed Haghjo & Elias Vahlberg
 * @date: 2021-05-12
 * @time: 12:26
 **********************************************************************************************************************/
public class GameInstance
{

    //region Constants
    private final long GAMEOBJECT_ID_START = 1000;
    private final long GAMEOBJECT_ID_INCREMENT = 10;
    private final String[] DEFAULT_ALLOWED_TEXTURE_FILE_EXTENSIONS = {"JPEG", "PNG", "BMP", "WEBMP", "GIF"};
    //endregion


    //region Fields
    private Grid mainGrid;
    private UserInputManager userInputManager;
    private FrameRenderer frameRenderer;
    private Timer renderTimer;
    private String gameInstanceName;
    private long gObjectId = GAMEOBJECT_ID_START;
    private String[] allowedTextureFileExtension = DEFAULT_ALLOWED_TEXTURE_FILE_EXTENSIONS;
    private HashMap<String, BufferedImage> textureMap;
    //endregion

    public GameInstance(String gameInstanceName)
    {
        this.gameInstanceName = gameInstanceName;
        textureMap = new HashMap<>();
    }

    //region Assets


    public void setAllowedTextureFileExtension(String[] allowedTextureFileExtension)
    {
        this.allowedTextureFileExtension = allowedTextureFileExtension;
    }

    public void loadTextureAssets()
    {

    }

    public void loadTextureAssets(String path)
    {

    }
    //endregion

    //region Grid
    public void setMainGrid(Grid mainGrid)
    {
        this.mainGrid = mainGrid;
    }

    public void addGameObject(GameObject gameObject, int x, int y)
    {
        mainGrid.addGameObject(gameObject, x, y).setId(gObjectId);
        gObjectId += GAMEOBJECT_ID_INCREMENT;
    }

    public void addGameObject(int x, int y, boolean isStatic)
    {
        mainGrid.addGameObject(x, y, isStatic).setId(gObjectId);
        gObjectId += GAMEOBJECT_ID_INCREMENT;
    }

    public GameObject getGameObject(int x, int y)
    {
        return mainGrid.get(x, y);
    }
    //endregion

    //region Renderer
    public void setFrameRenderer(FrameRenderer frameRenderer)
    {
        this.frameRenderer = frameRenderer;
    }

    public void refreshSpritesInRenderer()
    {
        Stream<GameObject> stream1 = mainGrid.getStaticObjects().stream();
        Stream<GameObject> stream2 =mainGrid.getDynamicObjects().stream();
        Stream.concat(stream1,stream2).
                map(g->g.getComponent(Sprite.class)).
                        filter(Objects::nonNull).map(c->(Sprite)c).
                            forEach(c->frameRenderer.getGridRenderer().addSprite(c));
    }

    public void addSprite(Sprite sprite)
    {
        frameRenderer.getGridRenderer().addSprite(sprite);
    }

    public void startRenderer()
    {
        frameRenderer.start();
    }

    public void stopRenderer()
    {
        frameRenderer.stop();
    }

    public void addRendererTimer(Timer timer)
    {
        renderTimer = timer;
    }
    //endregion



}
