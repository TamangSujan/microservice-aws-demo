package com.sujan.servicea;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "service")
public class ServiceA {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String value;
    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
