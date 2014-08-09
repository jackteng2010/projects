package com.core.mqactive;

import java.util.Map;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONObject;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

public class MqMessageProducer {
    
	private JmsTemplate jmsTemplate;

    private ActiveMQQueue tcpQueue;
    
    private static Log logger = LogFactory.getLog(MqMessageProducer.class);
    
    public void sendMessage(final Map<String, Object> jsonMap) {
        sendMessage(new JSONObject(jsonMap).toString());
    }

    public void sendMessage(final String jsonData) {
        try {
            jmsTemplate.send(tcpQueue, new MessageCreator() {
                @Override
                public Message createMessage(Session session) throws JMSException {
                	return session.createTextMessage(jsonData);
                }
            });
        } catch (Exception e) {
            logger.error("Send jms message failed ! ", e);
        }
    }

	public JmsTemplate getJmsTemplate() {
		return jmsTemplate;
	}

	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

	public ActiveMQQueue getTcpQueue() {
		return tcpQueue;
	}

	public void setTcpQueue(ActiveMQQueue tcpQueue) {
		this.tcpQueue = tcpQueue;
	}
    
}
