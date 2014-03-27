/*
 * StockLine.java
 *
 * Created on 10 fevrier 2007, 18:55
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package trading;

import java.io.Serializable;

/**
 * Contient les informations d'un titre. 
 */
public class StockProduct implements Serializable{
    private String stockName;
    private int stockID;
    private Double stockPrice = new Double(0);
    private int stockQty = 0;
    private double stockResult = 0.0;
    
    /**
     * Creates a new instance of StockLine
     */
    public StockProduct(String nameValue) {
        stockName = nameValue;
    }
    
    public String getStockName() {
        return stockName;
    }
    
    public void setStockName(String stockName) {
        this.stockName = stockName;
    }
    
    public int getStockID() {
        return stockID;
    }
    
    public void setStockID(int stockID) {
        this.stockID = stockID;
    }
    
    public java.lang.Double getStockPrice() {
        return stockPrice;
    }
    
    public void setStockPrice(Double stockPrice) {
        this.stockPrice = stockPrice;
    }
    
    public int getStockQty() {
        return stockQty;
    }
    
    public void setStockQty(int stockQty) {
        this.stockQty = stockQty;
        
    }
    
    public double getResult() {
        return stockResult;
    }
    
    public void setResult(double result) {
        this.stockResult = result;
    }
    
    public StockProduct clone(){
        StockProduct result = new StockProduct(this.stockName);
        result.setStockID(this.stockID);
        result.setStockPrice(this.stockPrice);
        result.setStockQty(this.stockQty);
        return (result);
    }
}
