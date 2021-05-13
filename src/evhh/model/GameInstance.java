package evhh.model;

import evhh.common.assetloading.AssetLoader;
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
    private HashMap<String, BufferedImage> textures;
    //endregion

    public GameInstance(String gameInstanceName)
    {
        this.gameInstanceName = gameInstanceName;
        textures = new HashMap<>();
    }

    public String getGameInstanceName()
    {
        return gameInstanceName;
    }

    //region Assets

    public void setAllowedTextureFileExtension(String[] allowedTextureFileExtension)
    {
        assert allowedTextureFileExtension != null;
        this.allowedTextureFileExtension = allowedTextureFileExtension;
    }

    public void loadTextureAssets()
    {
        String path = AssetLoader.getPathToDir();
        if (!path.equals(""))
            loadTextureAssets(path);
    }

    public void loadTextureAssets(String path)
    {
        HashMap<String, BufferedImage> map = AssetLoader.LoadImageAssets(path, allowedTextureFileExtension);
        assert map != null;
        map.forEach((k, v) -> textures.put(k, v));
    }

    public HashMap<String, BufferedImage> getTextures()
    {
        return textures;
    }

    //endregion

    //region Grid
    public void setMainGrid(Grid mainGrid)
    {
        this.mainGrid = mainGrid;
    }

    public GameObject addGameObject(GameObject gameObject, int x, int y)
    {
        assert mainGrid !=null;
        mainGrid.addGameObject(gameObject, x, y).setId(gObjectId);
        gObjectId += GAMEOBJECT_ID_INCREMENT;
        return gameObject;
    }

    public GameObject addGameObject(int x, int y, boolean isStatic)
    {
        assert mainGrid !=null;
        GameObject gameObject = mainGrid.addGameObject(x, y, isStatic);
        gameObject.setId(gObjectId);
        gObjectId += GAMEOBJECT_ID_INCREMENT;
        return gameObject;
    }

    public GameObject getGameObject(int x, int y)
    {
        assert mainGrid !=null;
        return mainGrid.get(x, y);
    }

    public GameObject getGameObject(long id)
    {
        assert mainGrid !=null;
        return mainGrid.get(id);
    }

    public GameObject[] getGameObjects(Class<? extends GameComponent> containedComponent)
    {
        assert mainGrid !=null;
        return mainGrid.getGameObjectsWithComponent(containedComponent);
    }
    //endregion

    //region Renderer

    public FrameRenderer getFrameRenderer()
    {
        return frameRenderer;
    }

    public void setFrameRenderer(FrameRenderer frameRenderer)
    {
        this.frameRenderer = frameRenderer;
    }

    public void refreshSpritesInRenderer()
    {
        assert frameRenderer != null;
        Stream<GameObject> stream1 = mainGrid.getStaticObjects().stream();
        Stream<GameObject> stream2 = mainGrid.getDynamicObjects().stream();
        Stream.concat(stream1, stream2).
                map(g -> g.getComponent(Sprite.class)).
                filter(Objects::nonNull).map(c -> (Sprite) c).
                forEach(c -> frameRenderer.getGridRenderer().addSprite(c));
    }

    public void addSprite(Sprite sprite)
    {
        assert frameRenderer != null;
        frameRenderer.getGridRenderer().addSprite(sprite);
    }

    public void startRenderer()
    {
        assert frameRenderer != null;
        frameRenderer.start();
    }

    public void stopRenderer()
    {
        assert frameRenderer != null;
        frameRenderer.stop();
    }

    public void addRendererTimer(Timer timer)
    {
        assert frameRenderer != null;
        renderTimer = timer;
        frameRenderer.addTimer(timer);
    }

    public void addRendererTimer(int delay)
    {
        frameRenderer.addTimer(delay);
    }

    public boolean isRendererStarted()
    {
        assert frameRenderer != null;
        return frameRenderer.isStarted();
    }
    //endregion


}
