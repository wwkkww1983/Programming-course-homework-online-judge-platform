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
@TableName("exam_scoreinfo")
public class ExamScoreinfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("student_id")
    private Integer studentId;

    @TableField("paper_id")
    private Integer paperId;

    @TableField("paper_type")
    private Integer paperType;

    @TableField("start_time")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private String startTime;

    @TableField("state")
    private Integer state;

    @TableField("score")
    private Integer score;


}
