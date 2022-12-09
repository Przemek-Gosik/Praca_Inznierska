package com.example.brainutrain.utils;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.annotation.Repeat;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
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

    @Test
    @Repeat(value = 5)
    public void generateTexts_givenGeneratedCharacters_getTextsContainingOnlyGivenCharacters(){
        String generatedCharacters = "aAbB";
        String [] generateTexts = stringGenerator.generateTexts(generatedCharacters);
        for(int i=0;i<10;i++){
            assertTrue(generateTexts[i].matches("^[A-Ba-b]*$"));
        }
    }
}
