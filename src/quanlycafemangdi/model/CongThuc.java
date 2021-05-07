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
public class CongThuc {
    private String maCT;
    private String maSP;
    private String cachLam;
    private HashMap<String, Integer> chiTietCT = new HashMap<>();

    public CongThuc() {
    }

    public CongThuc(String maCT, String maSP, String cachLam, HashMap<String, Integer> chiTietCT) {
        this.maCT = maCT;
        this.maSP = maSP;
        this.cachLam = cachLam;
        this.chiTietCT = chiTietCT;
    }

    public String getMaCT() {
        return maCT;
    }

    public void setMaCT(String maCT) {
        this.maCT = maCT;
    }

    public String getMaSP() {
        return maSP;
    }

    public void setMaSP(String maSP) {
        this.maSP = maSP;
    }

    public String getCachLam() {
        return cachLam;
    }

    public void setCachLam(String cachLam) {
        this.cachLam = cachLam;
    }

    public HashMap<String, Integer> getChiTietCT() {
        return chiTietCT;
    }

    public void setChiTietCT(HashMap<String, Integer> chiTietCT) {
        this.chiTietCT = chiTietCT;
    }
    
    
    
    
    
}
