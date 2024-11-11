package com.synchronia.enlightish.application.service;

import com.synchronia.enlightish.global.exception.ApiException;
import com.synchronia.enlightish.application.service.exception.DatabaseException;
import com.synchronia.enlightish.application.service.exception.ResourceNotFoundException;
import com.synchronia.enlightish.domain.entity.Lead;
import com.synchronia.enlightish.infrastructure.repository.LeadRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class LeadService {

    @Autowired
    public LeadRepository repository;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    public List<Lead> findAll() {
        return repository.findAll();
    }

    public Lead findById(String id) {
        Optional<Lead> lead = repository.findById(id);
        return lead.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public Lead insert(Lead lead) {
        if (repository.existsById(lead.getId())) {
            throw new ApiException(String.format("Um lead com o ID '%s' já existe.", lead.getId()), 409);
        }

        return repository.save(lead);
    }

    public void delete(String id) {
        try {
            if (!repository.existsById(id)) {
                throw new ResourceNotFoundException(id);
            }

            repository.deleteById(id);

        } catch (EmptyResultDataAccessException e) {
            LOGGER.error("Erro ao deletar lead com o ID {}: {}", id, e.getMessage());
            throw new ResourceNotFoundException(id);

        } catch (DataIntegrityViolationException e) {
            LOGGER.error("Violação de integridade do banco de dados ao tentar excluir lead com ID {}: {}", id, e.getMessage());
            throw new DatabaseException(e.getMessage());
        }
    }

    public Lead update(String id, Lead lead) {
        try {
            Lead entity = repository.getReferenceById(id);
            updateData(entity, lead);

            return repository.save(entity);

        } catch (EntityNotFoundException e) {
            LOGGER.error("Lead não encontrado com o ID {}: {}", id, e.getMessage());
            throw new ResourceNotFoundException(id);
        }
    }

    private void updateData(Lead entity, Lead lead) {
        updateIfPresent(entity::setName, lead.getName());
        updateIfPresent(entity::setMail, lead.getMail());
        updateIfPresent(entity::setPhone, lead.getPhone());
        updateIfPresent(entity::setBirthDate, lead.getBirthDate());
    }

    private <T> void updateIfPresent(Consumer<T> setter, T value) {
        if (value != null) {
            setter.accept(value);
        }
    }
}
