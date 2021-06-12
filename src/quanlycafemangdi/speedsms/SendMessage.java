package quanlycafemangdi.speedsms;

import java.io.IOException;

public class SendMessage {
    public static String defaultToken = "bpiwi7L6j6_43KgGUh8cKrxjtMseJPxy";
    public static String defaultSenderID = "91985efdca75eaeb";

    public static void sendMessage(String token,String phoneNumber, String content, String senderID){
        SpeedSMSAPI api  = new SpeedSMSAPI(token);
        try {
            String result = api.sendSMS(phoneNumber, content, 5, senderID);
            System.out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
