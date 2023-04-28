package com.carpetcleaningmart.Utils;

import com.carpetcleaningmart.model.Customer;
import com.carpetcleaningmart.model.Order;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
public class Notifier {
    private static Session session;
    public static void sendEmail(Customer customer, Order finishedOrder){

        final String senderEmail = "carpet.cleaner.mart@gmail.com";
        final String token = "hverxjkkrcdvvahu";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

//        System.setProperty("java.util.logging.manager", "org.apache.logging.log4j.jul.LogManager");
        if(session == null)
            session = Session.getInstance(props,
                    new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(senderEmail, token);
                        }
                    });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(customer.getEmail()));
            message.setSubject(String.format("Your %s is now Ready!", finishedOrder.getCategory().toString().toLowerCase()));
            message.setText(
                    String.format("""
                                    Dear %s,
                                                        
                                    We are pleased to inform you that your %s  is now ready and available to be taken!
                                                      
                                    You'll be pleased to know that your %s is priced at %.2f$,
                                     which we believe is a great value for the quality we offer.
                                                        
                                    Thank you for choosing us. We hope to serve you again soon.
                                                        
                                    Best regards,
                                    Carpet Cleaning Mart.        \s
                            """, customer.getName(), finishedOrder.getCategory().toString().toLowerCase(), finishedOrder.getDescription(), finishedOrder.getPrice()));

            Transport.send(message);


        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}



