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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import quanlycafemangdi.Util;
import quanlycafemangdi.model.NhanVien;

/**
 *
 * @author monar
 */
public class Data {
    
    private static Data INSTANCE ;
    private Connection connection;
    
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
        }
        return null;
    }
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
    
}
