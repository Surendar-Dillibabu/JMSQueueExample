package com.main.jms.producers;

import java.util.Hashtable;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class JMSProducer {

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
      for (int lp1 = 1; lp1 <= 30; lp1++) {
        MessageProducer producer = session.createProducer(queue);
        TextMessage msg = session.createTextMessage("Hi, Am msg-" + lp1);
        producer.send(msg);
      }
      System.out.println("Message succesfully sent to the producer");
    } finally {
      if (conn != null) {
        conn.close();
      }
    }
  }
}
