package com.sujan.servicea;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ServiceARepository extends JpaRepository<ServiceA, UUID> {
}
