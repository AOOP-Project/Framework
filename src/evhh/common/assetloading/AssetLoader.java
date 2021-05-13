package evhh.common.assetloading;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

/***********************************************************************************************************************
 * @project: MainProject
 * @package: evhh.common.assetloading
 * ---------------------------------------------------------------------------------------------------------------------
 * @authors: Hamed Haghjo & Elias Vahlberg
 * @date: 2021-05-12
 * @time: 15:22
 **********************************************************************************************************************/
public class AssetLoader
{

    public static String getPathToDir()
    {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int response = fileChooser.showOpenDialog(null);
        if(response == JFileChooser.APPROVE_OPTION)
            return fileChooser.getSelectedFile().getAbsolutePath();
        else
            return "";

    }

    public static HashMap<String, BufferedImage> LoadImageAssets(String path,String[] acceptedFileExtensions)
    {
        File dir = new File(path);
        if(!dir.isDirectory())
            return null;
        HashMap<String, BufferedImage> map = new HashMap<>();
        for(File file: Objects.requireNonNull(dir.listFiles()))
        {
            BufferedImage img = null;
            String name = "";

            try
            {
                img = ImageIO.read(file);
                name = file.getName().replaceFirst("[.][^.]+$", "");
            } catch (IOException e)
            {
                e.printStackTrace();
                continue;
            }
            map.put(name,img);
        }
        return  map;
    }
}
