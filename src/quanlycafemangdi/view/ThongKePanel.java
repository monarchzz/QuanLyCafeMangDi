/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quanlycafemangdi.view;

import java.awt.Color;
import java.awt.Font;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import quanlycafemangdi.Util;
import quanlycafemangdi.data.Data;
import quanlycafemangdi.view.chart.ChartAdapter;
import quanlycafemangdi.view.chart.DatasetValue;
import quanlycafemangdi.view.chart.MChartPanel;

/**
 *
 * @author monar
 */
public class ThongKePanel extends javax.swing.JPanel {
    private final Data data;
    private String ngayHienTai;
    private String thangHienTai;
    private String namHienTai;
    private String namDauTien;
    
    private DefaultTableModel chiTietNamTableModel;
    private DefaultTableModel chiTietThangTableModel;
    private DefaultTableModel chiTietNgayTableModel;

    /**
     * Creates new form ThongKePanel
     */
    public ThongKePanel() {
        initComponents();
        
        data = Data.getInstance();
        ngayHienTai = DateTimeFormatter.ofPattern("dd").format(LocalDateTime.now());
        thangHienTai = DateTimeFormatter.ofPattern("MM").format(LocalDateTime.now());
        namHienTai = DateTimeFormatter.ofPattern("yyyy").format(LocalDateTime.now());
        
        initComboBox();
        initTable();
        initData();
    }
    
    private void initTable(){
        xetBang(chiTietTKNamTable);
        xetBang(chiTietTKThangTable);
        xetBang(chiTietTKNgayTable);
        
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
        
        chiTietTKNamTable.setModel(chiTietNamTableModel);
        chiTietTKThangTable.setModel(chiTietThangTableModel);
        chiTietTKNgayTable.setModel(chiTietNgayTableModel);
        
        
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
        for (int i = Integer.valueOf(namDauTien); i <= Integer.valueOf(namHienTai); i++){
            chiTietNamCB.addItem(String.valueOf(i));
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
    }
    
    private void initData(){
        taoDuLieuTongQuanPanel();
        taoDuLieuTKChiTiet();
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
        
        doanhThuLb.setText(String.valueOf((long)doanhThuList.get(0).getValue()));
        chiPhiLb.setText(String.valueOf((long)chiPhiList.get(0).getValue()));
        loiNhuanLb.setText(String.valueOf((long)loiNhuanList.get(0).getValue()));
        
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
        
        doanhThuLb.setText(String.valueOf((long)doanhThuList.get(0).getValue()));
        chiPhiLb.setText(String.valueOf((long)chiPhiList.get(0).getValue()));
        loiNhuanLb.setText(String.valueOf((long)loiNhuanList.get(0).getValue()));
        
        
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
        
        doanhThuLb.setText(String.valueOf((long)doanhThuList.get(0).getValue()));
        chiPhiLb.setText(String.valueOf((long)chiPhiList.get(0).getValue()));
        loiNhuanLb.setText(String.valueOf((long)loiNhuanList.get(0).getValue()));
        
        Collections.reverse(doanhThuList);
        Collections.reverse(chiPhiList);
        Collections.reverse(loiNhuanList);
        MChartPanel mChartPanel = new MChartPanel(new ChartAdapter(doanhThuList, chiPhiList, loiNhuanList).getChart("Năm"));
        
        Util.doiPanel(chartLayeredPane, mChartPanel);
    }
    
    private void taoDuLieuTKChiTiet(){
        capNhatBangTKNam();
        
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
        jPanel3 = new javax.swing.JPanel();

        setBackground(new java.awt.Color(255, 255, 255));

        thongKeTabPanel.setBackground(new java.awt.Color(32, 136, 203));
        thongKeTabPanel.setForeground(new java.awt.Color(255, 255, 255));
        thongKeTabPanel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        tongQuanPanel.setBackground(new java.awt.Color(255, 255, 255));
        tongQuanPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jPanel1.setBackground(new java.awt.Color(114, 102, 186));

        jLabel1.setBackground(new java.awt.Color(114, 102, 186));
        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Tổng quan");

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
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 166, Short.MAX_VALUE))
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
        jLabel12.setText("Chi phí");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE)
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

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel10.setText("Lợi nhuận");

        loiNhuanLb.setBackground(new java.awt.Color(255, 255, 255));
        loiNhuanLb.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N
        loiNhuanLb.setForeground(new java.awt.Color(255, 255, 255));
        loiNhuanLb.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        loiNhuanLb.setText("0");

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
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE))
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

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel2.setText("Thời gian");

        chonTGCB.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        chonTGCB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Theo ngày", "Theo tháng", "Theo năm" }));
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
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

        jPanel2.setBackground(new java.awt.Color(114, 102, 186));

        jLabel3.setBackground(new java.awt.Color(114, 102, 186));
        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Thống kê chi tiết");

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
        jPanel10.setLayout(new java.awt.GridLayout());

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel4.setText("Năm");

        chiTietNamCB.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        chiTietNamCB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chiTietNamCBActionPerformed(evt);
            }
        });

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
        chiTietTKNamTable.setSelectionBackground(new java.awt.Color(153, 153, 255));
        chiTietTKNamTable.setSelectionForeground(new java.awt.Color(254, 254, 254));
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
                .addContainerGap(195, Short.MAX_VALUE))
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
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 324, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel10.add(jPanel11);

        jPanel13.setBackground(new java.awt.Color(255, 255, 255));

        chiTietThangCB.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        chiTietThangCB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chiTietThangCBActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel7.setText("Tháng");

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
                .addContainerGap(173, Short.MAX_VALUE))
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
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 324, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel10.add(jPanel13);

        jPanel14.setBackground(new java.awt.Color(255, 255, 255));

        chiTietNgayCB.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        chiTietNgayCB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chiTietNgayCBActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel11.setText("Ngày");

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
                .addGap(55, 191, Short.MAX_VALUE))
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
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 324, Short.MAX_VALUE)
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

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 900, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 497, Short.MAX_VALUE)
        );

        thongKeTabPanel.addTab("tab3", jPanel3);

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


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLayeredPane chartLayeredPane;
    private javax.swing.JLabel chiPhiLb;
    private javax.swing.JComboBox<String> chiTietNamCB;
    private javax.swing.JComboBox<String> chiTietNgayCB;
    private javax.swing.JPanel chiTietPanel;
    private javax.swing.JTable chiTietTKNamTable;
    private javax.swing.JTable chiTietTKNgayTable;
    private javax.swing.JTable chiTietTKThangTable;
    private javax.swing.JComboBox<String> chiTietThangCB;
    private javax.swing.JComboBox<String> chonTGCB;
    private javax.swing.JLabel doanhThuLb;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
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
    private javax.swing.JPanel jPanel2;
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
    private javax.swing.JLabel loiNhuanLb;
    private javax.swing.JTabbedPane thongKeTabPanel;
    private javax.swing.JPanel tongQuanPanel;
    // End of variables declaration//GEN-END:variables
}
