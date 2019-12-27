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

public class JMSConsumerUsingListener {

  public static void main(String[] args) throws NamingException, JMSException, InterruptedException {
    Connection conn = null;
    try {
      Hashtable<String, String> ht = new Hashtable<>();
      ht.put(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");
      ht.put(Context.PROVIDER_URL, "t3://localhost:7001");
      Context context = new InitialContext(ht);
      ConnectionFactory connFactory = (ConnectionFactory) context.lookup("jms/TestConnectionFactory");
      conn = connFactory.createConnection();
      Session session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
      Queue queue = (Queue) context.lookup("jms/TestQueue");
      conn.start();
      MessageConsumer consumer = session.createConsumer(queue);
      consumer.setMessageListener(new JMSMessageListener());
      System.out.println("Message listener setted for listening the messages");
      // Sleep time for to receive the messages in the listener. Or else before
      // receiving the messages in the message listener the main thread will complete
      // this method call
      Thread.sleep(1000);
    } finally {
      if (conn != null) {
        conn.close();
      }
    }
  }
}
