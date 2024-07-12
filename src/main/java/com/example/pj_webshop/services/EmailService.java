package com.example.pj_webshop.services;

import com.example.pj_webshop.entities.models.Email;
import com.example.pj_webshop.components.emails.HtmlToPdfConverter;
import com.example.pj_webshop.entities.models.Order;
import com.example.pj_webshop.services.moderation.ClientService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class EmailService {

    private HtmlToPdfConverter htmlToPdfConverter;

    private JavaMailSender javaMailSender;

    private ClientService clientService;

    @Value("${spring.mail.sender}")
    private String emailSender;
    @Value("classpath:templates/email-template.html")
    private String source;

    @Autowired
    public void setClientService(ClientService clientService) {
        this.clientService = clientService;
    }

    @Autowired
    public void setHtmlToPdfConverter(HtmlToPdfConverter htmlToPdfConverter) {
        this.htmlToPdfConverter = htmlToPdfConverter;
    }

    @Autowired
    public void setJavaMailSender(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendOrderConfirmation(Order order) throws IOException, MessagingException {
        Email email = createOrderConfirmationEmail(order);
        sendEmail(email);
    }

    @NotNull
    public Email createOrderConfirmationEmail(@NotNull Order order) throws IOException {
        var clientOpt = clientService.findClientByOrderId(order.getOrderId());

        if (clientOpt.isEmpty())
            throw new IOException("Invalid order id");

        Email email = new Email();
        email.setSender(emailSender);
        email.setRecipient(clientOpt.get().getEmail());
        email.setSubject("Order Confirmation");
        email.setBody("Thank you for your order. Details of your order are attached in the file below.");
        try {
            byte[] emailAttachment = htmlToPdfConverter.convertHtmlToPdf(source);
            email.setAttachment(emailAttachment);
        } catch (IOException e) {
            throw new IOException("Exception has arisen while converting HTML-source to PDF", e);
        }
        return email;
    }

    public void sendEmail(@NotNull Email email) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(email.getRecipient());
        helper.setSubject(email.getSubject());
        helper.setText(email.getBody());

        InputStreamSource attachmentSource = new ByteArrayResource(email.getAttachment());
        helper.addAttachment("order-confirmation.pdf", attachmentSource);
        javaMailSender.send(message);
    }
}
