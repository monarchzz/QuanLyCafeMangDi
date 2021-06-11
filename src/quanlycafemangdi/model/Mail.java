/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quanlycafemangdi.model;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author admin
 */
public class Mail 
{
    public static void guiEmail(String nguoiNhan, String nguoiGui, String matKhau, String mailServer, 
            String noiDung)
    {
        try
        {
            System.out.println("Email dang duoc gui");

            Properties properties = new Properties();

            properties.put("mail.smtp.auth", true); // Xac thuc tai khoan
            //properties.put("mail.smtp.starttls.enable", true);
            properties.put("mail.smtp.ssl.enable", true); // Su dung SSL
            properties.put("mail.smtp.host", mailServer); // Khai bao mail server
            properties.put("mail.smtp.ssl.trust", mailServer); // Trust certificate 
            properties.put("mail.smtp.port", "465"); // Su dung SMTP port 465 (SSL) de gui mail

            Session session = Session.getInstance(properties, new Authenticator() 
            {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(nguoiGui, matKhau);
                }
            });

            Message message = thietLapNoiDungEmail(session, nguoiGui, nguoiNhan, noiDung);

            Transport.send(message);
            System.out.println("Gui email thanh cong");            
        }catch (MessagingException ex)
        {
            ex.printStackTrace();
        }
    }
    
    public static Message thietLapNoiDungEmail(Session session, String nguoiGui, String nguoiNhan, 
            String noiDung)
    {
        try
        {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(nguoiGui));   
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(nguoiNhan));
            message.setSubject("Khôi phục mật khẩu");
            message.setText(noiDung);
            return message;
        }catch (MessagingException ex)
        {
            ex.printStackTrace();
        }
        return null;
    }
}
