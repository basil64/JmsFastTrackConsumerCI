package com.gft.jms.sender;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

/**
 * Created by piotrulek on 2016-01-24.
 */
@Component
public class Sender {

    public static final String REPLY_QUEUE = "ReplyQueue";
    private final JmsTemplate jmsTemplate;

    @Autowired
    public Sender(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void sendReplayMessage(final String correlationId) {
        jmsTemplate.send(REPLY_QUEUE, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                Message textMessage = session.createTextMessage("Message resend [" + correlationId + "]");
                textMessage.setJMSCorrelationID(correlationId);
                return textMessage;
            }
        });
    }
}
