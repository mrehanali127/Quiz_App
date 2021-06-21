package com.example.quizapp.models;

public class Question {
    public String description;
    public String option1;
    public String option2;
    public String option3;
    public String option4;
    public String answer;
    public String useranswer;


    public Question(String description, String option1,
                    String option2, String option3,
                    String option4, String answer,String useranswer) {
        this.description = description;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.answer = answer;
        this.useranswer=useranswer;
    }

    public Question(){

    }
}
