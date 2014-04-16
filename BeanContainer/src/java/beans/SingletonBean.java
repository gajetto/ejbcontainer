/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import dataTransferObjects.StockProductDTO;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import java.util.ArrayList;

/**
 *
 * @author Sohaila & Ludovic
 */
@Singleton
@LocalBean
public class SingletonBean {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    protected SingletonBean() {

    }

    private ArrayList<StockProductDTO> stockProducts = new ArrayList<>();
    private static SingletonBean instance = null;

    public ArrayList<StockProductDTO> getMess() {
        return getStockProducts();
    }

    
    public static SingletonBean getInstance() {
        if (instance == null) {
            instance = new SingletonBean();
        }
        return instance;
    }
    
    public void setStockProducts(ArrayList<StockProductDTO> newStockProducts) {
        this.stockProducts = newStockProducts;
    }

    /**
     * @return the stockProducts
     */
    public ArrayList<StockProductDTO> getStockProducts() {
        return stockProducts;
    }

}

