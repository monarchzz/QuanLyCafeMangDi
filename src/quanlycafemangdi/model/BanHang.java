/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quanlycafemangdi.model;

import java.util.HashMap;

/**
 *
 * @author monar
 */
public class BanHang {
    private String maBH;
    private String tenTK;
    private String tg;
    private long tongTien;
    private HashMap<String,Integer> dsSanPham;

    public BanHang() {
    }

    public BanHang(String maBH, String tenTK, String tg, long tongTien, HashMap<String, Integer> dsSanPham) {
        this.maBH = maBH;
        this.tenTK = tenTK;
        this.tg = tg;
        this.tongTien = tongTien;
        this.dsSanPham = dsSanPham;
    }

    public String getMaBH() {
        return maBH;
    }

    public void setMaBH(String maBH) {
        this.maBH = maBH;
    }

    public String getTenTK() {
        return tenTK;
    }

    public void setTenTK(String tenTK) {
        this.tenTK = tenTK;
    }

    public String getTg() {
        return tg;
    }

    public void setTg(String tg) {
        this.tg = tg;
    }

    public long getTongTien() {
        return tongTien;
    }

    public void setTongTien(long tongTien) {
        this.tongTien = tongTien;
    }

    public HashMap<String, Integer> getDsSanPham() {
        return dsSanPham;
    }

    public void setDsSanPham(HashMap<String, Integer> dsSanPham) {
        this.dsSanPham = dsSanPham;
    }
    
    
}
