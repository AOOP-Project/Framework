package evhh.common;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalField;
import java.util.Date;

/***********************************************************************************************************************
 * @project: MainProject
 * @package: evhh.common
 * ---------------------------------------------------------------------------------------------------------------------
 * @authors: Hamed Haghjo & Elias Vahlberg
 * @date: 2021-05-13
 * @time: 13:29
 **********************************************************************************************************************/
public class TimeReference
{
    private Date time;
    private Duration deltaTime = Duration.ZERO;
    private Instant startTime;
    private boolean started = false;


    public void start()
    {
        started = true;
        startTime = Instant.now();
    }

    public void reset()
    {
        startTime = Instant.now();
    }

    public long getDeltaTime()
    {
        if (!started)
            return 0;
        deltaTime = Duration.between(startTime, Instant.now());
        return deltaTime.toMillis();
    }

    public void incrementStartTime(long n)
    {
        if (n == 0)
            return;
        startTime = startTime.plusMillis(n);

    }

    public long getStartTime()
    {
        return startTime.toEpochMilli();
    }
}
