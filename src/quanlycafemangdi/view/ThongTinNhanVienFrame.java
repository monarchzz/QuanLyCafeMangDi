/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quanlycafemangdi.view;

import javax.swing.JOptionPane;
import quanlycafemangdi.Util;
import quanlycafemangdi.data.Data;
import quanlycafemangdi.model.NhanVien;

/**
 *
 * @author monar
 */
public class ThongTinNhanVienFrame extends javax.swing.JFrame {

    /**
     * Creates new form ThongTinNhanVienFrame
     */
    
    
    
    
    public static final int TAO_TAI_KHOAN = 0;
    public static final int SUA_TAI_KHOAN = 1;
    private IOnFrameDispose onFrameDispose;
    private int chucNang;
    private NhanVien nhanVienHienTai;
    private Data data;
    
    public ThongTinNhanVienFrame() {
        initComponents();
        
        data = Data.getInstance();
    }
    
    public void xetChucNang(int chucNang, IOnFrameDispose onDispose){
        this.onFrameDispose = onDispose;
        this.chucNang = chucNang;
        if (chucNang == TAO_TAI_KHOAN) {
            Util.doiPanel(buttonLayeredPanel, taoTaiKhoanPanel);
            xoaDuLieu();
        }else {
            Util.doiPanel(buttonLayeredPanel, suaTaiKhoanPanel);
        }
    }
    
    
    public void xetDuLieu(NhanVien nhanVien){
        this.nhanVienHienTai = nhanVien;
        taoDuLieu();
    }
    
    // xoa form
    private void xoaDuLieu(){
        tenNhanVienTF.setText("");
        cmndTF.setText("");
        sdtTF.setText("");
        tenDangNhapTF.setText("");
        matKhauTF.setText("");
    }
    
    // tao du lieu cho form
    private void taoDuLieu() {
        tenNhanVienTF.setText(nhanVienHienTai.getTenNhanVien());
        if (nhanVienHienTai.getGioiTinh().equals("Nam")){
            gioiTinhCB.setSelectedIndex(0);
        }else gioiTinhCB.setSelectedIndex(1);
        cmndTF.setText(nhanVienHienTai.getSoCM());
        sdtTF.setText(nhanVienHienTai.getSdt());
        
        // phan quyen chuc vu
//        if (nhanVien.getChucVu().equals("Quản lý") || nhanVien.getChucVu().equals("admin")){
//            switch(nhanVien.getChucVu()){
//                case "Quản lý":
//                    
//                    break;
//            }
//        }
        //
        tenDangNhapTF.setText(nhanVienHienTai.getTenTk());
        
        // phan quyen mk
        
    }
    
    // kiem tra rang buoc de tao tk
    private boolean kiemTra(){
        
        String loi = "";
        
        String tenNV = tenNhanVienTF.getText();
        String gioiTinh = gioiTinhCB.getSelectedItem().toString();
        String cmnd = cmndTF.getText();
        String sdt = sdtTF.getText();
        String chucVu = chucVuCB.getSelectedItem().toString();
        String tenDN = tenDangNhapTF.getText();
        String mk = String.valueOf(matKhauTF.getPassword());
        
        if (tenNV.trim().equals("")){
            loi += "Tên nhân viên không được để trống\n";
            
        }
        if (!(cmnd.matches("^[0-9]{9}$") || cmnd.matches("^[0-9]{12}$"))){
            loi += "Số chứng minh phải có 9 hoặc 12 số\n";
        }
        else if (!kiemTraSCM(cmnd)){
            loi += "Số chứng minh nhân dân đã tồn tại\n";
        }
        if (!sdt.matches("^[0-9]{10}$")){
            loi += "Số điện thoại không hợp lệ\n";
        }
        if (tenDN.trim().equals("")){
            loi += "Tên đăng nhập không được để trống\n";
        }
        else if (!kiemTraTDN(tenDN)){
            loi += "Tên đăng nhập đã tồn tại\n";
        }
        if(!mk.matches("[a-zA-Z0-9_]{6,}$")){
            loi += "Mật khẩu phải có ít nhất 6 ký tự\n";
        }
        if (!loi.equals("")){
            hienLoi(loi);
            return false;
        }else {
            return true;
        }
    }
    
