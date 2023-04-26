package com.carpetcleaningmart.Utils;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
public class Notifier {
    public static void sendEmail(){

        final String username = "carpet.cleaner.mart@gmail.com";
        final String password = "V1MOH&!!dul&6#nl";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

//        System.setProperty("java.util.logging.manager", "org.apache.logging.log4j.jul.LogManager");
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse("shaer.nasrallah@gmail.com"));
            message.setSubject("Your Order#123 is Ready");
            message.setText("Dear Mail Crawler,"
                    + "\n\n No spam to my email, please!");

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}



