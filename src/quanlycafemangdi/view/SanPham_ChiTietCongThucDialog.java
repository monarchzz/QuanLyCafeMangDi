/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quanlycafemangdi.view;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Set;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import quanlycafemangdi.Design;
import quanlycafemangdi.Util;
import quanlycafemangdi.model.CongThuc;
import quanlycafemangdi.model.NguyenLieu;

/**
 *
 * @author admin
 */
public class SanPham_ChiTietCongThucDialog extends javax.swing.JDialog {

    private SanPham_ThemFrame home;
    private SanPham_SuaFrame home2;
    
    private static String chucNang;
    
    private ArrayList<NguyenLieu> danhSachNguyenLieu = new ArrayList<>();
    private ArrayList<String> danhSachMaCongThuc = new ArrayList<>();
    
    private DefaultTableModel dtmChiTietCongThuc;
    private DefaultTableModel dtmKiemTraDanhSachNguyenLieu;
    
    private CongThuc congThuc;    
    
    public SanPham_ChiTietCongThucDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        if (chucNang.equals("Them"))
        {
            home = (SanPham_ThemFrame)parent;
            danhSachNguyenLieu = home.getDanhSachNguyenLieu();
        }
        else
        {
            home2 = (SanPham_SuaFrame)parent;
            danhSachNguyenLieu = home2.getDanhSachNguyenLieu();
        }
        
        dtmChiTietCongThuc = (DefaultTableModel)chiTietCongThuc_Table.getModel();
        dtmKiemTraDanhSachNguyenLieu = (DefaultTableModel)kiemTraDanhSachNguyenLieu_Table.getModel();
        
        Design.thietKeBang(chiTietCongThuc_Table, "Lon");
        Design.thietKeBang(kiemTraDanhSachNguyenLieu_Table, "Lon");
        
        hienThiDanhSachNguyenLieu(danhSachNguyenLieu);   
        layDanhSachMaCongThuc();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        kiemTraKetQua_Pn = new javax.swing.JPanel();
        kiemTraKetQua_Lbl = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        kiemTraDanhSachNguyenLieu_Table = new javax.swing.JTable();
        kiemTraCachLam_Lbl = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        kiemTraCachLam_TA = new javax.swing.JTextArea();
        jPanel1 = new javax.swing.JPanel();
        chiTietCongThuc_Lbl = new javax.swing.JLabel();
        kiemTraKetQua_Btn = new javax.swing.JButton();
        luuY_Btn = new javax.swing.JLabel();
        huy_Btn = new javax.swing.JButton();
        cachLam_Lbl = new javax.swing.JLabel();
        xacNhan_Btn = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        chiTietCongThuc_Table = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        cachLam_TA = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        kiemTraKetQua_Pn.setBackground(new java.awt.Color(255, 255, 255));
        kiemTraKetQua_Pn.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        kiemTraKetQua_Lbl.setBackground(new java.awt.Color(114, 102, 186));
        kiemTraKetQua_Lbl.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        kiemTraKetQua_Lbl.setForeground(new java.awt.Color(255, 255, 255));
        kiemTraKetQua_Lbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        kiemTraKetQua_Lbl.setText("Kiểm tra kết quả");
        kiemTraKetQua_Lbl.setOpaque(true);

        kiemTraDanhSachNguyenLieu_Table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã nguyên liệu", "Tên nguyên liệu", "Đơn vị tính", "Số lượng"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(kiemTraDanhSachNguyenLieu_Table);

        kiemTraCachLam_Lbl.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        kiemTraCachLam_Lbl.setText("Cách làm:");

        kiemTraCachLam_TA.setEditable(false);
        kiemTraCachLam_TA.setColumns(20);
        kiemTraCachLam_TA.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        kiemTraCachLam_TA.setRows(5);
        jScrollPane4.setViewportView(kiemTraCachLam_TA);

