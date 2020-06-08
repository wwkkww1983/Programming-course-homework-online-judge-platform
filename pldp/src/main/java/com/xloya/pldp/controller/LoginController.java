package com.xloya.pldp.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xloya.pldp.common.ResultObj;
import com.xloya.pldp.entity.Admins;
import com.xloya.pldp.entity.Students;
import com.xloya.pldp.entity.Teachers;
import com.xloya.pldp.service.AdminsService;
import com.xloya.pldp.service.StudentsService;
import com.xloya.pldp.service.TeachersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.RenderedImage;
import java.io.IOException;

@Controller
public class LoginController {

    @Autowired
    private AdminsService adminsService;

    @Autowired
    private StudentsService studentsService;

    @Autowired
    private TeachersService teachersService;

    @RequestMapping("/login")
    @ResponseBody
    public ResultObj login(HttpServletRequest httpRequest, String loginname, String pwd,String validateCode) {
        String code = httpRequest.getSession().getAttribute("validateCode").toString();
        if(!validateCode.equals(code))
            return new ResultObj(-1,"验证码不正确！");
        Integer role = Integer.parseInt(httpRequest.getParameter("role"));
        //学生
        if(role==0){
            try {
                QueryWrapper queryWrapper = new QueryWrapper();
                queryWrapper.eq("student_id",loginname);
                queryWrapper.eq("password",pwd);
                Students students = studentsService.getOne(queryWrapper);
                if(null!=students) {
                    httpRequest.getSession().setAttribute("user",students);
                    httpRequest.getSession().setAttribute("usertype",0);
                    return ResultObj.STUDENT_LOGIN_SUCCESS;
                }
                else
                    return new ResultObj(-1,"用户不存在或账号密码有误！");
            } catch (Exception e) {
                e.printStackTrace();
                return ResultObj.LOGIN_ERROR_PASS;
            }
        }
        //老师
        else if(role==1){
            try {
                QueryWrapper queryWrapper = new QueryWrapper();
                queryWrapper.eq("teacher_id",loginname);
                queryWrapper.eq("password",pwd);
                Teachers teachers = teachersService.getOne(queryWrapper);
                if(null!=teachers) {
                    httpRequest.getSession().setAttribute("user",teachers);
                    httpRequest.getSession().setAttribute("usertype",1);
                    return ResultObj.TEACHER_LOGIN_SUCCESS;
                }
                else
                    return new ResultObj(-1,"用户不存在或账号密码有误！");
            } catch (Exception e) {
                e.printStackTrace();
                return ResultObj.LOGIN_ERROR_PASS;
            }
            //管理员
        }else{
            try {
                QueryWrapper queryWrapper = new QueryWrapper();
                queryWrapper.eq("admin_id",loginname);
                queryWrapper.eq("password",pwd);
                Admins admins = adminsService.getOne(queryWrapper);
                if(null!=admins) {
                    httpRequest.getSession().setAttribute("user",admins);
                    httpRequest.getSession().setAttribute("usertype",2);
                    return ResultObj.ADMIN_LOGIN_SUCCESS;
                }
                else
                    return new ResultObj(-1,"用户不存在或账号密码有误！");
            } catch (Exception e) {
                e.printStackTrace();
                return ResultObj.LOGIN_ERROR_PASS;
            }
        }
    }


    /**
     *
     * 得到验证码
     */
    @RequestMapping("/getValidateCode")
    public void getValidateCode(HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) throws IOException {
        //定义图形验证码的长和宽
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(116, 36,4,5);

        httpServletRequest.getSession().setAttribute("validateCode",lineCaptcha.getCode());
        ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
        ImageIO.write( lineCaptcha.getImage(),"JPEG",servletOutputStream);
    }
}
