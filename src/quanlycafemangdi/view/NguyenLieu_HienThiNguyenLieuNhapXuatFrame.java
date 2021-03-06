/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quanlycafemangdi.view;

import java.util.ArrayList;
import java.util.Set;
import javax.swing.table.DefaultTableModel;
import quanlycafemangdi.Design;
import quanlycafemangdi.Util;
import quanlycafemangdi.model.NguyenLieu;
import quanlycafemangdi.model.NhapXuat;

/**
 *
 * @author admin
 */
public class NguyenLieu_HienThiNguyenLieuNhapXuatFrame extends javax.swing.JFrame {
    
    private ArrayList<NguyenLieu> danhSachNguyenLieu = new ArrayList<>();
        
    private NhapXuat nhapXuat;
    
    private DefaultTableModel dtmDanhSachNguyenLieuNhap;
    private DefaultTableModel dtmDanhSachNguyenLieuXuat;
    
    public NguyenLieu_HienThiNguyenLieuNhapXuatFrame() {
        initComponents();
        
        Design.thietKeBang(danhSachNguyenLieuNhapXuat_Table, "Nho");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        danhSachNguyenLieuNhapXuat_Lbl = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        danhSachNguyenLieuNhapXuat_Table = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Danh sách nguyên liệu được nhập/xuất");
        setBackground(new java.awt.Color(255, 255, 255));
        setResizable(false);

        danhSachNguyenLieuNhapXuat_Lbl.setBackground(new java.awt.Color(114, 102, 186));
        danhSachNguyenLieuNhapXuat_Lbl.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        danhSachNguyenLieuNhapXuat_Lbl.setForeground(new java.awt.Color(255, 255, 255));
        danhSachNguyenLieuNhapXuat_Lbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        danhSachNguyenLieuNhapXuat_Lbl.setText("Danh sách nguyên liệu");
        danhSachNguyenLieuNhapXuat_Lbl.setOpaque(true);

        danhSachNguyenLieuNhapXuat_Table.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        danhSachNguyenLieuNhapXuat_Table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        danhSachNguyenLieuNhapXuat_Table.setIntercellSpacing(new java.awt.Dimension(0, 1));
        danhSachNguyenLieuNhapXuat_Table.setSelectionBackground(new java.awt.Color(0, 204, 255));
        danhSachNguyenLieuNhapXuat_Table.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(danhSachNguyenLieuNhapXuat_Table);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 761, Short.MAX_VALUE)
            .addComponent(danhSachNguyenLieuNhapXuat_Lbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(danhSachNguyenLieuNhapXuat_Lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 316, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(NguyenLieu_HienThiNguyenLieuNhapXuatFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NguyenLieu_HienThiNguyenLieuNhapXuatFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NguyenLieu_HienThiNguyenLieuNhapXuatFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NguyenLieu_HienThiNguyenLieuNhapXuatFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NguyenLieu_HienThiNguyenLieuNhapXuatFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel danhSachNguyenLieuNhapXuat_Lbl;
    private javax.swing.JTable danhSachNguyenLieuNhapXuat_Table;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
        
    public void khoiTaoBangChiTietNhap()
    {
        dtmDanhSachNguyenLieuNhap = new DefaultTableModel(){
        @Override
            public boolean isCellEditable(int row, int column) 
            {
                return false;
            }
        };
        dtmDanhSachNguyenLieuNhap.setColumnIdentifiers(new Object[]
        {
            "Mã nguyên liệu", "Tên nguyên liệu", "Đơn vị tính", "Giá", "Số lượng nhập", "Thành tiền"
        });      
        danhSachNguyenLieuNhapXuat_Table.setModel(dtmDanhSachNguyenLieuNhap);        
    }
    
    public void khoiTaoBangChiTietXuat()
    {
        dtmDanhSachNguyenLieuXuat = new DefaultTableModel(){
        @Override
            public boolean isCellEditable(int row, int column) 
            {
                return false;
            }
        };
        dtmDanhSachNguyenLieuXuat.setColumnIdentifiers(new Object[]
        {
            "Mã nguyên liệu", "Tên nguyên liệu", "Đơn vị tính", "Giá", "Số lượng xuất"
        });      
        danhSachNguyenLieuNhapXuat_Table.setModel(dtmDanhSachNguyenLieuXuat);        
    }    
    
    public void hienThiNguyenLieuNhap(String trangThai)
    {
        if (trangThai.equals("0"))
        {
            Set<String> keySet = nhapXuat.getChiTietNhapXuat().keySet();
            for (String key: keySet)
            {
                for (int i = 0; i < danhSachNguyenLieu.size(); i++)
                {
                    if (danhSachNguyenLieu.get(i).getMaNguyenLieu().equals(key))
                    {
                        long gia = danhSachNguyenLieu.get(i).getGia();
                        int soLuong = nhapXuat.getChiTietNhapXuat().get(key);
                        long thanhTien = gia*soLuong;
                        dtmDanhSachNguyenLieuNhap.addRow(new Object[]
                        {
                            danhSachNguyenLieu.get(i).getMaNguyenLieu(), danhSachNguyenLieu.get(i).getTenNguyenLieu(),
                            danhSachNguyenLieu.get(i).getDonViTinh(), Util.formatCurrency(gia), soLuong, 
                            Util.formatCurrency(thanhTien)
                        });
                    }
                }
            }            
        }
        else // Khong hien thi thanh tien khi tra nguyen lieu
        {
            Set<String> keySet = nhapXuat.getChiTietNhapXuat().keySet();
            for (String key: keySet)
            {
                for (int i = 0; i < danhSachNguyenLieu.size(); i++)
                {
                    if (danhSachNguyenLieu.get(i).getMaNguyenLieu().equals(key))
                    {
                        long gia = danhSachNguyenLieu.get(i).getGia();
                        int soLuong = nhapXuat.getChiTietNhapXuat().get(key);
                        dtmDanhSachNguyenLieuNhap.addRow(new Object[]
                        {
                            danhSachNguyenLieu.get(i).getMaNguyenLieu(), danhSachNguyenLieu.get(i).getTenNguyenLieu(),
                            danhSachNguyenLieu.get(i).getDonViTinh(), Util.formatCurrency(gia), soLuong, 
                            "0"
                        });
                    }
                }
            }            
        }

    }
    
    public void hienThiNguyenLieuXuat()
    {       
        Set<String> keySet = nhapXuat.getChiTietNhapXuat().keySet();
        for (String key: keySet)
        {
            for (int i = 0; i < danhSachNguyenLieu.size(); i++)
            {
                if (danhSachNguyenLieu.get(i).getMaNguyenLieu().equals(key))
                {
                    long gia = danhSachNguyenLieu.get(i).getGia();
                    int soLuong = nhapXuat.getChiTietNhapXuat().get(key);
                    dtmDanhSachNguyenLieuXuat.addRow(new Object[]
                    {
                        danhSachNguyenLieu.get(i).getMaNguyenLieu(), danhSachNguyenLieu.get(i).getTenNguyenLieu(),
                        danhSachNguyenLieu.get(i).getDonViTinh(), Util.formatCurrency(gia), soLuong
                    });
                }
            }
        }
    }    
    
    public void setNhapXuat(NhapXuat nhapXuat) {
        this.nhapXuat = nhapXuat;
    }

    public void setDanhSachNguyenLieu(ArrayList<NguyenLieu> danhSachNguyenLieu) {
        this.danhSachNguyenLieu = danhSachNguyenLieu;
    }    
}