        javax.swing.GroupLayout kiemTraKetQua_PnLayout = new javax.swing.GroupLayout(kiemTraKetQua_Pn);
        kiemTraKetQua_Pn.setLayout(kiemTraKetQua_PnLayout);
        kiemTraKetQua_PnLayout.setHorizontalGroup(
            kiemTraKetQua_PnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(kiemTraKetQua_Lbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(kiemTraKetQua_PnLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(kiemTraCachLam_Lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4)
                .addContainerGap())
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 498, Short.MAX_VALUE)
        );
        kiemTraKetQua_PnLayout.setVerticalGroup(
            kiemTraKetQua_PnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kiemTraKetQua_PnLayout.createSequentialGroup()
                .addComponent(kiemTraKetQua_Lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addGroup(kiemTraKetQua_PnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(kiemTraCachLam_Lbl)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        chiTietCongThuc_Lbl.setBackground(new java.awt.Color(114, 102, 186));
        chiTietCongThuc_Lbl.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        chiTietCongThuc_Lbl.setForeground(new java.awt.Color(255, 255, 255));
        chiTietCongThuc_Lbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        chiTietCongThuc_Lbl.setText("Chi tiết công thức");
        chiTietCongThuc_Lbl.setOpaque(true);

        kiemTraKetQua_Btn.setBackground(new java.awt.Color(32, 136, 203));
        kiemTraKetQua_Btn.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        kiemTraKetQua_Btn.setForeground(new java.awt.Color(255, 255, 255));
        kiemTraKetQua_Btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/HinhAnh/kiemTraWhite.png"))); // NOI18N
        kiemTraKetQua_Btn.setText("Kiểm tra kết quả");
        kiemTraKetQua_Btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kiemTraKetQua_BtnActionPerformed(evt);
            }
        });

        luuY_Btn.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        luuY_Btn.setText("*Lưu ý: nhấn Enter mỗi khi hiệu chỉnh số lượng để tránh sai sót");

