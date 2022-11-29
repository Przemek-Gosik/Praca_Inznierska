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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name="fastWritingModules")
public class FastWritingModule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idFastWritingModule;

    @NotNull
    @Min(value = 0)
    private int number;

    @NotNull
    private String name;

    @OneToMany
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<FastWritingLesson> fastWritingLessons;
}
