package com.week9.spring.loginproject.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Users {
    @Id
    @SequenceGenerator(
            name = "user_id",
            sequenceName = "user_id",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.IDENTITY,
            generator = "user_id"
    )
    private Long userid;

    @Column(
            unique = true,
            nullable = false
    )
    private String username;

    @Email
    private String useremail;


    private String password;

    @Column(
            nullable = false,
            columnDefinition = "VARCHAR(20) DEFAULT 'USER'"
    )
    private String userrole = "USER";




}
