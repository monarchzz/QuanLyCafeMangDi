/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quanlycafemangdi.view;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author admin
 */
public class Test 
{
    public static void main(String[] args) throws ParseException
    {
//        String thoiGian = "2021-04-15 18:08:35.130";
//        String s[] = thoiGian.split(" ");
//        String ngayThangNam[] = s[0].split("-");
//        String ngayThang = ngayThangNam[2];
//        for (int i = ngayThangNam.length-2; i >= 0; i--)
//        {
//            ngayThang = ngayThang + "/" + ngayThangNam[i];
//        }   
//        ngayThang = ngayThang + " " + s[1];
//        System.out.print(ngayThang);
//        
        String thoiGian = "15/04/2021 18:08:35.130";
        String s[] = thoiGian.split(" ");
        String ngayThangNam[] = s[0].split("/");
        String ketQua = ngayThangNam[2];
        for (int i = ngayThangNam.length-2; i >= 0; i--)
        {
            ketQua = ketQua + "/" + ngayThangNam[i];
        }
        ketQua = ketQua + " " + s[1];
        System.out.println(ketQua);
        
    }
}
