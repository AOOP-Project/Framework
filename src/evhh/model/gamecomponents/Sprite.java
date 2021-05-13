package evhh.model.gamecomponents;

import evhh.model.GameComponent;
import evhh.model.GameObject;

import java.awt.image.BufferedImage;

/***********************************************************************************************************************
 * @project: MainProject
 * @package: evhh.model.gamecomponents
 * ---------------------------------------------------------------------------------------------------------------------
 * @authors: Hamed Haghjo & Elias Vahlberg
 * @date: 2021-05-12
 * @time: 14:01
 * A moveable graphical component (*TABORT*)
 **********************************************************************************************************************/
public class Sprite extends GameComponent
{
    private String imageRef;
    private transient BufferedImage texture;

    public Sprite(GameObject parent, BufferedImage image,String imageRef)
    {
        super(parent);
        this.texture = image;
        this.imageRef = imageRef;
    }

    public BufferedImage getTexture()
    {
        return texture;
    }

    //(*TABORT*) kan kanske komma till nytta, tänker för ifall en sprite behöver förändra lite karakteristik
    public void switchImage(BufferedImage image)
    {
        this.image = image;
    }

    @Override
    public void onStart()
    {
        if(texture == null && imageRef != null)
            texture = parent.getGrid().getGameInstance().getTexture(imageRef);
    }

    @Override
    public void update()
    {

    }

    @Override
    public void onExit()
    {

    }
}
