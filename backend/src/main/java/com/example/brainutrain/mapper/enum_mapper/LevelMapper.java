package com.example.brainutrain.mapper.enum_mapper;

import com.example.brainutrain.constants.Level;

public class LevelMapper {

    public static Level getLevelFromString(String level){
        if(level.equals("ADVANCED") || level.equals("MEDIUM") || level.equals("EASY")){
            return Level.valueOf(level);
        }else {
            throw new IllegalArgumentException("Nie odnaleziono wartości klasy level dla: "+level);
        }
    }
}
