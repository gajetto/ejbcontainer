/*
 * StockService.java
 *
 * Created on 20 mars 2007, 17:32
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package dataTransferObjects;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 */
public class StockServiceDTO implements Serializable{
    
    private ArrayList<StockProductDTO> daList;
    /** Creates a new instance of StockService */
    public StockServiceDTO() {
        setDaList(new ArrayList());
        StockProductDTO thisStockPdt = new StockProductDTO("Sun");
        thisStockPdt.setStockID(0);
        getDaList().add(thisStockPdt);
        thisStockPdt = new StockProductDTO("Apple");
        thisStockPdt.setStockID(1);       
        getDaList().add(thisStockPdt);
        thisStockPdt = new StockProductDTO("IBM");
        thisStockPdt.setStockID(2);
        getDaList().add(thisStockPdt);
    }
    
    public ArrayList getDaList() {
        return daList;
    }
    
    public void setDaList(ArrayList daList) {
        this.daList = daList;
    }
}
