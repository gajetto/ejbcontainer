/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package webclient;

import trading.Trader;
import trading.StockService;
import java.util.ArrayList;
import trading.StockProduct;

/**
 * Stores data that can stay persistent
 * @author Jerome
 */
public class WebAppData {
    protected static ArrayList<StockProduct> historyStocks = new ArrayList<>();
    protected static Trader trader;
    protected static StockService currentStocks;
    protected static boolean connected;
        
    /**
     * Get the history of all stocks during the webclient life
     * @return 
     */
    public static ArrayList<StockProduct> getHistoryStocks() {
        return historyStocks;
    }
    
    /**
     * Set the history of stocks
     * @param newStocks list of stocks
     */
    public static void sethistoryStocks(ArrayList<StockProduct> newStocks) {
        historyStocks = newStocks;
    }
        
    /**
     * Add stocks to the top of the history stocks
     * @param newStocks a list of new received stocks
     */
    public static void addStocks(ArrayList<StockProduct> newStocks){
        ArrayList<StockProduct> temp = new ArrayList<>();
        temp.addAll(newStocks);
        temp.addAll(historyStocks);
        historyStocks = temp;
    }
    
    /**
     * Instanciates a new trader
     * @param name the name of the new Trader
     */
    public static void newTrader(String name){
        trader = new Trader(name);
    }
    
    /**
     * Gets the Trader instance
     * @return the trader
     */
    public static Trader getTrader(){
        return trader;
    }
    
    /**
     * Update the trader with an unpdated trader
     * @param updatedTrader the updated trader to store
     */
    public static void updateTrader(Trader updatedTrader){
        trader = updatedTrader;
    }
    
    /**
     * Instanciates a StockService
     */
    public static void newStockService(){
        currentStocks = new StockService();
    }
    
    /**
     * Gets a StockService
     * @return the StockService
     */
    public static StockService getStockService(){
        return currentStocks;
    }
    
    /**
     * Set the StockServices' stock list to a new list
     * @param newStocks the new Stock list to set
     */
    public static void setStockServiceList(ArrayList newStocks){
        currentStocks.setDaList(newStocks);
    }
    
    public static void setConnected(boolean isConnected){
        connected = isConnected;
    }
    
    public static boolean getConnected(){
        return connected;
    }

}
