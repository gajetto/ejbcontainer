/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package trader;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;
import trading.SessionState;
import trading.SessionStatusRequest;
import trading.SessionStatusResponse;
import trading.StockProduct;
import trading.TradingTransaction;
import trading.TradingTransactionType;

/**
 *
 * @author Manixab
 */
public class TraderManager implements MessageListener {

    private final  String QUEUE_NAME = "Trader";
    
    private UserGUI uGUI;
    private JoinMarketGUI jmGUI;
    private boolean connected;
    private String name;
    
    private MarketManager marketM;
    
    public TraderManager(JoinMarketGUI jmGUI, MarketManager marketM) {
        this.jmGUI = jmGUI;
        this.marketM = marketM;
        connected = false;
    }
    
    public TraderManager() {
        
    }

//    public UserGUI getuGUI() {
//        return uGUI;
//    }
//
//    public void setuGUI(UserGUI uGUI) {
//        this.uGUI = uGUI;
//    }
//    
    
    /**
     * Connection to JMS for the point to point when a buying or a selling happen
     */
    public void connection(String name) {
        this.name = name;
        SessionState ss = SessionState.Connected;
        SessionStatusRequest ssr = new SessionStatusRequest(name, ss);
        ConnectionFactory connectionFactory = new com.sun.messaging.ConnectionFactory();
        Connection connection = null;

        try {
            
            connection = connectionFactory.createConnection();
            Queue queue = new com.sun.messaging.Queue(QUEUE_NAME);
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageProducer producer = session.createProducer(queue);
            ObjectMessage message = session.createObjectMessage(ssr);
            System.out.println("Sending order [" + message.getObject().toString()+ "]");
            producer.send(message);
            
            connection.close();
            
            //getConnectionResponse();
            
        } catch (Exception e) {
            System.out.println("Exception occurred: " + e.toString());
            System.exit(1);
        }
    }

    /*TO COMMENT IF NOT WORKING AND IN ON MESSAGE LISTENER TOO*/
    public void getConnectionResponse() {
        ConnectionFactory connectionFactory = new com.sun.messaging.ConnectionFactory();
        Connection connection = null;
        try {
            connection = connectionFactory.createConnection();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Queue queue = new com.sun.messaging.Queue(QUEUE_NAME);
            MessageConsumer consumer = session.createConsumer(queue);
            MessageListener listener = new TraderManager();
            consumer.setMessageListener(listener);
            connection.start();
            synchronized (listener) {
                listener.wait();
            }
        } catch (Exception e) {
            System.out.println("Exception occurred: " + e.toString());
            System.exit(1);
        }
    }

    /**
     * Deconnection from the Market using the P2P
     *
     * @param name
     */
    public void deconnection(String name) {
        this.name = name;
        SessionState ss = SessionState.Disconnected;
        SessionStatusRequest ssr = new SessionStatusRequest(name, ss);
        ConnectionFactory connectionFactory = new com.sun.messaging.ConnectionFactory();
        Connection connection = null;

        try {
            
            connection = connectionFactory.createConnection();
            Queue queue = new com.sun.messaging.Queue(QUEUE_NAME);
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageProducer producer = session.createProducer(queue);
            ObjectMessage message = session.createObjectMessage(ssr);
            System.out.println("Sending order [" + message.getObject().toString()+ "]");
            producer.send(message);
            
            connection.close();
            
        } catch (Exception e) {
            System.out.println("Exception occurred: " + e.toString());
            System.exit(1);
        }
    }
    
    /**
     * Connection to JMS for the point to point when a buying or a selling happen
     */
    public void clientPTP(String type, int stockID) {
        TradingTransactionType tty; 
        if (type.equals("buy")) {
            tty = TradingTransactionType.Buy;
        }
        else {
            tty = TradingTransactionType.Sell;
        }
        
        StockProduct p = new StockProduct(uGUI.getTrader().getMyStock().get(stockID).getStockName());
        TradingTransaction tt = new TradingTransaction(tty, p, uGUI.getQtty(), uGUI.getTrader().getName());
        
        ConnectionFactory connectionFactory = new com.sun.messaging.ConnectionFactory();
        Connection connection = null;
        try {
            connection = connectionFactory.createConnection();
            Queue queue = new com.sun.messaging.Queue(QUEUE_NAME);
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageProducer producer = session.createProducer(queue);
            ObjectMessage message = session.createObjectMessage(tt);
            System.out.println("Sending order [" + message.getObject().toString()+ "]");
            producer.send(message);
            
            connection.close();
            
        } catch (Exception e) {
            System.out.println("Exception occurred: " + e.toString());
            System.exit(1);
        }
    }
    
    
    @Override
    public void onMessage(Message msg) {
        Object order;
        try {
            order = ((ObjectMessage) msg).getObject();
            SessionStatusResponse ssr = (SessionStatusResponse) order;    
            System.out.println(name+" is connected: "+ssr.isIsAcknowledged());
            if (ssr.isIsAcknowledged()) {
                connected = true;
                synchronized (this) { this.notify(); }
            }
        } catch (JMSException ex) {
            System.out.println("Problem with message P2P");
        }
    }


    public UserGUI getuGUI() {
        return uGUI;
    }

    public void setuGUI(UserGUI uGUI) {
        this.uGUI = uGUI;
    }

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }
    
    
    
}
