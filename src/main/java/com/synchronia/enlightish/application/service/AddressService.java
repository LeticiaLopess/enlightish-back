package com.synchronia.enlightish.application.service;

import com.synchronia.enlightish.global.exception.ApiException;
import com.synchronia.enlightish.application.service.exception.ResourceNotFoundException;
import com.synchronia.enlightish.domain.entity.Address;
import com.synchronia.enlightish.infrastructure.repository.AddressRepository;
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
public class AddressService {

    @Autowired
    private AddressRepository repository;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    public List<Address> findAll() {
        try {
            return repository.findAll();

        } catch (Exception e) {
            LOGGER.error("Erro ao buscar os endereços: {}", e.getMessage());
            throw new ApiException("Erro ao buscar os endereços.", 500);
        }
    }

    public Address findById(String id) {
        try {
            Optional<Address> address = repository.findById(id);
            return address.orElseThrow(() -> new ResourceNotFoundException(id));

        } catch (Exception e) {
            LOGGER.error("Erro ao buscar endereço com o ID {}: {}", id, e.getMessage());
            throw new ApiException("Erro ao buscar o endereço.", 500);
        }
    }

    public Address insert(Address address) {
        try {
            if (repository.existsById(address.getId())) {
                throw new ApiException("Endereço já existe com o ID " + address.getId(), 409);
            }

            return repository.save(address);

        } catch (Exception e) {
            LOGGER.error("Erro ao inserir endereço: {}", e.getMessage());
            throw new ApiException("Erro ao inserir o endereço", 500);
        }
    }

    public boolean delete(String id) {
        try {
            if (!repository.existsById(id)) {
                throw new ResourceNotFoundException(id);
            }

            repository.deleteById(id);
            return true;

        } catch (EmptyResultDataAccessException e) {
            LOGGER.error("Erro ao deletar endereço com o ID {}: {}.", id, e.getMessage());
            throw new ResourceNotFoundException(id);

        } catch (DataIntegrityViolationException e) {
            LOGGER.error("Violação de integridade do banco de dados ao tentar excluir endereço com ID {}: {}.", id, e.getMessage());
            throw new ApiException("Erro ao tentar excluir o endereço devido a uma violação de integridade do banco de dados.", 500);

        } catch (Exception e) {
            LOGGER.error("Ocorreu um erro inesperado ao tentar deletar o endereço com o ID {}: {}.", id, e.getMessage());
            return false;
        }
    }

    public Address update(String id, Address address) {
        try {
            Address entity = repository.getReferenceById(id);
            updateData(entity, address);

            return repository.save(entity);

        } catch (EntityNotFoundException e) {
            LOGGER.error("Endereço não encontrado com o ID {}: {}.", id, e.getMessage());
            throw new ResourceNotFoundException(id);
        }
    }

    private void updateData(Address entity, Address address) {
        updateIfPresent(entity::setStreet, address.getStreet());
        updateIfPresent(entity::setNumber, address.getNumber());
        updateIfPresent(entity::setNeighborhood, address.getNeighborhood());
        updateIfPresent(entity::setCity, address.getCity());
        updateIfPresent(entity::setComplement, address.getComplement());
        updateIfPresent(entity::setState, address.getState());
        updateIfPresent(entity::setCountry, address.getCountry());
        updateIfPresent(entity::setZipCode, address.getZipCode());
    }

    private <T> void updateIfPresent(Consumer<T> setter, T value) {
        if (value != null) {
            setter.accept(value);
        }
    }

}
