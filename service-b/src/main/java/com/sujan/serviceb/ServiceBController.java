package com.sujan.serviceb;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServiceBController {
    @GetMapping("/service/hello")
    public ResponseEntity<String> getHello(){
        return ResponseEntity.ok().body("Hello from service b!");
    }
}
