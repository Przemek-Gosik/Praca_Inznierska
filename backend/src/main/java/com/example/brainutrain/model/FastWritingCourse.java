package com.example.brainutrain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "fastWritingCourses")
public class FastWritingCourse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idFastWritingCourse;

    @NotNull
    @Column
    @Min(value = 0)
    @Max(value = 1)
    private Double score;

    @NotNull
    @Column
    private Timestamp startTime;

    @NotNull
    @Column
    private float time;

    @Min(value = 0)
    private int numberOfAttempts;
}
