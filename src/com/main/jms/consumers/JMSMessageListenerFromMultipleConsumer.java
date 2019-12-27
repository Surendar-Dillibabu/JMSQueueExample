package com.main.jms.consumers;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class JMSMessageListenerFromMultipleConsumer implements MessageListener {

  private String consumerName;

  JMSMessageListenerFromMultipleConsumer(String consumerName) {
    this.consumerName = consumerName;
  }

  @Override
  public void onMessage(Message message) {
    try {
      TextMessage msg = (TextMessage) message;
      System.out.println("Message received in the listener :" + msg.getText() + " by consumer :" + consumerName);
    } catch (JMSException e) {
      e.printStackTrace();
    }
  }
}
