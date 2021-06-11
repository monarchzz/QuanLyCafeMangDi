/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quanlycafemangdi.view;

import com.github.lgooddatepicker.components.DatePickerSettings;
import java.awt.Color;
import java.awt.Font;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import quanlycafemangdi.Util;
import quanlycafemangdi.data.Data;
import quanlycafemangdi.model.BanHang;
import quanlycafemangdi.model.CaLamViec;
import quanlycafemangdi.model.DangKi;
import quanlycafemangdi.model.DiaDiem;
import quanlycafemangdi.model.Luong;
import quanlycafemangdi.model.NhanVien;
import quanlycafemangdi.model.ThongTinDangNhap;
import quanlycafemangdi.view.chart.ChartAdapter;
import quanlycafemangdi.view.chart.DatasetValue;
import quanlycafemangdi.view.chart.MChartPanel;

/**
 *
 * @author monar
 */
public class ThongKePanel extends javax.swing.JPanel {
    private Data data;
    private String ngayHienTai;
    private String thangHienTai;
    private String namHienTai;
    private String namDauTien;
    
    private DefaultTableModel chiTietNamTableModel;
    private DefaultTableModel chiTietThangTableModel;
    private DefaultTableModel chiTietNgayTableModel;
    private DefaultTableModel luongTableModel;
    private DefaultTableModel banHangTableModel;
    private DefaultTableModel chiTietBanHangTableModel;
    private DefaultTableModel dangKiTableModel;
    private DefaultTableModel chiTietDangKiTableModel;
    
    private List<Luong> dsLuong;
    private ArrayList<Luong> dsLuongHienThi;
    private List<NhanVien> dsNhanVien;
    private List<CaLamViec> dsCaLamViec;
    private List<String> dsThanNamCLVTrongNam;
    private List<DiaDiem> dsDiaDiem;
    private List<BanHang> dsBanHang;
    private ArrayList<BanHang> dsBanHangHienThi;
    private List<DangKi> dsDangKi;
    private ArrayList<DangKi> dsDangKiHienThi;
    
    
    private HashMap<String,String> nhanVienHashMap;
    private HashMap<String,CaLamViec> caLamViecHashMap;
    private HashMap<String,String> diaDiemHashMap;

    /**
     * Creates new form ThongKePanel
     */
    public ThongKePanel() {
        initComponents();
        
        initData();
        
        initComboBox();
        initTable();
        init();
    }
    
    public void updateData(){
        dsDangKiHienThi = null;
        dsBanHangHienThi = null;
        dsLuongHienThi = null;
        
        
        initData();
        
        initComboBox();
//        initTable();
        init();
    }
    
    private void initData(){
        data = Data.getInstance();
        ngayHienTai = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now());
        thangHienTai = DateTimeFormatter.ofPattern("MM").format(LocalDateTime.now());
        namHienTai = DateTimeFormatter.ofPattern("yyyy").format(LocalDateTime.now());
        dsLuong = data.layDSLuong();
        dsNhanVien = data.layDSNhanVienDayDu();
        dsCaLamViec = data.layDSCaLamViec();
        dsBanHang = data.layDSBanHang();
        dsThanNamCLVTrongNam = data.layDSThangNamCLV();
        dsDiaDiem = data.layDSDiaDiem();
        dsDangKi = data.layDSDangKi();
        nhanVienHashMap = new HashMap<>();
        caLamViecHashMap = new HashMap<>();
        diaDiemHashMap = new HashMap<>();
        
