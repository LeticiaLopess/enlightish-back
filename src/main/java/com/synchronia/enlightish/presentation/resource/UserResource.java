package com.synchronia.enlightish.presentation.resource;

import com.synchronia.enlightish.application.service.UserService;
import com.synchronia.enlightish.application.service.exception.ResourceNotFoundException;
import com.synchronia.enlightish.domain.entity.User;
import com.synchronia.enlightish.global.exception.ApiException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Base64;
import java.util.List;

@RestController
@RequestMapping(value = "/users")
@Tag(name = "Usuário", description = "Gerenciamento de usuários.")
public class UserResource {

    @Autowired
    private UserService service;

    @Operation(summary = "Busca todos os usuários.",description = "Retorna todos os usuários.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna os usuários."),
            @ApiResponse(responseCode = "404", description = "Nenhum usuário foi encontrado."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor.")
    })
    @GetMapping
    public ResponseEntity<List<User>> findAll() {
        try {
            List<User> list = service.findAll();
            return ResponseEntity.ok().body(list);

        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @Operation(summary = "Busca um usuário.", description = "Busca um usuário pelo ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna o usuário."),
            @ApiResponse(responseCode = "404", description = "Não existe usuário com o ID informado."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor.")
    })
    @GetMapping(value = "/{id}")
    public ResponseEntity<User> findById(@PathVariable String id) {
        try {
            User user = service.findById(id);
            return ResponseEntity.ok().body(user);

        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(404).body(null);

        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @Operation(summary = "Insere um usuário.", description = "Adiciona um novo usuário ao sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou incompletos para criação do usuário."),
            @ApiResponse(responseCode = "409", description = "Um usuário com o ID informado já existe."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor.")
    })
    @PostMapping
    public ResponseEntity<User> insert(@RequestBody User user) {
        try {
            String encodedPassword = Base64.getEncoder().encodeToString(user.getPassword().getBytes());
            user.setPassword(encodedPassword);

            user = service.insert(user);
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(user.getId()).toUri();

            return ResponseEntity.created(uri).body(user);

        } catch (ApiException e) {
            if (e.getStatusCode() == 409) {
                return ResponseEntity.status(409).body(null);
            }

            return ResponseEntity.status(e.getStatusCode()).body(null);

        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @Operation(summary = "Atualiza um usuário.", description = "Atualiza dados de um usuário pelo ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou incompletos para atualização do usuário."),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor.")
    })
    @PutMapping(value = "/{id}")
    public ResponseEntity<User> update(@PathVariable String id, @RequestBody User user) {
        try {
            if (user.getPassword() != null && !user.getPassword().isEmpty()) {
                String passwordBase64 = Base64.getEncoder().encodeToString(user.getPassword().getBytes());
                user.setPassword(passwordBase64);
            }
            user = service.update(id, user);
            return ResponseEntity.ok().body(user);

        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(404).body(null);

        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @Operation(summary = "Deleta um usuário.", description = "Deleta um usuário pelo ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Endereço deletado com sucesso."),
            @ApiResponse(responseCode = "400", description = "ID inválido ou dados incompletos para a exclusão do usuário."),
            @ApiResponse(responseCode = "404", description = "Endereço não encontrado."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor.")
    })
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        try {
            boolean deleted = service.delete(id);
            if (!deleted) {
                return ResponseEntity.status(404).build();
            }
            return ResponseEntity.noContent().build();

        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
}
