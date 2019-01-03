package CCOREAutomation;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Properties;
 
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.google.common.io.Files;
 
public class SendEMail {
	
 
    public static void sendEmailWithAttachments(String host, String port,
            final String userName, final String password, String toAddress,
            String subject, String content, String[] attachFiles)
            throws AddressException, MessagingException {
        // sets SMTP server properties
        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.user", userName);
        properties.put("mail.password", password);
 
        // creates a new session with an authenticator
        Authenticator auth = new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userName, password);
            }
        };
        Session session = Session.getInstance(properties, auth);
 
        // creates a new e-mail message
        Message msg = new MimeMessage(session);
 
        msg.setFrom(new InternetAddress(userName));
        InternetAddress[] toAddresses = { new InternetAddress(toAddress) };
        msg.setRecipients(Message.RecipientType.TO, toAddresses);
        
        InternetAddress[] ccAddresses = { new InternetAddress("pooja.patange@citruss.com") };
      
        msg.setRecipients(Message.RecipientType.CC, ccAddresses);
        msg.setSubject(subject);
        msg.setSentDate(new Date());
       
        
        // creates message part
        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent(content, "text/html");
        
 
        // creates multi-part
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);
 
        // adds attachments
        if (attachFiles != null && attachFiles.length > 0) {
            for (String filePath : attachFiles) {
                MimeBodyPart attachPart = new MimeBodyPart();
 
                try {
                    attachPart.attachFile(filePath);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
 
                multipart.addBodyPart(attachPart);
            }
        }
 
        // sets the multi-part as e-mail's content
        msg.setContent(multipart);
 
        // sends the e-mail
        Transport.send(msg);
 
    }
    
 
    /**
     * Test sending e-mail with attachments
     * @throws IOException 
     */
    public void emailsend(String Filename) throws IOException {
        // SMTP info
        String host = "mail.gandi.net";
        String port = "25";
        String mailFrom = "ctvbuild.test@ctv-it.net";
        String password = "NewComp123@";
 
        // message info
        String mailTo = "citool-support@citruss.com";
        String subject = "Fail Test report on CCORE Automation Testing with failed screenshot";
       // String message = "I have some attachments for you.";
        
        String content = Files.asCharSource(new File(System.getProperty("user.dir") + "/test-output/emailable-report.html"), StandardCharsets.UTF_8).read();
 
        // attachments
        String[] attachFiles = new String[1];//takes number of file attachment
        attachFiles[0] = Filename;
       // attachFiles[1] = System.getProperty("user.dir") + "/test-output/emailable-report.html";
      //  attachFiles[2] = "e:/Test/Video.mp4";
 
        try {
            sendEmailWithAttachments(host, port, mailFrom, password, mailTo, subject, content, attachFiles);
            System.out.println("Email sent.");
        } catch (Exception ex) {
            System.out.println("Could not send email.");
            ex.printStackTrace();
        }
    }
}