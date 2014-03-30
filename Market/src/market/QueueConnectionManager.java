/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package market;

import com.sun.messaging.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.Session;

/**
 *
 * @author Sohaila.Baset
 */
public class QueueConnectionManager{
    
    private  final  String QUEUE_NAME = "Trader";
    private  Queue queue = null;
    private  Session session = null;
    private  MessageConsumer consumer = null;
    private  MessageProducer producer = null;
    private  Connection connection=null;
    private  QueueConnectionFactory connectionFactory;
    
    private static QueueConnectionManager instance=null;

    
    private QueueConnectionManager(){}
    
    public static QueueConnectionManager getInstance() {
        if(instance==null){
            try {
                instance=new QueueConnectionManager();
                instance.connectionFactory = new com.sun.messaging.QueueConnectionFactory();
                instance.connection =  instance.connectionFactory.createConnection();
                instance.session = instance.connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
                instance.queue= new com.sun.messaging.Queue(instance.QUEUE_NAME);
                instance.consumer = instance.getSession().createConsumer(instance.queue);
                instance.producer = instance.getSession().createProducer(instance.queue);
                instance.connection.start();
            } catch (JMSException ex) {
                Logger.getLogger(QueueConnectionManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return instance;
    }

    /**
     * @return the connection
     */
    public Connection getConnection() {
        return instance.connection;
    }
    
    /**
     * @return the consumer
     */
    public  MessageConsumer getConsumer() {
        return instance.consumer;
    }

    /**
     * @return the producer
     */
    public MessageProducer getProducer() {
        return producer;
    }

    /**
     * @return the session
     */
    public Session getSession() {
        return session;
    }
    
    
    
    
}
