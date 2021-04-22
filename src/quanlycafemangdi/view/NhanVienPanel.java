/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quanlycafemangdi.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import quanlycafemangdi.data.Data;
import quanlycafemangdi.model.NhanVien;
import quanlycafemangdi.model.ThongTinDangNhap;

/**
 *
 * @author monar
 */
public class NhanVienPanel extends javax.swing.JPanel{

    /**
     * Creates new form NhanVienPanel
     */
    private final DefaultTableModel defaultTableModel;
    private List<NhanVien> dsNhanVien;
    private List<NhanVien> dsHienThi;
    private List<String> dsSapXep = new ArrayList<>(
            Arrays.asList("Tên tài khoản", "Họ tên", "Số chứng minh","Số điện thoại"));
    private List<String> dsTimKiem = new ArrayList<>(
            Arrays.asList("Tên tài khoản", "Họ tên","Chức vụ", 
                    "Số chứng minh", "Số điện thoại"));
    private final Data data;
    
    
    public NhanVienPanel() {
        initComponents();
        
        this.data = Data.getInstance();
        
        defaultTableModel = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        timKiem();
        
        
        khoiTaoBang();
        khoiTaoComboBox();
        taoThongTinNhanVien();
        
    }
    private void updateDataTable(){
        khoiTaoBang();
    }
    
    private void khoiTaoBang(){
        dsNhanVien = Data.getInstance().layDSNhanVien();
        
        bangNhanVien.setModel(defaultTableModel);
        bangNhanVien.setFont(new Font("Tahoma", Font.PLAIN, 14));
        bangNhanVien.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));
        bangNhanVien.getTableHeader().setBackground(new Color(32,136,203));
        bangNhanVien.getTableHeader().setForeground(Color.white);
       
        
        defaultTableModel.setColumnCount(0);
        defaultTableModel.addColumn("Tên tài khoản");
        defaultTableModel.addColumn("Tên nhân viên");
        defaultTableModel.addColumn("Giới tính");
        defaultTableModel.addColumn("Chức vụ");
