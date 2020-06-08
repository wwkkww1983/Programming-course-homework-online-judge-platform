package com.xloya.pldp.controller.StudentController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xloya.pldp.common.DataGridView;
import com.xloya.pldp.common.ResultObj;
import com.xloya.pldp.common.TreeNode;
import com.xloya.pldp.entity.*;
import com.xloya.pldp.judger.ExerciseJudgeCore;
import com.xloya.pldp.service.*;
import com.xloya.pldp.vo.CoursesVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/students")
public class CourseController {

    @Autowired
    CoursesService coursesService;

    @Autowired
    CourseContentService courseContentService;

    @Autowired
    StudentsService studentsService;

    @Autowired
    ClassesService classesService;

    @Autowired
    ExerciseQuestionService exerciseQuestionService;

    @Autowired
    ExerciseJudgeCore exerciseJudgeCore;
    /**
     * 跳转到课程学习页面
     */
    @RequestMapping("/toLearnCourse")
    public String toLearnCourse()
    {
        return "system/students/course/course_index";
    }

    /**
     * 课程查询
     */
    @RequestMapping("/showCourseList")
    @ResponseBody
    public DataGridView showCourseList(HttpServletRequest httpServletRequest,CoursesVo coursesVo) {
        Students students = (Students) httpServletRequest.getSession().getAttribute("user");
        if(students==null)
            return new DataGridView(Long.valueOf(0),null);
        QueryWrapper<Classes> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("class_id",students.getClassId());
        Classes classes = classesService.getOne(queryWrapper);
        IPage<Courses> page = new Page<>(coursesVo.getPage(), coursesVo.getLimit());
        QueryWrapper<Courses> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("teacher_id",classes.getTeacherId());
        queryWrapper1.like(StringUtils.isNotBlank(coursesVo.getCourseName()),"course_name",coursesVo.getCourseName());
        queryWrapper1.eq(coursesVo.getCourseId()!=null,"course_id",coursesVo.getCourseId());
        this.coursesService.page(page, queryWrapper1);
        httpServletRequest.getSession().setAttribute("user", students);
        return new DataGridView(page.getTotal(), page.getRecords());
    }


    /**
     * 详情跳转
     */
    @RequestMapping("/showOneCourse")
    public String showOneCourse(HttpSession httpSession, @RequestParam("id")Integer id) {
        httpSession.setAttribute("course_id",id);
        return "system/students/course/course_info";
    }

    /**
     * 课程树跳转
     */
    @RequestMapping("/showCourseTree")
    public String showCourseTree(HttpSession httpSession) {
        httpSession.setAttribute("course_id",httpSession.getAttribute("course_id"));
        return "system/students/course/course_tree";
    }

    /**
     * 内容跳转
     */
    @RequestMapping("/showCourseContent")
    public String showCourseContent(HttpSession httpSession) {
        httpSession.setAttribute("course_id",httpSession.getAttribute("course_id"));
        return "system/students/course/course_content";
    }


    /**
     * 课程树加载
     */
    @RequestMapping("/loadCourseTree")
    @ResponseBody
    public DataGridView loadCourseTree(@RequestParam("course_id")Integer course_id) {
        QueryWrapper<CourseContent> queryWrapper = new QueryWrapper<>();
        System.out.println(course_id);
        queryWrapper.eq("course_id",course_id);
        List<CourseContent> list = courseContentService.list(queryWrapper);

        List<TreeNode> nodeList = new ArrayList<>();
        for(CourseContent courseContent:list){
            if(courseContent.getSection().equals("0")) {
                nodeList.add(new TreeNode(-Integer.valueOf(courseContent.getChapter()),0,courseContent.getTitle(),true));
            }else{
                nodeList.add(new TreeNode(courseContent.getId(), -Integer.valueOf(courseContent.getChapter()),courseContent.getTitle(),false));
            }
        }
        return new DataGridView(nodeList);
    }


    /**
     * 内容查看
     */

    @RequestMapping("/showContent")
    public String showContent(HttpServletRequest httpServletRequest,@RequestParam("id")Integer id) {
        CourseContent courseContent = courseContentService.getById(id);
        if(courseContent!=null) {
            ExerciseQuestion exerciseQuestion = exerciseQuestionService.getById(courseContent.getExerciseId());
            QueryWrapper<Courses> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("course_id",courseContent.getCourseId());
            Courses courses = coursesService.getOne(queryWrapper);
            HttpSession httpSession = httpServletRequest.getSession();
            httpSession.setAttribute("courseContent", courseContent);
            httpSession.setAttribute("exercise",exerciseQuestion);
            httpSession.setAttribute("courseType",courses.getCourseType());
            httpSession.setAttribute("user",httpServletRequest.getSession().getAttribute("user"));
        }

        return "system/students/course/course_content";
    }

    /**
     * 练习代码提交调试
     */
    @RequestMapping("/doExerciseTest")
    @ResponseBody
    public ResultObj doExerciseTest(@RequestParam("content")String content,
                                @RequestParam("courseType")Integer courseType,
                                @RequestParam("exerciseId")Integer exerciseId,
                                @RequestParam("studentId")Integer studentId,
                                @RequestParam(value = "paperId",defaultValue = "")Integer paperId) {
        ResultObj resultObj;
        if(!StringUtils.isNotBlank(content))
            return new ResultObj(-1,"代码不能为空！");

        resultObj = exerciseJudgeCore.executeCode(content,courseType,exerciseId,studentId,0,paperId);

        return resultObj;
    }
}
