package com.vk.vkusersegmentationservice.controller;

import com.vk.vkusersegmentationservice.dto.SegmentDTO;
import com.vk.vkusersegmentationservice.model.Segment;
import com.vk.vkusersegmentationservice.service.SegmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/segments")
public class SegmentController {

    @Autowired
    private SegmentService segmentService;

    @GetMapping
    public List<SegmentDTO> getAllSegments() {
        return segmentService.getAllSegments().stream()
                .map(segment -> new SegmentDTO(segment.getId(), segment.getName(), segment.getDescription()))
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SegmentDTO> getSegmentById(@PathVariable Long id) {
        Segment segment = segmentService.getSegmentById(id);
        return ResponseEntity.ok(new SegmentDTO(segment.getId(), segment.getName(), segment.getDescription()));
    }
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SegmentDTO> createSegment(@RequestBody Segment segment) {
        Segment savedSegment = segmentService.createSegment(segment);
        return ResponseEntity.ok(new SegmentDTO(savedSegment.getId(), savedSegment.getName(), savedSegment.getDescription()));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SegmentDTO> updateSegment(@PathVariable Long id, @RequestBody SegmentDTO segmentDTO) {
        Segment segment = segmentService.updateSegment(id, segmentDTO.getName(), segmentDTO.getDescription());
        return ResponseEntity.ok(new SegmentDTO(segment.getId(), segment.getName(), segment.getDescription()));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteSegment(@PathVariable Long id) {
        segmentService.deleteSegment(id);
        return ResponseEntity.noContent().build();
    }
}
