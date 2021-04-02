/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quanlycafemangdi;
import java.sql.*;
/**
 *
 * @author admin
 */
public class Connector 
{
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
    
//    public static void main(String[] agrs)
//    {
//        Connection connect = getConnection();
//    }     
}
