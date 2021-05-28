package evhh.common.imagemanipulation;

import java.awt.*;
import java.awt.image.BufferedImage;

/***********************************************************************************************************************
 * @project: MainProject
 * @package: evhh.common.imagemanipulation
 * ---------------------------------------------------------------------------------------------------------------------
 * @authors: Hamed Haghjo & Elias Vahlberg
 * @date: 2021-05-16
 * @time: 13:55
 **********************************************************************************************************************/
public class ImageTiler
{
    public static BufferedImage tileImage(BufferedImage image, int cols, int rows)
    {
        int width = image.getWidth() * cols;
        int height = image.getWidth() * rows;
        BufferedImage tiledImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics2D = tiledImage.createGraphics();
        Color oldColor = graphics2D.getColor();
        graphics2D.setPaint(Color.BLACK);
        graphics2D.fillRect(0, 0, width, height);
        graphics2D.setColor(oldColor);
        for (int i = 0; i < cols; i++)
        {
            for (int j = 0; j < rows; j++)
            {
                graphics2D.drawImage(image, null, image.getWidth() * i, image.getHeight() * j);
            }
        }
        graphics2D.drawImage(tiledImage, null, 0, 0);
        graphics2D.dispose();
        return tiledImage;


    }
}
