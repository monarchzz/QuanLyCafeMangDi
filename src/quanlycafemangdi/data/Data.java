/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quanlycafemangdi.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import quanlycafemangdi.Util;
import quanlycafemangdi.model.BanHang;
import quanlycafemangdi.model.CaLamViec;
import quanlycafemangdi.model.CongThuc;
import quanlycafemangdi.model.DangKi;
import quanlycafemangdi.model.DiaDiem;
import quanlycafemangdi.model.NhanVien;
import quanlycafemangdi.model.SanPham;

/**
 *
 * @author monar
 */
public class Data {
    
    private static Data INSTANCE ;
    private final Connection connection;
    
    private Data(){
        connection = Util.getConnection();
    }
    
    public static Data getInstance(){
        if (INSTANCE == null){
            INSTANCE = new Data();
            
        }
        return INSTANCE;
    }
    
    
    public boolean kiemTraDangNhap(String tenDangNhap, String matKhau)
    {
        String query = "select * from NhanVien where tenTK = '" + tenDangNhap + "' and matKhau = '" + matKhau + "'";
        try
        {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
            {
                return true;
            }
        }catch (SQLException ex)
        {
            System.out.println("Truy van that bai");
        }
        return false;
    }
    
    public String layChucVu(String tenDangNhap, String matKhau)
    {
        String query = "select * from NhanVien where tenTK = '" + tenDangNhap + "' and matKhau = '" + matKhau + "'";
        try
        {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
            {
                return rs.getString("chucVu");
            }
        }catch (SQLException ex)
        {
            System.out.println("Truy van that bai");
            Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public String layTenNguoiDung(String tenTK){
        String query = "select tenNV from NhanVien  where tenTK = '" + tenTK + "'";
        
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                return rs.getString("tenNV");
            }
        } catch (SQLException ex) {
            System.out.println("Truy van that bai");
            Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    //
            
    public List<NhanVien> layDSNhanVien(){
        ArrayList<NhanVien> list = new ArrayList<>();
        
        String query = "select * from NhanVien";
            try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                String trangThai = rs.getString("trangThai");
                if (trangThai.equals("1")){
                    String tenTk = rs.getString("tenTK");
                    String chucVu = rs.getString("chucVu");
                    String cmnd = rs.getString("cmnd");
                    String sdt = rs.getString("sdt");
                    String gioiTinh = rs.getString("gioiTinh");
                    String tenNV = rs.getString("tenNV");
                    String matKhau = rs.getString("matKhau");
                    list.add(new NhanVien(tenTk,chucVu,cmnd,sdt,gioiTinh,tenNV,matKhau));
                }
                
            }
        } catch (SQLException e) {
            Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, e);
        }
        