        huy_Btn.setBackground(new java.awt.Color(32, 136, 203));
        huy_Btn.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        huy_Btn.setForeground(new java.awt.Color(255, 255, 255));
        huy_Btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/HinhAnh/huyWhite.png"))); // NOI18N
        huy_Btn.setText("Hủy");
        huy_Btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                huy_BtnActionPerformed(evt);
            }
        });

        cachLam_Lbl.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cachLam_Lbl.setText("Cách làm:");

        xacNhan_Btn.setBackground(new java.awt.Color(32, 136, 203));
        xacNhan_Btn.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        xacNhan_Btn.setForeground(new java.awt.Color(255, 255, 255));
        xacNhan_Btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/HinhAnh/xacNhanWhite.png"))); // NOI18N
        xacNhan_Btn.setText("Xác nhận");
        xacNhan_Btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                xacNhan_BtnActionPerformed(evt);
            }
        });

        chiTietCongThuc_Table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã nguyên liệu", "Tên nguyên liệu", "Đơn vị tính", "Số lượng"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        chiTietCongThuc_Table.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                chiTietCongThuc_TableKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(chiTietCongThuc_Table);

        cachLam_TA.setColumns(20);
        cachLam_TA.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cachLam_TA.setRows(5);
        jScrollPane2.setViewportView(cachLam_TA);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 26, Short.MAX_VALUE)
                        .addComponent(xacNhan_Btn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(kiemTraKetQua_Btn, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(huy_Btn, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(cachLam_Lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2)))
                .addContainerGap())
            .addComponent(chiTietCongThuc_Lbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(luuY_Btn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane1)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(chiTietCongThuc_Lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 308, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(luuY_Btn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cachLam_Lbl)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(huy_Btn)
                    .addComponent(kiemTraKetQua_Btn)
                    .addComponent(xacNhan_Btn))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(kiemTraKetQua_Pn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(kiemTraKetQua_Pn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void xacNhan_BtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_xacNhan_BtnActionPerformed
         if (congThuc.getMaCT() == null && chucNang.equals("Them")) // Chuc nang: them, cong thuc: chua duoc tao
        {
            themSuaChiTietCongThuc("ThemFrame");         
        }
        else if (congThuc.getMaCT() != null && chucNang.equals("Them")) // Bam xac nhan dong nghia la da tao cong thuc (congThuc.getMaCT != null)
                                                                        //  Tuy nhien, gia su sau do ta van chua bam them/sua, ta muon sua lai cong thuc vua tao ra 
                                                                        //  nen bam vao Chi Tiet Cong Thuc. Khi nay se khong the bam xac nhan duoc nua
                                                                        //  vi luc nay cong thuc da ton tai (congThuc.getMaCT () != null).
                                                                        //  Dieu kien nay la de khac phuc viec do
        {
            themSuaChiTietCongThuc("ThemFrame");           
        }
        else if (congThuc.getMaCT() == null && chucNang.equals("ChinhSua"))
        {
            themSuaChiTietCongThuc("SuaFrame");         
        }
        else if (congThuc.getMaCT() != null && chucNang.equals("ChinhSua"))
        {
            themSuaChiTietCongThuc("SuaFrame");          
        }
    }//GEN-LAST:event_xacNhan_BtnActionPerformed

    private void huy_BtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_huy_BtnActionPerformed
        this.dispose();
    }//GEN-LAST:event_huy_BtnActionPerformed

    private void chiTietCongThuc_TableKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_chiTietCongThuc_TableKeyPressed

    }//GEN-LAST:event_chiTietCongThuc_TableKeyPressed

    private void kiemTraKetQua_BtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kiemTraKetQua_BtnActionPerformed
        hienThiDanhSachNguyenLieuKiemTra();
    }//GEN-LAST:event_kiemTraKetQua_BtnActionPerformed

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
            java.util.logging.Logger.getLogger(SanPham_ChiTietCongThucDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SanPham_ChiTietCongThucDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SanPham_ChiTietCongThucDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SanPham_ChiTietCongThucDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                SanPham_ChiTietCongThucDialog dialog = new SanPham_ChiTietCongThucDialog(new javax.swing.JFrame(), true);
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
    private javax.swing.JLabel cachLam_Lbl;
    private javax.swing.JTextArea cachLam_TA;
    private javax.swing.JLabel chiTietCongThuc_Lbl;
    private javax.swing.JTable chiTietCongThuc_Table;
    private javax.swing.JButton huy_Btn;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JLabel kiemTraCachLam_Lbl;
    private javax.swing.JTextArea kiemTraCachLam_TA;
    private javax.swing.JTable kiemTraDanhSachNguyenLieu_Table;
    private javax.swing.JButton kiemTraKetQua_Btn;
    private javax.swing.JLabel kiemTraKetQua_Lbl;
    private javax.swing.JPanel kiemTraKetQua_Pn;
    private javax.swing.JLabel luuY_Btn;
    private javax.swing.JButton xacNhan_Btn;
    // End of variables declaration//GEN-END:variables

    public void thietLapDuLieu(CongThuc congThuc)
    {
        this.congThuc = congThuc;

        Set<String> keySet = congThuc.getChiTietCT().keySet();
        for (int i = 0; i < chiTietCongThuc_Table.getRowCount(); i++)
        {
            for (String key: keySet)
            {
                if (danhSachNguyenLieu.get(i).getMaNguyenLieu().equals(key))
                {
                    chiTietCongThuc_Table.setValueAt(congThuc.getChiTietCT().get(key).toString(), i, 3);
                    // Hien thi ben phan kiem tra
                    dtmKiemTraDanhSachNguyenLieu.addRow(new Object[]
                    {
                        danhSachNguyenLieu.get(i).getMaNguyenLieu(), danhSachNguyenLieu.get(i).getTenNguyenLieu(),
                        danhSachNguyenLieu.get(i).getDonViTinh(), congThuc.getChiTietCT().get(key)
                    });
                }
            }
        }
        cachLam_TA.setText(congThuc.getCachLam());
        kiemTraCachLam_TA.setText(congThuc.getCachLam());
    }
 
    public void layDanhSachMaCongThuc()
    {
        Connection connect = Util.getConnection();
        String query = "select * from CongThuc";
        try
        {
            PreparedStatement ps = connect.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                danhSachMaCongThuc.add(rs.getString("maCT"));
            }
            rs.close();
            ps.close();
            connect.close();
        }catch (SQLException ex)
        {
            System.out.println("Lay danh sach ma cong thuc that bai");
        }          
    }
     
    public String taoMaCongThuc()
    {
        int STT = 0;
        String maCongThuc = "";
        maCongThuc = "CT" + String.valueOf(STT);
        while (danhSachMaCongThuc.contains(maCongThuc))
       {
            STT++;
            maCongThuc = "CT" + String.valueOf(STT);
        }           
        return maCongThuc;        
    }       
    
