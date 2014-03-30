/*
 * PriceEvolution.java
 *
 * Created on 12 f�vrier 2007, 06:44
 *
 */

package market;


import trading.StockProduct;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.Random;
import java.util.TimerTask;


/**
 * Cet objet va faire �voluer les prix des titres du march�. 
 */
public class PPriceEvolution extends TimerTask implements Serializable {
    
    private PublicationManager pm;
    private ArrayList stockList;
    private javax.swing.JList jListEvent;
    private LogList logList;
    final int MSGT_CHANGE_PRICE = 6;
    final double PROBA_EVO = 0.05;//Probabilit� que la hausse ou la baisse d'un titre soit exceptionnelle'
    final double EXCEPTIONAL_EVO_RATE = 0.5;//taux de hausse/baisse exceptionnelle
    final double NORMAL_EVO_RATE = 0.1;//taux de hausse/baisse normale
    final double PROBA_UP = 0.475;//Si random sup�rieur � PROBA_UP => hausse
    final double PROBA_DOWN = 0.575;//Si random inf�rieur � PROBA_DOWN => baisse
    final double PRICE_ROOF = 0.5;//Prix minimal d'un titre'
    
    /** Creates a new instance of PriceEvolution */
    public PPriceEvolution(PublicationManager pm, ArrayList pdtList) {
        javax.swing.ListModel jListListModel;
        
        this.pm = pm;
        
        String newLine;
        this.logList = new LogList();
        this.logList.add("Application ready to listen for messages !");
        
        this.jListEvent = jListEvent;
        //On r�cup�re la liste des titres � la premi�re instantification
        
        this.setStockList(pdtList);
        
        
        //Chargement dans la JTable
//        jListEvent.setListData(this.logList.toArray());
    }
    
    /**
     * cette m�thode modifie les prix des titres du march�
     * Elle est appel� par un Timer
     */
    public void run() {
        
        this.logList.addReverse("*************New Prices Are Generated*************\n");
        javax.swing.ListModel jListListModel;
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
                    } else if ((proba <= PROBA_DOWN) && (thisStock.getStockPrice().doubleValue()>=(new Double(PRICE_ROOF)).doubleValue())){//Baisse seulement si la valeur du titre est sup�rieur � 0.5
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
                        //String newLine = "Message sends to set "+thisStock.getStockID()+" new price : "+newPrice;
                        String newLine = "Message sent to set "+thisStock.getStockName()+" new price : "+newPrice;
                        this.logList.addReverse(newLine);
                   
                    }catch (Exception e){
                        String newLine = "Problem sending message to set "+thisStock.getStockID()+" new price.";
                        this.logList.addReverse(newLine);
                    }
                }
                sendThis=false;
            }
            System.out.println(this.logList.toString());
            this.pm.publishPrices(this.getStockList());
        }
    }
    
    public ArrayList getStockList() {
        return stockList;
    }
    
    public void setStockList(ArrayList stockList) {
        this.stockList = stockList;
    }
    
    public LogList getLogList() {
        return logList;
    }
    
    public void setLogList(LogList logList) {
        this.logList = logList;
    }
}
