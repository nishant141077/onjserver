/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * Dashboard.java
 *
 * Created on Jan 7, 2016, 1:26:47 PM
 */
package gui;

import config.Configuration;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.spec.SecretKeySpec;
import management.Security;
import management.UserManagement;
import socket.Connector;

/**
 *
 * @author nishant
 */
public class Dashboard extends javax.swing.JFrame {

    /** Creates new form Dashboard */
    public Dashboard() {
        initComponents();
        initialize();
    }

    public void initialize() {
        welcomeLabel.setText("Welcome " + Configuration.HANDLE);
    }
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLayeredPane1 = new javax.swing.JLayeredPane();
        startServerButton = new javax.swing.JButton();
        welcomeLabel = new javax.swing.JLabel();
        announceButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBounds(new java.awt.Rectangle(450, 250, 400, 300));

        startServerButton.setFont(new java.awt.Font("Ubuntu", 0, 12));
        startServerButton.setText("Start server");
        startServerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startServerButtonActionPerformed(evt);
            }
        });
        startServerButton.setBounds(303, 10, 79, 27);
        jLayeredPane1.add(startServerButton, javax.swing.JLayeredPane.DEFAULT_LAYER);

        welcomeLabel.setFont(new java.awt.Font("Ubuntu", 1, 15));
        welcomeLabel.setBounds(10, 10, 280, 20);
        jLayeredPane1.add(welcomeLabel, javax.swing.JLayeredPane.DEFAULT_LAYER);

        announceButton.setText("Announce");
        announceButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                announceButtonActionPerformed(evt);
            }
        });
        announceButton.setBounds(130, 90, 130, 30);
        jLayeredPane1.add(announceButton, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void startServerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startServerButtonActionPerformed
    Configuration.desKey = new SecretKeySpec(Configuration.encryptionKey, 0, 
            Configuration.encryptionKey.length, "DES");
    Connector connector = new Connector();
    connector.startServer();
}//GEN-LAST:event_startServerButtonActionPerformed

private void announceButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_announceButtonActionPerformed
// TODO add your handling code here:
//    new UserManagement().makeAnnouncement();
}//GEN-LAST:event_announceButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new Dashboard().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton announceButton;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JButton startServerButton;
    private javax.swing.JLabel welcomeLabel;
    // End of variables declaration//GEN-END:variables
}
