package com.vk.vkusersegmentationservice.service;

import com.vk.vkusersegmentationservice.model.Segment;
import com.vk.vkusersegmentationservice.repository.SegmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public Segment updateSegment(Long id, String name, String description) {
        Optional<Segment> segmentOptional = segmentRepository.findById(id);
        if (segmentOptional.isPresent()) {
            Segment segment = segmentOptional.get();
            segment.setName(name);
            segment.setDescription(description);
            return segmentRepository.save(segment);
        }
        throw new RuntimeException("Segment not found");
    }

    public void deleteSegment(Long id) {
        segmentRepository.deleteById(id);
    }
}
