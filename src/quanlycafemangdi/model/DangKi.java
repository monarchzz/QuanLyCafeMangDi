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
public class DangKi {
    private String maDK;
    private String maCLV;
    private String thoiGian;
    private String ghiChu;
    private HashMap<String, Integer> chiTietDangKi;

    public DangKi() {
    }

    public DangKi(String maDK, String maCLV, String thoiGian, String ghiChu, HashMap<String, Integer> chiTietDangKi) {
        this.maDK = maDK;
        this.maCLV = maCLV;
        this.thoiGian = thoiGian;
        this.ghiChu = ghiChu;
        this.chiTietDangKi = chiTietDangKi;
    }

    public String getMaDK() {
        return maDK;
    }

    public void setMaDK(String maDK) {
        this.maDK = maDK;
    }

    public String getMaCLV() {
        return maCLV;
    }

    public void setMaCLV(String maCLV) {
        this.maCLV = maCLV;
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

    public HashMap<String, Integer> getChiTietDangKi() {
        return chiTietDangKi;
    }

    public void setChiTietDangKi(HashMap<String, Integer> chiTietDangKi) {
        this.chiTietDangKi = chiTietDangKi;
    }
    
    
}
