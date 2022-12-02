package com.example.brainutrain.utils;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Random;

@Service
@NoArgsConstructor
public class StringGenerator {

    public String generateCode(){
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();
        for(int i=0;i<5;i++){
            int randNumber=random.nextInt(10);
            stringBuilder.append(randNumber);
        }
        return stringBuilder.toString();
    }

    public String generatePassword(){
        final int LENGTH_LIMIT=8;
        final int MIN_CHAR = 48;
        final int MAX_CHAR = 122;
        Random random = new Random();
        return random.ints(MIN_CHAR,MAX_CHAR+1)
                .filter(c->(c<=57 || c>=63) && (c<=90 || c>=97))
                .limit(LENGTH_LIMIT)
                .collect(StringBuilder::new,StringBuilder::appendCodePoint,
                        StringBuilder::append)
                .toString();
    }

}
