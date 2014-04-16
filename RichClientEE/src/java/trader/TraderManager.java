/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package trader;

import dataTransferObjects.StockProductDTO;
import dataTransferObjects.TransactionDTO;
import dataTransferObjects.UserDTO;
import ejb.TradingRemote;
import java.util.Date;
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
import trading.TradingTransaction;
import trading.TradingTransactionType;

/**
 *
 * @author Manixab
 */
public class TraderManager { //implements MessageListener {

    private final  String QUEUE_NAME = "Trader";

    private UserData ud;
    private TradingRemote tradingBean;
    private UserGUI userGUI;
    
    public TraderManager(UserData ud, TradingRemote tradingBean, UserGUI userGUI) {
        this.ud = ud;
        this.tradingBean = tradingBean;
        this.userGUI = userGUI;
    }


    /**
     * Connection to JMS for the point to point when a buying or a selling happen
     */
    public void connection(String name) {
        name = ud.getUser().getUserName();
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

    /**
     * Connection to JMS for the point to point when a buying or a selling happen
     */
    public void clientPTP(String type, int stockID, int stockNumber) {
        TradingTransactionType tty; 
        String username = ud.getUser().getUserName();
        UserDTO user = ud.getUser();
        boolean isBuy = false;
        
        if (type.equals("buy")) {
            tty = TradingTransactionType.Buy;
            isBuy = true;
        }
        else {
            tty = TradingTransactionType.Sell;
        }
        
        StockProductDTO stock = (StockProductDTO) ud.getCurrentStocksPrices().get(stockID);
        Date now = new Date();
        TransactionDTO transaction = new TransactionDTO(stockNumber, stock.getStockPrice(), now, isBuy, stockID, user.getUserId());
        user.addTransaction(transaction);
        user.update(stockNumber, stockID, (isBuy?"buy":"sell"), ud.getCurrentStocksPrices());
        ud.setUser(user);
        tradingBean.sendTransactionOrder(transaction, user);
        try {
            tradingBean.addUserTransaction(transaction, user);
        }
        catch (Exception e) {
            
        }
        userGUI.updateTransactions();
    }
    
}
