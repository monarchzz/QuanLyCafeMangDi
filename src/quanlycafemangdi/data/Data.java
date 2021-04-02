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
import quanlycafemangdi.Connector;
import quanlycafemangdi.model.NhanVien;

/**
 *
 * @author monar
 */
public class Data {
    
    private static Data INSTANCE ;
    private Connection connection;
    
    private Data(){
        connection = Connector.getConnection();
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
                    list.add(new NhanVien(tenTk,chucVu,cmnd,sdt,gioiTinh,tenNV));
                }
                
            }
        } catch (SQLException e) {
        }
        
        return list;
    }
}
