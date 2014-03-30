/*
 * PPriceEvolution.java
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
import java.awt.event.ActionListener;

import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Cet objet va faire evoluer les prix des titres du marche.
 */
public class PPriceEvolution {
    
    private ArrayList stockList;
    final int MSGT_CHANGE_PRICE = 6;
    final double PROBA_EVO = 0.05;//Probabilité que la hausse ou la baisse d'un titre soit exceptionnelle'
    final double EXCEPTIONAL_EVO_RATE = 0.5;//taux de hausse/baisse exceptionnelle
    final double NORMAL_EVO_RATE = 0.1;//taux de hausse/baisse exceptionnelle
    final double PROBA_UP = 0.475;//Si random supérieur à PROBA_UP => hausse
    final double PROBA_DOWN = 0.575;//Si random inférieur à PROBA_DOWN => baisse
    final double PRICE_ROOF = 0.5;//Prix minimal d'un titre'
    
    /** Creates a new instance of PriceEvolution */
    public PPriceEvolution(ArrayList stockList) {   
        this.setStockList(stockList);
    }
    
    /**
     * cette methode modifie les prix des titres du marche
     * elle est appelee par le MarketPublisher
     */
    public void run() {
        //javax.swing.ListModel jListListModel;
        boolean sendThis = false;
        double newPrice = 0;
        if (getStockList() != null){
            java.util.Iterator it = getStockList().iterator();
            while(it.hasNext()){
                java.util.Random randomizer;
                StockProduct thisStock = (StockProduct) it.next();
                double proba ;
                randomizer = new Random();
                proba=randomizer.nextDouble();
                if (thisStock.getStockPrice().doubleValue()<=(new Double(0.5)).doubleValue()){
                    newPrice = Math.round(new Double(randomizer.nextDouble()).doubleValue()*100);
                    sendThis = true;
                }else {
                    double coef=0;
                    if (randomizer.nextDouble()<=PROBA_EVO){
                        coef = EXCEPTIONAL_EVO_RATE;
                    } else {
                        coef = NORMAL_EVO_RATE;
                    }
                    if (proba >= PROBA_UP ) {//hausse
                        newPrice = Math.round((thisStock.getStockPrice().doubleValue()*(new Double(1).doubleValue()+new Double(randomizer.nextDouble()*coef).doubleValue())));
                        sendThis = true;
                    } else if ((proba <= PROBA_DOWN) && (thisStock.getStockPrice().doubleValue()>=(new Double(PRICE_ROOF)).doubleValue())){//Baisse seulement si la valeur du titre est superieur a 0.5
                        newPrice = Math.round((thisStock.getStockPrice().doubleValue()*(new Double(1).doubleValue()-new Double(randomizer.nextDouble()*coef).doubleValue())));
                        if (newPrice <=PRICE_ROOF){
                            sendThis=false;
                        }else{
                            sendThis = true;
                        }
                    } else {//pas de changement de prix
                        sendThis = false;
                    }
                }
                if (sendThis){
                    //Modification de prix
                    try {
                        thisStock.setStockPrice(newPrice);
                    }catch (Exception e){
                        System.out.println("Problem to set new price stock ID " + thisStock.getStockID() + " price " + newPrice);
                    }
                }
                sendThis=false;
            } 
        }
    }
    
    public ArrayList getStockList() {
        return stockList;
    }
    
    public void setStockList(ArrayList stockList) {
        this.stockList = stockList;
    }
    
}
