package evhh.view.audio;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/***********************************************************************************************************************
 * @project: MainProject
 * @package: evhh.view.audio
 * ---------------------------------------------------------------------------------------------------------------------
 * @authors: Hamed Haghjo & Elias Vahlberg
 * @date: 2021-05-21
 * @time: 19:10
 **********************************************************************************************************************/
public class AudioTest implements LineListener
{
    boolean playCompleted = false;

    public static void main(String[] args)
    {
        AudioTest dis = new AudioTest();
        dis.play(System.getProperty("user.dir") +"/Assets/Music/MOOSIK.wav");
    }

    public void play(String path)
    {
        File audioFile = new File(path);

        try
        {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);

            AudioFormat format = audioStream.getFormat();

            DataLine.Info info = new DataLine.Info(Clip.class, format);

            Clip audioClip = (Clip) AudioSystem.getLine(info);

            audioClip.addLineListener(this);

            audioClip.open(audioStream);

            audioClip.start();

            while (!playCompleted)
            {
                // wait for the playback completes
                try
                {
                    Thread.sleep(1000);
                } catch (InterruptedException ex)
                {
                    ex.printStackTrace();
                }
            }
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void update(LineEvent event)
    {
        LineEvent.Type type = event.getType();
        if (type == LineEvent.Type.START)
            System.out.println("Started");
        else if (type == LineEvent.Type.STOP)
            System.out.println("Stopped");
    }
}
