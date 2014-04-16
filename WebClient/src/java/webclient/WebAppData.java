/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package webclient;

import dataTransferObjects.StockProductDTO;
import dataTransferObjects.UserDTO;
import java.util.ArrayList;


/**
 * Stores data that can stay persistent
 * @author Jerome
 */
public class WebAppData {
    protected static ArrayList<StockProductDTO> historyStocks = new ArrayList<>();
    protected static UserDTO user;
    private static ArrayList<StockProductDTO> currentStocksPrices = new ArrayList<>();
    protected static boolean editOther = false;
    protected static UserDTO userToModifyByAdmin;
        
    /**
     * Get the history of all stocks during the webclient life
     * @return 
     */
    public static ArrayList<StockProductDTO> getHistoryStocks() {
        return historyStocks;
    }
    
    /**
     * Set the history of stocks
     * @param newStocks list of stocks
     */
    public static void sethistoryStocks(ArrayList<StockProductDTO> newStocks) {
        historyStocks = newStocks;
    }
        
    /**
     * Add stocks to the top of the history stocks
     * @param newStocks a list of new received stocks
     */
    public static void addStocks(ArrayList<StockProductDTO> newStocks){
        ArrayList<StockProductDTO> temp = new ArrayList<>();
        temp.addAll(newStocks);
        temp.addAll(historyStocks);
        historyStocks = temp;
    }
    
    /**
     * Instanciates a new trader
     * @param name the name of the new Trader
     */
    public static void setUser(UserDTO newUser){
        user = newUser;
    }
    
    /**
     * Gets the Trader instance
     * @return the trader
     */
    public static UserDTO getUser(){
        return user;
    }

    
    public static void setEditOther(boolean isEditOther){
        editOther = isEditOther;
    }
    
    public static boolean getEditOther(){
        return editOther;
    }
    
    public static void setUserToModifyByAmin(UserDTO user){
        userToModifyByAdmin = user;
    }
    
    public static UserDTO getUserToModifyByAdmin(){
        return userToModifyByAdmin;
    }

    /**
     * @return the currentStocksPrices
     */
    public static ArrayList<StockProductDTO> getCurrentStocksPrices() {
        return currentStocksPrices;
    }

    /**
     * @param aCurrentStocksPrices the currentStocksPrices to set
     */
    public static void setCurrentStocksPrices(ArrayList<StockProductDTO> aCurrentStocksPrices) {
        currentStocksPrices = aCurrentStocksPrices;
    }

}
