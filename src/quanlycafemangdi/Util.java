/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quanlycafemangdi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

/**
 *
 * @author monar
 */
public class Util {
    public static void doiPanel(JLayeredPane jLayeredPane,JPanel panel){
        jLayeredPane.removeAll();
        jLayeredPane.add(panel);
        jLayeredPane.repaint();
        jLayeredPane.revalidate();
    }
    
    public static String formatCurrency(long currencyAmount){
        String tmp = String.valueOf(currencyAmount);
        int count = 0;
        for (int i = tmp.length() - 1; i >= 0; i--){
            count++;
            if (count == 3 && i!= 0) {
                count = 0;
                tmp = tmp.substring(0,i) + "," + tmp.substring(i);
            }
        }
        return tmp;
    }    
    public static Connection getConnection()
    {
        Connection connect = null;
        String URL = "jdbc:sqlserver://;databaseName=QLCTA";
        String username = "sa";
        String password = "123";
        try
        {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connect = DriverManager.getConnection(URL, username, password);
            System.out.println("Thanh cong");
        }catch (ClassNotFoundException | SQLException ex)
        {
            System.out.println("That bai");
        }
        return connect;
    }
}
