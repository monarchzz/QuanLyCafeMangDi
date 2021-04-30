/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quanlycafemangdi.view;

import java.util.ArrayList;
import java.util.Set;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import quanlycafemangdi.Util;
import quanlycafemangdi.model.NguyenLieu;
import quanlycafemangdi.model.NhapXuat;

/**
 *
 * @author admin
 */
public class NguyenLieu_ChiTietNhapXuatDialog extends javax.swing.JDialog {

    private NguyenLieu_NhapFrame home;
    private NguyenLieu_XuatFrame home2;
    
    private static String chucNang;
    
    private NhapXuat nhapXuat;
    
    private ArrayList<NguyenLieu> danhSachNguyenLieu = new ArrayList<>();
    
    private DefaultTableModel dtmDanhSachNguyenLieu;
    private DefaultTableModel dtmKiemTraDanhSachNguyenLieu;
    
    public NguyenLieu_ChiTietNhapXuatDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(null);
        
        if (chucNang.equals("Nhap"))
        {
            home = (NguyenLieu_NhapFrame)parent;
            danhSachNguyenLieu = home.getDanhSachNguyenLieu();            
        }
        else
        {
            home2 = (NguyenLieu_XuatFrame)parent;
            danhSachNguyenLieu = home2.getDanhSachNguyenLieu();
        }
        
        khoiTaoBang(chucNang);
        
        hienThiDanhSachNguyenLieu();
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        chiTietCongThuc_Lbl = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        danhSachNguyenLieu_Table = new javax.swing.JTable();
        luuY_Btn = new javax.swing.JLabel();
        xacNhan_Btn = new javax.swing.JButton();
        kiemTraKetQua_Btn = new javax.swing.JButton();
        huy_Btn = new javax.swing.JButton();
        kiemTraKetQua_Pn = new javax.swing.JPanel();
        kiemTraKetQua_Lbl = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        kiemTraDanhSachNguyenLieu_Table = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        chiTietCongThuc_Lbl.setText("Danh sách nguyên liệu");

        danhSachNguyenLieu_Table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        danhSachNguyenLieu_Table.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                danhSachNguyenLieu_TableKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(danhSachNguyenLieu_Table);

        luuY_Btn.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        luuY_Btn.setText("*Lưu ý: nhấn Enter mỗi khi hiệu chỉnh số lượng để tránh sai sót");

