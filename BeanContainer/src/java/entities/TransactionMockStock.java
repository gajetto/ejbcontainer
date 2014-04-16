/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author Sohaila.Baset
 */
@Entity
@Table
public class TransactionMockStock implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private int id;
    
    @ManyToOne
    @JoinColumn(name = "UserMockStock")
    private UserMockStock userMockStock;
    
    @Column(name = "stockId")
    private int stockId;
    
    @Column(name = "qty")
    private int qty;
    
    @Column(name = "stockPrice")
    private double stockPrice;
    
    @Temporal(javax.persistence.TemporalType.DATE)
    @Column(name = "transactionDate")
    private Date transactionDate;
    
    @Column(name = "isBuy")
    private boolean isBuy;
    
    @Column(name = "idTrader")
    private int idTrader;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        //hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TransactionMockStock)) {
            return false;
        }
        TransactionMockStock other = (TransactionMockStock) object;
//        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
//            return false;
//        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Transaction[ id=" + id + " ]";
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

//    /**
//     * @return the UserMockStock
//     */
//    public UserMockStock getUserMockStock() {
//        return UserMockStock;
//    }
//
//    /**
//     * @param UserMockStock the UserMockStock to set
//     */
//    public void setUserMockStock(UserMockStock UserMockStock) {
//        this.UserMockStock = UserMockStock;
//    }

//    /**
//     * @return the Stock
//     */
//    public Stock getStock() {
//        return Stock;
//    }
//
//    /**
//     * @param Stock the Stock to set
//     */
//    public void setStock(Stock Stock) {
//        this.Stock = Stock;
//    }

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
     * @return the idTrader
     */
    public int isIdTrader() {
        return idTrader;
    }

    /**
     * @param idTrader the idTrader to set
     */
    public void setIdTrader(int idTrader) {
        this.idTrader = idTrader;
    }
    
}
