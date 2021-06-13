
package quanlycafemangdi.view;

import com.github.lgooddatepicker.components.DatePickerSettings;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.print.SimpleDoc;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import quanlycafemangdi.Util;
import quanlycafemangdi.data.Data;
import quanlycafemangdi.model.CaLamViec;
import quanlycafemangdi.model.DiaDiem;
import quanlycafemangdi.model.ThongTinDangNhap;
import quanlycafemangdi.model.NhanVien;

public class DiaDiemPanel extends javax.swing.JPanel{
    
    private DefaultTableModel dtmDD;
    private DefaultTableModel dtmCLV;
    
    private List<DiaDiem> dsDiaDiem = new ArrayList<>();
    private List<DiaDiem> dsHienThiDD = new ArrayList<>();
    private List<NhanVien> dsNhanVien = new ArrayList<>();
    private List<String> dsSapXepDD = new ArrayList<>(Arrays.asList("Mã địa điểm", "Vị trí"));
    private List<String> dsTimKiemDD = new ArrayList<>(Arrays.asList("Mã địa điểm", "Vị trí"));
    
    private List<CaLamViec> dsCaLamViec = new ArrayList<>();
    private List<CaLamViec> dsHienThiCLV = new ArrayList<>();
    private List<String> dsSapXepCLV = new ArrayList<>(Arrays.asList("Mã ca làm việc", "Địa điểm", "Ngày làm"));
    private List<String> dsTimKiemCLV = new ArrayList<>(Arrays.asList("Mã ca làm việc", "Địa điểm", "Ca làm", "Ngày làm", "Tên nhân viên"));
    private List<String> dsCaLam = new ArrayList<>(Arrays.asList("Sáng", "Chiều"));
    
    private HashMap<String, String> dsDiaDiemHashMap;
    
    public DiaDiemPanel() {
        initComponents();
     
        data = Data.getInstance();
        dsNhanVien = locDSNhanVien(data.layDSNhanVienDayDu());
        
        xetBang(jT_DSCLV);
        xetBang(jT_DSDD);
        
        dtmDD = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        timKiemDD();
        jT_DSDD.setModel(dtmDD);
        khoiTaoBangDD();
        khoiTaoComboBoxDD();
        
        init();
        
    }
    private void init(){
        dtmCLV = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        DatePickerSettings datePickerSettings = new DatePickerSettings();
        datePickerSettings.setAllowEmptyDates(true);
        datePickerSettings.setFormatForDatesCommonEra("yyyy-MM-dd");
        
        jDC_DateCLV.setSettings(datePickerSettings);
        
        timKiemCLV();
        jT_DSCLV.setModel(dtmCLV);
        khoiTaoBangCLV();
        khoiTaoComboBoxCLV();
        
        jCB_DiaDiem.removeAllItems();
        dsDiaDiem.forEach(dd -> {
            jCB_DiaDiem.addItem(dd.getViTri());
        });
        
        
        
        jCB_TenNV1.removeAllItems();
        jCB_TenNV2.removeAllItems();
        jCB_TenNV2.addItem("");
        
        List<NhanVien> dsNVLoc = locDSNhanVien(data.layDSNhanVien());
        for (NhanVien nv : dsNVLoc){
            jCB_TenNV1.addItem(nv.getTenNhanVien());
            jCB_TenNV2.addItem(nv.getTenNhanVien());
        }
    }
    
    private String getTenNV(String maNV){
        for(NhanVien nv : dsNhanVien){
            if(nv.getTenTk().equals(maNV)){
                return nv.getTenNhanVien();
            }
        }
        return null;
    }
    
    private String getMaNV(String tenNV){
        for(NhanVien nv : dsNhanVien){
            if(nv.getTenNhanVien().equals(tenNV)){
                return nv.getTenTk();
            }
        }
        return null;
    }
    
    
    private void updateDataTable(){
        khoiTaoBangDD();
        khoiTaoBangCLV();
    }
    
//    private void updateDataTableCLV(){
//        khoiTaoBangCLV();
//    }
    
