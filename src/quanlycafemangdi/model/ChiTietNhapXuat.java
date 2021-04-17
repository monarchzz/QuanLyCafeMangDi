/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quanlycafemangdi.model;

/**
 *
 * @author admin
 */
public class ChiTietNhapXuat 
{
//    private String maNhapXuat;
    private String maNguyenLieu;
    private String tenNguyenLieu;
    private int soLuong;
    private String taiKhoan;
    private String thoiGian;
    private long thanhTien;
    private String ghiChu;
    
    public ChiTietNhapXuat()
    {
        
    }

    public ChiTietNhapXuat(String maNguyenLieu, String tenNguyenLieu, int soLuong, String taiKhoan, String thoiGian, long thanhTien, String ghiChu) {
        this.maNguyenLieu = maNguyenLieu;
        this.tenNguyenLieu = tenNguyenLieu;
        this.soLuong = soLuong;
        this.taiKhoan = taiKhoan;
        this.thoiGian = thoiGian;
        this.thanhTien = thanhTien;
        this.ghiChu = ghiChu;
    }

    public long getThanhTien() {
        return thanhTien;
    }

    public void setThanhTien(long thanhTien) {
        this.thanhTien = thanhTien;
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

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public String getTaiKhoan() {
        return taiKhoan;
    }

    public void setTaiKhoan(String taiKhoan) {
        this.taiKhoan = taiKhoan;
    }

    public String getThoiGian() {
        return thoiGian;
    }

    public void setThoiGian(String thoiGian) {
        this.thoiGian = thoiGian;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
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
     
    public String dinhDangThoiGianNamThangNgay(String thoiGian)
    {
        String s[] = thoiGian.split(" ");
        String ngayThangNam[] = s[0].split("/");
        String ketQua = ngayThangNam[2];
        for (int i = ngayThangNam.length-2; i >= 0; i--)
        {
            ketQua = ketQua + "/" + ngayThangNam[i];
        }
        ketQua = ketQua + " " + s[1];
        return ketQua;        
    }        
}
