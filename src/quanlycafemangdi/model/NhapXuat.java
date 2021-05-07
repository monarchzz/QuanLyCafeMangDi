/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quanlycafemangdi.model;

import java.sql.Timestamp;
import java.util.HashMap;

/**
 *
 * @author admin
 */
public class NhapXuat 
{
    private String maNhapXuat;
    private String TaiKhoan;
    private Timestamp thoiGian;
    private String trangThai;
    private String ghiChu;
    HashMap<String, Integer> chiTietNhapXuat = new HashMap<>();
    private long thanhTien = 0; // Chi dung cho NHAP nguyen lieu ve kho (khong co XUAT)
    
    public NhapXuat()
    {
        
    }

    public NhapXuat(String maNhapXuat, String TaiKhoan, Timestamp thoiGian, String trangThai, String ghiChu) {
        this.maNhapXuat = maNhapXuat;
        this.TaiKhoan = TaiKhoan;
        this.thoiGian = thoiGian;
        this.trangThai = trangThai;
        this.ghiChu = ghiChu;
    }

    public String getMaNhapXuat() {
        return maNhapXuat;
    }

    public void setMaNhapXuat(String maNhapXuat) {
        this.maNhapXuat = maNhapXuat;
    }

    public String getTaiKhoan() {
        return TaiKhoan;
    }

    public void setTaiKhoan(String TaiKhoan) {
        this.TaiKhoan = TaiKhoan;
    }

    public Timestamp getThoiGian() {
        return thoiGian;
    }

    public void setThoiGian(Timestamp thoiGian) {
        this.thoiGian = thoiGian;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public HashMap<String, Integer> getChiTietNhapXuat() {
        return chiTietNhapXuat;
    }

    public void setChiTietNhapXuat(HashMap<String, Integer> chiTietNhapXuat) {
        this.chiTietNhapXuat = chiTietNhapXuat;
    }
    
     public String dinhDangThoiGianNgayThangNam(String thoiGian)
    {
//        try
//        {
//            SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            Date date = (Date) SDF.parse(thoiGian);
//            SimpleDateFormat SDF2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
//            String newDate = SDF2.format(date);
//            return newDate;            
//        }catch (ParseException ex)
//        {
//            System.out.println("Dinh dang thoi gian that bai");
//        }
//        return null;
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
              

    public long getThanhTien() {
        return thanhTien;
    }

    public void setThanhTien(long thanhTien) {
        this.thanhTien = thanhTien;
    }
    
    
}
