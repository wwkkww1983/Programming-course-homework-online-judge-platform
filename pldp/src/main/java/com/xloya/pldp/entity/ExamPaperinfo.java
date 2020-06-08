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
import java.util.Date;

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
@TableName("exam_paperinfo")
public class ExamPaperinfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("course_id")
    private Integer courseId;

    @TableField("chapter")
    private String chapter;

    @TableField("class_id")
    private Integer classId;

    @TableField("type")
    private Integer type;

    @TableField("single_count")
    private Integer singleCount;

    @TableField("single_score")
    private Integer singleScore;

    @TableField("blank_count")
    private Integer blankCount;

    @TableField("blank_score")
    private Integer blankScore;

    @TableField("code_count")
    private Integer codeCount;

    @TableField("code_score")
    private Integer codeScore;

    @TableField("create_time")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private String createTime;

    @TableField("start_time")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private String startTime;

    @TableField("end_time")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private String endTime;

    @TableField("paper_name")
    private String paperName;

    @TableField("teacher_id")
    private Integer teacherId;


}
