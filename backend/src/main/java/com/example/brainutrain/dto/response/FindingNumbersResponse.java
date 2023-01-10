package com.example.brainutrain.dto.response;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class FindingNumbersResponse {
    private List<Integer> numbersToFind = new ArrayList<>();
    private List<List<Long>> schultzTables = new ArrayList<>();
}
