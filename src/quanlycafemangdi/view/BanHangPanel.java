/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quanlycafemangdi.view;

import com.github.lgooddatepicker.components.DatePickerSettings;
import java.awt.Color;
import java.awt.Font;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import quanlycafemangdi.Util;
import quanlycafemangdi.data.Data;
import quanlycafemangdi.model.BanHang;
import quanlycafemangdi.model.CaLamViec;
import quanlycafemangdi.model.CongThuc;
import quanlycafemangdi.model.DangKi;
import quanlycafemangdi.model.DiaDiem;
import quanlycafemangdi.model.NhapXuat;
import quanlycafemangdi.model.SanPham;
import quanlycafemangdi.model.ThongTinDangNhap;

/**
 *
 * @author monar
 */
public class BanHangPanel extends javax.swing.JPanel implements SanPhamPanel.IOnClickSPPanel, 
        ThanhPhanHoaDonPanel.IOnSpinnerStateChange{
    

    private final Data data;
    
    private List<SanPham> dsSanPham;
    private List<CongThuc> dsCongThuc;
    private HashMap<Integer,Integer> thanhPhanHoaDon;
    private HashMap<Integer,Integer> thanhPhanDangKi;
    private HashMap<String,Integer> khoHienTai;
    private HashMap<String,Integer> khoDangKi;
    private List<DiaDiem> dsDiaDiem;
    private List<CaLamViec> dsCaLamViec;
    private List<CaLamViec> dsCaLamViecHienTai;
    private ArrayList<CaLamViec> dsCaLamViecDialog;
    private List<BanHang> dsBanHangTrongCLV;
    private List<DangKi> dsDangKiTrongCLV;
    
    private DefaultTableModel defaultTableModelLichLam;
    private DefaultTableModel defaultTableModelDangKi;
    private DefaultTableModel defaultTableModelDialog;
    private DefaultTableModel defaultTableModelNL;
    private DefaultTableModel defaultTableModelSP;
    private DefaultTableModel defaultTableModelBH;
    private DefaultTableModel defaultTableModelDK;
    
    private int soLuongSanPhamBanHang;
    private long tongTien;
    private CaLamViec dialogCVL;
    /**
     * Creates new form BanHangPanel
     */
    public BanHangPanel() {
        initComponents();
        
        data = Data.getInstance();
        this.thanhPhanHoaDon = new HashMap<>();
        this.thanhPhanDangKi = new HashMap<>();
        this.khoHienTai = new HashMap<>();
        this.khoDangKi = new HashMap<>();
        
        
        initData();
        initTable();
    }
    
    private void initTable(){
        xetBang(lichLamTable);
        xetBang(soLuongNguyenLieuTable);
        xetBang(bangNLConLaiTable);
        xetBang(bangSPConLaiTable);
        xetBang(luaChonCLVTable);
        xetBang(thongKeBHTable);
        xetBang(thongKeDKTable);
    }
    private void xetBang(JTable table){
        table.setFont(new Font("Tahoma", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(114,102,186));
        table.getTableHeader().setForeground(Color.white);
    }
    private void initData(){
        tenNVLb.setText(ThongTinDangNhap.getTenNguoiDung());
        taoDSSP();
        taoThanhPhanHoaDon();
        taoDSCT();
        taoDuLieuLichLam();
        taoDuLieuDangKi();
        taoDuLieuKhoHienTai();
        taoDuLieuDialog();
        taoDuLieuQuanLyNLBan();
        taoDuLieuThongKe();
        
        if(layCaLamViecHienTai() == null){
            banHangPanel.removeAll();
            banHangPanel.add(khongCoCaLamPanel);
            donHangPanel.repaint();
            donHangPanel.validate();
        }else {
            banHangPanel.removeAll();
            banHangPanel.add(coCaLamPanel);
            donHangPanel.repaint();
            donHangPanel.validate();
        }
    }
    
    private void taoDuLieuKhoHienTai(){
        khoHienTai.clear();
        CaLamViec clv = layCaLamViecHienTai();
        if(clv != null){
            HashMap<String,Integer> dKMap = data.layDSNLDangKiTheoCa(clv.getMaCLV());
            HashMap<String,Integer> bHMap = data.layDSBanSPTheoCa(clv.getMaCLV());
            HashMap<String,Integer> traNLHashMap = data.layDSTraNLTheoCa(clv);
            HashMap<String,Integer> nguyenLieuBanHang = new HashMap<>();
            
            Set<String> mKSet = bHMap.keySet();// tra ve ma sp
            for (String key : mKSet){
                //chuyen ma sp ve ma nl
                HashMap<String, Integer> tmpMap = timNLTheoSP(timSPTheoMaSP(key));
                Set<String> mKeySet = tmpMap.keySet();
                for (String key2 : mKeySet){// key  : ma Nguyen lieu
                    if(nguyenLieuBanHang.get(key2) != null){
                        nguyenLieuBanHang.put(key2, nguyenLieuBanHang.get(key2) + tmpMap.get(key2) * bHMap.get(key));
                    }else {
                        nguyenLieuBanHang.put(key2, tmpMap.get(key2) * bHMap.get(key));
                    }
                }
            }
            
            Set<String> kSet = dKMap.keySet();
            for (String key : kSet){
                
                int slBH = 0;
                int slTNL = 0;
                if (nguyenLieuBanHang.get(key) != null){
                    slBH = nguyenLieuBanHang.get(key);
                }
                if (traNLHashMap.get(key) != null){
                    slTNL = traNLHashMap.get(key);
                }
                
                int sl = dKMap.get(key) - slBH - slTNL;
                if (sl > 0){
                    khoHienTai.put(key, sl);
                }
                
            }
        }
        
    }
    
    private SanPham timSPTheoMaSP(String maSP){
        for (SanPham sp : dsSanPham){
            if (sp.getMaSP().equals(maSP)){
                return sp;
            }
        }
        return null;
    }

    
    private HashMap<String,Integer> kiemTraKhoHienTai(){
        // return true : kho con du de ban hang
        HashMap<String,Integer> nLThanhToan = new HashMap<>();
        
        Set<Integer> keySet = thanhPhanHoaDon.keySet();
        for (Integer item : keySet){
            int sl = thanhPhanHoaDon.get(item);
            if (sl > 0){
                SanPham sp = dsSanPham.get(item);
                
                // tim nguyen lieu tu cong thuc
                HashMap<String, Integer> mMap = timNLTheoSP(sp);
                Set<String> mKeySet = mMap.keySet();
                for (String key : mKeySet){// key  : ma Nguyen lieu
                    if(nLThanhToan.get(key) != null){
                        nLThanhToan.put(key, nLThanhToan.get(key) + mMap.get(key) * sl);
                    }else {
                        nLThanhToan.put(key, mMap.get(key) * sl);
                    }
                }
            }
        }
        
        return nLThanhToan;
    }
// Ban Hang Panel    
    private void taoThanhPhanHoaDon(){
        soLuongSanPhamBanHang = 0;
        tongTien = 0;
        
        xetNutThanhToanHien(false);
    }
    private void taoDSSP(){
        dsSanPham = data.layDSSanPham();
        dsSanPham.forEach(item -> {
            sanPhamBanHangPanel.add(new SanPhamPanel(item, SanPhamPanel.BAN_HANG, this));
        });
        
    }
    
    private void capNhatHoaDon(){
        donHangPanel.removeAll();
        
        
        Set<Integer> keySet  = thanhPhanHoaDon.keySet();
        keySet.forEach(item -> {
            if(thanhPhanHoaDon.get(item) > 0){
                donHangPanel.add(new ThanhPhanHoaDonPanel(dsSanPham.get(item),
                        thanhPhanHoaDon.get(item), ThanhPhanHoaDonPanel.BAN_HANG, this));
                
            }
        });
        
        
        donHangPanel.repaint();
        donHangPanel.validate();
        capNhatTongTien();
        
    }
    
    private void capNhatTongTien(){
        soLuongSanPhamBanHang = 0;
        tongTien = 0;
        
        Set<Integer> keySet  = thanhPhanHoaDon.keySet();
        keySet.forEach(item -> {
            int sl = thanhPhanHoaDon.get(item);
            if(sl > 0){
                soLuongSanPhamBanHang += sl;
                tongTien += sl * dsSanPham.get(item).getGia();
            }
        });
        
        if (soLuongSanPhamBanHang == 0){
            soLuongDonHangLb.setText("0");
            tongTienLb.setText("0");
            
            xetNutThanhToanHien(false);
        }
        else {
            soLuongDonHangLb.setText(String.valueOf(soLuongSanPhamBanHang));
            tongTienLb.setText(Util.formatCurrency(tongTien));
            
            xetNutThanhToanHien(true);
        }
    }
    private void xoaHoaDon(){
        donHangPanel.removeAll();
        
        
        Set<Integer> keySet  = thanhPhanHoaDon.keySet();
        keySet.forEach(item -> {
            thanhPhanHoaDon.put(item, 0);
        });
        
        
        donHangPanel.repaint();
        donHangPanel.validate();
        capNhatTongTien();
    }
    
    private void thanhToanHoaDon(){
        
        HashMap<String,Integer> khoTam = kiemTraKhoHienTai();
        Set<String> khoTamKeySet = khoTam.keySet();
        for (String key: khoTamKeySet){
            if (khoHienTai.get(key) == null){
                JOptionPane.showMessageDialog(this, "Kh??ng c?? nguy??n li???u","Th??ng b??o",JOptionPane.ERROR_MESSAGE);
                return;
            }else if (khoHienTai.get(key) - khoTam.get(key) < 0){
                JOptionPane.showMessageDialog(this, "Kh??ng ????? s??? l?????ng nguy??n li???u","Th??ng b??o",JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        for (String key: khoTamKeySet){
            khoHienTai.put(key, khoHienTai.get(key) - khoTam.get(key));
        }
        
        
        
        String maBh = Util.autoGenId(Util.BH_TABLE);
        String tenTK = ThongTinDangNhap.getTenDangNhap();
        
        // lay thoi gian thuc
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String tg = dtf.format(now);
        
        // lay thong tin va so luong san pham
        HashMap<String,Integer> mMap = new HashMap<>();
        Set<Integer> keySet  = thanhPhanHoaDon.keySet();
        keySet.forEach(item -> {
            int sl = thanhPhanHoaDon.get(item);
            if (sl > 0) {
                mMap.put(dsSanPham.get(item).getMaSP(), sl);
            }
        });
        
            
        BanHang banHang = new BanHang(maBh,tenTK,layCaLamViecHienTai().getMaCLV(),tg,tongTien,mMap);
        
        data.taoHoaDonBanHang(banHang);
        JOptionPane.showMessageDialog(this, "Thanh to??n th??nh c??ng","Th??ng b??o",JOptionPane.INFORMATION_MESSAGE);
        xoaHoaDon();
        capNhatHaiBangQuanLy();
        capNhatThongKeTable();
    }
    
    private void xetNutThanhToanHien(boolean b){
        thanhToanBtn.setEnabled(b);
        huyDonHangBtn.setEnabled(b);
    }

// Cong thuc panel    
    private void taoDSCT(){
        dsCongThuc = data.layDSCongThuc();
        for (CongThuc ct : dsCongThuc){
            for(SanPham sp : dsSanPham){
                if (sp.getMaSP().equals(ct.getMaSP())){
                    sanPhamCongThucPanel.add(new SanPhamPanel(sp, 
                            SanPhamPanel.CONG_THUC, this));
                    break;
                }
            }
        }
    }
    private void chiTietCongThuc(SanPham sanPham){
        CongThuc congThuc = null;
        for (CongThuc ct : dsCongThuc){
            if (ct.getMaSP().equals(sanPham.getMaSP())){
                congThuc = ct;
                break;
            }
        }
        tenCTLb.setText("C??ng th???c pha ch??? " + sanPham.getTenSP());
        String nguyenLieu = "";
        
        Set<String> keySet = congThuc.getChiTietCT().keySet();
        for (String key : keySet){
            nguyenLieu += "???  " + congThuc.getChiTietCT().get(key) + " " + data.layTenNguyenLieu(key) + "\n";
        }
        
        chiTietNguyenLieuTA.setText(nguyenLieu);
        ChiTietPhaCheTA.setText(congThuc.getCachLam());
    }
    
    
// lich lam Panel
    private void taoDuLieuLichLam(){
        defaultTableModelLichLam = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        dsDiaDiem = data.layDSDiaDiem();
        
        // lay danh sach ca lam viec theo ca nhan
        dsCaLamViec = data.layDSCaLamViec();
        
        ArrayList<CaLamViec> tempList = new ArrayList<>();
        for (CaLamViec clv : dsCaLamViec){
            if (clv.getTK1().equals(ThongTinDangNhap.getTenDangNhap())){
                tempList.add(clv);
            }
            if(clv.getTK2() != null){
                if (clv.getTK2().equals(ThongTinDangNhap.getTenDangNhap())){
                    tempList.add(clv);
                }
            }
        }
        dsCaLamViec = tempList;
        
        diaDiemCB.removeAllItems();
        diaDiemCB.addItem("T???t c???");
        dsDiaDiem.forEach(dd -> {
            diaDiemCB.addItem(dd.getViTri());
        });
        
        
        dsCaLamViecHienTai = dsCaLamViec;
        hienBangSapXep(dsCaLamViecHienTai);
        
        DatePickerSettings datePickerSettings = new DatePickerSettings();
        datePickerSettings.setAllowEmptyDates(true);
        datePickerSettings.setFormatForDatesCommonEra("dd-MM-yyyy");
        
        chonNgayLamDC.setSettings(datePickerSettings);
        
        
        chiTietCaLamViecPanel.setVisible(false);
    }
    
    // chon che de sap xep theo ngay
    private void hienBangSapXep(List<CaLamViec> list){
        int index = sapXepTheoThoiGianCB.getSelectedIndex();
        
        switch(index){
            case 0 -> Collections.sort(list, (CaLamViec o1, CaLamViec o2) -> 
                    o1.getNgay().compareTo(o2.getNgay()));
            case 1 -> Collections.sort(list, (CaLamViec o1, CaLamViec o2) -> 
                    o2.getNgay().compareTo(o1.getNgay()));
        }
        xetDuLieuBangLichLam(list);
    }
    
    private void xetDuLieuBangLichLam(List<CaLamViec> list){
        defaultTableModelLichLam.setRowCount(0);
        defaultTableModelLichLam.setColumnCount(0);
        defaultTableModelLichLam.addColumn("Ng??y l??m");
        defaultTableModelLichLam.addColumn("?????a ??i???m");
        defaultTableModelLichLam.addColumn("Ca L??m Vi???c");
        
        for (CaLamViec clv: list){
            Vector vt = new Vector();
            String ngay[] = clv.getNgay().split("-");
            vt.add(ngay[2] + "-" + ngay[1] + "-" + ngay[0]);
            
            vt.add(layDiaDiem(clv.getMaDD()));
            vt.add(clv.getCaLamViec());
            defaultTableModelLichLam.addRow(vt);
        }
        
        lichLamTable.setModel(defaultTableModelLichLam);
        
    }
    private String layDiaDiem(String maDD){
        for(DiaDiem dd : dsDiaDiem){
            if (maDD.equals(dd.getMaDD()))
                return dd.getViTri();
        }
        return null;
    }
    
    private void chonDiaDiemTimKiem(){
        String tenDiaDiem = String.valueOf(diaDiemCB.getSelectedItem());
        if (tenDiaDiem.equals("T???t c???")){
            dsCaLamViecHienTai = dsCaLamViec;
            hienBangSapXep(dsCaLamViecHienTai);
        }else {
            ArrayList<CaLamViec> mList = new ArrayList<>();
            for (CaLamViec clv : dsCaLamViec) {
                if (layDiaDiem(clv.getMaDD()).equals(tenDiaDiem)){
                    mList.add(clv);
                }
            }
            dsCaLamViecHienTai = mList;
        }
        
    }
    
    private void chonNgayTimKiem(){
        
        if (!chonNgayLamDC.getDateStringOrEmptyString().isEmpty() && chonNgayLamDC.isEnabled()){
            ArrayList<CaLamViec> mList = new ArrayList<>();
            String date = chonNgayLamDC.getDateStringOrEmptyString();
            for (CaLamViec clv : dsCaLamViecHienTai){
                if (clv.getNgay().equals(date)){
                    mList.add(clv);
                }
            }
            
            dsCaLamViecHienTai = mList;
        }
            
    }
    
    private void timKiemCaLamViec(){
        chonDiaDiemTimKiem();
        chonNgayTimKiem();
        hienBangSapXep(dsCaLamViecHienTai);
    }
    
    private void chiTietCaLam(){
        if (!chiTietCaLamViecPanel.isVisible())
            chiTietCaLamViecPanel.setVisible(true);
        
        int index = lichLamTable.getSelectedRow();
        
        
        diaDiemChiTietLb.setText(layDiaDiem(dsCaLamViecHienTai.get(index).getMaDD()));
        caLamViecChiTietLb.setText(dsCaLamViecHienTai.get(index).getCaLamViec());
        
        String ngay[] = dsCaLamViecHienTai.get(index).getNgay().split("-");
        ngayChiTietLb.setText(ngay[2] + "-" + ngay[1] + "-" + ngay[0]);
        
        nguoiLamChinhLb.setText(data.layTenNguoiDung(dsCaLamViecHienTai.get(index).getTK1()));
        
        if (dsCaLamViecHienTai.get(index).getTK2() != null){
            nguoiLamPhuLb.setText(data.layTenNguoiDung(dsCaLamViecHienTai.get(index).getTK2()));
        }else {
            nguoiLamPhuLb.setText("Kh??ng c??");
        }
    }
    
// dang ki san pham Panel
    private void taoDuLieuDangKi(){
        
        defaultTableModelDangKi = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        defaultTableModelDangKi.setColumnCount(0);
        defaultTableModelDangKi.addColumn("M?? nguy??n li???u");
        defaultTableModelDangKi.addColumn("T??n nguy??n li???u");
        defaultTableModelDangKi.addColumn("S??? l?????ng");
        soLuongNguyenLieuTable.setModel(defaultTableModelDangKi);
        
        tenNVDKLb.setText(ThongTinDangNhap.getTenNguoiDung());
        
        CaLamViec clv = layCaLamViecHienTai();
        if (clv != null){
            tenDiaDiemDKLB.setText(layDiaDiem(clv.getMaDD()));
        }else {
            diaDiemDKLb.setText("");
            tenDiaDiemDKLB.setText("");
        }
        
        taoDuLieuDKSanPhamPanel();
        xetNutDangKiNL(false);
        
    }
    private CaLamViec layCaLamViecHienTai(){
        
        LocalDateTime now = LocalDateTime.now();
        
        String ngayHienTai = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(now);
        int gioHienTai = Integer.valueOf(DateTimeFormatter.ofPattern("HH").format(now));
        
        for (CaLamViec clv : dsCaLamViec){
            if (clv.getNgay().equals(ngayHienTai)){
               if (gioHienTai < 12 && clv.getCaLamViec().equals("S??ng")){
                   return clv;
               }
            }
            if (clv.getNgay().equals(ngayHienTai)){
               if (gioHienTai > 12 && clv.getCaLamViec().equals("Chi???u")){
                   return clv;
               }
            }
           
        }
        
        return null;
    }
    private void taoDuLieuDKSanPhamPanel(){
        dsSanPham.forEach(item -> {
            dkSanPhamPanel.add(new SanPhamPanel(item, SanPhamPanel.DANG_KY, this));
        });
    }
    
    
    private void dangKyNL(SanPham sp){
        
        int index = dsSanPham.indexOf(sp);
        if (index > -1){
            if (thanhPhanDangKi.get(index) != null){
                thanhPhanDangKi.put(index, thanhPhanDangKi.get(index) + 1);
            }else {
                thanhPhanDangKi.put(index, 1);

            }

            capNhapHoaDonDangKy();
        }
        
    }
    private void capNhapHoaDonDangKy(){
        
        donDKChiTietPanel.removeAll();
        

        Set<Integer> keySet  = thanhPhanDangKi.keySet();
        keySet.forEach(item -> {
            if(thanhPhanDangKi.get(item) > 0){
                donDKChiTietPanel.add(new ThanhPhanHoaDonPanel(dsSanPham.get(item),
                        thanhPhanDangKi.get(item), ThanhPhanHoaDonPanel.DANG_KI, this));
                
            }
        });
        
        donDKChiTietPanel.repaint();
        donDKChiTietPanel.validate();
    }
    
    private void capNhatBangDangKi(){
        defaultTableModelDangKi.setRowCount(0);
        khoDangKi.clear();
        
        int demSoLuong = 0;
        
        
        // doi san pham thanh nguyen lieu
        Set<Integer> keySet = thanhPhanDangKi.keySet();
        for (Integer item : keySet){
            if (thanhPhanDangKi.get(item) > 0){
                demSoLuong ++;
                SanPham sp = dsSanPham.get(item);
                
                // tim nguyen lieu tu cong thuc
                HashMap<String, Integer> mMap = timNLTheoSP(sp);
                Set<String> mKeySet = mMap.keySet();
                mKeySet.forEach(key -> {
                    if (khoDangKi.get(key) == null){
                        khoDangKi.put(key, thanhPhanDangKi.get(item) * mMap.get(key));
                    }else {
                        khoDangKi.put(key, khoDangKi.get(key) 
                                + thanhPhanDangKi.get(item) * mMap.get(key));
                    }
                });
            }
        }
        
        Set<String> mKhoDangKi = khoDangKi.keySet();
        mKhoDangKi.forEach(key -> {
            String tenVaDonVi = data.layTenNguyenLieu(key);
            String tenNL = tenVaDonVi.substring(tenVaDonVi.indexOf(" ") + 1);
            String donVi = tenVaDonVi.substring(0,tenVaDonVi.indexOf(" "));
            defaultTableModelDangKi.addRow(new Object[]{key,tenNL,khoDangKi.get(key) + " " + donVi});
        });
        soLuongNguyenLieuTable.setModel(defaultTableModelDangKi);
        
        
        if (demSoLuong > 0){
            xetNutDangKiNL(true);
        }else xetNutDangKiNL(false);
    }
    
    private CongThuc timCT(String maSP){
        for (CongThuc cp : dsCongThuc){
            if (cp.getMaSP().equals(maSP))
                return cp;
        }
        
        return null;
    }
    
    private HashMap<String, Integer> timNLTheoSP(SanPham sp){
        HashMap<String, Integer> mHashMap = new HashMap<>();
        CongThuc ct = timCT(sp.getMaSP());
        Set<String> keySet = ct.getChiTietCT().keySet();
        keySet.forEach(item -> {
            mHashMap.put(item, ct.getChiTietCT().get(item));
        });
        return mHashMap;
    }
    
    private void huyDangKi(){
        khoDangKi.clear();
        thanhPhanDangKi.clear();
        defaultTableModelDangKi.setRowCount(0);
        donDKChiTietPanel.removeAll();
        donDKChiTietPanel.repaint();
        donDKChiTietPanel.validate();
        ghiChuTA.setText("");
        xetNutDangKiNL(false);
    }
    private void xetNutDangKiNL(boolean b){
        huyDKBtn.setEnabled(b);
        dangKyBtn.setEnabled(b);
    }
     
    private void dangKiNguyenLieu(){
        //kiem tra so luong nguyen lieu
        HashMap<String, Integer> soLuongNL = data.laySoLuongNL();
        
        boolean kiemTra = true;
        String tinBaoLoi = "Kh??ng ????? s??? l?????ng nguy??n li???u\n";
        Set<String> keySet = khoDangKi.keySet();
        for (String key : keySet){
            if(khoDangKi.get(key) > soLuongNL.get(key)){
                kiemTra = false;
                String tenVaDonVi = data.layTenNguyenLieu(key);
                String tenNL = tenVaDonVi.substring(tenVaDonVi.indexOf(" ") + 1);
                String donVi = tenVaDonVi.substring(0,tenVaDonVi.indexOf(" "));
                tinBaoLoi += "Nguy??n li???u " + tenNL 
                        + " c?? s??? l?????ng l?? " + soLuongNL.get(key) 
                        + " " + donVi + "\n";
            }
        }
        
        if (kiemTra){
            // dang ki nguyenlieu
            String maDK = Util.autoGenId(Util.DK_TABLE);
            String ghiChu = ghiChuTA.getText();
            
            //lay thoi gian thuc
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            String tg = dtf.format(now);
            String maCLV = "";

            // chon ca lam viec
            chonLichLamDialog.setVisible(true);
            if (dialogCVL != null){
                maCLV = dialogCVL.getMaCLV();
                dialogCVL = null;
            }
            
            if (!maCLV.equals("")){
                DangKi dk = new DangKi(maDK, maCLV, tg, ghiChu, khoDangKi);
                data.themDangKi(dk);
                
                NhapXuat nx = new NhapXuat();
                nx.setMaNhapXuat(Util.autoGenId(Util.XUAT_NL));
                nx.setTaiKhoan(ThongTinDangNhap.getTenDangNhap());
                nx.setThoiGian(new Timestamp(new java.util.Date().getTime()));
                nx.setTrangThai("1");
                nx.setGhiChu("????ng k?? nguy??n li???u");
                nx.setChiTietNhapXuat(khoDangKi);
                nx.setThanhTien(0);
                data.xuatNL(nx);

                
                // huy man hinh dang ki
                huyDangKi();

                JOptionPane.showMessageDialog(this, "????ng k?? th??nh c??ng", "Th??ng b??o", JOptionPane.INFORMATION_MESSAGE);
                
                // cap nhat khoHienTai
                taoDuLieuKhoHienTai();
                
                capNhatHaiBangQuanLy();
                capNhatThongKeTable();
            }
        }else {
            // bao loi
            JOptionPane.showMessageDialog(this, tinBaoLoi, "Th??ng b??o", JOptionPane.ERROR_MESSAGE);
        }
    }
    
///// quan ly nguyen lieu ban
    private void taoDuLieuQuanLyNLBan(){
        defaultTableModelSP = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        defaultTableModelNL = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        defaultTableModelNL.setColumnCount(0);
        defaultTableModelNL.addColumn("M?? nguy??n li???u");
        defaultTableModelNL.addColumn("T??n nguy??n li???u");
        defaultTableModelNL.addColumn("S??? l?????ng");
        
        defaultTableModelSP.setColumnCount(0);
        defaultTableModelSP.addColumn("M?? s???n ph???m");
        defaultTableModelSP.addColumn("T??n s???n ph???m");
        defaultTableModelSP.addColumn("S??? l?????ng c?? th??? b??n");
        
        bangNLConLaiTable.setModel(defaultTableModelNL);
        
        bangSPConLaiTable.setModel(defaultTableModelSP);
        
        capNhatHaiBangQuanLy();
    }
    
    private boolean isKhoHienTaiEmpty(){
        if (khoHienTai == null) return true;
        Set<String> keySet = khoHienTai.keySet();
        for (String keyString : keySet){
            if (khoHienTai.get(keyString) != null && khoHienTai.get(keyString) > 0){
                return false;
            }
        }
        return true;
        
    }
    
    private void capNhatHaiBangQuanLy(){
        defaultTableModelSP.setRowCount(0);
        defaultTableModelNL.setRowCount(0); 
        if (khoHienTai.isEmpty() ){
            duaNLVeKhoBtn.setEnabled(false);
        } if (isKhoHienTaiEmpty()) {
            duaNLVeKhoBtn.setEnabled(false);
        }else {
            duaNLVeKhoBtn.setEnabled(true);
        }
        
        Set<String> mKeySet = khoHienTai.keySet();
        
        for (String key : mKeySet){
            String raw =  data.layTenNguyenLieu(key);
            String tenNL = raw.substring(raw.indexOf(" ") + 1);
            String donVi = raw.substring(0,raw.indexOf(" "));
            if (khoHienTai.get(key) > 0)
                defaultTableModelNL.addRow(new Object[]{key, tenNL, khoHienTai.get(key) + " " + donVi});
        }
        
        for (SanPham sp : dsSanPham){
            int min = Integer.MAX_VALUE;
            boolean coNL = true;
            // tim nguyen lieu tu cong thuc
            HashMap<String, Integer> mMap = timNLTheoSP(sp);
            Set<String> keySet = mMap.keySet();
            for (String key : keySet){
                if (khoHienTai.get(key) == null){
                    coNL = false;
                }else {
                    int amount = khoHienTai.get(key) / mMap.get(key);
                    min = Math.min(min, amount);
                }
            }
            
            if (coNL && min > 0){
                defaultTableModelSP.addRow(new Object[]{sp.getMaSP(), sp.getTenSP(), min + " ly"});
            }
            
        }
    }
    
    // thong ke panel
    private void taoDuLieuThongKe(){
        defaultTableModelBH = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        defaultTableModelDK = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        defaultTableModelBH.setColumnCount(0);
        defaultTableModelBH.addColumn("Th???i gian");
        defaultTableModelBH.addColumn("S???n ph???m");
        defaultTableModelBH.addColumn("Th??nh ti???n");
        
        defaultTableModelDK.setColumnCount(0);
        defaultTableModelDK.addColumn("Th???i gian");
        defaultTableModelDK.addColumn("Nguy??n li???u");
        
        
        thongKeBHTable.setModel(defaultTableModelBH);
        
        thongKeDKTable.setModel(defaultTableModelDK);
        
        
        capNhatThongKeTable();
    }
    private void capNhatThongKeTable(){
        defaultTableModelBH.setRowCount(0);
        defaultTableModelDK.setRowCount(0);
        
        CaLamViec clv = layCaLamViecHienTai();
        if (clv != null){
            dsBanHangTrongCLV = data.layDSBanHangTheoCa(clv);
            dsDangKiTrongCLV = data.layDSDangKiTheoCa(clv);
            
            
            
            for (BanHang bh : dsBanHangTrongCLV){
                String[] p = bh.getTg().split(" ");
                String gio = p[1].substring(0,8);
                
                int dem = 0;
                
                HashMap<String, String> mHashMap = data.chiTietBanHang(bh.getMaBH());
                if (!mHashMap.isEmpty()){
                    Set<String> keySet = mHashMap.keySet();
                    for (String key : keySet){
                        if (dem == 0){
                            defaultTableModelBH.addRow(new Object[]{
                                gio, key + ": " + mHashMap.get(key), 
                                Util.formatCurrency(bh.getTongTien())
                            });
                            dem ++;
                            
                        }else {
                            defaultTableModelBH.addRow(new Object[]{
                                "", key + ": " + mHashMap.get(key), ""});
                        }
                    }
                }
                
                
            }
            
            
            
            for (DangKi dk : dsDangKiTrongCLV){
                String[] p = dk.getThoiGian().split(" ");
                String gio = p[1].substring(0,8);
                
                int dem = 0;
                
                if (!dk.getChiTietDangKi().isEmpty()){
                    Set<String> keySet = dk.getChiTietDangKi().keySet();

                    for (String key : keySet){
                        String tenVaDV = data.layTenNguyenLieu(key);

                        String dv = tenVaDV.substring(0,tenVaDV.indexOf(" "));
                        String tenNL = tenVaDV.substring(tenVaDV.indexOf(" ") + 1);

                        String nl = tenNL + ": " + dk.getChiTietDangKi().get(key) + " " + dv;
                        
                        if (dem == 0){
                            dem ++;
                             defaultTableModelDK.addRow(new Object[]{gio, nl});
                        }else {
                             defaultTableModelDK.addRow(new Object[]{"", nl});
                        }
                    }
                }
                
               
            }
        }
    }
    
    
    
    
/////////// Dialog
    private void taoDuLieuDialog(){
        dialogCVL = null;
        this.dsCaLamViecDialog = new ArrayList<>();
        chonCaLamViecBtn.setEnabled(false);
        
        defaultTableModelDialog = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        
        luaChonCLVTable.setModel(defaultTableModelDialog);
        
        defaultTableModelDialog.setColumnCount(0);
        defaultTableModelDialog.setRowCount(0);
        defaultTableModelDialog.addColumn("Ng??y l??m");
        defaultTableModelDialog.addColumn("?????a ??i???m");
        defaultTableModelDialog.addColumn("Ca l??m vi???c");
        
        
        String now = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDateTime.now());
        int time = Integer.valueOf(DateTimeFormatter.ofPattern("HH").format(LocalDateTime.now()));
        String caLV = "";
        if (time >= 12) caLV = "Chi???u";
        else caLV = "S??ng";
        for (CaLamViec clv : dsCaLamViec){
            if(clv.getNgay().compareTo(now) > 0 
                    || (clv.getNgay().compareTo(now) == 0 && clv.getCaLamViec().equals(caLV))
                    || (clv.getNgay().compareTo(now) == 0 && clv.getCaLamViec().equals("Chi???u"))){
                
                dsCaLamViecDialog.add(clv);
                
                String[] ngay = clv.getNgay().split("-");
                defaultTableModelDialog.addRow(
                        new Object[]{ngay[2] + "-" + ngay[1] + "-" + ngay[0], 
                            layDiaDiem(clv.getMaDD()), 
                            clv.getCaLamViec() });
            }
        }
        
        
        luaChonCLVTable.setModel(defaultTableModelDialog);

        if (layCaLamViecHienTai() != null){
            chonCaLamHienTaiPanel.setVisible(true);
        }else {
            chonCaLamHienTaiPanel.setVisible(false);
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

        chonLichLamDialog = new javax.swing.JDialog();
        jPanel4 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        luaChonCLVTable = new javax.swing.JTable();
        chonCaLamHienTaiPanel = new javax.swing.JPanel();
        chonCaLamViecHienTaiBtn = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        chonCaLamViecBtn = new javax.swing.JButton();
        mTabbedPane = new javax.swing.JTabbedPane();
        banHangPanel = new javax.swing.JPanel();
        coCaLamPanel = new javax.swing.JPanel();
        hoaDonPanel = new javax.swing.JPanel();
        donHangPanel = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jPanel20 = new javax.swing.JPanel();
        thanhToanPanel = new javax.swing.JPanel();
        thanhToanBtn = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        tongTienLb = new javax.swing.JLabel();
        tenNVLb = new javax.swing.JLabel();
        soLuongDonHangLb = new javax.swing.JLabel();
        huyDonHangBtn = new javax.swing.JButton();
        jPanel16 = new javax.swing.JPanel();
        jLabel26 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        jPanel25 = new javax.swing.JPanel();
        sanPhamBanHangPanel = new javax.swing.JPanel();
        khongCoCaLamPanel = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        congThucPanel = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        chiTietCongThucPanel = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        chiTietNguyenLieuTA = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        ChiTietPhaCheTA = new javax.swing.JTextArea();
        jPanel14 = new javax.swing.JPanel();
        tenCTLb = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        jPanel26 = new javax.swing.JPanel();
        sanPhamCongThucPanel = new javax.swing.JPanel();
        lichLamPanel = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        lichLamTable = new javax.swing.JTable();
        diaDiemCB = new javax.swing.JComboBox<>();
        jLabel17 = new javax.swing.JLabel();
        sapXepTheoThoiGianCB = new javax.swing.JComboBox<>();
        jLabel19 = new javax.swing.JLabel();
        chonNgayLamDC = new com.github.lgooddatepicker.components.DatePicker();
        jPanel17 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        chiTietCaLamViecPanel = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        nguoiLamPhuLb = new javax.swing.JLabel();
        nguoiLamChinhLb = new javax.swing.JLabel();
        ngayChiTietLb = new javax.swing.JLabel();
        caLamViecChiTietLb = new javax.swing.JLabel();
        diaDiemChiTietLb = new javax.swing.JLabel();
        jPanel19 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        DKSPPanel = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        chiTietDKPanel = new javax.swing.JPanel();
        thanhToanPanel1 = new javax.swing.JPanel();
        dangKyBtn = new javax.swing.JButton();
        jLabel18 = new javax.swing.JLabel();
        tenNVDKLb = new javax.swing.JLabel();
        huyDKBtn = new javax.swing.JButton();
        diaDiemDKLb = new javax.swing.JLabel();
        tenDiaDiemDKLB = new javax.swing.JLabel();
        donDKChiTietPanel = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        soLuongNguyenLieuTable = new javax.swing.JTable();
        jPanel18 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        ghiChuTA = new javax.swing.JTextArea();
        jPanel27 = new javax.swing.JPanel();
        dkSanPhamPanel = new javax.swing.JPanel();
        quanLyNguyenLieuBanPanel = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        bangNLConLaiTable = new javax.swing.JTable();
        jPanel8 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        jScrollPane8 = new javax.swing.JScrollPane();
        bangSPConLaiTable = new javax.swing.JTable();
        jPanel10 = new javax.swing.JPanel();
        duaNLVeKhoBtn = new javax.swing.JButton();
        thongkePanel = new javax.swing.JPanel();
        jPanel21 = new javax.swing.JPanel();
        jPanel22 = new javax.swing.JPanel();
        jLabel28 = new javax.swing.JLabel();
        jScrollPane9 = new javax.swing.JScrollPane();
        thongKeBHTable = new javax.swing.JTable();
        jPanel23 = new javax.swing.JPanel();
        jPanel24 = new javax.swing.JPanel();
        jLabel29 = new javax.swing.JLabel();
        jScrollPane10 = new javax.swing.JScrollPane();
        thongKeDKTable = new javax.swing.JTable();

        chonLichLamDialog.setTitle("Ch???n ca l??m vi???c");
        chonLichLamDialog.setBackground(new java.awt.Color(255, 255, 255));
        chonLichLamDialog.setLocation(new java.awt.Point(500, 200));
        chonLichLamDialog.setMinimumSize(new java.awt.Dimension(450, 450));
        chonLichLamDialog.setModal(true);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setPreferredSize(new java.awt.Dimension(450, 442));

        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("L???a ch???n ????ng k?? v??o ca l??m");
        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        luaChonCLVTable.setModel(new javax.swing.table.DefaultTableModel(
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
        luaChonCLVTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        luaChonCLVTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                luaChonCLVTableMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(luaChonCLVTable);

        chonCaLamHienTaiPanel.setBackground(new java.awt.Color(255, 255, 255));

        chonCaLamViecHienTaiBtn.setText("Ch???n ca l??m vi???c hi???n t???i");
        chonCaLamViecHienTaiBtn.setBackground(new java.awt.Color(32, 136, 203));
        chonCaLamViecHienTaiBtn.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        chonCaLamViecHienTaiBtn.setForeground(new java.awt.Color(255, 255, 255));
        chonCaLamViecHienTaiBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chonCaLamViecHienTaiBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout chonCaLamHienTaiPanelLayout = new javax.swing.GroupLayout(chonCaLamHienTaiPanel);
        chonCaLamHienTaiPanel.setLayout(chonCaLamHienTaiPanelLayout);
        chonCaLamHienTaiPanelLayout.setHorizontalGroup(
            chonCaLamHienTaiPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(chonCaLamHienTaiPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(chonCaLamViecHienTaiBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        chonCaLamHienTaiPanelLayout.setVerticalGroup(
            chonCaLamHienTaiPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, chonCaLamHienTaiPanelLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(chonCaLamViecHienTaiBtn))
        );

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        chonCaLamViecBtn.setText("Ch???n");
        chonCaLamViecBtn.setBackground(new java.awt.Color(32, 136, 203));
        chonCaLamViecBtn.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        chonCaLamViecBtn.setForeground(new java.awt.Color(255, 255, 255));
        chonCaLamViecBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chonCaLamViecBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(chonCaLamViecBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(chonCaLamViecBtn)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE)
            .addComponent(chonCaLamHienTaiPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 307, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chonCaLamHienTaiPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout chonLichLamDialogLayout = new javax.swing.GroupLayout(chonLichLamDialog.getContentPane());
        chonLichLamDialog.getContentPane().setLayout(chonLichLamDialogLayout);
        chonLichLamDialogLayout.setHorizontalGroup(
            chonLichLamDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        chonLichLamDialogLayout.setVerticalGroup(
            chonLichLamDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(1000, 500));

        mTabbedPane.setBackground(new java.awt.Color(32, 136, 203));
        mTabbedPane.setForeground(new java.awt.Color(255, 255, 255));
        mTabbedPane.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        mTabbedPane.setPreferredSize(new java.awt.Dimension(1000, 500));

        banHangPanel.setLayout(new java.awt.CardLayout());

        coCaLamPanel.setBackground(new java.awt.Color(255, 255, 255));

        hoaDonPanel.setBackground(new java.awt.Color(255, 255, 255));

        donHangPanel.setBackground(new java.awt.Color(255, 255, 255));
        donHangPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        donHangPanel.setLayout(new javax.swing.BoxLayout(donHangPanel, javax.swing.BoxLayout.PAGE_AXIS));

        jPanel12.setBackground(new java.awt.Color(114, 102, 186));

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("H??a ????n");
        jLabel4.setBackground(new java.awt.Color(114, 102, 186));
        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jPanel20.setBackground(new java.awt.Color(255, 255, 255));
        jPanel20.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        thanhToanPanel.setBackground(new java.awt.Color(255, 255, 255));

        thanhToanBtn.setText("Thanh to??n");
        thanhToanBtn.setBackground(new java.awt.Color(32, 136, 203));
        thanhToanBtn.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        thanhToanBtn.setForeground(new java.awt.Color(255, 255, 255));
        thanhToanBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                thanhToanBtnActionPerformed(evt);
            }
        });

        jLabel1.setText("S??? l?????ng: ");
        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        jLabel2.setText("T???ng: ");
        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        jLabel3.setText("Nh??n vi??n:");
        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        tongTienLb.setText("0");
        tongTienLb.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        tongTienLb.setForeground(new java.awt.Color(255, 0, 0));

        tenNVLb.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        tenNVLb.setForeground(new java.awt.Color(255, 0, 0));

        soLuongDonHangLb.setText("0");
        soLuongDonHangLb.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        soLuongDonHangLb.setForeground(new java.awt.Color(255, 0, 0));

        huyDonHangBtn.setText("H???y");
        huyDonHangBtn.setBackground(new java.awt.Color(32, 136, 203));
        huyDonHangBtn.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        huyDonHangBtn.setForeground(new java.awt.Color(255, 255, 255));
        huyDonHangBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                huyDonHangBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout thanhToanPanelLayout = new javax.swing.GroupLayout(thanhToanPanel);
        thanhToanPanel.setLayout(thanhToanPanelLayout);
        thanhToanPanelLayout.setHorizontalGroup(
            thanhToanPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(thanhToanPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(thanhToanPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(thanhToanPanelLayout.createSequentialGroup()
                        .addGroup(thanhToanPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addGap(18, 18, 18)
                        .addGroup(thanhToanPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(thanhToanPanelLayout.createSequentialGroup()
                                .addComponent(soLuongDonHangLb)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(thanhToanPanelLayout.createSequentialGroup()
                                .addComponent(tongTienLb, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(tenNVLb, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(20, 20, 20))))
                    .addGroup(thanhToanPanelLayout.createSequentialGroup()
                        .addComponent(huyDonHangBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(thanhToanBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        thanhToanPanelLayout.setVerticalGroup(
            thanhToanPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, thanhToanPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(thanhToanPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(soLuongDonHangLb))
                .addGap(18, 18, 18)
                .addGroup(thanhToanPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(tongTienLb)
                    .addComponent(jLabel3)
                    .addComponent(tenNVLb))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(thanhToanPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(thanhToanBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                    .addComponent(huyDonHangBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel16.setBackground(new java.awt.Color(114, 102, 186));

        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel26.setText("Th??ng tin h??a ????n");
        jLabel26.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel26, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(thanhToanPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(thanhToanPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout hoaDonPanelLayout = new javax.swing.GroupLayout(hoaDonPanel);
        hoaDonPanel.setLayout(hoaDonPanelLayout);
        hoaDonPanelLayout.setHorizontalGroup(
            hoaDonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(hoaDonPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(hoaDonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(donHangPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        hoaDonPanelLayout.setVerticalGroup(
            hoaDonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, hoaDonPanelLayout.createSequentialGroup()
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(donHangPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 206, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel13.setBackground(new java.awt.Color(114, 102, 186));

        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel24.setText("S???n ph???m");
        jLabel24.setBackground(new java.awt.Color(32, 136, 203));
        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel24, javax.swing.GroupLayout.DEFAULT_SIZE, 47, Short.MAX_VALUE)
        );

        jPanel25.setBackground(new java.awt.Color(255, 255, 255));
        jPanel25.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        sanPhamBanHangPanel.setBackground(new java.awt.Color(255, 255, 255));
        sanPhamBanHangPanel.setLayout(new java.awt.GridLayout(0, 3, 15, 15));

        javax.swing.GroupLayout jPanel25Layout = new javax.swing.GroupLayout(jPanel25);
        jPanel25.setLayout(jPanel25Layout);
        jPanel25Layout.setHorizontalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(sanPhamBanHangPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 401, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel25Layout.setVerticalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel25Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(sanPhamBanHangPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout coCaLamPanelLayout = new javax.swing.GroupLayout(coCaLamPanel);
        coCaLamPanel.setLayout(coCaLamPanelLayout);
        coCaLamPanelLayout.setHorizontalGroup(
            coCaLamPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(coCaLamPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(hoaDonPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(coCaLamPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        coCaLamPanelLayout.setVerticalGroup(
            coCaLamPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(coCaLamPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(coCaLamPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(hoaDonPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(coCaLamPanelLayout.createSequentialGroup()
                        .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );

        banHangPanel.add(coCaLamPanel, "card2");

        khongCoCaLamPanel.setBackground(new java.awt.Color(255, 255, 255));

        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("B???n kh??ng c?? ca l??m vi???c b??y gi???");
        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N

        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("????ng k?? ca l??m vi???c ????? s??? d???ng ch???c n??ng n??y");
        jLabel21.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N

        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel27.setIcon(new javax.swing.ImageIcon(getClass().getResource("/HinhAnh/empty_state_ani.gif"))); // NOI18N

        javax.swing.GroupLayout khongCoCaLamPanelLayout = new javax.swing.GroupLayout(khongCoCaLamPanel);
        khongCoCaLamPanel.setLayout(khongCoCaLamPanelLayout);
        khongCoCaLamPanelLayout.setHorizontalGroup(
            khongCoCaLamPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(khongCoCaLamPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(khongCoCaLamPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, 975, Short.MAX_VALUE))
                .addContainerGap())
        );
        khongCoCaLamPanelLayout.setVerticalGroup(
            khongCoCaLamPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(khongCoCaLamPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 308, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel21)
                .addContainerGap(72, Short.MAX_VALUE))
        );

        banHangPanel.add(khongCoCaLamPanel, "card3");

        mTabbedPane.addTab("B??n h??ng", banHangPanel);

        congThucPanel.setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        chiTietCongThucPanel.setBackground(new java.awt.Color(255, 255, 255));
        chiTietCongThucPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel6.setText("Nguy??n li???u");
        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jLabel8.setText("C??ch pha ch???");
        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        chiTietNguyenLieuTA.setColumns(20);
        chiTietNguyenLieuTA.setEditable(false);
        chiTietNguyenLieuTA.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        chiTietNguyenLieuTA.setLineWrap(true);
        chiTietNguyenLieuTA.setRows(5);
        jScrollPane1.setViewportView(chiTietNguyenLieuTA);

        ChiTietPhaCheTA.setEditable(false);
        ChiTietPhaCheTA.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        ChiTietPhaCheTA.setLineWrap(true);
        jScrollPane2.setViewportView(ChiTietPhaCheTA);

        javax.swing.GroupLayout chiTietCongThucPanelLayout = new javax.swing.GroupLayout(chiTietCongThucPanel);
        chiTietCongThucPanel.setLayout(chiTietCongThucPanelLayout);
        chiTietCongThucPanelLayout.setHorizontalGroup(
            chiTietCongThucPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(chiTietCongThucPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(chiTietCongThucPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addComponent(jScrollPane2)
                    .addGroup(chiTietCongThucPanelLayout.createSequentialGroup()
                        .addGroup(chiTietCongThucPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel8))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        chiTietCongThucPanelLayout.setVerticalGroup(
            chiTietCongThucPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(chiTietCongThucPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel14.setBackground(new java.awt.Color(114, 102, 186));

        tenCTLb.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        tenCTLb.setText("Chi ti???t c??ng th???c");
        tenCTLb.setBackground(new java.awt.Color(114, 102, 186));
        tenCTLb.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        tenCTLb.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tenCTLb, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tenCTLb, javax.swing.GroupLayout.DEFAULT_SIZE, 47, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(chiTietCongThucPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(chiTietCongThucPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel15.setBackground(new java.awt.Color(114, 102, 186));

        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel25.setText("S???n ph???m");
        jLabel25.setBackground(new java.awt.Color(114, 102, 186));
        jLabel25.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel25, javax.swing.GroupLayout.DEFAULT_SIZE, 299, Short.MAX_VALUE)
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel25, javax.swing.GroupLayout.DEFAULT_SIZE, 47, Short.MAX_VALUE)
        );

        jPanel26.setBackground(new java.awt.Color(255, 255, 255));
        jPanel26.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        sanPhamCongThucPanel.setBackground(new java.awt.Color(255, 255, 255));
        sanPhamCongThucPanel.setLayout(new java.awt.GridLayout(0, 3, 15, 15));

        javax.swing.GroupLayout jPanel26Layout = new javax.swing.GroupLayout(jPanel26);
        jPanel26.setLayout(jPanel26Layout);
        jPanel26Layout.setHorizontalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(sanPhamCongThucPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel26Layout.setVerticalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(sanPhamCongThucPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 359, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout congThucPanelLayout = new javax.swing.GroupLayout(congThucPanel);
        congThucPanel.setLayout(congThucPanelLayout);
        congThucPanelLayout.setHorizontalGroup(
            congThucPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, congThucPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(congThucPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        congThucPanelLayout.setVerticalGroup(
            congThucPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(congThucPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(congThucPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(congThucPanelLayout.createSequentialGroup()
                        .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );

        mTabbedPane.addTab("C??ng th???c", congThucPanel);

        lichLamPanel.setBackground(new java.awt.Color(255, 255, 255));
        lichLamPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        lichLamTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Ng??y l??m", "?????a ??i???m", "Ca L??m vi???c"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        lichLamTable.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lichLamTable.setSelectionBackground(new java.awt.Color(153, 153, 255));
        lichLamTable.setSelectionForeground(new java.awt.Color(254, 254, 254));
        lichLamTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        lichLamTable.setToolTipText("Nh???n 2 l???n ????? xem");
        lichLamTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lichLamTableMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(lichLamTable);

        diaDiemCB.setBackground(new java.awt.Color(254, 254, 254));
        diaDiemCB.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        diaDiemCB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                diaDiemCBActionPerformed(evt);
            }
        });

        jLabel17.setText("?????a ??i???m");
        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        sapXepTheoThoiGianCB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "T??ng d???n theo ng??y", "Gi???m d???n theo ng??y" }));
        sapXepTheoThoiGianCB.setBackground(new java.awt.Color(254, 254, 254));
        sapXepTheoThoiGianCB.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        sapXepTheoThoiGianCB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sapXepTheoThoiGianCBActionPerformed(evt);
            }
        });

        jLabel19.setText("Ng??y l??m");
        jLabel19.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        chonNgayLamDC.setBackground(new java.awt.Color(254, 254, 254));
        chonNgayLamDC.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        chonNgayLamDC.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                chonNgayLamDCPropertyChange(evt);
            }
        });

        jPanel17.setBackground(new java.awt.Color(114, 102, 186));

        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("L???ch l??m");
        jLabel13.setBackground(new java.awt.Color(114, 102, 186));
        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 563, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel19, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 152, Short.MAX_VALUE)
                    .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(sapXepTheoThoiGianCB, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(diaDiemCB, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(chonNgayLamDC, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(diaDiemCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(chonNgayLamDC, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(2, 2, 2)))
                .addComponent(sapXepTheoThoiGianCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 287, Short.MAX_VALUE))
        );

        chiTietCaLamViecPanel.setBackground(new java.awt.Color(255, 255, 255));
        chiTietCaLamViecPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel7.setText("?????a ??i???m:");
        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel9.setText("Ca l??m vi???c:");
        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel10.setText("Ng??y:");
        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel11.setText("Ng?????i l??m ch??nh:");
        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel12.setText("Ng?????i l??m ph???:");
        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        nguoiLamPhuLb.setText("Ng?????i l??m ph???:");
        nguoiLamPhuLb.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        nguoiLamChinhLb.setText("Ng?????i l??m ph???:");
        nguoiLamChinhLb.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        ngayChiTietLb.setText("Ng?????i l??m ph???:");
        ngayChiTietLb.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        caLamViecChiTietLb.setText("Ng?????i l??m ph???:");
        caLamViecChiTietLb.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        diaDiemChiTietLb.setText("Ng?????i l??m ph???:");
        diaDiemChiTietLb.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jPanel19.setBackground(new java.awt.Color(114, 102, 186));

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Chi ti???t ca l??m vi???c");
        jLabel5.setBackground(new java.awt.Color(114, 102, 186));
        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout chiTietCaLamViecPanelLayout = new javax.swing.GroupLayout(chiTietCaLamViecPanel);
        chiTietCaLamViecPanel.setLayout(chiTietCaLamViecPanelLayout);
        chiTietCaLamViecPanelLayout.setHorizontalGroup(
            chiTietCaLamViecPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, chiTietCaLamViecPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(chiTietCaLamViecPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(chiTietCaLamViecPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(nguoiLamChinhLb, javax.swing.GroupLayout.DEFAULT_SIZE, 264, Short.MAX_VALUE)
                    .addComponent(ngayChiTietLb, javax.swing.GroupLayout.DEFAULT_SIZE, 264, Short.MAX_VALUE)
                    .addComponent(nguoiLamPhuLb, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(caLamViecChiTietLb, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 264, Short.MAX_VALUE)
                    .addComponent(diaDiemChiTietLb, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 264, Short.MAX_VALUE))
                .addContainerGap())
            .addComponent(jPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        chiTietCaLamViecPanelLayout.setVerticalGroup(
            chiTietCaLamViecPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(chiTietCaLamViecPanelLayout.createSequentialGroup()
                .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50)
                .addGroup(chiTietCaLamViecPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(diaDiemChiTietLb))
                .addGap(18, 18, 18)
                .addGroup(chiTietCaLamViecPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(caLamViecChiTietLb))
                .addGap(18, 18, 18)
                .addGroup(chiTietCaLamViecPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(ngayChiTietLb))
                .addGap(18, 18, 18)
                .addGroup(chiTietCaLamViecPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(nguoiLamChinhLb))
                .addGap(18, 18, 18)
                .addGroup(chiTietCaLamViecPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(nguoiLamPhuLb))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout lichLamPanelLayout = new javax.swing.GroupLayout(lichLamPanel);
        lichLamPanel.setLayout(lichLamPanelLayout);
        lichLamPanelLayout.setHorizontalGroup(
            lichLamPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(lichLamPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(chiTietCaLamViecPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        lichLamPanelLayout.setVerticalGroup(
            lichLamPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(lichLamPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(lichLamPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(chiTietCaLamViecPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        mTabbedPane.addTab("L???ch l??m", lichLamPanel);

        DKSPPanel.setBackground(new java.awt.Color(255, 255, 255));

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        chiTietDKPanel.setBackground(new java.awt.Color(255, 255, 255));

        thanhToanPanel1.setBackground(new java.awt.Color(255, 255, 255));

        dangKyBtn.setText("????ng k??");
        dangKyBtn.setBackground(new java.awt.Color(32, 136, 203));
        dangKyBtn.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        dangKyBtn.setForeground(new java.awt.Color(255, 255, 255));
        dangKyBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dangKyBtnActionPerformed(evt);
            }
        });

        jLabel18.setText("Nh??n vi??n:");
        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        tenNVDKLb.setText("Nguy???n Trung Hi???u");
        tenNVDKLb.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        tenNVDKLb.setForeground(new java.awt.Color(255, 0, 0));

        huyDKBtn.setText("H???y");
        huyDKBtn.setBackground(new java.awt.Color(32, 136, 203));
        huyDKBtn.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        huyDKBtn.setForeground(new java.awt.Color(255, 255, 255));
        huyDKBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                huyDKBtnActionPerformed(evt);
            }
        });

        diaDiemDKLb.setText("?????a ??i???m:");
        diaDiemDKLb.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        tenDiaDiemDKLB.setText("Nguy???n Trung Hi???u");
        tenDiaDiemDKLB.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        tenDiaDiemDKLB.setForeground(new java.awt.Color(255, 0, 0));

        javax.swing.GroupLayout thanhToanPanel1Layout = new javax.swing.GroupLayout(thanhToanPanel1);
        thanhToanPanel1.setLayout(thanhToanPanel1Layout);
        thanhToanPanel1Layout.setHorizontalGroup(
            thanhToanPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(thanhToanPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(thanhToanPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(thanhToanPanel1Layout.createSequentialGroup()
                        .addComponent(huyDKBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 157, Short.MAX_VALUE)
                        .addComponent(dangKyBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(thanhToanPanel1Layout.createSequentialGroup()
                        .addGroup(thanhToanPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(diaDiemDKLb, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(thanhToanPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tenDiaDiemDKLB, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(tenNVDKLb, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        thanhToanPanel1Layout.setVerticalGroup(
            thanhToanPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(thanhToanPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(thanhToanPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel18)
                    .addComponent(tenNVDKLb))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(thanhToanPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(diaDiemDKLb)
                    .addComponent(tenDiaDiemDKLB))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(thanhToanPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(huyDKBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dangKyBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        donDKChiTietPanel.setBackground(new java.awt.Color(255, 255, 255));
        donDKChiTietPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        donDKChiTietPanel.setLayout(new javax.swing.BoxLayout(donDKChiTietPanel, javax.swing.BoxLayout.PAGE_AXIS));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        soLuongNguyenLieuTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "M?? nguy??n li???u", "T??n nguy??n li???u", "S??? l?????ng"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        soLuongNguyenLieuTable.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        soLuongNguyenLieuTable.setSelectionBackground(new java.awt.Color(153, 153, 255));
        soLuongNguyenLieuTable.setSelectionForeground(new java.awt.Color(254, 254, 254));
        soLuongNguyenLieuTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane4.setViewportView(soLuongNguyenLieuTable);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE)
        );

        jPanel18.setBackground(new java.awt.Color(114, 102, 186));

        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel20.setText("S???n ph???m ????ng k??");
        jLabel20.setBackground(new java.awt.Color(114, 102, 186));
        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout chiTietDKPanelLayout = new javax.swing.GroupLayout(chiTietDKPanel);
        chiTietDKPanel.setLayout(chiTietDKPanelLayout);
        chiTietDKPanelLayout.setHorizontalGroup(
            chiTietDKPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(chiTietDKPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(chiTietDKPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(thanhToanPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(donDKChiTietPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        chiTietDKPanelLayout.setVerticalGroup(
            chiTietDKPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, chiTietDKPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(donDKChiTietPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 53, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(thanhToanPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jLabel15.setText("Ghi Ch??");
        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jScrollPane5.setBackground(new java.awt.Color(255, 255, 255));

        ghiChuTA.setColumns(20);
        ghiChuTA.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        ghiChuTA.setRows(5);
        jScrollPane5.setViewportView(ghiChuTA);

        jPanel27.setBackground(new java.awt.Color(255, 255, 255));
        jPanel27.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        dkSanPhamPanel.setBackground(new java.awt.Color(255, 255, 255));
        dkSanPhamPanel.setLayout(new java.awt.GridLayout(0, 3, 15, 15));

        javax.swing.GroupLayout jPanel27Layout = new javax.swing.GroupLayout(jPanel27);
        jPanel27.setLayout(jPanel27Layout);
        jPanel27Layout.setHorizontalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(dkSanPhamPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel27Layout.setVerticalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(dkSanPhamPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(chiTietDKPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                    .addComponent(jPanel27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(chiTietDKPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jPanel27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        javax.swing.GroupLayout DKSPPanelLayout = new javax.swing.GroupLayout(DKSPPanel);
        DKSPPanel.setLayout(DKSPPanelLayout);
        DKSPPanelLayout.setHorizontalGroup(
            DKSPPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        DKSPPanelLayout.setVerticalGroup(
            DKSPPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        mTabbedPane.addTab("????ng k?? s???n ph???m", DKSPPanel);

        quanLyNguyenLieuBanPanel.setBackground(new java.awt.Color(255, 255, 255));
        quanLyNguyenLieuBanPanel.setLayout(new javax.swing.BoxLayout(quanLyNguyenLieuBanPanel, javax.swing.BoxLayout.LINE_AXIS));

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));

        jPanel9.setBackground(new java.awt.Color(114, 102, 186));

        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel22.setText("Nguy??n li???u c??n l???i");
        jLabel22.setBackground(new java.awt.Color(114, 102, 186));
        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        bangNLConLaiTable.setModel(new javax.swing.table.DefaultTableModel(
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
        bangNLConLaiTable.setSelectionBackground(new java.awt.Color(153, 153, 255));
        bangNLConLaiTable.setSelectionForeground(new java.awt.Color(254, 254, 254));
        bangNLConLaiTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane7.setViewportView(bangNLConLaiTable);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 480, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 345, Short.MAX_VALUE)
                .addGap(67, 67, 67))
        );

        quanLyNguyenLieuBanPanel.add(jPanel7);

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));

        jPanel11.setBackground(new java.awt.Color(114, 102, 186));

        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel23.setText("S???n ph???m c?? th??? b??n");
        jLabel23.setBackground(new java.awt.Color(114, 102, 186));
        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel23, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
        );

        bangSPConLaiTable.setModel(new javax.swing.table.DefaultTableModel(
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
        bangSPConLaiTable.setSelectionBackground(new java.awt.Color(153, 153, 255));
        bangSPConLaiTable.setSelectionForeground(new java.awt.Color(254, 254, 254));
        bangSPConLaiTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane8.setViewportView(bangSPConLaiTable);

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));

        duaNLVeKhoBtn.setText("????a nguy??n li???u v??? kho");
        duaNLVeKhoBtn.setBackground(new java.awt.Color(32, 136, 203));
        duaNLVeKhoBtn.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        duaNLVeKhoBtn.setForeground(new java.awt.Color(255, 255, 255));
        duaNLVeKhoBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                duaNLVeKhoBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(duaNLVeKhoBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(duaNLVeKhoBtn)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 480, Short.MAX_VALUE)
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 345, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
        );

        quanLyNguyenLieuBanPanel.add(jPanel8);

        mTabbedPane.addTab("Qu???n l?? nguy??n li???u b??n ", quanLyNguyenLieuBanPanel);

        thongkePanel.setBackground(new java.awt.Color(255, 255, 255));
        thongkePanel.setLayout(new javax.swing.BoxLayout(thongkePanel, javax.swing.BoxLayout.LINE_AXIS));

        jPanel21.setBackground(new java.awt.Color(255, 255, 255));

        jPanel22.setBackground(new java.awt.Color(114, 102, 186));

        jLabel28.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel28.setText("Th???ng k?? b??n h??ng");
        jLabel28.setBackground(new java.awt.Color(114, 102, 186));
        jLabel28.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        thongKeBHTable.setModel(new javax.swing.table.DefaultTableModel(
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
        thongKeBHTable.setSelectionBackground(new java.awt.Color(153, 153, 255));
        thongKeBHTable.setSelectionForeground(new java.awt.Color(254, 254, 254));
        thongKeBHTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane9.setViewportView(thongKeBHTable);

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 477, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 342, Short.MAX_VALUE)
                .addGap(50, 50, 50))
        );

        thongkePanel.add(jPanel21);

        jPanel23.setBackground(new java.awt.Color(255, 255, 255));

        jPanel24.setBackground(new java.awt.Color(114, 102, 186));

        jLabel29.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel29.setText("Th???ng k?? ????ng k??");
        jLabel29.setBackground(new java.awt.Color(114, 102, 186));
        jLabel29.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel29, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel29, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
        );

        thongKeDKTable.setModel(new javax.swing.table.DefaultTableModel(
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
        thongKeDKTable.setSelectionBackground(new java.awt.Color(153, 153, 255));
        thongKeDKTable.setSelectionForeground(new java.awt.Color(254, 254, 254));
        thongKeDKTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane10.setViewportView(thongKeDKTable);

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane10, javax.swing.GroupLayout.DEFAULT_SIZE, 477, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jPanel24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane10, javax.swing.GroupLayout.DEFAULT_SIZE, 342, Short.MAX_VALUE)
                .addGap(50, 50, 50))
        );

        thongkePanel.add(jPanel23);

        mTabbedPane.addTab("Th???ng k??", thongkePanel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mTabbedPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(mTabbedPane, javax.swing.GroupLayout.DEFAULT_SIZE, 489, Short.MAX_VALUE))
        );

        mTabbedPane.getAccessibleContext().setAccessibleName("");
    }// </editor-fold>//GEN-END:initComponents

    private void huyDonHangBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_huyDonHangBtnActionPerformed
        // TODO add your handling code here:
        xoaHoaDon();
        
    }//GEN-LAST:event_huyDonHangBtnActionPerformed

    private void thanhToanBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_thanhToanBtnActionPerformed
        // TODO add your handling code here:
        thanhToanHoaDon();
    }//GEN-LAST:event_thanhToanBtnActionPerformed

    private void sapXepTheoThoiGianCBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sapXepTheoThoiGianCBActionPerformed
        // TODO add your handling code here:
        hienBangSapXep(dsCaLamViecHienTai);
    }//GEN-LAST:event_sapXepTheoThoiGianCBActionPerformed

    private void diaDiemCBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_diaDiemCBActionPerformed
        // TODO add your handling code here:
        timKiemCaLamViec();
    }//GEN-LAST:event_diaDiemCBActionPerformed

    private void lichLamTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lichLamTableMouseClicked
        chiTietCaLam();
    }//GEN-LAST:event_lichLamTableMouseClicked

    private void dangKyBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dangKyBtnActionPerformed
        // TODO add your handling code here:
        dangKiNguyenLieu();
        
    }//GEN-LAST:event_dangKyBtnActionPerformed

    private void huyDKBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_huyDKBtnActionPerformed
        // TODO add your handling code here:
        huyDangKi();
    }//GEN-LAST:event_huyDKBtnActionPerformed

    private void chonNgayLamDCPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_chonNgayLamDCPropertyChange
        // TODO add your handling code here:
        timKiemCaLamViec();
    }//GEN-LAST:event_chonNgayLamDCPropertyChange

    private void luaChonCLVTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_luaChonCLVTableMouseClicked
        // TODO add your handling code here:
        chonCaLamViecBtn.setEnabled(true);
    }//GEN-LAST:event_luaChonCLVTableMouseClicked

    private void chonCaLamViecBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chonCaLamViecBtnActionPerformed
        // TODO add your handling code here:
        int index = luaChonCLVTable.getSelectedRow();
        if (index >= 0){
            dialogCVL = dsCaLamViecDialog.get(index);
        }
        chonCaLamViecBtn.setEnabled(false);
        chonLichLamDialog.setVisible(false);
        
    }//GEN-LAST:event_chonCaLamViecBtnActionPerformed

    private void chonCaLamViecHienTaiBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chonCaLamViecHienTaiBtnActionPerformed
        // TODO add your handling code here:
        dialogCVL = layCaLamViecHienTai();
        chonCaLamViecBtn.setEnabled(false);
        chonLichLamDialog.setVisible(false);
    }//GEN-LAST:event_chonCaLamViecHienTaiBtnActionPerformed

    private void duaNLVeKhoBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_duaNLVeKhoBtnActionPerformed
        // TODO add your handling code here:
        //xoa het nguyen lieu khoHienTaiHasMap
        // dua nl ve kho
        
        NhapXuat nx = new NhapXuat();
        nx.setMaNhapXuat(Util.autoGenId(Util.TRA_NL));
        nx.setTaiKhoan(ThongTinDangNhap.getTenDangNhap());
        nx.setThoiGian(new Timestamp(new java.util.Date().getTime()));
        nx.setTrangThai("0");
        nx.setGhiChu("Tr??? nguy??n li???u th???a v??? kho");
        nx.setChiTietNhapXuat(khoHienTai);
        nx.setThanhTien(0);
        
        data.traNL(nx);
        
        khoHienTai.clear();
        
        JOptionPane.showMessageDialog(this, "Tr??? nguy??n li???u v??? kho th??nh c??ng", "Th??ng b??o", JOptionPane.INFORMATION_MESSAGE);
        
        capNhatHaiBangQuanLy();
    }//GEN-LAST:event_duaNLVeKhoBtnActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea ChiTietPhaCheTA;
    private javax.swing.JPanel DKSPPanel;
    private javax.swing.JPanel banHangPanel;
    private javax.swing.JTable bangNLConLaiTable;
    private javax.swing.JTable bangSPConLaiTable;
    private javax.swing.JLabel caLamViecChiTietLb;
    private javax.swing.JPanel chiTietCaLamViecPanel;
    private javax.swing.JPanel chiTietCongThucPanel;
    private javax.swing.JPanel chiTietDKPanel;
    private javax.swing.JTextArea chiTietNguyenLieuTA;
    private javax.swing.JPanel chonCaLamHienTaiPanel;
    private javax.swing.JButton chonCaLamViecBtn;
    private javax.swing.JButton chonCaLamViecHienTaiBtn;
    private javax.swing.JDialog chonLichLamDialog;
    private com.github.lgooddatepicker.components.DatePicker chonNgayLamDC;
    private javax.swing.JPanel coCaLamPanel;
    private javax.swing.JPanel congThucPanel;
    private javax.swing.JButton dangKyBtn;
    private javax.swing.JComboBox<String> diaDiemCB;
    private javax.swing.JLabel diaDiemChiTietLb;
    private javax.swing.JLabel diaDiemDKLb;
    private javax.swing.JPanel dkSanPhamPanel;
    private javax.swing.JPanel donDKChiTietPanel;
    private javax.swing.JPanel donHangPanel;
    private javax.swing.JButton duaNLVeKhoBtn;
    private javax.swing.JTextArea ghiChuTA;
    private javax.swing.JPanel hoaDonPanel;
    private javax.swing.JButton huyDKBtn;
    private javax.swing.JButton huyDonHangBtn;
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
    private javax.swing.JLabel jLabel4;
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
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JPanel khongCoCaLamPanel;
    private javax.swing.JPanel lichLamPanel;
    private javax.swing.JTable lichLamTable;
    private javax.swing.JTable luaChonCLVTable;
    private javax.swing.JTabbedPane mTabbedPane;
    private javax.swing.JLabel ngayChiTietLb;
    private javax.swing.JLabel nguoiLamChinhLb;
    private javax.swing.JLabel nguoiLamPhuLb;
    private javax.swing.JPanel quanLyNguyenLieuBanPanel;
    private javax.swing.JPanel sanPhamBanHangPanel;
    private javax.swing.JPanel sanPhamCongThucPanel;
    private javax.swing.JComboBox<String> sapXepTheoThoiGianCB;
    private javax.swing.JLabel soLuongDonHangLb;
    private javax.swing.JTable soLuongNguyenLieuTable;
    private javax.swing.JLabel tenCTLb;
    private javax.swing.JLabel tenDiaDiemDKLB;
    private javax.swing.JLabel tenNVDKLb;
    private javax.swing.JLabel tenNVLb;
    private javax.swing.JButton thanhToanBtn;
    private javax.swing.JPanel thanhToanPanel;
    private javax.swing.JPanel thanhToanPanel1;
    private javax.swing.JTable thongKeBHTable;
    private javax.swing.JTable thongKeDKTable;
    private javax.swing.JPanel thongkePanel;
    private javax.swing.JLabel tongTienLb;
    // End of variables declaration//GEN-END:variables

    @Override
    public void onClickItem(SanPham sanPham, int type) {
        switch(type){
            case SanPhamPanel.BAN_HANG -> {
                int index = dsSanPham.indexOf(sanPham);
                if (index > -1){
                    if (thanhPhanHoaDon.get(index) != null){
                        thanhPhanHoaDon.put(index, thanhPhanHoaDon.get(index) + 1);
                    }else {
                        thanhPhanHoaDon.put(index, 1);

                    }

                    capNhatHoaDon();
                }
            }
            case SanPhamPanel.CONG_THUC -> chiTietCongThuc(sanPham);
            case SanPhamPanel.DANG_KY -> dangKyNL(sanPham);
            
        }
                
            
    }

    @Override
    public void onStateChanged(SanPham sp, int sl, int type) {
        switch(type){
            case ThanhPhanHoaDonPanel.BAN_HANG -> {
                int index = dsSanPham.indexOf(sp);
                thanhPhanHoaDon.put(index, sl);
                capNhatTongTien();
            }
            
            case ThanhPhanHoaDonPanel.DANG_KI -> {
                int index = dsSanPham.indexOf(sp);
                thanhPhanDangKi.put(index, sl);
                capNhatBangDangKi();
            }
            default -> {
                
            }
        }
        
    }


    @Override
    public void onCancelButtonClicked(SanPham sp, int type) {
        switch(type){
            case ThanhPhanHoaDonPanel.BAN_HANG -> {
                int index = dsSanPham.indexOf(sp);
                thanhPhanHoaDon.put(index, 0);
                capNhatHoaDon();
            }
            
            case ThanhPhanHoaDonPanel.DANG_KI -> {
                int index = dsSanPham.indexOf(sp);
                thanhPhanDangKi.put(index, 0);
                capNhapHoaDonDangKy();
                capNhatBangDangKi();
            }
            default -> {
                
            }
        }
        
    }
}
