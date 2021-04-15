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
public class CaLamViec {
    private String maCLV;
    private String maDD;
    private String caLamViec;
    private String ngay;
    private String TK1;
    private String TK2;

    public CaLamViec() {
    }

    public CaLamViec(String maCLV, String maDD, String caLamViec, String ngay, String TK1) {
        this.maCLV = maCLV;
        this.maDD = maDD;
        this.caLamViec = caLamViec;
        this.ngay = ngay;
        this.TK1 = TK1;
    }

    public CaLamViec(String maCLV, String maDD, String caLamViec, String ngay, String TK1, String TK2) {
        this.maCLV = maCLV;
        this.maDD = maDD;
        this.caLamViec = caLamViec;
        this.ngay = ngay;
        this.TK1 = TK1;
        this.TK2 = TK2;
    }

    public String getMaCLV() {
        return maCLV;
    }

    public void setMaCLV(String maCLV) {
        this.maCLV = maCLV;
    }

    public String getMaDD() {
        return maDD;
    }

    public void setMaDD(String maDD) {
        this.maDD = maDD;
    }

    public String getCaLamViec() {
        return caLamViec;
    }

    public void setCaLamViec(String caLamViec) {
        this.caLamViec = caLamViec;
    }

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    public String getTK1() {
        return TK1;
    }

    public void setTK1(String TK1) {
        this.TK1 = TK1;
    }

    public String getTK2() {
        return TK2;
    }

    public void setTK2(String TK2) {
        this.TK2 = TK2;
    }
    
}
