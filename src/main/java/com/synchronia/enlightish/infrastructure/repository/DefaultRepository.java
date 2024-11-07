package com.synchronia.enlightish.infrastructure.repository;

import com.synchronia.enlightish.domain.entity.Default;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DefaultRepository extends JpaRepository<Default, UUID> {
}
