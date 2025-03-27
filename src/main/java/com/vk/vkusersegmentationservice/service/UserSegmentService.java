package com.vk.vkusersegmentationservice.service;

import com.vk.vkusersegmentationservice.model.Segment;
import com.vk.vkusersegmentationservice.model.User;
import com.vk.vkusersegmentationservice.model.UserSegment;
import com.vk.vkusersegmentationservice.repository.SegmentRepository;
import com.vk.vkusersegmentationservice.repository.UserRepository;
import com.vk.vkusersegmentationservice.repository.UserSegmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserSegmentService {

    @Autowired
    private UserSegmentRepository userSegmentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SegmentRepository segmentRepository;

    /**
     * Назначение пользователя на сегмент.
     * Проверяем существование пользователя и сегмента в базе данных перед созданием связи.
     * @param userId Идентификатор пользователя.
     * @param segmentId Идентификатор сегмента.
     * @return Сохраненная связь между пользователем и сегментом.
     * @throws ResponseStatusException если пользователь или сегмент не найдены.
     */

    public UserSegment assignUserToSegment(Long userId, Long segmentId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        User user = userOptional.get();

        Optional<Segment> segmentOptional = segmentRepository.findById(segmentId);
        if (segmentOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Segment not found");
        }
        Segment segment = segmentOptional.get();

        UserSegment userSegment = new UserSegment();
        userSegment.setUser(user);
        userSegment.setSegment(segment);
        userSegment.setAssignedAt(LocalDateTime.now());

        return userSegmentRepository.save(userSegment);
    }
}
