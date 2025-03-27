package com.vk.vkusersegmentationservice.repository;

import com.vk.vkusersegmentationservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
