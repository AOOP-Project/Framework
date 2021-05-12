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
    BufferedImage image;

    public Sprite(GameObject parent, BufferedImage image)
    {
        super(parent);
        this.image = image;
    }

    public BufferedImage getImage()
    {
        return image;
    }
}
