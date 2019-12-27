package com.main.jms.consumers;

import java.util.Hashtable;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class JMSConsumer {

  public static void main(String[] args) throws JMSException, NamingException {
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
      for (int lp1 = 1; lp1 <= 10; lp1++) {
        MessageConsumer consumer = session.createConsumer(queue);
        TextMessage msg = (TextMessage) consumer.receive();
        System.out.println("Message received from the producer :" + msg.getText());
      }
    } finally {
      if (conn != null) {
        conn.close();
      }
    }
  }
}
