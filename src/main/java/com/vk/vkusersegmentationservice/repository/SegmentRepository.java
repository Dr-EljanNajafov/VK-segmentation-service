package com.vk.vkusersegmentationservice.repository;

import com.vk.vkusersegmentationservice.model.Segment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SegmentRepository extends JpaRepository<Segment, Long> {
}