        return list;
    }
    
    public List<SanPham> layDSSanPham(){
        ArrayList<SanPham> list = new ArrayList<>();
        
        String query = "select Sp.maSP, SP.tenSP, SP.gia, Dv.tenDV " 
                + "from DonViTinh as DV, SanPham as SP " 
                + "where SP.maDV = Dv.maDV";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                String maSP = rs.getString("maSP");
                String tenSP = rs.getString("tenSP");
                
                String giaTien = rs.getString("gia");
                giaTien = giaTien.substring(0,giaTien.indexOf("."));
                long gia = Long.valueOf(giaTien);
                
                String donVi = rs.getString("tenDV");
                
                SanPham sp = new SanPham(maSP,tenSP,donVi,gia);
                list.add(sp);
                
            }
        } catch (SQLException e) {
            Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, e);
        }
        
        return list;
    }
    
    //lay ds Ban Hang
    public List<BanHang> layDSBanHang(){
        ArrayList<BanHang> list = new ArrayList<>();
        
        String query = "select * from BanHang";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                String maBH = rs.getString("maBH");
                String tenTK = rs.getString("tenTK");
                String tg = rs.getString("tg");
                String maCLV = rs.getString("maCLV");
                
                String tien = rs.getString("tongTien");
                tien = tien.substring(0, tien.indexOf("."));
                long tongTien = Long.valueOf(tien);
                
                // lay thong tin san pham va so luong san pham
                HashMap<String,Integer> mMap = new HashMap<>();
                String query2 = "select * from ChiTietBH where maBH = '" + maBH + "'";
                PreparedStatement ps2 = connection.prepareStatement(query2);
                ResultSet rs2 = ps2.executeQuery();
                while(rs2.next()){
                    String maSp = rs2.getString("maSP");
                    int soLuong = Integer.valueOf(rs2.getString("soLuong"));
                    mMap.put(maSp, soLuong);
                }
                //
                
                list.add(new BanHang(maBH,tenTK,maCLV,tg,tongTien,mMap));
                
            }
        } catch (SQLException e) {
            Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, e);
        }
        
        return list;
    }
    
    // lay sanh sach cong thuc
    public List<CongThuc> layDSCongThuc(){
        ArrayList<CongThuc> list = new ArrayList<>();
        
        String query = "select * from CongThuc";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                String maCT = rs.getString("maCT");
                String maSP = rs.getString("maSP");
                String cachLam = rs.getString("cachLam");
                
                HashMap<String,Integer> mMap = new HashMap<>();
                String query2 = "select * from ChiTietCT where maCT = '" + maCT + "'";
                PreparedStatement ps2 = connection.prepareStatement(query2);
                ResultSet rs2 = ps2.executeQuery();
                while(rs2.next()){
                    String maNL = rs2.getString("maNL");
                    int soLuong = Integer.valueOf(rs2.getString("soLuong"));
                    mMap.put(maNL, soLuong);
                }
                list.add(new CongThuc(maCT,maSP,cachLam,mMap));
            }
                
            
        } catch (SQLException e) {
            Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, e);
        }
        
        return list;
    }
    
    // lay ds dia diem
    public List<DiaDiem> layDSDiaDiem(){
        ArrayList<DiaDiem> list = new ArrayList<>();
        
        String query = "select * from DiaDiem";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                if (rs.getString("trangThai").equals("1")){
                    String maDD = rs.getString("maDD");
                    String viTri = rs.getString("viTri");
                    
                    list.add(new DiaDiem(maDD,viTri));
                }
                
            }
                
            
        } catch (SQLException e) {
            Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, e);
        }
        
        return list;
        
    }
    
    //lay danh sach ca lam viec
    public List<CaLamViec> layDSCaLamViec(){
        ArrayList<CaLamViec> list = new ArrayList<>();
        
        String query = "select * from CaLamViec";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                CaLamViec clv = new CaLamViec();
                clv.setMaCLV(rs.getString("maCLV"));
                clv.setMaDD(rs.getString("maDD"));
                clv.setCaLamViec(rs.getString("caLamViec"));
                clv.setNgay(rs.getString("ngay"));
                clv.setTK1(rs.getString("TK1"));
                if (rs.getString("TK2") != null){
                    clv.setTK2(rs.getString("TK2"));
                }
                
                list.add(clv);
                
            }
                
            
        } catch (SQLException e) {
            Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, e);
        }
        
        return list;
        
    }
    // lay danh sach dang ki
    // tra ve maNl va soLuong
    public HashMap<String, Integer> layDSDangKiTheoCa(String maCLV){ 
        HashMap<String, Integer> mHashMap = new HashMap<>();
        
        String query = "select maNL, SUM(soLuong) as soLuong from ChiTietDK where maDK in "
                + "( select maDK from DangKi where maCLV = '" + maCLV + "') "
                + "group by maNL";
        
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                mHashMap.put(rs.getString("maNL"), rs.getInt("soLuong"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, ex);
        }
        return mHashMap;
    }
    
    //doi san pham ra nguyen lieu theo ca
    // tra ve maSP va soLuong
     public HashMap<String, Integer> layDSBanHangTheoCa(String maCLV){ 
        HashMap<String, Integer> mHashMap = new HashMap<>();
        
        String query = "select maSP, SUM(soLuong) as soLuong from ChiTietBH where maBH in "
                + "(select BanHang.maBH from BanHang where BanHang.maCLV = '" + maCLV + "') "
                + "group by maSP";
        
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                mHashMap.put(rs.getString("maSP"), rs.getInt("soLuong"));
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, ex);
        }
        return mHashMap;
    }
    
    public List<String> layDSMaDK(){
        ArrayList<String> mArrayList = new ArrayList<>();
        
        String query = "select maDK from DangKi";
        
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
               
                mArrayList.add(rs.getString("maDK"));
                
            }
                
            
        } catch (SQLException e) {
            Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, e);
        }
        
        return  mArrayList;
        
    }
    
    // 
    
    // lay du lieu tu id 
    public String layTenNguyenLieu(String id){
        
        String query = "select tenNL, maDV from NguyenLieu where maNL = '" + id + "'";
        
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                return layDonVi(rs.getString("maDV")) + " " + rs.getString("tenNL");
            }
        } catch (SQLException ex) {
            
            Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return null;
    }
    //
    public HashMap<String, Integer> laySoLuongNL(){
        HashMap<String,Integer> mHashMap = new HashMap<>();
        
        String query = "select maNL, soLuong from NguyenLieu";
        
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                mHashMap.put(rs.getString("maNL"), rs.getInt("soLuong"));
            }
        } catch (SQLException ex) {
            
            Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return mHashMap;
    }
    
    public String layDonVi(String id){
        String query = "select tenDV from DonViTinh where maDV = '" + id + "'";
        
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                return rs.getString("tenDV");
            }
        } catch (SQLException ex) {
            
            Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return null;
    }

    // kiem tra so chung minh
    public boolean kiemTraSCM(String cmnd) {
        
        
        String query = "select cmnd from NhanVien";
        
        
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                if (cmnd.equals(rs.getString("cmnd"))){
                    return false;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return true;
    }

    
    // kiem tra ten dang nhap
    public boolean kiemTraTDN(String tdn) {
        
        
        String query = "select tenTK from NhanVien";
        
        
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                if (tdn.equals(rs.getString("tenTK"))){
                    return false;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return true;
    }
    
    public void taoNhanVien(NhanVien nhanVien){
        String query = "insert into NhanVien values(?,?,?,?,?,?,?,?)";
        
        
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, nhanVien.getTenTk());
            ps.setString(2, nhanVien.getMatKhau());
            ps.setString(3, nhanVien.getChucVu());
            ps.setString(4, nhanVien.getSoCM());
            ps.setString(5, nhanVien.getSdt());
            ps.setString(6, nhanVien.getGioiTinh());
            ps.setString(7, nhanVien.getTenNhanVien());
            ps.setString(8, "1");
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    // xet gia tri trang thai = 0
    public void xoaNhanVien(NhanVien nhanVien){
        try {
            String query = "update NhanVien set trangThai = '0' where tenTK = '"
                    + nhanVien.getTenTk() + "'";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.execute();
        } catch (SQLException ex) {
            Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // sua thong tin nhan vien
    public void suaThongTinNhanVien(NhanVien thongTinNVCu, NhanVien thongTinNVMoi){
        try {
            // xoa nhan vien
            String query = "delete from NhanVien where tenTK = '"
                    + thongTinNVCu.getTenTk() + "'";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.execute();
            
        } catch (SQLException ex) {
            Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // tao nhan vien
        taoNhanVien(thongTinNVMoi);
    }
    
    // tao hoa don ban hang
    public void taoHoaDonBanHang(BanHang bh){
        
        // them vao bang BanHang
        String query = "insert into BanHang values(?,?,?,?,?)";
        
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, bh.getMaBH());
            ps.setString(2, bh.getTenTK());
            ps.setString(3, bh.getMaCLV());
            ps.setString(4, bh.getTg());
            ps.setLong(5, bh.getTongTien());
            
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // them vao Bang ChiTietBanHang
        query = "insert into ChiTietBH values(?,?,?)";
        try {
            Set<String> keySet = bh.getDsSanPham().keySet();
            for (String item : keySet){
                PreparedStatement ps = connection.prepareStatement(query);
                ps.setString(1, bh.getMaBH());
                ps.setString(2, item);
                ps.setInt(3, bh.getDsSanPham().get(item));
                ps.executeUpdate();
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void themDangKi(DangKi dk){
        String query = "insert into DangKi values(?,?,?,?)";
        
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, dk.getMaDK());
            ps.setString(2, dk.getMaCLV());
            ps.setString(3, dk.getThoiGian());
            ps.setString(4, dk.getGhiChu());
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, ex);
        }
        // them vao Bang ChiTietDangKi
        query = "insert into ChiTietDK values(?,?,?)";
        try {
            Set<String> keySet = dk.getChiTietDangKi().keySet();
            for (String item : keySet){
                PreparedStatement ps = connection.prepareStatement(query);
                ps.setString(1, dk.getMaDK());
                ps.setString(2, item);
                ps.setInt(3, dk.getChiTietDangKi().get(item));
                ps.executeUpdate();
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //DIA DIEM
//    public List<DiaDiem> layDSDiaDiem(){
//        ArrayList<DiaDiem> list = new ArrayList<>();
//        
//        String sql = "select * from DiaDiem";
//        try {
//            PreparedStatement ps = connection.prepareStatement(sql);
//            ResultSet rs = ps.executeQuery();
//            while(rs.next()){
//                int trangThai = rs.getInt("trangThai");
//                
//                if (trangThai == 1){
//                    String maDD = rs.getString("maDD");
//                    String viTri = rs.getString("viTri");
//                    list.add(new DiaDiem(maDD, viTri));
//                }
//            }
//        } catch (SQLException e) {
//            Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, e);
//        }
//        return list;
//    }

    //kiem tra ma dia diem
    public boolean kiemTraMaDD(String maDD) {
        String sql = "select maDD from DiaDiem";
                
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                if (maDD.equals(rs.getString("maDD"))){
                    return false;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }
    
    //kiem tra vi tri dia diem
    public boolean kiemTraViTri(String viTri) {
        String sql = "select viTri from DiaDiem";
                
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                if (viTri.equals(rs.getString("viTri"))){
                    return false;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    public void themDiaDiem(DiaDiem diaDiem){
        String sql = "insert into DiaDiem values(?,?,?)";
        
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, diaDiem.getMaDD());
            ps.setString(2, diaDiem.getViTri());
            ps.setInt(3, 1);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //trang thai 1: hoat dong, 0: nghỉ
    public void xoaDiaDiem(DiaDiem diaDiem){
        String sql = "update DiaDiem set trangThai = '0' where maDD = '" + diaDiem.getMaDD() + "'";
        
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void suaDiaDiem(DiaDiem ddCu, DiaDiem ddMoi){
        String sql = "delete from DiaDiem where maDD = '" + ddCu.getMaDD() + "'";
        
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, ex);
        }
  
        themDiaDiem(ddMoi);
    }
 
    //CA LAM VIEC
//    public List<CaLamViec> layDSCaLamViec(){
//        ArrayList<CaLamViec> list = new ArrayList<>();
//         
//        String sql = "select * from CaLamViec";
//        try {
//            PreparedStatement ps = connection.prepareStatement(sql);
//            ResultSet rs = ps.executeQuery();
//            while(rs.next()){
//                String maCLV = rs.getString("maCLV");
//                String maDD = rs.getString("maDD");
//                String caLamViec = rs.getString("caLamViec");
//                String ngayLam = rs.getString("ngay");
//                String tk1 = rs.getString("tk1");
//                String tk2 = rs.getString("tk2");
//                list.add(new CaLamViec(maCLV, maDD, caLamViec, ngayLam, tk1, tk2));
//            }
//        } catch (SQLException e) {
//            Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, e);
//        }
//        return list;
//    }  
//    
    
    //kiem tra ma ca lam viec
    public boolean kiemTraMaCLV(String maCLV) {
        String sql = "select maCLV from CaLamViec";
                
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                if (maCLV.equals(rs.getString("maCLV"))){
                    return false;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }
    
     public void themCaLamViec(CaLamViec caLamViec){
        String sql = "insert into CaLamViec values(?,?,?,?,?,?)";
        
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, caLamViec.getMaCLV());
            ps.setString(2, caLamViec.getMaDD());
            ps.setString(3, caLamViec.getCaLamViec());
            ps.setString(4, caLamViec.getNgay());
            ps.setString(5, caLamViec.getTK1());
            ps.setString(6, caLamViec.getTK2());
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void xoaCaLamViec(CaLamViec caLamViec){
        String sql = "delete from CaLamViec where maCLV = '" + caLamViec.getMaCLV() + "'" ;
        
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void suaThongTinCaLamViec(CaLamViec clvCu, CaLamViec clvMoi){
        String sql = "delete from CaLamViec where maCLV = '" + clvCu.getMaCLV() + "'";
        
        try {         
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, ex);
        }
     
        themCaLamViec(clvMoi);
    }
}
