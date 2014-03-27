package trading;

/*
 * StockService is a class that provides the action of the market
 */

import trading.StockProduct;
import java.util.ArrayList;

/**
 *
 */
public class StockService {
    
    private ArrayList<StockProduct> daList;
    /** Creates a new instance of StockService */
    public StockService() {
        setDaList(new ArrayList());
        StockProduct thisStockPdt = new StockProduct("Sun");
        thisStockPdt.setStockID(1);
        getDaList().add(thisStockPdt);
        thisStockPdt = new StockProduct("Apple");
        thisStockPdt.setStockID(2);       
        getDaList().add(thisStockPdt);
        thisStockPdt = new StockProduct("IBM");
        thisStockPdt.setStockID(3);
        getDaList().add(thisStockPdt);
    }
    
    /**
     * Function to get the list of market's action.
     * @return ArrayList
     */
    public ArrayList getDaList() {
        return daList;
    }
    
    /**
     * Function to set the list of market's action
     * @param daList 
     */
    public void setDaList(ArrayList daList) {
        this.daList = daList;
    }
}
