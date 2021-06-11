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
import java.sql.Timestamp;
import quanlycafemangdi.Util;

/**
 *
 * @author admin
 */
public class LichSuChinhSuaNguyenLieu 
{
    private Timestamp thoiGian;
    private String maNguyenLieu;
    private String tenNguyenLieu;
    private String maDonVi;
    private String donViTinh;
    private long Gia;
    private String nguoiThucHien;
    private String ghiChu;
    
    public LichSuChinhSuaNguyenLieu()
    {
        
    }

    public LichSuChinhSuaNguyenLieu(Timestamp thoiGian, String maNguyenLieu, String tenNguyenLieu, String maDonVi, long Gia, String nguoiThucHien, String ghiChu) {
        this.thoiGian = thoiGian;
        this.maNguyenLieu = maNguyenLieu;
        this.tenNguyenLieu = tenNguyenLieu;
        this.maDonVi = maDonVi;
        this.Gia = Gia;
        this.nguoiThucHien = nguoiThucHien;
        this.ghiChu = ghiChu;
    }

    public Timestamp getThoiGian() {
        return thoiGian;
    }

    public void setThoiGian(Timestamp thoiGian) {
        this.thoiGian = thoiGian;
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
        return Gia;
    }

    public void setGia(long Gia) {
        this.Gia = Gia;
    }

    public String getNguoiThucHien() {
        return nguoiThucHien;
    }

    public void setNguoiThucHien(String nguoiThucHien) {
        this.nguoiThucHien = nguoiThucHien;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }
    
     public String dinhDangThoiGianNgayThangNam(String thoiGian)
    {
        String s[] = thoiGian.split(" ");
        String namThangNgay[] = s[0].split("-");
        String ketQua = namThangNgay[2];
        for (int i = namThangNgay.length-2; i >= 0; i--)
        {
            ketQua = ketQua + "/" + namThangNgay[i];
        }
        ketQua = ketQua + " " + s[1];
        return ketQua;
    }  
   
    public String layDonViTinh()
    {
        Connection connect = Util.getConnection();
        String query = "select DVT.tenDV"
                     + " from LichSuChinhSuaNguyenLieu as LSCSNL, DonViTinh as DVT"
                     + " where LSCSNL.maDV = DVT.maDV"
                     + "    and LSCSNL.MaDV = '" + this.maDonVi + "'";
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
