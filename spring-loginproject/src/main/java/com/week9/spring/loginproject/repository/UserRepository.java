package com.week9.spring.loginproject.repository;

import com.week9.spring.loginproject.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {



    Users findByUsername(String username);

    boolean existsByUsername(String userName);

    boolean existsByUseremail(String userEmail);

    List<Users> findByUsernameContaining(String startingWith);
}
