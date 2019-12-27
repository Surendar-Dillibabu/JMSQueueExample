package com.main.jms.consumers;

import java.util.Enumeration;
import java.util.Hashtable;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class JMSMessageBrowserEx {

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
      QueueBrowser browser = session.createBrowser(queue);
      System.out.println("Message selector :" + browser.getMessageSelector());
      System.out.println("Iterating messages in the QueueBrowser");
      @SuppressWarnings("unchecked")
      Enumeration<Message> enumerator = browser.getEnumeration();
      while (enumerator.hasMoreElements()) {
        TextMessage msg = (TextMessage) enumerator.nextElement();
        System.out.println("Message present in the queue :" + msg.getText());
      }
    } finally {
      if (conn != null) {
        conn.close();
      }
    }
  }
}
