package com.example.brainutrain.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name = "fastWritingLessons")
public class FastWritingLesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idFastWritingLesson;

    @Column(nullable = false,unique = true)
    private int number;

    @NotNull
    private String name;

    @NotNull
    @Size(max = 10)
    private String generatedCharacters;
}
