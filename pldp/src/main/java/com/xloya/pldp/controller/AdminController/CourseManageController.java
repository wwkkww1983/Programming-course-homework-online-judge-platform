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
import java.util.List;


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
public class CourseManageController {

    @Autowired
    CoursesService coursesService;

    @Autowired
    CourseContentService courseContentService;

    @Autowired
    TeachersService teachersService;

    @Autowired
    SingleSelectQuestionService singleSelectQuestionService;

    @Autowired
    FillBlankQuestionService fillBlankQuestionService;

    @Autowired
    CodeQuestionService codeQuestionService;

    @Autowired
    ExerciseQuestionService exerciseQuestionService;

    @Autowired
    CodeJudgementService codeJudgementService;

    @Autowired
    ExerciseJudgementService exerciseJudgementService;

    @Autowired
    CodeDiscussService codeDiscussService;

    /**
     * 跳转到课程信息管理页面
     */
    @RequestMapping("/toCourseInfoManager")
    public String toCourseInfoManager() {
        return "system/admins/coursemanage/course_info_manager";
    }

    /**
     * 课程信息查询
     */
    @RequestMapping("/showCourseList")
    @ResponseBody
    public DataGridView showCourseList(CoursesVo coursesVo) {
        IPage<Courses> page = new Page<>(coursesVo.getPage(), coursesVo.getLimit());
        QueryWrapper<Courses> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(coursesVo.getCourseName()),"course_name",coursesVo.getCourseName());
        queryWrapper.eq(coursesVo.getCourseId()!=null,"course_id",coursesVo.getCourseId());
        queryWrapper.eq(coursesVo.getTeacherId()!=null,"teacher_id",coursesVo.getTeacherId());
        this.coursesService.page(page, queryWrapper);
        return new DataGridView(page.getTotal(), page.getRecords());
    }

    /**
     * 添加课程信息
     */
    @RequestMapping("/addCourse")
    @ResponseBody
    public ResultObj addCourse(CoursesVo coursesVo){
        try {
            QueryWrapper<Courses> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("course_id",coursesVo.getCourseId());
            QueryWrapper<Teachers> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("teacher_id",coursesVo.getTeacherId());
            if(null!=coursesService.getOne(queryWrapper)||null==teachersService.getOne(queryWrapper1))
                return ResultObj.ADD_ERROR;
            coursesService.save(coursesVo);
            return ResultObj.ADD_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.ADD_ERROR;
        }
    }


    /**
     * 修改课程信息
     */
    @RequestMapping("/updateCourse")
    @ResponseBody
    public ResultObj updateCourse(CoursesVo coursesVo) {
        try {
            QueryWrapper<Courses> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("course_id",coursesVo.getCourseId());
            //if(classesService.getOne(queryWrapper)==null)
                //return ResultObj.UPDATE_ERROR;
            this.coursesService.updateById(coursesVo);
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.UPDATE_ERROR;
        }
    }

    /**
     * 删除课程信息
     */
    @RequestMapping("/deleteCourse")
    @ResponseBody
    public ResultObj deleteCourse(Integer id){
        try {
            Courses courses = coursesService.getById(id);

            QueryWrapper<ExerciseQuestion> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("course_id",courses.getCourseId());
            List<ExerciseQuestion> exerciseQuestionList = exerciseQuestionService.list(queryWrapper);
            exerciseQuestionService.remove(queryWrapper);

            for(ExerciseQuestion exerciseQuestion:exerciseQuestionList){
                QueryWrapper<ExerciseJudgement> queryWrapper1 = new QueryWrapper<>();
                queryWrapper1.eq("exercise_id",exerciseQuestion.getId());
                exerciseJudgementService.remove(queryWrapper1);
            }

            QueryWrapper<SingleSelectQuestion> queryWrapper2 = new QueryWrapper<>();
            queryWrapper2.eq("course_id",courses.getCourseId());
            singleSelectQuestionService.remove(queryWrapper2);

            QueryWrapper<FillBlankQuestion> queryWrapper3 = new QueryWrapper<>();
            queryWrapper3.eq("course_id",courses.getCourseId());
            fillBlankQuestionService.remove(queryWrapper3);

            QueryWrapper<CourseContent> queryWrapper4 = new QueryWrapper<>();
            queryWrapper4.eq("course_id",courses.getCourseId());
            courseContentService.remove(queryWrapper4);

            this.coursesService.removeById(id);

            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }

    /**
     * 批量删除课程信息
     */
    @RequestMapping("/batchDeleteCourse")
    @ResponseBody
    public ResultObj batchDeleteCourse(CoursesVo coursesVo) {
        try {
            Collection<Serializable> idList = new ArrayList<Serializable>();
            for (Integer id : coursesVo.getIds()) {
                idList.add(id);
                Courses courses = coursesService.getById(id);

                QueryWrapper<ExerciseQuestion> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("course_id",courses.getCourseId());
                List<ExerciseQuestion> exerciseQuestionList = exerciseQuestionService.list(queryWrapper);
                exerciseQuestionService.remove(queryWrapper);

                for(ExerciseQuestion exerciseQuestion:exerciseQuestionList){
                    QueryWrapper<ExerciseJudgement> queryWrapper1 = new QueryWrapper<>();
                    queryWrapper1.eq("exercise_id",exerciseQuestion.getId());
                    exerciseJudgementService.remove(queryWrapper1);
                }

                QueryWrapper<SingleSelectQuestion> queryWrapper2 = new QueryWrapper<>();
                queryWrapper2.eq("course_id",courses.getCourseId());
                singleSelectQuestionService.remove(queryWrapper2);

                QueryWrapper<FillBlankQuestion> queryWrapper3 = new QueryWrapper<>();
                queryWrapper3.eq("course_id",courses.getCourseId());
                fillBlankQuestionService.remove(queryWrapper3);

                QueryWrapper<CourseContent> queryWrapper4 = new QueryWrapper<>();
                queryWrapper4.eq("course_id",courses.getCourseId());
                courseContentService.remove(queryWrapper4);

            }
            this.coursesService.removeByIds(idList);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }






    /**
     * 跳转到课程内容管理页面
     */
    @RequestMapping("/toCourseContentManager")
    public String toCourseContentManager() {
        return "system/admins/coursemanage/course_content_manager";
    }

    /**
     * 课程内容信息查询
     */
    @RequestMapping("/showCourseContentList")
    @ResponseBody
    public DataGridView showCourseContentList(CourseContentsVo courseContentsVo) {
        IPage<CourseContent> page = new Page<>(courseContentsVo.getPage(), courseContentsVo.getLimit());
        QueryWrapper<CourseContent> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(courseContentsVo.getChapter()),"chapter",courseContentsVo.getChapter());
        queryWrapper.like(StringUtils.isNotBlank(courseContentsVo.getSection()),"section",courseContentsVo.getSection());
        queryWrapper.like(StringUtils.isNotBlank(courseContentsVo.getTitle()),"title",courseContentsVo.getTitle());
        queryWrapper.eq(courseContentsVo.getCourseId()!=null,"course_id",courseContentsVo.getCourseId());
        queryWrapper.eq(courseContentsVo.getExerciseId()!=null,"exercise_id",courseContentsVo.getExerciseId());
        this.courseContentService.page(page, queryWrapper);
        return new DataGridView(page.getTotal(), page.getRecords());
    }

    /**
     * 添加课程内容信息
     */
    @RequestMapping("/addCourseContent")
    @ResponseBody
    public ResultObj addCourseContent(CourseContentsVo courseContentsVo){
        try {
            QueryWrapper<CourseContent> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("course_id",courseContentsVo.getCourseId());
            queryWrapper.eq("chapter",courseContentsVo.getChapter());
            queryWrapper.eq("section",courseContentsVo.getSection());

            QueryWrapper<Courses> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("course_id",courseContentsVo.getCourseId());
            if(null!=courseContentService.getOne(queryWrapper)||null==coursesService.getOne(queryWrapper1))
                return ResultObj.ADD_ERROR;
            courseContentService.save(courseContentsVo);
            return ResultObj.ADD_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.ADD_ERROR;
        }
    }


    /**
     * 修改课程内容信息
     */
    @RequestMapping("/updateCourseContent")
    @ResponseBody
    public ResultObj updateCourseContent(CourseContentsVo courseContentsVo) {
        try {
            //QueryWrapper<CourseContent> queryWrapper = new QueryWrapper<>();
            //queryWrapper.eq("course_id",courseContentsVo.getCourseId());
            //if(classesService.getOne(queryWrapper)==null)
            //return ResultObj.UPDATE_ERROR;
            this.courseContentService.updateById(courseContentsVo);
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.UPDATE_ERROR;
        }
    }

    /**
     * 删除课程内容信息
     */
    @RequestMapping("/deleteCourseContent")
    @ResponseBody
    public ResultObj deleteCourseContent(Integer id){
        try {
            CourseContent courseContent = courseContentService.getById(id);
            if(courseContent.getExerciseId()!=0) {
                QueryWrapper<ExerciseQuestion> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("exercise_id", courseContent.getExerciseId());
                exerciseQuestionService.remove(queryWrapper);

                QueryWrapper<ExerciseJudgement> queryWrapper1 = new QueryWrapper<>();
                queryWrapper1.eq("exercise_id", courseContent.getExerciseId());
                exerciseJudgementService.remove(queryWrapper1);
            }

            QueryWrapper<SingleSelectQuestion> queryWrapper2 = new QueryWrapper<>();
            queryWrapper2.eq("course_id",courseContent.getCourseId());
            queryWrapper2.eq("chapter",courseContent.getChapter());
            queryWrapper2.eq("section",courseContent.getSection());
            singleSelectQuestionService.remove(queryWrapper2);

            QueryWrapper<FillBlankQuestion> queryWrapper3 = new QueryWrapper<>();
            queryWrapper3.eq("course_id",courseContent.getCourseId());
            queryWrapper3.eq("chapter",courseContent.getChapter());
            queryWrapper3.eq("section",courseContent.getSection());
            fillBlankQuestionService.remove(queryWrapper3);

            this.courseContentService.removeById(id);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }

    /**
     * 批量删除课程内容信息
     */
    @RequestMapping("/batchDeleteCourseContent")
    @ResponseBody
    public ResultObj batchDeleteCourseContent(CourseContentsVo courseContentsVo) {
        try {
            Collection<Serializable> idList = new ArrayList<Serializable>();
            for (Integer id : courseContentsVo.getIds()) {
                idList.add(id);
                CourseContent courseContent = courseContentService.getById(id);
                QueryWrapper<ExerciseQuestion> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("exercise_id",courseContent.getExerciseId());

                QueryWrapper<ExerciseJudgement> queryWrapper1 = new QueryWrapper<>();
                queryWrapper1.eq("exercise_id",courseContent.getExerciseId());
                exerciseJudgementService.remove(queryWrapper1);

                exerciseQuestionService.remove(queryWrapper);

                QueryWrapper<SingleSelectQuestion> queryWrapper2 = new QueryWrapper<>();
                queryWrapper2.eq("course_id",courseContent.getCourseId());
                queryWrapper2.eq("chapter",courseContent.getChapter());
                queryWrapper2.eq("section",courseContent.getSection());
                singleSelectQuestionService.remove(queryWrapper2);

                QueryWrapper<FillBlankQuestion> queryWrapper3 = new QueryWrapper<>();
                queryWrapper3.eq("course_id",courseContent.getCourseId());
                queryWrapper3.eq("chapter",courseContent.getChapter());
                queryWrapper3.eq("section",courseContent.getSection());
                fillBlankQuestionService.remove(queryWrapper3);

            }
            this.courseContentService.removeByIds(idList);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }





    /**
     * 跳转到选择题题库管理页面
     */
    @RequestMapping("/toSingleSelectManager")
    public String toSingleSelectManager() {
        return "system/admins/coursemanage/single_select_manager";
    }

    /**
     * 单选题题库信息查询
     */
    @RequestMapping("/showSingleSelectQuestionList")
    @ResponseBody
    public DataGridView showSingleSelectQuestionList(SingleSelectQuestionVo singleSelectQuestionVo) {
        IPage<SingleSelectQuestion> page = new Page<>(singleSelectQuestionVo.getPage(), singleSelectQuestionVo.getLimit());
        QueryWrapper<SingleSelectQuestion> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(singleSelectQuestionVo.getChapter()),"chapter",singleSelectQuestionVo.getChapter());
        queryWrapper.like(StringUtils.isNotBlank(singleSelectQuestionVo.getSection()),"section",singleSelectQuestionVo.getSection());
        queryWrapper.eq(singleSelectQuestionVo.getId()!=null,"id",singleSelectQuestionVo.getId());
        queryWrapper.eq(singleSelectQuestionVo.getCourseId()!=null,"course_id",singleSelectQuestionVo.getCourseId());
        queryWrapper.eq(singleSelectQuestionVo.getDegree()!=null,"degree",singleSelectQuestionVo.getDegree());
        this.singleSelectQuestionService.page(page, queryWrapper);
        return new DataGridView(page.getTotal(), page.getRecords());
    }

    /**
     * 添加单选题
     */
    @RequestMapping("/addSingleSelectQuestion")
    @ResponseBody
    public ResultObj addSingleSelectQuestion(SingleSelectQuestionVo singleSelectQuestionVo){
        try {
            QueryWrapper<CourseContent> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("course_id",singleSelectQuestionVo.getCourseId());
            queryWrapper.eq("chapter",singleSelectQuestionVo.getChapter());
            queryWrapper.eq("section",singleSelectQuestionVo.getSection());
            if(null==courseContentService.getOne(queryWrapper))
                return ResultObj.ADD_ERROR;
            singleSelectQuestionService.save(singleSelectQuestionVo);
            return ResultObj.ADD_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.ADD_ERROR;
        }
    }


    /**
     * 修改单选题
     */
    @RequestMapping("/updateSingleSelectQuestion")
    @ResponseBody
    public ResultObj updateSingleSelectQuestion(SingleSelectQuestionVo singleSelectQuestionVo) {
        try {
            //QueryWrapper<CourseContent> queryWrapper = new QueryWrapper<>();
            //queryWrapper.eq("course_id",courseContentsVo.getCourseId());
            //if(classesService.getOne(queryWrapper)==null)
            //return ResultObj.UPDATE_ERROR;
            this.singleSelectQuestionService.updateById(singleSelectQuestionVo);
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.UPDATE_ERROR;
        }
    }

    /**
     * 删除单选题
     */
    @RequestMapping("/deleteSingleSelectQuestion")
    @ResponseBody
    public ResultObj deleteSingleSelectQuestion(Integer id){
        try {
            this.singleSelectQuestionService.removeById(id);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }

    /**
     * 批量删除单选题
     */
    @RequestMapping("/batchDeleteSingleSelectQuestion")
    @ResponseBody
    public ResultObj batchDeleteSingleSelectQuestion(SingleSelectQuestionVo singleSelectQuestionVo) {
        try {
            Collection<Serializable> idList = new ArrayList<Serializable>();
            for (Integer id : singleSelectQuestionVo.getIds()) {
                idList.add(id);
            }
            this.singleSelectQuestionService.removeByIds(idList);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }




    /**
     * 跳转到填空题题库管理页面
     */
    @RequestMapping("/toFillBlankManager")
    public String toFillBlankManager() {
        return "system/admins/coursemanage/fill_blank_manager";
    }

    /**
     * 填空题题库信息查询
     */
    @RequestMapping("/showFillBlankQuestionList")
    @ResponseBody
    public DataGridView showFillBlankQuestionList(FillBlankQuestionVo fillBlankQuestionVo) {
        IPage<FillBlankQuestion> page = new Page<>(fillBlankQuestionVo.getPage(), fillBlankQuestionVo.getLimit());
        QueryWrapper<FillBlankQuestion> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(fillBlankQuestionVo.getChapter()),"chapter",fillBlankQuestionVo.getChapter());
        queryWrapper.like(StringUtils.isNotBlank(fillBlankQuestionVo.getSection()),"section",fillBlankQuestionVo.getSection());
        queryWrapper.eq(fillBlankQuestionVo.getId()!=null,"id",fillBlankQuestionVo.getId());
        queryWrapper.eq(fillBlankQuestionVo.getCourseId()!=null,"course_id",fillBlankQuestionVo.getCourseId());
        queryWrapper.eq(fillBlankQuestionVo.getDegree()!=null,"degree",fillBlankQuestionVo.getDegree());
        this.fillBlankQuestionService.page(page, queryWrapper);
        return new DataGridView(page.getTotal(), page.getRecords());
    }

    /**
     * 添加填空题
     */
    @RequestMapping("/addFillBlankQuestion")
    @ResponseBody
    public ResultObj addFillBlankQuestion(FillBlankQuestionVo fillBlankQuestionVo){
        try {
            QueryWrapper<CourseContent> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("course_id",fillBlankQuestionVo.getCourseId());
            queryWrapper.eq("chapter",fillBlankQuestionVo.getChapter());
            queryWrapper.eq("section",fillBlankQuestionVo.getSection());
            if(null==courseContentService.getOne(queryWrapper))
                return ResultObj.ADD_ERROR;
            fillBlankQuestionService.save(fillBlankQuestionVo);
            return ResultObj.ADD_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.ADD_ERROR;
        }
    }


    /**
     * 修改填空题
     */
    @RequestMapping("/updateFillBlankQuestion")
    @ResponseBody
    public ResultObj updateFillBlankQuestion(FillBlankQuestionVo fillBlankQuestionVo) {
        try {
            //QueryWrapper<CourseContent> queryWrapper = new QueryWrapper<>();
            //queryWrapper.eq("course_id",courseContentsVo.getCourseId());
            //if(classesService.getOne(queryWrapper)==null)
            //return ResultObj.UPDATE_ERROR;
            this.fillBlankQuestionService.updateById(fillBlankQuestionVo);
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.UPDATE_ERROR;
        }
    }

    /**
     * 删除填空题
     */
    @RequestMapping("/deleteFillBlankQuestion")
    @ResponseBody
    public ResultObj deleteFillBlankQuestion(Integer id){
        try {
            this.fillBlankQuestionService.removeById(id);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }

    /**
     * 批量删除填空题
     */
    @RequestMapping("/batchDeleteFillBlankQuestion")
    @ResponseBody
    public ResultObj batchDeleteFillBlankQuestion(FillBlankQuestionVo fillBlankQuestionVo) {
        try {
            Collection<Serializable> idList = new ArrayList<Serializable>();
            for (Integer id : fillBlankQuestionVo.getIds()) {
                idList.add(id);
            }
            this.fillBlankQuestionService.removeByIds(idList);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }



    /**
     * 跳转到编程题题库管理页面
     */
    @RequestMapping("/toCodeManager")
    public String toCodeManager() {
        return "system/admins/coursemanage/code_manager";
    }

    /**
     * 编程题库信息查询
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
     * 添加编程题
     */
    @RequestMapping("/addCodeQuestion")
    @ResponseBody
    public ResultObj addCodeQuestion(CodeQuestionVo codeQuestionVo){
        try {
            QueryWrapper<Courses> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("course_id",codeQuestionVo.getCourseId());
            if(null==coursesService.getOne(queryWrapper))
                return ResultObj.ADD_ERROR;
            codeQuestionService.save(codeQuestionVo);
            return ResultObj.ADD_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.ADD_ERROR;
        }
    }


    /**
     * 修改编程题
     */
    @RequestMapping("/updateCodeQuestion")
    @ResponseBody
    public ResultObj updateCodeQuestion(CodeQuestionVo codeQuestionVo) {
        try {
            //QueryWrapper<CourseContent> queryWrapper = new QueryWrapper<>();
            //queryWrapper.eq("course_id",courseContentsVo.getCourseId());
            //if(classesService.getOne(queryWrapper)==null)
            //return ResultObj.UPDATE_ERROR;
            this.codeQuestionService.updateById(codeQuestionVo);
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.UPDATE_ERROR;
        }
    }

    /**
     * 删除编程题
     */
    @RequestMapping("/deleteCodeQuestion")
    @ResponseBody
    public ResultObj deleteCodeQuestion(Integer id){
        try {
            QueryWrapper<CodeJudgement> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("code_id",id);
            codeJudgementService.remove(queryWrapper);
            QueryWrapper<CodeDiscuss> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("code_id",id);
            codeDiscussService.remove(queryWrapper1);
            this.codeQuestionService.removeById(id);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }

    /**
     * 批量删除编程题
     */
    @RequestMapping("/batchDeleteCodeQuestion")
    @ResponseBody
    public ResultObj batchDeleteCodeQuestion(CodeQuestionVo codeQuestionVo) {
        try {
            Collection<Serializable> idList = new ArrayList<Serializable>();
            for (Integer id : codeQuestionVo.getIds()) {
                idList.add(id);
                QueryWrapper<CodeJudgement> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("code_id",id);
                codeJudgementService.remove(queryWrapper);
                QueryWrapper<CodeDiscuss> queryWrapper1 = new QueryWrapper<>();
                queryWrapper1.eq("code_id",id);
                codeDiscussService.remove(queryWrapper1);
            }
            this.codeQuestionService.removeByIds(idList);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }



    /**
     * 跳转到编程题讨论管理页面
     */
    @RequestMapping("/toCodeDiscussManager")
    public String toCodeDiscussManager() {
        return "system/admins/coursemanage/code_discuss_manager";
    }


    /**
     * 编程题讨论信息查询
     */
    @RequestMapping("/showCodeDiscussList")
    @ResponseBody
    public DataGridView showCodeDiscussList(CodeDiscussVo codeDiscussVo) {
        IPage<CodeDiscuss> page = new Page<>(codeDiscussVo.getPage(), codeDiscussVo.getLimit());
        QueryWrapper<CodeDiscuss> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotBlank(codeDiscussVo.getCreateTime()),"create_time",codeDiscussVo.getCreateTime());
        queryWrapper.eq(codeDiscussVo.getId()!=null,"id",codeDiscussVo.getId());
        queryWrapper.eq(codeDiscussVo.getCodeId()!=null,"code_id",codeDiscussVo.getCodeId());
        queryWrapper.eq(codeDiscussVo.getStudentId()!=null,"student_id",codeDiscussVo.getStudentId());
        this.codeDiscussService.page(page, queryWrapper);
        return new DataGridView(page.getTotal(), page.getRecords());
    }

    /**
     * 修改编程题讨论
     */
    @RequestMapping("/updateCodeDiscuss")
    @ResponseBody
    public ResultObj updateCodeDiscuss(CodeDiscussVo codeDiscussVo) {
        try {
            //QueryWrapper<CourseContent> queryWrapper = new QueryWrapper<>();
            //queryWrapper.eq("course_id",courseContentsVo.getCourseId());
            //if(classesService.getOne(queryWrapper)==null)
            //return ResultObj.UPDATE_ERROR;
            this.codeDiscussService.updateById(codeDiscussVo);
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.UPDATE_ERROR;
        }
    }

    /**
     * 删除编程题讨论
     */
    @RequestMapping("/deleteCodeDiscuss")
    @ResponseBody
    public ResultObj deleteCodeDiscuss(Integer id){
        try {
            this.codeDiscussService.removeById(id);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }

    /**
     * 批量删除编程题讨论
     */
    @RequestMapping("/batchDeleteCodeDiscuss")
    @ResponseBody
    public ResultObj batchDeleteCodeDiscuss(CodeDiscussVo codeDiscussVo) {
        try {
            Collection<Serializable> idList = new ArrayList<Serializable>();
            for (Integer id : codeDiscussVo.getIds()) {
                idList.add(id);
            }
            this.codeDiscussService.removeByIds(idList);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }







    /**
     * 跳转到编程题测试用例管理页面
     */
    @RequestMapping("/toCodeTestManager")
    public String toCodeTestManager() {
        return "system/admins/coursemanage/code_test_manager";
    }


    /**
     * 编程题测试用例信息查询
     */
    @RequestMapping("/showCodeJudgementList")
    @ResponseBody
    public DataGridView showCodeJudgementList(CodeJudgementVo codeJudgementVo) {
        IPage<CodeJudgement> page = new Page<>(codeJudgementVo.getPage(), codeJudgementVo.getLimit());
        QueryWrapper<CodeJudgement> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(codeJudgementVo.getId()!=null,"id",codeJudgementVo.getId());
        queryWrapper.eq(codeJudgementVo.getCodeId()!=null,"code_id",codeJudgementVo.getCodeId());
        //queryWrapper.like(codeJudgementVo.getTestInput()!=null,"test_input",codeJudgementVo.getTestInput());
        //queryWrapper.like(codeJudgementVo.getTestOutput()!=null,"test_Output",codeJudgementVo.getTestOutput());
        this.codeJudgementService.page(page, queryWrapper);
        return new DataGridView(page.getTotal(), page.getRecords());
    }

    /**
     * 添加编程题测试用例
     */
    @RequestMapping("/addCodeJudgement")
    @ResponseBody
    public ResultObj addCodeJudgement(CodeJudgementVo codeJudgementVo){
        try {
            QueryWrapper<CodeQuestion> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("id",codeJudgementVo.getCodeId());
            if(null==codeQuestionService.getOne(queryWrapper))
                return ResultObj.ADD_ERROR;
            codeJudgementService.save(codeJudgementVo);
            return ResultObj.ADD_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.ADD_ERROR;
        }
    }


    /**
     * 修改编程题测试用例
     */
    @RequestMapping("/updateCodeJudgement")
    @ResponseBody
    public ResultObj updateCodeJudgement(CodeJudgementVo codeJudgementVo) {
        try {
            //QueryWrapper<CourseContent> queryWrapper = new QueryWrapper<>();
            //queryWrapper.eq("course_id",courseContentsVo.getCourseId());
            //if(classesService.getOne(queryWrapper)==null)
            //return ResultObj.UPDATE_ERROR;
            this.codeJudgementService.updateById(codeJudgementVo);
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.UPDATE_ERROR;
        }
    }

    /**
     * 删除编程题测试用例
     */
    @RequestMapping("/deleteCodeJudgement")
    @ResponseBody
    public ResultObj deleteCodeJudgement(Integer id){
        try {
            this.codeJudgementService.removeById(id);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }

    /**
     * 批量删除编程题测试用例
     */
    @RequestMapping("/batchDeleteCodeJudgement")
    @ResponseBody
    public ResultObj batchDeleteCodeJudgement(CodeJudgementVo codeJudgementVo) {
        try {
            Collection<Serializable> idList = new ArrayList<Serializable>();
            for (Integer id : codeJudgementVo.getIds()) {
                idList.add(id);
            }
            this.codeJudgementService.removeByIds(idList);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }





    /**
     * 跳转到练习题题库管理页面
     */
    @RequestMapping("/toExerciseManager")
    public String toExerciseManager() {
        return "system/admins/coursemanage/exercise_manager";
    }


    /**
     * 练习题库信息查询
     */
    @RequestMapping("/showExerciseQuestionList")
    @ResponseBody
    public DataGridView showExerciseQuestionList(ExerciseQuestionVo exerciseQuestionVo) {
        IPage<ExerciseQuestion> page = new Page<>(exerciseQuestionVo.getPage(), exerciseQuestionVo.getLimit());
        QueryWrapper<ExerciseQuestion> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(exerciseQuestionVo.getTitle()),"title",exerciseQuestionVo.getTitle());
        queryWrapper.like(StringUtils.isNotBlank(exerciseQuestionVo.getChapter()),"chapter",exerciseQuestionVo.getChapter());
        queryWrapper.eq(exerciseQuestionVo.getId()!=null,"id",exerciseQuestionVo.getId());
        queryWrapper.eq(exerciseQuestionVo.getCourseId()!=null,"course_id",exerciseQuestionVo.getCourseId());
        queryWrapper.eq(exerciseQuestionVo.getDegree()!=null,"degree",exerciseQuestionVo.getDegree());
        this.exerciseQuestionService.page(page, queryWrapper);
        return new DataGridView(page.getTotal(), page.getRecords());
    }

    /**
     * 添加练习题
     */
    @RequestMapping("/addExerciseQuestion")
    @ResponseBody
    public ResultObj addExerciseQuestion(ExerciseQuestionVo exerciseQuestionVo){
        try {
            QueryWrapper<CourseContent> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("course_id",exerciseQuestionVo.getCourseId());
            queryWrapper.eq("chapter",exerciseQuestionVo.getChapter());
            if(courseContentService.list(queryWrapper).size()==0)
                return ResultObj.ADD_ERROR;
            exerciseQuestionService.save(exerciseQuestionVo);
            return ResultObj.ADD_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.ADD_ERROR;
        }
    }


    /**
     * 修改练习题
     */
    @RequestMapping("/updateExerciseQuestion")
    @ResponseBody
    public ResultObj updateExerciseQuestion(ExerciseQuestionVo exerciseQuestionVo) {
        try {
            QueryWrapper<CourseContent> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("course_id",exerciseQuestionVo.getCourseId());
            queryWrapper.eq("chapter",exerciseQuestionVo.getChapter());
            if(null==courseContentService.getOne(queryWrapper))
                return ResultObj.ADD_ERROR;
            this.exerciseQuestionService.updateById(exerciseQuestionVo);
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.UPDATE_ERROR;
        }
    }

    /**
     * 删除练习题
     */
    @RequestMapping("/deleteExerciseQuestion")
    @ResponseBody
    public ResultObj deleteExerciseQuestion(Integer id){
        try {
            QueryWrapper<ExerciseJudgement> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("exercise_id",id);
            exerciseJudgementService.remove(queryWrapper);
            this.exerciseQuestionService.removeById(id);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }

    /**
     * 批量删除练习题
     */
    @RequestMapping("/batchDeleteExerciseQuestion")
    @ResponseBody
    public ResultObj batchDeleteExerciseQuestion(ExerciseQuestionVo exerciseQuestionVo) {
        try {
            Collection<Serializable> idList = new ArrayList<Serializable>();
            for (Integer id : exerciseQuestionVo.getIds()) {
                idList.add(id);
                QueryWrapper<ExerciseJudgement> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("exercise_id",id);
                exerciseJudgementService.remove(queryWrapper);
            }
            this.exerciseQuestionService.removeByIds(idList);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }


    /**
     * 跳转到练习题测试用例管理页面
     */
    @RequestMapping("/toExerciseTestManager")
    public String toExerciseTestManager() {
        return "system/admins/coursemanage/exercise_test_manager";
    }


    /**
     * 练习题测试用例信息查询
     */
    @RequestMapping("/showExerciseJudgementList")
    @ResponseBody
    public DataGridView showExerciseJudgementList(ExerciseJudgementVo exerciseJudgementVo) {
        IPage<ExerciseJudgement> page = new Page<>(exerciseJudgementVo.getPage(), exerciseJudgementVo.getLimit());
        QueryWrapper<ExerciseJudgement> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(exerciseJudgementVo.getId()!=null,"id",exerciseJudgementVo.getId());
        queryWrapper.eq(exerciseJudgementVo.getExerciseId()!=null,"exercise_id",exerciseJudgementVo.getExerciseId());
        this.exerciseJudgementService.page(page, queryWrapper);
        return new DataGridView(page.getTotal(), page.getRecords());
    }

    /**
     * 添加练习题测试用例
     */
    @RequestMapping("/addExerciseJudgement")
    @ResponseBody
    public ResultObj addExerciseJudgement(ExerciseJudgementVo exerciseJudgementVo){
        try {
            QueryWrapper<ExerciseQuestion> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("id",exerciseJudgementVo.getExerciseId());
            if(null==exerciseQuestionService.getOne(queryWrapper))
                return ResultObj.ADD_ERROR;
            exerciseJudgementService.save(exerciseJudgementVo);
            return ResultObj.ADD_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.ADD_ERROR;
        }
    }


    /**
     * 修改练习题测试用例
     */
    @RequestMapping("/updateExerciseJudgement")
    @ResponseBody
    public ResultObj updateExerciseJudgement(ExerciseJudgementVo exerciseJudgementVo) {
        try {
            //QueryWrapper<CourseContent> queryWrapper = new QueryWrapper<>();
            //queryWrapper.eq("course_id",courseContentsVo.getCourseId());
            //if(classesService.getOne(queryWrapper)==null)
            //return ResultObj.UPDATE_ERROR;
            this.exerciseJudgementService.updateById(exerciseJudgementVo);
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.UPDATE_ERROR;
        }
    }

    /**
     * 删除练习题测试用例
     */
    @RequestMapping("/deleteExerciseJudgement")
    @ResponseBody
    public ResultObj deleteExerciseJudgement(Integer id){
        try {
            this.exerciseJudgementService.removeById(id);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }

    /**
     * 批量删除练习题测试用例
     */
    @RequestMapping("/batchDeleteExerciseJudgement")
    @ResponseBody
    public ResultObj batchDeleteExerciseJudgement(ExerciseJudgementVo exerciseJudgementVo) {
        try {
            Collection<Serializable> idList = new ArrayList<Serializable>();
            for (Integer id : exerciseJudgementVo.getIds()) {
                idList.add(id);
            }
            this.exerciseJudgementService.removeByIds(idList);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }

}
