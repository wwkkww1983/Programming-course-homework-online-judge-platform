package com.xloya.pldp.controller.StudentController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xloya.pldp.common.DataGridView;
import com.xloya.pldp.common.ResultObj;
import com.xloya.pldp.entity.*;
import com.xloya.pldp.judger.CodeJudgeCore;
import com.xloya.pldp.service.CodeDiscussService;
import com.xloya.pldp.service.CodeJudgementService;
import com.xloya.pldp.service.CodeQuestionService;
import com.xloya.pldp.service.StudentsService;
import com.xloya.pldp.vo.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/students")
public class CodeController {
    @Autowired
    CodeQuestionService codeQuestionService;

    @Autowired
    CodeDiscussService codeDiscussService;

    @Autowired
    StudentsService studentsService;

    @Autowired
    CodeJudgementService codeJudgementService;

    @Autowired
    CodeJudgeCore codeJudgeCore;

    /**
     * 跳转到编程题页面
     */
    @RequestMapping("/toDoCodeQuestion")
    public String toDoCodeQuestion()
    {
        return "system/students/code/code_list";
    }


    /**
     * 编程题查询
     */
    @RequestMapping("/showCodeQuestionList")
    @ResponseBody
    public DataGridView showCodeQuestionList(CodeQuestionVo codeQuestionVo) {
        IPage<CodeQuestion> page = new Page<>(codeQuestionVo.getPage(), codeQuestionVo.getLimit());
        QueryWrapper<CodeQuestion> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(codeQuestionVo.getTitle()),"title",codeQuestionVo.getTitle());
        queryWrapper.eq(codeQuestionVo.getId()!=null,"id",codeQuestionVo.getId());
        queryWrapper.eq(codeQuestionVo.getCourseId()!=null,"course_id",codeQuestionVo.getCourseId());
        queryWrapper.eq(codeQuestionVo.getDegree()!=null,"degree",codeQuestionVo.getDegree());
        this.codeQuestionService.page(page, queryWrapper);
        return new DataGridView(page.getTotal(), page.getRecords());
    }

    /**
     * 详情查看
     */
    @RequestMapping("/showOneQuestion")
    public String showOneQuestion(HttpServletRequest httpServletRequest, @RequestParam("id")Integer id, Model model) {
        CodeQuestion codeQuestion = codeQuestionService.getById(id);
        model.addAttribute("codeQuestion",codeQuestion);
        Students students = (Students) httpServletRequest.getSession().getAttribute("user");
        QueryWrapper<CodeDiscuss> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("code_id", id);
        List<CodeDiscuss> codeDiscussList = codeDiscussService.list(queryWrapper);
        List<CodeDiscussVo> codeDiscussVoList = new ArrayList<>();
        for(CodeDiscuss codeDiscuss:codeDiscussList){
            QueryWrapper<Students> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("student_id",codeDiscuss.getStudentId());
            Students students1 = studentsService.getOne(queryWrapper1);

            CodeDiscussVo codeDiscussVo = new CodeDiscussVo();
            codeDiscussVo.setId(codeDiscuss.getId());
            codeDiscussVo.setCodeId(codeDiscuss.getCodeId());
            codeDiscussVo.setStudentName(students1.getName());
            codeDiscussVo.setStudentId(codeDiscuss.getStudentId());
            codeDiscussVo.setCreateTime(codeDiscuss.getCreateTime());
            codeDiscussVo.setContent(codeDiscuss.getContent());
            codeDiscussVoList.add(codeDiscussVo);
        }
        model.addAttribute("codediscuss", codeDiscussVoList);
        model.addAttribute("student",students);
        httpServletRequest.getSession().setAttribute("user", students);
        return "system/students/code/code_info";
    }

    /**
     * 代码提交调试
     */
    @RequestMapping("/doCodeTest")
    @ResponseBody
    public ResultObj doCodeTest(@RequestParam("content")String content,
                                @RequestParam("language")Integer language,
                                @RequestParam("codeQuestionId")Integer codeQuestionId,
                                @RequestParam("studentId")Integer studentId) {

        if(!StringUtils.isNotBlank(content))
            return new ResultObj(-1,"代码不能为空！");

        ResultObj resultObj = codeJudgeCore.executeCode(content,language,codeQuestionId,studentId);

        return resultObj;
    }



    /**
     * 代码讨论提交
     */

    @RequestMapping("/addCodeDiscuss")
    @ResponseBody
    public ResultObj addCodeDiscuss(HttpServletRequest httpServletRequest,
                                    @RequestParam("discuss")String discuss,
                                       @RequestParam("codeid")Integer codeid,
                                    @RequestParam("studentId")Integer studentId) {
        try {
            if(!StringUtils.isNotBlank(discuss))
                return new ResultObj(-1,"评论不能为空!");
            CodeDiscuss codeDiscuss = new CodeDiscuss();
            codeDiscuss.setCodeId(codeid);
            codeDiscuss.setStudentId(studentId);
            codeDiscuss.setContent(discuss);
            Date date = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            codeDiscuss.setCreateTime(simpleDateFormat.format(date.getTime()));
            codeDiscussService.save(codeDiscuss);
            return ResultObj.ADD_SUCCESS;
        }catch(Exception e){
            e.printStackTrace();
            return ResultObj.ADD_ERROR;
        }


    }

    /**
     * 跳转到排行榜页面
     */
    @RequestMapping("/toCodeQuestionRank")
    public String toCodeQuestionRank() {
        return "system/students/code/code_rank";
    }


    /**
     * 积分排行信息查询
     */
    @RequestMapping("/showPointList")
    @ResponseBody
    public DataGridView showPointList(StudentsVo studentsVo) {
        IPage<Students> page = new Page<>(studentsVo.getPage(), studentsVo.getLimit());
        QueryWrapper<Students> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(studentsVo.getName()),"name",studentsVo.getName());
        queryWrapper.eq(studentsVo.getStudentId()!=null,"student_id",studentsVo.getStudentId());
        queryWrapper.orderByDesc("points");
        this.studentsService.page(page, queryWrapper);
        return new DataGridView(page.getTotal(), page.getRecords());
    }
}
