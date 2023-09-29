package com.week9.spring.loginproject.service;

import com.week9.spring.loginproject.entity.Users;

import java.util.List;

public interface UserService {

   public Users findUserById(Long id);
   public void saveUser(Users user);

   public List<Users> listOfUsers();

   public void deleteUserById(Long id);

   public void updateById(Long id, Users user);

   public void saveAdmin(Users user);

   public boolean doUserNameExists(String userName);

   public boolean doUserEmailExists(String userName);

   public List<Users> listUsersStartingWith(String startingWith);





}
