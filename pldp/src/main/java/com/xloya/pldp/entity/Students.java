package com.xloya.pldp.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author xloya
 * @since 2020-01-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("user_students")
public class Students implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("id")
    private Integer id;

    @TableField("student_id")
    private Integer studentId;

    @TableField("password")
    private String password;

    @TableField("name")
    private String name;

    @TableField("class_id")
    private Integer classId;

    @TableField("email")
    private String email;

    @TableField("phone_num")
    private String phoneNum;

    @TableField("id_card")
    private String idCard;

    @TableField("points")
    private Integer points;

    @TableField("status")
    private Integer status;


}
