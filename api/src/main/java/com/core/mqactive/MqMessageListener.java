package com.core.mqactive;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MqMessageListener implements MessageListener {

	private Log logger = LogFactory.getLog(getClass());
	
    @Override
    public void onMessage(Message message) {
        ActiveMQTextMessage txtMsg = (ActiveMQTextMessage) message;
        String text = null;
        try {
            text = txtMsg.getText();
        } catch (JMSException e) {
            logger.error("Receive message failed", e);
        } catch (Exception e) {
            logger.error(String.format("Save behavior failed with parameters [%s]", text), e);
        }
    }

}
