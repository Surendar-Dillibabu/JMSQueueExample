package com.main.jms.consumers;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class JMSMessageListener implements MessageListener {

  private static int count = 0;

  @Override
  public void onMessage(Message message) {
    try {
      TextMessage msg = (TextMessage) message;
      count++;
      System.out.println("Message received in the listener :" + msg.getText());
    } catch (JMSException e) {
      e.printStackTrace();
    }
    if (count > 3) {
      // throw new RuntimeException(); For checking the re-delivery
    }
  }

}
