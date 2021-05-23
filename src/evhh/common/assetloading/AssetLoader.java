package evhh.common.assetloading;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
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

    public static String getPathToSavedData()
    {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setFileFilter(new FileNameExtensionFilter("Serialized data", "ser"));
        int response = fileChooser.showOpenDialog(null);
        if (response == JFileChooser.APPROVE_OPTION)
            return fileChooser.getSelectedFile().getAbsolutePath();
        else
            return "";
    }

    public static String setPathToSaveData()
    {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int response = fileChooser.showSaveDialog(null);
        if (response == JFileChooser.APPROVE_OPTION)
            return fileChooser.getSelectedFile().getAbsolutePath();
        else
            return "";
    }

    public static String getPathToDir()
    {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int response = fileChooser.showOpenDialog(null);
        if (response == JFileChooser.APPROVE_OPTION)
            return fileChooser.getSelectedFile().getAbsolutePath();
        else
            return "";

    }

    public static HashMap<String, BufferedImage> LoadImageAssets(String path, String[] acceptedFileExtensions)
    {
        File dir = new File(path);
        if (!dir.isDirectory())
            return null;
        HashMap<String, BufferedImage> map = new HashMap<>();
        for (File file : Objects.requireNonNull(dir.listFiles()))
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
            map.put(name, img);
        }
        return map;
    }

    public static File[] LoadFileAssetsFromDir(String path, String[] acceptedFileExtensions)
    {
        File dir = new File(path);
        if (!dir.isDirectory())
            return null;
        File[] files = new File[Objects.requireNonNull(dir.listFiles()).length];
        int i = 0;
        for (File file : Objects.requireNonNull(dir.listFiles()))
        {
            String fileName = file.toString();

            int index = fileName.lastIndexOf('.');
            if(index > 0) {
                String extension = fileName.substring(index + 1);
                if(Arrays.asList(acceptedFileExtensions).contains(extension))
                    files[i++]=file;
            }
        }
        File[] shortened = new File[i+1];
        System.arraycopy(files, 0, shortened, 0, i + 1);
        return shortened;
    }
    public static File loadFileUsingJFileChooser()
    {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int response = fileChooser.showSaveDialog(null);
        if (response == JFileChooser.APPROVE_OPTION)
            return new File(fileChooser.getSelectedFile().getAbsolutePath());
        else
            return null;
    }
}
