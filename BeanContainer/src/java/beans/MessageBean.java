/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package beans;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.*;
import beans.SingletonPriceEvolutionBean;
import java.lang.Exception;

/**
 *
 * @author Jerome
 */
@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic"),
    @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "jms/destinationTopic"),
    @ActivationConfigProperty(propertyName = "clientId", propertyValue = "jms/destinationTopic"),
    @ActivationConfigProperty(propertyName = "subscriptionDurability", propertyValue = "Durable"),
    @ActivationConfigProperty(propertyName = "subscriptionName", propertyValue = "jms/destinationTopic")
})
public class MessageBean implements MessageListener {
    
    
    
    public MessageBean() {
       
    }
    
    @Override
    public void onMessage(Message message) {
        try {
String StockPriceEvolution = ((TextMessage) message).getText();

        
            synchronized (this) { this.notify(); }
        System.out.println("message received : " + StockPriceEvolution );
    }
        catch (JMSException e){
            System.out.println("A JMS error has occured...");
        }
    
    }
}
