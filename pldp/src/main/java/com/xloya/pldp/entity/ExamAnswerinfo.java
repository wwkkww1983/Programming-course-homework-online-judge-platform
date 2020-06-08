package com.xloya.pldp.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author xloya
 * @since 2020-02-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("exam_answerinfo")
public class ExamAnswerinfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id")
    private Integer id;

    @TableField("student_id")
    private Integer studentId;

    @TableField("paper_id")
    private Integer paperId;

    @TableField("paper_type")
    private Integer paperType;

    @TableField("type")
    private Integer type;

    @TableField("question_id")
    private Integer questionId;

    @TableField("answer")
    private String answer;

    @TableField("correct_answer")
    private String correctAnswer;

    @TableField("score")
    private Integer score;


}
