package com.example.brainutrain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnTransformer;

import javax.persistence.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idUser;

    @Column(unique = true,length = 45)
    private String login;

    @Column(unique = true,length = 45)
    private String email;

    @Column(length = 45)
    @ColumnTransformer(read = "AES_DECRYPT(UNHEX(password), 'SECRET KEY')", write = "HEX(AES_ENCRYPT(?, 'SECRET KEY'))")
    private String password;



}
