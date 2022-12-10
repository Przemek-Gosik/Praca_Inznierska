package com.example.brainutrain.mapper.enum_mapper;

import com.example.brainutrain.constants.TypeMemory;

public class TypeMemoryMapper {
    public static TypeMemory getTypeFromString(String type){
        if(type.equals("MEMORY") || type.equals("MNEMONICS")){
            return TypeMemory.valueOf(type);
        }else {
            throw new IllegalArgumentException("Nie odnaleziono typu TypeMemomy dla: "+type);
        }
    }
}
