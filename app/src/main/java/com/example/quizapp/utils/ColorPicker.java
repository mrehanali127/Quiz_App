package com.example.quizapp.utils;

import java.lang.reflect.Array;

public class ColorPicker {
    private static int currentColorIndex=0;
   // private int currentColorIndex=0;
   public static String[] colors = {"#3FB9DF","#FFB9D0","#9F89DF","#32B0DF","#1FB0DF","#2FB9DE"};
    public static String getColor(){
        currentColorIndex=(currentColorIndex+1)%6;
        return colors[currentColorIndex];
    }
}

