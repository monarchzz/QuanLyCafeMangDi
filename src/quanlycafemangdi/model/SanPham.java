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
public class SanPham {
    private String maSP;
    private String tenSP;
    private String donVi;
    private long gia;

    public SanPham() {
    }

    public SanPham(String maSP, String tenSP, String doVi, long gia) {
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.donVi = doVi;
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
    
    
}
