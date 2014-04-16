/*
 * joinMarket.java
 *
 * Created on 20 mars 2007, 10:59
 */
package trader;

import com.sun.messaging.jmq.util.MD5;
import dataTransferObjects.UserDTO;
import ejb.TradingRemote;
import java.util.ArrayList;
import java.awt.event.*;
import java.lang.String;
import javax.ejb.EJB;
import javax.swing.JOptionPane;


/**
 * Main Class for the trader part
 *
 */
public class JoinMarketGUI extends javax.swing.JFrame {
    
    @EJB
    private static TradingRemote tradingBean;
    
    private boolean isCreate = false;
    private String username;
    private String password;
    
    private UserGUI uGUI;
    private UserData ud;


    /**
     * Creates new form JoinMarketGUI
     */
    public JoinMarketGUI() {
        initComponents();
    }



    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        nameTextField = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPasswordField1 = new javax.swing.JPasswordField();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        nameTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                nameTextFieldKeyReleased(evt);
            }
        });

        jButton1.setText("Join Market");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jButton1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jButton1KeyReleased(evt);
            }
        });

        jLabel1.setText("Username");

        jLabel2.setText("Password");

        jPasswordField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jPasswordField1KeyReleased(evt);
            }
        });

        jButton2.setText("Sign Up");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jLabel1)
                    .add(jLabel2))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 30, Short.MAX_VALUE)
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                    .add(nameTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE)
                    .add(jPasswordField1))
                .add(35, 35, 35)
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                    .add(jButton2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(jButton1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .add(30, 30, 30))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .add(35, 35, 35)
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel1)
                    .add(nameTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(10, 10, 10)
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel2)
                    .add(jPasswordField1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jButton1))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(jButton2)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(8, 8, 8)
                .add(jPanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(37, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        
        if (!nameTextField.getText().trim().equals("") && !jPasswordField1.getPassword().equals("")) {
            username = nameTextField.getText().trim();
            password = new String(jPasswordField1.getPassword());
            String hashedPassword = MD5.getHashString(password);

            if (tradingBean.authenticateUser(username, hashedPassword)) {
                System.out.println("Connect existe DB");
                UserDTO user = tradingBean.getUser(username);
                if (user != null) {
                   ud = new UserData();
                   ud.setUser(user);
                   ud.newStockService();
                   
                   uGUI = new UserGUI(ud, tradingBean);
                   this.setVisible(false);
                   new JoinMarketGUI().setVisible(true);
                }
                else {
                    nameTextField.setText("");
                    jPasswordField1.setText("");
                    JOptionPane.showMessageDialog(this, "An error occurs. Try again.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            else {
                nameTextField.setText("");
                jPasswordField1.setText("");
                JOptionPane.showMessageDialog(this, "Error username or password is wrong", "Error", JOptionPane.ERROR_MESSAGE);
            }

            


        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void nameTextFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nameTextFieldKeyReleased
        
    }//GEN-LAST:event_nameTextFieldKeyReleased

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        Formulaire f = new Formulaire(tradingBean, this);     
        f.setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton1KeyReleased
        
    }//GEN-LAST:event_jButton1KeyReleased

    private void jPasswordField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPasswordField1KeyReleased
         int key = evt.getKeyCode();
        if (key == KeyEvent.VK_ENTER) {
            if (!nameTextField.getText().trim().equals("") && !jPasswordField1.getPassword().equals("")) {
            username = nameTextField.getText().trim();
            password = new String(jPasswordField1.getPassword());
            String hashedPassword = MD5.getHashString(password);

            if (tradingBean.authenticateUser(username, hashedPassword)) {
                System.out.println("Connect existe DB");
                UserDTO user = tradingBean.getUser(username);
                if (user != null) {
                   ud = new UserData();
                   ud.setUser(user);
                   ud.newStockService();
                   
                   uGUI = new UserGUI(ud, tradingBean);
                   this.setVisible(false);
                   new JoinMarketGUI().setVisible(true);
                }
                else {
                    nameTextField.setText("");
                    jPasswordField1.setText("");
                    JOptionPane.showMessageDialog(this, "An error occurs. Try again.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            else {
                nameTextField.setText("");
                jPasswordField1.setText("");
                JOptionPane.showMessageDialog(this, "Error username or password is wrong", "Error", JOptionPane.ERROR_MESSAGE);
            }

            


        }
        }
    }//GEN-LAST:event_jPasswordField1KeyReleased

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {

                new JoinMarketGUI().setVisible(true);

            }
        });

    }

    public void setIsCreate(boolean isCreate) {
        this.isCreate = isCreate;
    }

    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JTextField nameTextField;
    // End of variables declaration//GEN-END:variables

}
