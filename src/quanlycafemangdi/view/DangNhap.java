/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quanlycafemangdi.view;


import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;
import java.sql.*;
import quanlycafemangdi.data.Data;
import quanlycafemangdi.model.ThongTinDangNhap;

public class DangNhap extends javax.swing.JFrame {
    
    private Data data;
    
    public DangNhap() {
        initComponents();
        data = Data.getInstance();
        this.setLocationRelativeTo(null);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        dangNhap_Lbl = new javax.swing.JLabel();
        tenDangNhap_TF = new javax.swing.JTextField();
        dangNhap_Btn = new javax.swing.JButton();
        tenDangNhap_Lbl = new javax.swing.JLabel();
        matKhau_Lbl = new javax.swing.JLabel();
        lamMoi_Btn = new javax.swing.JButton();
        matKhau_PwF = new javax.swing.JPasswordField();
        hienThiMatKhau_CB = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Dang nhap");

        dangNhap_Lbl.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        dangNhap_Lbl.setText("Đăng nhập");

        tenDangNhap_TF.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tenDangNhap_TF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tenDangNhap_TFActionPerformed(evt);
            }
        });
        tenDangNhap_TF.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tenDangNhap_TFKeyPressed(evt);
            }
        });

        dangNhap_Btn.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        dangNhap_Btn.setText("Đăng nhập");
        dangNhap_Btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dangNhap_BtnActionPerformed(evt);
            }
        });
        dangNhap_Btn.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                dangNhap_BtnKeyPressed(evt);
            }
        });

        tenDangNhap_Lbl.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tenDangNhap_Lbl.setText("Tên đăng nhập");

        matKhau_Lbl.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        matKhau_Lbl.setText("Mật khẩu");

        lamMoi_Btn.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lamMoi_Btn.setText("Lam moi");
        lamMoi_Btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lamMoi_BtnActionPerformed(evt);
            }
        });

        matKhau_PwF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                matKhau_PwFActionPerformed(evt);
            }
        });
        matKhau_PwF.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                matKhau_PwFKeyPressed(evt);
            }
        });

        hienThiMatKhau_CB.setText("Hien thi mat khau");
        hienThiMatKhau_CB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hienThiMatKhau_CBActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(148, Short.MAX_VALUE)
                .addComponent(dangNhap_Lbl)
                .addGap(128, 128, 128))
            .addGroup(layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(dangNhap_Btn, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lamMoi_Btn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(matKhau_Lbl)
                        .addComponent(tenDangNhap_Lbl)
                        .addComponent(tenDangNhap_TF, javax.swing.GroupLayout.DEFAULT_SIZE, 255, Short.MAX_VALUE)
                        .addComponent(matKhau_PwF)
                        .addComponent(hienThiMatKhau_CB, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(57, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(dangNhap_Lbl)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tenDangNhap_Lbl)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tenDangNhap_TF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(matKhau_Lbl)
                .addGap(9, 9, 9)
                .addComponent(matKhau_PwF, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(hienThiMatKhau_CB)
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lamMoi_Btn, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dangNhap_Btn, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(56, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lamMoi_BtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lamMoi_BtnActionPerformed
        tenDangNhap_TF.setText("");
        matKhau_PwF.setText("");
        
        hienThiMatKhau_CB.setSelected(false);
    }//GEN-LAST:event_lamMoi_BtnActionPerformed

    private void dangNhap_BtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dangNhap_BtnActionPerformed
        String tenDangNhap = tenDangNhap_TF.getText();
        String matKhau = matKhau_PwF.getText();
        
        boolean trongTenDangNhap = false, trongMatKhau = false;
        if (tenDangNhap.equals(""))
        {
            trongTenDangNhap = true;
            JOptionPane.showMessageDialog(rootPane, "Ten dang nhap hoac mat khau khong hop le.", "Loi!", 
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        else if (matKhau.equals(""))
        {
            trongMatKhau = true;
            JOptionPane.showMessageDialog(rootPane, "Ten dang nhap hoac mat khau khong hop le.", "Loi!", 
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        else
        {
            if (data.kiemTraDangNhap(tenDangNhap, matKhau) == true)
            {
                this.dispose();
                String chucVu = data.layChucVu(tenDangNhap, matKhau);
                ThongTinDangNhap.setTenDangNhap(tenDangNhap);
                ThongTinDangNhap.setChucVu(chucVu);
                TrangChu trangChu = new TrangChu();
                trangChu.setLocationRelativeTo(null);
                trangChu.setVisible(true);
            }
            else
            {
                JOptionPane.showMessageDialog(rootPane, "Sai ten dang nhap hoac mat khau.", "Loi!", 
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_dangNhap_BtnActionPerformed

    private void dangNhap_BtnKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_dangNhap_BtnKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_dangNhap_BtnKeyPressed

    private void tenDangNhap_TFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tenDangNhap_TFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tenDangNhap_TFActionPerformed

    private void matKhau_PwFKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_matKhau_PwFKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER)
        {
            String tenDangNhap = tenDangNhap_TF.getText();
            String matKhau = matKhau_PwF.getText();
        
            boolean trongTenDangNhap = false, trongMatKhau = false;
            if (tenDangNhap.equals(""))
            {
                trongTenDangNhap = true;
                JOptionPane.showMessageDialog(rootPane, "Ten dang nhap hoac mat khau khong hop le.", "Loi!", 
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            else if (matKhau.equals(""))
            {
                trongMatKhau = true;
                JOptionPane.showMessageDialog(rootPane, "Ten dang nhap hoac mat khau khong hop le.", "Loi!", 
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            else
            {
                if (data.kiemTraDangNhap(tenDangNhap, matKhau) == true)
                {
                    this.dispose();
                    String chucVu = data.layChucVu(tenDangNhap, matKhau);
                    ThongTinDangNhap.setTenDangNhap(tenDangNhap);
                    ThongTinDangNhap.setChucVu(chucVu);                
                     TrangChu trangChu = new TrangChu();
                    trangChu.setLocationRelativeTo(null);
                    trangChu.setVisible(true);
                }
                else
                {
                    JOptionPane.showMessageDialog(rootPane, "Sai ten dang nhap hoac mat khau.", "Loi!", 
                            JOptionPane.ERROR_MESSAGE);
                }
            }            
        }
    }//GEN-LAST:event_matKhau_PwFKeyPressed

    private void tenDangNhap_TFKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tenDangNhap_TFKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER)
        {
            String tenDangNhap = tenDangNhap_TF.getText();
            String matKhau = matKhau_PwF.getText();
        
            boolean trongTenDangNhap = false, trongMatKhau = false;
            if (tenDangNhap.equals(""))
            {
                trongTenDangNhap = true;
                JOptionPane.showMessageDialog(rootPane, "Ten dang nhap hoac mat khau khong hop le.", "Loi!", 
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            else if (matKhau.equals(""))
            {
                trongMatKhau = true;
                JOptionPane.showMessageDialog(rootPane, "Ten dang nhap hoac mat khau khong hop le.", "Loi!", 
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            else
            {
                if (data.kiemTraDangNhap(tenDangNhap, matKhau) == true)
                {
                    this.dispose();
                    String chucVu = data.layChucVu(tenDangNhap, matKhau);
                    ThongTinDangNhap.setTenDangNhap(tenDangNhap);
                    ThongTinDangNhap.setChucVu(chucVu);          
                    ThongTinDangNhap.setMatKhau(matKhau);
                    TrangChu trangChu = new TrangChu();
                    trangChu.setLocationRelativeTo(null);
                    trangChu.setVisible(true);
                }
                else
                {
                    JOptionPane.showMessageDialog(rootPane, "Sai ten dang nhap hoac mat khau.", "Loi!", 
                            JOptionPane.ERROR_MESSAGE);
                }
            }            
        }
    }//GEN-LAST:event_tenDangNhap_TFKeyPressed

    private void hienThiMatKhau_CBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hienThiMatKhau_CBActionPerformed
        if (hienThiMatKhau_CB.isSelected())
        {
            matKhau_PwF.setEchoChar((char)0);
        }
        else
        {
            matKhau_PwF.setEchoChar('●');
        }
    }//GEN-LAST:event_hienThiMatKhau_CBActionPerformed

    private void matKhau_PwFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_matKhau_PwFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_matKhau_PwFActionPerformed

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
            java.util.logging.Logger.getLogger(DangNhap.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DangNhap.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DangNhap.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DangNhap.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DangNhap().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton dangNhap_Btn;
    private javax.swing.JLabel dangNhap_Lbl;
    private javax.swing.JCheckBox hienThiMatKhau_CB;
    private javax.swing.JButton lamMoi_Btn;
    private javax.swing.JLabel matKhau_Lbl;
    private javax.swing.JPasswordField matKhau_PwF;
    private javax.swing.JLabel tenDangNhap_Lbl;
    private javax.swing.JTextField tenDangNhap_TF;
    // End of variables declaration//GEN-END:variables

    
    
}
