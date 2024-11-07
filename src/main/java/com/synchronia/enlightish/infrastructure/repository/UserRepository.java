package com.synchronia.enlightish.infrastructure.repository;

import com.synchronia.enlightish.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
}
