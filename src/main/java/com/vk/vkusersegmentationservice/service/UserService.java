package com.vk.vkusersegmentationservice.service;

import com.vk.vkusersegmentationservice.model.User;
import com.vk.vkusersegmentationservice.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Transactional(rollbackOn = Exception.class)
    public User createUser(User user) {
        try {
            return userRepository.save(user);
        } catch (Exception e) {
            // Пробрасываем исключение, чтобы откатить транзакцию
            throw new RuntimeException("Error creating user", e);
        }
    }

    @Transactional
    public User updateUser(Long id, User userDetails) {
        User user = getUserById(id);
        user.setEmail(userDetails.getEmail());
        return userRepository.save(user);
    }

    @Transactional
    public void deleteUser(Long id) {
        User user = getUserById(id);
        userRepository.delete(user);
    }
}
