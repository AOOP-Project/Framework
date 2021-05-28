package evhh.view.audio;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

/***********************************************************************************************************************
 * @project: MainProject
 * @package: evhh.view.audio
 * ---------------------------------------------------------------------------------------------------------------------
 * @authors: Hamed Haghjo & Elias Vahlberg
 * @date: 2021-05-21
 * @time: 21:18
 **********************************************************************************************************************/
public class AudioListener implements LineListener, Serializable
{
    private boolean currentlyPlaying = false;

    @Override
    public void update(LineEvent event)
    {
        LineEvent.Type type = event.getType();
        if (type == LineEvent.Type.START)
            currentlyPlaying = true;
        else if (type == LineEvent.Type.STOP)
            currentlyPlaying = false;
    }

    public boolean isCurrentlyPlaying()
    {
        return currentlyPlaying;
    }

}
