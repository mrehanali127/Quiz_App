package com.example.quizapp.models;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.HashMap;
import java.util.Map;


@RequiresApi(api = Build.VERSION_CODES.R)
public class Quiz {
    public String id;
    public String title;
    Map<String, Question> questions = Map.of();

    public Quiz(String id, String title) {
        this.id = id;
        this.title = title;
    }
}
