package com.vk.vkusersegmentationservice.controller;

import com.vk.vkusersegmentationservice.dto.UserSegmentDTO;
import com.vk.vkusersegmentationservice.model.UserSegment;
import com.vk.vkusersegmentationservice.service.UserSegmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/userSegment")
public class UserSegmentController {
    @Autowired
    private UserSegmentService userSegmentService;

    @PostMapping
    public ResponseEntity<UserSegmentDTO> assignUserToSegment(
            @RequestBody UserSegmentDTO userSegmentDTO) {
        UserSegment userSegment = userSegmentService.assignUserToSegment(
                userSegmentDTO.getUserEmail(), userSegmentDTO.getSegmentName()
        );
        return ResponseEntity.ok(new UserSegmentDTO(userSegment.getUser().getEmail(), userSegment.getSegment().getName()));
    }
}
