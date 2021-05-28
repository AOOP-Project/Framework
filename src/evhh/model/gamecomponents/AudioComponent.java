package evhh.model.gamecomponents;

import evhh.model.GameComponent;
import evhh.model.GameObject;
import evhh.view.audio.AudioListener;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

/***********************************************************************************************************************
 * @project: MainProject
 * @package: evhh.model.gamecomponents
 * ---------------------------------------------------------------------------------------------------------------------
 * @authors: Hamed Haghjo & Elias Vahlberg
 * @date: 2021-05-21
 * @time: 21:22
 **********************************************************************************************************************/
public class AudioComponent extends GameComponent
{
    private AudioListener audioListener;
    private File[] audioFiles;

    transient private Clip audioClip;
    transient private AudioInputStream audioStream;
    transient private DataLine.Info info;
    transient private AudioFormat format;
    private float volume = 0.5f;
    private FloatControl volumeControl;

    public AudioComponent(GameObject parent, AudioListener audioListener, File[] audioFiles)
    {
        super(parent);
        this.audioListener = audioListener;
        this.audioFiles = audioFiles;

    }

    public AudioComponent(GameObject parent, AudioListener audioListener, File[] audioFiles, float volume)
    {
        super(parent);
        this.audioListener = audioListener;
        this.audioFiles = audioFiles;
        this.volume = volume;

    }

    public boolean play(int index)
    {
        try
        {
            audioStream = AudioSystem.getAudioInputStream(audioFiles[index]);
            format = audioStream.getFormat();
            info = new DataLine.Info(Clip.class, format);
            audioClip = (Clip) AudioSystem.getLine(info);

            audioClip.addLineListener(audioListener);
            audioClip.open(audioStream);

            volumeControl = (FloatControl) audioClip.getControl(FloatControl.Type.MASTER_GAIN);
            volumeControl.setValue(volume);

            audioClip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException | IndexOutOfBoundsException ignored)
        {
            ignored.printStackTrace();
            return false;
        }
        return true;
    }

    public void resume()
    {
        assert audioClip != null;
        assert audioClip.isOpen();
        audioClip.start();
    }

    public void pause()
    {
        assert audioClip != null;
        audioClip.stop();
    }

    public void stop()
    {
        assert audioClip != null;
        audioClip.close();
    }

    public boolean isPlaying()
    {
        if (audioClip == null)
            return false;
        return audioClip.isRunning();
    }

    public void setVolume(float gain)
    {
        volume = gain;
        if (isPlaying() && volumeControl != null)
            volumeControl.setValue(gain);
    }

    public float getVolume()
    {
        return volume;
    }

    @Override
    public void onStart()
    {

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
