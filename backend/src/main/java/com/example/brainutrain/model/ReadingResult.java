package com.example.brainutrain.model;

import com.example.brainutrain.constants.Level;
import com.example.brainutrain.constants.TypeReading;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "reading_results")
public class ReadingResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idReadingResult;

    @Enumerated(EnumType.STRING)
    @NotNull
    private TypeReading type;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Level level;

    @NotNull
    private Double score;

    @NotNull
    private Timestamp startTime;

    @NotNull
    private float time;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private ReadingText text;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;
}
