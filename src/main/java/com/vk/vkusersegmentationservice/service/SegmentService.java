package com.vk.vkusersegmentationservice.service;

import com.vk.vkusersegmentationservice.model.Segment;
import com.vk.vkusersegmentationservice.repository.SegmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SegmentService {

    @Autowired
    private SegmentRepository segmentRepository;

    public List<Segment> getAllSegments() {
        return segmentRepository.findAll();
    }

    public Segment getSegmentById(Long id) {
        return segmentRepository.findById(id).orElseThrow(() -> new RuntimeException("Segment not found"));
    }

    public Segment createSegment(Segment segment) {
        return segmentRepository.save(segment);
    }
}
