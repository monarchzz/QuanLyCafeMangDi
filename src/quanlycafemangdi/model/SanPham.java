/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quanlycafemangdi.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import quanlycafemangdi.Util;

/**
 *
 * @author monar
 */
public class SanPham {
    private String maSP;
    private String tenSP;
    private String maDonVi;
    private String donVi;
    private long gia;
        
    
    public SanPham() {
    }
    
    public SanPham(String maSP, String tenSP, String donVi, long gia) {
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.donVi = donVi;
        this.gia = gia;
    }

    public SanPham(String maSP, String tenSP, String maDonVi, String donVi, long gia) {
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.maDonVi = maDonVi;
        this.donVi = donVi;
        this.gia = gia;
    }

    

    public String getMaSP() {
        return maSP;
    }

    public void setMaSP(String maSP) {
        this.maSP = maSP;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public String getMaDonVi() {
        return maDonVi;
    }

    public void setMaDonVi(String maDonVi) {
        this.maDonVi = maDonVi;
    }
    
    public String getDonVi() {
        return donVi;
    }

    public void setDonVi(String doVi) {
        this.donVi = doVi;
    }

    public long getGia() {
        return gia;
    }

    public void setGia(long gia) {
        this.gia = gia;
    }
    
    public String layDonViTinh()
    {
        Connection connect = Util.getConnection();
        String query = "select DVT.tenDV"
                     + " from SanPham as SP, DonViTinh as DVT"
                     + " where SP.maDV = DVT.maDV"
                     + "    and SP.MaDV = '" + this.maDonVi + "'";
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
