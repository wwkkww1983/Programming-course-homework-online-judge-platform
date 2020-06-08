package com.xloya.pldp.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.xloya.pldp.common.ResultObj;
import com.xloya.pldp.entity.Admins;
import com.xloya.pldp.entity.Classes;
import com.xloya.pldp.entity.Students;
import com.xloya.pldp.entity.Teachers;
import com.xloya.pldp.service.AdminsService;
import com.xloya.pldp.service.ClassesService;
import com.xloya.pldp.service.StudentsService;
import com.xloya.pldp.service.TeachersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class HomeController {
    @Autowired
    ClassesService classesService;

    @Autowired
    StudentsService studentsService;

    @Autowired
    TeachersService teachersService;

    @Autowired
    AdminsService adminsService;
    /**
     * 跳转到登陆页面
     */
    @RequestMapping("/toLogin")
    public String toLogin() {
        return "system/index/login";
    }

    /**
     * 跳转到工作台页面
     */
    @RequestMapping("/toDeskManager")
    public String toDeskManager() {
        return "system/index/main";
    }


    /**
     * 跳转到用户信息页面
     */
    @RequestMapping("/toUserInfo")
    public String toUserInfo(HttpServletRequest httpServletRequest, Model model) {
        Integer usertype = (Integer) httpServletRequest.getSession().getAttribute("usertype");
        if(usertype==0){
            Students students = (Students) httpServletRequest.getSession().getAttribute("user");
            QueryWrapper<Classes> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("class_id",students.getClassId());
            Classes classes = classesService.getOne(queryWrapper);
            model.addAttribute("user",students);
            model.addAttribute("class",classes);
            model.addAttribute("usertype",0);
        }else if(usertype==1){
            Teachers teachers = (Teachers) httpServletRequest.getSession().getAttribute("user");
            model.addAttribute("user",teachers);
            model.addAttribute("usertype",1);
        }else if(usertype==2){
            Admins admins = (Admins) httpServletRequest.getSession().getAttribute("user");
            model.addAttribute("user",admins);
            model.addAttribute("usertype",2);
        }

        return "system/index/userInfo";
    }

    /**
     * 修改用户信息
     */
    @RequestMapping("/fixInfo")
    @ResponseBody
    public ResultObj fixInfo(HttpServletRequest httpServletRequest,
                                @RequestParam("phone")String phone,
                                @RequestParam("email")String email,
                                @RequestParam("usertype")Integer usertype,
                                @RequestParam(value = "studentId",defaultValue = "")Integer studentId,
                                @RequestParam(value = "teacherId",defaultValue = "")Integer teacherId,
                                @RequestParam(value = "adminId",defaultValue = "")Integer adminId) {
        try {
            if (usertype == 0) {
                Students students = new Students();
                students.setPhoneNum(phone);
                students.setEmail(email);
                UpdateWrapper<Students> updateWrapper = new UpdateWrapper<>();
                updateWrapper.eq("student_id",studentId);
                studentsService.update(students,updateWrapper);
                QueryWrapper<Students> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("student_id",studentId);
                httpServletRequest.getSession().setAttribute("user",studentsService.getOne(queryWrapper));
            } else if (usertype == 1) {
                Teachers teachers = new Teachers();
                teachers.setPhoneNum(phone);
                teachers.setEmail(email);
                UpdateWrapper<Teachers> updateWrapper = new UpdateWrapper<>();
                updateWrapper.eq("teacher_id",teacherId);
                teachersService.update(teachers,updateWrapper);
                QueryWrapper<Teachers> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("teacher_id",teacherId);
                httpServletRequest.getSession().setAttribute("user",teachersService.getOne(queryWrapper));
            } else if (usertype == 2) {
                Admins admins = new Admins();
                admins.setPhoneNum(phone);
                admins.setEmail(email);
                UpdateWrapper<Admins> updateWrapper = new UpdateWrapper<>();
                updateWrapper.eq("admin_id",adminId);
                adminsService.update(admins,updateWrapper);
                QueryWrapper<Admins> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("admin_id",adminId);
                httpServletRequest.getSession().setAttribute("user",adminsService.getOne(queryWrapper));
            }
        }catch (Exception e){
            e.printStackTrace();
            return new ResultObj(-1,"修改失败");
        }

        return new ResultObj(200,"修改成功");
    }


    /**
     * 跳转到修改密码页面
     */
    @RequestMapping("/toChangePwd")
    public String toChangePwd(HttpServletRequest httpServletRequest,Model model) {
        Integer usertype = (Integer) httpServletRequest.getSession().getAttribute("usertype");
        if(usertype==0){
            model.addAttribute("user",httpServletRequest.getSession().getAttribute("user"));
            model.addAttribute("usertype",0);
        }else if(usertype==1){
            model.addAttribute("user",httpServletRequest.getSession().getAttribute("user"));
            model.addAttribute("usertype",1);
        }else if(usertype==2){
            model.addAttribute("user",httpServletRequest.getSession().getAttribute("user"));
            model.addAttribute("usertype",2);
        }
        return "system/index/changePwd";
    }

    /**
     * 修改密码
     */
    @RequestMapping("/fixPwd")
    @ResponseBody
    public ResultObj fixPwd(@RequestParam("username")String name,
                             @RequestParam("usertype")Integer usertype,
                             @RequestParam("oldP")String oldPwd,
                             @RequestParam("newP")String newPwd,
                             @RequestParam(value = "studentId",defaultValue = "")Integer studentId,
                             @RequestParam(value = "teacherId",defaultValue = "")Integer teacherId,
                             @RequestParam(value = "adminId",defaultValue = "")Integer adminId) {
        if(oldPwd.equals(newPwd))
            return new ResultObj(-1,"新密码不能与旧密码相同！请重新输入");
        try {
            if (usertype == 0) {
               QueryWrapper<Students> queryWrapper = new QueryWrapper<>();
               queryWrapper.eq("name",name);
               queryWrapper.eq("password",oldPwd);
               queryWrapper.eq("student_id",studentId);
               Students students = studentsService.getOne(queryWrapper);
               if(students==null)
                   return new ResultObj(-1,"旧密码不正确！请重新输入");
               UpdateWrapper<Students> updateWrapper = new UpdateWrapper<>();
               updateWrapper.eq("student_id",studentId);
               students.setPassword(newPwd);
               studentsService.update(students,updateWrapper);
            } else if (usertype == 1) {
                QueryWrapper<Teachers> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("name",name);
                queryWrapper.eq("password",oldPwd);
                queryWrapper.eq("teacher_id",teacherId);
                Teachers teachers = teachersService.getOne(queryWrapper);
                if(teachers==null)
                    return new ResultObj(-1,"旧密码不正确！请重新输入");
                UpdateWrapper<Teachers> updateWrapper = new UpdateWrapper<>();
                updateWrapper.eq("teacher_id",teacherId);
                teachers.setPassword(newPwd);
                teachersService.update(teachers,updateWrapper);
            } else if (usertype == 2) {
                QueryWrapper<Admins> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("name",name);
                queryWrapper.eq("password",oldPwd);
                queryWrapper.eq("admin_id",adminId);
                Admins admins = adminsService.getOne(queryWrapper);
                if(admins==null)
                    return new ResultObj(-1,"旧密码不正确！请重新输入");
                UpdateWrapper<Admins> updateWrapper = new UpdateWrapper<>();
                updateWrapper.eq("admin_id",adminId);
                admins.setPassword(newPwd);
                adminsService.update(admins,updateWrapper);
            }
        }catch (Exception e){
            e.printStackTrace();
            return new ResultObj(-1,"修改失败");
        }

        return new ResultObj(200,"修改成功");
    }
    /**
     * 跳转到管理员首页
     */
    @RequestMapping("/admins/admin_index")
    public String adminIndex() {
        return "system/index/admin_index";
    }

    /**
     * 跳转到学生首页
     */
    @RequestMapping("/students/student_index")
    public String studentIndex() {
        return "system/index/student_index";
    }

    /**
     * 跳转到老师首页
     */
    @RequestMapping("/teachers/teacher_index")
    public String teacherIndex() {
        return "system/index/teacher_index";
    }


}
