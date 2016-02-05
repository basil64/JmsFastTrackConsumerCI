package com.gft.jms.pipe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

@Component
public class Pipe {
    Logger logger = LoggerFactory.getLogger(Pipe.class);
    public static final int SLEEP_TIME = 2000;
    private BlockingQueue<DelayedElement> queue;

    public Pipe() {
        this.queue = new DelayQueue<>();
    }

    public void put(DelayedElement delayedElement) {
        try {
            queue.put(delayedElement);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String get() {
        DelayedElement delayedElement = null;
        try {
            //Thread.sleep(SLEEP_TIME);
            delayedElement = queue.take();
            logger.info("[{}] delay [{}] timeout [{}]", delayedElement.getCorrelationId(), delayedElement.getDelay(TimeUnit.MILLISECONDS),
                    delayedElement.getTimeOut());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return delayedElement.getCorrelationId();
    }

    public int getSize() {
        return queue.size();
    }
}