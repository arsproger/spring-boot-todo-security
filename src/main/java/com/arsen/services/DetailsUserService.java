package com.arsen.services;

import com.arsen.models.User;
import com.arsen.repositories.UserRepository;
import com.arsen.security.DetailsUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DetailsUserService implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public DetailsUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(username);

        if(user.isEmpty())
            throw new UsernameNotFoundException("User not found!");

        return new DetailsUser(user.get());
    }
}
