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
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class UserSegmentService {

    @Autowired
    private UserSegmentRepository userSegmentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SegmentRepository segmentRepository;

    public UserSegment assignUserToSegment(String userEmail, String segmentName) {
        Optional<User> userOptional = userRepository.findByEmail(userEmail);
        if (userOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        User user = userOptional.get();

        Optional<Segment> segmentOptional = segmentRepository.findByName(segmentName);
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

    // Назначение сегмента случайным образом определённому проценту пользователей
    public void assignSegmentToRandomUsers(Long segmentId, double percentage) {
        Segment segment = segmentRepository.findById(segmentId)
                .orElseThrow(() -> new RuntimeException("Segment not found"));

        List<User> allUsers = userRepository.findAll();
        int userCount = allUsers.size();
        int usersToAssign = (int) (userCount * percentage);

        Random random = new Random();

        // Выбираем случайных пользователей
        for (int i = 0; i < usersToAssign; i++) {
            User user = allUsers.get(random.nextInt(userCount));
            UserSegment userSegment = new UserSegment(null, user, segment, null);
            userSegmentRepository.save(userSegment);
        }
    }

    public List<Segment> getSegmentsByUserId(Long userId) {
        return userSegmentRepository.findSegmentsByUserId(userId);
    }
}
