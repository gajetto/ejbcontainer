/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ejb;

import dataTransferObjects.StockProductDTO;
import dataTransferObjects.UserDTO;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Sohaila.Baset
 */
@Remote
public interface TradingRemote {
    
    public boolean authenticateUser(String username, String hashedPassword);
    public boolean registerUser(UserDTO userDTO);
    public UserDTO getUser(String username);
    public boolean updateUser(UserDTO userDTO);
    public List<UserDTO> searchUsers(String username);
    public boolean deleteUser(UserDTO userDTO);
    public ArrayList<StockProductDTO> getLastStocks();
    
}
