/*
 * StockService.java
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
import com.*;
import java.util.ArrayList;
import java.util.Iterator;
/**
 *
 */
public class StockService {
    
    private ArrayList<StockProduct> stockList;
    private MarketPublisher pub;
    
    /**
     * Cree la liste de titres composee de StockProducts
     */
    public StockService(MarketPublisher pub) {
        this.pub = pub;
        setStockList(new ArrayList());
        
        StockProduct thisStockPdt = new StockProduct("Sun");
        thisStockPdt.setStockID("1");
        getStockList().add(thisStockPdt);
        
        thisStockPdt = new StockProduct("Apple");
        thisStockPdt.setStockID("2");
        getStockList().add(thisStockPdt);
        
        thisStockPdt = new StockProduct("IBM");
        thisStockPdt.setStockID("3");
        getStockList().add(thisStockPdt);
        
        // AJOUT: passe la liste au publisher qui s'occupera de la gerer.
        pub.setStockList(getStockList());
    }
    
    public ArrayList getStockList() {
        return stockList;
    }
    
    public void setStockList(ArrayList stockList) {
        this.stockList = stockList;
    }
    
}
