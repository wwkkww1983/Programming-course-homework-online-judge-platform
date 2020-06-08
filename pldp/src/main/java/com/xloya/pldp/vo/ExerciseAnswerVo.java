package com.xloya.pldp.vo;

import com.xloya.pldp.entity.ExamAnswerinfo;
import com.xloya.pldp.entity.ExerciseQuestion;



public class ExerciseAnswerVo {
    private ExerciseQuestion exerciseQuestion;
    private ExamAnswerinfo examAnswerinfo;
    private String avgScore;

    public ExerciseAnswerVo(){
    }

    public ExerciseAnswerVo(ExerciseQuestion exerciseQuestion){
        this.exerciseQuestion = exerciseQuestion;
    }

    public ExerciseAnswerVo(ExerciseQuestion exerciseQuestion, ExamAnswerinfo examAnswerinfo){
        this.exerciseQuestion = exerciseQuestion;
        this.examAnswerinfo = examAnswerinfo;
    }

    public ExerciseQuestion getExerciseQuestion() {
        return exerciseQuestion;
    }

    public ExamAnswerinfo getExamAnswerinfo() {
        return examAnswerinfo;
    }

    public String getAvgScore() {
        return avgScore;
    }

    public void setAvgScore(String avgScore) {
        this.avgScore = avgScore;
    }
}
