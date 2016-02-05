package com.gft.jms.sender;

import com.gft.jms.pipe.DelayedElement;
import com.gft.jms.pipe.Pipe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import static java.lang.Thread.sleep;

@Service
public class SenderThread {
    public static final int DELAY = 2000;
    public static final int BASIC_DELAY = 5000;
    Logger logger = LoggerFactory.getLogger(SenderThread.class);

    private Sender messageSender;
    private Pipe pipe;

    @Autowired
    public SenderThread(Sender messageSender, Pipe pipe) {
        this.messageSender = messageSender;
        this.pipe = pipe;
    }

    @Async
    public void run() {
        while (true) {
            String correlationId = pipe.get();
            messageSender.sendReplayMessage(correlationId);
            logger.info("Message [{}]sent to RequestQueue", correlationId );
        }
    }

}
