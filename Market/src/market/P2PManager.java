/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package market;

import trading.TradingTransaction;
import com.sun.messaging.Queue;
import com.sun.messaging.jmq.jmsclient.ConsumerReader;
import java.awt.Color;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.QueueConnectionFactory;
import javax.jms.Session;
import trading.SessionState;
import trading.SessionStatusRequest;
import trading.SessionStatusResponse;
import trading.TradingTransactionType;

/**
 *
 * @author Sohaila.Baset
 */
public class P2PManager implements MessageListener{
    
    private MarketGUI gui=null;
    private Connection connection=null;
    private MessageConsumer consumer=null;
    private  MessageProducer producer=null;
    public static final StockService service = new StockService();

        
    public P2PManager(MarketGUI gui)
    {                  
      this.gui = gui;
    }

       
    public void initialize()
    {
        try {
            connection=QueueConnectionManager.getInstance().getConnection();
            consumer=QueueConnectionManager.getInstance().getConsumer();
            MessageListener listener= new P2PManager(gui);
            consumer.setMessageListener(listener);
            connection.start();
            this.gui.updateEventList("P2P  Manager started....",Color.BLUE);
            synchronized (listener) {try { 
                listener.wait();
                } catch (InterruptedException ex) {
                    Logger.getLogger(P2PManager.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (JMSException ex) {
            this.gui.updateEventList("An error occured while starting P2P Manager!",Color.RED);
            Logger.getLogger(P2PManager.class.getName()).log(Level.SEVERE, null, ex);
        }
            
    }
    
    public void onMessage(javax.jms.Message message) 
    {
        Object request=null;
        TradingTransaction tt=null;
        SessionStatusRequest ssreq=null;
        SessionStatusResponse ssres=null;
        // try to recieve the msg from JMS
        try{
                request = ((ObjectMessage) message).getObject();
        }
        catch (JMSException ex)
        {
        this.gui.updateEventList("Client request was not understood!",Color.RED);
         Logger.getLogger(P2PManager.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        // Now try first to cast it to a TradingTransaction object
        try{
                    tt= (TradingTransaction) request;
                    System.out.println(GetOrderText(tt));
                    this.gui.updateEventList(GetOrderText(tt),Color.DARK_GRAY); 
   
            }
        catch(ClassCastException e)// this mean the message we recieved was a SessionStatusRequest
        {
            try // this mean the message we recieved was a SessionStatusRequest
            {
                ssreq=(SessionStatusRequest) request;
                ssres=new SessionStatusResponse(processSessionRequest(ssreq));
                ObjectMessage om= QueueConnectionManager.getInstance().getSession().createObjectMessage(ssres);
                producer= QueueConnectionManager.getInstance().getProducer();
                producer.send(om);
            } catch (JMSException ex) {
                this.gui.updateEventList("Client request was not aknowledged!",Color.RED);
                Logger.getLogger(P2PManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
   
}
   

    private String GetOrderText(TradingTransaction tt) {
        String type = tt.getType()== TradingTransactionType.Buy? "buying" : "selling";
       return String.format("Request from client: %s for %s %s of %s stocks has been acknowledged.",tt.getClientName(), type, tt.getQty(), tt.getProduct().getStockName());
    }
    
    private boolean processSessionRequest(SessionStatusRequest ssreq)
    {
        if(ssreq.getRequestedStatus()==SessionState.Disconnected)
        {
            Main.removeFromConnectedClients(ssreq.getClientName());
            this.gui.RemoveFromClientList(ssreq.getClientName());
            return true;
        }
        else if(ssreq.getRequestedStatus()==SessionState.Connected) // client is asking to connect
        {
          if( !Main.getConnectedClients().contains(ssreq.getClientName()))
          {
            Main.appendConnectedClients(ssreq.getClientName());
            this.gui.AppendClientList(ssreq.getClientName());
            return true;
          }
        
        }
        return false;
    }
}
    

