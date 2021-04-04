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
public class NhanVien {
    private String tenTk;
    private String chucVu;
    private String soCM;
    private String sdt;
    private String gioiTinh;
    private String tenNhanVien;
    private String matKhau;


    public NhanVien() {
        
    }
    
    public NhanVien(String tenTk, String chucVu, String soCM, String sdt, String gioiTinh, String tenNhanVien, String matKhau) {
        this.tenTk = tenTk;
        this.chucVu = chucVu;
        this.soCM = soCM;
        this.sdt = sdt;
        this.gioiTinh = gioiTinh;
        this.tenNhanVien = tenNhanVien;
        this.matKhau = matKhau;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public NhanVien(String tenTk, String chucVu, String soCM, String sdt, String gioiTinh, String tenNhanVien) {
        this.tenTk = tenTk;
        this.chucVu = chucVu;
        this.soCM = soCM;
        this.sdt = sdt;
        this.gioiTinh = gioiTinh;
        this.tenNhanVien = tenNhanVien;
    }

    public String getTenTk() {
        return tenTk;
    }

    public void setTenTk(String tenTk) {
        this.tenTk = tenTk;
    }

    public String getChucVu() {
        return chucVu;
    }

    public void setChucVu(String chucVu) {
        this.chucVu = chucVu;
    }

    public String getSoCM() {
        return soCM;
    }

    public void setSoCM(String soCM) {
        this.soCM = soCM;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getTenNhanVien() {
        return tenNhanVien;
    }

    public void setTenNhanVien(String tenNhanVien) {
        this.tenNhanVien = tenNhanVien;
    }

    
    
}
