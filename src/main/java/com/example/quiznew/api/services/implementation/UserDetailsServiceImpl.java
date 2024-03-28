package com.example.quiznew.api.services.implementation;

import com.example.quiznew.api.utils.UserDetailsImpl;
import com.example.quiznew.store.entities.User;
import com.example.quiznew.store.repositories.UserRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> user = userRepository.findByUsername(username);

        return user
                .map(UserDetailsImpl::new)
                .orElseThrow(() -> new UsernameNotFoundException(
                        String.format("User with username %s was not found", username)
                ));
    }

}
