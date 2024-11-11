package com.synchronia.enlightish.application.service;

import com.synchronia.enlightish.global.exception.ApiException;
import com.synchronia.enlightish.application.service.exception.DatabaseException;
import com.synchronia.enlightish.application.service.exception.ResourceNotFoundException;
import com.synchronia.enlightish.domain.entity.User;
import com.synchronia.enlightish.infrastructure.repository.UserRepository;
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
public class UserService {

    @Autowired
    private UserRepository repository;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    public List<User> findAll() {
        return repository.findAll();
    }

    public User findById(String id) {
        Optional<User> user = repository.findById(id);
        return user.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public User insert(User user) {
        if (repository.existsById(user.getId())) {
            throw new ApiException(String.format("Um usuário com o ID '%s' já existe.", user.getId()), 409);
        }

        return repository.save(user);
    }

    public void delete(String id) {
        try {
            if (!repository.existsById(id)) {
                throw new ResourceNotFoundException(id);
            }

            repository.deleteById(id);

        } catch (EmptyResultDataAccessException e) {
            LOGGER.error("Erro ao deletar usuário com o ID {}: {}", id, e.getMessage());
            throw new ResourceNotFoundException(id);

        } catch (DataIntegrityViolationException e) {
            LOGGER.error("Violação de integridade do banco de dados ao tentar excluir usuário com ID {}: {}", id, e.getMessage());
            throw new DatabaseException(e.getMessage());
        }
    }

    public User update(String id, User user) {
        try {
            User entity = repository.getReferenceById(id);
            updateData(entity, user);

            return repository.save(entity);

        } catch (EntityNotFoundException e) {
            LOGGER.error("Usuário não encontrado com o ID {}: {}", id, e.getMessage());
            throw new ResourceNotFoundException(id);
        }
    }

    private void updateData(User entity, User user) {
        updateIfPresent(entity::setUsername, user.getUsername());
        updateIfPresent(entity::setPassword, user.getPassword());
        updateIfPresent(entity::setName, user.getName());
        updateIfPresent(entity::setMail, user.getMail());
        updateIfPresent(entity::setPhone, user.getPhone());
        updateIfPresent(entity::setBirthDate, user.getBirthDate());
    }

    private <T> void updateIfPresent(Consumer<T> setter, T value) {
        if (value != null) {
            setter.accept(value);
        }
    }
}
