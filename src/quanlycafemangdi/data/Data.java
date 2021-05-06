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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
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
import quanlycafemangdi.model.Luong;
import quanlycafemangdi.model.NhanVien;
import quanlycafemangdi.model.NhapXuat;
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
    
    
    public boolean kiemTraDangNhap(String tenDangNhap, String matKhau){
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
    
    public String layChucVu(String tenDangNhap, String matKhau){
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
                }else {
                    clv.setTK2(null);
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
    // tra ve key = maNl, value = soLuong
    // danh sach tra nguyen lieu ve kho
    public HashMap<String, Integer> layDSTraNLTheoCa(CaLamViec clv){ 
        HashMap<String, Integer> mHashMap = new HashMap<>();
        
        String tgBD;
        String tgKT;
        
        if (clv.getCaLamViec().equals("Sáng")){
            tgBD = clv.getNgay() + " 00:00:01";
            tgKT = clv.getNgay() + " 12:00:01";
        }else {
            tgBD = clv.getNgay() + " 12:00:00";
            tgKT = clv.getNgay() + " 23:59:59";
        }
        
        String query = "select maNL, SUM(soLuong) as soLuong from ChiTietNhapXuat  "
                + "where maNX in (select NhapXuat.maNX from NhapXuat "
                + "where tg > '" + tgBD + "' and tg < '" + tgKT + "' "
                + "and NhapXuat.trangThai = '2') group by maNL";
        
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                if (rs.getString("maNL") != null)
                    mHashMap.put(rs.getString("maNL"), rs.getInt("soLuong"));
                
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
    
    // lay ds ma cham luong
    public List<String> layDSMaCL(){
        ArrayList<String> mArrayList = new ArrayList<>();
        
        String query = "select maLuong from ChamLuong";
        
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                
                mArrayList.add(rs.getString("maLuong"));
                
            }
                
            
        } catch (SQLException e) {
            Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, e);
        }
        
        return  mArrayList;
    }
    // lay ds ma nhap xuat
    
    public List<String> layDSMaXuat(){
        ArrayList<String> mArrayList = new ArrayList<>();
        
        String query = "select maNX from NhapXuat where LEFT(NhapXuat.MaNX,1) = 'X'";
        
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                
                mArrayList.add(rs.getString("maNX"));
                
            }
                
            
        } catch (SQLException e) {
            Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, e);
        }
        
        return  mArrayList;
    }
    public List<String> layDSMaTra(){
        ArrayList<String> mArrayList = new ArrayList<>();
        
        String query = "select maNX from NhapXuat where LEFT(NhapXuat.MaNX,1) = 'T'";
        
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                
                mArrayList.add(rs.getString("maNX"));
                
            }
                
            
        } catch (SQLException e) {
            Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, e);
        }
        
        return  mArrayList;
    }
    
    // lau ds luong
    public List<Luong> layDSLuong(){
        ArrayList<Luong> mArrayList = new ArrayList<>();
        
        String query = "select ChamLuong.*, NhanVien.tenNV, NhanVien.cmnd , NhanVien.chucVu "
                + "from ChamLuong,NhanVien where ChamLuong.tenTK = NhanVien.tenTK";
        
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                String maLuong = rs.getString("maLuong");
                String tenTK = rs.getString("tenTK");
                String tenNV = rs.getString("tenNV");
                String cmnd = rs.getString("cmnd");
                String thangNam = rs.getString("thangNam");
                int luongCoBan = rs.getInt("luongCoBan");
                float heSoLuong = rs.getFloat("heSoLuong");
                int soCaLam = rs.getInt("soCaLam");
                
                String luong = rs.getString("thanhTien");
                long tienLuong = Long.valueOf(luong.substring(0,luong.indexOf(".")));
                
                int trangThai = rs.getInt("trangThai");
                
                String chucVu = rs.getString("chucVu");
                
                Luong luongNV = new Luong(maLuong, tenTK, tenNV, cmnd, thangNam, 
                        luongCoBan, heSoLuong, soCaLam, tienLuong, trangThai, chucVu);
                mArrayList.add(luongNV);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return mArrayList;
    }
    
    // 
    public List<DangKi> layDSDangKi(){
        ArrayList<DangKi> mArrayList = new ArrayList<>();
        
        String query = "select * from DangKi";
        
        try {
            PreparedStatement ps1 = connection.prepareStatement(query);
            ResultSet rs1 = ps1.executeQuery();
            
            while (rs1.next()){
                if (rs1.getString("maDK") != null){
                    DangKi dk = new DangKi();
                    
                    dk.setMaDK(rs1.getString("maDK"));
                    dk.setMaCLV(rs1.getString("maCLV"));
                    dk.setThoiGian(rs1.getString("tg"));
                    dk.setGhiChu(rs1.getString("ghiChu"));
                    
                    String query1 = "select maNL,soLuong from ChiTietDK where maDK = '"
                            + dk.getMaDK() + "'";
                    
                    HashMap<String,Integer> mHashMap = new HashMap<>();
                    PreparedStatement ps2 = connection.prepareStatement(query1);
                    ResultSet rs2 = ps2.executeQuery();
                    while(rs2.next()){
                        mHashMap.put(rs2.getString("maNL"), rs2.getInt("soLuong"));
                    }
                    
                    dk.setChiTietDangKi(mHashMap);
                    
                    mArrayList.add(dk);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return mArrayList;
    }
    
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
    
    public long doanhThuTheoNgay(String ngay){
        String ngayBT = ngay + " 00:00:00";
        String ngayKT = ngay + " 23:59:59";
        String query = "select SUM(tongTien) as tongTien from BanHang where tg < '" 
                + ngayKT + "' and tg > '" + ngayBT +"'";
        
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()){
                String tien = rs.getString("tongTien");
                if (tien != null)
                    return Long.valueOf(tien.substring(0,tien.indexOf(".")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return 0;
    }
    
    public long chiPhiTheoNgay(String ngay){
        String ngayBT = ngay + " 00:00:00";
        String ngayKT = ngay + " 23:59:59";
        
        // lay gia tien cua nguyen lieu
        HashMap<String, Integer> giaTienMap = new HashMap<>();
        String query = "select maNL,gia from NguyenLieu";
        PreparedStatement ps;
        try {
            ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                String giaTien = rs.getString("gia");
                if (giaTien!= null ){
                    giaTienMap.put(rs.getString("maNL"), Integer.valueOf(giaTien.substring(0,giaTien.indexOf("."))));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        query = "select maNL,SUM(soLuong) as soLuong from ChiTietNhapXuat " 
                + "where maNX in (select maNX from NhapXuat where tg > '" 
                + ngayBT + "' and tg < '" + ngayKT + "' and trangThai = 0) " 
                + "group by maNL";
        try {
            ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            long giaTien = 0;
            while (rs.next()){
                if (rs.getString("maNL") != null){
                    int soLuong = rs.getInt("soLuong");
                    giaTien += giaTienMap.get(rs.getString("maNL")) * soLuong;
                }
            }
            
            return giaTien;
        } catch (SQLException ex) {
            Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return 0;
    }
    public long doanhThuTheoThang(String bt, String kt){
        String ngayBT = bt + " 00:00:00";
        String ngayKT = kt + " 23:59:59";
        String query = "select SUM(tongTien) as tongTien from BanHang where tg < '" 
                + ngayKT + "' and tg > '" + ngayBT +"'";
        
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()){
                String tien = rs.getString("tongTien");
                if (tien != null)
                    return Long.valueOf(tien.substring(0,tien.indexOf(".")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return 0;
    }
    public long chiPhiTheoThang(String bt, String kt){
        String ngayBT = bt + " 00:00:00";
        String ngayKT = kt + " 23:59:59";
        
        // lay gia tien cua nguyen lieu
        HashMap<String, Integer> giaTienMap = new HashMap<>();
        String query = "select maNL,gia from NguyenLieu";
        PreparedStatement ps;
        try {
            ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                String giaTien = rs.getString("gia");
                if (giaTien!= null ){
                    giaTienMap.put(rs.getString("maNL"), Integer.valueOf(giaTien.substring(0,giaTien.indexOf("."))));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        query = "select maNL,SUM(soLuong) as soLuong from ChiTietNhapXuat " 
                + "where maNX in (select maNX from NhapXuat where tg > '" 
                + ngayBT + "' and tg < '" + ngayKT + "' and trangThai = 0) " 
                + "group by maNL";
        try {
            ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            long giaTien = 0;
            while (rs.next()){
                if (rs.getString("maNL") != null){
                    int soLuong = rs.getInt("soLuong");
                    giaTien += giaTienMap.get(rs.getString("maNL")) * soLuong;
                }
            }
            
            return giaTien;
        } catch (SQLException ex) {
            Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return 0;
    }
    
    public long doanhThuTheoNam(String nam){
        String ngayBT = nam + "-01-01 00:00:00";
        String ngayKT = nam + "-12-31 23:59:59";
        String query = "select SUM(tongTien) as tongTien from BanHang where tg < '" 
                + ngayKT + "' and tg > '" + ngayBT +"'";
        
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()){
                String tien = rs.getString("tongTien");
                if (tien != null)
                    return Long.valueOf(tien.substring(0,tien.indexOf(".")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return 0;
    }
    
    public long chiPhiTheoNam(String nam){
        String ngayBT = nam + "-01-01 00:00:00";
        String ngayKT = nam + "-12-31 23:59:59";
        
        // lay gia tien cua nguyen lieu
        HashMap<String, Integer> giaTienMap = new HashMap<>();
        String query = "select maNL,gia from NguyenLieu";
        PreparedStatement ps;
        try {
            ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                String giaTien = rs.getString("gia");
                if (giaTien!= null ){
                    giaTienMap.put(rs.getString("maNL"), Integer.valueOf(giaTien.substring(0,giaTien.indexOf("."))));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        query = "select maNL,SUM(soLuong) as soLuong from ChiTietNhapXuat " 
                + "where maNX in (select maNX from NhapXuat where tg > '" 
                + ngayBT + "' and tg < '" + ngayKT + "' and trangThai = 0) " 
                + "group by maNL";
        try {
            ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            long giaTien = 0;
            while (rs.next()){
                if (rs.getString("maNL") != null){
                    int soLuong = rs.getInt("soLuong");
                    giaTien += giaTienMap.get(rs.getString("maNL")) * soLuong;
                }
            }
            
            return giaTien;
        } catch (SQLException ex) {
            Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return 0;
    }
    public int layNamDauTien(){
        String query = "select MIN(BanHang.tg) as tg1, MIN(NhapXuat.tg) as tg2 from BanHang, NhapXuat";
        
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                String tg1 = rs.getString("tg1");
                String tg2 = rs.getString("tg2");
                if (tg1 == null && tg2 == null){
                    return -1;
                }
                if (tg1 != null && tg2 != null){
                    tg1 = tg1.substring(0, tg1.indexOf("-"));
                    tg2 = tg2.substring(0, tg2.indexOf("-"));
                    
                    if (tg1.compareTo(tg2) > 0)    
                        return Integer.valueOf(tg2);
                    else return Integer.valueOf(tg1);
                }
                if (tg1 != null){
                    return Integer.valueOf(tg1.substring(0, tg1.indexOf("-")));
                }else {
                    return Integer.valueOf(tg2.substring(0, tg2.indexOf("-")));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return -1;
    }
    public void capNhatLuong(Luong l){
        String query = "DELETE FROM ChamLuong WHERE maLuong = '" 
                + l.getMaLuong() 
                + "' INSERT INTO ChamLuong VALUES(?,?,?,?,?,?,?,?)";
        
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, l.getMaLuong());
            ps.setString(2, l.getTenTK());
            ps.setString(3, l.getThangNam());
            ps.setInt(4, l.getLuongCoBan());
            ps.setInt(5, l.getSoCaLam());
            ps.setFloat(6, l.getHeSoLuong());
            ps.setString(7, String.valueOf(l.getLuong()));
            ps.setInt(8, l.getTrangThai());
            
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void taoLuong(Luong l) {
        String query = "INSERT INTO ChamLuong VALUES(?,?,?,?,?,?,?,?)";
        
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, l.getMaLuong());
            ps.setString(2, l.getTenTK());
            ps.setString(3, l.getThangNam());
            ps.setInt(4, l.getLuongCoBan());
            ps.setInt(5, l.getSoCaLam());
            ps.setFloat(6, l.getHeSoLuong());
            ps.setString(7, String.valueOf(l.getLuong()));
            ps.setInt(8, l.getTrangThai());
            
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // tim thang chua nhap luong
    public boolean ktThemThangLuong(String tg){
        
        String query = "select COUNT(thangNam) as soLuong from ChamLuong where thangNam = '" + tg + "'";
        
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()){
                int sl = rs.getInt("soLuong");
                if (sl == 0){
                    return false;
                }
                return true;
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return true;
    }
    
    // lay ds thangnam ca lam viec
    public List<String> layDSThangNamCLV(){
        String query = "select ngay from CaLamViec";
        
        Set<String> mSet = new HashSet<>();
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            
            String tg  = DateTimeFormatter.ofPattern("yyyy").format(LocalDateTime.now());
            
            while(rs.next()){
                String tg1 = rs.getString("ngay");
                if (tg1 != null && tg1.contains(tg)){
                    tg1 = tg1.substring(0,7);
                    mSet.add(tg1);
                }
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        ArrayList<String> mList = new ArrayList<>(mSet);
        return mList;
    }
    
    // lay chi tiet ban hang theo ma ban hang
    public HashMap<String, String> chiTietBanHang(String maBH){
        HashMap<String,String> mHashMap = new HashMap<>();
        
        String query = "select SanPham.tenSP, ChiTietBH.soLuong, DonViTinh.tenDV from SanPham, "
                + "ChiTietBH, DonViTinh where SanPham.maSP = ChiTietBH.maSP "
                + "and ChiTietBH.maBH = '" + maBH + "' "
                + "and SanPham.maDV = DonViTinh.maDV";
        
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                if (rs.getString("tenSP") != null){
                    mHashMap.put(rs.getString("tenSP"), rs.getInt("soLuong") + " " + rs.getString("tenDV"));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return mHashMap;
    }
    public void xuatNL(NhapXuat nx){
        String query = "insert into NhapXuat values (?,?,?,?,?)";
        try
        {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, nx.getMaNhapXuat());
            ps.setString(2, nx.getTaiKhoan());
            ps.setTimestamp(3, nx.getThoiGian());
            ps.setString(4, "1");
            ps.setString(5, nx.getGhiChu());
            ps.executeUpdate();
            
            Set<String> keySet = nx.getChiTietNhapXuat().keySet();
            for (String key: keySet)
            {
                String query2 = "insert into ChiTietNhapXuat values (?,?,?)"
                            +   " update NguyenLieu"
                            +   "  set SoLuong = SoLuong - " + nx.getChiTietNhapXuat().get(key)
                            +   "  where maNL = '" + key + "'";
                ps = connection.prepareStatement(query2);
                ps.setString(1, nx.getMaNhapXuat());
                ps.setString(2, key);
                ps.setInt(3, nx.getChiTietNhapXuat().get(key));
                ps.executeUpdate();
                
                
            }
            ps.close();
        }catch (SQLException ex)
        {
            System.out.println("Nhap nguyen lieu SQL that bai");
            Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void traNL(NhapXuat nx){
        String query = "insert into NhapXuat values (?,?,?,?,?)";
        try
        {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, nx.getMaNhapXuat());
            ps.setString(2, nx.getTaiKhoan());
            ps.setTimestamp(3, nx.getThoiGian());
            ps.setString(4, "2");
            ps.setString(5, nx.getGhiChu());
            ps.executeUpdate();
            
            Set<String> keySet = nx.getChiTietNhapXuat().keySet();
            for (String key: keySet)
            {
                String query2 = "insert into ChiTietNhapXuat values (?,?,?)"
                            +   " update NguyenLieu"
                            +   "  set SoLuong = SoLuong + " + nx.getChiTietNhapXuat().get(key)
                            +   "  where maNL = '" + key + "'";
                ps = connection.prepareStatement(query2);
                ps.setString(1, nx.getMaNhapXuat());
                ps.setString(2, key);
                ps.setInt(3, nx.getChiTietNhapXuat().get(key));
                ps.executeUpdate();
                
                
            }
            ps.close();
        }catch (SQLException ex)
        {
            System.out.println("Nhap nguyen lieu SQL that bai");
            Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