    private void xetBang(JTable table){
        table.setFont(new Font("Tahoma", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(114,102,186));
        table.getTableHeader().setForeground(Color.white);
        table.setBackground(new Color(254,254,254));
    }
    private void khoiTaoBangDD(){
        dsDiaDiem = Data.getInstance().layDSDiaDiem();
        dsDiaDiemHashMap = new HashMap<>();
        for (DiaDiem dd : dsDiaDiem){
            dsDiaDiemHashMap.put(dd.getMaDD(), dd.getViTri());
        }
        
        dtmDD.setColumnCount(0);
        dtmDD.addColumn("Mã địa điểm");
        dtmDD.addColumn("Vị trí");
        
        taoDSBangDD(dsDiaDiem);
    }
    
    private void taoDSBangDD(List<DiaDiem> ds){
        dsHienThiDD = sapXepDD(ds);
        dtmDD.setRowCount(0);
        for(DiaDiem item: dsHienThiDD) {
            dtmDD.addRow(new Object[]{item.getMaDD(), item.getViTri()});
        }
    }
    
    private void khoiTaoComboBoxDD() {
        // Tao SapXepComboBox
        for(String item : dsSapXepDD) {
            jCB_SapXepDD.addItem(item);
        }
        
        // Tao TimKiemComboBox
        for (String item : dsTimKiemDD){
            jCB_TimKiemDD.addItem(item);
        }
    }
    
    private void timKiemDiaDiem(){
        xoaThongTinDD();
        jLP_ChucNangDD.removeAll();
        jLP_ChucNangDD.add(jP_HienTTDD);
        jLP_ChucNangDD.repaint();
        jLP_ChucNangDD.validate();
        
        String kyTuCanTim = jTF_TimKiemDD.getText();
        if (kyTuCanTim.equals("")){
            taoDSBangDD(dsDiaDiem);
            return;
        }
        
        ArrayList<DiaDiem> ds = new ArrayList<>();
        int index = jCB_TimKiemDD.getSelectedIndex();
        
        switch(index){
            case 0: // tim kiem theo ma dia diem
                for (DiaDiem item : dsDiaDiem){
                    if (item.getMaDD().toLowerCase().contains(kyTuCanTim.trim().toLowerCase())){
                        ds.add(item);
                    }
                }
                break;
            case 1: // tim kiem theo vi tri
                for (DiaDiem item : dsDiaDiem){
                    if (item.getViTri().toLowerCase().contains(kyTuCanTim.trim().toLowerCase())){
                        ds.add(item);
                    }
                }
                break;
        }
        
        taoDSBangDD(ds);
    }
    
    private void timKiemDD(){
        jTF_TimKiemDD.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timKiemDiaDiem();
            }
        });
    }
        
    private int sapXepDiaDiem(DiaDiem dd1, DiaDiem dd2){
        int index = jCB_SapXepDD.getSelectedIndex();
        
        switch(index) {
            case 0: //theo ma dia diem
                return dd1.getMaDD().compareTo(dd2.getMaDD());
            case 1: //theo vi tri
                return dd1.getViTri().compareTo(dd2.getViTri());
        }
        return 0;
    }
    
    private List<DiaDiem> sapXepDD(List<DiaDiem> ds){        
        Collections.sort(ds,new Comparator<DiaDiem>() {
            @Override
            public int compare(DiaDiem dd1, DiaDiem dd2) {
                return sapXepDiaDiem(dd1, dd2);
            }
        });
        return ds;
    }
    
    private void khoiTaoBangCLV(){
        dsCaLamViec = Data.getInstance().layDSCaLamViec();
        
        dtmCLV.setColumnCount(0);
        dtmCLV.addColumn("Mã ca làm việc");
        dtmCLV.addColumn("Địa điểm");
        dtmCLV.addColumn("Ca làm việc");
        dtmCLV.addColumn("Ngày làm");
        dtmCLV.addColumn("Tên nhân viên 1");
        dtmCLV.addColumn("Tên nhân viên 2");
        
        taoDSBangCLV(dsCaLamViec);
    }
    
    private void taoDSBangCLV(List<CaLamViec> ds){
        dsHienThiCLV = sapXepCLV(ds);
        dtmCLV.setRowCount(0);
        for(CaLamViec item: dsHienThiCLV) {
            dtmCLV.addRow(new Object[]{item.getMaCLV(), dsDiaDiemHashMap.get(item.getMaDD()), item.getCaLamViec(), item.getNgay(), getTenNV(item.getTK1()), getTenNV(item.getTK2())});
        }
    }
    
    private void khoiTaoComboBoxCLV(){
        for(String item : dsSapXepCLV){
            jCB_SapXepCLV.addItem(item);
        }
        
        for(String item : dsTimKiemCLV){
            jCB_TimKiemCLV.addItem(item);
        }
        
        for(String item : dsCaLam){
            jCB_CaLam.addItem(item);
        }
    }
    
    private void timKiemCaLamViec(){
        xoaThongTinCLV();
        hienTFCLV(false);
        jLP_ChucNangCLV.removeAll();
        jLP_ChucNangCLV.add(jP_HienCLV);
        jLP_ChucNangCLV.repaint();
        jLP_ChucNangCLV.validate();
            
        String kyTuCanTim = jTF_TimKiemCLV.getText();
        if (kyTuCanTim.equals("")){
            taoDSBangCLV(dsCaLamViec);
            return;
        }
        
        ArrayList<CaLamViec> ds = new ArrayList<>();
        int index = jCB_TimKiemCLV.getSelectedIndex();
        
        switch(index){
            case 0: // tim kiem theo ma ca lam viec
                for (CaLamViec item : dsCaLamViec){
                    if (item.getMaCLV().toLowerCase().contains(kyTuCanTim.trim().toLowerCase())){
                        ds.add(item);
                    }
                }
                break;
            case 1: // tim kiem theo ma dia diem
                for (CaLamViec item : dsCaLamViec){
                    if (dsDiaDiemHashMap.get(item.getMaDD()).toLowerCase()
                            .contains(kyTuCanTim.trim().toLowerCase())){
                        ds.add(item);
                    }
                }
                break;
            case 2: // tim kiem theo ca lam
                for (CaLamViec item : dsCaLamViec){
                    if (item.getCaLamViec().toLowerCase().contains(kyTuCanTim.trim().toLowerCase())){
                        ds.add(item);
                    }
                }
                break;
            case 3: // tim kiem theo ngay lam
                for (CaLamViec item : dsCaLamViec){
                    if (item.getNgay().toLowerCase().contains(kyTuCanTim.trim().toLowerCase())){
                        ds.add(item);
                    }
                }
                break;
            case 4: // tim kiem theo ten nhan vien
                for (CaLamViec item : dsCaLamViec){
                    String t2 = item.getTK2();
                    if (getTenNV(item.getTK1()).toLowerCase().contains(kyTuCanTim.trim().toLowerCase())){
                        ds.add(item);
                    }
                    else{
                        if (item.getTK2() != null)
                            if (getTenNV(item.getTK2()).toLowerCase().contains(kyTuCanTim.trim().toLowerCase())){
                                ds.add(item);
                            }
                    } 
                }
                break;
        }
        
        taoDSBangCLV(ds);
    }
    
    private void timKiemCLV(){
        jTF_TimKiemCLV.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timKiemCaLamViec();
            }
        });
    }
    
    private int sapXepCaLamViec(CaLamViec clv1, CaLamViec clv2){
        int index = jCB_SapXepCLV.getSelectedIndex();
        
        switch(index) {
            case 0: //theo ma ca lma viec
                return clv1.getMaCLV().compareTo(clv2.getMaCLV());
            case 1: //theo ma dia diem
                return clv1.getMaDD().compareTo(clv2.getMaDD());
            case 2: //theo ngay lam
                return clv2.getNgay().compareTo(clv1.getNgay());
        }
        return 0;
    }
    
    private List<CaLamViec> sapXepCLV(List<CaLamViec> ds){        
        Collections.sort(ds,new Comparator<CaLamViec>() {
            @Override
            public int compare(CaLamViec clv1, CaLamViec clv2) {
                return sapXepCaLamViec(clv1, clv2);
            }
        });
        return ds;
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTP_DiaDiem = new javax.swing.JTabbedPane();
        jP_DD = new javax.swing.JPanel();
        jTF_TimKiemDD = new javax.swing.JTextField();
        jCB_TimKiemDD = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        jT_DSDD = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jCB_SapXepDD = new javax.swing.JComboBox<>();
        jB_ThemDD = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jTF_ViTriDD = new javax.swing.JTextField();
        jLP_ChucNangDD = new javax.swing.JLayeredPane();
        jP_HienTTDD = new javax.swing.JPanel();
        jP_ThemDD = new javax.swing.JPanel();
        jB_TaoDD = new javax.swing.JButton();
        jB_Thoat1DD = new javax.swing.JButton();
        jP_SuaXoaDD = new javax.swing.JPanel();
        jB_SuaDD = new javax.swing.JButton();
        jB_XoaDD = new javax.swing.JButton();
        jB_Thoat2DD = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jP_CLV = new javax.swing.JPanel();
        jTF_TimKiemCLV = new javax.swing.JTextField();
        jCB_TimKiemCLV = new javax.swing.JComboBox<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        jT_DSCLV = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        jCB_SapXepCLV = new javax.swing.JComboBox<>();
        jB_ThemCLV = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jCB_CaLam = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        jDC_DateCLV = new com.github.lgooddatepicker.components.DatePicker();
        jLP_ChucNangCLV = new javax.swing.JLayeredPane();
        jP_HienCLV = new javax.swing.JPanel();
        jP_SuaXoaCLV = new javax.swing.JPanel();
        jB_SuaCLV = new javax.swing.JButton();
        jB_Thoat2CLV = new javax.swing.JButton();
        jP_ThemCLV = new javax.swing.JPanel();
        jB_TaoCLV = new javax.swing.JButton();
        jB_Thoat1CLV = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jCB_DiaDiem = new javax.swing.JComboBox<>();
        jCB_TenNV1 = new javax.swing.JComboBox<>();
        jCB_TenNV2 = new javax.swing.JComboBox<>();
        jPanel7 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        setPreferredSize(new java.awt.Dimension(943, 617));

        jTP_DiaDiem.setBackground(new java.awt.Color(32, 136, 203));
        jTP_DiaDiem.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jTP_DiaDiem.setForeground(new java.awt.Color(255, 255, 255));
        jTP_DiaDiem.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jTP_DiaDiem.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTP_DiaDiem.setPreferredSize(new java.awt.Dimension(943, 617));

        jP_DD.setBackground(new java.awt.Color(255, 255, 255));
        jP_DD.setPreferredSize(new java.awt.Dimension(943, 617));

        jCB_TimKiemDD.setBackground(new java.awt.Color(254, 254, 254));
        jCB_TimKiemDD.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jCB_TimKiemDD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCB_TimKiemDDActionPerformed(evt);
            }
        });

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jScrollPane1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jScrollPane1MouseClicked(evt);
            }
        });

        jT_DSDD.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jT_DSDD.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jT_DSDDMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jT_DSDD);

        jLabel2.setText("Sắp xếp theo: ");
        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jCB_SapXepDD.setBackground(new java.awt.Color(254, 254, 254));
        jCB_SapXepDD.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jCB_SapXepDD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCB_SapXepDDActionPerformed(evt);
            }
        });

        jB_ThemDD.setText(" Thêm địa điểm");
        jB_ThemDD.setBackground(new java.awt.Color(32, 136, 203));
        jB_ThemDD.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jB_ThemDD.setForeground(new java.awt.Color(255, 255, 255));
        jB_ThemDD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jB_ThemDDActionPerformed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel4.setText("Vị trí");
        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        jTF_ViTriDD.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTF_ViTriDD.setBackground(new java.awt.Color(254, 254, 254));

        jLP_ChucNangDD.setLayout(new java.awt.CardLayout());

        jP_HienTTDD.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jP_HienTTDDLayout = new javax.swing.GroupLayout(jP_HienTTDD);
        jP_HienTTDD.setLayout(jP_HienTTDDLayout);
        jP_HienTTDDLayout.setHorizontalGroup(
            jP_HienTTDDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jP_HienTTDDLayout.setVerticalGroup(
            jP_HienTTDDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 73, Short.MAX_VALUE)
        );

        jLP_ChucNangDD.add(jP_HienTTDD, "card4");

        jP_ThemDD.setBackground(new java.awt.Color(255, 255, 255));

        jB_TaoDD.setText("Thêm");
        jB_TaoDD.setBackground(new java.awt.Color(32, 136, 203));
        jB_TaoDD.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jB_TaoDD.setForeground(new java.awt.Color(255, 255, 255));
        jB_TaoDD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jB_TaoDDActionPerformed(evt);
            }
        });

        jB_Thoat1DD.setText("Thoát");
        jB_Thoat1DD.setBackground(new java.awt.Color(32, 136, 203));
        jB_Thoat1DD.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jB_Thoat1DD.setForeground(new java.awt.Color(255, 255, 255));
        jB_Thoat1DD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jB_Thoat1DDActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jP_ThemDDLayout = new javax.swing.GroupLayout(jP_ThemDD);
        jP_ThemDD.setLayout(jP_ThemDDLayout);
        jP_ThemDDLayout.setHorizontalGroup(
            jP_ThemDDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jP_ThemDDLayout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addComponent(jB_TaoDD, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addComponent(jB_Thoat1DD, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(37, Short.MAX_VALUE))
        );
        jP_ThemDDLayout.setVerticalGroup(
            jP_ThemDDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jP_ThemDDLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jP_ThemDDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jB_TaoDD, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jB_Thoat1DD, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jLP_ChucNangDD.add(jP_ThemDD, "card2");

        jP_SuaXoaDD.setBackground(new java.awt.Color(255, 255, 255));
        jP_SuaXoaDD.setPreferredSize(new java.awt.Dimension(250, 73));

        jB_SuaDD.setText("Sửa");
        jB_SuaDD.setBackground(new java.awt.Color(32, 136, 203));
        jB_SuaDD.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jB_SuaDD.setForeground(new java.awt.Color(255, 255, 255));
        jB_SuaDD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jB_SuaDDActionPerformed(evt);
            }
        });

        jB_XoaDD.setText("Xóa");
        jB_XoaDD.setBackground(new java.awt.Color(32, 136, 203));
        jB_XoaDD.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jB_XoaDD.setForeground(new java.awt.Color(255, 255, 255));
        jB_XoaDD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jB_XoaDDActionPerformed(evt);
            }
        });

        jB_Thoat2DD.setText("Thoát");
        jB_Thoat2DD.setBackground(new java.awt.Color(32, 136, 203));
        jB_Thoat2DD.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jB_Thoat2DD.setForeground(new java.awt.Color(255, 255, 255));
        jB_Thoat2DD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jB_Thoat2DDActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jP_SuaXoaDDLayout = new javax.swing.GroupLayout(jP_SuaXoaDD);
        jP_SuaXoaDD.setLayout(jP_SuaXoaDDLayout);
        jP_SuaXoaDDLayout.setHorizontalGroup(
            jP_SuaXoaDDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jP_SuaXoaDDLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jB_SuaDD, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jB_XoaDD, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jB_Thoat2DD, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jP_SuaXoaDDLayout.setVerticalGroup(
            jP_SuaXoaDDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jP_SuaXoaDDLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jP_SuaXoaDDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jB_SuaDD, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jB_XoaDD, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jB_Thoat2DD, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLP_ChucNangDD.add(jP_SuaXoaDD, "card3");

        jPanel2.setBackground(new java.awt.Color(114, 102, 186));

        jLabel1.setText("Thông tin địa điểm");
        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(94, 94, 94)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLP_ChucNangDD)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(70, 70, 70)
                        .addComponent(jTF_ViTriDD, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(87, 87, 87)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTF_ViTriDD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(50, 50, 50)
                .addComponent(jLP_ChucNangDD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/HinhAnh/logo.png"))); // NOI18N

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );

        jPanel6.setBackground(new java.awt.Color(114, 102, 186));

        jLabel3.setText("Quản lý địa điểm");
        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(100, 100, 100)
                .addComponent(jLabel3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addContainerGap())
        );

        javax.swing.GroupLayout jP_DDLayout = new javax.swing.GroupLayout(jP_DD);
        jP_DD.setLayout(jP_DDLayout);
        jP_DDLayout.setHorizontalGroup(
            jP_DDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jP_DDLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jP_DDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jP_DDLayout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jCB_SapXepDD, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jP_DDLayout.createSequentialGroup()
                        .addGroup(jP_DDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jP_DDLayout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 508, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jP_DDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(jP_DDLayout.createSequentialGroup()
                                .addComponent(jTF_TimKiemDD)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jCB_TimKiemDD, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jB_ThemDD, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())))
        );
        jP_DDLayout.setVerticalGroup(
            jP_DDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jP_DDLayout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jP_DDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jB_ThemDD, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCB_TimKiemDD, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTF_TimKiemDD, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jP_DDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCB_SapXepDD, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jP_DDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jP_DDLayout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 420, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTP_DiaDiem.addTab("Địa điểm ", jP_DD);

        jP_CLV.setBackground(new java.awt.Color(255, 255, 255));
        jP_CLV.setFocusable(false);
        jP_CLV.setPreferredSize(new java.awt.Dimension(943, 617));

        jTF_TimKiemCLV.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jTF_TimKiemCLV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTF_TimKiemCLVActionPerformed(evt);
            }
        });

        jCB_TimKiemCLV.setBackground(new java.awt.Color(254, 254, 254));
        jCB_TimKiemCLV.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jCB_TimKiemCLV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCB_TimKiemCLVActionPerformed(evt);
            }
        });

        jScrollPane2.setBackground(new java.awt.Color(254, 254, 254));
        jScrollPane2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jScrollPane2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jScrollPane2MouseClicked(evt);
            }
        });

        jT_DSCLV.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jT_DSCLV.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jT_DSCLVMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jT_DSCLV);

        jLabel5.setText("Sắp xếp theo: ");
        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jCB_SapXepCLV.setBackground(new java.awt.Color(254, 254, 254));
        jCB_SapXepCLV.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jCB_SapXepCLV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCB_SapXepCLVActionPerformed(evt);
            }
        });

        jB_ThemCLV.setText("Thêm ca làm việc");
        jB_ThemCLV.setBackground(new java.awt.Color(32, 136, 203));
        jB_ThemCLV.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jB_ThemCLV.setForeground(new java.awt.Color(255, 255, 255));
        jB_ThemCLV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jB_ThemCLVActionPerformed(evt);
            }
        });

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel7.setText("Địa điểm");
        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        jLabel8.setText("Ca làm việc");
        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        jCB_CaLam.setBackground(new java.awt.Color(254, 254, 254));
        jCB_CaLam.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel9.setText("Ngày làm");
        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        jDC_DateCLV.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLP_ChucNangCLV.setLayout(new java.awt.CardLayout());

        jP_HienCLV.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jP_HienCLVLayout = new javax.swing.GroupLayout(jP_HienCLV);
        jP_HienCLV.setLayout(jP_HienCLVLayout);
        jP_HienCLVLayout.setHorizontalGroup(
            jP_HienCLVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jP_HienCLVLayout.setVerticalGroup(
            jP_HienCLVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jLP_ChucNangCLV.add(jP_HienCLV, "card4");

        jP_SuaXoaCLV.setBackground(new java.awt.Color(254, 254, 254));

        jB_SuaCLV.setText("Sửa");
        jB_SuaCLV.setBackground(new java.awt.Color(32, 136, 203));
        jB_SuaCLV.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jB_SuaCLV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jB_SuaCLVActionPerformed(evt);
            }
        });

        jB_Thoat2CLV.setText("Thoát");
        jB_Thoat2CLV.setBackground(new java.awt.Color(32, 136, 203));
        jB_Thoat2CLV.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jB_Thoat2CLV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jB_Thoat2CLVActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jP_SuaXoaCLVLayout = new javax.swing.GroupLayout(jP_SuaXoaCLV);
        jP_SuaXoaCLV.setLayout(jP_SuaXoaCLVLayout);
        jP_SuaXoaCLVLayout.setHorizontalGroup(
            jP_SuaXoaCLVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jP_SuaXoaCLVLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jB_SuaCLV, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 99, Short.MAX_VALUE)
                .addComponent(jB_Thoat2CLV, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40))
        );
        jP_SuaXoaCLVLayout.setVerticalGroup(
            jP_SuaXoaCLVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jP_SuaXoaCLVLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jP_SuaXoaCLVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jB_SuaCLV, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jB_Thoat2CLV, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        jLP_ChucNangCLV.add(jP_SuaXoaCLV, "card3");

        jP_ThemCLV.setBackground(new java.awt.Color(254, 254, 254));

        jB_TaoCLV.setText("Thêm");
        jB_TaoCLV.setBackground(new java.awt.Color(32, 136, 203));
        jB_TaoCLV.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jB_TaoCLV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jB_TaoCLVActionPerformed(evt);
            }
        });

        jB_Thoat1CLV.setText("Thoát");
        jB_Thoat1CLV.setBackground(new java.awt.Color(32, 136, 203));
        jB_Thoat1CLV.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jB_Thoat1CLV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jB_Thoat1CLVActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jP_ThemCLVLayout = new javax.swing.GroupLayout(jP_ThemCLV);
        jP_ThemCLV.setLayout(jP_ThemCLVLayout);
        jP_ThemCLVLayout.setHorizontalGroup(
            jP_ThemCLVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jP_ThemCLVLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jB_TaoCLV, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addComponent(jB_Thoat1CLV, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38))
        );
        jP_ThemCLVLayout.setVerticalGroup(
            jP_ThemCLVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jP_ThemCLVLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jP_ThemCLVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jB_TaoCLV, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jB_Thoat1CLV, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        jLP_ChucNangCLV.add(jP_ThemCLV, "card2");

        jLabel10.setText("Nhân viên 1");
        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        jLabel11.setText("Nhân viên 2");
        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        jPanel4.setBackground(new java.awt.Color(114, 102, 186));

        jLabel12.setText("Thông tin ca làm việc");
        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel12)
                .addGap(82, 82, 82))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                .addContainerGap())
        );

        jCB_DiaDiem.setBackground(new java.awt.Color(254, 254, 254));
        jCB_DiaDiem.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jCB_TenNV1.setBackground(new java.awt.Color(254, 254, 254));
        jCB_TenNV1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jCB_TenNV1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCB_TenNV1ActionPerformed(evt);
            }
        });

        jCB_TenNV2.setBackground(new java.awt.Color(254, 254, 254));
        jCB_TenNV2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jCB_TenNV2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCB_TenNV2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLP_ChucNangCLV, javax.swing.GroupLayout.PREFERRED_SIZE, 379, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10)
                            .addComponent(jLabel11)
                            .addComponent(jLabel9)
                            .addComponent(jLabel8)
                            .addComponent(jLabel7))
                        .addGap(49, 49, 49)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jCB_CaLam, 0, 245, Short.MAX_VALUE)
                            .addComponent(jDC_DateCLV, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jCB_DiaDiem, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jCB_TenNV1, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jCB_TenNV2, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jCB_DiaDiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCB_CaLam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addGap(26, 26, 26)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jDC_DateCLV, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addGap(27, 27, 27)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jCB_TenNV1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jCB_TenNV2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(40, 40, 40)
                .addComponent(jLP_ChucNangCLV, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel7.setBackground(new java.awt.Color(114, 102, 186));

        jLabel13.setText("Quản lý ca làm việc");
        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(100, 100, 100)
                .addComponent(jLabel13)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel13)
                .addContainerGap())
        );

        javax.swing.GroupLayout jP_CLVLayout = new javax.swing.GroupLayout(jP_CLV);
        jP_CLV.setLayout(jP_CLVLayout);
        jP_CLVLayout.setHorizontalGroup(
            jP_CLVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jP_CLVLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jP_CLVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jP_CLVLayout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCB_SapXepCLV, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jP_CLVLayout.createSequentialGroup()
                        .addComponent(jTF_TimKiemCLV)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCB_TimKiemCLV, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jB_ThemCLV, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jP_CLVLayout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 505, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jP_CLVLayout.setVerticalGroup(
            jP_CLVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jP_CLVLayout.createSequentialGroup()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jP_CLVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jP_CLVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jB_ThemCLV, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jCB_TimKiemCLV, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jTF_TimKiemCLV, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jP_CLVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCB_SapXepCLV, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jP_CLVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 401, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 408, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTP_DiaDiem.addTab("Phân công ca làm việc ", jP_CLV);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTP_DiaDiem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTP_DiaDiem, javax.swing.GroupLayout.DEFAULT_SIZE, 630, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jCB_SapXepDDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCB_SapXepDDActionPerformed
        // TODO add your handling code here:
        taoDSBangDD(dsHienThiDD);
        xoaThongTinDD();
        jLP_ChucNangDD.removeAll();
        jLP_ChucNangDD.add(jP_HienTTDD);
        jLP_ChucNangDD.repaint();
        jLP_ChucNangDD.validate();
    }//GEN-LAST:event_jCB_SapXepDDActionPerformed

    private void jCB_TimKiemDDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCB_TimKiemDDActionPerformed
        // TODO add your handling code here:
        timKiemDiaDiem();
    }//GEN-LAST:event_jCB_TimKiemDDActionPerformed

    private void jScrollPane1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jScrollPane1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jScrollPane1MouseClicked

    private void jT_DSDDMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jT_DSDDMouseClicked
        // TODO add your handling code here:
        int index = jT_DSDD.getSelectedRow();
        DiaDiem dd = dsHienThiDD.get(index);
        jLP_ChucNangDD.removeAll();
        hienTTDD(dd, true);
        if(ThongTinDangNhap.getChucVu().equals("Nhân viên bán hàng")){
            jLP_ChucNangDD.add(jP_HienTTDD);
            jLP_ChucNangDD.repaint();
            jLP_ChucNangDD.validate();
        }else{
            jLP_ChucNangDD.add(jP_SuaXoaDD);
            jLP_ChucNangDD.repaint();
            jLP_ChucNangDD.validate();
        }
    }//GEN-LAST:event_jT_DSDDMouseClicked

    private void jB_ThemDDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jB_ThemDDActionPerformed
        // TODO add your handling code here
        jLP_ChucNangDD.removeAll();
        if(ThongTinDangNhap.getChucVu().equals("Nhân viên bán hàng")){
            jLP_ChucNangDD.add(jP_HienTTDD);
            jLP_ChucNangDD.repaint();
            jLP_ChucNangDD.validate();
        }else{
            jLP_ChucNangDD.add(jP_ThemDD);
            jLP_ChucNangDD.repaint();
            jLP_ChucNangDD.validate();
        }
    }//GEN-LAST:event_jB_ThemDDActionPerformed

    private void jB_ThemCLVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jB_ThemCLVActionPerformed
        // TODO add your handling code here:
        jLP_ChucNangCLV.removeAll();
        if(ThongTinDangNhap.getChucVu().equals("Nhân viên bán hàng")){
            jLP_ChucNangCLV.add(jP_HienCLV);
            jLP_ChucNangCLV.repaint();
            jLP_ChucNangCLV.validate();
        }else{ 
            jLP_ChucNangCLV.add(jP_ThemCLV);
            jLP_ChucNangCLV.repaint();
            jLP_ChucNangCLV.validate();
        }
        hienTFCLV(true);
    }//GEN-LAST:event_jB_ThemCLVActionPerformed

    private void jCB_TimKiemCLVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCB_TimKiemCLVActionPerformed
        // TODO add your handling code here:
        timKiemCaLamViec();
    }//GEN-LAST:event_jCB_TimKiemCLVActionPerformed

    private void jCB_SapXepCLVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCB_SapXepCLVActionPerformed
        // TODO add your handling code here:
        taoDSBangCLV(dsHienThiCLV);
        xoaThongTinCLV();
        hienTFCLV(false);
        jLP_ChucNangCLV.removeAll();
        jLP_ChucNangCLV.add(jP_HienCLV);
        jLP_ChucNangCLV.repaint();
        jLP_ChucNangCLV.validate();
    }//GEN-LAST:event_jCB_SapXepCLVActionPerformed

    private void jScrollPane2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jScrollPane2MouseClicked
        // TODO add your handling code here:
        
    }//GEN-LAST:event_jScrollPane2MouseClicked

    private void jT_DSCLVMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jT_DSCLVMouseClicked
        // TODO add your handling code here:
        int index = jT_DSCLV.getSelectedRow();
        CaLamViec clv = dsHienThiCLV.get(index);
        jLP_ChucNangCLV.removeAll();
        
        String currentDate = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDateTime.now());
        
        if(ThongTinDangNhap.getChucVu().contains("nhân viên") || clv.getNgay().compareTo(currentDate) < 0){
            jLP_ChucNangCLV.add(jP_HienCLV);
            jLP_ChucNangCLV.repaint();
            jLP_ChucNangCLV.validate();
            hienTTCLV(clv, false);
        }else{
            jLP_ChucNangCLV.add(jP_SuaXoaCLV);
            jLP_ChucNangCLV.repaint();
            jLP_ChucNangCLV.validate();
            hienTTCLV(clv, true);
        }
        
    }//GEN-LAST:event_jT_DSCLVMouseClicked

    private void jB_TaoDDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jB_TaoDDActionPerformed
        // TODO add your handling code here:
        if(kTThongTinDD()){
            themDiaDiem();
            JOptionPane.showMessageDialog(this, "Thêm địa điểm thành công.", "Thông tin", JOptionPane.INFORMATION_MESSAGE);
            xoaThongTinDD();
            khoiTaoBangDD();
           // dispose();n
        }
    }//GEN-LAST:event_jB_TaoDDActionPerformed

    private void jB_Thoat1DDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jB_Thoat1DDActionPerformed
        // TODO add your handling code here:
        jLP_ChucNangDD.removeAll();
        jLP_ChucNangDD.add(jP_HienTTDD);
        jLP_ChucNangDD.repaint();
        jLP_ChucNangDD.validate();
        //dispose();
    }//GEN-LAST:event_jB_Thoat1DDActionPerformed

    private void jB_SuaDDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jB_SuaDDActionPerformed
        // TODO add your handling code here:
        int index = jT_DSDD.getSelectedRow();
        DiaDiem dd = dsHienThiDD.get(index);
        
        if(kTHopLeDD(dd)){
            int kink = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn sửa thông tin địa điểm không?", "Thông báo", JOptionPane.YES_NO_OPTION);

            if(kink == 0){
                String viTri = jTF_ViTriDD.getText();
                
                DiaDiem ddm = new DiaDiem(dd.getMaDD(), viTri);
                data.suaDiaDiem(dd, ddm);
                JOptionPane.showMessageDialog(this, "Sửa thông tin thành công");
                xoaThongTinDD();
                khoiTaoBangDD();
            }
        }
    }//GEN-LAST:event_jB_SuaDDActionPerformed

    private void jB_XoaDDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jB_XoaDDActionPerformed
        // TODO add your handling code here:
        int index = jT_DSDD.getSelectedRow();
        DiaDiem dd = dsHienThiDD.get(index);
        
        int kink = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa địa điểm này không?", "Thông báo", JOptionPane.YES_NO_OPTION);

        if(kink == 0){
            data.xoaDiaDiem(dd);
            JOptionPane.showMessageDialog(this, "Xóa thông tin thành công");
            //xoaThongTinDD();
            khoiTaoBangDD();
            //dispose();
        }
    }//GEN-LAST:event_jB_XoaDDActionPerformed

    private void jB_Thoat2DDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jB_Thoat2DDActionPerformed
        // TODO add your handling code here:
        jLP_ChucNangDD.removeAll();
        jLP_ChucNangDD.add(jP_HienTTDD);
        jLP_ChucNangDD.repaint();
        jLP_ChucNangDD.validate();
        //dispose();
    }//GEN-LAST:event_jB_Thoat2DDActionPerformed

    private void jB_SuaCLVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jB_SuaCLVActionPerformed
        // TODO add your handling code here:
        int index = jT_DSCLV.getSelectedRow();
        CaLamViec clv = dsHienThiCLV.get(index);
        
        if(kTHopLeCLV(clv)){
            int kink = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn sửa thông tin ca làm việc không?", "Thông báo", JOptionPane.YES_NO_OPTION);

            if(kink == 0){
                String caLamViec = (String) jCB_CaLam.getSelectedItem();
                String ngayLam = jDC_DateCLV.getDateStringOrEmptyString();
             
                String dd = jCB_DiaDiem.getSelectedItem().toString();
                String maDD = layMaDD(dd);
                String tk1 = getMaNV(jCB_TenNV1.getSelectedItem().toString());
                String tk2;
                if (jCB_TenNV2.getSelectedItem() == null){
                    tk2 = null;
                }else {
                     tk2 = getMaNV(jCB_TenNV2.getSelectedItem().toString().trim());
                }
               

                CaLamViec clv2 = new CaLamViec(clv.getMaCLV(), maDD, caLamViec, ngayLam, tk1, tk2);
                data.suaThongTinCaLamViec(clv2);
                JOptionPane.showMessageDialog(this, "Sửa thông tin thành công.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);

                thoatButtonAction();
                khoiTaoBangCLV();
                //dispose();
            }
        }
    }//GEN-LAST:event_jB_SuaCLVActionPerformed

    private void jB_Thoat2CLVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jB_Thoat2CLVActionPerformed
        // TODO add your handling code here:
        thoatButtonAction();
        
        
    }//GEN-LAST:event_jB_Thoat2CLVActionPerformed

    private void jB_TaoCLVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jB_TaoCLVActionPerformed
        // TODO add your handling code here:
        if(kTThongTinCLV()){
            themCaLamViec();
            JOptionPane.showMessageDialog(this, "Thêm ca làm việc thành công.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            thoatButtonAction();
            xoaThongTinCLV();
            khoiTaoBangCLV();
        //dispose();
        }
    }//GEN-LAST:event_jB_TaoCLVActionPerformed

    private void jB_Thoat1CLVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jB_Thoat1CLVActionPerformed
        // TODO add your handling code here:
        jLP_ChucNangCLV.removeAll();
        jLP_ChucNangCLV.add(jP_HienTTDD);
        jLP_ChucNangCLV.repaint();
        jLP_ChucNangCLV.validate();
        //dispose();
    }//GEN-LAST:event_jB_Thoat1CLVActionPerformed

    private void jTF_TimKiemCLVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTF_TimKiemCLVActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTF_TimKiemCLVActionPerformed

    private void jCB_TenNV2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCB_TenNV2ActionPerformed
        // TODO add your handling code here:
        if (jCB_TenNV2.getSelectedItem()== null || jCB_TenNV1.getSelectedItem()== null) return;
        String t2 = jCB_TenNV2.getSelectedItem().toString();
        String t1 = jCB_TenNV1.getSelectedItem().toString();
        
        if (t2.equals(t1)){
            JOptionPane.showMessageDialog(this, "Tên nhân viên phụ không được trùng.\n", "Thông báo", JOptionPane.ERROR_MESSAGE);
            
            jCB_TenNV2.setSelectedItem("");
        }
    }//GEN-LAST:event_jCB_TenNV2ActionPerformed

    private void jCB_TenNV1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCB_TenNV1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCB_TenNV1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jB_SuaCLV;
    private javax.swing.JButton jB_SuaDD;
    private javax.swing.JButton jB_TaoCLV;
    private javax.swing.JButton jB_TaoDD;
    private javax.swing.JButton jB_ThemCLV;
    private javax.swing.JButton jB_ThemDD;
    private javax.swing.JButton jB_Thoat1CLV;
    private javax.swing.JButton jB_Thoat1DD;
    private javax.swing.JButton jB_Thoat2CLV;
    private javax.swing.JButton jB_Thoat2DD;
    private javax.swing.JButton jB_XoaDD;
    private javax.swing.JComboBox<String> jCB_CaLam;
    private javax.swing.JComboBox<String> jCB_DiaDiem;
    private javax.swing.JComboBox<String> jCB_SapXepCLV;
    private javax.swing.JComboBox<String> jCB_SapXepDD;
    private javax.swing.JComboBox<String> jCB_TenNV1;
    private javax.swing.JComboBox<String> jCB_TenNV2;
    private javax.swing.JComboBox<String> jCB_TimKiemCLV;
    private javax.swing.JComboBox<String> jCB_TimKiemDD;
    private com.github.lgooddatepicker.components.DatePicker jDC_DateCLV;
    private javax.swing.JLayeredPane jLP_ChucNangCLV;
    private javax.swing.JLayeredPane jLP_ChucNangDD;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jP_CLV;
    private javax.swing.JPanel jP_DD;
    private javax.swing.JPanel jP_HienCLV;
    private javax.swing.JPanel jP_HienTTDD;
    private javax.swing.JPanel jP_SuaXoaCLV;
    private javax.swing.JPanel jP_SuaXoaDD;
    private javax.swing.JPanel jP_ThemCLV;
    private javax.swing.JPanel jP_ThemDD;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jTF_TimKiemCLV;
    private javax.swing.JTextField jTF_TimKiemDD;
    private javax.swing.JTextField jTF_ViTriDD;
    private javax.swing.JTabbedPane jTP_DiaDiem;
    private javax.swing.JTable jT_DSCLV;
    private javax.swing.JTable jT_DSDD;
    // End of variables declaration//GEN-END:variables

    public void onFrameDispose() {
        updateDataTable();
        //updateDataTableDD();
    }
    
    private Data data;
    //private DiaDiem diaDiemHT;
    //private CaLamViec caLamViecHT;
    
    //xoa thong tin 
    public void xoaThongTinDD(){
        jTF_ViTriDD.setText("");
    }
    
    //kiem tra thong tin them dai diem
    public boolean kTThongTinDD(){
        String loi = "";
        String viTri = jTF_ViTriDD.getText();
        
        if(viTri.replace("\\s\\s+", "").trim().equals("")){
            loi += "Vị trí không được bỏ trống.\n";
        }
        if(!loi.equals("")){
            hienLoi(loi);
            return false;
        }else{
            return true;
        }
    }
    //kiem tra thong tin vi tri dia diem
    private boolean kiemTraViTri(String viTri){
        return data.kiemTraViTri(viTri);
//    return false;
    }
    
    //kiem tra thong tin dia diem truoc khi sua
    public boolean kTHopLeDD(DiaDiem dd){
        String loi = "";
        String viTri = jTF_ViTriDD.getText();
        
        if(viTri.replace("\\s\\s+", "").trim().equals("")){
            loi += "Vị trí không được bỏ trống.\n";
        }else if(!viTri.equals(dd.getViTri()) && !kiemTraViTri(viTri)){
            loi += "Vi tri này đã tồn tại.\n";
        }
        if(!loi.equals("")){
            hienLoi(loi);
            return false;
        }else{
            return true;
        }
    }
    
        //hien thi thong tin loi
    private void hienLoi(String loi){
        JOptionPane.showMessageDialog(this, loi, "Lỗi", JOptionPane.ERROR_MESSAGE);
    }
    
    public void hienTTDD(DiaDiem dd, boolean hienTF){    
        jTF_ViTriDD.setText(dd.getViTri());
        
        hienTFDD(hienTF);
    }
    
    public void hienTFDD(boolean flag){
        jTF_ViTriDD.setEnabled(flag);
    }
    
    public void enableTFDD(){
        jTF_ViTriDD.setEnabled(true);
        
        xoaThongTinDD();
    }

    public void themDiaDiem(){
        String viTri = jTF_ViTriDD.getText();
        
        DiaDiem dd = new DiaDiem(Util.autoGenId(Util.DD_TABLE),viTri);
        data.themDiaDiem(dd);
    }
    
    public void xoaThongTinCLV(){
        jCB_DiaDiem.setSelectedItem("");
        jCB_CaLam.setSelectedItem("");
        jDC_DateCLV.setText("");
        jCB_TenNV1.setSelectedItem("");
        jCB_TenNV2.setSelectedItem("");
    }
    
    public boolean kTThongTinCLV(){
        String loi = "";
        
        String diaDiem = (String) jCB_DiaDiem.getSelectedItem();
        String caLamViec = (String) jCB_CaLam.getSelectedItem();
        String ngayLam = jDC_DateCLV.getText();
        String tk1 = (String) jCB_TenNV1.getSelectedItem();
        String tk2 = (String) jCB_TenNV2.getSelectedItem();
        
        if(diaDiem.trim().equals("")){
            loi += "Địa điểm không được bỏ trống.\n";
        }
        if(caLamViec.trim().equals("")){
            loi += "Ca làm việc không được bỏ trống.\n";
        }
        if(ngayLam.trim().equals("")){
            loi += "Ngày làm không duco975 bỏ trống.\n";
        }
        if(tk1.trim().equals("")){
            loi += "Tên nhân viên 1 không được bỏ trống.\n";
        }
        if(!loi.equals("")){
            hienLoi(loi);
            return false;
        }else{
            return true;
        }
    } 
    
    public boolean kTHopLeCLV(CaLamViec clv){
        String loi = "";
        
        String diaDiem = (String) jCB_DiaDiem.getSelectedItem();
        String caLamViec = (String) jCB_CaLam.getSelectedItem();
        String ngayLam = jDC_DateCLV.getDateStringOrEmptyString();
        String tk1 = (String) jCB_TenNV1.getSelectedItem();
        String tk2 = (String) jCB_TenNV2.getSelectedItem();
        
        if(diaDiem.trim().equals("")){
            loi += "Địa điểm không được bỏ trống.\n";
        }
        if(caLamViec.trim().equals("")){
            loi += "Ca làm việc không được bỏ trống.\n";
        }
        if(ngayLam.isEmpty()){
            loi += "Ngày làm không được bỏ trống.\n";
        }
        if(tk1.trim().equals("")){
            loi += "Tên nhân viên 1 không được bỏ trống.\n";
        }
        if(!loi.equals("")){
            hienLoi(loi);
            return false;
        }else{
            return true;
        }
    } 
    
    private boolean kiemTraMaCLV(String maCLV){
        return data.kiemTraMaCLV(maCLV);
//return false;
    }
    
    public void hienTTCLV(CaLamViec clv, boolean  hienTF){
        jCB_DiaDiem.setSelectedItem(dsDiaDiemHashMap.get(clv.getMaDD()));
        jCB_CaLam.setSelectedItem(clv.getCaLamViec());
        jDC_DateCLV.setText(clv.getNgay());

        jCB_TenNV1.setSelectedItem(getTenNV(clv.getTK1()));   
        if (clv.getTK2() != null){
            jCB_TenNV2.setSelectedItem(getTenNV(clv.getTK2()));   
        }else {
            jCB_TenNV2.setSelectedItem("");
        }
        
        
        hienTFCLV(hienTF);
    }
    
    public void hienTFCLV(boolean flag){
        jCB_DiaDiem.setEnabled(flag);
        jCB_CaLam.setEnabled(flag);
        jDC_DateCLV.setEnabled(flag);
        jCB_TenNV1.setEnabled(flag);
        jCB_TenNV2.setEnabled(flag);
    }
    
    public void enableTFCLV(){
        jCB_DiaDiem.setEnabled(true);
        jCB_CaLam.setEnabled(true);
        jDC_DateCLV.setEnabled(true);
        jCB_TenNV1.setEnabled(true);
        jCB_TenNV2.setEnabled(true);
        
        xoaThongTinCLV();
    }
    
    private String layMaDD(String tenDD){
        for(DiaDiem dd : dsDiaDiem){
            if (tenDD.equals(dd.getViTri()))
                return dd.getMaDD();
        }
        return null;
    }
    
    
    private void themCaLamViec(){
        String diaDiem = layMaDD(jCB_DiaDiem.getSelectedItem().toString());
        String caLamViec = (String) jCB_CaLam.getSelectedItem();
        String ngayLam = jDC_DateCLV.getText();
        String tk1 = getMaNV(jCB_TenNV1.getSelectedItem().toString());
        String tk2 = getMaNV(jCB_TenNV2.getSelectedItem().toString().trim());

        
        if(diaDiem != null){
            CaLamViec clv = new CaLamViec(Util.autoGenId(Util.CLV_TABLE), diaDiem,caLamViec, ngayLam, tk1, tk2);
            data.themCaLamViec(clv);
        }
        
    }

    private void thoatButtonAction(){
        jLP_ChucNangCLV.removeAll();
        jLP_ChucNangCLV.add(jP_HienTTDD);
        jLP_ChucNangCLV.repaint();
        jLP_ChucNangCLV.validate();
        //dispose();
        hienTFCLV(false);
    }
    private List<NhanVien> locDSNhanVien(List<NhanVien> rawList){
        ArrayList<NhanVien> mList = new ArrayList<>();
        
        for (NhanVien nv : rawList){
            if (nv.getChucVu().contains("nhân viên")){
                mList.add(nv);
            }
        }
        
        return mList;
    }
}

