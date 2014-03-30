/*
 * StockProduct.java
 *
 * Created on October 29, 2007, 5:20 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package market12;

/**
 *
 * @author Alexandre Metrailler
 */
import java.io.Serializable;
import java.lang.Double;

/**
 * Contient les informations d'un titre.
 */
public class StockProduct implements Serializable {
    private String stockName;
    private int stockID;
    private Double stockPrice = new Double(0);
    
    /**
     * Creates a new instance of StockProduct
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
}
