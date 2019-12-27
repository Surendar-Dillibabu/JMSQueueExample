package com.main.jms.consumers;

import java.util.Hashtable;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class JMSMultipleConsumerEx {

  public static void main(String[] args) throws NamingException, JMSException, InterruptedException {
    Connection conn = null, conn1 = null;
    try {
      Hashtable<String, String> ht = new Hashtable<>();
      ht.put(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");
      ht.put(Context.PROVIDER_URL, "t3://localhost:7001");
      Context context = new InitialContext(ht);
      ConnectionFactory connFactory = (ConnectionFactory) context.lookup("jms/TestConnectionFactory");
      ConnectionFactory connFactory1 = (ConnectionFactory) context.lookup("jms/TestConnectionFactory");
      conn = connFactory.createConnection();
      conn1 = connFactory1.createConnection();
      Session session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
      Session session1 = conn1.createSession(false, Session.AUTO_ACKNOWLEDGE);
      Queue queue = (Queue) context.lookup("jms/TestQueue");
      conn.start();
      conn1.start();
      MessageConsumer consumer = session.createConsumer(queue);
      MessageConsumer consumer1 = session1.createConsumer(queue);
      consumer.setMessageListener(new JMSMessageListenerFromMultipleConsumer("Consumer-1"));
      consumer1.setMessageListener(new JMSMessageListenerFromMultipleConsumer("Consumer-2"));
      System.out.println("Message listener setted for listening the messages");
      // Sleep time for to receive the messages in the listener. Or else before
      // receiving the messages in the message listener the main thread will complete
      // this method call
      Thread.sleep(3000);
    } finally {
      if (conn != null) {
        conn.close();
      }
      if (conn1 != null) {
        conn1.close();
      }
    }
  }
}
