package com.xloya.pldp.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultObj {
    public static final ResultObj  STUDENT_LOGIN_SUCCESS=new ResultObj(0, "登陆成功");
    public static final ResultObj  TEACHER_LOGIN_SUCCESS=new ResultObj(1, "登陆成功");
    public static final ResultObj  ADMIN_LOGIN_SUCCESS=new ResultObj(2, "登陆成功");
    public static final ResultObj  LOGIN_ERROR_PASS=new ResultObj(-1, "登陆失败,用户名或密码不正确");
    public static final ResultObj  LOGIN_ERROR_CODE=new ResultObj(-1, "登陆失败,验证码不正确");
    public static final ResultObj  LOGIN_FAILURE=new ResultObj(-1, "登陆失败,不存在该用户");

    public static final ResultObj  UPDATE_SUCCESS=new ResultObj(200, "更新成功");
    public static final ResultObj  UPDATE_ERROR=new ResultObj(-1, "更新失败");

    public static final ResultObj  ADD_SUCCESS=new ResultObj(200, "添加成功");
    public static final ResultObj  ADD_ERROR=new ResultObj(-1, "添加失败");

    public static final ResultObj  DELETE_SUCCESS=new ResultObj(200, "删除成功");
    public static final ResultObj  DELETE_ERROR=new ResultObj(-1, "删除失败");



    private Integer code;
    private String msg;
}
