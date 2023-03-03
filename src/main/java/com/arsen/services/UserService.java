package com.arsen.services;

import com.arsen.models.User;
import com.arsen.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public String updateUserById(Long id, String name) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null)
            return null;

        user.setName(name);
        userRepository.save(user);
        return "User id: " + id + " new name " + name;
    }

}
