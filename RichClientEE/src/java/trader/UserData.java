/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package trader;

import dataTransferObjects.StockProductDTO;
import dataTransferObjects.StockServiceDTO;
import dataTransferObjects.UserDTO;
import java.util.ArrayList;

/**
 *
 * @author Manixab
 */
public class UserData {
    protected ArrayList<StockProductDTO> historyStocks = new ArrayList();
    protected UserDTO user;
    protected StockServiceDTO currentStocks;
    private ArrayList<StockProductDTO> currentStocksPrices = new ArrayList();
    protected boolean editOther = false;
    protected UserDTO userToModifyByAdmin;
        
    /**
     * Get the history of all stocks during the webclient life
     * @return 
     */
    public ArrayList<StockProductDTO> getHistoryStocks() {
        return historyStocks;
    }
    
    /**
     * Set the history of stocks
     * @param newStocks list of stocks
     */
    public void sethistoryStocks(ArrayList<StockProductDTO> newStocks) {
        historyStocks = newStocks;
    }
        
    /**
     * Add stocks to the top of the history stocks
     * @param newStocks a list of new received stocks
     */
    public void addStocks(ArrayList<StockProductDTO> newStocks){
        ArrayList<StockProductDTO> temp = new ArrayList();
        temp.addAll(newStocks);
        temp.addAll(historyStocks);
        historyStocks = temp;
    }
    
    /**
     * Instanciates a new trader
     * @param name the name of the new Trader
     */
    public void setUser(UserDTO newUser){
        user = newUser;
    }
    
    /**
     * Gets the Trader instance
     * @return the trader
     */
    public UserDTO getUser(){
        return user;
    }
    
    /**
     * Instanciates a StockService
     */
    public void newStockService(){
        currentStocks = new StockServiceDTO();
    }
    
    /**
     * Gets a StockService
     * @return the StockService
     */
    public StockServiceDTO getStockService(){
        return currentStocks;
    }
    
    /**
     * Set the StockServices' stock list to a new list
     * @param newStocks the new Stock list to set
     */
    public void setStockServiceList(ArrayList newStocks){
        currentStocks.setDaList(newStocks);
    }
    
    public void setEditOther(boolean isEditOther){
        editOther = isEditOther;
    }
    
    public boolean getEditOther(){
        return editOther;
    }
    
    public void setUserToModifyByAmin(UserDTO user){
        userToModifyByAdmin = user;
    }
    
    public UserDTO getUserToModifyByAdmin(){
        return userToModifyByAdmin;
    }

    /**
     * @return the currentStocksPrices
     */
    public ArrayList<StockProductDTO> getCurrentStocksPrices() {
        return currentStocksPrices;
    }

    /**
     * @param aCurrentStocksPrices the currentStocksPrices to set
     */
    public void setCurrentStocksPrices(ArrayList<StockProductDTO> aCurrentStocksPrices) {
        currentStocksPrices = aCurrentStocksPrices;
    }

}
