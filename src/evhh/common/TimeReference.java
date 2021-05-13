package evhh.common;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
    Duration deltaTime = Duration.ZERO;
    Instant startTime;


    public void start()
    {
        startTime = Instant.now();
    }
    public void  reset()
    {
        startTime = Instant.now();
    }
    public long getDeltaTime()
    {
        deltaTime = Duration.between(startTime, Instant.now());
        return deltaTime.toMillis();
    }
    public void incrementStartTime(long n)
    {
        if(n==0)
            return;
        startTime.plus(n, ChronoUnit.MILLIS);
    }
}
