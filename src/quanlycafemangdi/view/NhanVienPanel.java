/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quanlycafemangdi.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import quanlycafemangdi.Util;
import quanlycafemangdi.data.Data;
import quanlycafemangdi.model.NhanVien;

/**
 *
 * @author monar
 */
public class NhanVienPanel extends javax.swing.JPanel implements IOnFrameDispose{

    /**
     * Creates new form NhanVienPanel
     */
    private ThongTinNhanVienFrame thongTinNhanVienFrame;
    private final JLayeredPane jLayeredPane;
    private final JPanel homePanel;
    private final DefaultTableModel defaultTableModel;
    private List<NhanVien> dsNhanVien;
    private List<NhanVien> dsHienThi;
    private List<String> dsSapXep = new ArrayList<>(
            Arrays.asList("Tên tài khoản", "Họ tên", "Số chứng minh","Số điện thoại"));
    private List<String> dsTimKiem = new ArrayList<>(
            Arrays.asList("Tên tài khoản", "Họ tên","Chức vụ", 
                    "Số chứng minh", "Số điện thoại"));
    
    
    
    public NhanVienPanel(JLayeredPane jLayeredPane, JPanel homePanel) {
        initComponents();
        
        
        this.jLayeredPane = jLayeredPane;
        this.homePanel = homePanel;
        
        timKiem();
        thongTinNhanVienFrame = new ThongTinNhanVienFrame();
        
        defaultTableModel = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        bangNhanVien.setModel(defaultTableModel);
        
        khoiTaoBang();
        khoiTaoComboBox();
        
    }
    private void updateDataTable(){
        khoiTaoBang();
    }
    
    private void khoiTaoBang(){
        dsNhanVien = Data.getInstance().layDSNhanVien();
       
        
        defaultTableModel.setColumnCount(0);
        defaultTableModel.addColumn("Tên tài khoản");
        defaultTableModel.addColumn("Tên nhân viên");
        defaultTableModel.addColumn("Giới tính");
        defaultTableModel.addColumn("Chức vụ");
        defaultTableModel.addColumn("Số chứng minh");
        defaultTableModel.addColumn("Số điện thoại");
            
        taoDSBang(dsNhanVien);
        
        
    }
    private void taoDSBang(List<NhanVien> ds){
        dsHienThi = sapXepNhanVien(ds);
        defaultTableModel.setRowCount(0);
        for(NhanVien item: dsHienThi) {
            defaultTableModel.addRow(new Object[]{item.getTenTk(), 
                item.getTenNhanVien(), 
                item.getGioiTinh(), 
                item.getChucVu(), 
                item.getSoCM(), 
                item.getSdt()});
        }
    }

    private void khoiTaoComboBox() {
        // Tao SapXepComboBox
        for(String item : dsSapXep) {
            sapXepComboBox.addItem(item);
        }
        
        // Tao TimKiemComboBox
        for (String item : dsTimKiem){
            timKiemComboBox.addItem(item);
        }
        
    }
    
