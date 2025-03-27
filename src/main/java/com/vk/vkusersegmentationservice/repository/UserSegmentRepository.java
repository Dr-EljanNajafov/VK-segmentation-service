package com.vk.vkusersegmentationservice.repository;

import com.vk.vkusersegmentationservice.model.Segment;
import com.vk.vkusersegmentationservice.model.UserSegment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserSegmentRepository extends JpaRepository<UserSegment, Long> {
    List<Segment> findSegmentsByUserId(Long userId);
}
