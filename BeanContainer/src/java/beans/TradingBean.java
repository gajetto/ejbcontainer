/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import dataTransferObjects.UserDTO;
import entities.UserMockStock;
import java.sql.*;
import java.sql.Connection;
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
    public boolean authenticateUser(UserDTO userDTO) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    @TransactionAttribute(javax.ejb.TransactionAttributeType.REQUIRES_NEW)
    public boolean registerUser(UserDTO userDTO) {
        System.out.println("hola");
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
}
