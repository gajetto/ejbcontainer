/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package beans;

import dataTransferObjects.UserDTO;
import javax.ejb.Local;
import javax.ejb.Remote;

/**
 *
 * @author Sohaila.Baset
 */
@Local
public interface TradingRemote {
    
    public boolean authenticateUser(UserDTO userDTO);
    public boolean registerUser(UserDTO userDTO);    
    
}
