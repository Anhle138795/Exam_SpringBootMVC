package net.codejava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import net.codejava.model.Employees;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendVerificationEmail(Employees employee, String siteURL) {
        String toAddress = employee.getEmail();
        String fromAddress = "ank138795@gmail.com"; // Replace with your email
        String senderName = "Aptech";
        String subject = "Please verify your registration";
        String verifyURL = siteURL + "/employees/verify?code=" + employee.getVerifyCode();

        String content = "Dear " + employee.getFullname() + ",\n\n"
                + "Thank you for registering. Please click the link below to verify your email address:\n"
                + verifyURL + "\n\n"
                + "Best regards,\n"
                + senderName;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromAddress);
        message.setTo(toAddress);
        message.setSubject(subject);
        message.setText(content);

        mailSender.send(message);
    }
    
    public void sendResetPasswordEmail(Employees employee, String resetURL) {
        String toAddress = employee.getEmail();
        String fromAddress = "ank138795@gmail.com"; // Replace with your email
        String senderName = "Aptech";
        String subject = "Reset Your Password";
        
        String content = "Dear " + employee.getFullname() + ",\n\n"
                + "We received a request to reset your password. Click the link below to change your password:\n"
                + resetURL + "\n\n"
                + "If you did not request a password reset, you can ignore this email.\n\n"
                + "Best regards,\n"
                + senderName;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromAddress);
        message.setTo(toAddress);
        message.setSubject(subject);
        message.setText(content);

        mailSender.send(message);
    }
}
