package com.vk.vkusersegmentationservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserSegmentDTO {
    private Long userId;
    private Long segmentId;
}
