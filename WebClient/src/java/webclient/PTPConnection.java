/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webclient;

import javax.jms.*;
import trading.SessionStatusRequest;
import trading.TradingTransaction;

/**
 * Creates a PTP connection to send messages to the market
 * @author Jerome
 */
public class PTPConnection{
    
    protected PTPConnection(){}
    
    protected static String queueName;
    protected static final String QUEUE_NAME = "Trader";
    /**
     * Creates a PTP connection to send a buy or sell order to the market
     * @param tt the transaction information to send to the market
     */
    public static void sendOrder(TradingTransaction tt) {
        
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
        
        /**
         * Creates a PTP connection to connect or disconnect to the market
         * @param ssr the state of connection sent to the market
         */
        public static void sendStatusRequest(SessionStatusRequest ssr){
        
            ConnectionFactory connectionFactory = new com.sun.messaging.ConnectionFactory();
            Connection connection = null;
            try {
                connection = connectionFactory.createConnection();
                Queue queue = new com.sun.messaging.Queue(QUEUE_NAME);
                Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
                MessageProducer producer = session.createProducer(queue);
                ObjectMessage message = session.createObjectMessage(ssr);
                System.out.println("Sending connection request [" + message.getObject().toString()+ "]");
                producer.send(message);

                connection.close();

            } catch (Exception e) {
                System.out.println("Exception occurred: " + e.toString());
                System.exit(1);
            }
        }
        
//        public static boolean getConnexionResponse(){
//            ConnectionFactory connectionFactory = new com.sun.messaging.ConnectionFactory();
//            Connection connection = null;
//            try {
//                connection = connectionFactory.createConnection();
//                Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
//                Queue queue= new com.sun.messaging.Queue(queueName);
//                MessageConsumer consumer = session.createConsumer(queue);
//                MessageListener listener = new PTPConnection();
//                consumer.setMessageListener(listener);
//                connection.start();
//            synchronized (listener) { listener.wait(); }
//            connection.close();
//            } catch (Exception e) {
//            System.out.println("Exception occurred: " + e.toString()); System.exit(1);
//            }
//            return WebAppData.getConnected();
//    }
//    
//    @Override
//    public void onMessage(Message msg) {
//        Object order;
//        try {
//            order = ((ObjectMessage) msg).getObject();
//            SessionStatusResponse ssr = (SessionStatusResponse) order; 
//            System.out.println("hello");
//            if (ssr.isIsAcknowledged()) {
//                WebAppData.setConnected(true);
//                this.notify();
//            }
//            else if(!ssr.isIsAcknowledged()){
//                WebAppData.setConnected(false);
//                this.notify();
//            }
//        } catch (JMSException ex) {
//            System.out.println("Problem with message P2P");
//        }
//    }
}
