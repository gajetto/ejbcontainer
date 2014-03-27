/*
 * Trader.java
 *
 * Created on November 8, 2007, 5:03 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package trader;

import java.util.ArrayList;
import trading.StockProduct;
import trading.StockService;

/**
 * Trader object
 */
public class Trader {
    
    private String name;
    private StockService marketPrices = new StockService();
    private ArrayList<StockProduct> myStock;
    
    /**
     * Creates a new instance of Trader
     */
    public Trader(String name) {
        this.name = name;
        initiateMyStock();
    }
    
    /**
     * methode appelee par le UserGUI lors de l'achat ou la vente
     */
    public void update(int qtty, int stockID, String type, ArrayList marketPricesUpdate){
        
        if(type.equals("buy")){//si l'utilisateur achete
            //recupere le prix d'achat
            double price = ((StockProduct)marketPricesUpdate.get(stockID)).getStockPrice();
            //insere dans myStock la quantite du titre achete
            ((StockProduct) myStock.get(stockID)).setStockQty(((StockProduct) myStock.get(stockID)).getStockQty() + qtty);
            //insere dans myStock le prix auquel le titre a ete achete
            ((StockProduct) myStock.get(stockID)).setStockPrice(price);
            
        }else if(type.equals("sell")){//si l'utilisateur vend
            if (qtty <= ((StockProduct) myStock.get(stockID)).getStockQty()){//verifie si il possede la quantite qu'il veut vendre
                //soustrait la quantite vendue a la quantite dans myStock
                ((StockProduct) myStock.get(stockID)).setStockQty(((StockProduct) myStock.get(stockID)).getStockQty() - qtty);
                //calcule l'ajustement a faire au resultat en fonction du prix d'achat et du prix de vente du titre
                double adjust = ((qtty* ((StockProduct)marketPricesUpdate.get(stockID)).getStockPrice()))-((qtty* ((StockProduct) myStock.get(stockID)).getStockPrice()));
                //fait l'ajustement
                ((StockProduct) myStock.get(stockID)).setResult(((StockProduct) myStock.get(stockID)).getResult() + Math.round(adjust));
                //enregistre le dernier prix d'achat
                ((StockProduct) myStock.get(stockID)).setStockPrice(((StockProduct)marketPricesUpdate.get(stockID)).getStockPrice());
            }
        }
    }

    /**
     * Get the name of the trader
     * @return String
     */
    public String getName() {
        return name;
    }
    
    /**
     * Get the list of stocks
     * @return ArrayList
     */
    public ArrayList<StockProduct> getMyStock() {
        return myStock;
    }
    
//    
//    public String getText(int stockID){
//        String text = "";
//        text = ((StockProduct) myStock.get(stockID)).getStockName()+ " (Qtty " + ((StockProduct) myStock.get(stockID)).getStockQty() + ") "+ "Result(" + ((StockProduct) myStock.get(stockID)).getResult() + ")";
//        return text;
//    }
    
     /**
      * Get the quantity of the stocks with the id of the stocks
      * @param stockID int
      * @return String
      */
    public String getTextQuantity(int stockID){
        String text = "";
        text = " "+ ((StockProduct) myStock.get(stockID)).getStockQty();
        return text;
    }
    
    /**
      * Get the result of the stocks with the id of the stocks
      * @param stockID int
      * @return String
      */
    public String getTextResult(int stockID){
        String text = "";
        text = " "+ ((StockProduct) myStock.get(stockID)).getResult();
        return text;
    }
    
    /**
      * Get the total result of the trader
      * @return Double
      */
    public Double getTotalResult(){
        double total = new Double(0);
        for(StockProduct j : myStock){
            total = total + j.getResult();}
        return total;
    }
    
    /**
     * Initialize the stocks for the view
     */
    public void initiateMyStock(){
        myStock = new ArrayList();
        ArrayList<StockProduct> serviceList = marketPrices.getDaList();
        for(StockProduct j: serviceList){
            myStock.add(j.clone());
        }
    }
}
