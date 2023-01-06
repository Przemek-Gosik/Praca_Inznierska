package com.example.brainutrain.model;

import com.example.brainutrain.constants.Level;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="writing_texts")
public class WritingText {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idWritingText;

    @Column(nullable = false)
    private String title;

    @Lob
    @Column(nullable = false)
    private String text;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column
    private Level level;
}
