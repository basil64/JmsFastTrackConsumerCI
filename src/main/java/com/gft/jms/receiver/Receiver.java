package com.gft.jms.receiver;

import com.gft.jms.pipe.DelayedElement;
import com.gft.jms.pipe.Pipe;
import com.gft.jms.sender.Sender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.springframework.util.FileSystemUtils;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import java.io.File;

@Component
public class Receiver {
    public static final int MAX_DELAY = 20000;
    public static final int TIME_OFFSET = 1000;
    Logger logger = LoggerFactory.getLogger(Receiver.class);

    private Pipe pipe;

    @Autowired
    public Receiver(Pipe pipe) {
        this.pipe = pipe;
    }
    @Autowired
    private ConfigurableApplicationContext context;

    @JmsListener(destination = "RequestQueue")
    public void receiveMessage(MapMessage message) {
        try {
            logger.info("Received message. CorrelationId [{}]", message.getJMSCorrelationID());
            pipe.put(new DelayedElement(message.getJMSCorrelationID(), TIME_OFFSET + (long)(Math.random() * MAX_DELAY)));
        } catch (JMSException e) {
            e.printStackTrace();
        }
        FileSystemUtils.deleteRecursively(new File("activemq-data"));
    }
}