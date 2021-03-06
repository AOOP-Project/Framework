package evhh.view.renderers;

import evhh.view.audio.AudioListener;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

/***********************************************************************************************************************
 * @project: MainProject
 * @package: evhh.view.renderers
 * ---------------------------------------------------------------------------------------------------------------------
 * @authors: Hamed Haghjo & Elias Vahlberg
 * @date: 2021-05-12
 * @time: 14:06
 **********************************************************************************************************************/
public class FrameRenderer implements ActionListener
{
    private GridRenderer gridRenderer;
    private GameFrame gameFrame;
    private Timer renderTimer;
    private boolean started = false;
    private AudioListener audioListener;

    public FrameRenderer(GameFrame gameFrame, int gridWidth, int gridHeight, int cellSize)
    {
        this.gameFrame = gameFrame;
        this.gridRenderer = new GridRenderer(gridWidth, gridHeight, cellSize);
        this.gameFrame.add(this.gridRenderer.getGridPanel());
        this.gameFrame.pack();

    }

    public FrameRenderer(GameFrame gameFrame, GridRenderer gridRenderer)
    {
        this.gameFrame = gameFrame;
        this.gridRenderer = gridRenderer;
        this.gameFrame.add(this.gridRenderer.getGridPanel());
        this.gameFrame.pack();
    }

    public void renderFrame()
    {
        gridRenderer.renderFrame();
    }

    public GridRenderer getGridRenderer()
    {
        return gridRenderer;
    }

    public void setGridRenderer(GridRenderer gridRenderer)
    {
        this.gridRenderer = gridRenderer;
    }

    public void start()
    {
        if (renderTimer != null)
            renderTimer.start();
        started = true;
        gameFrame.setVisible(true);
    }

    public void stop()
    {

        if (renderTimer != null)
            renderTimer.stop();
        started = false;
    }

    public void addTimer(int delay)
    {
        renderTimer = new Timer(delay, this);
    }

    public void addTimer(Timer timer)
    {
        renderTimer = timer;
        timer.addActionListener(this);
    }

    public Timer getRenderTimer()
    {
        return renderTimer;
    }

    public boolean isStarted()
    {
        return started;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (started)
            if (e.getSource() == renderTimer)
                renderFrame();

    }

    public GameFrame getGameFrame()
    {
        return gameFrame;
    }

    public BufferedImage getGridBackgroundImage()
    {
        return gridRenderer.getBackground();
    }

    public void setGridBackgroundImage(BufferedImage image)
    {
        gridRenderer.setBackground(image);
    }

    public AudioListener getAudioListener()
    {
        return audioListener;
    }

    public void setAudioListener(AudioListener audioListener)
    {
        this.audioListener = audioListener;
    }
}
