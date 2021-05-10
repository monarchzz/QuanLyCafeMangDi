/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quanlycafemangdi.view;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;


public class Test {

   public static void main(String [] args) { 
       String test = "abc";
       byte[] salt = getSalt();
       test = hashing(test, salt);
       System.out.println(test);
   }
   
    public static byte[] getSalt()
    {
        try
        {
            // Dung SecureRandom de tao salt
            SecureRandom SR = SecureRandom.getInstance("SHA1PRNG"); // Thuat toan tao salt SHA-1 Pseudo-Random Number Generator        
            // Tao 1 mang byte cho salt
            byte[] salt = new byte[16];
            // Get salt ngau nhien
            SR.nextBytes(salt);
            return salt;            
        }catch (NoSuchAlgorithmException ex)
        {
            ex.printStackTrace();
        }
        return null;
    }  
   
    public static String hashing(String originalPassword, byte[] salt)
    {
        String hashedPassword = "";
        try 
        {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt);
            byte[] hash = md.digest(originalPassword.getBytes()); // Hash password va dua vao trong mang byte
            // Convert mang byte sang String
            StringBuilder sb = new StringBuilder();
            for (byte b: hash)
            {
                sb.append(String.format("%02x", b)); // String.format("%02x"): convert sang hexa (chu thuong),
                                                     //   chu in hoa: %02X
            }
            hashedPassword = sb.toString();
            return hashedPassword;
        }catch (NoSuchAlgorithmException e) 
        {
            System.out.println("Hash that bai");
        }
        return "";
    }         
}
