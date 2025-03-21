package com.sujan.emailservice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {
    private final EmailService emailService;
    public EmailController(EmailService emailService){
        this.emailService = emailService;
    }

    @PostMapping("/notification/email/send")
    public ResponseEntity<Void> subscriber(@RequestBody EmailRequest emailRequest){
        emailService.sendWelcomeEmail(emailRequest.getDestination());
        return ResponseEntity.noContent().build();
    }
}
