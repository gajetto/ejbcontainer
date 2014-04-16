/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package trader;

import dataTransferObjects.StockProductDTO;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;


/**
 *
 * @author Manixab
 */
public class MarketManager {//implements MessageListener {

    final private String TOPIC_NAME = "market";
    private MessageListener listener;
    private TopicConnectionFactory connectionFactory;
    private TopicConnection connection;
   // private UserGUI uGUI;
    private UserData ud;
    
    public MarketManager(UserData ud) {
        this.ud = ud;
        connectionFactory = new com.sun.messaging.TopicConnectionFactory();
        connection = null;
        
    }
    
//    public UserGUI getuGUI() {
//        return uGUI;
//    }
//
//    public void setuGUI(UserGUI uGUI) {
//        this.uGUI = uGUI;
//    }
    
    
    /**
     * Connection to the JMS in order to enter the market for pub/sub
     */
    public void clientSubscribe() {
        //Connection to JMS
        try {
            connection = connectionFactory.createTopicConnection();
            TopicSession session = connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
            Topic topic = new com.sun.messaging.Topic(TOPIC_NAME);
            TopicSubscriber subscriber = session.createSubscriber(topic);
//                TopicSubscriber subscriber = session.createDurableSubscriber(topic, trader.getName());
            //listener = this;
            subscriber.setMessageListener(listener);
            connection.start();

        } catch (Exception e) {
            System.out.println("Exception occurred: " + e.toString());
            System.exit(1);
        }

        System.out.println("Client started!!");

/*        synchronized (listener) {
            try {
                listener.wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(UserGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }*/
    }

        /**
     * Get the object from the JMS
     * @param message 
     */
//    @Override
//    public void onMessage(javax.jms.Message message) {
//
//        ObjectMessage stockProducts = null;
//        stockProducts = (ObjectMessage) message;
////        try {
////            
////            //uGUI.setStocks((ArrayList<StockProductDTO>) stockProducts.getObject());
////        } catch (JMSException ex) {
////            Logger.getLogger(UserGUI.class.getName()).log(Level.SEVERE, null, ex);
////        }
//        //uGUI.updateStocks();
//        synchronized (this) {
//            this.notify();
//        }
//    }
    
}
