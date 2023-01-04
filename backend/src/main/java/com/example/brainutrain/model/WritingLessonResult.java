package com.example.brainutrain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
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
@Table(name = "writing_lesson_results")
public class WritingLessonResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idWritingLessonResult;

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

    @Min(value = 0)
    private int numberOfTypedLetters;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private WritingLesson writingLesson;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    public WritingLessonResult(Long idWritingLessonResult, Double score){
        this.idWritingLessonResult = idWritingLessonResult;
        this.score = score;
    }
}
