package com.example.lisvikproject.Models;

public class Question {
    public String question;
    public String answer1, answer2, answer3, correctAnswer;

    public Question(String question, String answer1, String answer2, String answer3, String correctAnswer)
    {
        this.question=question;
        this.answer1=answer1;
        this.answer2=answer2;
        this.answer3=answer3;
        this.correctAnswer=correctAnswer;
    }

    public Question(){}

    public String getQuestion(){return question;}
    public void setQuestion(String question){this.question=question;}

    public String getAnswer1(){return answer1;}
    public void setAnswer1(String answer1){this.answer1=answer1;}

    public String getAnswer2(){return answer2;}
    public void setAnswer2(String answer2){this.answer2=answer2;}

    public String getAnswer3(){return answer3;}
    public void setAnswer3(String answer3){this.answer3=answer3;}

    public String getCorrectAnswer(){return correctAnswer;}
    public void setCorrectAnswer(String correctAnswer){this.correctAnswer=correctAnswer;}
}
