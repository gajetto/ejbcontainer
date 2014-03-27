/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package trading;

import java.io.Serializable;

/**
 *
 * @author Sohaila.Baset
 */
public class SessionStatusRequest implements Serializable{
    private String clientName;
    private SessionState requestedStatus;

    public SessionStatusRequest(String clientName,SessionState requestedStatus)
    {
    this.clientName=clientName;
    this.requestedStatus=requestedStatus;
    }
    
    
    /**
     * @return the clientName
     */
    public String getClientName() {
        return clientName;
    }

    /**
     * @param clientName the clientName to set
     */
    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    /**
     * @return the requestedStatus
     */
    public SessionState getRequestedStatus() {
        return requestedStatus;
    }

    /**
     * @param requestedStatus the requestedStatus to set
     */
    public void setRequestedStatus(SessionState requestedStatus) {
        this.requestedStatus = requestedStatus;
    }
}
