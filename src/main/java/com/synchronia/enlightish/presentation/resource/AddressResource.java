package com.synchronia.enlightish.presentation.resource;


import com.synchronia.enlightish.application.service.AddressService;
import com.synchronia.enlightish.domain.entity.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.net.URI;

@RestController
@RequestMapping(value = "/addresses")
public class AddressResource {

    @Autowired
    private AddressService service;

    @GetMapping
    public ResponseEntity<List<Address>> findAll() {
        List<Address> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Address> findById(@PathVariable String id) {
        Address address = service.findById(id);
        return ResponseEntity.ok().body(address);
    }

    @PostMapping
    public ResponseEntity<Address> insert(@RequestBody Address address) {
        address = service.insert(address);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(address.getId()).toUri();
        return ResponseEntity.created(uri).body(address);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Address> update(@PathVariable String id, @RequestBody Address address) {
        address = service.update(id, address);
        return ResponseEntity.ok().body(address);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
