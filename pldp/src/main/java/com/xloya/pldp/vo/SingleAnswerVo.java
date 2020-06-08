package com.xloya.pldp.vo;

import com.xloya.pldp.entity.ExamAnswerinfo;
import com.xloya.pldp.entity.SingleSelectQuestion;


public class SingleAnswerVo {
    private SingleSelectQuestion singleSelectQuestion;
    private ExamAnswerinfo examAnswerinfo;
    private String avgScore;
    private String optionAPercent;
    private String optionBPercent;
    private String optionCPercent;
    private String optionDPercent;
    private String optionNullPercent;

    public SingleAnswerVo(){
    }

    public SingleAnswerVo(SingleSelectQuestion singleSelectQuestion){
        this.singleSelectQuestion = singleSelectQuestion;
    }

    public SingleAnswerVo(SingleSelectQuestion singleSelectQuestion,ExamAnswerinfo examAnswerinfo){
        this.singleSelectQuestion = singleSelectQuestion;
        this.examAnswerinfo = examAnswerinfo;
    }

    public SingleSelectQuestion getSingleSelectQuestion() {
        return singleSelectQuestion;
    }

    public ExamAnswerinfo getExamAnswerinfo() {
        return examAnswerinfo;
    }

    public String getOptionAPercent() {
        return optionAPercent;
    }

    public void setOptionAPercent(String optionAPercent) {
        this.optionAPercent = optionAPercent;
    }

    public String getOptionBPercent() {
        return optionBPercent;
    }

    public void setOptionBPercent(String optionBPercent) {
        this.optionBPercent = optionBPercent;
    }

    public String getOptionCPercent() {
        return optionCPercent;
    }

    public void setOptionCPercent(String optionCPercent) {
        this.optionCPercent = optionCPercent;
    }

    public String getOptionDPercent() {
        return optionDPercent;
    }

    public void setOptionDPercent(String optionDPercent) {
        this.optionDPercent = optionDPercent;
    }

    public String getOptionNullPercent() {
        return optionNullPercent;
    }

    public void setOptionNullPercent(String optionNullPercent) {
        this.optionNullPercent = optionNullPercent;
    }

    public String getAvgScore() {
        return avgScore;
    }

    public void setAvgScore(String avgScore) {
        this.avgScore = avgScore;
    }
}
