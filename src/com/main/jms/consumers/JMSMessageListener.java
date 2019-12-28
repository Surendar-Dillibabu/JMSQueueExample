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
      // Acknowledge to the JMS provider that the client is received the message
      // properly. So, they remove the message from their FileStore or JDBCStore which
      // hash been configured. They won't re-deliver the message again
      // We have to call this acknowledge method only when we create the session with
      // CLIENT_ACKNOWLEDGE option. For AUTO_ACKNOWLEDGE and DUPS_OK_ACKNOWLEDGE it
      // has been acknowledged automatically when the onMessage is executed properly.
      if(count > 29) {
        // If we acknowledge the last message then all the previous message as well will get acknowledged
        // It will acknowledge all the messages which has been received through the particular session
        msg.acknowledge(); 
      }
    } catch (JMSException e) {
      e.printStackTrace();
    }
    if (count > 3) {
      // For checking the re-delivery
      // throw new RuntimeException();
    }
  }

}