//    public void sapXepDanhSachNguyenLieu()
//    {
//        danhSachNguyenLieu.sort(new Comparator<NguyenLieu>() {
//            @Override
//            public int compare(NguyenLieu o1, NguyenLieu o2) 
//            {
//                String ten1 = o1.getTenNguyenLieu();
//                String ten2 = o2.getTenNguyenLieu();
//                return ten1.compareTo(ten2);
//            }           
//        });
//    }    
    
    public void hienThiDanhSachNguyenLieu(ArrayList<NguyenLieu> danhSachNguyenLieu)
    {
        dtmChiTietCongThuc.setRowCount(0);
        //layDanhSachNguyenLieu();
        //sapXepDanhSachNguyenLieu();
        for (NguyenLieu nguyenLieu: danhSachNguyenLieu)
        {
            dtmChiTietCongThuc.addRow(new Object[]
            {
                nguyenLieu.getMaNguyenLieu(), nguyenLieu.getTenNguyenLieu(), nguyenLieu.getDonViTinh()
            });
        }            
    }
    
    public void hienThiDanhSachNguyenLieuKiemTra()
    {
        dtmKiemTraDanhSachNguyenLieu.setRowCount(0);
        for (int i = 0; i < chiTietCongThuc_Table.getRowCount(); i++)
        {
            if (chiTietCongThuc_Table.getValueAt(i, 3) != null)
            {
                String maNguyenLieu = (String) chiTietCongThuc_Table.getValueAt(i, 0);
                String tenNguyenLieu = (String) chiTietCongThuc_Table.getValueAt(i, 1);
                String donViTinh = (String) chiTietCongThuc_Table.getValueAt(i, 2);
                String soLuong = (String) chiTietCongThuc_Table.getValueAt(i, 3);
                dtmKiemTraDanhSachNguyenLieu.addRow(new Object[]
                {
                    maNguyenLieu, tenNguyenLieu, donViTinh, soLuong
                });
            }
        }
        kiemTraCachLam_TA.setText(cachLam_TA.getText());
    }
    
    public void themSuaChiTietCongThuc(String truongHop)
    {
        if (truongHop.equals("ThemFrame"))
        {
            congThuc.setMaCT(taoMaCongThuc());
        }
        else if (truongHop.equals("SuaFrame")) // Khong tao moi ma cong thuc khi chinh sua
        {
            
        }             

        for (int i = 0; i < chiTietCongThuc_Table.getRowCount(); i++)
        {
            if (chiTietCongThuc_Table.getValueAt(i, 3) != null)
            {
                if (chiTietCongThuc_Table.getValueAt(i, 3).equals("")) // Xoa di so luong cua 1 nguyen lieu 
                                                                       //    hien co (Nguyen lieu da ton
                                                                       //    tai trong cong thuc va muon chinh sua)
                {
                    congThuc.getChiTietCT().remove(danhSachNguyenLieu.get(i).getMaNguyenLieu());
                }
                else if (chiTietCongThuc_Table.getValueAt(i, 3).toString().matches("\\d+") == false)
                {
                    JOptionPane.showMessageDialog(rootPane, "Số lượng không hợp lệ", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                else
                {
                    int soLuong = Integer.valueOf((String)chiTietCongThuc_Table.getValueAt(i, 3));
                    congThuc.getChiTietCT().put(danhSachNguyenLieu.get(i).getMaNguyenLieu(), soLuong);                      
                }
            }
//            else
//            {
//                congThuc.getChiTietCT().remove(danhSachNguyenLieu.get(i).getMaNguyenLieu());
//            }
        }        
        congThuc.setCachLam(cachLam_TA.getText());
        
        if (truongHop.equals("ThemFrame"))
        {
            home.setCongThuc(congThuc);
        }
        else if (truongHop.equals("SuaFrame"))
        {
            home2.setCongThuc(congThuc);
        }                    
        this.dispose();
    }
      
    public static void setChucNang(String chucNang) {
        SanPham_ChiTietCongThucDialog.chucNang = chucNang;
    }    

    public void setDanhSachNguyenLieu(ArrayList<NguyenLieu> danhSachNguyenLieu) {
        this.danhSachNguyenLieu = danhSachNguyenLieu;
    }   
}
