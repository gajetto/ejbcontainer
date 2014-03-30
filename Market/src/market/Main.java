/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package market;

import java.util.ArrayList;
import trading.MarketStatusNotification;

public class Main {
    
    private static MarketGUI marketGui=null;
    private static PublicationManager pm=null;
    private static P2PManager tm=null;
    private static ArrayList<String> connectedClients=null;
    public static MarketService service=null;
    private static MarketStatusNotification statusNotification=null;
    
     /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        connectedClients= new ArrayList();
        marketGui= new MarketGUI();
        marketGui.setVisible(true);
        pm=new PublicationManager(marketGui);
        tm=new P2PManager(marketGui);
        initialize();
    }
    
public static void initialize()
{
    service= new MarketService(marketGui, pm, tm);
    service.initializeMarket();
}

    public static void openMarket()
    {
        statusNotification=new MarketStatusNotification(MarketStatus.Open, 0);
        pm.startTimer();
        pm.SendStatusNotification(statusNotification);
         
    }   
    
    public static void closeMarket()
    {
        statusNotification=new MarketStatusNotification(MarketStatus.Closed, 0);
        pm.stopTimer();
        pm.SendStatusNotification(statusNotification);
    }
    
        public static void sendClosingReminder()
    {
        statusNotification=new MarketStatusNotification(MarketStatus.Closed, 5);
        pm.SendStatusNotification(statusNotification);
        }

    /**
     * @return the connectedClients
     */
    public static ArrayList<String> getConnectedClients() {
        return connectedClients;
    }

       public static void appendConnectedClients(String newClient) {
    connectedClients.add(newClient);
    }
    public static void removeFromConnectedClients(String newClient) {
    connectedClients.remove(newClient);
}
     
    }
