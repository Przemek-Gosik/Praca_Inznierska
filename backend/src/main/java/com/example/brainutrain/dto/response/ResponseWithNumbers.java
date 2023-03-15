package com.example.brainutrain.dto.response;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class ResponseWithNumbers {

    private List<Integer> randomNumbers;


    private List<Integer> shuffledRandomNumbers;
}
