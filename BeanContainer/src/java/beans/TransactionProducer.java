/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package beans;

import dataTransferObjects.StockProductDTO;
import dataTransferObjects.StockServiceDTO;
import dataTransferObjects.TransactionDTO;
import dataTransferObjects.UserDTO;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.naming.InitialContext;

/**
 *
 * @author Jerome
 */
public class TransactionProducer {
    
    private static Message message;
    private static MessageProducer producer;
    
    public static void sendOrder(TransactionDTO transaction, UserDTO user) {
        
        try {
            StockServiceDTO ss = new StockServiceDTO();
            //recherche de la ConnectionFactory et de la Quote dans l'annuaire JNDI du serveur d'application
            InitialContext ic = new InitialContext();
            ConnectionFactory connectionFactory = (ConnectionFactory) ic.lookup("jms/QueueFactory");
            Queue queue = (Queue) ic.lookup("jms/transaction");
            //connection au provider de messages via l'objet javax.jms.Connection ...
            Connection connection = connectionFactory.createConnection();
            // ... pour en obtenir une session
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            //a partir de la session on cre un MessageProducer qui dans notre cas sera un MessageProducer (Queue)
            producer = session.createProducer(queue);
            //a partir de la session on cre un Message
            message = session.createMessage();
            
            message.setStringProperty("trStockId", String.valueOf(transaction.getStockID()));
            message.setStringProperty("trQuantity", String.valueOf(transaction.getQty()));
            message.setStringProperty("trType", (transaction.isIsBuy()?"buys":"sells"));
            message.setStringProperty("trUser", user.getUserName());
            message.setStringProperty("trStockName", ((StockProductDTO)ss.getDaList().get(transaction.getStockID())).getStockName());
            producer.send(message);
            System.out.println("Sending order ");
            
            connection.close();
            
        } catch (Exception e) {
            System.out.println("Exception occurred: " + e.toString());
            System.exit(1);
        }
    }
}
