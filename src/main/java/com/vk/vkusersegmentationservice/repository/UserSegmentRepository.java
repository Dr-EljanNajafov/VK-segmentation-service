package com.vk.vkusersegmentationservice.repository;

import com.vk.vkusersegmentationservice.model.UserSegment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserSegmentRepository extends JpaRepository<UserSegment, Long> {
}
