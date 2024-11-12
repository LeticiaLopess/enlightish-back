package com.synchronia.enlightish.presentation.resource;

import com.synchronia.enlightish.application.service.AddressService;
import com.synchronia.enlightish.application.service.exception.ResourceNotFoundException;
import com.synchronia.enlightish.domain.entity.Address;
import com.synchronia.enlightish.global.exception.ApiException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.net.URI;

@RestController
@RequestMapping(value = "/addresses")
@Tag(name = "Endereço", description = "Gerenciamento de endereços.")
public class AddressResource {

    @Autowired
    private AddressService service;

    @Operation(summary = "Busca todos os endereços.",description = "Retorna todos os endereços.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna os endereços."),
            @ApiResponse(responseCode = "404", description = "Nenhum endereço foi encontrado."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor.")
    })
    @GetMapping
    public ResponseEntity<List<Address>> findAll() {
        try {
            List<Address> list = service.findAll();
            return ResponseEntity.ok().body(list);

        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @Operation(summary = "Busca um endereço.", description = "Busca um endereço pelo ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna o endereço."),
            @ApiResponse(responseCode = "404", description = "Não existe endereço com o ID informado."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor.")
    })
    @GetMapping(value = "/{id}")
    public ResponseEntity<Address> findById(@PathVariable String id) {
        try {
            Address address = service.findById(id);
            return ResponseEntity.ok().body(address);

        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(404).body(null);

        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @Operation(summary = "Insere um endereço.", description = "Adiciona um novo endereço ao sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Endereço criado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou incompletos para criação do endereço."),
            @ApiResponse(responseCode = "409", description = "Um endereço com o ID informado já existe."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor.")
    })
    @PostMapping
    public ResponseEntity<Address> insert(@RequestBody Address address) {
        try {
            address = service.insert(address);
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(address.getId()).toUri();
            return ResponseEntity.created(uri).body(address);

        } catch (ApiException e) {
            if (e.getStatusCode() == 409) {
                return ResponseEntity.status(409).body(null);
            }

            return ResponseEntity.status(e.getStatusCode()).body(null);

        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @Operation(summary = "Atualiza um endereço.", description = "Atualiza dados de um endereço pelo ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Endereço atualizado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou incompletos para atualização do endereço."),
            @ApiResponse(responseCode = "404", description = "Usuário associado ou endereço não encontrado."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor.")
    })
    @PutMapping(value = "/{id}")
    public ResponseEntity<Address> update(@PathVariable String id, @RequestBody Address address) {
        try {
            address = service.update(id, address);
            return ResponseEntity.ok().body(address);

        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(404).body(null);

        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @Operation(summary = "Deleta um endereço.", description = "Deleta um endereço pelo ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Endereço deletado com sucesso."),
            @ApiResponse(responseCode = "400", description = "ID inválido ou dados incompletos para a exclusão do endereço."),
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
