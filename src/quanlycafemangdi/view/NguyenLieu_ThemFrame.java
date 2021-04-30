/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quanlycafemangdi.view;

import javax.swing.JOptionPane;
import quanlycafemangdi.model.NguyenLieu;
import java.sql.*;
import java.util.ArrayList;
import quanlycafemangdi.Util;
import quanlycafemangdi.model.NhapXuat;
import quanlycafemangdi.model.ThongTinDangNhap;
/**
 *
 * @author admin
 */
public class NguyenLieu_ThemFrame extends javax.swing.JFrame {
    
    private ArrayList<NguyenLieu> danhSachNguyenLieu;
    private ArrayList<String> danhSachMaNhapXuat = new ArrayList<>();
    private ArrayList<NhapXuat> danhSachNhap = new ArrayList<>();
    
    private IOnFrameDispose onFrameDispose;
        
    private String loi;
    private String buttonClicked = "";

    public NguyenLieu_ThemFrame() {
        initComponents();
        
        this.setLocationRelativeTo(null);
        
        layDanhSachMaNhapXuat(danhSachMaNhapXuat); 
        layDonViTinh();
        
        ghiChu_TA.setText("Thêm nguyên liệu mới vào kho");
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        nhapXuat_BtnG = new javax.swing.ButtonGroup();
        themNguyenLieuMoi_Lbl = new javax.swing.JLabel();
        maNguyenLieu_Lbl = new javax.swing.JLabel();
        maNguyenLieu_TF = new javax.swing.JTextField();
        tenNguyenLieu_Lbl = new javax.swing.JLabel();
        tenNguyenLieu_TF = new javax.swing.JTextField();
        donViTinh_Lbl = new javax.swing.JLabel();
        gia_Lbl = new javax.swing.JLabel();
        gia_TF = new javax.swing.JTextField();
        soLuong_Lbl = new javax.swing.JLabel();
        soLuong_TF = new javax.swing.JTextField();
        ghiChu_Lbl = new javax.swing.JLabel();
        them_Btn = new javax.swing.JButton();
        huy_Btn = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        ghiChu_TA = new javax.swing.JTextArea();
        donViTinh_CBx = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        themNguyenLieuMoi_Lbl.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        themNguyenLieuMoi_Lbl.setText("Thêm nguyên liệu mới");

        maNguyenLieu_Lbl.setText("Mã nguyên liệu");

        tenNguyenLieu_Lbl.setText("Tên nguyên liệu");

        donViTinh_Lbl.setText("Đơn vị tính");

        gia_Lbl.setText("Giá");

        soLuong_Lbl.setText("Số lượng");

        ghiChu_Lbl.setText("Ghi chú");

        them_Btn.setText("Thêm");
        them_Btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                them_BtnActionPerformed(evt);
            }
        });

        huy_Btn.setText("Hủy");
        huy_Btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                huy_BtnActionPerformed(evt);
            }
        });

        ghiChu_TA.setColumns(20);
        ghiChu_TA.setRows(5);
        jScrollPane1.setViewportView(ghiChu_TA);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(ghiChu_Lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane1))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(soLuong_Lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(soLuong_TF, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(gia_Lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(gia_TF, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(donViTinh_Lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(donViTinh_CBx, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(140, 140, 140))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(tenNguyenLieu_Lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tenNguyenLieu_TF, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(maNguyenLieu_Lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(maNguyenLieu_TF, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(38, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 112, Short.MAX_VALUE)
                        .addComponent(themNguyenLieuMoi_Lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(118, 118, 118))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(them_Btn, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(huy_Btn, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(90, 90, 90))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(themNguyenLieuMoi_Lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(maNguyenLieu_TF)
                    .addComponent(maNguyenLieu_Lbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(tenNguyenLieu_TF)
                    .addComponent(tenNguyenLieu_Lbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(donViTinh_Lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(donViTinh_CBx, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(gia_TF)
                    .addComponent(gia_Lbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(soLuong_TF)
                    .addComponent(soLuong_Lbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1)
                    .addComponent(ghiChu_Lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(them_Btn, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(huy_Btn, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void huy_BtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_huy_BtnActionPerformed
        buttonClicked = "Huy";
        this.dispose();
    }//GEN-LAST:event_huy_BtnActionPerformed

    private void them_BtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_them_BtnActionPerformed
        if (kiemTraLoi() == false)
        {
            themNguyenLieu();
        }
    }//GEN-LAST:event_them_BtnActionPerformed

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
            java.util.logging.Logger.getLogger(NguyenLieu_ThemFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NguyenLieu_ThemFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NguyenLieu_ThemFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NguyenLieu_ThemFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NguyenLieu_ThemFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> donViTinh_CBx;
    private javax.swing.JLabel donViTinh_Lbl;
    private javax.swing.JLabel ghiChu_Lbl;
    private javax.swing.JTextArea ghiChu_TA;
    private javax.swing.JLabel gia_Lbl;
    private javax.swing.JTextField gia_TF;
    private javax.swing.JButton huy_Btn;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel maNguyenLieu_Lbl;
    private javax.swing.JTextField maNguyenLieu_TF;
    private javax.swing.ButtonGroup nhapXuat_BtnG;
    private javax.swing.JLabel soLuong_Lbl;
    private javax.swing.JTextField soLuong_TF;
    private javax.swing.JLabel tenNguyenLieu_Lbl;
    private javax.swing.JTextField tenNguyenLieu_TF;
    private javax.swing.JLabel themNguyenLieuMoi_Lbl;
    private javax.swing.JButton them_Btn;
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

    public void layDonViTinh()
    {
        Connection connect = Util.getConnection();
        String query = "select * from DonViTinh";
        try
        {
            PreparedStatement ps = connect.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                donViTinh_CBx.addItem(rs.getString("tenDV"));
            }
        }catch (SQLException ex)
        {
            System.out.println("Lay don vi tinh len combo box that bai");
        }
    }
    
    public String layMaDonVi(String donViTinh)
    {
        Connection connect = Util.getConnection();
        String query = "select * from DonViTinh where tenDV = '" + donViTinh + "'";
        try
        {
            PreparedStatement ps = connect.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
            {
                return rs.getString("maDV");
            }
        }catch (SQLException ex)
        {
            System.out.println("Lay ma don vi that bai");
        }
        return null;
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
        String maNhapXuat = "N" + String.valueOf(STT);
        while (danhSachMaNhapXuat.contains(maNhapXuat))
        {
            STT++;
            maNhapXuat = "N" + String.valueOf(STT);
        }
        return maNhapXuat;        
    }     
    
    public boolean kiemTraMaNguyenLieu(String maNguyenLieu)
    {
        Connection connect = Util.getConnection();
        String query = "select * from NguyenLieu";
        try
        {
            PreparedStatement ps = connect.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
            {
                if (maNguyenLieu.equals(rs.getString("maNL")))
                {
                    return true;
                }
            }
        }catch (SQLException ex)
        {
            System.out.println("Kiem tra ma nguyen lieu that bai");
        }
        return false;
    }    

    public boolean kiemTraLoi()
    {   
        loi = "";
        
        String maNguyenLieu = maNguyenLieu_TF.getText();
        String tenNguyenLieu = tenNguyenLieu_TF.getText();
        String gia = gia_TF.getText();
        String soLuong = soLuong_TF.getText();
        
        if (maNguyenLieu.equals(""))
        {
            loi = loi + "Mã nguyên liệu không được để trống\n";
        }
        else if (kiemTraMaNguyenLieu(maNguyenLieu) == true)
        {
            loi = loi + "Mã nguyên liệu đã tồn tại\n";
        }
        
        if (tenNguyenLieu.equals(""))
        {
            loi = loi + "Tên nguyên liệu không được để trống\n";
        }

        if (gia.equals(""))
        {
            loi = loi + "Giá tiền không được để trống\n";
        }
        else if (gia.matches("\\d+") == false)
        {
            loi = loi + "Giá tiền không hợp lệ\n";
        }
        
        if (soLuong.equals(""))
        {
            loi = loi + "Số lượng không được để trống\n";
        }
        else if (soLuong.matches("\\d+") == false)
        {
            loi = loi + "Số lượng phải là số nguyên\n";
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
    
    public void hienThiLoi(String loi)
    {
        JOptionPane.showMessageDialog(rootPane, loi, "Lỗi", JOptionPane.ERROR_MESSAGE);
    }    
    
    public void themNguyenLieu()
    {
        // Nguyen lieu
        String maNguyenLieu = maNguyenLieu_TF.getText();
        String tenNguyenLieu = tenNguyenLieu_TF.getText();
        String donViTinh = (String) donViTinh_CBx.getSelectedItem();
        String maDonVi = layMaDonVi(donViTinh);
        String gia = gia_TF.getText();
        
        // Nhap xuat
        String maNhapXuat = taoMaNhapXuat();
        String tenTK = ThongTinDangNhap.getTenDangNhap();
        Timestamp thoiGian = new Timestamp(new java.util.Date().getTime());
        String trangThai = "0";
        String ghiChu = ghiChu_TA.getText();
        String soLuong = soLuong_TF.getText();       
        
        NhapXuat nhapXuat = new NhapXuat();
        nhapXuat.setMaNhapXuat(maNhapXuat);
        nhapXuat.setTaiKhoan(tenTK);
        nhapXuat.setThoiGian(thoiGian);
        nhapXuat.setTrangThai(trangThai);
        nhapXuat.setGhiChu(ghiChu);
        nhapXuat.getChiTietNhapXuat().put(maNguyenLieu, Integer.parseInt(soLuong));
        nhapXuat.setThanhTien(Long.parseLong(gia)*Integer.parseInt(soLuong));
        danhSachNhap.add(nhapXuat);
        
        NguyenLieu nguyenLieu = new NguyenLieu(maNguyenLieu, tenNguyenLieu, maDonVi, donViTinh,
                Long.parseLong(gia), Integer.parseInt(soLuong));
        danhSachNguyenLieu.add(nguyenLieu);
        
        themNguyenLieuSQL(maNguyenLieu, tenNguyenLieu, maDonVi, Long.parseLong(gia),
                Integer.parseInt(soLuong), maNhapXuat, tenTK, thoiGian, trangThai, ghiChu);
        JOptionPane.showMessageDialog(rootPane, "Thêm nguyên liệu mới thành công");
        this.dispose();
    }
       
    public void themNguyenLieuSQL(String maNguyenLieu, String tenNguyenLieu, String maDonVi, long gia,
            int soLuong, String maNhapXuat, String tenTaiKhoan, Timestamp thoiGian, String trangThai, 
            String ghiChu)
    {
        Connection connect = Util.getConnection();
        String query = "insert into NguyenLieu values (?,?,?,?,?)"
                    +  " insert into NhapXuat values (?,?,?,?,?)"
                    +  " insert into ChiTietNhapXuat values (?,?,?)";
        try
        {
            PreparedStatement ps = connect.prepareStatement(query);
            ps.setString(1, maNguyenLieu);
            ps.setString(2, tenNguyenLieu);
            ps.setString(3, maDonVi);
            ps.setLong(4, gia);
            ps.setInt(5, soLuong);
            ps.setString(6, maNhapXuat);
            ps.setString(7, tenTaiKhoan);
            ps.setTimestamp(8, thoiGian);
            ps.setString(9, trangThai);
            ps.setString(10, ghiChu);
            ps.setString(11, maNhapXuat);
            ps.setString(12, maNguyenLieu);
            ps.setInt(13, soLuong);
            ps.executeUpdate();
            ps.close();
            connect.close();
        }catch (SQLException ex)
        {
            System.out.println("Them nguyen lieu that bai");
        }
    }

    public void setDanhSachNhap(ArrayList<NhapXuat> danhSachNhap) {
        this.danhSachNhap = danhSachNhap;
    }         
    
    public void setOnFrameDispose(IOnFrameDispose onFrameDispose){
        this.onFrameDispose = onFrameDispose;
    } 
    
    public void setDanhSachNguyenLieu(ArrayList<NguyenLieu> danhSachNguyenLieu) {
        this.danhSachNguyenLieu = danhSachNguyenLieu;
    }        
}
