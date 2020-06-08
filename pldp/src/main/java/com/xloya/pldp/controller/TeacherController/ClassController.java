package com.xloya.pldp.controller.TeacherController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xloya.pldp.common.DataGridView;
import com.xloya.pldp.common.ResultObj;
import com.xloya.pldp.entity.*;
import com.xloya.pldp.service.ClassesService;
import com.xloya.pldp.service.StudentsService;
import com.xloya.pldp.vo.StudentsVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/teachers")
public class ClassController {

    @Autowired
    StudentsService studentsService;

    @Autowired
    ClassesService classesService;


    /**
     * 跳转到教师班级管理页面
     */
    @RequestMapping("/toClassManager")
    public String toClassManager() {
        return "system/teachers/class/class_manager";
    }


    /**
     * 班级学生信息查询
     */
    @RequestMapping("/showMyStudentList")
    @ResponseBody
    public DataGridView showMyStudentList(HttpServletRequest httpServletRequest,StudentsVo studentsVo) {
        QueryWrapper<Classes> queryWrapper1 = new QueryWrapper<>();
        Teachers teachers = (Teachers) httpServletRequest.getSession().getAttribute("user");
        queryWrapper1.eq("teacher_id",teachers.getTeacherId());
        List<Classes> classesList = classesService.list(queryWrapper1);

        QueryWrapper<Students> queryWrapper = new QueryWrapper<>();
        if(StringUtils.isNotBlank(studentsVo.getName())||StringUtils.isNotBlank(studentsVo.getIdCard())||studentsVo.getClassId() != null
        ||studentsVo.getStudentId() != null) {
            queryWrapper.like(StringUtils.isNotBlank(studentsVo.getName()), "name", studentsVo.getName());
            queryWrapper.eq(studentsVo.getClassId() != null, "class_id", studentsVo.getClassId());
            queryWrapper.eq(studentsVo.getStudentId() != null, "student_id", studentsVo.getStudentId());
            queryWrapper.eq(StringUtils.isNotBlank(studentsVo.getIdCard()), "id_card", studentsVo.getIdCard());
        }else {
            for (Classes classes : classesList) {
                queryWrapper.or().eq("class_id", classes.getClassId());
            }
        }
        IPage<Students> page = new Page<>(studentsVo.getPage(), studentsVo.getLimit());
        this.studentsService.page(page, queryWrapper);
        httpServletRequest.getSession().setAttribute("user",teachers);
        return new DataGridView(page.getTotal(), page.getRecords());
    }

    /**
     * 添加班级学生
     */
    @RequestMapping("/addMyStudent")
    @ResponseBody
    public ResultObj addMyStudent(HttpServletRequest httpServletRequest,StudentsVo studentsVo){
        try {
            Teachers teachers = (Teachers) httpServletRequest.getSession().getAttribute("user");
            QueryWrapper<Students> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("student_id",studentsVo.getStudentId());
            QueryWrapper<Classes> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("class_id",studentsVo.getClassId());
            queryWrapper1.eq("teacher_id",teachers.getTeacherId());
            if(null==studentsService.getOne(queryWrapper)||classesService.getOne(queryWrapper1)==null)
                return ResultObj.ADD_ERROR;
            UpdateWrapper<Students> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("student_id",studentsVo.getStudentId());
            Students students = new Students();
            students.setClassId(studentsVo.getClassId());
            studentsService.update(students,updateWrapper);
            httpServletRequest.getSession().setAttribute("user",teachers);
            return ResultObj.ADD_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.ADD_ERROR;
        }
    }


    /**
     * 修改班级学生
     */
    @RequestMapping("/updateMyStudent")
    @ResponseBody
    public ResultObj updateMyStudent(HttpServletRequest httpServletRequest,StudentsVo studentsVo) {
        try {
            Teachers teachers = (Teachers) httpServletRequest.getSession().getAttribute("user");
            QueryWrapper<Classes> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("class_id",studentsVo.getClassId());
            queryWrapper1.eq("teacher_id",teachers.getTeacherId());
            if(classesService.getOne(queryWrapper1)==null)
                return ResultObj.UPDATE_ERROR;
            this.studentsService.updateById(studentsVo);
            httpServletRequest.getSession().setAttribute("user",teachers);
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.UPDATE_ERROR;
        }
    }

    /**
     * 删除班级学生
     */
    @RequestMapping("/deleteMyStudent")
    @ResponseBody
    public ResultObj deleteMyStudent(HttpServletRequest httpServletRequest,Integer id){
        try {
            Students students = studentsService.getById(id);
            students.setClassId(0);
            studentsService.updateById(students);
            httpServletRequest.getSession().setAttribute("user",httpServletRequest.getSession().getAttribute("user"));
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }

    /**
     * 批量删除班级学生
     */
    @RequestMapping("/batchDeleteMyStudent")
    @ResponseBody
    public ResultObj batchDeleteMyStudent(HttpServletRequest httpServletRequest,StudentsVo studentsVo) {
        try {
            for (Integer id : studentsVo.getIds()) {
                Students students = studentsService.getById(id);
                students.setClassId(0);
                studentsService.updateById(students);
            }
            httpServletRequest.getSession().setAttribute("user",httpServletRequest.getSession().getAttribute("user"));
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }






}
