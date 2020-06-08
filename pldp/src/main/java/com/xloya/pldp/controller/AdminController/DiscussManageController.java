package com.xloya.pldp.controller.AdminController;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xloya.pldp.common.DataGridView;
import com.xloya.pldp.common.ResultObj;
import com.xloya.pldp.entity.*;
import com.xloya.pldp.service.DiscussCommentService;
import com.xloya.pldp.service.DiscussQuestionService;
import com.xloya.pldp.service.StudentsService;
import com.xloya.pldp.service.TeachersService;
import com.xloya.pldp.vo.DiscussCommentVo;
import com.xloya.pldp.vo.DiscussQuestionVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xloya
 * @since 2020-01-30
 */
@Controller
@RequestMapping("/admins")
public class DiscussManageController {

    @Autowired
    DiscussQuestionService discussQuestionService;

    @Autowired
    DiscussCommentService discussCommentService;

    @Autowired
    StudentsService studentsService;

    @Autowired
    TeachersService teachersService;


    /**
     * 跳转到讨论区问题管理页面
     */
    @RequestMapping("/toQuestionInfoManager")
    public String toQuestionInfoManager() {
        return "system/admins/discussmanage/question_info_manager";
    }


    /**
     * 讨论区问题信息查询
     */
    @RequestMapping("/showQuestionList")
    @ResponseBody
    public DataGridView showQuestionList(DiscussQuestionVo discussQuestionVo) {
        IPage<DiscussQuestion> page = new Page<>(discussQuestionVo.getPage(), discussQuestionVo.getLimit());
        QueryWrapper<DiscussQuestion> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(discussQuestionVo.getTitle()),"title",discussQuestionVo.getTitle());
        queryWrapper.eq(discussQuestionVo.getUserId()!=null,"user_id",discussQuestionVo.getUserId());
        queryWrapper.ge(StringUtils.isNotBlank(discussQuestionVo.getStartTime()), "create_time", discussQuestionVo.getStartTime());
        queryWrapper.le(StringUtils.isNotBlank(discussQuestionVo.getEndTime()), "create_time", discussQuestionVo.getEndTime());
        this.discussQuestionService.page(page, queryWrapper);
        return new DataGridView(page.getTotal(), page.getRecords());
    }


    /**
     * 修改讨论区问题
     */
    @RequestMapping("/updateQuestion")
    @ResponseBody
    public ResultObj updateQuestion(DiscussQuestionVo discussQuestionVo) {
        try {
            int type = Integer.valueOf(discussQuestionVo.getUserType());
            switch (type) {
                case 1:
                    QueryWrapper<Students> queryWrapper = new QueryWrapper<>();
                    queryWrapper.eq("student_id", discussQuestionVo.getUserId());
                    if(studentsService.getOne(queryWrapper)==null)
                        return ResultObj.UPDATE_ERROR;
                    break;
                case 2:
                    QueryWrapper<Teachers> queryWrapper1 = new QueryWrapper<>();
                    queryWrapper1.eq("teacher_id", discussQuestionVo.getUserId());
                    if(teachersService.getOne(queryWrapper1)==null)
                        return ResultObj.UPDATE_ERROR;
                    break;
            }
            this.discussQuestionService.updateById(discussQuestionVo);
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.UPDATE_ERROR;
        }
    }

    /**
     * 删除讨论区问题
     */
    @RequestMapping("/deleteQuestion")
    @ResponseBody
    public ResultObj deleteQuestion(Integer id){
        try {
            QueryWrapper<DiscussComment> queryWrapper = new QueryWrapper<>();
            // type 1是问题，2是评论
            queryWrapper.eq("entity_type",1);
            queryWrapper.eq("entity_id",id);
            discussCommentService.remove(queryWrapper);
            this.discussQuestionService.removeById(id);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }

    /**
     * 批量删除讨论区问题
     */
    @RequestMapping("/batchDeleteQuestion")
    @ResponseBody
    public ResultObj batchDeleteQuestion(DiscussQuestionVo discussQuestionVo) {
        try {
            Collection<Serializable> idList = new ArrayList<Serializable>();
            for (Integer id : discussQuestionVo.getIds()) {
                idList.add(id);
                QueryWrapper<DiscussComment> queryWrapper = new QueryWrapper<>();
                // type 1是问题，2是评论
                queryWrapper.eq("entity_type",1);
                queryWrapper.eq("entity_id",id);
                discussCommentService.remove(queryWrapper);
            }
            this.discussQuestionService.removeByIds(idList);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }




    /**
     * 跳转到讨论区评论管理页面
     */
    @RequestMapping("/toCommentInfoManager")
    public String toCommentInfoManager() {
        return "system/admins/discussmanage/comment_info_manager";
    }


    /**
     * 讨论区评论信息查询
     */
    @RequestMapping("/showCommentList")
    @ResponseBody
    public DataGridView showCommentList(DiscussCommentVo discussCommentVo) {
        IPage<DiscussComment> page = new Page<>(discussCommentVo.getPage(), discussCommentVo.getLimit());
        QueryWrapper<DiscussComment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(discussCommentVo.getUserId()!=null,"user_id",discussCommentVo.getUserId());
        queryWrapper.ge(StringUtils.isNotBlank(discussCommentVo.getStartTime()), "create_time", discussCommentVo.getStartTime());
        queryWrapper.le(StringUtils.isNotBlank(discussCommentVo.getEndTime()), "create_time", discussCommentVo.getEndTime());
        this.discussCommentService.page(page, queryWrapper);
        return new DataGridView(page.getTotal(), page.getRecords());
    }


    /**
     * 修改讨论区评论
     */
    @RequestMapping("/updateComment")
    @ResponseBody
    public ResultObj updateComment(DiscussCommentVo discussCommentVo) {
        try {
            int usertype = Integer.valueOf(discussCommentVo.getUserType());
            switch (usertype) {
                case 1:
                    QueryWrapper<Students> queryWrapper = new QueryWrapper<>();
                    queryWrapper.eq("student_id", discussCommentVo.getUserId());
                    if(studentsService.getOne(queryWrapper)==null)
                        return ResultObj.UPDATE_ERROR;
                    break;
                case 2:
                    QueryWrapper<Teachers> queryWrapper1 = new QueryWrapper<>();
                    queryWrapper1.eq("teacher_id", discussCommentVo.getUserId());
                    if(teachersService.getOne(queryWrapper1)==null)
                        return ResultObj.UPDATE_ERROR;
                    break;
            }
            int entitytype = Integer.valueOf(discussCommentVo.getEntityType());
            switch (entitytype) {
                case 1:
                    QueryWrapper<DiscussQuestion> queryWrapper = new QueryWrapper<>();
                    queryWrapper.eq("id", discussCommentVo.getEntityId());
                    if(discussQuestionService.getOne(queryWrapper)==null)
                        return ResultObj.UPDATE_ERROR;
                    break;
                case 2:
                    QueryWrapper<DiscussComment> queryWrapper1 = new QueryWrapper<>();
                    queryWrapper1.eq("id", discussCommentVo.getEntityId());
                    if(discussCommentService.getOne(queryWrapper1)==null)
                        return ResultObj.UPDATE_ERROR;
                    break;
            }
            this.discussCommentService.updateById(discussCommentVo);
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.UPDATE_ERROR;
        }
    }

    /**
     * 删除讨论区评论
     */
    @RequestMapping("/deleteComment")
    @ResponseBody
    public ResultObj deleteComment(Integer id){
        try {

            this.discussCommentService.removeById(id);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }

    /**
     * 批量删除讨论区评论
     */
    @RequestMapping("/batchDeleteComment")
    @ResponseBody
    public ResultObj batchDeleteComment(DiscussCommentVo discussCommentVo) {
        try {
            Collection<Serializable> idList = new ArrayList<Serializable>();
            for (Integer id : discussCommentVo.getIds()) {
                idList.add(id);
            }
            this.discussCommentService.removeByIds(idList);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }


}
