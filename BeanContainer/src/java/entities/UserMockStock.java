/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author Sohaila.Baset
 */
@Entity
@Table(name = "UserMockStock")
public class UserMockStock implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "UserId", nullable = false)    
    private int id;
    
    @Column(name = "UserName", unique = true)
    private String userName;
    
    @Column(name = "FirstName")
    private String firstName;
    
    @Column(name = "LastName")
    private String lastName;
    
    @Temporal(javax.persistence.TemporalType.DATE)
    @Column(name = "DOB")
    private Date dOB;
    
    @Column(name = "Password")
    private String password;
    
    @Column(name = "IsAdmin")
    private boolean isAdmin;
    
    @OneToMany (cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<TransactionMockStock> transactionsMockStock;
    
    @OneToMany (cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<StockProduct> stockProducts;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
//        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserMockStock)) {
            return false;
        }
        UserMockStock other = (UserMockStock) object;
//        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
//            return false;
//        }
        return true;
    }

    @Override
    public String toString() {
        return "beans.User[ id=" + id + " ]";
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
     * @return the dOB
     */
    public Date getdOB() {
        return dOB;
    }

    /**
     * @param dOB the dOB to set
     */
    public void setdOB(Date dOB) {
        this.dOB = dOB;
    }

    /**
     * @return the transactionsMockStock
     */
    public List<TransactionMockStock> getTransactionsMockStock() {
        return transactionsMockStock;
    }

    /**
     * @param transactionsMockStock the transactionsMockStock to set
     */
    public void setTransactionsMockStock(List<TransactionMockStock> transactionsMockStock) {
        this.transactionsMockStock = transactionsMockStock;
    }

    /**
     * @return the stockProducts
     */
    public List<StockProduct> getStockProducts() {
        return stockProducts;
    }

    /**
     * @param stockProducts the stockProducts to set
     */
    public void setStockProducts(List<StockProduct> stockProducts) {
        this.stockProducts = stockProducts;
    }
    
}
