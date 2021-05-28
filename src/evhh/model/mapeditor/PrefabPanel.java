package evhh.model.mapeditor;

import evhh.model.ObjectPrefab;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

/***********************************************************************************************************************
 * @project: MainProject
 * @package: evhh.model.mapeditor
 * ---------------------------------------------------------------------------------------------------------------------
 * @authors: Hamed Haghjo & Elias Vahlberg
 * @date: 2021-05-15
 * @time: 10:40
 **********************************************************************************************************************/
public class PrefabPanel extends JPanel implements MouseListener
{
    BufferedImage preFabSpriteTexture;
    MapEditor mapEditor;
    ObjectPrefab prefab;

    public PrefabPanel(ObjectPrefab prefab, BufferedImage preFabSprite, Dimension dimension, MapEditor mapEditor)
    {
        super();
        this.prefab = prefab;
        this.preFabSpriteTexture = preFabSprite;
        this.mapEditor = mapEditor;
        setPreferredSize(dimension);
        addMouseListener(this);
    }

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        assert preFabSpriteTexture != null;
        g2d.drawImage(preFabSpriteTexture, 0, 0, this);

    }

    @Override
    public void mouseClicked(MouseEvent e)
    {


    }

    @Override
    public void mousePressed(MouseEvent e)
    {
        if (e.getSource() == this)
        {
            mapEditor.setSelectedPrefab(prefab);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e)
    {

    }

    @Override
    public void mouseEntered(MouseEvent e)
    {

    }

    @Override
    public void mouseExited(MouseEvent e)
    {

    }
}
