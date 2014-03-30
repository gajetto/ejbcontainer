/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package market;

import java.awt.Color;
import trading.MarketStatusNotification;

/**
 *
 * @author Sohaila.Baset
 */
public  class MarketService{

    private MarketGUI gui=null;
    private PublicationManager pm=null;
    private P2PManager tm=null;
    private static MarketStatus marketStatus;
    private static MarketStatusNotification statusNotification=null;

    public MarketService(MarketGUI gui,PublicationManager pm,P2PManager tm) {

        this.gui = gui;
        this.pm = pm;
        this.tm = tm;
        
        intitialize();
    }

    private void intitialize()
    {
        this.gui.updateEventList("Market started!!", Color.BLUE);
        pm.initialize();
        tm.initialize();
       

     }

    /**
     * @return the pm
     */
    public PublicationManager getPm() {
        return pm;
    }
    
      public void openMarket()
    {
        statusNotification=new MarketStatusNotification(MarketStatus.Open, 0);
        pm.startTimer();
        SendStatusNotification(statusNotification);
         
    }   
    
    public void closeMarket()
    {
        statusNotification=new MarketStatusNotification(MarketStatus.Closed, 0);
        pm.stopTimer();
        SendStatusNotification(statusNotification);
    }
    
     public void initializeMarket()
    {
        try{
            setMarketStatus(MarketStatus.Open);
        }
        catch(Exception e)
        {
        setMarketStatus(MarketStatus.Closed);
        }
    }
     
     public void SendStatusNotification(MarketStatusNotification statusNotification)
     {}

    /**
     * @return the marketStatus
     */
    public static MarketStatus getMarketStatus() {
        return marketStatus;
    }

    /**
     * @param aMarketStatus the marketStatus to set
     */
    public static void setMarketStatus(MarketStatus aMarketStatus) {
        marketStatus = aMarketStatus;
    }
        

}
