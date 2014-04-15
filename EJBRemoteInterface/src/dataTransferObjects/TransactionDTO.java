/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dataTransferObjects;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Jerome
 */
public class TransactionDTO  implements Serializable {
    private int transactionID;
    private int qty;
    private double stockPrice;
    private Date transactionDate;
    private boolean isBuy;
    private int stockID;
    
    public TransactionDTO(int TransactionID, int qty, double stockPrice, Date transactionDate, boolean isBuy, int stockID){
        this.transactionID = transactionID;
        this.qty = qty;
        this.stockPrice = stockPrice;
        this.transactionDate = transactionDate;
        this.isBuy = isBuy;
        this.stockID = stockID;
    }

    /**
     * @return the transactionID
     */
    public int getTransactionID() {
        return transactionID;
    }

    /**
     * @param transactionID the transactionID to set
     */
    public void setTransactionID(int transactionID) {
        this.transactionID = transactionID;
    }

    /**
     * @return the number
     */
    public int getQty() {
        return qty;
    }

    /**
     * @param number the number to set
     */
    public void setQty(int number) {
        this.qty = number;
    }

    /**
     * @return the stockPrice
     */
    public double getStockPrice() {
        return stockPrice;
    }

    /**
     * @param stockPrice the stockPrice to set
     */
    public void setStockPrice(double stockPrice) {
        this.stockPrice = stockPrice;
    }

    /**
     * @return the transactionDate
     */
    public Date getTransactionDate() {
        return transactionDate;
    }

    /**
     * @param transactionDate the transactionDate to set
     */
    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    /**
     * @return the isBuy
     */
    public boolean isIsBuy() {
        return isBuy;
    }

    /**
     * @param isBuy the isBuy to set
     */
    public void setIsBuy(boolean isBuy) {
        this.isBuy = isBuy;
    }

    /**
     * @return the stockID
     */
    public int getStockID() {
        return stockID;
    }

    /**
     * @param stockID the stockID to set
     */
    public void setStockID(int stockID) {
        this.stockID = stockID;
    }
    
    
}
