package com.sujan.emailservice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.ses.model.*;

@Service
public class EmailService {
    private final SesClient sesClient;
    private final String source;
    public EmailService(SesClient sesClient, @Value("${SES_PRIMARY_SENDER_MAIL}") String source){
        this.sesClient = sesClient;
        this.source = source;
    }

    public void sendWelcomeEmail(String to){
        Message message = Message.builder()
                .subject(Content.builder().data("Test Subject").build())
                .body(Body.builder().text(Content.builder().data("Test Content").build()).build())
                .build();
        SendEmailRequest emailRequest = SendEmailRequest.builder()
                .source(source)
                .message(message)
                .destination(Destination.builder().toAddresses(to).build())
                .build();
        sesClient.sendEmail(emailRequest);
    }
}
