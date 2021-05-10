/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quanlycafemangdi;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JTable;

/**
 *
 * @author admin
 */
public class Design 
{
    public static void thietKeBang(JTable table, String kichThuoChu)
    {
        if (kichThuoChu.equals("Lon"))
        {
            table.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));          
        }
        else 
        {
            table.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 12));          
        }
        table.getTableHeader().setOpaque(false);
        table.getTableHeader().setBackground(new Color(32,136,203));
        table.getTableHeader().setForeground(new Color(255,255,255));
        table.setRowHeight(25);          
    }    
}
