/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import dataTransferObjects.UserDTO;
import ejb.TradingRemote;
import ejb.TradingRemote;
import entities.UserMockStock;
import java.sql.*;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.sql.DataSource;
import javax.*;
import javax.ejb.TransactionAttribute;

/**
 *
 * @author Sohaila.Baset
 */
@Resource(name = "jdbc/MockStockDB", type = javax.sql.DataSource.class)
@Stateless
public class TradingBean implements TradingRemote {

    private DataSource ds;
    private Connection cn;
    @Resource
    SessionContext ctx;

    @PersistenceContext
    EntityManager manager;

    @Override
    public boolean authenticateUser(String username, String hashedPassword) {
        Query query = manager.createQuery("SELECT u FROM UserMockStock u WHERE u.userName = '"+username+"' ");
        List<UserMockStock> users = query.getResultList();
        if(users.isEmpty()){
            return false;
        }else{
            return(users.get(0).getPassword().equals(hashedPassword));
        }
    }

    @Override
    @TransactionAttribute(javax.ejb.TransactionAttributeType.REQUIRES_NEW)
    public boolean registerUser(UserDTO userDTO) {
        try {
            insertUser(GetEntitiyFromDTO(userDTO));
//            ds = (javax.sql.DataSource) ctx.lookup("jdbc/MockStockDB");
//            cn = ds.getConnection();
//            cn.setAutoCommit(false);
            return true;

        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }

    }

    public void insertUser(UserMockStock entity) {
        manager.persist(entity);
    }

    private UserMockStock GetEntitiyFromDTO(UserDTO userDTO) {
        UserMockStock entity = new UserMockStock();
        entity.setFirstName(userDTO.getFirstName());
        entity.setLastName(userDTO.getLastName());
        entity.setUserName(userDTO.getUserName());
        entity.setdOB(userDTO.getDateOfBirth());
        entity.setPassword(userDTO.getPassword());
        entity.setIsAdmin(userDTO.isIsAdmin());

        return entity;

    }
    
    private UserDTO GetDTOFromEntity(UserMockStock entity){
        return new UserDTO(entity.getUserName(), entity.getFirstName(), entity.getLastName(), entity.getdOB(), entity.getPassword(), entity.isIsAdmin());
    }

    @Override
    public UserDTO getUser(String username) {
        Query query = manager.createQuery("SELECT u FROM UserMockStock u WHERE u.userName = '"+username+"' ");
        List<UserMockStock> users = query.getResultList();
        if(users.isEmpty()){
            return null;
        }else{
            
            return GetDTOFromEntity(users.get(0));
        }
    }

    @Override
    public boolean updateUser(UserDTO userDTO) {
        System.out.println("update");
        return true;
    }

    @Override
    public List<UserDTO> searchUsers(String username) {
        String q = "SELECT u FROM UserMockStock u WHERE u.userName LIKE '%"+username+"%' ";
        List<UserDTO> usersDTO = new ArrayList<UserDTO>();
        
        Query query = manager.createQuery(q);
        List<UserMockStock> users = query.getResultList();
        if(!users.isEmpty()){
            for (UserMockStock entity : users) {
                UserDTO userDTO = new UserDTO(entity.getUserName(), entity.getFirstName(), entity.getLastName(), entity.getdOB(), entity.getPassword(), entity.isIsAdmin());
                usersDTO.add(userDTO);
            }            
        }
        return usersDTO;
    }
}
