package com.synchronia.enlightish.infrastructure.repository;

import com.synchronia.enlightish.domain.entity.Lead;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LeadRepository extends JpaRepository<Lead, UUID> {
}
