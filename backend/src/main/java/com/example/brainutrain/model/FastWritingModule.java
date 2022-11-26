package com.example.brainutrain.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

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
}
