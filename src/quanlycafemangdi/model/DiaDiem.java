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
public class DiaDiem {
    private String maDD;
    private String viTri;

    public DiaDiem() {
    }

    public DiaDiem(String maDD, String viTri) {
        this.maDD = maDD;
        this.viTri = viTri;
    }

    public String getMaDD() {
        return maDD;
    }

    public void setMaDD(String maDD) {
        this.maDD = maDD;
    }

    public String getViTri() {
        return viTri;
    }

    public void setViTri(String viTri) {
        this.viTri = viTri;
    }

    
    
}
