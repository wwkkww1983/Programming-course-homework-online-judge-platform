package com.xloya.pldp.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xloya.pldp.common.DataGridView;
import com.xloya.pldp.common.ResultObj;
import com.xloya.pldp.entity.*;
import com.xloya.pldp.service.DiscussCommentService;
import com.xloya.pldp.service.DiscussQuestionService;
import com.xloya.pldp.vo.DiscussCommentVo;
import com.xloya.pldp.vo.DiscussQuestionVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller

public class DiscussController {

    @Autowired
    DiscussQuestionService discussQuestionService;

    @Autowired
    DiscussCommentService discussCommentService;

    /**
     * 跳转到讨论社区页面
     */
    @RequestMapping("/toDiscuss")
    public String toClassManager() {
        return "system/discuss/discuss_main";
    }


    /**
     * 查看帖子
     */
    @RequestMapping("/showDiscussQuestionList")
    @ResponseBody
    public DataGridView showDiscussQuestionList(HttpServletRequest httpServletRequest, DiscussQuestionVo discussQuestionVo) {
        IPage<DiscussQuestion> page = new Page<>(1, 10);
        QueryWrapper<DiscussQuestion> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(discussQuestionVo.getTitle()), "title", discussQuestionVo.getTitle());
        queryWrapper.like(StringUtils.isNotBlank(discussQuestionVo.getUserName()), "user_name", discussQuestionVo.getUserName());

        this.discussQuestionService.page(page, queryWrapper);
        return new DataGridView(page.getTotal(), page.getRecords());
    }


    /**
     * 添加帖子
     */

    @RequestMapping("/addDiscussQuestion")
    @ResponseBody
    public ResultObj addDiscussQuestion(HttpServletRequest httpServletRequest, DiscussQuestionVo discussQuestionVo) {
        try {
            Integer usertype = (Integer) httpServletRequest.getSession().getAttribute("usertype");
            if (usertype == 0) {
                Students students = (Students) httpServletRequest.getSession().getAttribute("user");
                discussQuestionVo.setUserType(1);
                discussQuestionVo.setUserId(students.getStudentId());
                discussQuestionVo.setUserName(students.getName());
                Date date = new Date();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                discussQuestionVo.setCreateTime(simpleDateFormat.format(date.getTime()));
                discussQuestionService.save(discussQuestionVo);
                httpServletRequest.getSession().setAttribute("user", students);
            } else if (usertype == 1) {
                Teachers teachers = (Teachers) httpServletRequest.getSession().getAttribute("user");
                discussQuestionVo.setUserType(2);
                discussQuestionVo.setUserId(teachers.getTeacherId());
                discussQuestionVo.setUserName(teachers.getName());
                Date date = new Date();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                discussQuestionVo.setCreateTime(simpleDateFormat.format(date.getTime()));
                discussQuestionService.save(discussQuestionVo);
                httpServletRequest.getSession().setAttribute("user", teachers);
            }else{
                return ResultObj.ADD_ERROR;
            }
            return ResultObj.ADD_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.ADD_ERROR;
        }
    }


    /**
     * 跳转到帖子详情页面
     */
    @RequestMapping("/showDiscussQuestion")
    public String showDiscussQuestion(HttpServletRequest httpServletRequest,
                                      @RequestParam("id") Integer questionid, Model model) {
        Integer usertype = (Integer) httpServletRequest.getSession().getAttribute("usertype");
        if (usertype == 0) {
            Students students = (Students) httpServletRequest.getSession().getAttribute("user");
            DiscussQuestion discussQuestion = discussQuestionService.getById(questionid);
            model.addAttribute("question", discussQuestion);
            QueryWrapper<DiscussComment> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("entity_type", 1);
            queryWrapper.eq("entity_id", questionid);
            List<DiscussComment> discussCommentList = discussCommentService.list(queryWrapper);
            model.addAttribute("count",discussCommentList.size());
            model.addAttribute("comment", discussCommentList);
            httpServletRequest.getSession().setAttribute("user", students);
        } else if (usertype == 1) {
            Teachers teachers = (Teachers) httpServletRequest.getSession().getAttribute("user");
            DiscussQuestion discussQuestion = discussQuestionService.getById(questionid);
            model.addAttribute("question", discussQuestion);
            QueryWrapper<DiscussComment> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("entity_type", 1);
            queryWrapper.eq("entity_id", questionid);
            List<DiscussComment> discussCommentList = discussCommentService.list(queryWrapper);
            model.addAttribute("count",discussCommentList.size());
            model.addAttribute("comment", discussCommentList);
            httpServletRequest.getSession().setAttribute("user", teachers);
        }else{
            return "system/index/login";
        }
        return "system/discuss/discuss_content";
    }


    /**
     * 添加评论
     */

    @RequestMapping("/addDiscussComment")
    @ResponseBody
    public ResultObj addDiscussComment(HttpServletRequest httpServletRequest, DiscussCommentVo discussCommentVo,
            @RequestParam("questionid")Integer questionid) {
        try {
            Integer usertype = (Integer) httpServletRequest.getSession().getAttribute("usertype");
            if (usertype == 0) {
                Students students = (Students) httpServletRequest.getSession().getAttribute("user");
                discussCommentVo.setUserType(1);
                discussCommentVo.setUserId(students.getStudentId());
                discussCommentVo.setUserName(students.getName());
                discussCommentVo.setEntityType(1);
                discussCommentVo.setEntityId(questionid);
                Date date = new Date();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                discussCommentVo.setCreateTime(simpleDateFormat.format(date.getTime()));
                discussCommentService.save(discussCommentVo);
                UpdateWrapper<DiscussQuestion> updateWrapper = new UpdateWrapper<>();
                updateWrapper.eq("id",questionid);
                DiscussQuestionVo discussQuestionVo = new DiscussQuestionVo();
                discussQuestionVo.setCommentCount(discussQuestionService.getById(questionid).getCommentCount()+1);
                discussQuestionService.update(discussQuestionVo,updateWrapper);
                httpServletRequest.getSession().setAttribute("user", students);
            } else if (usertype == 1) {
                Teachers teachers = (Teachers) httpServletRequest.getSession().getAttribute("user");
                discussCommentVo.setUserType(2);
                discussCommentVo.setUserId(teachers.getTeacherId());
                discussCommentVo.setUserName(teachers.getName());
                discussCommentVo.setEntityType(1);
                discussCommentVo.setEntityId(questionid);
                Date date = new Date();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                discussCommentVo.setCreateTime(simpleDateFormat.format(date.getTime()));
                discussCommentService.save(discussCommentVo);
                UpdateWrapper<DiscussQuestion> updateWrapper = new UpdateWrapper<>();
                updateWrapper.eq("id",questionid);
                DiscussQuestionVo discussQuestionVo = new DiscussQuestionVo();
                discussQuestionVo.setCommentCount(discussQuestionService.getById(questionid).getCommentCount()+1);
                discussQuestionService.update(discussQuestionVo,updateWrapper);
                httpServletRequest.getSession().setAttribute("user", teachers);
            }else{
                return ResultObj.ADD_ERROR;
            }
            return ResultObj.ADD_SUCCESS;
        }catch(Exception e){
                e.printStackTrace();
                return ResultObj.ADD_ERROR;
            }


    }
}
