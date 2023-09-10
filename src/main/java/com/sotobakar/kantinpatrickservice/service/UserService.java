package com.sotobakar.kantinpatrickservice.service;

import com.sotobakar.kantinpatrickservice.model.User;
import com.sotobakar.kantinpatrickservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findOrCreateUserByEmail(String email) {
        User user = this.userRepository.findUserByEmail(email).orElse(null);

        if (user != null) {
            return user;
        } else {
            user = new User();
            user.setEmail(email);

            return this.userRepository.save(user);
        }
    }
}
