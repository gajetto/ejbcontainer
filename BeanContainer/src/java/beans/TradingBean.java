/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import dataTransferObjects.StockPriceDTO;
import dataTransferObjects.StockProductDTO;
import dataTransferObjects.TransactionDTO;
import dataTransferObjects.UserDTO;
import ejb.TradingRemote;
import ejb.TradingRemote;
import entities.StockProduct;
import entities.TransactionMockStock;
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
    private SingletonBean sb = SingletonBean.getInstance();
    
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
            insertUser(getEntitiyFromDTO(userDTO));
//            ds = (javax.sql.DataSource) ctx.lookup("jdbc/MockStockDB");
//            cn = ds.getConnection();
//            cn.setAutoCommit(false);
            return true;

        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }

    }
    
    @Override
    public ArrayList<StockPriceDTO> getLastStocks(){
        return sb.getStockProducts();
    }

    public void insertUser(UserMockStock entity) {
        manager.persist(entity);
    }

    private UserMockStock getEntitiyFromDTO(UserDTO userDTO) {
        UserMockStock entity = new UserMockStock();
        entity.setFirstName(userDTO.getFirstName());
        entity.setLastName(userDTO.getLastName());
        entity.setUserName(userDTO.getUserName());
        entity.setdOB(userDTO.getDateOfBirth());
        entity.setPassword(userDTO.getPassword());
        entity.setIsAdmin(userDTO.isIsAdmin());
        entity.setTransactionsMockStock(getEntityListFromTransactionDTOList(userDTO.getTransactionList()));
        entity.setStockProducts(getEntityListFromStockProductDTOList(userDTO.getMyStock()));

        return entity;

    }
    
    private TransactionDTO getDTOFromEntity(TransactionMockStock entity){
        return new TransactionDTO(entity.getId(), entity.getQty(), entity.getStockPrice(), entity.getTransactionDate(), entity.isIsBuy(), entity.getStockId());
    }
    
    private StockProductDTO getDTOFromEntity(StockProduct entity){
        return new StockProductDTO(entity.getId(), entity.getStockPrice(), entity.getStockQty(), entity.getStockResult());
    }
    
    private List<TransactionDTO> getTransactionDTOListFromEntityList(List<TransactionMockStock> entities){
        List<TransactionDTO> transactionsList = new ArrayList<>();
        for (TransactionMockStock entity : entities) {
            TransactionDTO transaction = getDTOFromEntity(entity);
            transactionsList.add(transaction);
        }
        return transactionsList;
    }
    
    private List<TransactionMockStock> getEntityListFromTransactionDTOList(List<TransactionDTO> transactionsDTO){
        List<TransactionMockStock> entities = new ArrayList<>();
        for (TransactionDTO transaction : transactionsDTO) {
            TransactionMockStock entity = getEntityFromDTO(transaction);
            entities.add(entity);
        }
        return entities;
    }
    
     private List<StockProduct> getEntityListFromStockProductDTOList(List<StockProductDTO> productsDTO){
        List<StockProduct> entities = new ArrayList<>();
        for (StockProductDTO product : productsDTO) {
            StockProduct entity = getEntityFromDTO(product);
            entities.add(entity);
        }
        return entities;
    }
     private List<StockProductDTO> getStockProductDTOListFromEntityList(List<StockProduct> stocks){
         List<StockProductDTO> entities = new ArrayList<>();
         for (StockProduct stock : stocks) {
             StockProductDTO entity = getDTOFromEntity(stock);
             entities.add(entity);
         }
         return entities;
     }
     
     private StockProduct getEntityFromDTO(StockProductDTO productDTO){
         StockProduct product = new StockProduct();
         product.setStockId(productDTO.getStockID());
         product.setStockPrice(productDTO.getStockPrice());
         product.setStockQty(productDTO.getStockQty());
         product.setStockResult(productDTO.getResult());
         return product;
     }
     
     private TransactionMockStock getEntityFromDTO(TransactionDTO transactionDTO){
         TransactionMockStock transaction = new TransactionMockStock();
         transaction.setStockId(transactionDTO.getStockID());
         transaction.setIsBuy(transactionDTO.isIsBuy());
         transaction.setQty(transactionDTO.getQty());
         transaction.setStockPrice(transactionDTO.getStockPrice());
         transaction.setTransactionDate(transactionDTO.getTransactionDate());
         return transaction;
     }
    
    private UserDTO getDTOFromEntity(UserMockStock entity){
        return new UserDTO(entity.getUserName(), entity.getFirstName(), entity.getLastName(), entity.getdOB(), entity.getPassword(), entity.isIsAdmin(), getTransactionDTOListFromEntityList(entity.getTransactionsMockStock()), getStockProductDTOListFromEntityList(entity.getStockProducts()));
    }

    @Override
    public UserDTO getUser(String username) {
        Query query = manager.createQuery("SELECT u FROM UserMockStock u WHERE u.userName = '"+username+"' ");
        List<UserMockStock> users = query.getResultList();
        if(users.isEmpty()){
            return null;
        }else{
            
            return getDTOFromEntity(users.get(0));
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
                UserDTO userDTO = new UserDTO(entity.getUserName(), entity.getFirstName(), entity.getLastName(), entity.getdOB(), entity.getPassword(), entity.isIsAdmin(), getTransactionDTOListFromEntityList(entity.getTransactionsMockStock()), getStockProductDTOListFromEntityList(entity.getStockProducts()));
                usersDTO.add(userDTO);
            }            
        }
        return usersDTO;
    }

    @Override
    public boolean deleteUser(UserDTO userDTO) {
        System.out.println("delete");
        return true;
    }
}
