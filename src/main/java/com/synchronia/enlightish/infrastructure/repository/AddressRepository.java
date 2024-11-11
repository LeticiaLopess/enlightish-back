package com.synchronia.enlightish.infrastructure.repository;

import com.synchronia.enlightish.domain.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, String> {
}
