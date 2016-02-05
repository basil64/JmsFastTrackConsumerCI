package com.gft.jms.pipe;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;


public class DelayedElement implements Delayed {

    private String correlationId;
    private long timeOut;
    private long startTime;

    public DelayedElement(String correlationId, long timeOut) {
       this.correlationId = correlationId;
       this.timeOut = timeOut;
       this.startTime = System.currentTimeMillis();
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(timeOut + startTime - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
        long diff = getDelay(TimeUnit.MILLISECONDS) - o.getDelay(TimeUnit.MILLISECONDS);
        if (diff > 0) {
            return 1;
        } else if (diff < 0) {
            return -1;
        } else {
            return 0;
        }
    }

    public String getCorrelationId() {
        return correlationId;
    }

    public long getTimeOut() {
        return timeOut;
    }

    public long getStartTime() {
        return startTime;
    }
}
