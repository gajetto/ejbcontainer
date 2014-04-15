/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.jms.Message;
import beans.MessageBean;
import java.util.ArrayList;
import market12.StockProduct;

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

    ArrayList<StockProduct> mess;
    private static SingletonBean speb = null;

    public ArrayList<StockProduct> getMess() {
        return mess;
    }

    
    public static SingletonBean getInstance() {
        if (speb == null) {
            speb = new SingletonBean();
        }
        return speb;
    }
    
    public void setMess(ArrayList<StockProduct> mess) {
        this.mess = mess;
    }

    }

