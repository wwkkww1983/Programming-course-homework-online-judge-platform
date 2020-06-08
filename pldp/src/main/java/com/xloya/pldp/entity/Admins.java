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
@TableName("user_admins")
public class Admins implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("id")
    private Integer id;

    @TableField("admin_id")
    private Integer adminId;

    @TableField("password")
    private String password;

    @TableField("level")
    private Integer level;

    @TableField("name")
    private String name;

    @TableField("email")
    private String email;

    @TableField("phone_num")
    private String phoneNum;

    @TableField("id_card")
    private String idCard;

    @TableField("status")
    private Integer status;


}
