package entity;

import java.time.Instant;
public class TimeStamp {
    private int currentTime;

    public TimeStamp(){
        this.currentTime= (int) Instant.now().getEpochSecond();
    }

    public int currentTime(){
        return (int) Instant.now().getEpochSecond();
    }
}
