/*
 * MarketPublisher.java
 *
 * Created on October 29, 2007, 5:24 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com;


import javax.annotation.*;
import javax.jms.*;
import javax.naming.InitialContext;
import GUI.MockStockServerGUI;
import java.util.ArrayList;
import java.util.Iterator;
import javax.naming.NamingException;
import market12.*;

/**
 *
 * @author Alexandre Metrailler
 */
public class MarketPublisher {
    
    private ArrayList stockList = null;
    private PPriceEvolution pE;
    private MockStockServerGUI gui;
    
    /**
     * Creates a new instance of MarketPublisher
     * Cette classe permer de se connecter au Topic
     * Cette classe s'occupe de faire varier le cours des actions contenues dans stockList
     * Et egalement de publier les cours dans le Topic
     */
    public MarketPublisher(MockStockServerGUI gui) {
        this.gui = gui;
        
    }
    
    /**
     * Appele par main pour lancer le serveur qui publie les cours des actions
     */
    public void publish(){
        try {
            //recherche de la ConnectionFactory et du Topic dans l'annuaire JNDI du serveur d'application
            InitialContext ic = new InitialContext();
            ConnectionFactory connectionFactory = (ConnectionFactory) ic.lookup("jms/TopicPublisher");
            Topic destinationTopic = (Topic) ic.lookup("jms/destinationTopic");
            //connection au provider de messages via l'objet javax.jms.Connection ...
            Connection connection = connectionFactory.createConnection();
            // ... pour en obtenir une session
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            //a partir de la session on cre un MessageProducer qui dans notre cas sera un TopicPublisher
            MessageProducer producer = (MessageProducer) session.createProducer(destinationTopic);
            //a partir de la session on cre un ObjectMessage
            ObjectMessage message = session.createObjectMessage();
            while(true){
                //on fait evoluer les cours des actions avec run() du PPriceEvolution
                pE.run();
                //on affecte au message la liste des actions mise a jour par pE.run()
                message.setObject(stockList);
                //on envoie le message dans le Topic
                producer.send(message);
                System.out.println("Message envoye");
                //on met a jour l'interface graphique
                print();
                //on fait patienter 10 secondes le producteur de messages avant de recommancer le while
                synchronized(this) {
                    try {
                        this.wait(10000);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
            }
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * Appele par StockService pour lui passer la liste des actions
     */
    public void setStockList(ArrayList stockList) {
        this.stockList = stockList;
        pE = new PPriceEvolution(stockList);
    }
    
    
    /**
     * Met a jour l'interface MockStockServerGUI
     */
    public void print(){
        String text = "";
        Iterator it = stockList.iterator();
        while (it.hasNext()){
            StockPriceDTO stock = (StockPriceDTO) it.next();
            text += stock.getStockName() + ": " + stock.getStockPrice() + "\n";
        }
        gui.updatePrice(text);
    }
}
