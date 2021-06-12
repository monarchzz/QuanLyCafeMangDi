/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quanlycafemangdi.view;

import java.sql.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Set;
import javax.swing.JOptionPane;
import quanlycafemangdi.Util;
import quanlycafemangdi.model.NguyenLieu;
import quanlycafemangdi.model.NhapXuat;
import quanlycafemangdi.model.ThongTinDangNhap;

/**
 *
 * @author admin
 */
public class NguyenLieu_NhapFrame extends javax.swing.JFrame {

    private ArrayList<String> danhSachMaNhapXuat = new ArrayList<>();
    private ArrayList<NguyenLieu> danhSachNguyenLieu = new ArrayList<>();
    private ArrayList<NhapXuat> danhSachNhap = new ArrayList<>();
    
    private NhapXuat nhapXuat = new NhapXuat(); 
    
    private IOnFrameDispose onFrameDispose;
    
    private String loi;
    private String buttonClicked = "";
        
    public NguyenLieu_NhapFrame() {
        initComponents();
        this.setLocationRelativeTo(null);
        
        layDanhSachMaNhapXuat(danhSachMaNhapXuat);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        nhapNguyenLieu_Lbl = new javax.swing.JLabel();
        maNhapXuat_Lbl = new javax.swing.JLabel();
        maNhapXuat_TF = new javax.swing.JTextField();
        nguoiThucHien_Lbl = new javax.swing.JLabel();
        nguoiThucHien_TF = new javax.swing.JTextField();
        trangThai_Lbl = new javax.swing.JLabel();
        trangThai_TF = new javax.swing.JTextField();
        ghiChu_Lbl = new javax.swing.JLabel();
        chiTiet_Lbl = new javax.swing.JLabel();
        danhSachNguyenLieu_Btn = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        ghiChu_TA = new javax.swing.JTextArea();
        nhap_Btn = new javax.swing.JButton();
        huy_Btn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Nhập nguyên liệu");
        setBackground(new java.awt.Color(254, 254, 254));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        nhapNguyenLieu_Lbl.setBackground(new java.awt.Color(255, 255, 255));
        nhapNguyenLieu_Lbl.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        nhapNguyenLieu_Lbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        nhapNguyenLieu_Lbl.setText("Nhập nguyên liệu");
        nhapNguyenLieu_Lbl.setOpaque(true);

        maNhapXuat_Lbl.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        maNhapXuat_Lbl.setText("Mã nhập xuất:");

        maNhapXuat_TF.setEditable(false);
        maNhapXuat_TF.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        maNhapXuat_TF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                maNhapXuat_TFActionPerformed(evt);
            }
        });

        nguoiThucHien_Lbl.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        nguoiThucHien_Lbl.setText("Người thực hiện:");

        nguoiThucHien_TF.setEditable(false);
        nguoiThucHien_TF.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        nguoiThucHien_TF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nguoiThucHien_TFActionPerformed(evt);
            }
        });

        trangThai_Lbl.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        trangThai_Lbl.setText("Trạng thái:");

        trangThai_TF.setEditable(false);
        trangThai_TF.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        trangThai_TF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                trangThai_TFActionPerformed(evt);
            }
        });

        ghiChu_Lbl.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        ghiChu_Lbl.setText("Ghi chú:");

        chiTiet_Lbl.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        chiTiet_Lbl.setText("Chi tiết:");

        danhSachNguyenLieu_Btn.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        danhSachNguyenLieu_Btn.setText("Danh sách nguyên liệu");
        danhSachNguyenLieu_Btn.setPreferredSize(new java.awt.Dimension(175, 23));
        danhSachNguyenLieu_Btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                danhSachNguyenLieu_BtnActionPerformed(evt);
            }
        });

        ghiChu_TA.setColumns(20);
        ghiChu_TA.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        ghiChu_TA.setRows(5);
        jScrollPane1.setViewportView(ghiChu_TA);

        nhap_Btn.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        nhap_Btn.setText("Nhập");
        nhap_Btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nhap_BtnActionPerformed(evt);
            }
        });

        huy_Btn.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        huy_Btn.setText("Hủy");
        huy_Btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                huy_BtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(nhap_Btn, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(huy_Btn, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(nguoiThucHien_Lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(chiTiet_Lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(ghiChu_Lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(trangThai_Lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(9, 9, 9)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jScrollPane1)
                                            .addComponent(danhSachNguyenLieu_Btn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(nguoiThucHien_TF, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(trangThai_TF, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(maNhapXuat_Lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(maNhapXuat_TF, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 119, Short.MAX_VALUE))
                    .addComponent(nhapNguyenLieu_Lbl, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(nhapNguyenLieu_Lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(maNhapXuat_TF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(maNhapXuat_Lbl))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nguoiThucHien_Lbl)
                    .addComponent(nguoiThucHien_TF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(trangThai_Lbl)
                    .addComponent(trangThai_TF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(chiTiet_Lbl)
                    .addComponent(danhSachNguyenLieu_Btn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ghiChu_Lbl)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(huy_Btn, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nhap_Btn))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void maNhapXuat_TFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_maNhapXuat_TFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_maNhapXuat_TFActionPerformed

    private void nguoiThucHien_TFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nguoiThucHien_TFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nguoiThucHien_TFActionPerformed

    private void trangThai_TFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_trangThai_TFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_trangThai_TFActionPerformed

    private void danhSachNguyenLieu_BtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_danhSachNguyenLieu_BtnActionPerformed
        NguyenLieu_ChiTietNhapXuatDialog.setChucNang("Nhap");
        NguyenLieu_ChiTietNhapXuatDialog nguyenLieuChiTietNhapXuatDialog = new NguyenLieu_ChiTietNhapXuatDialog(this, rootPaneCheckingEnabled);
        nguyenLieuChiTietNhapXuatDialog.thietLapDuLieu(nhapXuat); // Hien thi lai du lieu vua them o ChiTietNhapXuatDialog
        nguyenLieuChiTietNhapXuatDialog.setVisible(true);
    }//GEN-LAST:event_danhSachNguyenLieu_BtnActionPerformed

    private void huy_BtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_huy_BtnActionPerformed
        buttonClicked = "Huy";
        this.dispose();
    }//GEN-LAST:event_huy_BtnActionPerformed

    private void nhap_BtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nhap_BtnActionPerformed
        if (kiemTraLoi() == false)
        {
            nhapNguyenLieu();
        }
    }//GEN-LAST:event_nhap_BtnActionPerformed

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
            java.util.logging.Logger.getLogger(NguyenLieu_NhapFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NguyenLieu_NhapFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NguyenLieu_NhapFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NguyenLieu_NhapFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NguyenLieu_NhapFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel chiTiet_Lbl;
    private javax.swing.JButton danhSachNguyenLieu_Btn;
    private javax.swing.JLabel ghiChu_Lbl;
    private javax.swing.JTextArea ghiChu_TA;
    private javax.swing.JButton huy_Btn;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel maNhapXuat_Lbl;
    private javax.swing.JTextField maNhapXuat_TF;
    private javax.swing.JLabel nguoiThucHien_Lbl;
    private javax.swing.JTextField nguoiThucHien_TF;
    private javax.swing.JLabel nhapNguyenLieu_Lbl;
    private javax.swing.JButton nhap_Btn;
    private javax.swing.JLabel trangThai_Lbl;
    private javax.swing.JTextField trangThai_TF;
    // End of variables declaration//GEN-END:variables

    @Override
    public void dispose() 
    {
        if (buttonClicked.equals("Huy"))
        {
            super.dispose();
        }
        else
        {
            onFrameDispose.onFrameDispose();
            super.dispose(); //To change body of generated methods, choose Tools | Templates.            
        }
    }    
       
    public void layDanhSachMaNhapXuat(ArrayList<String> danhSachMaNhapXuat)
    {
        Connection connect = Util.getConnection();
        String query = "select * from NhapXuat";
        try
        {
            PreparedStatement ps = connect.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                danhSachMaNhapXuat.add(rs.getString("maNX"));
            }
        }catch (SQLException ex)
        {
            System.out.println("Lay danh sach ma nhap xuat that bai");
        }       
    }
    
    public String taoMaNhapXuat()
    {
        int STT = 0;
        String maNhapXuat = "";
        maNhapXuat = "N" + String.valueOf(STT);
        while (danhSachMaNhapXuat.contains(maNhapXuat))
        {
            STT++;
            maNhapXuat = "N" + String.valueOf(STT);
        }           
        return maNhapXuat;
    }    
    
    public void thietLapDuLieu()
    {
        String maNhapXuat = taoMaNhapXuat();
        maNhapXuat_TF.setText(maNhapXuat);
        nguoiThucHien_TF.setText(ThongTinDangNhap.getTenDangNhap() + " - " + ThongTinDangNhap.getTenNguoiDung());
        trangThai_TF.setText("0");
        ghiChu_TA.setText("Nhập nguyên liệu về kho");
    }
    
    public void nhapNguyenLieu()
    {
        String maNhapXuat = maNhapXuat_TF.getText();
        String tenTaiKhoan = ThongTinDangNhap.getTenDangNhap();
        Timestamp thoiGian = new Timestamp(new java.util.Date().getTime());
        String trangThai = trangThai_TF.getText();
        String ghiChu = ghiChu_TA.getText();
        
        nhapXuat.setMaNhapXuat(maNhapXuat);
        nhapXuat.setTaiKhoan(tenTaiKhoan);
        nhapXuat.setThoiGian(thoiGian);
        nhapXuat.setTrangThai(trangThai);
        nhapXuat.setGhiChu(ghiChu);
        // Khong co setChiTietNhapXuat() va setThanhTien() vi da duoc set ben dialog
        nhapNguyenLieuSQL(maNhapXuat, tenTaiKhoan, thoiGian, trangThai, nhapXuat.getThanhTien(), ghiChu);

        danhSachNhap.add(nhapXuat);
        
        JOptionPane.showMessageDialog(rootPane, "Nhập nguyên liệu thành công");
        this.dispose();
    }
    
    public void nhapNguyenLieuSQL(String maNhapXuat, String tenTaiKhoan,Timestamp thoiGian, String trangThai, 
            long thanhTien, String ghiChu)
    {
        Connection connect = Util.getConnection();
        String query = "insert into NhapXuat values (?,?,?,?,?,?)";
        try
        {
            PreparedStatement ps = connect.prepareStatement(query);
            ps.setString(1, maNhapXuat);
            ps.setString(2, tenTaiKhoan);
            ps.setTimestamp(3, thoiGian);
            ps.setString(4, trangThai);
            ps.setLong(5, thanhTien);
            ps.setString(6, ghiChu);
            ps.executeUpdate();
            
            Set<String> keySet = nhapXuat.getChiTietNhapXuat().keySet();
            for (String key: keySet)
            {
                String query2 = "insert into ChiTietNhapXuat values (?,?,?)"
                            +   " update NguyenLieu"
                            +   "  set SoLuong = SoLuong + " + nhapXuat.getChiTietNhapXuat().get(key)
                            +   "  where maNL = '" + key + "'";
                ps = connect.prepareStatement(query2);
                ps.setString(1, maNhapXuat);
                ps.setString(2, key);
                ps.setInt(3, nhapXuat.getChiTietNhapXuat().get(key));
                ps.executeUpdate();
                
                String query3 = "delete from LichSuChinhSuaNguyenLieu"
                              + " where thoiGian = (select max(thoiGian) from LichSuChinhSuaNguyenLieu)";
                ps = connect.prepareStatement(query3);
                ps.executeUpdate();
                
                // Update so luong trong chuong trinh
                int soLuong = nhapXuat.getChiTietNhapXuat().get(key);
                for (int i = 0; i < danhSachNguyenLieu.size(); i++)
                {
                    if (danhSachNguyenLieu.get(i).getMaNguyenLieu().equals(key))
                    {
                        danhSachNguyenLieu.get(i).setSoLuong(danhSachNguyenLieu.get(i).getSoLuong() + soLuong);
                        break;
                    }
                }
            }
            ps.close();
            connect.close();
        }catch (SQLException ex)
        {
            ex.printStackTrace();
            //System.out.println("Nhap nguyen lieu SQL that bai");
        }
    }
    
    public void hienThiLoi(String loi)
    {
        JOptionPane.showMessageDialog(rootPane, loi, "Lỗi", JOptionPane.ERROR_MESSAGE);
    }
    
    public boolean kiemTraLoi()
    {
        loi = "";
        
        if (nhapXuat.getChiTietNhapXuat().isEmpty())
        {
            loi = loi + "Vui lòng cho biết các nguyên liệu cần nhập\n";
        }
        
        if (loi.equals("") == false)
        {
            hienThiLoi(loi);
            return true;
        }
        else
        {
            return false;
        }
    }    

    public void setNhapXuat(NhapXuat nhapXuat) {
        this.nhapXuat = nhapXuat;
    }

    public void setDanhSachNhap(ArrayList<NhapXuat> danhSachNhap) {
        this.danhSachNhap = danhSachNhap;
    }

    public void setDanhSachNguyenLieu(ArrayList<NguyenLieu> danhSachNguyenLieu) {
        this.danhSachNguyenLieu = danhSachNguyenLieu;
    }   
    
    public ArrayList<NguyenLieu> getDanhSachNguyenLieu() {
        return danhSachNguyenLieu;
    }    
    
    public void setOnFrameDispose(IOnFrameDispose onFrameDispose){
        this.onFrameDispose = onFrameDispose;
    }        
}
