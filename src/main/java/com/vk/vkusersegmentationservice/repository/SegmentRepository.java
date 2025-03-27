package com.vk.vkusersegmentationservice.repository;

import com.vk.vkusersegmentationservice.model.Segment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SegmentRepository extends JpaRepository<Segment, Long> {
    Optional<Segment> findByName(String segmentName);
}
