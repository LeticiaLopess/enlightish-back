package com.synchronia.enlightish.presentation.resource;

import com.synchronia.enlightish.application.service.LeadService;
import com.synchronia.enlightish.application.service.exception.ResourceNotFoundException;
import com.synchronia.enlightish.domain.entity.Lead;
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
@RequestMapping(value = "/leads")
@Tag(name = "Lead", description = "Gerenciamento de leads.")
public class LeadResource {

    @Autowired
    private LeadService service;

    @Operation(summary = "Busca todos os leads.",description = "Retorna todos os leads.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna os leads."),
            @ApiResponse(responseCode = "404", description = "Nenhum lead foi encontrado."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor.")
    })
    @GetMapping
    public ResponseEntity<List<Lead>> findAll() {
        try {
            List<Lead> list = service.findAll();
            return ResponseEntity.ok().body(list);

        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @Operation(summary = "Busca um lead.", description = "Busca um lead pelo ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna o lead."),
            @ApiResponse(responseCode = "404", description = "Não existe lead com o ID informado."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor.")
    })
    @GetMapping(value = "/{id}")
    public ResponseEntity<Lead> findById(@PathVariable String id) {
        try {
            Lead lead = service.findById(id);
            return ResponseEntity.ok().body(lead);

        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(404).body(null);

        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @Operation(summary = "Insere um lead.", description = "Adiciona um novo lead ao sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Lead criado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou incompletos para criação do lead."),
            @ApiResponse(responseCode = "409", description = "Um lead com o ID informado já existe."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor.")
    })
    @PostMapping
    public ResponseEntity<Lead> insert(@RequestBody Lead lead) {
        try {
            lead = service.insert(lead);
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(lead.getId()).toUri();
            return ResponseEntity.created(uri).body(lead);

        } catch (ApiException e) {
            if (e.getStatusCode() == 409) {
                return ResponseEntity.status(409).body(null);
            }

            return ResponseEntity.status(e.getStatusCode()).body(null);

        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @Operation(summary = "Atualiza um lead.", description = "Atualiza dados de um lead pelo ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lead atualizado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou incompletos para atualização do lead."),
            @ApiResponse(responseCode = "404", description = "Lead não encontrado."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor.")
    })
    @PutMapping(value = "/{id}")
    public ResponseEntity<Lead> update(@PathVariable String id, @RequestBody Lead lead) {
        try {
            lead = service.update(id, lead);
            return ResponseEntity.ok().body(lead);

        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(404).body(null);

        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @Operation(summary = "Deleta um lead.", description = "Deleta um lead pelo ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Lead deletado com sucesso."),
            @ApiResponse(responseCode = "400", description = "ID inválido ou dados incompletos para a exclusão do lead."),
            @ApiResponse(responseCode = "404", description = "Lead não encontrado."),
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
