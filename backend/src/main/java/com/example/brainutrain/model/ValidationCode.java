package com.example.brainutrain.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Entity
public class ValidationCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idValidationCode;

    @NotBlank
    private String code;

    @NotNull
    @OneToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;
}
