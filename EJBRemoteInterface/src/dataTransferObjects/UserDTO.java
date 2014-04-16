/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dataTransferObjects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Jerome
 */
public class UserDTO implements Serializable{
    private int userId;
    private String userName;
    private String firstName;
    private String lastName;
    private Date dateOfBirth;
    private String password;
    private boolean isAdmin;
    private List<TransactionDTO> transactionList;
    private List<StockProductDTO> myStock;

    public UserDTO(int userId, String userName, String firstName, String lastName, Date dateOfBirth, String password, boolean isAdmin, List<TransactionDTO> transactionList, List<StockProductDTO> products) {
        this.userId = userId;
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.password = password;
        this.isAdmin = isAdmin;
        this.transactionList = transactionList;
        this.myStock = products;
    }
    
    public UserDTO(String userName, String firstName, String lastName, Date dateOfBirth, String password, boolean isAdmin, List<TransactionDTO> transactionList, List<StockProductDTO> products) {
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.password = password;
        this.isAdmin = isAdmin;
        this.transactionList = transactionList;
        this.myStock = products;
    }
    
     /**
     * 
     * @param qtty
     * @param stockID
     * @param type
     * @param marketPricesUpdate 
     */
    public void update(int qtty, int stockID, String type, ArrayList marketPricesUpdate){

        if(type.equals("buy")){//si l'utilisateur achete
            //recupere le prix d'achat
            double price = ((StockProductDTO)marketPricesUpdate.get(stockID)).getStockPrice();
            //insere dans myStock la quantite du titre achete
            ((StockProductDTO) getMyStock().get(stockID)).setStockQty(((StockProductDTO) getMyStock().get(stockID)).getStockQty() + qtty);
            //insere dans myStock le prix auquel le titre a ete achete
            ((StockProductDTO) getMyStock().get(stockID)).setStockPrice(price);
            
        }else if(type.equals("sell")){//si l'utilisateur vend
            if (qtty <= ((StockProductDTO) getMyStock().get(stockID)).getStockQty()){//verifie si il possede la quantite qu'il veut vendre
                //soustrait la quantite vendue a la quantite dans myStock
                ((StockProductDTO) getMyStock().get(stockID)).setStockQty(((StockProductDTO) getMyStock().get(stockID)).getStockQty() - qtty);
                //calcule l'ajustement a faire au resultat en fonction du prix d'achat et du prix de vente du titre
                double adjust = ((qtty* ((StockProductDTO)marketPricesUpdate.get(stockID)).getStockPrice()))-((qtty* ((StockProductDTO) getMyStock().get(stockID)).getStockPrice()));
                //fait l'ajustement
                ((StockProductDTO) getMyStock().get(stockID)).setResult(((StockProductDTO) getMyStock().get(stockID)).getResult() + Math.round(adjust));
                //enregistre le dernier prix d'achat
                ((StockProductDTO) getMyStock().get(stockID)).setStockPrice(((StockProductDTO)marketPricesUpdate.get(stockID)).getStockPrice());
            }
        }
    }

    /**
     * @return the userId
     */
    public int getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return the dateOfBirth
     */
    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     * @param dateOfBirth the dateOfBirth to set
     */
    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the isAdmin
     */
    public boolean isIsAdmin() {
        return isAdmin;
    }

    /**
     * @param isAdmin the isAdmin to set
     */
    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    /**
     * @return the transactionList
     */
    public List<TransactionDTO> getTransactionList() {
        return transactionList;
    }

    /**
     * @param transactionList the transactionList to set
     */
    public void setTransactionList(List<TransactionDTO> transactionList) {
        this.transactionList = transactionList;
    }

    /**
     * @return the myStock
     */
    public List<StockProductDTO> getMyStock() {
        return myStock;
    }

    /**
     * @param myStock the myStock to set
     */
    public void setMyStock(List<StockProductDTO> myStock) {
        this.myStock = myStock;
    }
    
    public void addTransaction(TransactionDTO transaction){
        this.transactionList.add(transaction);
    }
}