        xacNhan_Btn.setText("Xác nhận");
        xacNhan_Btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                xacNhan_BtnActionPerformed(evt);
            }
        });

        kiemTraKetQua_Btn.setText("Kiểm tra kết quả");
        kiemTraKetQua_Btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kiemTraKetQua_BtnActionPerformed(evt);
            }
        });

        huy_Btn.setText("Hủy");
        huy_Btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                huy_BtnActionPerformed(evt);
            }
        });

        kiemTraKetQua_Lbl.setText("Kiểm tra kết quả");

        kiemTraDanhSachNguyenLieu_Table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane3.setViewportView(kiemTraDanhSachNguyenLieu_Table);

        javax.swing.GroupLayout kiemTraKetQua_PnLayout = new javax.swing.GroupLayout(kiemTraKetQua_Pn);
        kiemTraKetQua_Pn.setLayout(kiemTraKetQua_PnLayout);
        kiemTraKetQua_PnLayout.setHorizontalGroup(
            kiemTraKetQua_PnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kiemTraKetQua_PnLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 461, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, kiemTraKetQua_PnLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(kiemTraKetQua_Lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(173, 173, 173))
        );
        kiemTraKetQua_PnLayout.setVerticalGroup(
            kiemTraKetQua_PnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kiemTraKetQua_PnLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(kiemTraKetQua_Lbl)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(65, 65, 65))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(luuY_Btn, javax.swing.GroupLayout.PREFERRED_SIZE, 367, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 487, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(213, 213, 213)
                        .addComponent(chiTietCongThuc_Lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(86, 86, 86)
                        .addComponent(xacNhan_Btn, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(kiemTraKetQua_Btn, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(huy_Btn, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(kiemTraKetQua_Pn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(chiTietCongThuc_Lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 308, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(luuY_Btn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(kiemTraKetQua_Btn)
                            .addComponent(huy_Btn)
                            .addComponent(xacNhan_Btn)))
                    .addComponent(kiemTraKetQua_Pn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void danhSachNguyenLieu_TableKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_danhSachNguyenLieu_TableKeyPressed

    }//GEN-LAST:event_danhSachNguyenLieu_TableKeyPressed

    private void xacNhan_BtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_xacNhan_BtnActionPerformed
        themSuaDanhSachNhapXuat();
    }//GEN-LAST:event_xacNhan_BtnActionPerformed

    private void kiemTraKetQua_BtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kiemTraKetQua_BtnActionPerformed
        hienThiDanhSachNguyenLieuKiemTra();
    }//GEN-LAST:event_kiemTraKetQua_BtnActionPerformed

    private void huy_BtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_huy_BtnActionPerformed
        this.dispose();
    }//GEN-LAST:event_huy_BtnActionPerformed

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
            java.util.logging.Logger.getLogger(NguyenLieu_ChiTietNhapXuatDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NguyenLieu_ChiTietNhapXuatDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NguyenLieu_ChiTietNhapXuatDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NguyenLieu_ChiTietNhapXuatDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                NguyenLieu_ChiTietNhapXuatDialog dialog = new NguyenLieu_ChiTietNhapXuatDialog(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel chiTietCongThuc_Lbl;
    private javax.swing.JTable danhSachNguyenLieu_Table;
    private javax.swing.JButton huy_Btn;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable kiemTraDanhSachNguyenLieu_Table;
    private javax.swing.JButton kiemTraKetQua_Btn;
    private javax.swing.JLabel kiemTraKetQua_Lbl;
    private javax.swing.JPanel kiemTraKetQua_Pn;
    private javax.swing.JLabel luuY_Btn;
    private javax.swing.JButton xacNhan_Btn;
    // End of variables declaration//GEN-END:variables

    public void khoiTaoBang(String chucNang)
    {
        if (chucNang.equals("Nhap"))
        {           
            dtmDanhSachNguyenLieu = new DefaultTableModel(){
                boolean canEdit[] = new boolean [] {
                    false, false, false, false, false, true
                };
                @Override
                public boolean isCellEditable(int row, int column) 
                {
                    return canEdit[column];
                }
            };
            dtmDanhSachNguyenLieu.setColumnIdentifiers(new Object[]
            {
                "Mã nguyên liệu", "Tên nguyên liệu", "Đơn vị tính", "Giá", "Số lượng tồn", "Số lượng nhập"
            });
            danhSachNguyenLieu_Table.setModel(dtmDanhSachNguyenLieu);
            
            dtmKiemTraDanhSachNguyenLieu = new DefaultTableModel(){
                @Override
                public boolean isCellEditable(int row, int column) 
                {
                    return false;
                }
            };
            dtmKiemTraDanhSachNguyenLieu.setColumnIdentifiers(new Object[]
            {
                "Mã nguyên liệu", "Tên nguyên liệu", "Đơn vị tính", "Giá", "Số lượng tồn", "Số lượng nhập"
            });
            kiemTraDanhSachNguyenLieu_Table.setModel(dtmKiemTraDanhSachNguyenLieu);            
        }
        else
        {
            dtmDanhSachNguyenLieu = new DefaultTableModel(){
                boolean canEdit[] = new boolean [] {
                    false, false, false, false, false, true
                };
                @Override
                public boolean isCellEditable(int row, int column) 
                {
                    return canEdit[column];
                }
            };
            dtmDanhSachNguyenLieu.setColumnIdentifiers(new Object[]
            {
                "Mã nguyên liệu", "Tên nguyên liệu", "Đơn vị tính", "Giá", "Số lượng tồn", "Số lượng xuất"
            });
            danhSachNguyenLieu_Table.setModel(dtmDanhSachNguyenLieu);
            
            dtmKiemTraDanhSachNguyenLieu = new DefaultTableModel(){
                @Override
                public boolean isCellEditable(int row, int column) 
                {
                    return false;
                }
            };
            dtmKiemTraDanhSachNguyenLieu.setColumnIdentifiers(new Object[]
            {
                "Mã nguyên liệu", "Tên nguyên liệu", "Đơn vị tính", "Giá", "Số lượng tồn", "Số lượng xuất"
            });
            kiemTraDanhSachNguyenLieu_Table.setModel(dtmKiemTraDanhSachNguyenLieu);  
        }
    }
    
    public void thietLapDuLieu(NhapXuat nhapXuat)
    {
        this.nhapXuat = nhapXuat;

        Set<String> keySet = nhapXuat.getChiTietNhapXuat().keySet();
        for (int i = 0; i < danhSachNguyenLieu_Table.getRowCount(); i++)
        {
            for (String key: keySet)
            {
                if (danhSachNguyenLieu.get(i).getMaNguyenLieu().equals(key))
                {
                    danhSachNguyenLieu_Table.setValueAt(nhapXuat.getChiTietNhapXuat().get(key).toString(), i, 5);
                    //Hien thi ben phan kiem tra
                    dtmKiemTraDanhSachNguyenLieu.addRow(new Object[]
                    {
                        danhSachNguyenLieu.get(i).getMaNguyenLieu(), danhSachNguyenLieu.get(i).getTenNguyenLieu(),
                        danhSachNguyenLieu.get(i).getDonViTinh(), Util.formatCurrency(danhSachNguyenLieu.get(i).getGia()),
                        danhSachNguyenLieu.get(i).getSoLuong(), nhapXuat.getChiTietNhapXuat().get(key)
                    });
                }
            }
        }     
    }
       
    public void hienThiDanhSachNguyenLieu()
    {
        dtmDanhSachNguyenLieu.setRowCount(0);
        for (NguyenLieu nguyenLieu: danhSachNguyenLieu)
        {
            dtmDanhSachNguyenLieu.addRow(new Object[]
            {
                nguyenLieu.getMaNguyenLieu(), nguyenLieu.getTenNguyenLieu(), nguyenLieu.getDonViTinh(),
                Util.formatCurrency(nguyenLieu.getGia()), nguyenLieu.getSoLuong()
            });
        }       
    }
    
    public void hienThiDanhSachNguyenLieuKiemTra()
    {
        dtmKiemTraDanhSachNguyenLieu.setRowCount(0);
        for (int i = 0; i < danhSachNguyenLieu_Table.getRowCount(); i++)
        {
            if (danhSachNguyenLieu_Table.getValueAt(i, 5) != null)
            {
                String maNguyenLieu = (String) danhSachNguyenLieu_Table.getValueAt(i, 0);
                String tenNguyenLieu = (String) danhSachNguyenLieu_Table.getValueAt(i, 1);
                String donViTinh = (String) danhSachNguyenLieu_Table.getValueAt(i, 2);
                long gia = danhSachNguyenLieu.get(i).getGia();
                String formatted = Util.formatCurrency(gia);                
                String soLuongTon = danhSachNguyenLieu_Table.getValueAt(i, 4).toString();
                String soLuongNhapXuat = (String) danhSachNguyenLieu_Table.getValueAt(i, 5);
                dtmKiemTraDanhSachNguyenLieu.addRow(new Object[]
                {
                    maNguyenLieu, tenNguyenLieu, donViTinh, formatted, soLuongTon, soLuongNhapXuat
                });
            }
        }
    }     
    
    public void themSuaDanhSachNhapXuat()
    {
        long thanhTien = 0;
        for (int i = 0; i < danhSachNguyenLieu_Table.getRowCount(); i++)
        {
            if (danhSachNguyenLieu_Table.getValueAt(i, 5) != null)
            {
                if (danhSachNguyenLieu_Table.getValueAt(i, 5).equals("")) // Xoa di so luong cua 1 nguyen lieu 
                                                                       //    hien co (Nguyen lieu da ton
                                                                       //    tai va muon chinh sua)
                {
                    nhapXuat.getChiTietNhapXuat().remove(danhSachNguyenLieu.get(i).getMaNguyenLieu());
                }                
                else if (danhSachNguyenLieu_Table.getValueAt(i, 5).toString().matches("\\d+") == false)
                {
                    JOptionPane.showMessageDialog(rootPane, "Số lượng không hợp lệ", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                else
                {
                    if (chucNang.equals("Xuat"))
                    {
                        int soLuongTon = Integer.valueOf(danhSachNguyenLieu_Table.getValueAt(i, 4).toString());
                        int soLuongXuat = Integer.valueOf(danhSachNguyenLieu_Table.getValueAt(i, 5).toString());
                        if (soLuongXuat > soLuongTon)
                        {
                            JOptionPane.showMessageDialog(rootPane, "Số lượng xuất không thể lớn hơn số lượng hiện có trong kho", 
                                    "Lỗi", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    }
                    int soLuong = Integer.valueOf((String)danhSachNguyenLieu_Table.getValueAt(i, 5));
                    if (chucNang.equals("Nhap"))
                    {                         
                        thanhTien = thanhTien + soLuong*danhSachNguyenLieu.get(i).getGia();
                    }
                    nhapXuat.getChiTietNhapXuat().put(danhSachNguyenLieu.get(i).getMaNguyenLieu(), soLuong);                     
                }
            }                
        }
        nhapXuat.setThanhTien(thanhTien);
        
        if (chucNang.equals("Nhap"))
        {
            home.setNhapXuat(nhapXuat);
        }
        else
        {
            home2.setNhapXuat(nhapXuat);
        }
        
        this.dispose();
    }
           
    public static void setChucNang(String chucNang) {
        NguyenLieu_ChiTietNhapXuatDialog.chucNang = chucNang;
    }
    
    public void setNhapXuat(NhapXuat nhapXuat) {
        this.nhapXuat = nhapXuat;
    }        
}
