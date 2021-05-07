/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quanlycafemangdi.model;

import quanlycafemangdi.Util;
import java.sql.*;
/**
 *
 * @author admin
 */
public class NguyenLieu 
{
    private String maNguyenLieu;
    private String tenNguyenLieu;
    private String maDonVi;
    private String donViTinh;
    private long gia;
    private int soLuong;
    
    public NguyenLieu()
    {
        
    } 

    public NguyenLieu(String maNguyenLieu, String tenNguyenLieu, String maDonVi, String donViTinh, long gia, int soLuong) {
        this.maNguyenLieu = maNguyenLieu;
        this.tenNguyenLieu = tenNguyenLieu;
        this.maDonVi = maDonVi;
        this.donViTinh = donViTinh;
        this.gia = gia;
        this.soLuong = soLuong;
    }    
    
    public String getMaNguyenLieu() {
        return maNguyenLieu;
    }

    public void setMaNguyenLieu(String maNguyenLieu) {
        this.maNguyenLieu = maNguyenLieu;
    }

    public String getTenNguyenLieu() {
        return tenNguyenLieu;
    }

    public void setTenNguyenLieu(String tenNguyenLieu) {
        this.tenNguyenLieu = tenNguyenLieu;
    }

    public String getMaDonVi() {
        return maDonVi;
    }

    public void setMaDonVi(String maDonVi) {
        this.maDonVi = maDonVi;
    }

    public String getDonViTinh() {
        return donViTinh;
    }

    public void setDonViTinh(String donViTinh) {
        this.donViTinh = donViTinh;
    }
    
    public long getGia() {
        return gia;
    }

    public void setGia(long gia) {
        this.gia = gia;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }
    
    public String layDonViTinh()
    {
        Connection connect = Util.getConnection();
        String query = "select DVT.tenDV"
                     + " from NguyenLieu as NL, DonViTinh as DVT"
                     + " where NL.maDV = DVT.maDV"
                     + "    and NL.MaDV = '" + this.maDonVi + "'";
        try
        {
            PreparedStatement ps = connect.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
            {
                return rs.getString("tenDV");
            }
        }catch (SQLException ex)
        {
            System.out.println("Lay don vi tinh that bai");
        }
        return null;
    }   
}
