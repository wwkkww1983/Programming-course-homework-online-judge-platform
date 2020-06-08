package com.xloya.pldp.vo;

import com.xloya.pldp.entity.ExamAnswerinfo;
import com.xloya.pldp.entity.FillBlankQuestion;



public class BlankAnswerVo {
    private FillBlankQuestion fillBlankQuestion;
    private ExamAnswerinfo examAnswerinfo;
    private String avgScore;

    public BlankAnswerVo(){
    }

    public BlankAnswerVo(FillBlankQuestion fillBlankQuestion){
        this.fillBlankQuestion = fillBlankQuestion;
    }

    public BlankAnswerVo(FillBlankQuestion fillBlankQuestion, ExamAnswerinfo examAnswerinfo){
        this.fillBlankQuestion = fillBlankQuestion;
        this.examAnswerinfo = examAnswerinfo;
    }

    public FillBlankQuestion getFillBlankQuestion() {
        return fillBlankQuestion;
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
