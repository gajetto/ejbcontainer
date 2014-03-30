/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package trading;

import java.io.Serializable;
import market.MarketStatus;

/**
 *
 * @author Sohaila.Baset
 */
public class MarketStatusNotification implements Serializable {
    
    private MarketStatus newStatus=null;
    private int countDown=0; // in minutes

    
    public MarketStatusNotification(MarketStatus newStatus, int countDown)
    {
        this.newStatus=newStatus;
        this.countDown=countDown;
    }
    /**
     * @return the newStatus
     */
    public MarketStatus getNewStatus() {
        return newStatus;
    }

    /**
     * @param newStatus the newStatus to set
     */
    public void setNewStatus(MarketStatus newStatus) {
        this.newStatus = newStatus;
    }

    /**
     * @return the countDown
     */
    public int getCountDown() {
        return countDown;
    }

    /**
     * @param countDown the countDown to set
     */
    public void setCountDown(int countDown) {
        this.countDown = countDown;
    }
    
}