    // kiemt tra rang buoc de sua tk
    private boolean kiemTraHopLe(){
        String loi = "";
        
        String tenNV = tenNhanVienTF.getText();
        String gioiTinh = gioiTinhCB.getSelectedItem().toString();
        String cmnd = cmndTF.getText();
        String sdt = sdtTF.getText();
        String chucVu = chucVuCB.getSelectedItem().toString();
        String tenDN = tenDangNhapTF.getText();
        String mk = String.valueOf(matKhauTF.getPassword());
        
        if (tenNV.trim().equals("")){
            loi += "Tên nhân viên không được để trống\n";
            
        }
        if (!(cmnd.matches("^[0-9]{9}$") || cmnd.matches("^[0-9]{12}$"))){
            loi += "Số chứng minh phải có 9 hoặc 12 số\n";
        }
        else if (!cmnd.equals(nhanVienHienTai.getSoCM()) && !kiemTraSCM(cmnd)){
            loi += "Số chứng minh nhân dân đã tồn tại\n";
        }
        if (!sdt.matches("^[0-9]{10}$")){
            loi += "Số điện thoại không hợp lệ\n";
        }
        if (tenDN.trim().equals("")){
            loi += "Tên đăng nhập không được để trống\n";
        }
        else if (!tenDN.equals(nhanVienHienTai.getTenTk()) && !kiemTraTDN(tenDN)){
            loi += "Tên đăng nhập đã tồn tại\n";
        }
        
        if(!mk.matches("[a-zA-Z0-9_]{6,}$")){
            loi += "Mật khẩu phải có ít nhất 6 ký tự\n";
        }
        if (!loi.equals("")){
            hienLoi(loi);
            return false;
        }else {
            return true;
        }
    }
    
    // kiem tra so chung minh
    private boolean kiemTraSCM(String cmnd){
        return data.kiemTraSCM(cmnd);
    }
    
    // kiem tra ten dang nhap
    private boolean kiemTraTDN(String ten){
        
        return data.kiemTraTDN(ten);
    }
    private void hienLoi(String loi){
        JOptionPane.showMessageDialog(this, loi, "Lỗi", JOptionPane.ERROR_MESSAGE);
    }
    
    //tao tk cho nhan vien
    private void taoTK(){
        String tenNV = tenNhanVienTF.getText();
        String gioiTinh = gioiTinhCB.getSelectedItem().toString();
        String cmnd = cmndTF.getText();
        String sdt = sdtTF.getText();
        String chucVu = chucVuCB.getSelectedItem().toString();
        String tenDN = tenDangNhapTF.getText();
        String mk = String.valueOf(matKhauTF.getPassword());
        
        NhanVien nv = new NhanVien(tenDN, chucVu, cmnd, sdt, gioiTinh, tenNV, mk);
        
        data.taoNhanVien(nv);
    }
    
    

