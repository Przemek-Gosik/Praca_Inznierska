package com.example.brainutrain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="users",uniqueConstraints = {@UniqueConstraint(columnNames = "email" ),
        @UniqueConstraint(columnNames = "login")})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUser;

    @Column(unique = true, length = 45)
    private String login;

    @Column(name="email",unique = true, length = 45)
    private String email;

    @Column(length = 120)
    private String password;

    @Column(name="isEmailConfirmed")
    private Boolean isEmailConfirmed;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="User_roles",
    joinColumns = @JoinColumn(name="User_idUser"),
            inverseJoinColumns = @JoinColumn(name="Role_idRole"))
    private Set<Role> roles = new HashSet<>();
}
