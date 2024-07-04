package com.example.pj_webshop.components;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Component
@Setter
public class Email {
    private String sender;

    private String recipient;

    private String body;

    private String subject;

    private byte[] attachment;
}
