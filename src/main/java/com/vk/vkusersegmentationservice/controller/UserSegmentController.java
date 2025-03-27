package com.vk.vkusersegmentationservice.controller;

import com.vk.vkusersegmentationservice.dto.UserSegmentDTO;
import com.vk.vkusersegmentationservice.model.Segment;
import com.vk.vkusersegmentationservice.model.UserSegment;
import com.vk.vkusersegmentationservice.service.UserSegmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/userSegment")
public class UserSegmentController {
    @Autowired
    private UserSegmentService userSegmentService;

    @PostMapping("/assign")
    public ResponseEntity<UserSegmentDTO> assignUserToSegment(
            @RequestBody UserSegmentDTO userSegmentDTO) {
        UserSegment userSegment = userSegmentService.assignUserToSegment(
                userSegmentDTO.getUserEmail(), userSegmentDTO.getSegmentName()
        );
        return ResponseEntity.ok(new UserSegmentDTO(userSegment.getUser().getEmail(), userSegment.getSegment().getName()));
    }
    @PostMapping("/assign_random")
    public void assignRandomSegmentToUsers(@RequestParam Long segmentId, @RequestParam double percentage) {
        userSegmentService.assignSegmentToRandomUsers(segmentId, percentage);
    }

    // Получение списка сегментов по user_id
    @GetMapping("/user/{userId}")
    public List<String> getUserSegments(@PathVariable Long userId) {
        List<Segment> segments = userSegmentService.getSegmentsByUserId(userId);
        return segments.stream()
                .map(Segment::getName)
                .collect(Collectors.toList());
    }
}
