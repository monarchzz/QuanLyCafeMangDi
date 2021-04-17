/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quanlycafemangdi.view;

import com.github.lgooddatepicker.components.DatePickerSettings;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import quanlycafemangdi.Util;
import quanlycafemangdi.data.Data;
import quanlycafemangdi.model.BanHang;
import quanlycafemangdi.model.CaLamViec;
import quanlycafemangdi.model.CongThuc;
import quanlycafemangdi.model.DiaDiem;
import quanlycafemangdi.model.SanPham;
import quanlycafemangdi.model.ThongTinDangNhap;

/**
 *
 * @author monar
 */
public class BanHangPanel extends javax.swing.JPanel implements SanPhamPanel.IOnClickSPPanel, 
        ThanhPhanHoaDonPanel.IOnSpinnerStateChange{
    

    private final Data data;
    private final JLayeredPane jLayeredPane;
    private final JPanel homePanel;
    
    private List<SanPham> dsSanPham;
    private List<CongThuc> dsCongThuc;
    private final HashMap<Integer,Integer> thanhPhanHoaDon;
    private List<DiaDiem> dsDiaDiem;
    private List<CaLamViec> dsCaLamViec;
    private List<CaLamViec> dsCaLamViecHienTai;
    
    private DefaultTableModel defaultTableModel;
    private int soLuongSanPham;
    private long tongTien;
    /**
     * Creates new form BanHangPanel
     * @param jLayeredPane
     * @param homePanel
     */
    public BanHangPanel(JLayeredPane jLayeredPane, JPanel homePanel) {
        initComponents();
        
        this.jLayeredPane = jLayeredPane;
        this.homePanel = homePanel;
        data = Data.getInstance();
        thanhPhanHoaDon = new HashMap<>();
        
        initData();
    }
    
    private void initData(){
        tenNVLb.setText(ThongTinDangNhap.getTenNguoiDung());
        taoDSSP();
        taoThanhPhanHoaDon();
        taoDSCT();
        taoDuLieuLichLam();
        taoDuLieuDangKi();
    }
    
// Ban Hang Panel    
    private void taoThanhPhanHoaDon(){
        soLuongSanPham = 0;
        tongTien = 0;
        
        setVisibleButton(false);
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
                        thanhPhanHoaDon.get(item), this));
                
            }
        });
        
        
        donHangPanel.repaint();
        donHangPanel.validate();
        capNhatTongTien();
        
    }
    
    private void capNhatTongTien(){
        soLuongSanPham = 0;
        tongTien = 0;
        
        Set<Integer> keySet  = thanhPhanHoaDon.keySet();
        keySet.forEach(item -> {
            int sl = thanhPhanHoaDon.get(item);
            if(sl > 0){
                soLuongSanPham += sl;
                tongTien += sl * dsSanPham.get(item).getGia();
            }
        });
        if (soLuongSanPham == 0){
            soLuongDonHangLb.setText("0");
            tongTienLb.setText("0");
            
            setVisibleButton(false);
        }
        else {
            soLuongDonHangLb.setText(String.valueOf(soLuongSanPham));
            tongTienLb.setText(Util.formatCurrency(tongTien));
            
            setVisibleButton(true);
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
        String maBh = Util.autoGenId(Util.BH_TABLE);
        String tenTK = ThongTinDangNhap.getTenDangNhap();
        
        // lay thoi gian thuc
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String tg = dtf.format(now);
        
        long tt = tongTien;
        
        // lay thong tin va so luong san pham
        HashMap<String,Integer> mMap = new HashMap<>();
        Set<Integer> keySet  = thanhPhanHoaDon.keySet();
        for (int item: keySet){
            int sl = thanhPhanHoaDon.get(item);
            if (sl > 0){
                mMap.put(dsSanPham.get(item).getMaSP(), sl);
            }
        }
            
        BanHang banHang = new BanHang(maBh,tenTK,tg,tongTien,mMap);
        
        data.taoHoaDonBanHang(banHang);
        JOptionPane.showMessageDialog(this, "Thanh toán thành công","Thông báo",JOptionPane.INFORMATION_MESSAGE);
        xoaHoaDon();
        
    }
    
    private void setVisibleButton(boolean b){
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
        tenCTLb.setText("Công thức pha chế " + sanPham.getTenSP());
        String nguyenLieu = "";
        
        Set<String> keySet = congThuc.getChiTietCT().keySet();
        for (String key : keySet){
            nguyenLieu += "●  " + congThuc.getChiTietCT().get(key) + " " + data.layTenNgyenLieu(key) + "\n";
        }
        
        chiTietNguyenLieuTA.setText(nguyenLieu);
        ChiTietPhaCheTA.setText(congThuc.getCachLam());
    }
    
    
// lich lam Panel
    private void taoDuLieuLichLam(){
        defaultTableModel = new DefaultTableModel(){
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
        diaDiemCB.addItem("Tất cả");
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
        defaultTableModel.setRowCount(0);
        defaultTableModel.setColumnCount(0);
        defaultTableModel.addColumn("Ngày làm");
        defaultTableModel.addColumn("Địa điểm");
        defaultTableModel.addColumn("Ca Làm Việc");
        
        for (CaLamViec clv: list){
            Vector vt = new Vector();
            String ngay[] = clv.getNgay().split("-");
            vt.add(ngay[2] + "-" + ngay[1] + "-" + ngay[0]);
            
            vt.add(layDiaDiem(clv.getMaDD()));
            vt.add(clv.getCaLamViec());
            defaultTableModel.addRow(vt);
        }
        
        lichLamTable.setModel(defaultTableModel);
        
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
        if (tenDiaDiem.equals("Tất cả")){
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
            nguoiLamPhuLb.setText("Không có");
        }
    }
    
// dang ki san pham Panel
    private void taoDuLieuDangKi(){
        
        tenNVDKLb.setText(ThongTinDangNhap.getTenNguoiDung());
        
        CaLamViec clv = layCaLamViecHienTai();
        if (clv != null){
            tenDiaDiemDKLB.setText(layDiaDiem(clv.getMaDD()));
        }
        
        taoDuLieuDKSanPhamPanel();
    }
    private CaLamViec layCaLamViecHienTai(){
        
        LocalDateTime now = LocalDateTime.now();
        
        String ngayHienTai = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(now);
        int gioHienTai = Integer.valueOf(DateTimeFormatter.ofPattern("HH").format(now));
        
        System.out.println(ngayHienTai);
        System.out.println(gioHienTai);
        
        for (CaLamViec clv : dsCaLamViec){
            if (clv.getNgay().equals(ngayHienTai)){
               if (gioHienTai < 12 && clv.getCaLamViec().equals("Sáng")){
                   return clv;
               }
            }
            if (clv.getNgay().equals(ngayHienTai)){
               if (gioHienTai > 12 && clv.getCaLamViec().equals("Chiều")){
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
        
        donDKChiTietPanel.removeAll();
        
        // hien len thnah phan can dang ky
//        donDKChiTietPanel.add(new ThanhPhanHoaDonPanel(dsSanPham.get(0),1, this));
        
        donDKChiTietPanel.repaint();
        donDKChiTietPanel.validate();
        
        capNhapHoaDonDangKy();
    }
    private void capNhapHoaDonDangKy(){
        
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        quayLaiBtn = new javax.swing.JButton();
        mTabbedPane = new javax.swing.JTabbedPane();
        banHangPanel = new javax.swing.JPanel();
        hoaDonPanel = new javax.swing.JPanel();
        thanhToanPanel = new javax.swing.JPanel();
        thanhToanBtn = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        tongTienLb = new javax.swing.JLabel();
        tenNVLb = new javax.swing.JLabel();
        soLuongDonHangLb = new javax.swing.JLabel();
        huyDonHangBtn = new javax.swing.JButton();
        donHangPanel = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        sanPhamBanHangPanel = new javax.swing.JPanel();
        congThucPanel = new javax.swing.JPanel();
        sanPhamCongThucPanel = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        tenCTLb = new javax.swing.JLabel();
        chiTietCongThucPanel = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        chiTietNguyenLieuTA = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        ChiTietPhaCheTA = new javax.swing.JTextArea();
        lichLamPanel = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        lichLamTable = new javax.swing.JTable();
        diaDiemCB = new javax.swing.JComboBox<>();
        jLabel17 = new javax.swing.JLabel();
        sapXepTheoThoiGianCB = new javax.swing.JComboBox<>();
        jLabel19 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        chonNgayLamDC = new com.github.lgooddatepicker.components.DatePicker();
        chiTietCaLamViecPanel = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
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
        DKSPPanel = new javax.swing.JPanel();
        dkSanPhamPanel = new javax.swing.JPanel();
        chiTietDKPanel = new javax.swing.JPanel();
        thanhToanPanel1 = new javax.swing.JPanel();
        dangKyBtn = new javax.swing.JButton();
        jLabel18 = new javax.swing.JLabel();
        tenNVDKLb = new javax.swing.JLabel();
        huyDKBtn = new javax.swing.JButton();
        jLabel21 = new javax.swing.JLabel();
        tenDiaDiemDKLB = new javax.swing.JLabel();
        donDKChiTietPanel = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        soLuongNguyenLieuTable = new javax.swing.JTable();

        setPreferredSize(new java.awt.Dimension(1000, 500));

        quayLaiBtn.setText("Quay lại");
        quayLaiBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                quayLaiBtnActionPerformed(evt);
            }
        });

        mTabbedPane.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        mTabbedPane.setPreferredSize(new java.awt.Dimension(1000, 500));

        thanhToanBtn.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        thanhToanBtn.setText("Thanh toán");
        thanhToanBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                thanhToanBtnActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel1.setText("Số lượng: ");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel2.setText("Tổng: ");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel3.setText("Nhân viên:");

        tongTienLb.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        tongTienLb.setForeground(new java.awt.Color(255, 0, 0));
        tongTienLb.setText("0");

        tenNVLb.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        tenNVLb.setForeground(new java.awt.Color(255, 0, 0));

        soLuongDonHangLb.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        soLuongDonHangLb.setForeground(new java.awt.Color(255, 0, 0));
        soLuongDonHangLb.setText("0");

        huyDonHangBtn.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        huyDonHangBtn.setText("Hủy");
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
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(tenNVLb, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(20, 20, 20))))
                    .addGroup(thanhToanPanelLayout.createSequentialGroup()
                        .addComponent(huyDonHangBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(thanhToanBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

        donHangPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        donHangPanel.setLayout(new javax.swing.BoxLayout(donHangPanel, javax.swing.BoxLayout.PAGE_AXIS));

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Hóa đơn");

        javax.swing.GroupLayout hoaDonPanelLayout = new javax.swing.GroupLayout(hoaDonPanel);
        hoaDonPanel.setLayout(hoaDonPanelLayout);
        hoaDonPanelLayout.setHorizontalGroup(
            hoaDonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(hoaDonPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(hoaDonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(thanhToanPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(donHangPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        hoaDonPanelLayout.setVerticalGroup(
            hoaDonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, hoaDonPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(donHangPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(thanhToanPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        sanPhamBanHangPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        sanPhamBanHangPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 10, 10));

        javax.swing.GroupLayout banHangPanelLayout = new javax.swing.GroupLayout(banHangPanel);
        banHangPanel.setLayout(banHangPanelLayout);
        banHangPanelLayout.setHorizontalGroup(
            banHangPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(banHangPanelLayout.createSequentialGroup()
                .addComponent(hoaDonPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(sanPhamBanHangPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 416, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        banHangPanelLayout.setVerticalGroup(
            banHangPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(hoaDonPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(banHangPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(sanPhamBanHangPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        mTabbedPane.addTab("Bán hàng", banHangPanel);

        sanPhamCongThucPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        sanPhamCongThucPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 10, 10));

        tenCTLb.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        tenCTLb.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        tenCTLb.setText("Chi tiết công thức");

        chiTietCongThucPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel6.setText("Nguyên liệu");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel8.setText("Cách pha chế");

        chiTietNguyenLieuTA.setEditable(false);
        chiTietNguyenLieuTA.setBackground(new java.awt.Color(240, 240, 240));
        chiTietNguyenLieuTA.setColumns(20);
        chiTietNguyenLieuTA.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        chiTietNguyenLieuTA.setLineWrap(true);
        chiTietNguyenLieuTA.setRows(5);
        jScrollPane1.setViewportView(chiTietNguyenLieuTA);

        ChiTietPhaCheTA.setEditable(false);
        ChiTietPhaCheTA.setBackground(new java.awt.Color(240, 240, 240));
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
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 304, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tenCTLb, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(chiTietCongThucPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tenCTLb)
                .addGap(18, 18, 18)
                .addComponent(chiTietCongThucPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                .addComponent(sanPhamCongThucPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 416, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        congThucPanelLayout.setVerticalGroup(
            congThucPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(congThucPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(congThucPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(sanPhamCongThucPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        mTabbedPane.addTab("Công thức", congThucPanel);

        lichLamPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("Lịch làm");

        lichLamTable.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lichLamTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Ngày làm", "Địa điểm", "Ca Làm việc"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        lichLamTable.setToolTipText("Nhấn 2 lần để xem");
        lichLamTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        lichLamTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lichLamTableMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(lichLamTable);

        diaDiemCB.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        diaDiemCB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                diaDiemCBActionPerformed(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel17.setText("Địa điểm");

        sapXepTheoThoiGianCB.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        sapXepTheoThoiGianCB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tăng dần theo ngày", "Giảm dần theo ngày" }));
        sapXepTheoThoiGianCB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sapXepTheoThoiGianCBActionPerformed(evt);
            }
        });

        jLabel19.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel19.setText("Ngày làm");

        jLabel14.setText("* Nhấn 2 lần để xem chi tết");

        chonNgayLamDC.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        chonNgayLamDC.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                chonNgayLamDCPropertyChange(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 589, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel19, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 152, Short.MAX_VALUE)
                            .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(sapXepTheoThoiGianCB, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(diaDiemCB, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(chonNgayLamDC, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
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
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel14)
                .addContainerGap())
        );

        chiTietCaLamViecPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Chi tiết ca làm việc");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setText("Đại điểm:");

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel9.setText("Ca làm việc:");

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel10.setText("Ngày:");

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel11.setText("Người làm chính:");

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel12.setText("Người làm phụ:");

        nguoiLamPhuLb.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        nguoiLamPhuLb.setText("Người làm phụ:");

        nguoiLamChinhLb.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        nguoiLamChinhLb.setText("Người làm phụ:");

        ngayChiTietLb.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        ngayChiTietLb.setText("Người làm phụ:");

        caLamViecChiTietLb.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        caLamViecChiTietLb.setText("Người làm phụ:");

        diaDiemChiTietLb.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        diaDiemChiTietLb.setText("Người làm phụ:");

        javax.swing.GroupLayout chiTietCaLamViecPanelLayout = new javax.swing.GroupLayout(chiTietCaLamViecPanel);
        chiTietCaLamViecPanel.setLayout(chiTietCaLamViecPanelLayout);
        chiTietCaLamViecPanelLayout.setHorizontalGroup(
            chiTietCaLamViecPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(chiTietCaLamViecPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(chiTietCaLamViecPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(chiTietCaLamViecPanelLayout.createSequentialGroup()
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
                            .addComponent(diaDiemChiTietLb, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 264, Short.MAX_VALUE))))
                .addContainerGap())
        );
        chiTietCaLamViecPanelLayout.setVerticalGroup(
            chiTietCaLamViecPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(chiTietCaLamViecPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addGap(18, 18, 18)
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
                .addContainerGap(381, Short.MAX_VALUE))
        );

        jLabel7.getAccessibleContext().setAccessibleName("Địa điểm:");

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

        mTabbedPane.addTab("Lịch làm", lichLamPanel);

        dkSanPhamPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        dkSanPhamPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 10, 10));

        dangKyBtn.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        dangKyBtn.setText("Đăng ký");
        dangKyBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dangKyBtnActionPerformed(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel18.setText("Nhân viên:");

        tenNVDKLb.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        tenNVDKLb.setForeground(new java.awt.Color(255, 0, 0));
        tenNVDKLb.setText("Nguyễn Trung Hiếu");

        huyDKBtn.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        huyDKBtn.setText("Hủy");
        huyDKBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                huyDKBtnActionPerformed(evt);
            }
        });

        jLabel21.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel21.setText("Địa điểm:");

        tenDiaDiemDKLB.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        tenDiaDiemDKLB.setForeground(new java.awt.Color(255, 0, 0));
        tenDiaDiemDKLB.setText("Nguyễn Trung Hiếu");

        javax.swing.GroupLayout thanhToanPanel1Layout = new javax.swing.GroupLayout(thanhToanPanel1);
        thanhToanPanel1.setLayout(thanhToanPanel1Layout);
        thanhToanPanel1Layout.setHorizontalGroup(
            thanhToanPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(thanhToanPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(thanhToanPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(thanhToanPanel1Layout.createSequentialGroup()
                        .addComponent(huyDKBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 82, Short.MAX_VALUE)
                        .addComponent(dangKyBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(thanhToanPanel1Layout.createSequentialGroup()
                        .addGroup(thanhToanPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(thanhToanPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tenDiaDiemDKLB, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tenNVDKLb, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
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
                    .addComponent(jLabel21)
                    .addComponent(tenDiaDiemDKLB))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(thanhToanPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(huyDKBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dangKyBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        donDKChiTietPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        donDKChiTietPanel.setLayout(new javax.swing.BoxLayout(donDKChiTietPanel, javax.swing.BoxLayout.PAGE_AXIS));

        jLabel20.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel20.setText("Sản phẩm đăng ký");

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        soLuongNguyenLieuTable.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        soLuongNguyenLieuTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã nguyên liệu", "Tên nguyên liệu", "Số lượng"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane4.setViewportView(soLuongNguyenLieuTable);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 197, Short.MAX_VALUE)
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
                    .addComponent(jLabel20, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        chiTietDKPanelLayout.setVerticalGroup(
            chiTietDKPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, chiTietDKPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel20)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(donDKChiTietPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 223, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(thanhToanPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout DKSPPanelLayout = new javax.swing.GroupLayout(DKSPPanel);
        DKSPPanel.setLayout(DKSPPanelLayout);
        DKSPPanelLayout.setHorizontalGroup(
            DKSPPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, DKSPPanelLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(chiTietDKPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dkSanPhamPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 416, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        DKSPPanelLayout.setVerticalGroup(
            DKSPPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, DKSPPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(DKSPPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(chiTietDKPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(dkSanPhamPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        mTabbedPane.addTab("Đăng ký sản phẩm", DKSPPanel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(quayLaiBtn)
                .addContainerGap())
            .addComponent(mTabbedPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(quayLaiBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(mTabbedPane, javax.swing.GroupLayout.DEFAULT_SIZE, 646, Short.MAX_VALUE))
        );

        mTabbedPane.getAccessibleContext().setAccessibleName("");
    }// </editor-fold>//GEN-END:initComponents

    private void quayLaiBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_quayLaiBtnActionPerformed
        // TODO add your handling code here:
        Util.doiPanel(jLayeredPane, homePanel);
    }//GEN-LAST:event_quayLaiBtnActionPerformed

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
        // TODO add your handling code here:
        if (evt.getClickCount() == 2){
            chiTietCaLam();
        }
    }//GEN-LAST:event_lichLamTableMouseClicked

    private void dangKyBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dangKyBtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dangKyBtnActionPerformed

    private void huyDKBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_huyDKBtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_huyDKBtnActionPerformed

    private void chonNgayLamDCPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_chonNgayLamDCPropertyChange
        // TODO add your handling code here:
        timKiemCaLamViec();
    }//GEN-LAST:event_chonNgayLamDCPropertyChange


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea ChiTietPhaCheTA;
    private javax.swing.JPanel DKSPPanel;
    private javax.swing.JPanel banHangPanel;
    private javax.swing.JLabel caLamViecChiTietLb;
    private javax.swing.JPanel chiTietCaLamViecPanel;
    private javax.swing.JPanel chiTietCongThucPanel;
    private javax.swing.JPanel chiTietDKPanel;
    private javax.swing.JTextArea chiTietNguyenLieuTA;
    private com.github.lgooddatepicker.components.DatePicker chonNgayLamDC;
    private javax.swing.JPanel congThucPanel;
    private javax.swing.JButton dangKyBtn;
    private javax.swing.JComboBox<String> diaDiemCB;
    private javax.swing.JLabel diaDiemChiTietLb;
    private javax.swing.JPanel dkSanPhamPanel;
    private javax.swing.JPanel donDKChiTietPanel;
    private javax.swing.JPanel donHangPanel;
    private javax.swing.JPanel hoaDonPanel;
    private javax.swing.JButton huyDKBtn;
    private javax.swing.JButton huyDonHangBtn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JPanel lichLamPanel;
    private javax.swing.JTable lichLamTable;
    private javax.swing.JTabbedPane mTabbedPane;
    private javax.swing.JLabel ngayChiTietLb;
    private javax.swing.JLabel nguoiLamChinhLb;
    private javax.swing.JLabel nguoiLamPhuLb;
    private javax.swing.JButton quayLaiBtn;
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
    public void onStateChanged(SanPham sp, int sl) {
        int index = dsSanPham.indexOf(sp);
        thanhPhanHoaDon.put(index, sl);
        capNhatTongTien();
    }


    @Override
    public void onCancelButtonClicked(SanPham sp) {
        int index = dsSanPham.indexOf(sp);
        thanhPhanHoaDon.put(index, 0);
        capNhatHoaDon();
    }
}
