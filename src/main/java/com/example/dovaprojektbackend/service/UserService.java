package com.example.dovaprojektbackend.service;

import com.example.dovaprojektbackend.model.User;
import com.example.dovaprojektbackend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserById(UUID userId){
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
    }

    public User updateUser(UUID userId,User updatedUser){
        User user = getUserById(userId);

        // Opdater felter
        if (updatedUser.getName() != null) {
            user.setName(updatedUser.getName());
        }
        if (updatedUser.getEmail() != null) {
            user.setEmail(updatedUser.getEmail());
        }
        if (updatedUser.getPhone() != null) {
            user.setPhone(updatedUser.getPhone());
        }
        if (updatedUser.getAddress() != null) {
            user.setAddress(updatedUser.getAddress());
        }

        return userRepository.save(user);
    }

    public void deleteUser(UUID userId){
        User user = getUserById(userId);
        userRepository.delete(user);
    }
}
