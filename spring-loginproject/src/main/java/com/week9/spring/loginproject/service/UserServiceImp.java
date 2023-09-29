package com.week9.spring.loginproject.service;

import com.week9.spring.loginproject.entity.Users;
import com.week9.spring.loginproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserServiceImp implements UserService{

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Users findUserById(Long id) {
        return userRepository.findById(id).get();
    }

    @Override
    public void saveUser(Users user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if(userRepository.count() < 1){
            user.setUserrole("ADMIN");
        }

        userRepository.save(user);

    }

    @Override
    public List<Users> listOfUsers() {


        return userRepository.findAll();
    }

    @Override
    public void deleteUserById(Long id) {

        userRepository.deleteById(id);

    }

    @Override
    public void updateById(Long id, Users user) {
        Users userToUpdate = userRepository.findById(id).get();

        if(Objects.nonNull(user.getUsername()) && !"".equalsIgnoreCase(user.getUsername())){
            userToUpdate.setUsername(user.getUsername());
        }

        if(Objects.nonNull(user.getUseremail()) && !"".equalsIgnoreCase(user.getUseremail())){
            userToUpdate.setUseremail(user.getUseremail());
        }

        if(Objects.nonNull(user.getUserrole()) && !"".equalsIgnoreCase(user.getUserrole())){
            userToUpdate.setUserrole(user.getUserrole());
        }


        userRepository.save(userToUpdate);
    }

    @Override
    public void saveAdmin(Users user) {
        Users userToSetAdmin = userRepository.findByUsername(user.getUsername());
        userToSetAdmin.setPassword(passwordEncoder.encode(user.getPassword()));
        userToSetAdmin.setUserrole("ADMIN");
        userRepository.save(userToSetAdmin);
    }

    @Override
    public boolean doUserNameExists(String userName) {
        return userRepository.existsByUsername(userName);
    }

    @Override
    public boolean doUserEmailExists(String userEmail) {
        return userRepository.existsByUseremail(userEmail);
    }

    @Override
    public List<Users> listUsersStartingWith(String startingWith) {
        return userRepository.findByUsernameContaining(startingWith);
    }


}