//        defaultTableModel.addColumn("Số chứng minh");
        defaultTableModel.addColumn("Số điện thoại");
        
        
        taoDSBang(dsNhanVien);
        
        
    }
    private void taoDSBang(List<NhanVien> ds){
        ArrayList<NhanVien> tmp = new ArrayList<>();
        //xoa nhan vien hien tai 
        for (NhanVien nv: ds){
            if (!nv.getTenTk().equals(ThongTinDangNhap.getTenDangNhap())){
                tmp.add(nv);
            }
        }
        dsHienThi = sapXepNhanVien(tmp);
        defaultTableModel.setRowCount(0);
        for(NhanVien item: dsHienThi) {
            defaultTableModel.addRow(new Object[]{item.getTenTk(), 
                item.getTenNhanVien(), 
                item.getGioiTinh(), 
                item.getChucVu(), 
//                item.getSoCM(), 
                item.getSdt()});
        }
    }
    private void taoThongTinNhanVien(){
        
        // neu la nhan vien ban hang thì khong cho them nha vien
        if (ThongTinDangNhap.getChucVu().equals("Nhân viên bán hàng")){
            themNhanVienButton.setEnabled(false);
        }
        
        for (NhanVien nv : dsNhanVien){
            if (nv.getTenTk().equals(ThongTinDangNhap.getTenDangNhap())){
                hienThongTinNhanVien(nv,false);
                chucNangPanel.removeAll();
                chucNangPanel.add(panelTam);
                chucNangPanel.repaint();
                chucNangPanel.validate();
                return;
            }
        }
        
        
    }
    private void chucVuCBMode(int mode){
        chucVuCB.removeAllItems();
        // xoa 
        if (mode == 0){
            chucVuCB.addItem("Nhân viên bán hàng");
            chucVuCB.addItem("Quản lý");
        }else {
            chucVuCB.addItem("Nhân viên bán hàng");
            chucVuCB.addItem("Quản lý");
            chucVuCB.addItem("admin");
        }
    }
    private void hienThongTinNhanVien(NhanVien nv, boolean hienTF){
        if (ThongTinDangNhap.getChucVu().equals("admin")){
            chucVuCBMode(1);
        }else {
            chucVuCBMode(0);
        }
        
        
        tenNhanVienTF.setText(nv.getTenNhanVien());
        gioiTinhCB.setSelectedItem(nv.getGioiTinh());
        cmndTF.setText(nv.getSoCM());
        sdtTF.setText(nv.getSdt());
        chucVuCB.setSelectedItem(nv.getChucVu());
        tenDangNhapTF.setText(nv.getTenTk());

        hienTF(hienTF);
        
    }
    private void hienTF(boolean flag){
        tenNhanVienTF.setEnabled(flag);
        gioiTinhCB.setEnabled(flag);
        cmndTF.setEnabled(flag);
        sdtTF.setEnabled(flag);
        chucVuCB.setEnabled(flag);
        tenDangNhapTF.setEnabled(flag);
        matKhauPanel.setVisible(flag);
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
        int mode = sapXepTGCB.getSelectedIndex();
        int index = sapXepComboBox.getSelectedIndex();
        
        if (mode == 0){
            switch(index) {
                case 0 -> {
                    //theo ten tk
                    return nv1.getTenTk().compareTo(nv2.getTenTk());
                }
                case 1 -> {
                    // Ho ten
                    return nv1.getTenNhanVien().compareTo(nv2.getTenNhanVien());
                }
                case 2 -> {
                    //so cm
                    return nv1.getSoCM().compareTo(nv2.getSoCM());
                }
                case 3 -> {
                    // so dien thoai
                    return nv1.getSdt().compareTo(nv2.getSdt());
                }
            }
        }else {
             switch(index) {
                case 0 -> {
                    //theo ten tk
                    return nv2.getTenTk().compareTo(nv1.getTenTk());
                }
                case 1 -> {
                    // Ho ten
                    return nv2.getTenNhanVien().compareTo(nv1.getTenNhanVien());
                }
                case 2 -> {
                    //so cm
                    return nv2.getSoCM().compareTo(nv1.getSoCM());
                }
                case 3 -> {
                    // so dien thoai
                    return nv2.getSdt().compareTo(nv1.getSdt());
                }
            }
        }
        

        return 0;
    }
    
    private void enableTextField(){
        tenNhanVienTF.setEnabled(true);
        gioiTinhCB.setEnabled(true);
        chucVuCB.setEnabled(true);
        cmndTF.setEnabled(true);
        sdtTF.setEnabled(true);
        tenDangNhapTF.setEnabled(true);
        matKhauPanel.setVisible(true);
        
        if (ThongTinDangNhap.getChucVu().equals("admin")){
            chucVuCBMode(1);
        }else {
            chucVuCBMode(0);
        }
        
        clearTF();
    }
    
    private void clearTF(){
        tenNhanVienTF.setText("");
        cmndTF.setText("");
        sdtTF.setText("");
        tenDangNhapTF.setText("");
        matKhauTF.setText("");
        nhapLaiMatKhauTF.setText("");
    }
    
    private void dangKyTaiKhoan(){
        if(kiemTra()){
            taoTK();
            JOptionPane.showMessageDialog(this, "Tạo tài khoản thành công","Thông báo",JOptionPane.INFORMATION_MESSAGE);
            clearTF();
            updateDataTable();     
        }
    }
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
    
    private boolean kiemTra(){
        
        String loi = "";
        
        String tenNV = tenNhanVienTF.getText();
        String gioiTinh = gioiTinhCB.getSelectedItem().toString();
        String cmnd = cmndTF.getText();
        String sdt = sdtTF.getText();
        String chucVu = chucVuCB.getSelectedItem().toString();
        String tenDN = tenDangNhapTF.getText();
        String mk = String.valueOf(matKhauTF.getPassword());
        String cMK = String.valueOf(nhapLaiMatKhauTF.getPassword());
        
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
        if (!cMK.equals(mk)){
            loi += "Mật khẩu các nhận không chính xác";
        }
        if (!loi.equals("")){
            hienLoi(loi);
            return false;
        }else {
            return true;
        }
    }
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
    
    private boolean luuThongTin(NhanVien nv){
        if (kiemTraHopLe(nv)){
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

                // nhan vien moi
                NhanVien nvm = new NhanVien(tenDN, chucVu, cmnd, sdt, gioiTinh, tenNV, mk);
                
                data.suaThongTinNhanVien(nv,nvm);
                JOptionPane.showMessageDialog(this, "Sửa thông tin nhân viên thành công",
                        "Thông báo",JOptionPane.INFORMATION_MESSAGE);
                
                return true;
            }
        }
        return false;
    }
    // kiemt tra rang buoc de sua tk
    private boolean kiemTraHopLe(NhanVien nv){
        String loi = "";
        
        String tenNV = tenNhanVienTF.getText();
        String gioiTinh = gioiTinhCB.getSelectedItem().toString();
        String cmnd = cmndTF.getText();
        String sdt = sdtTF.getText();
        String chucVu = chucVuCB.getSelectedItem().toString();
        String tenDN = tenDangNhapTF.getText();
        String mk = String.valueOf(matKhauTF.getPassword());
        String cMK = String.valueOf(nhapLaiMatKhauTF.getPassword());
        
        if (tenNV.trim().equals("")){
            loi += "Tên nhân viên không được để trống\n";
            
        }
        if (!(cmnd.matches("^[0-9]{9}$") || cmnd.matches("^[0-9]{12}$"))){
            loi += "Số chứng minh phải có 9 hoặc 12 số\n";
        }
        else if (!cmnd.equals(nv.getSoCM()) && !kiemTraSCM(cmnd)){
            loi += "Số chứng minh nhân dân đã tồn tại\n";
        }
        if (!sdt.matches("^[0-9]{10}$")){
            loi += "Số điện thoại không hợp lệ\n";
        }
        if (tenDN.trim().equals("")){
            loi += "Tên đăng nhập không được để trống\n";
        }
        else if (!tenDN.equals(nv.getTenTk()) && !kiemTraTDN(tenDN)){
            loi += "Tên đăng nhập đã tồn tại\n";
        }
        
        if(!mk.matches("[a-zA-Z0-9_]{6,}$")){
            loi += "Mật khẩu phải có ít nhất 6 ký tự\n";
        }
        if (!cMK.equals(mk)){
            loi += "Mật khẩu xác nhận không chính xác";
        }
        if (!loi.equals("")){
            hienLoi(loi);
            return false;
        }else {
            return true;
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        timKiemTextField = new javax.swing.JTextField();
        timKiemButton = new javax.swing.JButton();
        themNhanVienButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        bangNhanVien = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        sapXepComboBox = new javax.swing.JComboBox<>();
        timKiemComboBox = new javax.swing.JComboBox<>();
        sapXepTGCB = new javax.swing.JComboBox<>();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        tenNhanVienTF = new javax.swing.JTextField();
        tenNVLb = new javax.swing.JLabel();
        gioiTinh = new javax.swing.JLabel();
        gioiTinhCB = new javax.swing.JComboBox<>();
        cmndTF = new javax.swing.JTextField();
        cmndLb = new javax.swing.JLabel();
        sdtLb = new javax.swing.JLabel();
        sdtTF = new javax.swing.JTextField();
        chucVuLb = new javax.swing.JLabel();
        chucVuCB = new javax.swing.JComboBox<>();
        tenDangNhapTF = new javax.swing.JTextField();
        tenDangNhapLb = new javax.swing.JLabel();
        chucNangPanel = new javax.swing.JPanel();
        suaNhanVienPanel = new javax.swing.JPanel();
        xoaNhanVienBtn = new javax.swing.JButton();
        suaThongTinBtn = new javax.swing.JButton();
        themNhanVienPanel = new javax.swing.JPanel();
        dangKyBtn = new javax.swing.JButton();
        panelTam = new javax.swing.JPanel();
        matKhauPanel = new javax.swing.JPanel();
        nhapLaiMatKhauTF = new javax.swing.JPasswordField();
        nhapLaiMatKhauLb = new javax.swing.JLabel();
        hienMKCheckBox = new javax.swing.JCheckBox();
        matKhauTF = new javax.swing.JPasswordField();
        matKhauLb = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 204));
        setPreferredSize(new java.awt.Dimension(943, 617));

        timKiemTextField.setToolTipText("Tìm kiếm nhân viên");
        timKiemTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                timKiemTextFieldActionPerformed(evt);
            }
        });

        timKiemButton.setBackground(new java.awt.Color(32, 136, 203));
        timKiemButton.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        timKiemButton.setForeground(new java.awt.Color(255, 255, 255));
        timKiemButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/HinhAnh/search_white.png"))); // NOI18N
        timKiemButton.setText("Tìm kiếm");
        timKiemButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                timKiemButtonActionPerformed(evt);
            }
        });

        themNhanVienButton.setBackground(new java.awt.Color(32, 136, 203));
        themNhanVienButton.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        themNhanVienButton.setForeground(new java.awt.Color(255, 255, 255));
        themNhanVienButton.setText("Thêm nhân viên");
        themNhanVienButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                themNhanVienButtonActionPerformed(evt);
            }
        });

        bangNhanVien.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        bangNhanVien.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
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
        bangNhanVien.setFocusable(false);
        bangNhanVien.setGridColor(new java.awt.Color(0, 0, 0));
        bangNhanVien.setRowHeight(25);
        bangNhanVien.setRowMargin(5);
        bangNhanVien.setSelectionBackground(new java.awt.Color(32, 136, 203));
        bangNhanVien.setSelectionForeground(new java.awt.Color(204, 255, 255));
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

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("Sắp xếp theo");

        sapXepComboBox.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        sapXepComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sapXepComboBoxActionPerformed(evt);
            }
        });

        timKiemComboBox.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        timKiemComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                timKiemComboBoxActionPerformed(evt);
            }
        });

        sapXepTGCB.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        sapXepTGCB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tăng dần", "Giảm dần" }));
        sapXepTGCB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sapXepTGCBActionPerformed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Thông tin nhân viên");

        tenNhanVienTF.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tenNhanVienTF.setCaretColor(new java.awt.Color(0, 153, 255));
        tenNhanVienTF.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        tenNhanVienTF.setSelectionColor(new java.awt.Color(0, 153, 255));
        tenNhanVienTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tenNhanVienTFActionPerformed(evt);
            }
        });

        tenNVLb.setBackground(new java.awt.Color(255, 255, 255));
        tenNVLb.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tenNVLb.setText("Tên nhân viên");

        gioiTinh.setBackground(new java.awt.Color(255, 255, 255));
        gioiTinh.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        gioiTinh.setText("Giới tính");

        gioiTinhCB.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        gioiTinhCB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nam", "Nữ" }));

        cmndTF.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cmndTF.setCaretColor(new java.awt.Color(0, 153, 255));
        cmndTF.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        cmndTF.setSelectionColor(new java.awt.Color(0, 153, 255));

        cmndLb.setBackground(new java.awt.Color(255, 255, 255));
        cmndLb.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cmndLb.setText("Chứng CMND");

        sdtLb.setBackground(new java.awt.Color(255, 255, 255));
        sdtLb.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        sdtLb.setText("Số điện thoại");

        sdtTF.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        sdtTF.setCaretColor(new java.awt.Color(0, 153, 255));
        sdtTF.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        sdtTF.setSelectionColor(new java.awt.Color(0, 153, 255));

        chucVuLb.setBackground(new java.awt.Color(255, 255, 255));
        chucVuLb.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        chucVuLb.setText("Chức vụ");

        chucVuCB.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        chucVuCB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nhân viên bán hàng", "Quản lý", "admin" }));
        chucVuCB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chucVuCBActionPerformed(evt);
            }
        });

        tenDangNhapTF.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tenDangNhapTF.setCaretColor(new java.awt.Color(0, 153, 255));
        tenDangNhapTF.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        tenDangNhapTF.setSelectionColor(new java.awt.Color(0, 153, 255));

        tenDangNhapLb.setBackground(new java.awt.Color(255, 255, 255));
        tenDangNhapLb.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tenDangNhapLb.setText("Tên đăng nhập");

        chucNangPanel.setBackground(new java.awt.Color(255, 255, 255));
        chucNangPanel.setLayout(new java.awt.CardLayout());

        suaNhanVienPanel.setBackground(new java.awt.Color(255, 255, 255));

        xoaNhanVienBtn.setBackground(new java.awt.Color(32, 136, 203));
        xoaNhanVienBtn.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        xoaNhanVienBtn.setForeground(new java.awt.Color(255, 255, 255));
        xoaNhanVienBtn.setText("Xóa nhân viên");
        xoaNhanVienBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                xoaNhanVienBtnActionPerformed(evt);
            }
        });

        suaThongTinBtn.setBackground(new java.awt.Color(32, 136, 203));
        suaThongTinBtn.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        suaThongTinBtn.setForeground(new java.awt.Color(255, 255, 255));
        suaThongTinBtn.setText("Sửa thông tin");
        suaThongTinBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                suaThongTinBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout suaNhanVienPanelLayout = new javax.swing.GroupLayout(suaNhanVienPanel);
        suaNhanVienPanel.setLayout(suaNhanVienPanelLayout);
        suaNhanVienPanelLayout.setHorizontalGroup(
            suaNhanVienPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(suaNhanVienPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(xoaNhanVienBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 75, Short.MAX_VALUE)
                .addComponent(suaThongTinBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        suaNhanVienPanelLayout.setVerticalGroup(
            suaNhanVienPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, suaNhanVienPanelLayout.createSequentialGroup()
                .addContainerGap(19, Short.MAX_VALUE)
                .addGroup(suaNhanVienPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(xoaNhanVienBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 49, Short.MAX_VALUE)
                    .addComponent(suaThongTinBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        chucNangPanel.add(suaNhanVienPanel, "card3");

        themNhanVienPanel.setBackground(new java.awt.Color(255, 255, 255));

        dangKyBtn.setBackground(new java.awt.Color(32, 136, 203));
        dangKyBtn.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        dangKyBtn.setForeground(new java.awt.Color(255, 255, 255));
        dangKyBtn.setText("Đăng ký");
        dangKyBtn.setPreferredSize(new java.awt.Dimension(90, 25));
        dangKyBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dangKyBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout themNhanVienPanelLayout = new javax.swing.GroupLayout(themNhanVienPanel);
        themNhanVienPanel.setLayout(themNhanVienPanelLayout);
        themNhanVienPanelLayout.setHorizontalGroup(
            themNhanVienPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, themNhanVienPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(dangKyBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 375, Short.MAX_VALUE)
                .addContainerGap())
        );
        themNhanVienPanelLayout.setVerticalGroup(
            themNhanVienPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, themNhanVienPanelLayout.createSequentialGroup()
                .addContainerGap(19, Short.MAX_VALUE)
                .addComponent(dangKyBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        chucNangPanel.add(themNhanVienPanel, "card2");

        panelTam.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout panelTamLayout = new javax.swing.GroupLayout(panelTam);
        panelTam.setLayout(panelTamLayout);
        panelTamLayout.setHorizontalGroup(
            panelTamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 395, Short.MAX_VALUE)
        );
        panelTamLayout.setVerticalGroup(
            panelTamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 79, Short.MAX_VALUE)
        );

        chucNangPanel.add(panelTam, "card4");

        matKhauPanel.setBackground(new java.awt.Color(255, 255, 255));

        nhapLaiMatKhauTF.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        nhapLaiMatKhauTF.setCaretColor(new java.awt.Color(0, 153, 255));
        nhapLaiMatKhauTF.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        nhapLaiMatKhauTF.setSelectionColor(new java.awt.Color(0, 153, 255));
        nhapLaiMatKhauTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nhapLaiMatKhauTFActionPerformed(evt);
            }
        });

        nhapLaiMatKhauLb.setBackground(new java.awt.Color(255, 255, 255));
        nhapLaiMatKhauLb.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        nhapLaiMatKhauLb.setText("Nhập lại mật khẩu");
        nhapLaiMatKhauLb.setPreferredSize(new java.awt.Dimension(115, 17));

        hienMKCheckBox.setBackground(new java.awt.Color(255, 255, 255));
        hienMKCheckBox.setText("Hiện thị mật khẩu");
        hienMKCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hienMKCheckBoxActionPerformed(evt);
            }
        });

        matKhauTF.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        matKhauTF.setCaretColor(new java.awt.Color(0, 153, 255));
        matKhauTF.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        matKhauTF.setSelectionColor(new java.awt.Color(0, 153, 255));
        matKhauTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                matKhauTFActionPerformed(evt);
            }
        });

        matKhauLb.setBackground(new java.awt.Color(255, 255, 255));
        matKhauLb.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        matKhauLb.setText("Mật khẩu");
        matKhauLb.setPreferredSize(new java.awt.Dimension(115, 17));

        javax.swing.GroupLayout matKhauPanelLayout = new javax.swing.GroupLayout(matKhauPanel);
        matKhauPanel.setLayout(matKhauPanelLayout);
        matKhauPanelLayout.setHorizontalGroup(
            matKhauPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, matKhauPanelLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(hienMKCheckBox))
            .addGroup(matKhauPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(matKhauPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(matKhauLb, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(nhapLaiMatKhauLb, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(matKhauPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(matKhauTF)
                    .addComponent(nhapLaiMatKhauTF))
                .addContainerGap())
        );
        matKhauPanelLayout.setVerticalGroup(
            matKhauPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(matKhauPanelLayout.createSequentialGroup()
                .addGroup(matKhauPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(matKhauTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(matKhauLb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(matKhauPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nhapLaiMatKhauLb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nhapLaiMatKhauTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(hienMKCheckBox))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(chucNangPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(tenDangNhapLb, javax.swing.GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE)
                            .addComponent(sdtLb, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(chucVuLb, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cmndLb, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(gioiTinh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(tenNVLb, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(gioiTinhCB, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cmndTF, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(sdtTF, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(chucVuCB, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(tenDangNhapTF, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(tenNhanVienTF, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addContainerGap())
            .addComponent(matKhauPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addGap(60, 60, 60)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tenNVLb)
                    .addComponent(tenNhanVienTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(gioiTinh)
                    .addComponent(gioiTinhCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmndLb)
                    .addComponent(cmndTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sdtLb)
                    .addComponent(sdtTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(chucVuLb)
                    .addComponent(chucVuCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tenDangNhapTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tenDangNhapLb))
                .addGap(18, 18, 18)
                .addComponent(matKhauPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(chucNangPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(timKiemTextField)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(timKiemComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(timKiemButton)
                        .addGap(18, 18, 18)
                        .addComponent(themNhanVienButton))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(sapXepComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(sapXepTGCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(themNhanVienButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(timKiemTextField)
                    .addComponent(timKiemButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(timKiemComboBox))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(sapXepComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sapXepTGCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void timKiemTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_timKiemTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_timKiemTextFieldActionPerformed

    private void sapXepComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sapXepComboBoxActionPerformed
        // TODO add your handling code here:
        taoDSBang(dsHienThi);
    }//GEN-LAST:event_sapXepComboBoxActionPerformed

    private void themNhanVienButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_themNhanVienButtonActionPerformed
        if (ThongTinDangNhap.getChucVu().equals("admin")){
            chucVuCBMode(0);
        }else {
            chucVuCBMode(1);
        }
        enableTextField();
        matKhauPanel.setVisible(true);
        chucNangPanel.removeAll();
        chucNangPanel.add(themNhanVienPanel);
        chucNangPanel.repaint();
        chucNangPanel.validate();
        
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
        NhanVien nv = dsHienThi.get(index);
        
        chucNangPanel.removeAll();
//        chucNangPanel.add(suaNhanVienPanel);
//        chucNangPanel.repaint();
//        chucNangPanel.validate();
        suaThongTinBtn.setText("Sửa thông tin");
        
        hienThongTinNhanVien(nv,false);
        //xet quyen cho nhan vien
        if (ThongTinDangNhap.getChucVu().equals("Nhân viên bán hàng")){
//            suaNhanVienPanel.setVisible(false);
            chucNangPanel.add(panelTam);
            chucNangPanel.repaint();
            chucNangPanel.validate();
        }else if (ThongTinDangNhap.getChucVu().equals("Quản lý") && nv.getChucVu().equals("Quản lý")){
//            suaNhanVienPanel.setVisible(false);
            chucNangPanel.add(panelTam);
            chucNangPanel.repaint();
            chucNangPanel.validate();
        }else if (nv.getChucVu().equals("admin")){
//            suaNhanVienPanel.setVisible(false);
            chucNangPanel.add(panelTam);
            chucNangPanel.repaint();
            chucNangPanel.validate();
        }else {
//            suaNhanVienPanel.setVisible(true);
            chucNangPanel.add(suaNhanVienPanel);
            chucNangPanel.repaint();
            chucNangPanel.validate();
        }
        
        
        
    }//GEN-LAST:event_bangNhanVienMouseClicked

    private void bangNhanVienMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bangNhanVienMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_bangNhanVienMouseReleased

    private void sapXepTGCBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sapXepTGCBActionPerformed
        // TODO add your handling code here:
        taoDSBang(dsHienThi);
    }//GEN-LAST:event_sapXepTGCBActionPerformed

    private void tenNhanVienTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tenNhanVienTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tenNhanVienTFActionPerformed

    private void matKhauTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_matKhauTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_matKhauTFActionPerformed

    private void hienMKCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hienMKCheckBoxActionPerformed
        // TODO add your handling code here:
        if (hienMKCheckBox.isSelected())
        {
            matKhauTF.setEchoChar((char)0);
            nhapLaiMatKhauTF.setEchoChar((char)0);
        }
        else
        {
            matKhauTF.setEchoChar('●');
            nhapLaiMatKhauTF.setEchoChar('●');
        }
    }//GEN-LAST:event_hienMKCheckBoxActionPerformed

    private void nhapLaiMatKhauTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nhapLaiMatKhauTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nhapLaiMatKhauTFActionPerformed

    private void chucVuCBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chucVuCBActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chucVuCBActionPerformed

    private void dangKyBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dangKyBtnActionPerformed
        // TODO add your handling code here:
        dangKyTaiKhoan();
    }//GEN-LAST:event_dangKyBtnActionPerformed

    private void suaThongTinBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_suaThongTinBtnActionPerformed
        // TODO add your handling code here:
        int index = bangNhanVien.getSelectedRow();

        // neu da sua roi lai sua lai lan nua
        if (index == -1){
            String tenDN = tenDangNhapTF.getText();

            for (int i = 0; i < dsHienThi.size(); i++){
                if (dsHienThi.get(i).getTenTk().equals(tenDN)){
                    index = i;
                    break;
                }
            }
        }
        if (suaThongTinBtn.getText().equals("Sửa thông tin")){
            suaThongTinBtn.setText("Lưu thông tin");

            NhanVien nv = dsHienThi.get(index);
            hienThongTinNhanVien(nv, true);

        }else {
            NhanVien nv = dsHienThi.get(index);
            if (luuThongTin(nv)){
                updateDataTable();
                String tenDN = tenDangNhapTF.getText();

                for (NhanVien tmp: dsHienThi){
                    if (tmp.getTenTk().equals(tenDN)){
                        hienThongTinNhanVien(tmp, false);
                        break;
                    }
                }
                suaThongTinBtn.setText("Sửa thông tin");
            }

        }
    }//GEN-LAST:event_suaThongTinBtnActionPerformed

    private void xoaNhanVienBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_xoaNhanVienBtnActionPerformed
        // TODO add your handling code here:
        int mode = JOptionPane.showConfirmDialog(this,
            "Bạn có muốn xóa tài khoản này", "Xóa tài koản",
            JOptionPane.YES_NO_OPTION);
        int index = bangNhanVien.getSelectedRow();
        // neu khong chon nhan vien
        if (index == -1){
            String tenDN = tenDangNhapTF.getText();

            for (int i = 0; i < dsHienThi.size(); i++){
                if (dsHienThi.get(i).getTenTk().equals(tenDN)){
                    index = i;
                    break;
                }
            }
        }
        NhanVien nv = dsHienThi.get(index);

        if(mode == 0){
            data.xoaNhanVien(nv);
            JOptionPane.showMessageDialog(this, "Xóa nhân viên thành công","Thông báo",JOptionPane.INFORMATION_MESSAGE);
            clearTF();
            updateDataTable();
        }
        suaThongTinBtn.setText("Sửa thông tin");
    }//GEN-LAST:event_xoaNhanVienBtnActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable bangNhanVien;
    private javax.swing.JPanel chucNangPanel;
    private javax.swing.JComboBox<String> chucVuCB;
    private javax.swing.JLabel chucVuLb;
    private javax.swing.JLabel cmndLb;
    private javax.swing.JTextField cmndTF;
    private javax.swing.JButton dangKyBtn;
    private javax.swing.JLabel gioiTinh;
    private javax.swing.JComboBox<String> gioiTinhCB;
    private javax.swing.JCheckBox hienMKCheckBox;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel matKhauLb;
    private javax.swing.JPanel matKhauPanel;
    private javax.swing.JPasswordField matKhauTF;
    private javax.swing.JLabel nhapLaiMatKhauLb;
    private javax.swing.JPasswordField nhapLaiMatKhauTF;
    private javax.swing.JPanel panelTam;
    private javax.swing.JComboBox<String> sapXepComboBox;
    private javax.swing.JComboBox<String> sapXepTGCB;
    private javax.swing.JLabel sdtLb;
    private javax.swing.JTextField sdtTF;
    private javax.swing.JPanel suaNhanVienPanel;
    private javax.swing.JButton suaThongTinBtn;
    private javax.swing.JLabel tenDangNhapLb;
    private javax.swing.JTextField tenDangNhapTF;
    private javax.swing.JLabel tenNVLb;
    private javax.swing.JTextField tenNhanVienTF;
    private javax.swing.JButton themNhanVienButton;
    private javax.swing.JPanel themNhanVienPanel;
    private javax.swing.JButton timKiemButton;
    private javax.swing.JComboBox<String> timKiemComboBox;
    private javax.swing.JTextField timKiemTextField;
    private javax.swing.JButton xoaNhanVienBtn;
    // End of variables declaration//GEN-END:variables

}
