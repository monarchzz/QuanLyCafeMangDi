package quanlycafemangdi.view;

import java.awt.Color;
import java.sql.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import quanlycafemangdi.Util;
import quanlycafemangdi.data.Data;
import quanlycafemangdi.model.ThongTinDangNhap;
import quanlycafemangdi.view.chart.ChartAdapter;
import quanlycafemangdi.view.chart.DatasetValue;
import quanlycafemangdi.view.chart.MChartPanel;

/**
 *
 * @author admin
 */
public class TrangChu extends javax.swing.JFrame {
    
    private Data data;
    private String ngayHienTai;
    private String thangHienTai;
    private String namHienTai;
    private String namDauTien;
    
    private NhanVienPanel nhanVienPanel;
    private NguyenLieu_Panel nguyenLieu_Panel;
    private SanPham_Panel sanPham_Panel;
    private ThongKePanel thongKePanel;
    Timer timer = new Timer(0, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            Date current = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            thoiGian_Lbl.setText(sdf.format(current));
        }
    });
    
    public TrangChu() {
        initComponents();
        this.setLocationRelativeTo(null);
        // Hien thi o goc tren
        hienThiTenNguoiDung_Lbl.setText(ThongTinDangNhap.getTenNguoiDung());
        ngay_Lbl.setText(new SimpleDateFormat("dd/MM/yyyy").format(new Date())); 
        
        // Thoi gian
        timer.start();
        
        // Hien thi o phan "Thong tin tai khoan" trong Tuy Chon
        hienThiThongTinTaiKhoan(ThongTinDangNhap.getTenDangNhap());
        
        
        nhanVienPanel = new NhanVienPanel();
        nguyenLieu_Panel = new NguyenLieu_Panel();
        sanPham_Panel = new SanPham_Panel();
        thongKePanel = new ThongKePanel();
        
        initTrangChu();
        
    }
    
    private void initTrangChu(){
        data = Data.getInstance();
        ngayHienTai = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now());
        thangHienTai = DateTimeFormatter.ofPattern("MM").format(LocalDateTime.now());
        namHienTai = DateTimeFormatter.ofPattern("yyyy").format(LocalDateTime.now());
        
        taoDuLieuTongQuanPanel();
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
            chiPhiList.add(new DatasetValue("Chi ph??",String.valueOf(i),chiPhiTheoNgay(ngay)));
            
        }
        
        
        
        //loi nhuan
        for (int i = 0; i < doanhThuList.size(); i++){
            DatasetValue d1 = doanhThuList.get(i);
            DatasetValue d2 = chiPhiList.get(i);
            
            long lN = (long)(d1.getValue() - d2.getValue());
            loiNhuanList.add(new DatasetValue("L???i nhu???n",d1.getCategory(),lN));
//            if (lN > 0){
//                loiNhuanList.add(new DatasetValue("L???i nhu???n",d1.getCategory(),lN));
//            }else loiNhuanList.add(new DatasetValue("L???i nhu???n",d1.getCategory(),0));
        }
        
        doanhThuLb.setText(Util.formatCurrency((long)doanhThuList.get(0).getValue()));
        chiPhiLb.setText(Util.formatCurrency((long)chiPhiList.get(0).getValue()));
        loiNhuanLb.setText(Util.formatCurrency((long)loiNhuanList.get(0).getValue()));
        
        Collections.reverse(doanhThuList);
        Collections.reverse(chiPhiList);
        Collections.reverse(loiNhuanList);
        MChartPanel mChartPanel = new MChartPanel(new ChartAdapter(doanhThuList, chiPhiList, loiNhuanList).getChart("Ng??y"));
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
            chiPhiList.add(new DatasetValue("Chi ph??",String.valueOf(i),chiPhiTheoThang(bt, kt)));
            
        }
        
        for (int i = 0; i < doanhThuList.size(); i++){
            DatasetValue d1 = doanhThuList.get(i);
            DatasetValue d2 = chiPhiList.get(i);
            
            
            long lN = (long)(d1.getValue() - d2.getValue());
            
//            if (lN > 0){
//                loiNhuanList.add(new DatasetValue("L???i nhu???n",d1.getCategory(),lN));
//            }else loiNhuanList.add(new DatasetValue("L???i nhu???n",d1.getCategory(),0));
            loiNhuanList.add(new DatasetValue("L???i nhu???n",d1.getCategory(),lN));
        }
        
        doanhThuLb.setText(Util.formatCurrency((long)doanhThuList.get(0).getValue()));
        chiPhiLb.setText(Util.formatCurrency((long)chiPhiList.get(0).getValue()));
        loiNhuanLb.setText(Util.formatCurrency((long)loiNhuanList.get(0).getValue()));
        
        
        Collections.reverse(doanhThuList);
        Collections.reverse(chiPhiList);
        Collections.reverse(loiNhuanList);
        MChartPanel mChartPanel = new MChartPanel(new ChartAdapter(doanhThuList, chiPhiList, loiNhuanList).getChart("Th??ng"));
        
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
            chiPhiList.add(new DatasetValue("Chi ph??",String.valueOf(i),cp));
            loiNhuanList.add(new DatasetValue("L???i nhu???n",String.valueOf(i),ln));
            
        }
        
        doanhThuLb.setText(Util.formatCurrency((long)doanhThuList.get(0).getValue()));
        chiPhiLb.setText(Util.formatCurrency((long)chiPhiList.get(0).getValue()));
        loiNhuanLb.setText(Util.formatCurrency((long)loiNhuanList.get(0).getValue()));
        
        Collections.reverse(doanhThuList);
        Collections.reverse(chiPhiList);
        Collections.reverse(loiNhuanList);
        MChartPanel mChartPanel = new MChartPanel(new ChartAdapter(doanhThuList, chiPhiList, loiNhuanList).getChart("N??m"));
        
        Util.doiPanel(chartLayeredPane, mChartPanel);
    }
    
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

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel7 = new javax.swing.JPanel();
        thongTinDangNhap_Pn = new javax.swing.JPanel();
        hienThiTenNguoiDung_Lbl = new javax.swing.JLabel();
        ngay_Lbl = new javax.swing.JLabel();
        thoiGian_Lbl = new javax.swing.JLabel();
        trangChu_LPn = new javax.swing.JLayeredPane();
        trangChu_Pn = new javax.swing.JPanel();
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
        jPanel8 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        loiNhuanLb = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        chonTGCB = new javax.swing.JComboBox<>();
        jPanel12 = new javax.swing.JPanel();
        chartLayeredPane = new javax.swing.JLayeredPane();
        tuyChon_Pn = new javax.swing.JPanel();
        tinhNangCuaTuyChon_LPn = new javax.swing.JLayeredPane();
        thongTinCaNhan_Pn = new javax.swing.JPanel();
        tenTaiKhoan_Lbl = new javax.swing.JLabel();
        tenTaiKhoan_TF = new javax.swing.JTextField();
        chucVu_Lbl = new javax.swing.JLabel();
        chucVu_TF = new javax.swing.JTextField();
        CMND_Lbl = new javax.swing.JLabel();
        CMND_TF = new javax.swing.JTextField();
        SDT_Lbl = new javax.swing.JLabel();
        SDT_TF = new javax.swing.JTextField();
        gioiTinh_Lbl = new javax.swing.JLabel();
        gioiTinh_TF = new javax.swing.JTextField();
        hoVaTen_Lbl = new javax.swing.JLabel();
        hoVaTen_TF = new javax.swing.JTextField();
        avatar_Lbl = new javax.swing.JLabel();
        thongTinCaNhan_Lbl = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        doiMatKhau_Pn = new javax.swing.JPanel();
        matKhauCu_Lbl = new javax.swing.JLabel();
        matKhauCu_PwF = new javax.swing.JPasswordField();
        matKhauMoi_Lbl = new javax.swing.JLabel();
        matKhauMoi_PwF = new javax.swing.JPasswordField();
        nhapLaiMatKhauMoi_Lbl = new javax.swing.JLabel();
        nhapLaiMatKhauMoi_PwF = new javax.swing.JPasswordField();
        doiMatKhau_Btn = new javax.swing.JButton();
        doiMatKhau_Lbl = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel4 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        chucNang_LPn = new javax.swing.JLayeredPane();
        quanLy_Pn = new javax.swing.JPanel();
        trangChuClick_Pn = new javax.swing.JPanel();
        trangChuClick_Lbl = new javax.swing.JLabel();
        quanLyKhoClick_Pn = new javax.swing.JPanel();
        quanLyKhoClick_Lbl = new javax.swing.JLabel();
        sanPhamClick_Pn = new javax.swing.JPanel();
        sanPhamClick_Lbl = new javax.swing.JLabel();
        nhanVienClick_Pn = new javax.swing.JPanel();
        nhanVienClick_Lbl = new javax.swing.JLabel();
        diaDiemClick_Pn = new javax.swing.JPanel();
        diaDiemClick_Lbl = new javax.swing.JLabel();
        banHangClick_Pn = new javax.swing.JPanel();
        banHangClick_Lbl = new javax.swing.JLabel();
        thongKeClick_Pn = new javax.swing.JPanel();
        thongKeClick_Lbl = new javax.swing.JLabel();
        tuyChonClick_Pn = new javax.swing.JPanel();
        tuyChonClick_Lbl = new javax.swing.JLabel();
        tuyChon2_Pn = new javax.swing.JPanel();
        thongTinCaNhanClick_Pn = new javax.swing.JPanel();
        thongTinCaNhanClick_Lbl = new javax.swing.JLabel();
        doiMatKhauClick_Pn = new javax.swing.JPanel();
        doiMatKhauClick_Lbl = new javax.swing.JLabel();
        quayLaiClick_Pn = new javax.swing.JPanel();
        quayLaiClick_Lbl = new javax.swing.JLabel();
        dangXuatClick_Pn = new javax.swing.JPanel();
        dangXuatClick_Lbl = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        addContainerListener(new java.awt.event.ContainerAdapter() {
            public void componentAdded(java.awt.event.ContainerEvent evt) {
                formComponentAdded(evt);
            }
        });

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));

        thongTinDangNhap_Pn.setBackground(new java.awt.Color(153, 153, 255));
        thongTinDangNhap_Pn.setPreferredSize(new java.awt.Dimension(92, 115));

        hienThiTenNguoiDung_Lbl.setBackground(new java.awt.Color(153, 153, 255));
        hienThiTenNguoiDung_Lbl.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        hienThiTenNguoiDung_Lbl.setForeground(new java.awt.Color(255, 255, 255));
        hienThiTenNguoiDung_Lbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        hienThiTenNguoiDung_Lbl.setText("887687hjhj");

        ngay_Lbl.setBackground(new java.awt.Color(153, 153, 255));
        ngay_Lbl.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        ngay_Lbl.setForeground(new java.awt.Color(255, 255, 255));
        ngay_Lbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ngay_Lbl.setText("sdfsdfsdf");

        thoiGian_Lbl.setBackground(new java.awt.Color(153, 153, 255));
        thoiGian_Lbl.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        thoiGian_Lbl.setForeground(new java.awt.Color(255, 255, 255));
        thoiGian_Lbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        thoiGian_Lbl.setText("sdadsa");

        javax.swing.GroupLayout thongTinDangNhap_PnLayout = new javax.swing.GroupLayout(thongTinDangNhap_Pn);
        thongTinDangNhap_Pn.setLayout(thongTinDangNhap_PnLayout);
        thongTinDangNhap_PnLayout.setHorizontalGroup(
            thongTinDangNhap_PnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(hienThiTenNguoiDung_Lbl, javax.swing.GroupLayout.DEFAULT_SIZE, 187, Short.MAX_VALUE)
            .addComponent(ngay_Lbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(thoiGian_Lbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        thongTinDangNhap_PnLayout.setVerticalGroup(
            thongTinDangNhap_PnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(thongTinDangNhap_PnLayout.createSequentialGroup()
                .addComponent(hienThiTenNguoiDung_Lbl, javax.swing.GroupLayout.DEFAULT_SIZE, 65, Short.MAX_VALUE)
                .addGap(11, 11, 11)
                .addComponent(ngay_Lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(thoiGian_Lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
        );

        trangChu_LPn.setLayout(new java.awt.CardLayout());

        trangChu_Pn.setBackground(new java.awt.Color(255, 255, 255));
        trangChu_Pn.setPreferredSize(new java.awt.Dimension(1000, 700));

        jPanel1.setBackground(new java.awt.Color(114, 102, 186));

        jLabel1.setBackground(new java.awt.Color(114, 102, 186));
        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Trang ch???");

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

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("Doanh thu");

        doanhThuLb.setBackground(new java.awt.Color(255, 255, 255));
        doanhThuLb.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N
        doanhThuLb.setForeground(new java.awt.Color(255, 255, 255));
        doanhThuLb.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        doanhThuLb.setText("0");

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
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 253, Short.MAX_VALUE))
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

        chiPhiLb.setBackground(new java.awt.Color(255, 255, 255));
        chiPhiLb.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N
        chiPhiLb.setForeground(new java.awt.Color(255, 255, 255));
        chiPhiLb.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        chiPhiLb.setText("0");

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel12.setText("Chi ph??");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, 255, Short.MAX_VALUE)
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

        jPanel8.setBackground(new java.awt.Color(27, 188, 155));

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/HinhAnh/local_atm_white.png"))); // NOI18N

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel10.setText("L???i nhu???n");

        loiNhuanLb.setBackground(new java.awt.Color(255, 255, 255));
        loiNhuanLb.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N
        loiNhuanLb.setForeground(new java.awt.Color(255, 255, 255));
        loiNhuanLb.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        loiNhuanLb.setText("0");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(loiNhuanLb, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, 255, Short.MAX_VALUE))
                .addGap(30, 30, 30))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(loiNhuanLb, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.add(jPanel8);

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setLayout(new java.awt.GridLayout(1, 0));

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel2.setText("Th???i gian");

        chonTGCB.setBackground(new java.awt.Color(254, 254, 254));
        chonTGCB.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        chonTGCB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Theo ng??y", "Theo th??ng", "Theo n??m" }));
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

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(chonTGCB, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(chonTGCB, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel9.add(jPanel10);

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
            .addComponent(chartLayeredPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 561, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout trangChu_PnLayout = new javax.swing.GroupLayout(trangChu_Pn);
        trangChu_Pn.setLayout(trangChu_PnLayout);
        trangChu_PnLayout.setHorizontalGroup(
            trangChu_PnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(trangChu_PnLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(trangChu_PnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel12, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 1141, Short.MAX_VALUE)
                    .addGroup(trangChu_PnLayout.createSequentialGroup()
                        .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(119, 119, 119)))
                .addContainerGap())
        );
        trangChu_PnLayout.setVerticalGroup(
            trangChu_PnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(trangChu_PnLayout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        trangChu_LPn.add(trangChu_Pn, "card2");

        tuyChon_Pn.setBackground(new java.awt.Color(255, 255, 255));

        tinhNangCuaTuyChon_LPn.setLayout(new java.awt.CardLayout());

        thongTinCaNhan_Pn.setBackground(new java.awt.Color(255, 255, 255));

        tenTaiKhoan_Lbl.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        tenTaiKhoan_Lbl.setText("T??n t??i kho???n:");

        tenTaiKhoan_TF.setEditable(false);
        tenTaiKhoan_TF.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        chucVu_Lbl.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        chucVu_Lbl.setText("Ch???c v???:");

        chucVu_TF.setEditable(false);
        chucVu_TF.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        CMND_Lbl.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        CMND_Lbl.setText("CMND/CCCD:");

        CMND_TF.setEditable(false);
        CMND_TF.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        CMND_TF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CMND_TFActionPerformed(evt);
            }
        });

        SDT_Lbl.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        SDT_Lbl.setText("S??T:");

        SDT_TF.setEditable(false);
        SDT_TF.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        gioiTinh_Lbl.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        gioiTinh_Lbl.setText("Gi???i t??nh:");

        gioiTinh_TF.setEditable(false);
        gioiTinh_TF.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        hoVaTen_Lbl.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        hoVaTen_Lbl.setText("H??? v?? t??n:");

        hoVaTen_TF.setEditable(false);
        hoVaTen_TF.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        avatar_Lbl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/HinhAnh/man-avatar-profile-vector-21372065.jpg"))); // NOI18N

        thongTinCaNhan_Lbl.setBackground(new java.awt.Color(114, 102, 186));
        thongTinCaNhan_Lbl.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        thongTinCaNhan_Lbl.setForeground(new java.awt.Color(255, 255, 255));
        thongTinCaNhan_Lbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        thongTinCaNhan_Lbl.setText("Th??ng tin c?? nh??n");
        thongTinCaNhan_Lbl.setOpaque(true);

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        javax.swing.GroupLayout thongTinCaNhan_PnLayout = new javax.swing.GroupLayout(thongTinCaNhan_Pn);
        thongTinCaNhan_Pn.setLayout(thongTinCaNhan_PnLayout);
        thongTinCaNhan_PnLayout.setHorizontalGroup(
            thongTinCaNhan_PnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(thongTinCaNhan_PnLayout.createSequentialGroup()
                .addComponent(avatar_Lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 557, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(48, 48, 48)
                .addGroup(thongTinCaNhan_PnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, thongTinCaNhan_PnLayout.createSequentialGroup()
                        .addGroup(thongTinCaNhan_PnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(tenTaiKhoan_Lbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(chucVu_Lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(thongTinCaNhan_PnLayout.createSequentialGroup()
                        .addGroup(thongTinCaNhan_PnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(hoVaTen_Lbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(gioiTinh_Lbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(SDT_Lbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(CMND_Lbl))
                        .addGap(56, 56, 56)))
                .addGroup(thongTinCaNhan_PnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(CMND_TF, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SDT_TF, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(gioiTinh_TF, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(hoVaTen_TF, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tenTaiKhoan_TF, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(chucVu_TF, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(141, Short.MAX_VALUE))
            .addComponent(thongTinCaNhan_Lbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        thongTinCaNhan_PnLayout.setVerticalGroup(
            thongTinCaNhan_PnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(thongTinCaNhan_PnLayout.createSequentialGroup()
                .addComponent(thongTinCaNhan_Lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(thongTinCaNhan_PnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(thongTinCaNhan_PnLayout.createSequentialGroup()
                        .addGap(182, 182, 182)
                        .addGroup(thongTinCaNhan_PnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tenTaiKhoan_Lbl)
                            .addComponent(tenTaiKhoan_TF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(thongTinCaNhan_PnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(chucVu_Lbl)
                            .addComponent(chucVu_TF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(thongTinCaNhan_PnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(CMND_TF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(CMND_Lbl))
                        .addGap(18, 18, 18)
                        .addGroup(thongTinCaNhan_PnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(SDT_TF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(SDT_Lbl))
                        .addGap(18, 18, 18)
                        .addGroup(thongTinCaNhan_PnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(gioiTinh_TF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(gioiTinh_Lbl))
                        .addGap(18, 18, 18)
                        .addGroup(thongTinCaNhan_PnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(hoVaTen_TF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(hoVaTen_Lbl)))
                    .addGroup(thongTinCaNhan_PnLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(thongTinCaNhan_PnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 658, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(avatar_Lbl))))
                .addContainerGap(89, Short.MAX_VALUE))
        );

        tinhNangCuaTuyChon_LPn.add(thongTinCaNhan_Pn, "card2");

        doiMatKhau_Pn.setBackground(new java.awt.Color(255, 255, 255));
        doiMatKhau_Pn.setPreferredSize(new java.awt.Dimension(1161, 807));

        matKhauCu_Lbl.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        matKhauCu_Lbl.setText("M???t kh???u c??:");

        matKhauCu_PwF.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        matKhauMoi_Lbl.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        matKhauMoi_Lbl.setText("M???t kh???u m???i:");

        matKhauMoi_PwF.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        nhapLaiMatKhauMoi_Lbl.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        nhapLaiMatKhauMoi_Lbl.setText("Nh???p l???i m???t kh???u m???i:");

        nhapLaiMatKhauMoi_PwF.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        doiMatKhau_Btn.setBackground(new java.awt.Color(32, 136, 203));
        doiMatKhau_Btn.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        doiMatKhau_Btn.setForeground(new java.awt.Color(255, 255, 255));
        doiMatKhau_Btn.setText("?????i m???t kh???u");
        doiMatKhau_Btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                doiMatKhau_BtnActionPerformed(evt);
            }
        });

        doiMatKhau_Lbl.setBackground(new java.awt.Color(114, 102, 186));
        doiMatKhau_Lbl.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        doiMatKhau_Lbl.setForeground(new java.awt.Color(255, 255, 255));
        doiMatKhau_Lbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        doiMatKhau_Lbl.setText("?????i m???t kh???u");
        doiMatKhau_Lbl.setOpaque(true);

        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/HinhAnh/changeYourPassword_auto_x2_resize.jpg"))); // NOI18N

        jLabel7.setBackground(new java.awt.Color(255, 255, 255));
        jLabel7.setFont(new java.awt.Font("Tahoma", 3, 16)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 51, 51));
        jLabel7.setText("*Tuy???t ?????i kh??ng chia s??? m???t kh???u v???i ng?????i kh??c");

        javax.swing.GroupLayout doiMatKhau_PnLayout = new javax.swing.GroupLayout(doiMatKhau_Pn);
        doiMatKhau_Pn.setLayout(doiMatKhau_PnLayout);
        doiMatKhau_PnLayout.setHorizontalGroup(
            doiMatKhau_PnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(doiMatKhau_Lbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(doiMatKhau_PnLayout.createSequentialGroup()
                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 650, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(doiMatKhau_PnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(doiMatKhau_Btn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(doiMatKhau_PnLayout.createSequentialGroup()
                        .addGroup(doiMatKhau_PnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(doiMatKhau_PnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(matKhauMoi_Lbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(matKhauCu_Lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(nhapLaiMatKhauMoi_Lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(doiMatKhau_PnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(matKhauMoi_PwF, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(matKhauCu_PwF)
                            .addComponent(nhapLaiMatKhauMoi_PwF, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(30, 30, 30))
        );
        doiMatKhau_PnLayout.setVerticalGroup(
            doiMatKhau_PnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(doiMatKhau_PnLayout.createSequentialGroup()
                .addComponent(doiMatKhau_Lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(doiMatKhau_PnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(doiMatKhau_PnLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(doiMatKhau_PnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(matKhauCu_Lbl, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(matKhauCu_PwF, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(doiMatKhau_PnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(matKhauMoi_PwF, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(matKhauMoi_Lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(doiMatKhau_PnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(nhapLaiMatKhauMoi_PwF)
                            .addComponent(nhapLaiMatKhauMoi_Lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(doiMatKhau_Btn, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(353, 353, 353))
                    .addGroup(doiMatKhau_PnLayout.createSequentialGroup()
                        .addGroup(doiMatKhau_PnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(doiMatKhau_PnLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 627, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(doiMatKhau_PnLayout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 734, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(76, Short.MAX_VALUE))))
        );

        tinhNangCuaTuyChon_LPn.add(doiMatKhau_Pn, "card3");

        javax.swing.GroupLayout tuyChon_PnLayout = new javax.swing.GroupLayout(tuyChon_Pn);
        tuyChon_Pn.setLayout(tuyChon_PnLayout);
        tuyChon_PnLayout.setHorizontalGroup(
            tuyChon_PnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tinhNangCuaTuyChon_LPn, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        tuyChon_PnLayout.setVerticalGroup(
            tuyChon_PnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tinhNangCuaTuyChon_LPn, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        trangChu_LPn.add(tuyChon_Pn, "card3");

        chucNang_LPn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                chucNang_LPnMouseClicked(evt);
            }
        });
        chucNang_LPn.setLayout(new java.awt.CardLayout());

        quanLy_Pn.setPreferredSize(new java.awt.Dimension(30, 535));
        quanLy_Pn.setLayout(new javax.swing.BoxLayout(quanLy_Pn, javax.swing.BoxLayout.PAGE_AXIS));

        trangChuClick_Pn.setBackground(new java.awt.Color(255, 255, 255));
        trangChuClick_Pn.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        trangChuClick_Pn.setPreferredSize(new java.awt.Dimension(72, 30));
        trangChuClick_Pn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                trangChuClick_PnMouseClicked(evt);
            }
        });

        trangChuClick_Lbl.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        trangChuClick_Lbl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/HinhAnh/trangChu.png"))); // NOI18N
        trangChuClick_Lbl.setText("Trang ch???");
        trangChuClick_Lbl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                trangChuClick_LblMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout trangChuClick_PnLayout = new javax.swing.GroupLayout(trangChuClick_Pn);
        trangChuClick_Pn.setLayout(trangChuClick_PnLayout);
        trangChuClick_PnLayout.setHorizontalGroup(
            trangChuClick_PnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, trangChuClick_PnLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(trangChuClick_Lbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        trangChuClick_PnLayout.setVerticalGroup(
            trangChuClick_PnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(trangChuClick_Lbl, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE)
        );

        quanLy_Pn.add(trangChuClick_Pn);

        quanLyKhoClick_Pn.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        quanLyKhoClick_Pn.setPreferredSize(new java.awt.Dimension(72, 24));
        quanLyKhoClick_Pn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                quanLyKhoClick_PnMouseClicked(evt);
            }
        });

        quanLyKhoClick_Lbl.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        quanLyKhoClick_Lbl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/HinhAnh/nguyenLieu.png"))); // NOI18N
        quanLyKhoClick_Lbl.setText("Qu???n l?? kho");
        quanLyKhoClick_Lbl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                quanLyKhoClick_LblMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout quanLyKhoClick_PnLayout = new javax.swing.GroupLayout(quanLyKhoClick_Pn);
        quanLyKhoClick_Pn.setLayout(quanLyKhoClick_PnLayout);
        quanLyKhoClick_PnLayout.setHorizontalGroup(
            quanLyKhoClick_PnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, quanLyKhoClick_PnLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(quanLyKhoClick_Lbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        quanLyKhoClick_PnLayout.setVerticalGroup(
            quanLyKhoClick_PnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(quanLyKhoClick_Lbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        quanLy_Pn.add(quanLyKhoClick_Pn);

        sanPhamClick_Pn.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        sanPhamClick_Pn.setPreferredSize(new java.awt.Dimension(72, 24));
        sanPhamClick_Pn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sanPhamClick_PnMouseClicked(evt);
            }
        });

        sanPhamClick_Lbl.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        sanPhamClick_Lbl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/HinhAnh/sanPham.png"))); // NOI18N
        sanPhamClick_Lbl.setText("S???n ph???m");
        sanPhamClick_Lbl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sanPhamClick_LblMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout sanPhamClick_PnLayout = new javax.swing.GroupLayout(sanPhamClick_Pn);
        sanPhamClick_Pn.setLayout(sanPhamClick_PnLayout);
        sanPhamClick_PnLayout.setHorizontalGroup(
            sanPhamClick_PnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, sanPhamClick_PnLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(sanPhamClick_Lbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        sanPhamClick_PnLayout.setVerticalGroup(
            sanPhamClick_PnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(sanPhamClick_Lbl, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
        );

        quanLy_Pn.add(sanPhamClick_Pn);

        nhanVienClick_Pn.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        nhanVienClick_Pn.setPreferredSize(new java.awt.Dimension(72, 24));
        nhanVienClick_Pn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                nhanVienClick_PnMouseClicked(evt);
            }
        });

        nhanVienClick_Lbl.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        nhanVienClick_Lbl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/HinhAnh/nhanVien.png"))); // NOI18N
        nhanVienClick_Lbl.setText("Nh??n vi??n");
        nhanVienClick_Lbl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                nhanVienClick_LblMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout nhanVienClick_PnLayout = new javax.swing.GroupLayout(nhanVienClick_Pn);
        nhanVienClick_Pn.setLayout(nhanVienClick_PnLayout);
        nhanVienClick_PnLayout.setHorizontalGroup(
            nhanVienClick_PnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(nhanVienClick_PnLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(nhanVienClick_Lbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        nhanVienClick_PnLayout.setVerticalGroup(
            nhanVienClick_PnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(nhanVienClick_Lbl, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
        );

        quanLy_Pn.add(nhanVienClick_Pn);

        diaDiemClick_Pn.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        diaDiemClick_Pn.setPreferredSize(new java.awt.Dimension(72, 24));
        diaDiemClick_Pn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                diaDiemClick_PnMouseClicked(evt);
            }
        });

        diaDiemClick_Lbl.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        diaDiemClick_Lbl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/HinhAnh/diaDiem.png"))); // NOI18N
        diaDiemClick_Lbl.setText("?????a ??i???m");
        diaDiemClick_Lbl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                diaDiemClick_LblMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout diaDiemClick_PnLayout = new javax.swing.GroupLayout(diaDiemClick_Pn);
        diaDiemClick_Pn.setLayout(diaDiemClick_PnLayout);
        diaDiemClick_PnLayout.setHorizontalGroup(
            diaDiemClick_PnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, diaDiemClick_PnLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(diaDiemClick_Lbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        diaDiemClick_PnLayout.setVerticalGroup(
            diaDiemClick_PnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(diaDiemClick_Lbl, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
        );

        quanLy_Pn.add(diaDiemClick_Pn);

        banHangClick_Pn.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        banHangClick_Pn.setPreferredSize(new java.awt.Dimension(72, 24));
        banHangClick_Pn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                banHangClick_PnMouseClicked(evt);
            }
        });

        banHangClick_Lbl.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        banHangClick_Lbl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/HinhAnh/banHang.png"))); // NOI18N
        banHangClick_Lbl.setText("B??n h??ng");
        banHangClick_Lbl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                banHangClick_LblMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout banHangClick_PnLayout = new javax.swing.GroupLayout(banHangClick_Pn);
        banHangClick_Pn.setLayout(banHangClick_PnLayout);
        banHangClick_PnLayout.setHorizontalGroup(
            banHangClick_PnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, banHangClick_PnLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(banHangClick_Lbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        banHangClick_PnLayout.setVerticalGroup(
            banHangClick_PnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(banHangClick_Lbl, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
        );

        quanLy_Pn.add(banHangClick_Pn);

        thongKeClick_Pn.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        thongKeClick_Pn.setPreferredSize(new java.awt.Dimension(72, 24));
        thongKeClick_Pn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                thongKeClick_PnMouseClicked(evt);
            }
        });

        thongKeClick_Lbl.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        thongKeClick_Lbl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/HinhAnh/thongKe.png"))); // NOI18N
        thongKeClick_Lbl.setText("Th???ng k??");
        thongKeClick_Lbl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                thongKeClick_LblMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout thongKeClick_PnLayout = new javax.swing.GroupLayout(thongKeClick_Pn);
        thongKeClick_Pn.setLayout(thongKeClick_PnLayout);
        thongKeClick_PnLayout.setHorizontalGroup(
            thongKeClick_PnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, thongKeClick_PnLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(thongKeClick_Lbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        thongKeClick_PnLayout.setVerticalGroup(
            thongKeClick_PnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(thongKeClick_Lbl, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
        );

        quanLy_Pn.add(thongKeClick_Pn);

        tuyChonClick_Pn.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        tuyChonClick_Pn.setPreferredSize(new java.awt.Dimension(72, 24));
        tuyChonClick_Pn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tuyChonClick_PnMouseClicked(evt);
            }
        });

        tuyChonClick_Lbl.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        tuyChonClick_Lbl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/HinhAnh/ThietLap.png"))); // NOI18N
        tuyChonClick_Lbl.setText("T??y ch???n");
        tuyChonClick_Lbl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tuyChonClick_LblMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout tuyChonClick_PnLayout = new javax.swing.GroupLayout(tuyChonClick_Pn);
        tuyChonClick_Pn.setLayout(tuyChonClick_PnLayout);
        tuyChonClick_PnLayout.setHorizontalGroup(
            tuyChonClick_PnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tuyChonClick_PnLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tuyChonClick_Lbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        tuyChonClick_PnLayout.setVerticalGroup(
            tuyChonClick_PnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tuyChonClick_Lbl, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
        );

        quanLy_Pn.add(tuyChonClick_Pn);

        chucNang_LPn.add(quanLy_Pn, "card2");

        tuyChon2_Pn.setPreferredSize(new java.awt.Dimension(72, 489));
        tuyChon2_Pn.setRequestFocusEnabled(false);
        tuyChon2_Pn.setLayout(new javax.swing.BoxLayout(tuyChon2_Pn, javax.swing.BoxLayout.PAGE_AXIS));

        thongTinCaNhanClick_Pn.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        thongTinCaNhanClick_Pn.setPreferredSize(new java.awt.Dimension(72, 24));
        thongTinCaNhanClick_Pn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                thongTinCaNhanClick_PnMouseClicked(evt);
            }
        });

        thongTinCaNhanClick_Lbl.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        thongTinCaNhanClick_Lbl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/HinhAnh/thongTinCaNhan.png"))); // NOI18N
        thongTinCaNhanClick_Lbl.setText("Th??ng tin c?? nh??n");
        thongTinCaNhanClick_Lbl.setPreferredSize(new java.awt.Dimension(99, 24));
        thongTinCaNhanClick_Lbl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                thongTinCaNhanClick_LblMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout thongTinCaNhanClick_PnLayout = new javax.swing.GroupLayout(thongTinCaNhanClick_Pn);
        thongTinCaNhanClick_Pn.setLayout(thongTinCaNhanClick_PnLayout);
        thongTinCaNhanClick_PnLayout.setHorizontalGroup(
            thongTinCaNhanClick_PnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, thongTinCaNhanClick_PnLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(thongTinCaNhanClick_Lbl, javax.swing.GroupLayout.DEFAULT_SIZE, 156, Short.MAX_VALUE))
        );
        thongTinCaNhanClick_PnLayout.setVerticalGroup(
            thongTinCaNhanClick_PnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(thongTinCaNhanClick_Lbl, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        tuyChon2_Pn.add(thongTinCaNhanClick_Pn);

        doiMatKhauClick_Pn.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        doiMatKhauClick_Pn.setPreferredSize(new java.awt.Dimension(72, 24));
        doiMatKhauClick_Pn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                doiMatKhauClick_PnMouseClicked(evt);
            }
        });

        doiMatKhauClick_Lbl.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        doiMatKhauClick_Lbl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/HinhAnh/matKhau.png"))); // NOI18N
        doiMatKhauClick_Lbl.setText("?????i m???t kh???u");
        doiMatKhauClick_Lbl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                doiMatKhauClick_LblMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout doiMatKhauClick_PnLayout = new javax.swing.GroupLayout(doiMatKhauClick_Pn);
        doiMatKhauClick_Pn.setLayout(doiMatKhauClick_PnLayout);
        doiMatKhauClick_PnLayout.setHorizontalGroup(
            doiMatKhauClick_PnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(doiMatKhauClick_PnLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(doiMatKhauClick_Lbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        doiMatKhauClick_PnLayout.setVerticalGroup(
            doiMatKhauClick_PnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(doiMatKhauClick_Lbl, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE)
        );

        tuyChon2_Pn.add(doiMatKhauClick_Pn);

        quayLaiClick_Pn.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        quayLaiClick_Pn.setPreferredSize(new java.awt.Dimension(72, 24));

        quayLaiClick_Lbl.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        quayLaiClick_Lbl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/HinhAnh/quayLai.png"))); // NOI18N
        quayLaiClick_Lbl.setText("Quay l???i");
        quayLaiClick_Lbl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                quayLaiClick_LblMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout quayLaiClick_PnLayout = new javax.swing.GroupLayout(quayLaiClick_Pn);
        quayLaiClick_Pn.setLayout(quayLaiClick_PnLayout);
        quayLaiClick_PnLayout.setHorizontalGroup(
            quayLaiClick_PnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(quayLaiClick_PnLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(quayLaiClick_Lbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        quayLaiClick_PnLayout.setVerticalGroup(
            quayLaiClick_PnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(quayLaiClick_Lbl, javax.swing.GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE)
        );

        tuyChon2_Pn.add(quayLaiClick_Pn);

        dangXuatClick_Pn.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        dangXuatClick_Pn.setPreferredSize(new java.awt.Dimension(72, 24));

        dangXuatClick_Lbl.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        dangXuatClick_Lbl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/HinhAnh/dangXuat.png"))); // NOI18N
        dangXuatClick_Lbl.setText("????ng xu???t");
        dangXuatClick_Lbl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dangXuatClick_LblMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout dangXuatClick_PnLayout = new javax.swing.GroupLayout(dangXuatClick_Pn);
        dangXuatClick_Pn.setLayout(dangXuatClick_PnLayout);
        dangXuatClick_PnLayout.setHorizontalGroup(
            dangXuatClick_PnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dangXuatClick_PnLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(dangXuatClick_Lbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        dangXuatClick_PnLayout.setVerticalGroup(
            dangXuatClick_PnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(dangXuatClick_Lbl, javax.swing.GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE)
        );

        tuyChon2_Pn.add(dangXuatClick_Pn);

        chucNang_LPn.add(tuyChon2_Pn, "card3");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(thongTinDangNhap_Pn, javax.swing.GroupLayout.DEFAULT_SIZE, 187, Short.MAX_VALUE)
                    .addComponent(chucNang_LPn))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(trangChu_LPn))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(thongTinDangNhap_Pn, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chucNang_LPn))
            .addComponent(trangChu_LPn)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formComponentAdded(java.awt.event.ContainerEvent evt) {//GEN-FIRST:event_formComponentAdded
        // TODO add your handling code here:
        
    }//GEN-LAST:event_formComponentAdded

    private void CMND_TFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CMND_TFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CMND_TFActionPerformed

    private void doiMatKhau_BtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_doiMatKhau_BtnActionPerformed
        String matKhauCu = matKhauCu_PwF.getText();
        String matKhauMoi = matKhauMoi_PwF.getText();
        String nhapLaiMatKhauMoi = nhapLaiMatKhauMoi_PwF.getText();
        
        if (matKhauCu.equals(""))
        {
            JOptionPane.showMessageDialog(rootPane, "Vui l??ng nh???p ?????y ????? th??ng tin", "L???i",
                    JOptionPane.ERROR_MESSAGE);
        }
        else if (matKhauMoi.equals(""))
        {
            JOptionPane.showMessageDialog(rootPane, "Vui l??ng nh???p ?????y ????? th??ng tin", "L???i",
                    JOptionPane.ERROR_MESSAGE);
        }        
        else if (nhapLaiMatKhauMoi.equals(""))
        {
            JOptionPane.showMessageDialog(rootPane, "Vui l??ng nh???p ?????y ????? th??ng tin", "L???i",
                    JOptionPane.ERROR_MESSAGE);
        }        
        else if (matKhauCu.equals(ThongTinDangNhap.getMatKhau()) == false)
        {
            JOptionPane.showMessageDialog(rootPane, "M???t kh???u c?? kh??ng ch??nh x??c", "L???i",
                    JOptionPane.ERROR_MESSAGE);            
        }
        else if (matKhauMoi.equals(nhapLaiMatKhauMoi) == false)
        {
            JOptionPane.showMessageDialog(rootPane, "M???t kh???u m???i kh??ng tr??ng kh???p", "L???i",
                    JOptionPane.ERROR_MESSAGE);                
        }
        else
        {
            if (matKhauMoi.length() < 6)
            {
                JOptionPane.showMessageDialog(rootPane, "M???t kh???u ph???i c?? ??t nh???t 6 k?? t???.", "L???i",
                        JOptionPane.ERROR_MESSAGE);                
                return;
            }
            doiMatKhau(ThongTinDangNhap.getTenDangNhap(), matKhauMoi);
            JOptionPane.showMessageDialog(rootPane, "?????i m???t kh???u th??nh c??ng");
            matKhauCu_PwF.setText("");
            matKhauMoi_PwF.setText("");
            nhapLaiMatKhauMoi_PwF.setText("");                
        }
    }//GEN-LAST:event_doiMatKhau_BtnActionPerformed

    private void trangChuClick_PnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_trangChuClick_PnMouseClicked
        doiMauKhiClick(evt, trangChuClick_Pn, trangChuClick_Lbl, trangChu_Pn);
        taoDuLieuTongQuanPanel();
    }//GEN-LAST:event_trangChuClick_PnMouseClicked

    private void trangChuClick_LblMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_trangChuClick_LblMouseClicked
        doiMauKhiClick(evt, trangChuClick_Pn, trangChuClick_Lbl, trangChu_Pn);
        taoDuLieuTongQuanPanel();
    }//GEN-LAST:event_trangChuClick_LblMouseClicked

    private void quanLyKhoClick_LblMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_quanLyKhoClick_LblMouseClicked
        doiMauKhiClick(evt, quanLyKhoClick_Pn, quanLyKhoClick_Lbl, nguyenLieu_Panel);
    }//GEN-LAST:event_quanLyKhoClick_LblMouseClicked

    private void quanLyKhoClick_PnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_quanLyKhoClick_PnMouseClicked
        doiMauKhiClick(evt, quanLyKhoClick_Pn, quanLyKhoClick_Lbl, nguyenLieu_Panel);
    }//GEN-LAST:event_quanLyKhoClick_PnMouseClicked

    private void chucNang_LPnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_chucNang_LPnMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_chucNang_LPnMouseClicked

    private void sanPhamClick_LblMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sanPhamClick_LblMouseClicked
        doiMauKhiClick(evt, sanPhamClick_Pn, sanPhamClick_Lbl, sanPham_Panel);
    }//GEN-LAST:event_sanPhamClick_LblMouseClicked

    private void sanPhamClick_PnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sanPhamClick_PnMouseClicked
        doiMauKhiClick(evt, sanPhamClick_Pn, sanPhamClick_Lbl, sanPham_Panel);
    }//GEN-LAST:event_sanPhamClick_PnMouseClicked

    private void nhanVienClick_LblMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_nhanVienClick_LblMouseClicked
        doiMauKhiClick(evt, nhanVienClick_Pn, nhanVienClick_Lbl, nhanVienPanel);
        nhanVienPanel.updateData();
    }//GEN-LAST:event_nhanVienClick_LblMouseClicked

    private void nhanVienClick_PnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_nhanVienClick_PnMouseClicked
        doiMauKhiClick(evt, nhanVienClick_Pn, nhanVienClick_Lbl, nhanVienPanel);
        nhanVienPanel.updateData();
    }//GEN-LAST:event_nhanVienClick_PnMouseClicked

    private void diaDiemClick_LblMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_diaDiemClick_LblMouseClicked
        doiMauKhiClick(evt, diaDiemClick_Pn, diaDiemClick_Lbl, new DiaDiemPanel());
        
    }//GEN-LAST:event_diaDiemClick_LblMouseClicked

    private void diaDiemClick_PnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_diaDiemClick_PnMouseClicked
        doiMauKhiClick(evt, diaDiemClick_Pn, diaDiemClick_Lbl, new DiaDiemPanel());
    }//GEN-LAST:event_diaDiemClick_PnMouseClicked

    private void thongKeClick_LblMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_thongKeClick_LblMouseClicked
        doiMauKhiClick(evt, thongKeClick_Pn, thongKeClick_Lbl, thongKePanel);
        thongKePanel.updateData();
    }//GEN-LAST:event_thongKeClick_LblMouseClicked

    private void thongKeClick_PnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_thongKeClick_PnMouseClicked
        doiMauKhiClick(evt, thongKeClick_Pn, thongKeClick_Lbl, thongKePanel);
        thongKePanel.updateData();
    }//GEN-LAST:event_thongKeClick_PnMouseClicked

    private void tuyChonClick_LblMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tuyChonClick_LblMouseClicked
        Util.doiPanel(chucNang_LPn, tuyChon2_Pn);
    }//GEN-LAST:event_tuyChonClick_LblMouseClicked

    private void tuyChonClick_PnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tuyChonClick_PnMouseClicked
        Util.doiPanel(chucNang_LPn, tuyChon2_Pn);
    }//GEN-LAST:event_tuyChonClick_PnMouseClicked

    private void thongTinCaNhanClick_LblMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_thongTinCaNhanClick_LblMouseClicked
        doiMauKhiClick(evt, thongTinCaNhanClick_Pn, thongTinCaNhanClick_Lbl, thongTinCaNhan_Pn);
    }//GEN-LAST:event_thongTinCaNhanClick_LblMouseClicked

    private void doiMatKhauClick_LblMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_doiMatKhauClick_LblMouseClicked
        doiMauKhiClick(evt, doiMatKhauClick_Pn, doiMatKhauClick_Lbl, doiMatKhau_Pn);
    }//GEN-LAST:event_doiMatKhauClick_LblMouseClicked

    private void thongTinCaNhanClick_PnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_thongTinCaNhanClick_PnMouseClicked
        doiMauKhiClick(evt, thongTinCaNhanClick_Pn, thongTinCaNhanClick_Lbl, thongTinCaNhan_Pn);
    }//GEN-LAST:event_thongTinCaNhanClick_PnMouseClicked

    private void doiMatKhauClick_PnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_doiMatKhauClick_PnMouseClicked
        doiMauKhiClick(evt, thongTinCaNhanClick_Pn, thongTinCaNhanClick_Lbl, thongTinCaNhan_Pn);
    }//GEN-LAST:event_doiMatKhauClick_PnMouseClicked

    private void quayLaiClick_LblMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_quayLaiClick_LblMouseClicked
        Util.doiPanel(chucNang_LPn, quanLy_Pn);
    }//GEN-LAST:event_quayLaiClick_LblMouseClicked

    private void dangXuatClick_LblMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dangXuatClick_LblMouseClicked
        Object source = evt.getSource();
        if (source instanceof JPanel)
        {
            dangXuatClick_Lbl.setBackground(Color.white);
            dangXuatClick_Pn.setBackground(Color.white);
            int luaChon = JOptionPane.showConfirmDialog(rootPane, "????ng xu???t?", "????ng xu???t", JOptionPane.YES_NO_OPTION);
            if (luaChon == JOptionPane.YES_OPTION)
            {
                this.dispose();
                DangNhap dangNhapFrm = new DangNhap();
                dangNhapFrm.setLocationRelativeTo(null);
                dangNhapFrm.setVisible(true);
            }
            else
            {
                dangXuatClick_Lbl.setBackground(null);
                dangXuatClick_Pn.setBackground(null);
            }
        }
        else if (source instanceof JLabel)
        {
            dangXuatClick_Lbl.setBackground(Color.white);
            dangXuatClick_Pn.setBackground(Color.white);
            int luaChon = JOptionPane.showConfirmDialog(rootPane, "????ng xu???t?", "????ng xu???t", JOptionPane.YES_NO_OPTION);
            if (luaChon == JOptionPane.YES_OPTION)
            {
                this.dispose();
                DangNhap dangNhapFrm = new DangNhap();
                dangNhapFrm.setLocationRelativeTo(null);
                dangNhapFrm.setVisible(true);
            }
            else
            {
                dangXuatClick_Lbl.setBackground(null);
                dangXuatClick_Pn.setBackground(null);                
            }         
        }

    }//GEN-LAST:event_dangXuatClick_LblMouseClicked

    private void chonTGCBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chonTGCBActionPerformed
        // TODO add your handling code here:
        taoDuLieuTongQuanPanel();
    }//GEN-LAST:event_chonTGCBActionPerformed

    private void chonTGCBPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_chonTGCBPropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_chonTGCBPropertyChange

    private void banHangClick_PnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_banHangClick_PnMouseClicked
        doiMauKhiClick(evt, banHangClick_Pn, banHangClick_Lbl, new BanHangPanel());
    }//GEN-LAST:event_banHangClick_PnMouseClicked

    private void banHangClick_LblMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_banHangClick_LblMouseClicked
        doiMauKhiClick(evt, banHangClick_Pn, banHangClick_Lbl, new BanHangPanel());
    }//GEN-LAST:event_banHangClick_LblMouseClicked

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
            java.util.logging.Logger.getLogger(TrangChu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TrangChu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TrangChu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TrangChu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TrangChu().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel CMND_Lbl;
    private javax.swing.JTextField CMND_TF;
    private javax.swing.JLabel SDT_Lbl;
    private javax.swing.JTextField SDT_TF;
    private javax.swing.JLabel avatar_Lbl;
    private javax.swing.JLabel banHangClick_Lbl;
    private javax.swing.JPanel banHangClick_Pn;
    private javax.swing.JLayeredPane chartLayeredPane;
    private javax.swing.JLabel chiPhiLb;
    private javax.swing.JComboBox<String> chonTGCB;
    private javax.swing.JLayeredPane chucNang_LPn;
    private javax.swing.JLabel chucVu_Lbl;
    private javax.swing.JTextField chucVu_TF;
    private javax.swing.JLabel dangXuatClick_Lbl;
    private javax.swing.JPanel dangXuatClick_Pn;
    private javax.swing.JLabel diaDiemClick_Lbl;
    private javax.swing.JPanel diaDiemClick_Pn;
    private javax.swing.JLabel doanhThuLb;
    private javax.swing.JLabel doiMatKhauClick_Lbl;
    private javax.swing.JPanel doiMatKhauClick_Pn;
    private javax.swing.JButton doiMatKhau_Btn;
    private javax.swing.JLabel doiMatKhau_Lbl;
    private javax.swing.JPanel doiMatKhau_Pn;
    private javax.swing.JLabel gioiTinh_Lbl;
    private javax.swing.JTextField gioiTinh_TF;
    private javax.swing.JLabel hienThiTenNguoiDung_Lbl;
    private javax.swing.JLabel hoVaTen_Lbl;
    private javax.swing.JTextField hoVaTen_TF;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel loiNhuanLb;
    private javax.swing.JLabel matKhauCu_Lbl;
    private javax.swing.JPasswordField matKhauCu_PwF;
    private javax.swing.JLabel matKhauMoi_Lbl;
    private javax.swing.JPasswordField matKhauMoi_PwF;
    private javax.swing.JLabel ngay_Lbl;
    private javax.swing.JLabel nhanVienClick_Lbl;
    private javax.swing.JPanel nhanVienClick_Pn;
    private javax.swing.JLabel nhapLaiMatKhauMoi_Lbl;
    private javax.swing.JPasswordField nhapLaiMatKhauMoi_PwF;
    private javax.swing.JLabel quanLyKhoClick_Lbl;
    private javax.swing.JPanel quanLyKhoClick_Pn;
    private javax.swing.JPanel quanLy_Pn;
    private javax.swing.JLabel quayLaiClick_Lbl;
    private javax.swing.JPanel quayLaiClick_Pn;
    private javax.swing.JLabel sanPhamClick_Lbl;
    private javax.swing.JPanel sanPhamClick_Pn;
    private javax.swing.JLabel tenTaiKhoan_Lbl;
    private javax.swing.JTextField tenTaiKhoan_TF;
    private javax.swing.JLabel thoiGian_Lbl;
    private javax.swing.JLabel thongKeClick_Lbl;
    private javax.swing.JPanel thongKeClick_Pn;
    private javax.swing.JLabel thongTinCaNhanClick_Lbl;
    private javax.swing.JPanel thongTinCaNhanClick_Pn;
    private javax.swing.JLabel thongTinCaNhan_Lbl;
    private javax.swing.JPanel thongTinCaNhan_Pn;
    private javax.swing.JPanel thongTinDangNhap_Pn;
    private javax.swing.JLayeredPane tinhNangCuaTuyChon_LPn;
    private javax.swing.JLabel trangChuClick_Lbl;
    private javax.swing.JPanel trangChuClick_Pn;
    private javax.swing.JLayeredPane trangChu_LPn;
    private javax.swing.JPanel trangChu_Pn;
    private javax.swing.JPanel tuyChon2_Pn;
    private javax.swing.JLabel tuyChonClick_Lbl;
    private javax.swing.JPanel tuyChonClick_Pn;
    private javax.swing.JPanel tuyChon_Pn;
    // End of variables declaration//GEN-END:variables
        
    public void anChucNang(JPanel panelClick, JLabel labelClick)
    {
        panelClick.setVisible(false);
        labelClick.setVisible(false);
    }
    
    public void xetChucVu(String chucVu)
    {
        if (chucVu.toLowerCase().contains("nh??n vi??n"))
        {
            anChucNang(quanLyKhoClick_Pn, quanLyKhoClick_Lbl);
            anChucNang(sanPhamClick_Pn, sanPhamClick_Lbl);
            anChucNang(nhanVienClick_Pn, nhanVienClick_Lbl);
            anChucNang(diaDiemClick_Pn, diaDiemClick_Lbl);
            anChucNang(thongKeClick_Pn, thongKeClick_Lbl);
            anChucNang(trangChuClick_Pn, trangChuClick_Lbl);
            
            Util.doiPanel(trangChu_LPn, new BanHangPanel());
            banHangClick_Pn.setBackground(Color.white);
        }else {
            anChucNang(banHangClick_Pn, banHangClick_Lbl);
        }
    }
      
    public void hienThiThongTinTaiKhoan(String tenDangNhap)
    {
        String query = "select * from NhanVien where tenTK = '" + tenDangNhap + "'";
        try
        {
            Connection connect = Util.getConnection();
            PreparedStatement ps = connect.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
            {
                tenTaiKhoan_TF.setText(rs.getString("tenTK"));
                chucVu_TF.setText(rs.getString("chucVu"));
                CMND_TF.setText(rs.getString("CMND"));
                SDT_TF.setText(rs.getString("SDT"));
                gioiTinh_TF.setText(rs.getString("gioiTinh"));
                if (gioiTinh_TF.getText().equals("Nam"))
                {
                    avatar_Lbl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/HinhAnh/man-avatar-profile-vector-21372065.jpg")));                    
                }
                else
                {
                    avatar_Lbl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/HinhAnh/woman-avatar-profile-vector-21372074.jpg")));
                }
                hoVaTen_TF.setText(rs.getString("tenNV"));
                rs.close();
                ps.close();
                connect.close();
            }
        }catch (SQLException ex)
        {
            System.out.println("Lay thong tin tai khoan that bai");
        }
    }
 
    
    public void doiMatKhau(String tenDangNhap, String matKhau)
    {
        matKhau = Util.hashing(matKhau);
        String query = "update NhanVien set matKhau = '" + matKhau + "' where tenTK = '" + tenDangNhap + "'";
        try
        {
            Connection connect = Util.getConnection();
            PreparedStatement ps = connect.prepareStatement(query);
            ps.executeUpdate();
            ps.close();
            connect.close();
        }catch (SQLException ex)
        {
            System.out.println("Doi mat khau that bai");
        }
    }
    
    public void doiMauKhiClick(java.awt.event.MouseEvent evt, JPanel panelClick, 
            JLabel labelClick, JPanel panelDisplay)
    {
        trangChuClick_Pn.setBackground(null);
        trangChuClick_Lbl.setBackground(null);
        
        quanLyKhoClick_Pn.setBackground(null);
        quanLyKhoClick_Lbl.setBackground(null);
        
        sanPhamClick_Pn.setBackground(null);
        sanPhamClick_Lbl.setBackground(null);
        
        nhanVienClick_Pn.setBackground(null);
        nhanVienClick_Lbl.setBackground(null);
        
        diaDiemClick_Pn.setBackground(null);
        diaDiemClick_Lbl.setBackground(null);
        
        thongKeClick_Pn.setBackground(null);
        thongKeClick_Lbl.setBackground(null);
        
        banHangClick_Pn.setBackground(null);
        banHangClick_Lbl.setBackground(null);
        
        tuyChonClick_Pn.setBackground(null);
        tuyChonClick_Lbl.setBackground(null);
        
        thongTinCaNhanClick_Pn.setBackground(null);
        thongTinCaNhanClick_Lbl.setBackground(null);
        
        doiMatKhauClick_Pn.setBackground(null);
        doiMatKhauClick_Lbl.setBackground(null);
        
        dangXuatClick_Pn.setBackground(null);
        dangXuatClick_Lbl.setBackground(null);        
        
        labelClick.setOpaque(true); 
        Object source = evt.getSource();
        if (source instanceof JPanel)
        {
            Util.doiPanel(trangChu_LPn, panelDisplay);
            panelClick.setBackground(Color.white);
//            labelClick.setBackground(Color.white);   
        }
        else if (source instanceof JLabel)
        {
            Util.doiPanel(trangChu_LPn, panelDisplay);
            panelClick.setBackground(Color.white);
//            labelClick.setBackground(Color.white);            
        }
    }
}