        for (NhanVien nv : dsNhanVien){
            nhanVienHashMap.put(nv.getTenTk(), nv.getTenNhanVien());
        }
        for (CaLamViec clv : dsCaLamViec){
            caLamViecHashMap.put(clv.getMaCLV(), clv);
        }
        for (DiaDiem dd : dsDiaDiem){
            diaDiemHashMap.put(dd.getMaDD(), dd.getViTri());
        }
    }
    
    
    private void taoNgayCB(int thang){
        chiTietNgayCB.removeAllItems(); 
        chiTietNgayCB.addItem("Tất cả");
        int nam = Integer.valueOf(namHienTai);
        for (int i = 1; i<= Util.soNGayTrongThang(thang, nam); i++){
            chiTietNgayCB.addItem(String.valueOf(i));
        }
    }
    
    private void xetBang(JTable table){
        table.setFont(new Font("Tahoma", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(114,102,186));
        table.getTableHeader().setForeground(Color.white);
        table.setBackground(new Color(254,254,254));
    }
    
    private void init(){
        taoDuLieuTongQuanPanel();
        capNhatBangTKNam();
        taoDuLieuTKLuong();
        taoDuLieuBanHang();
        taoDuLieuDangKi();
    }
    
    private void taoDuLieuTongQuanPanel(){
        switch(chonTGCB.getSelectedIndex()){
            //theo ngay
            case 0 -> {
                tongQuanDuLieuTheoNgay();
            }
            case 1 -> {
                tongQuanDuLieuTheoThang();
            }
            case 2 -> {
                tongQuanDuLieuTheoNam();
            }
        }
        
    }
    private void tongQuanDuLieuTheoNgay(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        
        ArrayList<DatasetValue> doanhThuList = new ArrayList<>();
        ArrayList<DatasetValue> chiPhiList = new ArrayList<>();
        ArrayList<DatasetValue> loiNhuanList = new ArrayList<>();
        
        for (int i = Integer.valueOf(ngayHienTai); i > 0; i--){
            String ngay = "";
            try {
                ngay = dtf.format(LocalDateTime.of(Integer.valueOf(namHienTai), Integer.valueOf(thangHienTai), i, 0, 1));
            } catch (DateTimeException e) {
                continue;
            }
            
            doanhThuList.add(new DatasetValue("Doanh thu",String.valueOf(i),doanhThuTheoNgay(ngay)));
            chiPhiList.add(new DatasetValue("Chi phí",String.valueOf(i),chiPhiTheoNgay(ngay)));
            
        }
        
        
        
        //loi nhuan
        for (int i = 0; i < doanhThuList.size(); i++){
            DatasetValue d1 = doanhThuList.get(i);
            DatasetValue d2 = chiPhiList.get(i);
            
            long lN = (long)(d1.getValue() - d2.getValue());
            loiNhuanList.add(new DatasetValue("Lợi nhuận",d1.getCategory(),lN));
//            if (lN > 0){
//                loiNhuanList.add(new DatasetValue("Lợi nhuận",d1.getCategory(),lN));
//            }else loiNhuanList.add(new DatasetValue("Lợi nhuận",d1.getCategory(),0));
        }
        
        doanhThuLb.setText(Util.formatCurrency((long)doanhThuList.get(0).getValue()));
        chiPhiLb.setText(Util.formatCurrency((long)chiPhiList.get(0).getValue()));
        loiNhuanLb.setText(Util.formatCurrency((long)loiNhuanList.get(0).getValue()));
        
        Collections.reverse(doanhThuList);
        Collections.reverse(chiPhiList);
        Collections.reverse(loiNhuanList);
        MChartPanel mChartPanel = new MChartPanel(new ChartAdapter(doanhThuList, chiPhiList, loiNhuanList).getChart("Ngày"));
        Util.doiPanel(chartLayeredPane, mChartPanel);
    }
    private void tongQuanDuLieuTheoThang(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        
        ArrayList<DatasetValue> doanhThuList = new ArrayList<>();
        ArrayList<DatasetValue> chiPhiList = new ArrayList<>();
        ArrayList<DatasetValue> loiNhuanList = new ArrayList<>();
        
        for (int i = Integer.valueOf(thangHienTai); i > 0 ; i--){
            String kt = "";
            String bt = dtf.format(LocalDateTime.of(Integer.valueOf(namHienTai), i, 1, 0, 1));
            
            for (int j = 31; j > 1; j--){
                try {
                    kt =dtf.format(LocalDateTime.of(Integer.valueOf(namHienTai), i, j, 0, 1));
                } catch (DateTimeException e) {
                    continue;
                }
                
                break;
            }
            doanhThuList.add(new DatasetValue("Doanh thu",String.valueOf(i),doanhThuTheoThang(bt, kt)));
            chiPhiList.add(new DatasetValue("Chi phí",String.valueOf(i),chiPhiTheoThang(bt, kt)));
            
        }
        
        for (int i = 0; i < doanhThuList.size(); i++){
            DatasetValue d1 = doanhThuList.get(i);
            DatasetValue d2 = chiPhiList.get(i);
            
            
            long lN = (long)(d1.getValue() - d2.getValue());
            
//            if (lN > 0){
//                loiNhuanList.add(new DatasetValue("Lợi nhuận",d1.getCategory(),lN));
//            }else loiNhuanList.add(new DatasetValue("Lợi nhuận",d1.getCategory(),0));
            loiNhuanList.add(new DatasetValue("Lợi nhuận",d1.getCategory(),lN));
        }
        
        doanhThuLb.setText(Util.formatCurrency((long)doanhThuList.get(0).getValue()));
        chiPhiLb.setText(Util.formatCurrency((long)chiPhiList.get(0).getValue()));
        loiNhuanLb.setText(Util.formatCurrency((long)loiNhuanList.get(0).getValue()));
        
        
        Collections.reverse(doanhThuList);
        Collections.reverse(chiPhiList);
        Collections.reverse(loiNhuanList);
        MChartPanel mChartPanel = new MChartPanel(new ChartAdapter(doanhThuList, chiPhiList, loiNhuanList).getChart("Tháng"));
        
        Util.doiPanel(chartLayeredPane, mChartPanel);
    }
    private void tongQuanDuLieuTheoNam(){
        ArrayList<DatasetValue> doanhThuList = new ArrayList<>();
        ArrayList<DatasetValue> chiPhiList = new ArrayList<>();
        ArrayList<DatasetValue> loiNhuanList = new ArrayList<>();
        
        int nam = Integer.valueOf(namHienTai);
        
        for (int i = nam; i >= nam - 5; i--){
            double dt = doanhThuTheoNam(String.valueOf(i));
            double cp = chiPhiTheoNam(String.valueOf(i));
            double ln = dt - cp;
            doanhThuList.add(new DatasetValue("Doanh thu",String.valueOf(i),dt));
            chiPhiList.add(new DatasetValue("Chi phí",String.valueOf(i),cp));
            loiNhuanList.add(new DatasetValue("Lợi nhuận",String.valueOf(i),ln));
            
        }
        
        doanhThuLb.setText(Util.formatCurrency((long)doanhThuList.get(0).getValue()));
        chiPhiLb.setText(Util.formatCurrency((long)chiPhiList.get(0).getValue()));
        loiNhuanLb.setText(Util.formatCurrency((long)loiNhuanList.get(0).getValue()));
        
        Collections.reverse(doanhThuList);
        Collections.reverse(chiPhiList);
        Collections.reverse(loiNhuanList);
        MChartPanel mChartPanel = new MChartPanel(new ChartAdapter(doanhThuList, chiPhiList, loiNhuanList).getChart("Năm"));
        
        Util.doiPanel(chartLayeredPane, mChartPanel);
    }
    
    private void capNhatBangTKNam(){
        chiTietNamTableModel.setRowCount(0);
        for (int i = Integer.valueOf(namDauTien); i <= Integer.valueOf(namHienTai); i++){
            tKTheoNam(i);
        }
    }
    private void tKTheoNam(int nam){
        double doanhThu = doanhThuTheoNam(String.valueOf(nam));
        double chiPhi = chiPhiTheoNam(String.valueOf(nam));
        String dt = Util.formatCurrency((long)doanhThu);
        String cp = Util.formatCurrency((long)chiPhi);
        String ln = Util.formatCurrency((long) (doanhThu - chiPhi));
        chiTietNamTableModel.addRow(new Object[]{nam, dt, cp, ln});
    }
    private void tKTheoThang(int thang, int nam){
        String bt = String.valueOf(nam) + "-" + String.valueOf(thang) + "-" + "1";
        String kt = String.valueOf(nam) + "-" + String.valueOf(thang) + "-" 
                + String.valueOf(Util.soNGayTrongThang(thang, nam));
        double doanhThu = doanhThuTheoThang(bt,kt);
        double chiPhi = chiPhiTheoThang(bt,kt);
        String dt = Util.formatCurrency((long)doanhThu);
        String cp = Util.formatCurrency((long)chiPhi);
        String ln = Util.formatCurrency((long) (doanhThu - chiPhi));
        chiTietThangTableModel.addRow(new Object[]{thang, dt, cp, ln});
    }
    private void tKTheoNgay(int ngay, int thang, int nam){
        String tg = nam + "-" + thang + "-" + ngay;
        
        double doanhThu = doanhThuTheoNgay(tg);
        double chiPhi = chiPhiTheoNgay(tg);
        
        String dt = Util.formatCurrency((long)doanhThu);
        String cp = Util.formatCurrency((long)chiPhi);
        String ln = Util.formatCurrency((long) (doanhThu - chiPhi));
        chiTietNgayTableModel.addRow(new Object[]{ngay, dt, cp, ln});
    }
    
// luong panel
    private void taoDuLieuTKLuong(){
        soCLVNVTFTKL.setEnabled(false);
        hslNVTFTKL.setEnabled(false);
        luongCBNVTFTKL.setEnabled(false);
        ttlPanel.setVisible(false);
        
        dsLuongHienThi = new ArrayList<>(dsLuong);
        
        
        tKLuong();
    }
    private void tKLuong(){
        if (luongTableModel != null)
            luongTableModel.setRowCount(0);
        
        chonNgayThangTKLuong();
        ArrayList<Luong> mArrayList = new ArrayList<>();
        String tenTimKiem = timTheoTenTF.getText();
        if (!tenTimKiem.isEmpty()){
            for (Luong l : dsLuongHienThi){
                if (l.getTenNV().toLowerCase().contains(tenTimKiem.toLowerCase())){
                    mArrayList.add(l);
                }
            }
            dsLuongHienThi = mArrayList;
        }
        
        Collections.sort(dsLuongHienThi, new Comparator<Luong>() {
            @Override
            public int compare(Luong o1, Luong o2) {
                String[] s1 = o1.getThangNam().split("-");
                String[] s2 = o2.getThangNam().split("-");
                String tg1 = s1[1] + "-" + s1[0];
                String tg2 = s2[1] + "-" + s2[0];
                
                return tg2.compareTo(tg1);
            }
        });
        
        for (Luong l : dsLuongHienThi){
            String trangThai = "";
            if (l.getTrangThai() == 0)
                    trangThai = "Chưa thanh toán";
            else trangThai = "Đã thanh toán";
            luongTableModel.addRow(new Object[]{
                l.getTenTK(),
                l.getTenNV(),
                l.getCmnd(),
                l.getThangNam(),
                Util.formatCurrency(l.getLuong()),
                trangThai
            });
        }
    }
    
    private void chonNgayThangTKLuong(){
        dsLuongHienThi.clear();
        
        int nam = Integer.valueOf(tKLuongNamCB.getSelectedItem().toString());
        int thang = tKLuongThangCB.getSelectedIndex();
        String tg;
        if (thang == 0){
            tg = String.valueOf(nam);
        }else {
            tg = String.valueOf(thang) + "-" + String.valueOf(nam);
        }
        for (Luong l : dsLuong){
            if (l.getThangNam().contains(tg)){
                dsLuongHienThi.add(l);
            }
        }
    }
    
    private void capNhatBangLuong(){
        dsLuong = data.layDSLuong();
        tKLuong();
    }
    
//////// ban hang panel
    private void taoDuLieuBanHang(){
        
        DatePickerSettings datePickerSettings = new DatePickerSettings();
        datePickerSettings.setAllowEmptyDates(true);
        datePickerSettings.setFormatForDatesCommonEra("dd-MM-yyyy");
        chonNgayBHDP.setSettings(datePickerSettings);
        
        diaDiemBHCB.removeAllItems();
        diaDiemBHCB.addItem("Tất cả");
        for (int i = 0; i < dsDiaDiem.size(); i++){
            diaDiemBHCB.addItem(dsDiaDiem.get(i).getViTri());
        }
        
        
        hienDuLieuBanHang();
        
    }
    private void hienDuLieuBanHang(){
        dsBanHangHienThi = new ArrayList<>();
        ArrayList<BanHang> mArrayList;
        
        // sap xep theo ca lam
        String caLam = caLamBHComboBox.getSelectedItem().toString();
        
        if (!caLam.equals("Tất cả")){
            for (BanHang bh : dsBanHang){
                String c = caLamViecHashMap.get(bh.getMaCLV()).getCaLamViec();
                if (c.equals(caLam)){
                    dsBanHangHienThi.add(bh);
                }
            }
        }else {
            dsBanHangHienThi = new ArrayList<>(dsBanHang);
        }
        
        mArrayList = new ArrayList<>(dsBanHangHienThi);
        
        // sap xep theo dia diem
        int index=  diaDiemBHCB.getSelectedIndex();
        if (index != 0){
            dsBanHangHienThi.clear();
            String maDD = dsDiaDiem.get(index - 1).getMaDD();
            for (BanHang bh : mArrayList){
                String dd = caLamViecHashMap.get(bh.getMaCLV()).getMaDD();
                if (dd.equals(maDD)){
                    dsBanHangHienThi.add(bh);
                }
            }
            mArrayList.clear();
            mArrayList = new ArrayList<>(dsBanHangHienThi);
        }
        
        // sap xep theo ngay
        String ngay;
        ngay = chonNgayBHDP.getDateStringOrEmptyString();
        
        if (ngay != null && !ngay.isEmpty()){
            dsBanHangHienThi.clear();
            for (BanHang bh : mArrayList){
                if (bh.getTg().contains(ngay)){
                    dsBanHangHienThi.add(bh);
                }
            }
            mArrayList.clear();
        }
        
        banHangTableModel.setRowCount(0);
        for(BanHang bh : dsBanHangHienThi){
            String[] p = bh.getTg().split(" ");
            String[] n = p[0].split("-");
            String ntn = n[2] + "-" + n[1] + "-" + n[0];
            String tg = p[1].substring(0,8);
            banHangTableModel.addRow(new Object[]{
                nhanVienHashMap.get(bh.getTenTK()), 
                caLamViecHashMap.get(bh.getMaCLV()).getCaLamViec(),
                ntn,
                tg,
                diaDiemHashMap.get(caLamViecHashMap.get(bh.getMaCLV()).getMaDD()),
                Util.formatCurrency(bh.getTongTien())
            });
        }
    }

/////// dang ki panel
    private void taoDuLieuDangKi(){
        DatePickerSettings datePickerSettings = new DatePickerSettings();
        datePickerSettings.setAllowEmptyDates(true);
        datePickerSettings.setFormatForDatesCommonEra("dd-MM-yyyy");
        chonNgayDKDP.setSettings(datePickerSettings);
        
        diaDiemDKCB.removeAllItems();
        diaDiemDKCB.addItem("Tất cả");
        for (int i = 0; i < dsDiaDiem.size(); i++){
            diaDiemDKCB.addItem(dsDiaDiem.get(i).getViTri());
        }
        
        hienDuLieuDangKi();
    }
    
    private void hienDuLieuDangKi(){
        dsDangKiHienThi = new ArrayList<>();
        ArrayList<DangKi> mArrayList;
        
        // sap xep theo ca lam
        String caLam = caLamDKCB.getSelectedItem().toString();
        
        if (!caLam.equals("Tất cả")){
            for (DangKi dk : dsDangKi){
                String c = caLamViecHashMap.get(dk.getMaCLV()).getCaLamViec();
                if (c.equals(caLam)){
                    dsDangKiHienThi.add(dk);
                }
            }
        }else {
            dsDangKiHienThi = new ArrayList<>(dsDangKi);
        }
        
        mArrayList = new ArrayList<>(dsDangKiHienThi);
        
        // sap xep theo dia diem
        int index=  diaDiemDKCB.getSelectedIndex();
        if (index != 0){
            dsDangKiHienThi.clear();
            String maDD = dsDiaDiem.get(index - 1).getMaDD();
            for (DangKi dk : mArrayList){
                String dd = caLamViecHashMap.get(dk.getMaCLV()).getMaDD();
                if (dd.equals(maDD)){
                    dsDangKiHienThi.add(dk);
                }
            }
            mArrayList.clear();
            mArrayList = new ArrayList<>(dsDangKiHienThi);
        }
        
        // sap xep theo ngay
        String ngay;
        ngay = chonNgayDKDP.getDateStringOrEmptyString();
        
        if (ngay != null && !ngay.isEmpty()){
            dsDangKiHienThi.clear();
            for (DangKi dk : mArrayList){
                if (dk.getThoiGian().contains(ngay)){
                    dsDangKiHienThi.add(dk);
                    
                }
            }
            mArrayList.clear();
        }
        
        dangKiTableModel.setRowCount(0);
        for(DangKi dk : dsDangKiHienThi){
            String[] p = dk.getThoiGian().split(" ");
            String[] n = p[0].split("-");
            String ngayLV = n[2] + "-" + n[1] + "-" + n[0];
            String tg = p[1].substring(0,8);
            dangKiTableModel.addRow(new Object[]{
                caLamViecHashMap.get(dk.getMaCLV()).getCaLamViec(),
                ngayLV,
                tg,
                diaDiemHashMap.get(caLamViecHashMap.get(dk.getMaCLV()).getMaDD()),
            });
        }
    }
/////////////////////////
    private double doanhThuTheoNgay(String ngay){
        return data.doanhThuTheoNgay(ngay);
    }
    private double chiPhiTheoNgay(String ngay){
        return data.chiPhiTheoNgay(ngay);
    }
    private double doanhThuTheoThang(String bt, String kt){
        return data.doanhThuTheoThang(bt, kt);
    }
    private double chiPhiTheoThang(String bt, String kt){
        return data.chiPhiTheoThang(bt, kt);
    }
    private double doanhThuTheoNam(String nam){
        return data.doanhThuTheoNam(nam);
    }
    private double chiPhiTheoNam(String nam){
        return data.chiPhiTheoNam(nam);
    }
    private void capNhatThongTinLuong(Luong l){
        data.capNhatLuong(l);
    }
    
    private void thongTinChitietLuong(){
        int index = luongTable.getSelectedRow();
        if (index != -1){
            Luong l = dsLuongHienThi.get(index);
                    
            if (l.getTrangThai() != 0){ // da thanh toan
                soCLVNVTFTKL.setEnabled(false);
                hslNVTFTKL.setEnabled(false);
                luongCBNVTFTKL.setEnabled(false);
                ttlPanel.setVisible(false);
            }else{// chua thanh toan
                if (ThongTinDangNhap.getChucVu().equals("admin")){
                    soCLVNVTFTKL.setEnabled(true);
                    hslNVTFTKL.setEnabled(true);
                    luongCBNVTFTKL.setEnabled(true);
                    ttlPanel.setVisible(true);
                }
                
            }
                
            
            tKNVLbTKL.setText(l.getTenTK());
            tenNVLbTKL.setText(l.getTenNV());
            cmndNVLbTKL.setText(l.getCmnd());
            chucVuNVLbTKL.setText(l.getChucVu());
            tgNVLbTKL.setText(l.getThangNam());
            soCLVNVTFTKL.setText(String.valueOf(l.getSoCaLam()));
            luongCBNVTFTKL.setText(String.valueOf(l.getLuongCoBan()));
            hslNVTFTKL.setText(String.valueOf(l.getHeSoLuong()));
            tienLuongNVLbTKL.setText(Util.formatCurrency(l.getLuong()));
            
        }
    }
    private void capNhatLuong(){
        int soCLV = 0;
        int luongCoBan = 0;
        float heSoLuong = 0f;
        try {
            soCLV = Integer.valueOf(soCLVNVTFTKL.getText());
            if (soCLV < 0)
                soCLVNVTFTKL.setText("0");
        } catch (NumberFormatException e) {
            soCLVNVTFTKL.setText("");
        }
        
        try {
            luongCoBan = Integer.valueOf(luongCBNVTFTKL.getText());
            if (luongCoBan < 0)
                luongCBNVTFTKL.setText("0");
        } catch (NumberFormatException e) {
            luongCBNVTFTKL.setText("");
        }
         try {
            heSoLuong = Float.valueOf(hslNVTFTKL.getText());
            if (Float.compare(heSoLuong, 0) < 0)
                hslNVTFTKL.setText("0");
        } catch (NumberFormatException e) {
            hslNVTFTKL.setText("");
        }
        
        
        if (soCLV >= 0 && luongCoBan >= 0 && Float.compare(heSoLuong, 0f) >= 0){
            long luong = (long) (soCLV * heSoLuong * luongCoBan);
            tienLuongNVLbTKL.setText(Util.formatCurrency(luong));
        }else {
            tienLuongNVLbTKL.setText("0");
        }
        
    }
    private void thanhToanLuong(){
        int index = luongTable.getSelectedRow();
        if (index != -1){
            Luong l = dsLuongHienThi.get(index);
            
            try {
                int soCLV = Integer.valueOf(soCLVNVTFTKL.getText());
                int luongCoBan = Integer.valueOf(luongCBNVTFTKL.getText());
                float heSoLuong = Float.valueOf(hslNVTFTKL.getText());
                
                if (soCLV >= 0 && luongCoBan >= 0 && Float.compare(heSoLuong, 0f) >= 0){
                    long luong = (long) (soCLV * heSoLuong * luongCoBan);
                    
                    l.setHeSoLuong(heSoLuong);
                    l.setLuongCoBan(luongCoBan);
                    l.setSoCaLam(soCLV);
                    l.setLuong(luong);
                    l.setTrangThai(1);
                    
                    capNhatThongTinLuong(l);
                    JOptionPane.showMessageDialog(this, "Thanh toán thành công","Thông báo",JOptionPane.INFORMATION_MESSAGE);
                    
                    xoaThongTinLuongNV();
                }else {
                    JOptionPane.showMessageDialog(this, "Thanh toán không thành công","Thông báo", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException numberFormatException) {
                JOptionPane.showMessageDialog(this, "Thanh toán không thành công","Thông báo", JOptionPane.ERROR_MESSAGE);
            }
            
        }
    }
    
    private void xoaThongTinLuongNV(){
            soCLVNVTFTKL.setEnabled(false);
            hslNVTFTKL.setEnabled(false);
            luongCBNVTFTKL.setEnabled(false);
            ttlPanel.setVisible(false);
            
            tKNVLbTKL.setText("");
            tenNVLbTKL.setText("");
            cmndNVLbTKL.setText("");
            chucVuNVLbTKL.setText("");
            tgNVLbTKL.setText("");
            soCLVNVTFTKL.setText("");
            luongCBNVTFTKL.setText("");
            hslNVTFTKL.setText("");
            tienLuongNVLbTKL.setText("");
    }
    
    private int timSoCLV(String tenTK, String tg){
        String[] split = tg.split("-");
        tg = split[1] + "-" + split[0];
        int count = 0;
        for (CaLamViec clv : dsCaLamViec){
            if (clv.getNgay().contains(tg)){
                if (clv.getTK1().equals(tenTK)){
                    count ++;
                }
                if(clv.getTK2() != null){
                    if (clv.getTK2().equals(tenTK)){
                        count ++;
                    }
                }
            }
            
        }
        return count;
    }
    
    private void themDSLuong(){
        String tg = chonThangLuongCB.getSelectedItem().toString();
        for (NhanVien nv : dsNhanVien){
            int soCalam = timSoCLV(nv.getTenTk(), tg);
            if (soCalam == 0) continue;
            
            String maLuong = Util.autoGenId(Util.CL_TABLE);
            int luongCoBan = 200000;
            float heSoLuong = 1.0f;
            long thanhTien = (long)(luongCoBan * heSoLuong * soCalam);
            
            Luong luong = new Luong(maLuong, nv.getTenTk(), nv.getTenNhanVien(), 
                    nv.getSoCM(), tg, luongCoBan, heSoLuong, soCalam, 
                    thanhTien, 0, nv.getChucVu());
            
            data.taoLuong(luong);
        }
        
    }
    
    private void xemChiTietBanHang(){
        int index = tKBanHangTable.getSelectedRow();
        BanHang bh = dsBanHangHienThi.get(index);
        
        String[] p = bh.getTg().split(" ");
        String[] n = p[0].split("-");
        String ngay = n[2] + "-" + n[1] + "-" + n[0];
        String gio = p[1].substring(0,8);
        
        chiTietTenNVLb.setText(nhanVienHashMap.get(bh.getTenTK()));
        chiTietDDBHLb.setText(diaDiemHashMap.get(caLamViecHashMap.get(bh.getMaCLV()).getMaDD()));
        chiTietCaLamBHLb.setText(caLamViecHashMap.get(bh.getMaCLV()).getCaLamViec());
        chiTietNgayBHLb.setText(ngay);
        chiTietTGBHLb.setText(gio);
        chiTietTongTienLb.setText(Util.formatCurrency(bh.getTongTien()));
        
        chiTietBanHangTableModel.setRowCount(0);
        
        HashMap<String, String> mHashMap = data.chiTietBanHang(bh.getMaBH());
        if (!mHashMap.isEmpty()){
            Set<String> keySet = mHashMap.keySet();
        
            for (String key : keySet){
                chiTietBanHangTableModel.addRow(new Object[]{key, mHashMap.get(key)});
            }
        }
        
    }
    
    private void xemChiTietDangKi(){
        int index = tKDangKiTable.getSelectedRow();
        DangKi dk = dsDangKi.get(index);
        
        String[] p = dk.getThoiGian().split(" ");
        String[] n = p[0].split("-");
        String ngay = n[2] + "-" + n[1] + "-" + n[0];
        String gio = p[1].substring(0,8);
        
        
        chiTietTenNVCLb.setText(nhanVienHashMap.get(caLamViecHashMap.get(dk.getMaCLV()).getTK1()));
        if (caLamViecHashMap.get(dk.getMaCLV()).getTK2() != null){
            chiTietTenNVPLb.setText(nhanVienHashMap.get(caLamViecHashMap.get(dk.getMaCLV()).getTK2()));
        }else {
             chiTietTenNVPLb.setText("Không có");
        }
        
        chiTietDDDKLb.setText(diaDiemHashMap.get(caLamViecHashMap.get(dk.getMaCLV()).getMaDD()));
        chiTietCaLamDKLb.setText(caLamViecHashMap.get(dk.getMaCLV()).getCaLamViec());
        chiTietNgayDKLb.setText(ngay);
        chiTietTGDKLb.setText(gio);
        ghiChuTA.setText(dk.getGhiChu());
        
        chiTietDangKiTableModel.setRowCount(0);
        if (!dk.getChiTietDangKi().isEmpty()){
            Set<String> keySet = dk.getChiTietDangKi().keySet();
        
            for (String key : keySet){
                String tenVaDV = data.layTenNguyenLieu(key);
                
                String dv = tenVaDV.substring(0,tenVaDV.indexOf(" "));
                String tenNL = tenVaDV.substring(tenVaDV.indexOf(" ") + 1);
                
                chiTietDangKiTableModel.addRow(new Object[]{tenNL, dk.getChiTietDangKi().get(key) + " " + dv});
            }
        }
        
    }
    
//--------------------------------------------------------------------------------    
    
    private void initTable(){
        xetBang(chiTietTKNamTable);
        xetBang(chiTietTKThangTable);
        xetBang(chiTietTKNgayTable);
        xetBang(luongTable);
        xetBang(tKBanHangTable);
        xetBang(chiTietBanHangTable);
        xetBang(tKDangKiTable);
        xetBang(chiTietDangKiTable);
        
        chiTietNamTableModel = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        chiTietNgayTableModel = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        chiTietThangTableModel = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        luongTableModel = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
            
        };
        banHangTableModel = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
            
        }; 
        chiTietBanHangTableModel = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
            
        }; 
        dangKiTableModel = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
            
        }; 
        chiTietDangKiTableModel = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
            
        }; 
        
        
        chiTietNamTableModel.setColumnCount(0);
        chiTietNamTableModel.addColumn("Năm");
        chiTietNamTableModel.addColumn("Doanh thu");
        chiTietNamTableModel.addColumn("Chi phí");
        chiTietNamTableModel.addColumn("Lợi nhuận");
        
        chiTietThangTableModel.setColumnCount(0);
        chiTietThangTableModel.addColumn("Tháng");
        chiTietThangTableModel.addColumn("Doanh thu");
        chiTietThangTableModel.addColumn("Chi phí");
        chiTietThangTableModel.addColumn("Lợi nhuận");
        
        chiTietNgayTableModel.setColumnCount(0);
        chiTietNgayTableModel.addColumn("Ngày");
        chiTietNgayTableModel.addColumn("Doanh thu");
        chiTietNgayTableModel.addColumn("Chi phí");
        chiTietNgayTableModel.addColumn("Lợi nhuận");
        
        luongTableModel.setColumnCount(0);
        luongTableModel.addColumn("Tên tài khoản");
        luongTableModel.addColumn("Tên nhân viên");
        luongTableModel.addColumn("CMND");
        luongTableModel.addColumn("Thời gian");
        luongTableModel.addColumn("Lương");
        luongTableModel.addColumn("Trạng thái");
        
        banHangTableModel.setColumnCount(0);
        banHangTableModel.addColumn("Tên nhân viên");
        banHangTableModel.addColumn("Ca làm");
        banHangTableModel.addColumn("Ngày");
        banHangTableModel.addColumn("Thời gian");
        banHangTableModel.addColumn("Địa điểm");
        banHangTableModel.addColumn("Số tiền");
        
        chiTietBanHangTableModel.setColumnCount(0);
        chiTietBanHangTableModel.addColumn("Tên sản phẩm");
        chiTietBanHangTableModel.addColumn("Số lượng");
        
        dangKiTableModel.setColumnCount(0);
        dangKiTableModel.addColumn("Ca làm");
        dangKiTableModel.addColumn("Ngày");
        dangKiTableModel.addColumn("Thời gian");
        dangKiTableModel.addColumn("Địa điểm");
        
        chiTietDangKiTableModel.setColumnCount(0);
        chiTietDangKiTableModel.addColumn("Tên nguyên liệu");
        chiTietDangKiTableModel.addColumn("Số lượng");
        
        
        chiTietTKNamTable.setModel(chiTietNamTableModel);
        chiTietTKThangTable.setModel(chiTietThangTableModel);
        chiTietTKNgayTable.setModel(chiTietNgayTableModel);
        luongTable.setModel(luongTableModel);
        tKBanHangTable.setModel(banHangTableModel);
        chiTietBanHangTable.setModel(chiTietBanHangTableModel);
        tKDangKiTable.setModel(dangKiTableModel);
        chiTietDangKiTable.setModel(chiTietDangKiTableModel);
    }

    private void initComboBox(){
        
        chiTietThangCB.removeAllItems();
        chiTietThangCB.addItem("Tất cả");
        for (int i = 1; i <= 12; i++){
            chiTietThangCB.addItem(String.valueOf(i));
        }
        // lay nam hien tai
        int nam = data.layNamDauTien();
        if (nam != -1 ){
            namDauTien = String.valueOf(nam);
        }else namDauTien = namHienTai;
        
        chiTietNamCB.removeAllItems();
        chiTietNamCB.addItem("Tất cả");
        for (int i = Integer.valueOf(namHienTai); i >= Integer.valueOf(namDauTien); i--){
            chiTietNamCB.addItem(String.valueOf(i));
        }
        
        tKLuongThangCB.removeAllItems();
        tKLuongThangCB.addItem("Tất cả");
        for (int i = 1; i <= 12; i++){
            tKLuongThangCB.addItem("Tháng " + i);
        }
        
        tKLuongNamCB.removeAllItems();
        for (int i = Integer.valueOf(namHienTai); i >= Integer.valueOf(namDauTien); i--){
            tKLuongNamCB.addItem(String.valueOf(i));
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

        themLuongChoNhanVienDialog = new javax.swing.JDialog();
        jPanel17 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        chonThangLuongCB = new javax.swing.JComboBox<>();
        chonThangLuongBtn = new javax.swing.JButton();
        thongKeTabPanel = new javax.swing.JTabbedPane();
        tongQuanPanel = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        doanhThuLb = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        chiPhiLb = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        loiNhuanLb = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        chonTGCB = new javax.swing.JComboBox<>();
        jPanel12 = new javax.swing.JPanel();
        chartLayeredPane = new javax.swing.JLayeredPane();
        chiTietPanel = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        chiTietNamCB = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        chiTietTKNamTable = new javax.swing.JTable();
        jPanel13 = new javax.swing.JPanel();
        chiTietThangCB = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        chiTietTKThangTable = new javax.swing.JTable();
        jPanel14 = new javax.swing.JPanel();
        chiTietNgayCB = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        chiTietTKNgayTable = new javax.swing.JTable();
        jPanel15 = new javax.swing.JPanel();
        chamLuongPanel = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        luongTable = new javax.swing.JTable();
        timTheoTenTF = new javax.swing.JTextField();
        timTheoTenBtn = new javax.swing.JButton();
        tKLuongThangCB = new javax.swing.JComboBox<>();
        tKLuongNamCB = new javax.swing.JComboBox<>();
        jPanel3 = new javax.swing.JPanel();
        jPanel16 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        tenNVLbTKL = new javax.swing.JLabel();
        cmndNVLbTKL = new javax.swing.JLabel();
        tKNVLbTKL = new javax.swing.JLabel();
        chucVuNVLbTKL = new javax.swing.JLabel();
        tgNVLbTKL = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        soCLVNVTFTKL = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        luongCBNVTFTKL = new javax.swing.JTextField();
        hslNVTFTKL = new javax.swing.JTextField();
        tienLuongNVLbTKL = new javax.swing.JLabel();
        ttlPanel = new javax.swing.JPanel();
        ttlBtn = new javax.swing.JButton();
        themLuongNVBtn = new javax.swing.JButton();
        thongKeBanHangPanel = new javax.swing.JPanel();
        jPanel19 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        jPanel23 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tKBanHangTable = new javax.swing.JTable();
        jLabel21 = new javax.swing.JLabel();
        chonNgayBHDP = new com.github.lgooddatepicker.components.DatePicker();
        jLabel22 = new javax.swing.JLabel();
        caLamBHComboBox = new javax.swing.JComboBox<>();
        jLabel23 = new javax.swing.JLabel();
        diaDiemBHCB = new javax.swing.JComboBox<>();
        jPanel20 = new javax.swing.JPanel();
        jPanel21 = new javax.swing.JPanel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        chiTietBanHangTable = new javax.swing.JTable();
        jLabel35 = new javax.swing.JLabel();
        chiTietTenNVLb = new javax.swing.JLabel();
        chiTietDDBHLb = new javax.swing.JLabel();
        chiTietCaLamBHLb = new javax.swing.JLabel();
        chiTietNgayBHLb = new javax.swing.JLabel();
        chiTietTGBHLb = new javax.swing.JLabel();
        chiTietTongTienLb = new javax.swing.JLabel();
        thongKeDangKiPanel = new javax.swing.JPanel();
        jPanel24 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        tKDangKiTable = new javax.swing.JTable();
        jLabel36 = new javax.swing.JLabel();
        chonNgayDKDP = new com.github.lgooddatepicker.components.DatePicker();
        jLabel37 = new javax.swing.JLabel();
        caLamDKCB = new javax.swing.JComboBox<>();
        jLabel38 = new javax.swing.JLabel();
        diaDiemDKCB = new javax.swing.JComboBox<>();
        jPanel22 = new javax.swing.JPanel();
        jLabel39 = new javax.swing.JLabel();
        jPanel25 = new javax.swing.JPanel();
        jPanel26 = new javax.swing.JPanel();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        jPanel18 = new javax.swing.JPanel();
        jPanel27 = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        chiTietDangKiTable = new javax.swing.JTable();
        jPanel28 = new javax.swing.JPanel();
        jLabel49 = new javax.swing.JLabel();
        jScrollPane9 = new javax.swing.JScrollPane();
        ghiChuTA = new javax.swing.JTextArea();
        jLabel47 = new javax.swing.JLabel();
        chiTietTenNVCLb = new javax.swing.JLabel();
        chiTietTenNVPLb = new javax.swing.JLabel();
        chiTietDDDKLb = new javax.swing.JLabel();
        chiTietCaLamDKLb = new javax.swing.JLabel();
        chiTietNgayDKLb = new javax.swing.JLabel();
        chiTietTGDKLb = new javax.swing.JLabel();

        themLuongChoNhanVienDialog.setTitle("Chọn tháng lương");
        themLuongChoNhanVienDialog.setBackground(new java.awt.Color(255, 255, 255));
        themLuongChoNhanVienDialog.setLocationByPlatform(true);
        themLuongChoNhanVienDialog.setMinimumSize(new java.awt.Dimension(300, 100));
        themLuongChoNhanVienDialog.setModal(true);
        themLuongChoNhanVienDialog.setResizable(false);

        jPanel17.setBackground(new java.awt.Color(255, 255, 255));

        jLabel19.setText("Chọn tháng");
        jLabel19.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        chonThangLuongCB.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        chonThangLuongBtn.setText("Chọn");
        chonThangLuongBtn.setBackground(new java.awt.Color(32, 136, 203));
        chonThangLuongBtn.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        chonThangLuongBtn.setForeground(new java.awt.Color(255, 255, 255));
        chonThangLuongBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chonThangLuongBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel19)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(chonThangLuongCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 94, Short.MAX_VALUE)
                .addComponent(chonThangLuongBtn)
                .addContainerGap())
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(chonThangLuongCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(chonThangLuongBtn))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout themLuongChoNhanVienDialogLayout = new javax.swing.GroupLayout(themLuongChoNhanVienDialog.getContentPane());
        themLuongChoNhanVienDialog.getContentPane().setLayout(themLuongChoNhanVienDialogLayout);
        themLuongChoNhanVienDialogLayout.setHorizontalGroup(
            themLuongChoNhanVienDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        themLuongChoNhanVienDialogLayout.setVerticalGroup(
            themLuongChoNhanVienDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setBackground(new java.awt.Color(255, 255, 255));

        thongKeTabPanel.setBackground(new java.awt.Color(32, 136, 203));
        thongKeTabPanel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        thongKeTabPanel.setForeground(new java.awt.Color(255, 255, 255));

        tongQuanPanel.setBackground(new java.awt.Color(255, 255, 255));
        tongQuanPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jPanel1.setBackground(new java.awt.Color(114, 102, 186));

        jLabel1.setText("Tổng quan");
        jLabel1.setBackground(new java.awt.Color(114, 102, 186));
        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(100, 100, 100)
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 47, Short.MAX_VALUE)
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(new java.awt.GridLayout(1, 0, 10, 0));

        jPanel5.setBackground(new java.awt.Color(255, 95, 95));

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/HinhAnh/payments_white.png"))); // NOI18N

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("Doanh thu");
        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));

        doanhThuLb.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        doanhThuLb.setText("0");
        doanhThuLb.setBackground(new java.awt.Color(255, 255, 255));
        doanhThuLb.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N
        doanhThuLb.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(doanhThuLb, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 193, Short.MAX_VALUE))
                .addGap(30, 30, 30))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(doanhThuLb, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel4.add(jPanel5);

        jPanel6.setBackground(new java.awt.Color(87, 142, 190));

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/HinhAnh/add_shopping_cart_white.png"))); // NOI18N

        chiPhiLb.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        chiPhiLb.setText("0");
        chiPhiLb.setBackground(new java.awt.Color(255, 255, 255));
        chiPhiLb.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N
        chiPhiLb.setForeground(new java.awt.Color(255, 255, 255));

        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel12.setText("Chi phí");
        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, 195, Short.MAX_VALUE)
                    .addComponent(chiPhiLb, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(30, 30, 30))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(chiPhiLb, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel4.add(jPanel6);

        jPanel7.setBackground(new java.awt.Color(27, 188, 155));

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/HinhAnh/local_atm_white.png"))); // NOI18N

        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel10.setText("Lợi nhuận");
        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));

        loiNhuanLb.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        loiNhuanLb.setText("0");
        loiNhuanLb.setBackground(new java.awt.Color(255, 255, 255));
        loiNhuanLb.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N
        loiNhuanLb.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(loiNhuanLb, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, 195, Short.MAX_VALUE))
                .addGap(30, 30, 30))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(loiNhuanLb, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.add(jPanel7);

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setLayout(new java.awt.GridLayout(1, 0));

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setText("Thời gian");
        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N

        chonTGCB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Theo ngày", "Theo tháng", "Theo năm" }));
        chonTGCB.setBackground(new java.awt.Color(254, 254, 254));
        chonTGCB.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        chonTGCB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chonTGCBActionPerformed(evt);
            }
        });
        chonTGCB.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                chonTGCBPropertyChange(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(chonTGCB, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(chonTGCB, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel8.add(jPanel9);

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));

        chartLayeredPane.setLayout(new java.awt.CardLayout());

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(chartLayeredPane)
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(chartLayeredPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 249, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout tongQuanPanelLayout = new javax.swing.GroupLayout(tongQuanPanel);
        tongQuanPanel.setLayout(tongQuanPanelLayout);
        tongQuanPanelLayout.setHorizontalGroup(
            tongQuanPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(tongQuanPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(tongQuanPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel12, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 961, Short.MAX_VALUE)
                    .addGroup(tongQuanPanelLayout.createSequentialGroup()
                        .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(119, 119, 119)))
                .addContainerGap())
        );
        tongQuanPanelLayout.setVerticalGroup(
            tongQuanPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tongQuanPanelLayout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        thongKeTabPanel.addTab("Tổng quan", tongQuanPanel);

        chiTietPanel.setBackground(new java.awt.Color(255, 255, 255));
        chiTietPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jPanel2.setBackground(new java.awt.Color(114, 102, 186));

        jLabel3.setText("Thống kê chi tiết");
        jLabel3.setBackground(new java.awt.Color(114, 102, 186));
        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(100, 100, 100)
                .addComponent(jLabel3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 47, Short.MAX_VALUE)
        );

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));
        jPanel10.setLayout(new java.awt.GridLayout(1, 0));

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));

        jLabel4.setText("Năm");
        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N

        chiTietNamCB.setBackground(new java.awt.Color(254, 254, 254));
        chiTietNamCB.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        chiTietNamCB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chiTietNamCBActionPerformed(evt);
            }
        });

        jScrollPane1.setBackground(new java.awt.Color(254, 254, 254));

        chiTietTKNamTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        chiTietTKNamTable.setBackground(new java.awt.Color(254, 254, 254));
        chiTietTKNamTable.setSelectionBackground(new java.awt.Color(153, 153, 255));
        chiTietTKNamTable.setSelectionForeground(new java.awt.Color(254, 254, 254));
        chiTietTKNamTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        chiTietTKNamTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                chiTietTKNamTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(chiTietTKNamTable);

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(chiTietNamCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(222, Short.MAX_VALUE))
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(chiTietNamCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 322, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel10.add(jPanel11);

        jPanel13.setBackground(new java.awt.Color(255, 255, 255));

        chiTietThangCB.setBackground(new java.awt.Color(254, 254, 254));
        chiTietThangCB.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        chiTietThangCB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chiTietThangCBActionPerformed(evt);
            }
        });

        jLabel7.setText("Tháng");
        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N

        chiTietTKThangTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        chiTietTKThangTable.setSelectionBackground(new java.awt.Color(153, 153, 255));
        chiTietTKThangTable.setSelectionForeground(new java.awt.Color(254, 254, 254));
        chiTietTKThangTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        chiTietTKThangTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                chiTietTKThangTableMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(chiTietTKThangTable);

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel7)
                .addGap(18, 18, 18)
                .addComponent(chiTietThangCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(200, Short.MAX_VALUE))
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(chiTietThangCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 322, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel10.add(jPanel13);

        jPanel14.setBackground(new java.awt.Color(255, 255, 255));

        chiTietNgayCB.setBackground(new java.awt.Color(254, 254, 254));
        chiTietNgayCB.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        chiTietNgayCB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chiTietNgayCBActionPerformed(evt);
            }
        });

        jLabel11.setText("Ngày");
        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N

        chiTietTKNgayTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        chiTietTKNgayTable.setSelectionBackground(new java.awt.Color(153, 153, 255));
        chiTietTKNgayTable.setSelectionForeground(new java.awt.Color(254, 254, 254));
        chiTietTKNgayTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane2.setViewportView(chiTietTKNgayTable);

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(chiTietNgayCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(55, 218, Short.MAX_VALUE))
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(chiTietNgayCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 322, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel10.add(jPanel14);

        jPanel15.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout chiTietPanelLayout = new javax.swing.GroupLayout(chiTietPanel);
        chiTietPanel.setLayout(chiTietPanelLayout);
        chiTietPanelLayout.setHorizontalGroup(
            chiTietPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(chiTietPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        chiTietPanelLayout.setVerticalGroup(
            chiTietPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(chiTietPanelLayout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        thongKeTabPanel.addTab("Thống kê chi tiết", chiTietPanel);

        chamLuongPanel.setBackground(new java.awt.Color(255, 255, 255));

        luongTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        luongTable.setSelectionBackground(new java.awt.Color(153, 153, 255));
        luongTable.setSelectionForeground(new java.awt.Color(254, 254, 254));
        luongTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        luongTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                luongTableMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(luongTable);

        timTheoTenTF.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        timTheoTenTF.setBackground(new java.awt.Color(254, 254, 254));
        timTheoTenTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                timTheoTenTFActionPerformed(evt);
            }
        });

        timTheoTenBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/HinhAnh/search_white.png"))); // NOI18N
        timTheoTenBtn.setText("Tìm theo tên");
        timTheoTenBtn.setBackground(new java.awt.Color(32, 136, 203));
        timTheoTenBtn.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        timTheoTenBtn.setForeground(new java.awt.Color(255, 255, 255));
        timTheoTenBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                timTheoTenBtnActionPerformed(evt);
            }
        });

        tKLuongThangCB.setBackground(new java.awt.Color(254, 254, 254));
        tKLuongThangCB.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tKLuongThangCB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tKLuongThangCBActionPerformed(evt);
            }
        });
        tKLuongThangCB.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tKLuongThangCBPropertyChange(evt);
            }
        });

        tKLuongNamCB.setBackground(new java.awt.Color(254, 254, 254));
        tKLuongNamCB.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tKLuongNamCB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tKLuongNamCBActionPerformed(evt);
            }
        });

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jPanel16.setBackground(new java.awt.Color(114, 102, 186));

        jLabel13.setText("Thông tin chi tiết");
        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel13)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jLabel14.setText("Tài khoản");
        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        jLabel15.setText("Tên nhân viên");
        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        jLabel16.setText("CMND");
        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        jLabel17.setText("Chức vụ");
        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        jLabel18.setText("Thời gian");
        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        tenNVLbTKL.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        cmndNVLbTKL.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        tKNVLbTKL.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        chucVuNVLbTKL.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        tgNVLbTKL.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        jLabel24.setText("Số ca làm việc");
        jLabel24.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        soCLVNVTFTKL.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        soCLVNVTFTKL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                soCLVNVTFTKLActionPerformed(evt);
            }
        });

        jLabel25.setText("Lương cơ bản");
        jLabel25.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        jLabel26.setText("Hệ số lương");
        jLabel26.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        jLabel27.setText("Thành lương");
        jLabel27.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        luongCBNVTFTKL.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        luongCBNVTFTKL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                luongCBNVTFTKLActionPerformed(evt);
            }
        });

        hslNVTFTKL.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        hslNVTFTKL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hslNVTFTKLActionPerformed(evt);
            }
        });

        tienLuongNVLbTKL.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        ttlPanel.setBackground(new java.awt.Color(255, 255, 255));

        ttlBtn.setText("Thanh toán lương");
        ttlBtn.setBackground(new java.awt.Color(32, 136, 203));
        ttlBtn.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        ttlBtn.setForeground(new java.awt.Color(255, 255, 255));
        ttlBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ttlBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout ttlPanelLayout = new javax.swing.GroupLayout(ttlPanel);
        ttlPanel.setLayout(ttlPanelLayout);
        ttlPanelLayout.setHorizontalGroup(
            ttlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ttlPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ttlBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        ttlPanelLayout.setVerticalGroup(
            ttlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ttlPanelLayout.createSequentialGroup()
                .addContainerGap(47, Short.MAX_VALUE)
                .addComponent(ttlBtn)
                .addGap(20, 20, 20))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ttlPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel15)
                            .addComponent(jLabel16)
                            .addComponent(jLabel14)
                            .addComponent(jLabel17)
                            .addComponent(jLabel18)
                            .addComponent(jLabel24))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tKNVLbTKL, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(tenNVLbTKL, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cmndNVLbTKL, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(chucVuNVLbTKL, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(tgNVLbTKL, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(soCLVNVTFTKL)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel27)
                        .addGap(28, 28, 28)
                        .addComponent(tienLuongNVLbTKL, javax.swing.GroupLayout.DEFAULT_SIZE, 309, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel25)
                        .addGap(22, 22, 22)
                        .addComponent(luongCBNVTFTKL))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel26)
                        .addGap(33, 33, 33)
                        .addComponent(hslNVTFTKL)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(tKNVLbTKL))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(tenNVLbTKL))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(cmndNVLbTKL))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(chucVuNVLbTKL))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(tgNVLbTKL))
                .addGap(9, 9, 9)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(soCLVNVTFTKL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25)
                    .addComponent(luongCBNVTFTKL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(hslNVTFTKL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27)
                    .addComponent(tienLuongNVLbTKL))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                .addComponent(ttlPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        themLuongNVBtn.setText("Thêm");
        themLuongNVBtn.setBackground(new java.awt.Color(32, 136, 203));
        themLuongNVBtn.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        themLuongNVBtn.setForeground(new java.awt.Color(255, 255, 255));
        themLuongNVBtn.setToolTipText("");
        themLuongNVBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                themLuongNVBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout chamLuongPanelLayout = new javax.swing.GroupLayout(chamLuongPanel);
        chamLuongPanel.setLayout(chamLuongPanelLayout);
        chamLuongPanelLayout.setHorizontalGroup(
            chamLuongPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(chamLuongPanelLayout.createSequentialGroup()
                .addGroup(chamLuongPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(chamLuongPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(chamLuongPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 503, Short.MAX_VALUE)
                            .addGroup(chamLuongPanelLayout.createSequentialGroup()
                                .addComponent(timTheoTenTF)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(timTheoTenBtn)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(themLuongNVBtn)))
                        .addGap(10, 10, 10))
                    .addGroup(chamLuongPanelLayout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(tKLuongThangCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(tKLuongNamCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        chamLuongPanelLayout.setVerticalGroup(
            chamLuongPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, chamLuongPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(chamLuongPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(chamLuongPanelLayout.createSequentialGroup()
                        .addGroup(chamLuongPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(themLuongNVBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(timTheoTenBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(timTheoTenTF, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(chamLuongPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tKLuongThangCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tKLuongNamCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        thongKeTabPanel.addTab("Thống kê lương", chamLuongPanel);

        thongKeBanHangPanel.setBackground(new java.awt.Color(255, 255, 255));
        thongKeBanHangPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jPanel19.setBackground(new java.awt.Color(114, 102, 186));

        jLabel20.setText("Thống kê bán hàng");
        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addGap(100, 100, 100)
                .addComponent(jLabel20)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel23.setBackground(new java.awt.Color(255, 255, 255));

        tKBanHangTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tKBanHangTable.setSelectionBackground(new java.awt.Color(153, 153, 255));
        tKBanHangTable.setSelectionForeground(new java.awt.Color(254, 254, 254));
        tKBanHangTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tKBanHangTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tKBanHangTableMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tKBanHangTable);

        jLabel21.setText("Ngày");
        jLabel21.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        chonNgayBHDP.setBackground(new java.awt.Color(254, 254, 254));
        chonNgayBHDP.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        chonNgayBHDP.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                chonNgayBHDPPropertyChange(evt);
            }
        });

        jLabel22.setText("Ca làm");
        jLabel22.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        caLamBHComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tất cả", "Sáng", "Chiều" }));
        caLamBHComboBox.setBackground(new java.awt.Color(254, 254, 254));
        caLamBHComboBox.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        caLamBHComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                caLamBHComboBoxActionPerformed(evt);
            }
        });

        jLabel23.setText("Địa điểm");
        jLabel23.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        diaDiemBHCB.setBackground(new java.awt.Color(254, 254, 254));
        diaDiemBHCB.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        diaDiemBHCB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                diaDiemBHCBActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 559, Short.MAX_VALUE)
                    .addGroup(jPanel23Layout.createSequentialGroup()
                        .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel23)
                            .addComponent(jLabel21))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel23Layout.createSequentialGroup()
                                .addComponent(chonNgayBHDP, javax.swing.GroupLayout.DEFAULT_SIZE, 167, Short.MAX_VALUE)
                                .addGap(40, 40, 40)
                                .addComponent(jLabel22)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(caLamBHComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(diaDiemBHCB, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel23Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(chonNgayBHDP, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22)
                    .addComponent(caLamBHComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(diaDiemBHCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel20.setBackground(new java.awt.Color(255, 255, 255));
        jPanel20.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jPanel21.setBackground(new java.awt.Color(114, 102, 186));

        jLabel28.setText("Chi tiết");
        jLabel28.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jLabel28)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel28)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel29.setText("Tên nhân viên");
        jLabel29.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        jLabel30.setText("Địa điểm");
        jLabel30.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        jLabel31.setText("Ca làm");
        jLabel31.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        jLabel32.setText("Ngày");
        jLabel32.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        jLabel33.setText("Thời gian");
        jLabel33.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        jLabel34.setText("Tổng tiền");
        jLabel34.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        chiTietBanHangTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        chiTietBanHangTable.setSelectionBackground(new java.awt.Color(153, 153, 255));
        chiTietBanHangTable.setSelectionForeground(new java.awt.Color(254, 254, 254));
        chiTietBanHangTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        chiTietBanHangTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                chiTietBanHangTableMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(chiTietBanHangTable);

        jLabel35.setText("Danh sách sản phẩm");
        jLabel35.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        chiTietTenNVLb.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        chiTietDDBHLb.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        chiTietCaLamBHLb.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        chiTietNgayBHLb.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        chiTietTGBHLb.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        chiTietTongTienLb.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel20Layout.createSequentialGroup()
                        .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 354, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel35))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel20Layout.createSequentialGroup()
                        .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel34, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel29, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel33, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel30, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel31, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel32, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(chiTietTenNVLb, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(chiTietDDBHLb, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(chiTietCaLamBHLb, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(chiTietNgayBHLb, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(chiTietTGBHLb, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(chiTietTongTienLb, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel29)
                    .addComponent(chiTietTenNVLb))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel30)
                    .addComponent(chiTietDDBHLb))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel31)
                    .addComponent(chiTietCaLamBHLb))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel32)
                    .addComponent(chiTietNgayBHLb))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel33)
                    .addComponent(chiTietTGBHLb))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel34)
                    .addComponent(chiTietTongTienLb))
                .addGap(18, 18, 18)
                .addComponent(jLabel35)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE)
                .addGap(40, 40, 40))
        );

        javax.swing.GroupLayout thongKeBanHangPanelLayout = new javax.swing.GroupLayout(thongKeBanHangPanel);
        thongKeBanHangPanel.setLayout(thongKeBanHangPanelLayout);
        thongKeBanHangPanelLayout.setHorizontalGroup(
            thongKeBanHangPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(thongKeBanHangPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        thongKeBanHangPanelLayout.setVerticalGroup(
            thongKeBanHangPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(thongKeBanHangPanelLayout.createSequentialGroup()
                .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(thongKeBanHangPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        thongKeTabPanel.addTab("Thống kê bán hàng", thongKeBanHangPanel);

        thongKeDangKiPanel.setBackground(new java.awt.Color(255, 255, 255));
        thongKeDangKiPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jPanel24.setBackground(new java.awt.Color(255, 255, 255));

        tKDangKiTable.setBackground(new java.awt.Color(254, 254, 254));
        tKDangKiTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tKDangKiTable.setSelectionBackground(new java.awt.Color(153, 153, 255));
        tKDangKiTable.setSelectionForeground(new java.awt.Color(254, 254, 254));
        tKDangKiTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tKDangKiTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tKDangKiTableMouseClicked(evt);
            }
        });
        jScrollPane7.setViewportView(tKDangKiTable);

        jLabel36.setText("Ngày");
        jLabel36.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        chonNgayDKDP.setBackground(new java.awt.Color(254, 254, 254));
        chonNgayDKDP.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        chonNgayDKDP.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                chonNgayDKDPPropertyChange(evt);
            }
        });

        jLabel37.setText("Ca làm");
        jLabel37.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        caLamDKCB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tất cả", "Sáng", "Chiều" }));
        caLamDKCB.setBackground(new java.awt.Color(254, 254, 254));
        caLamDKCB.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        caLamDKCB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                caLamDKCBActionPerformed(evt);
            }
        });

        jLabel38.setText("Địa điểm");
        jLabel38.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        diaDiemDKCB.setBackground(new java.awt.Color(254, 254, 254));
        diaDiemDKCB.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        diaDiemDKCB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                diaDiemDKCBActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 559, Short.MAX_VALUE)
                    .addGroup(jPanel24Layout.createSequentialGroup()
                        .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel38)
                            .addComponent(jLabel36))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel24Layout.createSequentialGroup()
                                .addComponent(chonNgayDKDP, javax.swing.GroupLayout.DEFAULT_SIZE, 167, Short.MAX_VALUE)
                                .addGap(40, 40, 40)
                                .addComponent(jLabel37)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(caLamDKCB, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(diaDiemDKCB, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel24Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel36)
                    .addComponent(chonNgayDKDP, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel37)
                    .addComponent(caLamDKCB, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel38)
                    .addComponent(diaDiemDKCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel22.setBackground(new java.awt.Color(114, 102, 186));

        jLabel39.setText("Thống kê đăng kí");
        jLabel39.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel39.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addGap(100, 100, 100)
                .addComponent(jLabel39)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel22Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel39, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel25.setBackground(new java.awt.Color(255, 255, 255));
        jPanel25.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel25.setPreferredSize(new java.awt.Dimension(376, 431));

        jPanel26.setBackground(new java.awt.Color(114, 102, 186));

        jLabel40.setText("Chi tiết");
        jLabel40.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel40.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel26Layout = new javax.swing.GroupLayout(jPanel26);
        jPanel26.setLayout(jPanel26Layout);
        jPanel26Layout.setHorizontalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jLabel40)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel26Layout.setVerticalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel40)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel41.setText("Nhân viên chính");
        jLabel41.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        jLabel42.setText("Địa điểm");
        jLabel42.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        jLabel43.setText("Ca làm");
        jLabel43.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        jLabel44.setText("Ngày");
        jLabel44.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        jLabel45.setText("Thời gian");
        jLabel45.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        jLabel48.setText("Nhân viên phụ");
        jLabel48.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        jPanel18.setBackground(new java.awt.Color(255, 255, 255));
        jPanel18.setLayout(new java.awt.GridLayout(2, 1));

        jPanel27.setBackground(new java.awt.Color(255, 255, 255));

        chiTietDangKiTable.setBackground(new java.awt.Color(254, 254, 254));
        chiTietDangKiTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        chiTietDangKiTable.setSelectionBackground(new java.awt.Color(153, 153, 255));
        chiTietDangKiTable.setSelectionForeground(new java.awt.Color(254, 254, 254));
        chiTietDangKiTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        chiTietDangKiTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                chiTietDangKiTableMouseClicked(evt);
            }
        });
        jScrollPane8.setViewportView(chiTietDangKiTable);

        javax.swing.GroupLayout jPanel27Layout = new javax.swing.GroupLayout(jPanel27);
        jPanel27.setLayout(jPanel27Layout);
        jPanel27Layout.setHorizontalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 354, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel27Layout.setVerticalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 66, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel18.add(jPanel27);

        jPanel28.setBackground(new java.awt.Color(255, 255, 255));

        jLabel49.setText("Ghi chú");
        jLabel49.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        ghiChuTA.setEditable(false);
        ghiChuTA.setBackground(new java.awt.Color(254, 254, 254));
        ghiChuTA.setColumns(20);
        ghiChuTA.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        ghiChuTA.setRows(5);
        jScrollPane9.setViewportView(ghiChuTA);

        javax.swing.GroupLayout jPanel28Layout = new javax.swing.GroupLayout(jPanel28);
        jPanel28.setLayout(jPanel28Layout);
        jPanel28Layout.setHorizontalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel28Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel28Layout.createSequentialGroup()
                        .addComponent(jLabel49)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 354, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel28Layout.setVerticalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel28Layout.createSequentialGroup()
                .addComponent(jLabel49)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel18.add(jPanel28);

        jLabel47.setText("Danh sách sản phẩm");
        jLabel47.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        chiTietTenNVCLb.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        chiTietTenNVPLb.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        chiTietDDDKLb.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        chiTietCaLamDKLb.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        chiTietNgayDKLb.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        chiTietTGDKLb.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        javax.swing.GroupLayout jPanel25Layout = new javax.swing.GroupLayout(jPanel25);
        jPanel25.setLayout(jPanel25Layout);
        jPanel25Layout.setHorizontalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel25Layout.createSequentialGroup()
                        .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel41, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel42, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel43, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel44, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel45, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(chiTietTenNVCLb, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(chiTietDDDKLb, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(chiTietCaLamDKLb, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(chiTietNgayDKLb, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(chiTietTGDKLb, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel25Layout.createSequentialGroup()
                        .addComponent(jLabel47)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel25Layout.createSequentialGroup()
                        .addComponent(jLabel48, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(chiTietTenNVPLb, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel25Layout.setVerticalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addComponent(jPanel26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel41)
                    .addComponent(chiTietTenNVCLb))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel48)
                    .addComponent(chiTietTenNVPLb))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel42)
                    .addComponent(chiTietDDDKLb))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel43)
                    .addComponent(chiTietCaLamDKLb))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel44)
                    .addComponent(chiTietNgayDKLb))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel45)
                    .addComponent(chiTietTGDKLb))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel47)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, 155, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout thongKeDangKiPanelLayout = new javax.swing.GroupLayout(thongKeDangKiPanel);
        thongKeDangKiPanel.setLayout(thongKeDangKiPanelLayout);
        thongKeDangKiPanelLayout.setHorizontalGroup(
            thongKeDangKiPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(thongKeDangKiPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        thongKeDangKiPanelLayout.setVerticalGroup(
            thongKeDangKiPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(thongKeDangKiPanelLayout.createSequentialGroup()
                .addComponent(jPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(thongKeDangKiPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        thongKeTabPanel.addTab("Thống kê đăng kí", thongKeDangKiPanel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(thongKeTabPanel)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(thongKeTabPanel)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void chonTGCBPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_chonTGCBPropertyChange
        // TODO add your handling code here:

    }//GEN-LAST:event_chonTGCBPropertyChange

    private void chonTGCBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chonTGCBActionPerformed
        // TODO add your handling code here:
        taoDuLieuTongQuanPanel();
    }//GEN-LAST:event_chonTGCBActionPerformed

    private void chiTietThangCBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chiTietThangCBActionPerformed
        // TODO add your handling code here:
        int select = chiTietTKNamTable.getSelectedRow();
        if (select != -1){
            int index = chiTietThangCB.getSelectedIndex();
            int nam = (int)chiTietNamTableModel.getValueAt(chiTietTKNamTable.getSelectedRow(), 0);
            chiTietThangTableModel.setRowCount(0);
            if (index == 0){
                for (int i = 1; i <= 12; i++){
                    tKTheoThang(i, nam);
                }
            }else {
                tKTheoThang(index, nam);
            }
        }
        
        chiTietNgayCB.setEnabled(false);
        if (chiTietNgayTableModel != null)
            chiTietNgayTableModel.setRowCount(0);
    }//GEN-LAST:event_chiTietThangCBActionPerformed

    private void chiTietNamCBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chiTietNamCBActionPerformed
        // TODO add your handling code here:
        if (chiTietNamTableModel != null){
            chiTietNamTableModel.setRowCount(0);
            try {
                int nam = Integer.valueOf(chiTietNamCB.getSelectedItem().toString());
                tKTheoNam(nam);
            } catch (NumberFormatException e) {
                capNhatBangTKNam();
            } catch (NullPointerException e){
                capNhatBangTKNam();
            }
            
        }
        
        chiTietThangCB.setEnabled(false);
        chiTietNgayCB.setEnabled(false);
        if (chiTietThangTableModel != null)
            chiTietThangTableModel.setRowCount(0);
        if (chiTietNgayTableModel != null)
            chiTietNgayTableModel.setRowCount(0);
    }//GEN-LAST:event_chiTietNamCBActionPerformed

    private void chiTietTKNamTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_chiTietTKNamTableMouseClicked
        // TODO add your handling code here:
        int nam = (int)chiTietNamTableModel.getValueAt(chiTietTKNamTable.getSelectedRow(), 0);
        chiTietThangTableModel.setRowCount(0);
        for (int i = 1; i <= 12; i++){
            tKTheoThang(i, nam);
        }
        if (!chiTietThangCB.isEnabled())
            chiTietThangCB.setEnabled(true);
    }//GEN-LAST:event_chiTietTKNamTableMouseClicked

    private void chiTietTKThangTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_chiTietTKThangTableMouseClicked
        // TODO add your handling code here:
        int nam = (int)chiTietNamTableModel.getValueAt(chiTietTKNamTable.getSelectedRow(), 0);
        int thang = (int)chiTietThangTableModel.getValueAt(chiTietTKThangTable.getSelectedRow(), 0);
        chiTietNgayTableModel.setRowCount(0);
        
        taoNgayCB(thang);
        if (!chiTietNgayCB.isEnabled())
            chiTietNgayCB.setEnabled(true);
    }//GEN-LAST:event_chiTietTKThangTableMouseClicked

    private void chiTietNgayCBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chiTietNgayCBActionPerformed
        // TODO add your handling code here:
        int nam = (int)chiTietNamTableModel.getValueAt(chiTietTKNamTable.getSelectedRow(), 0);
        int thang = (int)chiTietThangTableModel.getValueAt(chiTietTKThangTable.getSelectedRow(), 0);
        int index = chiTietNgayCB.getSelectedIndex();
        chiTietNgayTableModel.setRowCount(0);
        if (index == -1) return;
        if (index == 0){
            for (int i = 1; i <= Util.soNGayTrongThang(thang, nam); i++){
                tKTheoNgay(i, thang, nam);
            }
        }else {
            tKTheoNgay(index, thang, nam);
        }
    }//GEN-LAST:event_chiTietNgayCBActionPerformed

    private void timTheoTenBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_timTheoTenBtnActionPerformed
        // TODO add your handling code here:
        
        tKLuong();
    }//GEN-LAST:event_timTheoTenBtnActionPerformed

    private void timTheoTenTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_timTheoTenTFActionPerformed
        // TODO add your handling code here:
        
        tKLuong();
    }//GEN-LAST:event_timTheoTenTFActionPerformed

    private void tKLuongThangCBPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tKLuongThangCBPropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_tKLuongThangCBPropertyChange

    private void tKLuongThangCBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tKLuongThangCBActionPerformed
        // TODO add your handling code here:
        if (dsLuongHienThi != null){
            tKLuong();
        }
    }//GEN-LAST:event_tKLuongThangCBActionPerformed

    private void tKLuongNamCBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tKLuongNamCBActionPerformed
        // TODO add your handling code here:
        if (dsLuongHienThi != null){
            tKLuong();
        }
    }//GEN-LAST:event_tKLuongNamCBActionPerformed

    private void luongTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_luongTableMouseClicked
        // TODO add your handling code here:
        thongTinChitietLuong();
    }//GEN-LAST:event_luongTableMouseClicked

    private void ttlBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ttlBtnActionPerformed
        // TODO add your handling code here:
        thanhToanLuong();
        capNhatBangLuong();
    }//GEN-LAST:event_ttlBtnActionPerformed

    private void soCLVNVTFTKLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_soCLVNVTFTKLActionPerformed
        // TODO add your handling code here:
        capNhatLuong();
    }//GEN-LAST:event_soCLVNVTFTKLActionPerformed

    private void luongCBNVTFTKLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_luongCBNVTFTKLActionPerformed
        // TODO add your handling code here:
        capNhatLuong();
    }//GEN-LAST:event_luongCBNVTFTKLActionPerformed

    private void hslNVTFTKLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hslNVTFTKLActionPerformed
        // TODO add your handling code here:
        capNhatLuong();
    }//GEN-LAST:event_hslNVTFTKLActionPerformed

    private void themLuongNVBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_themLuongNVBtnActionPerformed
        // TODO add your handling code here:
        
        
        chonThangLuongCB.removeAllItems();
        int dem = 0;
        
        for (String item : dsThanNamCLVTrongNam){
            String[] s = item.split("-");
            String tg = s[1] + "-" + s[0];
            if (!data.ktThemThangLuong(tg)){
                chonThangLuongCB.addItem(tg);
                dem ++;
            }
        }
            
        
        if (dem != 0)
            themLuongChoNhanVienDialog.setVisible(true);
        else JOptionPane.showMessageDialog(this, "Danh sách lương đã tồn tại","Thông báo",JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_themLuongNVBtnActionPerformed

    private void chonThangLuongBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chonThangLuongBtnActionPerformed
        // TODO add your handling code here:
        themLuongChoNhanVienDialog.setVisible(false);
        
        themDSLuong();
        
        capNhatBangLuong();
    }//GEN-LAST:event_chonThangLuongBtnActionPerformed

    private void diaDiemBHCBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_diaDiemBHCBActionPerformed
        // TODO add your handling code here:
        if (dsBanHangHienThi != null){
            hienDuLieuBanHang();
        }
    }//GEN-LAST:event_diaDiemBHCBActionPerformed

    private void caLamBHComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_caLamBHComboBoxActionPerformed
        // TODO add your handling code here:
        if (dsBanHangHienThi != null){
            hienDuLieuBanHang();
        }
    }//GEN-LAST:event_caLamBHComboBoxActionPerformed

    private void chonNgayBHDPPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_chonNgayBHDPPropertyChange
        // TODO add your handling code here:
        if (dsBanHangHienThi != null){
            hienDuLieuBanHang();
        }
    }//GEN-LAST:event_chonNgayBHDPPropertyChange

    private void chiTietBanHangTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_chiTietBanHangTableMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_chiTietBanHangTableMouseClicked

    private void tKBanHangTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tKBanHangTableMouseClicked
        // TODO add your handling code here:
        xemChiTietBanHang();
    }//GEN-LAST:event_tKBanHangTableMouseClicked

    private void tKDangKiTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tKDangKiTableMouseClicked
        // TODO add your handling code here:
        xemChiTietDangKi();
    }//GEN-LAST:event_tKDangKiTableMouseClicked

    private void chonNgayDKDPPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_chonNgayDKDPPropertyChange
        // TODO add your handling code here:
        if (dsDangKiHienThi != null){
            hienDuLieuDangKi();
        }
    }//GEN-LAST:event_chonNgayDKDPPropertyChange

    private void caLamDKCBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_caLamDKCBActionPerformed
        // TODO add your handling code here:
        if (dsDangKiHienThi != null){
            hienDuLieuDangKi();
        }
    }//GEN-LAST:event_caLamDKCBActionPerformed

    private void diaDiemDKCBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_diaDiemDKCBActionPerformed
        // TODO add your handling code here:
        if (dsDangKiHienThi != null){
            hienDuLieuDangKi();
        }
    }//GEN-LAST:event_diaDiemDKCBActionPerformed

    private void chiTietDangKiTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_chiTietDangKiTableMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_chiTietDangKiTableMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> caLamBHComboBox;
    private javax.swing.JComboBox<String> caLamDKCB;
    private javax.swing.JPanel chamLuongPanel;
    private javax.swing.JLayeredPane chartLayeredPane;
    private javax.swing.JLabel chiPhiLb;
    private javax.swing.JTable chiTietBanHangTable;
    private javax.swing.JLabel chiTietCaLamBHLb;
    private javax.swing.JLabel chiTietCaLamDKLb;
    private javax.swing.JLabel chiTietDDBHLb;
    private javax.swing.JLabel chiTietDDDKLb;
    private javax.swing.JTable chiTietDangKiTable;
    private javax.swing.JComboBox<String> chiTietNamCB;
    private javax.swing.JLabel chiTietNgayBHLb;
    private javax.swing.JComboBox<String> chiTietNgayCB;
    private javax.swing.JLabel chiTietNgayDKLb;
    private javax.swing.JPanel chiTietPanel;
    private javax.swing.JLabel chiTietTGBHLb;
    private javax.swing.JLabel chiTietTGDKLb;
    private javax.swing.JTable chiTietTKNamTable;
    private javax.swing.JTable chiTietTKNgayTable;
    private javax.swing.JTable chiTietTKThangTable;
    private javax.swing.JLabel chiTietTenNVCLb;
    private javax.swing.JLabel chiTietTenNVLb;
    private javax.swing.JLabel chiTietTenNVPLb;
    private javax.swing.JComboBox<String> chiTietThangCB;
    private javax.swing.JLabel chiTietTongTienLb;
    private com.github.lgooddatepicker.components.DatePicker chonNgayBHDP;
    private com.github.lgooddatepicker.components.DatePicker chonNgayDKDP;
    private javax.swing.JComboBox<String> chonTGCB;
    private javax.swing.JButton chonThangLuongBtn;
    private javax.swing.JComboBox<String> chonThangLuongCB;
    private javax.swing.JLabel chucVuNVLbTKL;
    private javax.swing.JLabel cmndNVLbTKL;
    private javax.swing.JComboBox<String> diaDiemBHCB;
    private javax.swing.JComboBox<String> diaDiemDKCB;
    private javax.swing.JLabel doanhThuLb;
    private javax.swing.JTextArea ghiChuTA;
    private javax.swing.JTextField hslNVTFTKL;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel28;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JLabel loiNhuanLb;
    private javax.swing.JTextField luongCBNVTFTKL;
    private javax.swing.JTable luongTable;
    private javax.swing.JTextField soCLVNVTFTKL;
    private javax.swing.JTable tKBanHangTable;
    private javax.swing.JTable tKDangKiTable;
    private javax.swing.JComboBox<String> tKLuongNamCB;
    private javax.swing.JComboBox<String> tKLuongThangCB;
    private javax.swing.JLabel tKNVLbTKL;
    private javax.swing.JLabel tenNVLbTKL;
    private javax.swing.JLabel tgNVLbTKL;
    private javax.swing.JDialog themLuongChoNhanVienDialog;
    private javax.swing.JButton themLuongNVBtn;
    private javax.swing.JPanel thongKeBanHangPanel;
    private javax.swing.JPanel thongKeDangKiPanel;
    private javax.swing.JTabbedPane thongKeTabPanel;
    private javax.swing.JLabel tienLuongNVLbTKL;
    private javax.swing.JButton timTheoTenBtn;
    private javax.swing.JTextField timTheoTenTF;
    private javax.swing.JPanel tongQuanPanel;
    private javax.swing.JButton ttlBtn;
    private javax.swing.JPanel ttlPanel;
    // End of variables declaration//GEN-END:variables
}
