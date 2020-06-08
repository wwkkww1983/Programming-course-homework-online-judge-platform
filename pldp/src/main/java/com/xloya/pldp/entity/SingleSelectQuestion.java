package com.xloya.pldp.entity;

import com.baomidou.mybatisplus.annotation.IdType;
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
 * @since 2020-02-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("single_select_question")
public class SingleSelectQuestion implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("course_id")
    private Integer courseId;

    @TableField("chapter")
    private String chapter;

    @TableField("section")
    private String section;

    @TableField("question_text")
    private String questionText;

    @TableField("degree")
    private Integer degree;

    @TableField("optionA")
    private String optionA;

    @TableField("optionB")
    private String optionB;

    @TableField("optionC")
    private String optionC;

    @TableField("optionD")
    private String optionD;

    @TableField("answer")
    private String answer;


}
