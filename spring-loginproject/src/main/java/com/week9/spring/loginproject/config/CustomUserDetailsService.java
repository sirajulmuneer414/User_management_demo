package com.week9.spring.loginproject.config;

import com.week9.spring.loginproject.entity.Users;
import com.week9.spring.loginproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println(username);
        Users user = userRepository.findByUsername(username);
        System.out.println(user.toString());

        if(user == null){
            throw new UsernameNotFoundException(username);
        }
        return new CustomUserPrincipal(user);
    }
}
