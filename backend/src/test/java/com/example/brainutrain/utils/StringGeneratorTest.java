package com.example.brainutrain.utils;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.annotation.Repeat;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith({MockitoExtension.class})
public class StringGeneratorTest {

    @InjectMocks
    private StringGenerator stringGenerator;

    @Test
    @Repeat(value = 5)
    public void generateCode_checkIfCode_containsOnlyNumbers(){
        String result = stringGenerator.generateCode();
        assertTrue(result.matches("\\d+"));
    }

    @Test
    @Repeat(value = 5)
    public void generatePassword_checkIfPassword_doNotContainsBlankSpaces(){
        String result = stringGenerator.generatePassword();
        assertFalse(result.contains(" "));
    }
}
