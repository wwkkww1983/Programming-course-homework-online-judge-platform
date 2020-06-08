package com.xloya.pldp.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author xloya
 * @since 2020-02-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("exercise_submit")
public class ExerciseSubmit implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("paper_id")
    private Integer paperId;

    @TableField("input")
    private String input;

    @TableField("time")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private String time;

    @TableField("type")
    private Integer type;

    @TableField("exercise_id")
    private Integer exerciseId;

    @TableField("student_id")
    private Integer studentId;

    @TableField("state")
    private Double state;


}
