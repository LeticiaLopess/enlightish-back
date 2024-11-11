package com.synchronia.enlightish.presentation.resource;

import com.synchronia.enlightish.application.service.UserService;
import com.synchronia.enlightish.domain.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Base64;
import java.util.List;

@RestController
@RequestMapping(value = "/users")
public class UserResource {

    @Autowired
    private UserService service;

    @GetMapping
    public ResponseEntity<List<User>> findAll() {
        List<User> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<User> findById(@PathVariable String id) {
        User user = service.findById(id);
        return ResponseEntity.ok().body(user);
    }

    @PostMapping
    public ResponseEntity<User> insert(@RequestBody User user) {
        String encodedPassword = Base64.getEncoder().encodeToString(user.getPassword().getBytes());
        user.setPassword(encodedPassword);

        user = service.insert(user);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(uri).body(user);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<User> update(@PathVariable String id, @RequestBody User user) {
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            String passwordBase64 = Base64.getEncoder().encodeToString(user.getPassword().getBytes());
            user.setPassword(passwordBase64);
        }

        user = service.update(id, user);
        return ResponseEntity.ok().body(user);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
