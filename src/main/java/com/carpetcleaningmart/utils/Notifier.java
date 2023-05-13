package com.carpetcleaningmart.utils;

import com.carpetcleaningmart.functions.OrdersPage;
import com.carpetcleaningmart.model.Customer;
import com.carpetcleaningmart.model.Order;

import java.io.File;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
public class Notifier {
    private static Session session;

    private  Notifier(){
        // Do nothing
    }
    public static void sendEmail(Customer customer, Order finishedOrder){

        final String senderEmail = "carpet.cleaner.mart@gmail.com";
        final String token = "hverxjkkrcdvvahu";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        if(session == null)
            session = Session.getInstance(props,
                    new javax.mail.Authenticator() {
                        @Override
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(senderEmail, token);
                        }
                    });

        try {
            finishedOrder.setCustomerId(customer.getId());
            double discount = OrdersPage.getDiscount(finishedOrder.getPrice(), customer.getTimesServed());
            double totalPrice = finishedOrder.getPrice() - discount * finishedOrder.getPrice();
            String discountMsg = String.format("And you have received a discount! The new price is %.2f$.", totalPrice);

            Invoice.generateInvoice(finishedOrder, customer, discount, totalPrice);


            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(customer.getEmail()));
            message.setSubject(String.format("Your %s is now Ready!", finishedOrder.getCategory().toString().toLowerCase()));



            Multipart multipart = new MimeMultipart();


            MimeBodyPart textBodyPart = new MimeBodyPart();
            textBodyPart.setText(String.format("Dear %s,\n We are pleased to inform you that your %s  is now ready and available to be taken!\n You'll be pleased to know that your %s is priced at %.2f$,\n %s \nwhich we believe is a great value for the quality we offer. \nThank you for choosing us. We hope to serve you again soon. \nBest regards, \nCarpet Cleaning Mart.\n", customer.getName(), finishedOrder.getCategory().toString().toLowerCase(), finishedOrder.getDescription(), finishedOrder.getPrice(), discount > 0 ? discountMsg : "")); // Set the message body text


            multipart.addBodyPart(textBodyPart);


            MimeBodyPart attachmentBodyPart = new MimeBodyPart();
            FileDataSource fileDataSource = new FileDataSource(new File("invoice.pdf"));
            attachmentBodyPart.setDataHandler(new DataHandler(fileDataSource));
            attachmentBodyPart.setFileName(fileDataSource.getName());


            multipart.addBodyPart(attachmentBodyPart);


            message.setContent(multipart);



            Transport.send(message);


        } catch (MessagingException exception) {
            Printer.printError(exception.getMessage());

        }
    }
}



