package com.example.pj_webshop.controllers;

import com.example.pj_webshop.entities.Order;
import com.example.pj_webshop.services.EmailService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;

@RestController
@RequestMapping("/email")
@Scope(WebApplicationContext.SCOPE_SESSION)
public class EmailController {

    private final EmailService emailService;

    @Autowired
    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/sendOrderConfirmation")
    public ResponseEntity<String> sendOrderConfirmation(@RequestBody Order order) {
        try {
            emailService.sendOrderConfirmation(order);
            return new ResponseEntity<>("Order confirmation email sent successfully.", HttpStatus.OK);
        } catch (IOException | MessagingException e) {
            return new ResponseEntity<>("Failed to send order confirmation email.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
