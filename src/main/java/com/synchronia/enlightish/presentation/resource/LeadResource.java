package com.synchronia.enlightish.presentation.resource;

import com.synchronia.enlightish.application.service.LeadService;
import com.synchronia.enlightish.domain.entity.Lead;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.UUID;
import java.net.URI;

@RestController
@RequestMapping(value = "/leads")
public class LeadResource {

    @Autowired
    private LeadService service;

    @GetMapping
    public ResponseEntity<List<Lead>> findAll() {
        List<Lead> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Lead> findById(@PathVariable UUID id) {
        Lead lead = service.findById(id);
        return ResponseEntity.ok().body(lead);
    }

    @PostMapping
    public ResponseEntity<Lead> insert(@RequestBody Lead lead) {
        lead = service.insert(lead);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(lead.getId()).toUri();
        return ResponseEntity.created(uri).body(lead);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Lead> update(@PathVariable UUID id, @RequestBody Lead lead) {
        lead = service.update(id, lead);
        return ResponseEntity.ok().body(lead);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
