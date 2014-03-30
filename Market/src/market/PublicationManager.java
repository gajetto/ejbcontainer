/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package market;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import trading.MarketStatusNotification;



/**
 *
 * @author Sohaila.Baset
 */
public class PublicationManager{
    
    final String TOPIC_NAME = "market";
    private TopicSession session = null;
    private TopicPublisher publisher = null;
    private Message message = null;

    private Timer daTimer;
    private PPriceEvolution pE;
    public static final StockService service = new StockService();
    private ArrayList daList;
    
    private MarketGUI gui=null;
    
    public PublicationManager(MarketGUI gui) {
        this.gui=gui;

      
}
    
    
    public void initialize()
    {
        try {
            TopicConnectionFactory connectionFactory = new com.sun.messaging.TopicConnectionFactory();
            TopicConnection  connection = connectionFactory.createTopicConnection();
            session = connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
            javax.jms.Topic topic = session.createTopic(TOPIC_NAME);
            publisher = session.createPublisher(topic);
            startTimer();
            this.gui.updateEventList("Price publication manager started!!",Color.BLUE);
            
        } catch (JMSException ex) {
            this.gui.updateEventList("An error occured while starting publication manager!",Color.RED);
            Logger.getLogger(PublicationManager.class.getName()).log(Level.SEVERE, null, ex);
        }
      
 }

        public void startTimer() {
        this.daTimer = new Timer();
        daList = service.getDaList();
        this.pE = new PPriceEvolution(this, daList);
        this.getDaTimer().schedule(this.pE, 0, 60 * 100);
    }
    
        public void stopTimer() {
        this.getDaTimer().cancel();
    }
    public void publishPrices(ArrayList stockList) 
    {
        try {
            message = session.createObjectMessage(stockList);
            publisher.publish(message);     
            this.gui.updateEventList("New prices have been published...",Color.BLACK);
        } catch (JMSException ex) {
            Logger.getLogger(PublicationManager.class.getName()).log(Level.SEVERE, null, ex);
            this.gui.updateEventList("Error occured. New prices could not be published!",Color.RED);
        }
       
    }

    /**
     * @return the daTimer
     */
    public Timer getDaTimer() {
        return daTimer;
    }
    
    public void SendStatusNotification(MarketStatusNotification statusNotification)
     {
      try {
            message = session.createObjectMessage(statusNotification);
            publisher.publish(message);     
            this.gui.updateEventList("Status notification sent to clients...",Color.BLUE);
        } catch (JMSException ex) {
            Logger.getLogger(PublicationManager.class.getName()).log(Level.SEVERE, null, ex);
            this.gui.updateEventList("Error occured. Status notification could not be sent to clients!",Color.RED);
        }
     }
}
