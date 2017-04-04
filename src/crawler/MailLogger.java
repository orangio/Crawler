package crawler;

/**
 * Created by Filip on 21.03.2017.
 */

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties.*;
import javax.activation.*;

public class MailLogger implements Logger, CrawlerEventListener {

    private static String adress = "programcrawler@gmail.com";
    private static String pass ="qwertasdfg";
    private static String mailto="filip.bartman@gmail.com";
    Properties properties = System.getProperties();
    Session session;

    public void onStudentAdded(Student a)
    {
        log("add",a);
    }
    public void onStudentDeleted(Student a)
    {
        log("del",a);
    }
    public void onNoChange()
    {
        Student a=new Student();
        log("no",a);
    }
    public  MailLogger()
    {
        properties.setProperty("mail.smtp.host","smtp.gmail.com");
        properties.setProperty("mail.smtp.socketFactory.port", "465");
        properties.setProperty("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
        properties.setProperty("mail.smtp.auth","true");
        properties.setProperty("mail.smtp.port", "465");
        session = Session.getDefaultInstance(properties,

                new javax.mail.Authenticator(){
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication(){
                        return new PasswordAuthentication(adress,pass);
                    }
                });
        session.setDebug(true);


    }
        public  void log(String status, Student student) {
            try {
                // Create a default MimeMessage object.
                Message message = new MimeMessage(session);

                // Set From: header field of the header.
                message.setFrom(new InternetAddress(mailto));

                // Set To: header field of the header.
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mailto));

                // Set Subject: header field
                message.setSubject("Crawler Update");

                // Now set the actual message
                switch (status){
                    case "add":
                        message.setText("STUDENT ADDED "+student.getFirstName()+" "+student.getLastName()+" "+student.getMark()+" "+student.getAge());
                        break;

                    case "del":
                        message.setText("STUDENT REMOVED"+student.getFirstName()+" "+student.getLastName()+" "+student.getMark()+" "+student.getAge());
                        break;

                    case "no":
                        message.setText("NO CHANGE IN ENTRIES");
                        break;


                }



                //message.setText("This is actual message");

                // Send message
                Transport.send(message);
                System.out.println("Sent message successfully....");
            }catch (MessagingException mex) {
               throw new RuntimeException(mex);
            }
        }
    }