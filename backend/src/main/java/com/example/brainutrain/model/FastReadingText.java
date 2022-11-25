package com.example.brainutrain.model;

import com.example.brainutrain.constants.Level;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@EqualsAndHashCode
@Table(name = "fastReadingTexts")
public class FastReadingText {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idFastReadingText;

    @NotNull
    private String title;

    @Lob
    @NotNull
    private String text;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Level level;

    @OneToMany
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private List<FastReadingQuestion> questions = new ArrayList<>();
}
