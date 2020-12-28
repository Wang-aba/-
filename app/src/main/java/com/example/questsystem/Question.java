package com.example.questsystem;

public class Question {
    public int _id;
    public String question;
    public String keyA;
    public String keyB;
    public String keyC;
    public String keyD;
    //答案
    public int answer;
    //用户选择的答案
    public int answerKey;


    public Question() {
    }

    public Question(String question, String keyA, String keyB, String keyC, String keyD, int answer) {
        this.question = question;
        this.keyA = keyA;
        this.keyB = keyB;
        this.keyC = keyC;
        this.keyD = keyD;
        this.answer = answer;
    }


    @Override
    public String toString() {
        return "Question{" +
                "_id=" + _id +
                ", question='" + question + '\'' +
                ", keyA='" + keyA + '\'' +
                ", keyB='" + keyB + '\'' +
                ", keyC='" + keyC + '\'' +
                ", keyD='" + keyD + '\'' +
                ", answer=" + answer +
                ", answerKey=" + answerKey +
                '}';
    }
}