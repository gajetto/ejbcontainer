/*
 * Main.java
 *
 * Created on October 29, 2007, 5:15 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package market12;

/**
 *
 * @author Alexandre Metrailler
 */
import GUI.*;
import com.*;
import javax.annotation.Resource;
import javax.jms.*;

public class Main {

    /** Creates a new instance of Main */
    public Main() {
    }
    
    public static void main(String[] args) {
        MockStockServerGUI gui = new MockStockServerGUI();          //Cree l'interface du serveur
        MarketPublisher pub = new MarketPublisher(gui);             //Cree le publisher (serveur)
        StockService service = new StockService(pub);               //Cree la liste des actions
        TransactionConsumer consumer = new TransactionConsumer(gui);//Cree le consommateur
        //Lance un nouveau thread pour la reception asyncrone de messages provenant du client web 
        new Thread(consumer).start();
        gui.setVisible(true);                                       //affiche l'interface du serveur
        pub.publish();                                              //lance le publisher
    }   
}
