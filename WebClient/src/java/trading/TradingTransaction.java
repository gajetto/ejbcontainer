/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package trading;

import dataTransferObjects.StockProductDTO;
import java.io.Serializable;

/**
 *
 * @author Sohaila.Baset
 */
public class TradingTransaction implements Serializable{
    
    private TradingTransactionType type;
    private StockProductDTO product;
    private int qty;
    private String clientName;

    
    public TradingTransaction(TradingTransactionType type,StockProductDTO product,int qty,String clientName)
    {
        this.product=product;
        this.qty=qty;
        this.type=type;
        this.clientName=clientName;
    }
    
    
    /**
     * @return the type
     */
    public TradingTransactionType getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(TradingTransactionType type) {
        this.type = type;
    }

    /**
     * @return the product
     */
    public StockProductDTO getProduct() {
        return product;
    }

    /**
     * @param product the product to set
     */
    public void setProduct(StockProductDTO product) {
        this.product = product;
    }

    /**
     * @return the qty
     */
    public int getQty() {
        return qty;
    }

    /**
     * @param qty the qty to set
     */
    public void setQty(int qty) {
        this.qty = qty;
    }

    /**
     * @return the clientName
     */
    public String getClientName() {
        return clientName;
    }

    /**
     * @param clientName the clientName to set
     */
    public void setClientName(String clientName) {
        this.clientName = clientName;
    }
}


