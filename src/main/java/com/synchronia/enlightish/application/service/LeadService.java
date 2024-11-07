package com.synchronia.enlightish.application.service;

import com.synchronia.enlightish.application.service.exception.DatabaseException;
import com.synchronia.enlightish.application.service.exception.ResourceNotFoundException;
import com.synchronia.enlightish.domain.entity.Lead;
import com.synchronia.enlightish.domain.entity.User;
import com.synchronia.enlightish.infrastructure.repository.LeadRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

@Service
public class LeadService {

    @Autowired
    public LeadRepository repository;

    public List<Lead> findAll() {
        return repository.findAll();
    }

    public Lead findById(UUID id) {
        Optional<Lead> lead = repository.findById(id);
        return lead.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public Lead insert(Lead lead) {
        return repository.save(lead);
    }

    public void delete(UUID id) {
        try {
            if (!repository.existsById(id)) {
                throw new ResourceNotFoundException(id);
            }

            repository.deleteById(id);

        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    public Lead update(UUID id, Lead lead) {
        try {
            Lead entity = repository.getReferenceById(id);
            updateData(entity, lead);

            return repository.save(entity);

        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(id);
        }
    }

    private void updateData(Lead entity, Lead lead) {
        updateIfPresent(entity::setName, lead.getName());
        updateIfPresent(entity::setMail, lead.getMail());
        updateIfPresent(entity::setPhone, lead.getPhone());
        updateIfPresent(entity::setBirth, lead.getBirth());
    }

    private <T> void updateIfPresent(Consumer<T> setter, T value) {
        if (value != null) {
            setter.accept(value);
        }
    }
}
