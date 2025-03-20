package com.sujan.servicea;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ServiceAController {
    private final ServiceARepository serviceARepository;
    public ServiceAController(ServiceARepository serviceARepository){
        this.serviceARepository = serviceARepository;
    }
    @GetMapping("/services")
    public ResponseEntity<List<ServiceA>> getAllServices(){
        return ResponseEntity.ok().body(serviceARepository.findAll());
    }
}
