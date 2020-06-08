package com.xloya.pldp.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
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
@TableName("code_discuss")
public class CodeDiscuss implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("code_id")
    private Integer codeId;

    @TableField("student_id")
    private Integer studentId;

    @TableField("content")
    private String content;

    @TableField("create_time")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private String createTime;


}
