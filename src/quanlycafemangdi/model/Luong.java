/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quanlycafemangdi.model;

/**
 *
 * @author monar
 */
public class Luong {
    private String maLuong;
    private String tenTK;
    private String tenNV;
    private String cmnd;
    private String thangNam;
    private int luongCoBan;
    private float heSoLuong;
    private int soCaLam;
    private long luong;
    private String chucVu;
    
    // trang thai = 0 chua thanh toan
    // trang thai = 1 da thanh toan tien luong
    private int trangThai;

    public Luong() {
    }

    public Luong(String maLuong, String tenTK, String tenNV, String cmnd, 
            String thangNam, int luongCoBan, float heSoLuong, int soCaLam, 
            long luong, int trangThai, String chucVu) {
        
        this.maLuong = maLuong;
        this.tenTK = tenTK;
        this.tenNV = tenNV;
        this.cmnd = cmnd;
        this.thangNam = thangNam;
        this.luongCoBan = luongCoBan;
        this.heSoLuong = heSoLuong;
        this.soCaLam = soCaLam;
        this.luong = luong;
        this.trangThai = trangThai;
        this.chucVu = chucVu;
    }

    public String getMaLuong() {
        return maLuong;
    }

    public void setMaLuong(String maLuong) {
        this.maLuong = maLuong;
    }

    public String getTenTK() {
        return tenTK;
    }

    public void setTenTK(String tenTK) {
        this.tenTK = tenTK;
    }

    public String getTenNV() {
        return tenNV;
    }

    public void setTenNV(String tenNV) {
        this.tenNV = tenNV;
    }

    public String getCmnd() {
        return cmnd;
    }

    public void setCmnd(String cmnd) {
        this.cmnd = cmnd;
    }

    public String getThangNam() {
        return thangNam;
    }

    public void setThangNam(String thangNam) {
        this.thangNam = thangNam;
    }

    public int getLuongCoBan() {
        return luongCoBan;
    }

    public void setLuongCoBan(int luongCoBan) {
        this.luongCoBan = luongCoBan;
    }

    public float getHeSoLuong() {
        return heSoLuong;
    }

    public void setHeSoLuong(float heSoLuong) {
        this.heSoLuong = heSoLuong;
    }

    public int getSoCaLam() {
        return soCaLam;
    }

    public void setSoCaLam(int soCaLam) {
        this.soCaLam = soCaLam;
    }

    public long getLuong() {
        return luong;
    }

    public void setLuong(long luong) {
        this.luong = luong;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    public String getChucVu() {
        return chucVu;
    }

    public void setChucVu(String chucVu) {
        this.chucVu = chucVu;
    }
    
    
    
}
