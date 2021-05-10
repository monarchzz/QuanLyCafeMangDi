/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quanlycafemangdi.view;


import java.awt.Color;
import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;
import quanlycafemangdi.data.Data;
import quanlycafemangdi.model.ThongTinDangNhap;

public class DangNhap extends javax.swing.JFrame {
    
    private Data data;
    
    public DangNhap() {
        initComponents();
        data = Data.getInstance();
        this.setLocationRelativeTo(null);
        
        setBackground(Color.RED);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        dangNhap_Lbl = new javax.swing.JLabel();
        tenDangNhap_TF = new javax.swing.JTextField();
        dangNhap_Btn = new javax.swing.JButton();
        tenDangNhap_Lbl = new javax.swing.JLabel();
        matKhau_Lbl = new javax.swing.JLabel();
        matKhau_PwF = new javax.swing.JPasswordField();
        hienThiMatKhau_CB = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Đăng nhập");
        setBackground(new java.awt.Color(102, 255, 0));
        setResizable(false);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        dangNhap_Lbl.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        dangNhap_Lbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        dangNhap_Lbl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/HinhAnh/logo.png"))); // NOI18N
        dangNhap_Lbl.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

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

        dangNhap_Btn.setBackground(new java.awt.Color(0, 102, 255));
        dangNhap_Btn.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        dangNhap_Btn.setForeground(new java.awt.Color(255, 255, 255));
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

        hienThiMatKhau_CB.setBackground(new java.awt.Color(255, 255, 255));
        hienThiMatKhau_CB.setText("Hiển thị mật khẩu");
        hienThiMatKhau_CB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hienThiMatKhau_CBActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(80, 80, 80)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dangNhap_Lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(matKhau_Lbl)
                        .addComponent(tenDangNhap_Lbl)
                        .addComponent(tenDangNhap_TF)
                        .addComponent(matKhau_PwF)
                        .addComponent(hienThiMatKhau_CB, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(dangNhap_Btn, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(80, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(dangNhap_Lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
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
                .addComponent(dangNhap_Btn, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(56, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void dangNhap_BtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dangNhap_BtnActionPerformed
      dangNhap();
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
            dangNhap();
        }
    }//GEN-LAST:event_matKhau_PwFKeyPressed

    private void tenDangNhap_TFKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tenDangNhap_TFKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER)
        {
            dangNhap();
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
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel matKhau_Lbl;
    private javax.swing.JPasswordField matKhau_PwF;
    private javax.swing.JLabel tenDangNhap_Lbl;
    private javax.swing.JTextField tenDangNhap_TF;
    // End of variables declaration//GEN-END:variables

    
    public void dangNhap()
    {
        String tenDangNhap = tenDangNhap_TF.getText().trim();
        String matKhau = matKhau_PwF.getText().trim();
        
        boolean trongTenDangNhap = false, trongMatKhau = false;
        if (tenDangNhap.equals(""))
        {
            trongTenDangNhap = true;
            JOptionPane.showMessageDialog(rootPane, "Tên đăng nhập hoặc mật khẩu không hợp lệ.", "Lỗi!", 
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        else if (matKhau.equals(""))
        {
            trongMatKhau = true;
            JOptionPane.showMessageDialog(rootPane, "Tên đăng nhập hoặc mật khẩu không hợp lệ.", "Lỗi!", 
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
                ThongTinDangNhap.setTenNguoiDung(data.layTenNguoiDung(tenDangNhap));
                ThongTinDangNhap.setChucVu(chucVu);
                ThongTinDangNhap.setMatKhau(matKhau);
                TrangChu trangChu = new TrangChu();
                trangChu.xetChucVu(chucVu);
                trangChu.setLocationRelativeTo(null);
                trangChu.setVisible(true);
            }
            else
            {
                JOptionPane.showMessageDialog(rootPane, "Sai tên đăng nhập hoặc mật khẩu.", "Lỗi!", 
                        JOptionPane.ERROR_MESSAGE);
            }
        }        
    }
    
}
