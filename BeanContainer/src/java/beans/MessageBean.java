/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.*;
import beans.SingletonBean;
import java.lang.Exception;
import javax.ejb.EJBException;
import market12.StockProduct;
import java.util.ArrayList;
/**
 *
 * @author Jerome & Ludovic
 */
@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic"),
    @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "jms/destinationTopic"),
    @ActivationConfigProperty(propertyName = "clientId", propertyValue = "jms/destinationTopic"),
    @ActivationConfigProperty(propertyName = "subscriptionDurability", propertyValue = "Durable"),
    @ActivationConfigProperty(propertyName = "subscriptionName", propertyValue = "jms/destinationTopic")
})

public class MessageBean implements MessageListener {

    StockProduct stockSingle;
    StockProduct paramessage;
    SingletonBean spb;

    public MessageBean() {
        spb.getInstance();
    }

    
    @Override
    public void onMessage(Message message) {
        try {
            ObjectMessage objectMessage = (ObjectMessage) message;

            ArrayList<StockProduct> messageList = (ArrayList) objectMessage.getObject();
            
            spb.setMess(messageList);
            synchronized (this) {
                this.notify();
            }
        } catch (JMSException e) {
            System.out.println("A JMS error has occured...");
        } catch (EJBException e) {
            System.out.println("An EJB exception has occured ...");
        }
    }
}
