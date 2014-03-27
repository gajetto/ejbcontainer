/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package trading;

/**
 *
 * @author Sohaila.Baset
 */
public class SessionStatusResponse {
    private boolean isAcknowledged;

    
    public SessionStatusResponse(boolean isAcknowledged)
    {
    this.isAcknowledged=isAcknowledged;
    }
    
    
    /**
     * @return the isAcknowledged
     */
    public boolean isIsAcknowledged() {
        return isAcknowledged;
    }

    /**
     * @param isAcknowledged the isAcknowledged to set
     */
    public void setIsAcknowledged(boolean isAcknowledged) {
        this.isAcknowledged = isAcknowledged;
    }
}
