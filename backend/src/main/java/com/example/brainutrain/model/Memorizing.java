package com.example.brainutrain.model;

import com.example.brainutrain.constants.Level;
import com.example.brainutrain.constants.TypeMemory;
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
@Table(name="memorizings")
public class Memorizing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMemorizing;

    @NotNull
    @Enumerated(EnumType.STRING)
    private TypeMemory type;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Level level;

    @NotNull
    private Long score;

    @NotNull
    private Timestamp startTime;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;
}
