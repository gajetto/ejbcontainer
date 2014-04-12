/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import dataTransferObjects.UserDTO;
import entities.User;
import java.sql.Connection;
import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.sql.DataSource;

/**
 *
 * @author Sohaila.Baset
 */
@Resource(name = "jdbc/MockStockDB")
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
    public boolean registerUser(UserDTO userDTO) {
        try {
            ds = (javax.sql.DataSource) ctx.lookup("jdbc/MockStockDB");
            cn = ds.getConnection();
            cn.setAutoCommit(false);
            insertUser(GetEntitiyFromDTO(userDTO));
            return true;

        } catch (Exception ex) {
            return false;
        }

    }

    public void insertUser(User entity) {
        manager.persist(entity);

    }

    private User GetEntitiyFromDTO(UserDTO userDTO) {
        User entity = new User();
        entity.setFirstName(userDTO.getFirstName());
        entity.setLastName(userDTO.getLastName());
        entity.setUserName(userDTO.getUserName());
        entity.setdOB(userDTO.getDateOfBirth());
        entity.setPassword(userDTO.getPassword());
        entity.setIsAdmin(userDTO.isIsAdmin());

        return entity;

    }
}
