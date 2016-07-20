/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EmailHandler;

/**
 *
 * @author Hariom
 */
import com.sendgrid.SendGrid;
import com.sendgrid.SendGridException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
public class EmailSender 
{
    String SenderEmail;
    String ReceiverEmail;

    
   public void EmailVerification(String Email, ArrayList list)
   { 
       try { 
           SenderEmail="no-reply@dreamalarm.in";
           ReceiverEmail = Email;
           
           
           SendGrid sendgrid = new SendGrid("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");
           SendGrid.Email email = new SendGrid.Email();
           
           email.addTo(ReceiverEmail);
           email.setFrom(SenderEmail);
           email.setSubject("Image compression Results");
           email.setHtml("Hi, Image compression List: "+list);
           
           try {
               SendGrid.Response response = sendgrid.send(email);
               System.out.println("email successfully sent"+response);
           } catch (SendGridException ex) {
               Logger.getLogger(EmailSender.class.getName()).log(Level.SEVERE, null, ex);
           }
       } 
       catch(Exception e)
       {
           System.out.print("Exception occured"+e);
       }
   }
    
}
