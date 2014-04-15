/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Jerome
 */
@Entity
@Table(name = "StockProduct")
public class StockProduct implements Serializable {
    private static long serialVersionUID = 1L;

    /**
     * @return the serialVersionUID
     */
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    /**
     * @param aSerialVersionUID the serialVersionUID to set
     */
    public static void setSerialVersionUID(long aSerialVersionUID) {
        serialVersionUID = aSerialVersionUID;
    }
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "id", nullable = false)    
    private int id;
    
    @Column(name = "stockId", nullable = false)
    private int stockId;
    
    @Column (name = "stockPrice")
    private double stockPrice;
    
    @Column (name = "stockQty")
    private int stockQty;
    
    @Column (name = "stockResult")
    private double stockResult;
    
    @ManyToOne
    @JoinColumn(name = "UserMockStock")
    private UserMockStock userMockStock;

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the stockId
     */
    public int getStockId() {
        return stockId;
    }

    /**
     * @param stockId the stockId to set
     */
    public void setStockId(int stockId) {
        this.stockId = stockId;
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
     * @return the stockQty
     */
    public int getStockQty() {
        return stockQty;
    }

    /**
     * @param stockQty the stockQty to set
     */
    public void setStockQty(int stockQty) {
        this.stockQty = stockQty;
    }

    /**
     * @return the stockResult
     */
    public double getStockResult() {
        return stockResult;
    }

    /**
     * @param stockResult the stockResult to set
     */
    public void setStockResult(double stockResult) {
        this.stockResult = stockResult;
    }

    /**
     * @return the userMockStock
     */
    public UserMockStock getUserMockStock() {
        return userMockStock;
    }

    /**
     * @param userMockStock the userMockStock to set
     */
    public void setUserMockStock(UserMockStock userMockStock) {
        this.userMockStock = userMockStock;
    }
}