    private void timKiem(){
        timKiemTextField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timKiemNhanVien();
            }
        });
    }
    private void timKiemNhanVien(){
        String kyTuCanTim = timKiemTextField.getText();
        if (kyTuCanTim.equals("")){
            taoDSBang(dsNhanVien);
            return;
        }
        
        ArrayList<NhanVien> ds = new ArrayList<>();
        int index = timKiemComboBox.getSelectedIndex();
        
        switch(index){
            case 0: // tim kiem theo ten TK
                for (NhanVien item : dsNhanVien){
                    if (item.getTenTk().toLowerCase()
                            .contains(kyTuCanTim.trim().toLowerCase())){
                        
                        ds.add(item);
                    }
                }
                break;
            case 1: // tim kiem theo ho ten
                for (NhanVien item : dsNhanVien){
                    if (item.getTenNhanVien().toLowerCase()
                            .contains(kyTuCanTim.trim().toLowerCase())){
                        
                        ds.add(item);
                    }
                }
                break;
            case 2:// tim kiem theo chuc vu
                for (NhanVien item : dsNhanVien){
                    if (item.getChucVu().toLowerCase()
                            .contains(kyTuCanTim.trim().toLowerCase())){
                        
                        ds.add(item);
                    }
                }
                break;
            case 3: // tim kiem theo co cm
                for (NhanVien item : dsNhanVien){
                    if (item.getSoCM().toLowerCase()
                            .contains(kyTuCanTim.trim().toLowerCase())){
                        
                        ds.add(item);
                    }
                }
                break;
            case 4:
                for (NhanVien item : dsNhanVien){
                    if (item.getSdt().toLowerCase()
                            .contains(kyTuCanTim.trim().toLowerCase())){
                        
                        ds.add(item);
                    }
                }
                break;
        }
        
        taoDSBang(ds);
    }
    
    private List<NhanVien> sapXepNhanVien(List<NhanVien> dsNguon){
        
        Collections.sort(dsNguon,new Comparator<NhanVien>() {
            @Override
            public int compare(NhanVien nv1, NhanVien nv2) {
                return giaTriSapXep(nv1,nv2);
            }
        });
        
        return dsNguon;
    }
    private int giaTriSapXep(NhanVien nv1, NhanVien nv2){
        int index = sapXepComboBox.getSelectedIndex();
        switch(index) {
            case 0: //theo ten tk
                return nv1.getTenTk().compareTo(nv2.getTenTk());
            case 1: // Ho ten
                return nv1.getTenNhanVien().compareTo(nv2.getTenNhanVien());
            case 2: //so cm
                return nv1.getSoCM().compareTo(nv2.getSoCM());
            case 3: // so dien thoai
                return nv1.getSdt().compareTo(nv2.getSdt());
        }

        return 0;
    }
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        quay_lai_btn = new javax.swing.JButton();
        timKiemTextField = new javax.swing.JTextField();
        timKiemButton = new javax.swing.JButton();
        themNhanVienButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        bangNhanVien = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        sapXepComboBox = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        timKiemComboBox = new javax.swing.JComboBox<>();

        setPreferredSize(new java.awt.Dimension(605, 334));

        quay_lai_btn.setText("Quay Lại");
        quay_lai_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                quay_lai_btnActionPerformed(evt);
            }
        });

        timKiemTextField.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        timKiemTextField.setToolTipText("Tìm kiếm nhân viên");
        timKiemTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                timKiemTextFieldActionPerformed(evt);
            }
        });

        timKiemButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/HinhAnh/search.png"))); // NOI18N
        timKiemButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                timKiemButtonActionPerformed(evt);
            }
        });

        themNhanVienButton.setText("Thêm nhân viên");
        themNhanVienButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                themNhanVienButtonActionPerformed(evt);
            }
        });

        bangNhanVien.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        bangNhanVien.setToolTipText("Nhấn vào để chỉnh sửa");
        bangNhanVien.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        bangNhanVien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bangNhanVienMouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                bangNhanVienMouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(bangNhanVien);

        jLabel1.setText("Sắp xếp theo");

        sapXepComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sapXepComboBoxActionPerformed(evt);
            }
        });

        jLabel2.setText("TÌm kiếm theo");

        timKiemComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                timKiemComboBoxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 585, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(quay_lai_btn)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(timKiemComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(sapXepComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(timKiemTextField)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(timKiemButton)
                                .addGap(18, 18, 18)
                                .addComponent(themNhanVienButton)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(themNhanVienButton, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(timKiemTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(quay_lai_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(timKiemButton)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(sapXepComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel1))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(timKiemComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 254, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void quay_lai_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_quay_lai_btnActionPerformed
        // TODO add your handling code here:
        Util.doiPanel(jLayeredPane, homePanel);
    }//GEN-LAST:event_quay_lai_btnActionPerformed

    private void timKiemTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_timKiemTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_timKiemTextFieldActionPerformed

    private void sapXepComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sapXepComboBoxActionPerformed
        // TODO add your handling code here:
        taoDSBang(dsHienThi);
    }//GEN-LAST:event_sapXepComboBoxActionPerformed

    private void themNhanVienButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_themNhanVienButtonActionPerformed
        // TODO add your handling code here:
        thongTinNhanVienFrame.xetChucNang(ThongTinNhanVienFrame.TAO_TAI_KHOAN,this);
        thongTinNhanVienFrame.setVisible(true);
        
    }//GEN-LAST:event_themNhanVienButtonActionPerformed

    private void timKiemButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_timKiemButtonActionPerformed
        // TODO add your handling code here:
        timKiemNhanVien();
    }//GEN-LAST:event_timKiemButtonActionPerformed

    private void timKiemComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_timKiemComboBoxActionPerformed
        // TODO add your handling code here:
        timKiemNhanVien();
    }//GEN-LAST:event_timKiemComboBoxActionPerformed

    private void bangNhanVienMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bangNhanVienMouseClicked
        // TODO add your handling code here:
        int index = bangNhanVien.getSelectedRow();
        thongTinNhanVienFrame.xetChucNang(ThongTinNhanVienFrame.SUA_TAI_KHOAN,this);
        thongTinNhanVienFrame.xetDuLieu(dsHienThi.get(index));
        thongTinNhanVienFrame.setVisible(true);
    }//GEN-LAST:event_bangNhanVienMouseClicked

    private void bangNhanVienMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bangNhanVienMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_bangNhanVienMouseReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable bangNhanVien;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton quay_lai_btn;
    private javax.swing.JComboBox<String> sapXepComboBox;
    private javax.swing.JButton themNhanVienButton;
    private javax.swing.JButton timKiemButton;
    private javax.swing.JComboBox<String> timKiemComboBox;
    private javax.swing.JTextField timKiemTextField;
    // End of variables declaration//GEN-END:variables

    @Override
    public void onFrameDispose() {
        updateDataTable();       
    }    
}
