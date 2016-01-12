/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * Register.java
 *
 * Created on Jan 6, 2016, 12:10:08 AM
 */
package gui;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import service.AdminService;

/**
 *
 * @author nishant
 */
public class Register extends javax.swing.JFrame {

    /** Creates new form Register */
    public Register() {
        initComponents();
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
        registrationFormLabel = new javax.swing.JLabel();
        nameLabel = new javax.swing.JLabel();
        nameField = new javax.swing.JTextField();
        genderLabel = new javax.swing.JLabel();
        handleLabel = new javax.swing.JLabel();
        handleField = new javax.swing.JTextField();
        passwordLabel = new javax.swing.JLabel();
        femaleButton = new javax.swing.JRadioButton();
        maleButton = new javax.swing.JRadioButton();
        submitButton = new javax.swing.JButton();
        passwordField = new javax.swing.JPasswordField();
        backLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBounds(new java.awt.Rectangle(450, 250, 400, 300));

        registrationFormLabel.setFont(new java.awt.Font("Ubuntu", 1, 18));
        registrationFormLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        registrationFormLabel.setText("Registration Form");
        registrationFormLabel.setBounds(100, 10, 200, 30);
        jLayeredPane1.add(registrationFormLabel, javax.swing.JLayeredPane.DEFAULT_LAYER);

        nameLabel.setFont(new java.awt.Font("Ubuntu", 1, 17));
        nameLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        nameLabel.setText("Name");
        nameLabel.setBounds(30, 60, 80, 30);
        jLayeredPane1.add(nameLabel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        nameField.setBounds(120, 60, 230, 30);
        jLayeredPane1.add(nameField, javax.swing.JLayeredPane.DEFAULT_LAYER);

        genderLabel.setFont(new java.awt.Font("Ubuntu", 1, 17));
        genderLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        genderLabel.setText("Gender");
        genderLabel.setBounds(10, 210, 100, 30);
        jLayeredPane1.add(genderLabel, javax.swing.JLayeredPane.DEFAULT_LAYER);

        handleLabel.setFont(new java.awt.Font("Ubuntu", 1, 17));
        handleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        handleLabel.setText("onj Handle");
        handleLabel.setBounds(0, 110, 110, 30);
        jLayeredPane1.add(handleLabel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        handleField.setBounds(120, 110, 230, 30);
        jLayeredPane1.add(handleField, javax.swing.JLayeredPane.DEFAULT_LAYER);

        passwordLabel.setFont(new java.awt.Font("Ubuntu", 1, 17));
        passwordLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        passwordLabel.setText("Password");
        passwordLabel.setBounds(10, 160, 100, 30);
        jLayeredPane1.add(passwordLabel, javax.swing.JLayeredPane.DEFAULT_LAYER);

        femaleButton.setFont(new java.awt.Font("Ubuntu", 1, 17));
        femaleButton.setText("Female");
        femaleButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                femaleButtonMouseClicked(evt);
            }
        });
        femaleButton.setBounds(220, 213, 90, 24);
        jLayeredPane1.add(femaleButton, javax.swing.JLayeredPane.DEFAULT_LAYER);

        maleButton.setFont(new java.awt.Font("Ubuntu", 1, 17));
        maleButton.setSelected(true);
        maleButton.setText("Male");
        maleButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                maleButtonMouseClicked(evt);
            }
        });
        maleButton.setBounds(120, 213, 90, 24);
        jLayeredPane1.add(maleButton, javax.swing.JLayeredPane.DEFAULT_LAYER);

        submitButton.setFont(new java.awt.Font("Ubuntu", 1, 18));
        submitButton.setText("SUBMIT");
        submitButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                submitButtonMouseClicked(evt);
            }
        });
        submitButton.setBounds(150, 250, 100, 35);
        jLayeredPane1.add(submitButton, javax.swing.JLayeredPane.DEFAULT_LAYER);
        passwordField.setBounds(120, 160, 230, 30);
        jLayeredPane1.add(passwordField, javax.swing.JLayeredPane.DEFAULT_LAYER);

        backLabel.setText("<<Back");
        backLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                backLabelMouseClicked(evt);
            }
        });
        backLabel.setBounds(20, 260, 60, 18);
        jLayeredPane1.add(backLabel, javax.swing.JLayeredPane.DEFAULT_LAYER);

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

    AdminService adminService;
    
private void maleButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_maleButtonMouseClicked
// TODO add your handling code here:
    if(femaleButton.isSelected()) {
        femaleButton.setSelected(false);
    }
}//GEN-LAST:event_maleButtonMouseClicked

private void femaleButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_femaleButtonMouseClicked
    if(maleButton.isSelected()) {
        maleButton.setSelected(false);
    }
}//GEN-LAST:event_femaleButtonMouseClicked

private void submitButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_submitButtonMouseClicked
    if(formComplete()) {
        adminService = new AdminService();
        String gender = maleButton.isSelected() ? "Male" : "Female";
        if(adminService.registerAdmin(nameField.getText(), handleField.getText(),
                passwordField.getText(), gender)) {
            this.dispose();
            login.setVisible(true);
        }
    }
}//GEN-LAST:event_submitButtonMouseClicked

private void backLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_backLabelMouseClicked
// TODO add your handling code here:
    this.dispose();
    login.setVisible(true);
}//GEN-LAST:event_backLabelMouseClicked

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
            java.util.logging.Logger.getLogger(Register.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Register.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Register.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Register.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new Register().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel backLabel;
    private javax.swing.JRadioButton femaleButton;
    private javax.swing.JLabel genderLabel;
    private javax.swing.JTextField handleField;
    private javax.swing.JLabel handleLabel;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JRadioButton maleButton;
    private javax.swing.JTextField nameField;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JPasswordField passwordField;
    private javax.swing.JLabel passwordLabel;
    private javax.swing.JLabel registrationFormLabel;
    private javax.swing.JButton submitButton;
    // End of variables declaration//GEN-END:variables

    Login login = new Login();
    public Register(Login login) {
        initComponents();
        this.login = login;
    }
    
    private boolean formComplete() {
        if(nameField.getText().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Name field cannot be left blank.");
            return false;
        }
        else if(handleField.getText().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Handle field cannot be left blank.");
            return false;
        }
        else if(passwordField.getText().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Password field cannot be left blank.");
            return false;
        }
        else if(maleButton.isSelected() == false && femaleButton.isSelected() == false) {
            JOptionPane.showMessageDialog(rootPane, "Select a Gender.");
            return false;
        }
        else if(handleAvailable(handleField.getText()) == false) {
            JOptionPane.showMessageDialog(rootPane, "Handle already taken.");
            return false;
        }
        return true;
    }

    private boolean handleAvailable(String handle) {
        adminService = new AdminService();
        return adminService.handleAvailable(handle);
    }
}