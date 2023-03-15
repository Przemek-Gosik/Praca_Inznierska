package com.example.brainutrain.model;

import com.example.brainutrain.constants.Level;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
@EqualsAndHashCode
@Table(name = "reading_texts")
public class ReadingText {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idReadingText;

    @NotNull
    private String title;

    @Lob
    @NotNull
    private String text;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Level level;

}
