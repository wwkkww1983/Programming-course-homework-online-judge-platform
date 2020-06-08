package com.xloya.pldp.controller.AdminController;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xloya.pldp.common.DataGridView;
import com.xloya.pldp.common.ResultObj;
import com.xloya.pldp.entity.*;
import com.xloya.pldp.service.*;
import com.xloya.pldp.vo.*;
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
public class ExamManageController {

    @Autowired
    ExamAnswerinfoService examAnswerinfoService;

    @Autowired
    ExamPaperinfoService examPaperinfoService;

    @Autowired
    ExamScoreinfoService examScoreinfoService;

    @Autowired
    CoursesService coursesService;

    @Autowired
    TeachersService teachersService;

    @Autowired
    StudentsService studentsService;

    @Autowired
    SingleSelectQuestionService singleSelectQuestionService;

    @Autowired
    FillBlankQuestionService fillBlankQuestionService;

    @Autowired
    ExerciseQuestionService exerciseQuestionService;


    /**
     * 跳转到试卷信息管理页面
     */
    @RequestMapping("/toPaperInfoManager")
    public String toPaperInfoManager() {
        return "system/admins/exammanage/paper_info_manager";
    }


    /**
     * 试卷信息查询
     */
    @RequestMapping("/showPaperList")
    @ResponseBody
    public DataGridView showPaperList(ExamPaperInfoVo examPaperInfoVo) {
        IPage<ExamPaperinfo> page = new Page<>(examPaperInfoVo.getPage(), examPaperInfoVo.getLimit());
        QueryWrapper<ExamPaperinfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(examPaperInfoVo.getPaperName()),"paper_name",examPaperInfoVo.getPaperName());
        queryWrapper.eq(examPaperInfoVo.getCourseId()!=null,"course_id",examPaperInfoVo.getCourseId());
        queryWrapper.eq(examPaperInfoVo.getTeacherId()!=null,"teacher_id",examPaperInfoVo.getTeacherId());
        queryWrapper.ge(StringUtils.isNotBlank(examPaperInfoVo.getStartTime()), "start_time", examPaperInfoVo.getStartTime());
        queryWrapper.le(StringUtils.isNotBlank(examPaperInfoVo.getEndTime()), "end_time", examPaperInfoVo.getEndTime());
        this.examPaperinfoService.page(page, queryWrapper);
        return new DataGridView(page.getTotal(), page.getRecords());
    }


    /**
     * 修改试卷信息
     */
    @RequestMapping("/updatePaper")
    @ResponseBody
    public ResultObj updatePaper(ExamPaperInfoVo examPaperInfoVo) {
        try {
            QueryWrapper<Courses> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("course_id",examPaperInfoVo.getCourseId());
            QueryWrapper<Teachers> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("teacher_id",examPaperInfoVo.getTeacherId());
            if(coursesService.getOne(queryWrapper)==null||teachersService.getOne(queryWrapper1)==null)
                return ResultObj.UPDATE_ERROR;

            String starttime = examPaperInfoVo.getStartTime();
            String endtime = examPaperInfoVo.getEndTime();
            if(starttime.compareTo(endtime)>0)
                return ResultObj.UPDATE_ERROR;
            this.examPaperinfoService.updateById(examPaperInfoVo);
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.UPDATE_ERROR;
        }
    }

    /**
     * 删除试卷信息
     */
    @RequestMapping("/deletePaper")
    @ResponseBody
    public ResultObj deletePaper(Integer id){
        try {
            QueryWrapper<ExamAnswerinfo> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("paper_id",id);
            examAnswerinfoService.remove(queryWrapper);

            QueryWrapper<ExamScoreinfo> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("paper_id",id);
            examScoreinfoService.remove(queryWrapper1);

            this.examPaperinfoService.removeById(id);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }

    /**
     * 批量删除试卷信息
     */
    @RequestMapping("/batchDeletePaper")
    @ResponseBody
    public ResultObj batchDeletePaper(ExamPaperInfoVo examPaperInfoVo) {
        try {
            Collection<Serializable> idList = new ArrayList<Serializable>();
            for (Integer id : examPaperInfoVo.getIds()) {
                idList.add(id);
                QueryWrapper<ExamAnswerinfo> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("paper_id",id);
                examAnswerinfoService.remove(queryWrapper);

                QueryWrapper<ExamScoreinfo> queryWrapper1 = new QueryWrapper<>();
                queryWrapper1.eq("paper_id",id);
                examScoreinfoService.remove(queryWrapper1);
            }
            this.examPaperinfoService.removeByIds(idList);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }





    /**
     * 跳转到考生考试答案信息管理页面
     */
    @RequestMapping("/toAnswerInfoManager")
    public String toAnswerInfoManager() {
        return "system/admins/exammanage/answer_info_manager";
    }

    /**
     * 考生考试答案信息查询
     */
    @RequestMapping("/showAnswerList")
    @ResponseBody
    public DataGridView showAnswerList(ExamAnswerInfoVo examAnswerInfoVo) {
        IPage<ExamAnswerinfo> page = new Page<>(examAnswerInfoVo.getPage(), examAnswerInfoVo.getLimit());
        QueryWrapper<ExamAnswerinfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(examAnswerInfoVo.getQuestionId()!=null,"question_id",examAnswerInfoVo.getQuestionId());
        queryWrapper.eq(examAnswerInfoVo.getPaperId()!=null,"paper_id",examAnswerInfoVo.getPaperId());
        queryWrapper.eq(examAnswerInfoVo.getStudentId()!=null,"student_id",examAnswerInfoVo.getStudentId());
        queryWrapper.eq(examAnswerInfoVo.getType()!=null,"type",examAnswerInfoVo.getType());
        this.examAnswerinfoService.page(page, queryWrapper);
        return new DataGridView(page.getTotal(), page.getRecords());
    }


    /**
     * 修改考生考试答案信息
     */
    @RequestMapping("/updateAnswer")
    @ResponseBody
    public ResultObj updateAnswer(ExamAnswerInfoVo examAnswerInfoVo) {
        try {
            QueryWrapper<Students> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("student_id",examAnswerInfoVo.getStudentId());

            QueryWrapper<ExamPaperinfo> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("id",examAnswerInfoVo.getPaperId());
            if(studentsService.getOne(queryWrapper)==null||examPaperinfoService.getOne(queryWrapper1)==null)
                return ResultObj.UPDATE_ERROR;
            int type = Integer.valueOf(examAnswerInfoVo.getType());
            switch (type) {
                case 1:
                    QueryWrapper<SingleSelectQuestion> queryWrapper2 = new QueryWrapper<>();
                    queryWrapper2.eq("id",examAnswerInfoVo.getQuestionId());
                    if(singleSelectQuestionService.getOne(queryWrapper2)==null)
                        return ResultObj.UPDATE_ERROR;
                    break;
                case 2:
                    QueryWrapper<FillBlankQuestion> queryWrapper3 = new QueryWrapper<>();
                    queryWrapper3.eq("id",examAnswerInfoVo.getQuestionId());
                    if(fillBlankQuestionService.getOne(queryWrapper3)==null)
                        return ResultObj.UPDATE_ERROR;
                    break;
                case 3:
                    QueryWrapper<ExerciseQuestion> queryWrapper4 = new QueryWrapper<>();
                    queryWrapper4.eq("id",examAnswerInfoVo.getQuestionId());
                    if(exerciseQuestionService.getOne(queryWrapper4)==null)
                        return ResultObj.UPDATE_ERROR;
                    break;
            }
            this.examAnswerinfoService.updateById(examAnswerInfoVo);
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.UPDATE_ERROR;
        }
    }

    /**
     * 删除考生考试答案信息
     */
    @RequestMapping("/deleteAnswer")
    @ResponseBody
    public ResultObj deleteAnswer(Integer id){
        try {
            this.examAnswerinfoService.removeById(id);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }

    /**
     * 批量删除考生考试答案信息
     */
    @RequestMapping("/batchDeleteAnswer")
    @ResponseBody
    public ResultObj batchDeleteAnswer(ExamAnswerInfoVo examAnswerInfoVo) {
        try {
            Collection<Serializable> idList = new ArrayList<Serializable>();
            for (Integer id : examAnswerInfoVo.getIds()) {
                idList.add(id);
            }
            this.examAnswerinfoService.removeByIds(idList);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }



    /**
     * 跳转到考生成绩信息管理页面
     */
    @RequestMapping("/toScoreInfoManager")
    public String toScoreInfoManager() {
        return "system/admins/exammanage/score_info_manager";
    }


    /**
     * 考生成绩信息查询
     */
    @RequestMapping("/showScoreList")
    @ResponseBody
    public DataGridView showScoreList(ExamScoreInfoVo examScoreInfoVo) {
        IPage<ExamScoreinfo> page = new Page<>(examScoreInfoVo.getPage(), examScoreInfoVo.getLimit());
        QueryWrapper<ExamScoreinfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(examScoreInfoVo.getPaperId()!=null,"paper_id",examScoreInfoVo.getPaperId());
        queryWrapper.eq(examScoreInfoVo.getStudentId()!=null,"student_id",examScoreInfoVo.getStudentId());
        this.examScoreinfoService.page(page, queryWrapper);
        return new DataGridView(page.getTotal(), page.getRecords());
    }


    /**
     * 修改考生成绩信息
     */
    @RequestMapping("/updateScore")
    @ResponseBody
    public ResultObj updateScore(ExamScoreInfoVo examScoreInfoVo) {
        try {
            QueryWrapper<Students> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("student_id",examScoreInfoVo.getStudentId());

            QueryWrapper<ExamPaperinfo> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("id",examScoreInfoVo.getPaperId());
            if(studentsService.getOne(queryWrapper)==null||examPaperinfoService.getOne(queryWrapper1)==null)
                return ResultObj.UPDATE_ERROR;
            this.examScoreinfoService.updateById(examScoreInfoVo);
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.UPDATE_ERROR;
        }
    }

    /**
     * 删除考生成绩信息
     */
    @RequestMapping("/deleteScore")
    @ResponseBody
    public ResultObj deleteScore(Integer id){
        try {
            this.examScoreinfoService.removeById(id);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }

    /**
     * 批量删除考生成绩信息
     */
    @RequestMapping("/batchDeleteScore")
    @ResponseBody
    public ResultObj batchDeleteScore(ExamScoreInfoVo examScoreInfoVo) {
        try {
            Collection<Serializable> idList = new ArrayList<Serializable>();
            for (Integer id : examScoreInfoVo.getIds()) {
                idList.add(id);
            }
            this.examScoreinfoService.removeByIds(idList);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }

}
