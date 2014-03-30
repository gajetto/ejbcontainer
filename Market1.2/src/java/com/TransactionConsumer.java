/*
 * TransactionConsumer.java
 *
 * Created on 6. novembre 2007, 15:10
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com;

import GUI.MockStockServerGUI;
import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author Alexandre Metrailler
 */
public class TransactionConsumer implements MessageListener, Runnable{
    
    private MockStockServerGUI gui;
    
    /**
     * Creates a new instance of TransactionConsumer
     * Cette classe permer de se connecter a la Queue de messages
     * Cette classe s'occupe de recuperer les transactions (buy-sell) des clients
     * Et de la mise a jour de l'interface du serveur
     */
    public TransactionConsumer(MockStockServerGUI gui) {
        this.gui = gui;
        
    }
    
    public void run() {
        try {
            //recherche de la ConnectionFactory et de la Queue dans l'annuaire JNDI du serveur d'application
            InitialContext ic = new InitialContext();
            ConnectionFactory connectionFactory = (ConnectionFactory) ic.lookup("jms/QueueFactory");
            Queue queue = (Queue) ic.lookup("jms/transaction");
            //connection au provider de messages via l'objet javax.jms.Connection ...
            Connection connection = connectionFactory.createConnection();
            // ... pour en obtenir une session
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            //a partir de la session on cre un MessageConsumer sur la Queue
            MessageConsumer consumer = session.createConsumer(queue);
            //on associe un listener au consumer
            consumer.setMessageListener(this);
            //on demarre la connection
            connection.start();
            //fait attendre le thread pour la reception de messages asynchrone
            synchronized(this){
                this.wait();
            }
        }catch (Exception e) {
            System.out.println("Exception occurred: " + e.toString()); System.exit(1);
        }
    }

    /**
     * Methode appelee lorsqu'un nouveau message arrive dans la Queue
     */    
    public void onMessage(Message message) {
        try {
            //Recupere les informations su le titre qui sont stockees dans les proprietes du message
            String trStockId = message.getStringProperty("trStockId");
            String trQuantity = message.getStringProperty("trQuantity");
            String trType = message.getStringProperty("trType");
            String trUser = message.getStringProperty("trUser");
            String trStockName = message.getStringProperty("trStockName");
            //Mise a jour de l'interface du serveur
            gui.update("User " + trUser + " " + trType + " " + trQuantity + " stocks " + trStockName + " (" + trStockId + ")");
        } catch (JMSException ex) {
            ex.printStackTrace();
        }
    }
    
    
    
}
