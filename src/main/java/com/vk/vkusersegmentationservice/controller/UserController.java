package com.vk.vkusersegmentationservice.controller;

import com.vk.vkusersegmentationservice.dto.UserDTO;
import com.vk.vkusersegmentationservice.model.User;
import com.vk.vkusersegmentationservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers().stream()
                .map(user -> new UserDTO(user.getId(), user.getEmail()))
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(new UserDTO(user.getId(), user.getEmail()));
    }


    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody User user) {
        user.setCreatedAt(LocalDateTime.now());
        User savedUser = userService.createUser(user);
        return ResponseEntity.ok(new UserDTO(savedUser.getId(), savedUser.getEmail()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        User user = userService.updateUser(id, userDetails);
        return ResponseEntity.ok(new UserDTO(user.getId(), user.getEmail()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}