    @Override
    public void dispose() {
        onFrameDispose.onFrameDispose();
        super.dispose(); //To change body of generated methods, choose Tools | Templates.
    }
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        tenNhanVienTF = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        cmndTF = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        sdtTF = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        tenDangNhapTF = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        gioiTinhCB = new javax.swing.JComboBox<>();
        chucVuCB = new javax.swing.JComboBox<>();
        matKhauTF = new javax.swing.JPasswordField();
        buttonLayeredPanel = new javax.swing.JLayeredPane();
        taoTaiKhoanPanel = new javax.swing.JPanel();
        taoTKBtn = new javax.swing.JButton();
        troVeBtn = new javax.swing.JButton();
        suaTaiKhoanPanel = new javax.swing.JPanel();
        thoatButton = new javax.swing.JButton();
        xoaButton = new javax.swing.JButton();
        suaThongTinButton = new javax.swing.JButton();
        hienMKCheckBox = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Thêm nhân viên");
        setAlwaysOnTop(true);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setFocusTraversalPolicyProvider(true);
        setLocationByPlatform(true);
        setResizable(false);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Thông tin nhân viên");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Tên nhân viên");

        tenNhanVienTF.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tenNhanVienTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tenNhanVienTFActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("Giới tính");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Chứng minh nhân dân");

        cmndTF.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("Số điện thoại");

        sdtTF.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("Chức vụ");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setText("Tên đăng nhập");

        tenDangNhapTF.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setText("Mật khẩu");

        gioiTinhCB.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        gioiTinhCB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nam", "Nữ" }));

        chucVuCB.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        chucVuCB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nhân viên bán hàng", "Quản lý" }));

        matKhauTF.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        matKhauTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                matKhauTFActionPerformed(evt);
            }
        });

        buttonLayeredPanel.setLayout(new java.awt.CardLayout());

        taoTaiKhoanPanel.setPreferredSize(new java.awt.Dimension(433, 134));

        taoTKBtn.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        taoTKBtn.setText("Tạo tài khoản");
        taoTKBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                taoTKBtnActionPerformed(evt);
            }
        });

        troVeBtn.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        troVeBtn.setText("Trở về");
        troVeBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                troVeBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout taoTaiKhoanPanelLayout = new javax.swing.GroupLayout(taoTaiKhoanPanel);
        taoTaiKhoanPanel.setLayout(taoTaiKhoanPanelLayout);
        taoTaiKhoanPanelLayout.setHorizontalGroup(
            taoTaiKhoanPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(taoTaiKhoanPanelLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(troVeBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 86, Short.MAX_VALUE)
                .addComponent(taoTKBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50))
        );
        taoTaiKhoanPanelLayout.setVerticalGroup(
            taoTaiKhoanPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(taoTaiKhoanPanelLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(taoTaiKhoanPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(taoTKBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(troVeBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(34, Short.MAX_VALUE))
        );

        buttonLayeredPanel.add(taoTaiKhoanPanel, "card2");

        thoatButton.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        thoatButton.setText("Thoát");
        thoatButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                thoatButtonActionPerformed(evt);
            }
        });

        xoaButton.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        xoaButton.setText("Xóa nhân viên");
        xoaButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                xoaButtonActionPerformed(evt);
            }
        });

        suaThongTinButton.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        suaThongTinButton.setText("Sửa thông tin");
        suaThongTinButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                suaThongTinButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout suaTaiKhoanPanelLayout = new javax.swing.GroupLayout(suaTaiKhoanPanel);
        suaTaiKhoanPanel.setLayout(suaTaiKhoanPanelLayout);
        suaTaiKhoanPanelLayout.setHorizontalGroup(
            suaTaiKhoanPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(suaTaiKhoanPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(thoatButton, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(xoaButton, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(suaThongTinButton, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        suaTaiKhoanPanelLayout.setVerticalGroup(
            suaTaiKhoanPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(suaTaiKhoanPanelLayout.createSequentialGroup()
                .addContainerGap(33, Short.MAX_VALUE)
                .addGroup(suaTaiKhoanPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(xoaButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(suaThongTinButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(thoatButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(41, 41, 41))
        );

        buttonLayeredPanel.add(suaTaiKhoanPanel, "card3");

        hienMKCheckBox.setText("Hiện thị mật khẩu");
        hienMKCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hienMKCheckBoxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(buttonLayeredPanel)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(hienMKCheckBox)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel8)
                        .addComponent(jLabel7)
                        .addComponent(tenDangNhapTF, javax.swing.GroupLayout.DEFAULT_SIZE, 353, Short.MAX_VALUE)
                        .addComponent(jLabel6)
                        .addComponent(jLabel5)
                        .addComponent(sdtTF, javax.swing.GroupLayout.DEFAULT_SIZE, 353, Short.MAX_VALUE)
                        .addComponent(cmndTF, javax.swing.GroupLayout.DEFAULT_SIZE, 353, Short.MAX_VALUE)
                        .addComponent(jLabel3)
                        .addComponent(jLabel2)
                        .addComponent(tenNhanVienTF)
                        .addComponent(jLabel4)
                        .addComponent(gioiTinhCB, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(chucVuCB, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(matKhauTF)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tenNhanVienTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(gioiTinhCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(9, 9, 9)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmndTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sdtTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(chucVuCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tenDangNhapTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(matKhauTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(hienMKCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(buttonLayeredPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void matKhauTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_matKhauTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_matKhauTFActionPerformed

    private void tenNhanVienTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tenNhanVienTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tenNhanVienTFActionPerformed

    private void troVeBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_troVeBtnActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_troVeBtnActionPerformed

    private void taoTKBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_taoTKBtnActionPerformed
        // TODO add your handling code here:
        if(kiemTra()){
            taoTK();
            JOptionPane.showMessageDialog(this, "Tạo tài khoản thành công","Thông báo",JOptionPane.INFORMATION_MESSAGE);
            dispose();
        }
    }//GEN-LAST:event_taoTKBtnActionPerformed

    private void hienMKCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hienMKCheckBoxActionPerformed
        // TODO add your handling code here:
        if (hienMKCheckBox.isSelected())
        {
            matKhauTF.setEchoChar((char)0);
        }
        else
        {
            matKhauTF.setEchoChar('●');
        }
    }//GEN-LAST:event_hienMKCheckBoxActionPerformed

    private void thoatButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_thoatButtonActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_thoatButtonActionPerformed

    private void xoaButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_xoaButtonActionPerformed
        // TODO add your handling code here:
        int mode = JOptionPane.showConfirmDialog(this, 
                "Bạn có muốn xóa tài khoản này", "Xóa tài koản", 
                JOptionPane.YES_NO_OPTION);
        
        if(mode == 0){
            data.xoaNhanVien(nhanVienHienTai);
            JOptionPane.showMessageDialog(this, "Xóa nhân viên thành công","Thông báo",JOptionPane.INFORMATION_MESSAGE);
            dispose();
        }
    }//GEN-LAST:event_xoaButtonActionPerformed

    private void suaThongTinButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_suaThongTinButtonActionPerformed
        // TODO add your handling code here:
        
        if (kiemTraHopLe()){
            int mode = JOptionPane.showConfirmDialog(this, "Bạn có muốn sửa thông tin tài khoản nhân viên", 
                    "Thông báo", JOptionPane.YES_NO_OPTION);
            
            if (mode == 0) {
                // lay du lieu nhan vien
                String tenNV = tenNhanVienTF.getText();
                String gioiTinh = gioiTinhCB.getSelectedItem().toString();
                String cmnd = cmndTF.getText();
                String sdt = sdtTF.getText();
                String chucVu = chucVuCB.getSelectedItem().toString();
                String tenDN = tenDangNhapTF.getText();
                String mk = String.valueOf(matKhauTF.getPassword());

                NhanVien nv = new NhanVien(tenDN, chucVu, cmnd, sdt, gioiTinh, tenNV, mk);
                
                data.suaThongTinNhanVien(nhanVienHienTai,nv);
                JOptionPane.showMessageDialog(this, "Sửa thông tin nhân viên thành công",
                        "Thông báo",JOptionPane.INFORMATION_MESSAGE);
                dispose();
            }
        }
    }//GEN-LAST:event_suaThongTinButtonActionPerformed

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
            java.util.logging.Logger.getLogger(ThongTinNhanVienFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ThongTinNhanVienFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ThongTinNhanVienFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ThongTinNhanVienFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ThongTinNhanVienFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLayeredPane buttonLayeredPanel;
    private javax.swing.JComboBox<String> chucVuCB;
    private javax.swing.JTextField cmndTF;
    private javax.swing.JComboBox<String> gioiTinhCB;
    private javax.swing.JCheckBox hienMKCheckBox;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPasswordField matKhauTF;
    private javax.swing.JTextField sdtTF;
    private javax.swing.JPanel suaTaiKhoanPanel;
    private javax.swing.JButton suaThongTinButton;
    private javax.swing.JButton taoTKBtn;
    private javax.swing.JPanel taoTaiKhoanPanel;
    private javax.swing.JTextField tenDangNhapTF;
    private javax.swing.JTextField tenNhanVienTF;
    private javax.swing.JButton thoatButton;
    private javax.swing.JButton troVeBtn;
    private javax.swing.JButton xoaButton;
    // End of variables declaration//GEN-END:variables
}
