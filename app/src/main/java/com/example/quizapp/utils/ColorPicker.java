package com.example.quizapp.utils;

import java.lang.reflect.Array;

public class ColorPicker {
    static String[] colors = {
            "#3EB9DF",
            "#3685BC",
            "#D36280",
            "#E44F55",
            "#FA8056",
            "#818BCA",
            "#7D659F",
            "#51BAB3",
            "#4FB66C",
            "#E3AD17",
            "#EF8EAD",
            "#B5BFC6"

    };
    static int currentColorIndex = 0;
    public static String getColor(){
        currentColorIndex = (currentColorIndex + 1) % colors.length;
        return colors[currentColorIndex];
    }
}
