package com.xloya.pldp.controller.StudentController;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xloya.pldp.common.DataGridView;
import com.xloya.pldp.common.ResultObj;
import com.xloya.pldp.entity.*;
import com.xloya.pldp.judger.ExerciseJudgeCore;
import com.xloya.pldp.service.*;
import com.xloya.pldp.vo.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/students")
public class MyExamController {
    @Autowired
    ExamPaperinfoService examPaperinfoService;

    @Autowired
    ExamScoreinfoService examScoreinfoService;

    @Autowired
    ExamSingleGroupService examSingleGroupService;

    @Autowired
    ExamBlankGroupService examBlankGroupService;

    @Autowired
    ExamCodeGroupService examCodeGroupService;

    @Autowired
    SingleSelectQuestionService singleSelectQuestionService;

    @Autowired
    FillBlankQuestionService fillBlankQuestionService;

    @Autowired
    ExerciseQuestionService exerciseQuestionService;

    @Autowired
    ExamAnswerinfoService examAnswerinfoService;

    @Autowired
    CoursesService coursesService;

    @Autowired
    ExerciseJudgeCore exerciseJudgeCore;

    @Autowired
    ExerciseSubmitService exerciseSubmitService;


    /**
     * 跳转到未完成考试页面
     */
    @RequestMapping("/toExamNotDo")
    public String toExamNotDo() {
        return "system/students/exam/exam_not_do";
    }



    /**
     * 考试未完成信息查询
     */
    @RequestMapping("/showExamNotDoList")
    @ResponseBody
    public DataGridView showExamNotDoList(HttpServletRequest httpServletRequest, ExamPaperInfoVo examPaperInfoVo) {
        Students students = (Students) httpServletRequest.getSession().getAttribute("user");
        QueryWrapper<ExamPaperinfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(examPaperInfoVo.getPaperName()),"paper_name",examPaperInfoVo.getPaperName());
        queryWrapper.like(StringUtils.isNotBlank(examPaperInfoVo.getChapter()),"chapter",examPaperInfoVo.getChapter());
        queryWrapper.eq(examPaperInfoVo.getCourseId()!=null,"course_id",examPaperInfoVo.getCourseId());
        queryWrapper.ge(StringUtils.isNotBlank(examPaperInfoVo.getStartTime()), "start_time", examPaperInfoVo.getStartTime());
        queryWrapper.le(StringUtils.isNotBlank(examPaperInfoVo.getEndTime()), "end_time", examPaperInfoVo.getEndTime());
        queryWrapper.eq("class_id",students.getClassId());
        queryWrapper.eq("type",2);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        queryWrapper.ge("end_time",simpleDateFormat.format(new Date().getTime()));
        List<ExamPaperinfo> examPaperinfoList = examPaperinfoService.list(queryWrapper);
        List<ExamPaperinfo> exampaperresult = new ArrayList<>();
        for(ExamPaperinfo examPaperinfo:examPaperinfoList){
            QueryWrapper<ExamScoreinfo> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("paper_type",2);
            queryWrapper1.eq("paper_id",examPaperinfo.getId());
            queryWrapper1.eq("student_id",students.getStudentId());
            ExamScoreinfo examScoreinfo = examScoreinfoService.getOne(queryWrapper1);
            if(examScoreinfo.getState()!=2)
                exampaperresult.add(examPaperinfo);
        }
        httpServletRequest.getSession().setAttribute("user",students);
        return new DataGridView((long)exampaperresult.size(),exampaperresult);
    }


    /**
     * 考试详情跳转
     */
    @RequestMapping("/showExamInfo")
    public String showExamInfo(HttpServletRequest httpServletRequest, @RequestParam("id")Integer id) {
        //httpServletRequest.getSession().setAttribute("paper_id",id);
        Students students = (Students) httpServletRequest.getSession().getAttribute("user");
        if(students==null)
            return "/toLogin";
        try {
            ExamPaperinfo examPaperinfo = examPaperinfoService.getById(id);
            UpdateWrapper<ExamScoreinfo> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("paper_id", id);
            updateWrapper.eq("student_id", students.getStudentId());
            updateWrapper.eq("paper_type", 2);
            ExamScoreinfo examScoreinfo = new ExamScoreinfo();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date();
            examScoreinfo.setStartTime(simpleDateFormat.format(date.getTime()));
            examScoreinfo.setState(1);
            examScoreinfoService.update(examScoreinfo,updateWrapper);



            httpServletRequest.getSession().setAttribute("serverTime", date.getTime());
            httpServletRequest.getSession().setAttribute("endTime", simpleDateFormat.parse(examPaperinfo.getEndTime()));
            httpServletRequest.getSession().setAttribute("paper", examPaperinfo);
            httpServletRequest.getSession().setAttribute("user", students);


        }catch (Exception e){
            e.printStackTrace();
        }
        return "system/students/exam/exam_info";
    }

    /**
     * 跳转到选择题页面
     */
    @RequestMapping("/doSingleQuestionExam")
    public String doSingleQuestionExam(HttpServletRequest httpServletRequest,@RequestParam("id")Integer paperId) {
        Students students = (Students) httpServletRequest.getSession().getAttribute("user");
        if(students==null)
            return "/toLogin";
        try {
            QueryWrapper<ExamSingleGroup> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("paper_id",paperId);
            queryWrapper.eq("paper_type",2);
            List<ExamSingleGroup> examSingleGroups = examSingleGroupService.list(queryWrapper);
            List<SingleSelectQuestion> selectQuestionList = new ArrayList<>();
            for(ExamSingleGroup examSingleGroup:examSingleGroups){
                QueryWrapper<SingleSelectQuestion> questionQueryWrapper = new QueryWrapper<>();
                questionQueryWrapper.eq("id",examSingleGroup.getSingleSelectId());
                selectQuestionList.add(singleSelectQuestionService.getOne(questionQueryWrapper));
            }

            ExamPaperinfo examPaperinfo = examPaperinfoService.getById(paperId);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date();

            httpServletRequest.getSession().setAttribute("serverTime", date.getTime());
            httpServletRequest.getSession().setAttribute("endTime", simpleDateFormat.parse(examPaperinfo.getEndTime()));
            httpServletRequest.getSession().setAttribute("singleQuestion",selectQuestionList);
            httpServletRequest.getSession().setAttribute("user",students);
            httpServletRequest.getSession().setAttribute("paper",examPaperinfo);


        }catch (Exception e){
            e.printStackTrace();
        }
        return "system/students/exam/exam_single_question_info";
    }


    /**
     * 提交选择题
     */
    @RequestMapping("/submitSingleQuestionExam")
    @ResponseBody
    public ResultObj submitSingleQuestionExam(HttpServletRequest httpServletRequest){
        ExamPaperinfo examPaperinfo = (ExamPaperinfo) httpServletRequest.getSession().getAttribute("paper");
        Students students = (Students)  httpServletRequest.getSession().getAttribute("user");
        if(examPaperinfo==null||students==null)
            return new ResultObj(-1,"提交失败");
        Gson gson = new Gson();
        Map<Integer, Integer> hashMap= new HashMap<>();
        hashMap = gson.fromJson(httpServletRequest.getParameter("data"), new TypeToken<Map<Integer, Integer>>() {
        }.getType());
        for (Integer id : hashMap.keySet()){
            //key是题目id,value是选择
            System.out.println("id:"+id);
            System.out.println("choose:"+hashMap.get(id));
            SingleSelectQuestion singleSelectQuestion = singleSelectQuestionService.getById(id);
            String choose="";
            switch (hashMap.get(id)){
                case 1:
                    choose = "A";
                    break;
                case 2:
                    choose = "B";
                    break;
                case 3:
                    choose = "C";
                    break;
                case 4:
                    choose = "D";
                    break;

            }

            Integer score;
            if(choose.equals(singleSelectQuestion.getAnswer())){
                score = examPaperinfo.getSingleScore();
            }else {
                score = 0;
            }

            ExamAnswerinfo examAnswerinfo = new ExamAnswerinfo();
            UpdateWrapper<ExamAnswerinfo> updateWrapper = new UpdateWrapper<>();
            examAnswerinfo.setStudentId(students.getStudentId());
            examAnswerinfo.setPaperId(examPaperinfo.getId());
            examAnswerinfo.setPaperType(2);
            examAnswerinfo.setQuestionId(id);
            //type是题目类型，1为选择题
            examAnswerinfo.setType(1);
            examAnswerinfo.setAnswer(choose);
            examAnswerinfo.setCorrectAnswer(singleSelectQuestion.getAnswer());
            examAnswerinfo.setScore(score);
            updateWrapper.eq("student_id",students.getStudentId());
            updateWrapper.eq("paper_type",2);
            updateWrapper.eq("paper_id",examPaperinfo.getId());
            updateWrapper.eq("type",1);
            updateWrapper.eq("question_id",id);
            examAnswerinfoService.saveOrUpdate(examAnswerinfo,updateWrapper);
        }

        return new ResultObj(200,"ok");
    }



    /**
     * 跳转到填空题页面
     */
    @RequestMapping("/doBlankQuestionExam")
    public String doBlankQuestionExam(HttpServletRequest httpServletRequest,@RequestParam("id")Integer paperId) {
        Students students = (Students) httpServletRequest.getSession().getAttribute("user");
        if(students==null)
            return "/toLogin";
        try {
            QueryWrapper<ExamBlankGroup> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("paper_id",paperId);
            queryWrapper.eq("paper_type",2);
            List<ExamBlankGroup> examSingleGroups = examBlankGroupService.list(queryWrapper);
            List<FillBlankQuestion> fillBlankQuestionList = new ArrayList<>();
            for(ExamBlankGroup examBlankGroup:examSingleGroups){
                QueryWrapper<FillBlankQuestion> questionQueryWrapper = new QueryWrapper<>();
                questionQueryWrapper.eq("id",examBlankGroup.getFillBlankId());
                fillBlankQuestionList.add(fillBlankQuestionService.getOne(questionQueryWrapper));
            }

            ExamPaperinfo examPaperinfo = examPaperinfoService.getById(paperId);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date();

            httpServletRequest.getSession().setAttribute("serverTime", date.getTime());
            httpServletRequest.getSession().setAttribute("endTime", simpleDateFormat.parse(examPaperinfo.getEndTime()));
            httpServletRequest.getSession().setAttribute("blankQuestion",fillBlankQuestionList);
            httpServletRequest.getSession().setAttribute("user",students);
            httpServletRequest.getSession().setAttribute("paper",examPaperinfo);


        }catch (Exception e){
            e.printStackTrace();
        }

        return "system/students/exam/exam_blank_question_info";
    }


    /**
     * 提交填空题
     */
    @RequestMapping("/submitBlankQuestionExam")
    @ResponseBody
    public ResultObj submitBlankQuestionExam(HttpServletRequest httpServletRequest){
        ExamPaperinfo examPaperinfo = (ExamPaperinfo) httpServletRequest.getSession().getAttribute("paper");
        Students students = (Students)  httpServletRequest.getSession().getAttribute("user");
        if(examPaperinfo==null||students==null)
            return new ResultObj(-1,"提交失败");
        Gson gson = new Gson();
        Map<Integer, String> hashMap= new HashMap<>();
        hashMap = gson.fromJson(httpServletRequest.getParameter("data"), new TypeToken<Map<Integer, String>>() {
        }.getType());
        for (Integer id : hashMap.keySet()){
            //key是题目id,value是回答
            System.out.println("id:"+id);
            System.out.println("choose:"+hashMap.get(id));
            FillBlankQuestion fillBlankQuestion = fillBlankQuestionService.getById(id);
            String choose=hashMap.get(id);

            Integer score;

            if(choose.equals(fillBlankQuestion.getAnswer())){
                score = examPaperinfo.getBlankScore();
            }else {
                score = 0;
            }

            ExamAnswerinfo examAnswerinfo = new ExamAnswerinfo();
            UpdateWrapper<ExamAnswerinfo> updateWrapper = new UpdateWrapper<>();
            examAnswerinfo.setStudentId(students.getStudentId());
            examAnswerinfo.setPaperId(examPaperinfo.getId());
            examAnswerinfo.setPaperType(2);
            examAnswerinfo.setQuestionId(id);
            //type是题目类型，1为选择题，2为填空，3为代码
            examAnswerinfo.setType(2);
            examAnswerinfo.setAnswer(choose);
            examAnswerinfo.setCorrectAnswer(fillBlankQuestion.getAnswer());
            examAnswerinfo.setScore(score);
            updateWrapper.eq("student_id",students.getStudentId());
            updateWrapper.eq("paper_type",2);
            updateWrapper.eq("paper_id",examPaperinfo.getId());
            updateWrapper.eq("type",2);
            updateWrapper.eq("question_id",id);
            examAnswerinfoService.saveOrUpdate(examAnswerinfo,updateWrapper);
        }

        return new ResultObj(200,"ok");
    }


    /**
     * 跳转到代码题页面
     */
    @RequestMapping("/doCodeQuestionExam")
    public String doCodeQuestionExam(HttpServletRequest httpServletRequest,@RequestParam("id")Integer paperId) {
        Students students = (Students) httpServletRequest.getSession().getAttribute("user");
        if(students==null)
            return "/toLogin";
        try {
            QueryWrapper<ExamCodeGroup> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("paper_id",paperId);
            queryWrapper.eq("paper_type",2);
            List<ExamCodeGroup> examCodeGroupList = examCodeGroupService.list(queryWrapper);
            List<ExerciseQuestion> exerciseQuestionList = new ArrayList<>();
            for(ExamCodeGroup examCodeGroup:examCodeGroupList){
                QueryWrapper<ExerciseQuestion> questionQueryWrapper = new QueryWrapper<>();
                questionQueryWrapper.eq("id",examCodeGroup.getCodeOrExerciseId());
                exerciseQuestionList.add(exerciseQuestionService.getOne(questionQueryWrapper));
            }

            ExamPaperinfo examPaperinfo = examPaperinfoService.getById(paperId);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date();

            QueryWrapper<Courses> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("course_id",examPaperinfo.getCourseId());
            Courses courses = coursesService.getOne(queryWrapper1);

            httpServletRequest.getSession().setAttribute("serverTime", date.getTime());
            httpServletRequest.getSession().setAttribute("endTime", simpleDateFormat.parse(examPaperinfo.getEndTime()));
            httpServletRequest.getSession().setAttribute("codeQuestion",exerciseQuestionList);
            httpServletRequest.getSession().setAttribute("user",students);
            httpServletRequest.getSession().setAttribute("paper",examPaperinfo);
            httpServletRequest.getSession().setAttribute("courseType",courses.getCourseType());


        }catch (Exception e){
            e.printStackTrace();
        }


        return "system/students/exam/exam_code_question_info";
    }



    /**
     * 代码调试
     */
    @RequestMapping("/ExerciseExamTest")
    @ResponseBody
    public ResultObj ExerciseExamTest(@RequestParam("content")String content,
                                          @RequestParam("courseType")Integer courseType,
                                          @RequestParam("exerciseId")Integer exerciseId,
                                          @RequestParam("studentId")Integer studentId,
                                          @RequestParam("paperId")Integer paperId) {
        ResultObj resultObj = new ResultObj();
        if(!StringUtils.isNotBlank(content))
            return new ResultObj(-1,"代码不能为空！");

        resultObj = exerciseJudgeCore.executeCode(content,courseType,exerciseId,studentId,2,paperId);

        return resultObj;
    }



    /**
     * 提交代码
     */
    @RequestMapping("/submitExerciseQuestionExam")
    @ResponseBody
    public ResultObj submitExerciseQuestionExam(HttpServletRequest httpServletRequest){
        ExamPaperinfo examPaperinfo = (ExamPaperinfo) httpServletRequest.getSession().getAttribute("paper");
        Students students = (Students)  httpServletRequest.getSession().getAttribute("user");
        if(examPaperinfo==null||students==null)
            return new ResultObj(-1,"提交失败");
        String data = httpServletRequest.getParameter("data");
        Gson gson = new Gson();
        Map<Integer, String> hashMap= new HashMap<>();
        hashMap = gson.fromJson(data, new TypeToken<Map<Integer, String>>() {
        }.getType());
        for (Integer id : hashMap.keySet()){
            //key是题目id,value是回答
            String answer=hashMap.get(id);

            Integer score;
            QueryWrapper<ExerciseSubmit> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("student_id",students.getStudentId());
            queryWrapper.eq("type",2);
            queryWrapper.eq("exercise_id",id);
            queryWrapper.eq("paper_id",examPaperinfo.getId());
            List<ExerciseSubmit> list = exerciseSubmitService.list(queryWrapper);
            if(list==null){
                ResultObj resultObj ;
                Integer courseType = (Integer)httpServletRequest.getSession().getAttribute("courseType");
                resultObj = exerciseJudgeCore.executeCode(answer,courseType,id,students.getStudentId(),2,examPaperinfo.getId());
                list = exerciseSubmitService.list(queryWrapper);
            }
            Double result = 0.0;
            String codeAnswer = "";
            for(ExerciseSubmit exerciseSubmit:list){
                BigDecimal temp = new BigDecimal(exerciseSubmit.getState());
                BigDecimal max = new BigDecimal(result);
                if(max.compareTo(temp)<0){
                    result = exerciseSubmit.getState();
                    codeAnswer = exerciseSubmit.getInput();
                }
            }
            result = examPaperinfo.getCodeScore()*result;
            score =  result.intValue();

            ExamAnswerinfo examAnswerinfo = new ExamAnswerinfo();
            UpdateWrapper<ExamAnswerinfo> updateWrapper = new UpdateWrapper<>();
            examAnswerinfo.setStudentId(students.getStudentId());
            examAnswerinfo.setPaperId(examPaperinfo.getId());
            examAnswerinfo.setPaperType(2);
            examAnswerinfo.setQuestionId(id);
            //type是题目类型，1为选择题，2为填空，3为代码
            examAnswerinfo.setType(3);
            if(codeAnswer.equals(answer))
                examAnswerinfo.setAnswer(answer);
            else
                examAnswerinfo.setAnswer(codeAnswer);
            examAnswerinfo.setCorrectAnswer("");
            examAnswerinfo.setScore(score);
            updateWrapper.eq("student_id",students.getStudentId());
            updateWrapper.eq("paper_type",2);
            updateWrapper.eq("paper_id",examPaperinfo.getId());
            updateWrapper.eq("type",3);
            updateWrapper.eq("question_id",id);
            examAnswerinfoService.saveOrUpdate(examAnswerinfo,updateWrapper);
        }

        return new ResultObj(200,"ok");
    }



    /**
     * 提交试卷
     */
    @RequestMapping("/submitExam")
    @ResponseBody
    public ResultObj submitExam(HttpServletRequest httpServletRequest){
        ExamPaperinfo examPaperinfo = (ExamPaperinfo) httpServletRequest.getSession().getAttribute("paper");
        Students students = (Students)  httpServletRequest.getSession().getAttribute("user");
        if(examPaperinfo==null||students==null)
            return new ResultObj(-1,"提交失败");

        QueryWrapper<ExamAnswerinfo> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("paper_type",2);
        queryWrapper1.eq("paper_id",examPaperinfo.getId());
        queryWrapper1.eq("student_id",students.getStudentId());
        List<ExamAnswerinfo> examAnswerinfos = examAnswerinfoService.list(queryWrapper1);

        Integer score = 0;
        for(ExamAnswerinfo examAnswerinfo:examAnswerinfos){
            score+=examAnswerinfo.getScore();
        }

        UpdateWrapper<ExamScoreinfo> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("paper_type",2);
        updateWrapper.eq("paper_id",examPaperinfo.getId());
        updateWrapper.eq("student_id",students.getStudentId());

        ExamScoreinfo examScoreinfo = new ExamScoreinfo();
        examScoreinfo.setScore(score);
        examScoreinfo.setState(2);

        examScoreinfoService.update(examScoreinfo,updateWrapper);

        return new ResultObj(200,"ok");
    }



    /**
     * 跳转到考试完成页面
     */
    @RequestMapping("/toExamDone")
    public String toExamDone() {
        return "system/students/exam/exam_done";
    }


    /**
     * 考试完成信息查询
     */
    @RequestMapping("/showExamDoneList")
    @ResponseBody
    public DataGridView showExamDoneList(HttpServletRequest httpServletRequest, ExamScoreInfoVo examScoreInfoVo,
                                             @RequestParam(value = "paperName",defaultValue = "")String paperName) {
        Students students = (Students) httpServletRequest.getSession().getAttribute("user");

        QueryWrapper<ExamScoreinfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("student_id",students.getStudentId());
        queryWrapper.eq("paper_type",2);
        queryWrapper.eq(examScoreInfoVo.getPaperId()!=null,"paper_id",examScoreInfoVo.getPaperId());
        List<ExamScoreinfo> examScoreinfoList = examScoreinfoService.list(queryWrapper);
        List<ExamScoreInfoVo> examscoreresult = new ArrayList<>();
        for(ExamScoreinfo examScoreinfo:examScoreinfoList){
            Date date = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            QueryWrapper<ExamPaperinfo> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("id",examScoreinfo.getPaperId());
            queryWrapper1.like(StringUtils.isNotBlank(paperName),"paper_name",paperName);
            ExamPaperinfo examPaper = examPaperinfoService.getOne(queryWrapper1);
            ExamScoreInfoVo examScoreinfovo = new ExamScoreInfoVo();
            examScoreinfovo.setId(examScoreinfo.getId());
            examScoreinfovo.setStudentId(examScoreinfo.getStudentId());
            examScoreinfovo.setPaperId(examScoreinfo.getPaperId());
            examScoreinfovo.setScore(examScoreinfo.getScore());
            examScoreinfovo.setState(examScoreinfo.getState());
            if(examPaper.getEndTime().compareTo(simpleDateFormat.format(date.getTime()))<=0){
                examScoreinfovo.setPaperName(examPaper.getPaperName());
                examscoreresult.add(examScoreinfovo);
            }else if(examPaper.getEndTime().compareTo(simpleDateFormat.format(date.getTime()))>0
                    &&examScoreinfo.getState()==2) {
                examScoreinfovo.setPaperName(examPaper.getPaperName());
                examscoreresult.add(examScoreinfovo);
            }
        }
        httpServletRequest.getSession().setAttribute("user",students);
        return new DataGridView((long)examscoreresult.size(),examscoreresult);
    }



    /**
     * 跳转到试题答案页面
     */
    @RequestMapping("/toExamAnswer")
    public String toExamAnswer(HttpServletRequest httpServletRequest, @RequestParam("id")Integer paperId, Model model) {
        Students students = (Students) httpServletRequest.getSession().getAttribute("user");
        List<ExamSingleGroup> examSingleGroups = new ArrayList<>();
        List<ExamBlankGroup> examBlankGroups = new ArrayList<>();
        List<ExamCodeGroup> examCodeGroups = new ArrayList<>();
        QueryWrapper<ExamSingleGroup> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("paper_id",paperId);
        queryWrapper1.eq("paper_type",2);
        examSingleGroups = examSingleGroupService.list(queryWrapper1);

        QueryWrapper<ExamBlankGroup> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("paper_id",paperId);
        queryWrapper2.eq("paper_type",2);
        examBlankGroups = examBlankGroupService.list(queryWrapper2);

        QueryWrapper<ExamCodeGroup> queryWrapper3 = new QueryWrapper<>();
        queryWrapper3.eq("paper_id",paperId);
        queryWrapper3.eq("paper_type",2);
        examCodeGroups = examCodeGroupService.list(queryWrapper3);

        //单选答案
        List<SingleAnswerVo> singleAnswerVos = new ArrayList<>();
        for(ExamSingleGroup examSingleGroup:examSingleGroups){
            SingleSelectQuestion singleSelectQuestion = singleSelectQuestionService.getById(examSingleGroup.getSingleSelectId());
            QueryWrapper<ExamAnswerinfo> queryWrapper4 = new QueryWrapper<>();
            queryWrapper4.eq("paper_id",paperId);
            queryWrapper4.eq("paper_type",2);
            queryWrapper4.eq("student_id",students.getStudentId());
            queryWrapper4.eq("type",1);
            queryWrapper4.eq("question_id",examSingleGroup.getSingleSelectId());
            ExamAnswerinfo examAnswerinfo = examAnswerinfoService.getOne(queryWrapper4);

            singleAnswerVos.add(new SingleAnswerVo(singleSelectQuestion,examAnswerinfo));
        }

        //填空答案
        List<BlankAnswerVo> blankAnswerVos = new ArrayList<>();
        for(ExamBlankGroup examBlankGroup:examBlankGroups){
            FillBlankQuestion fillBlankQuestion = fillBlankQuestionService.getById(examBlankGroup.getFillBlankId());
            QueryWrapper<ExamAnswerinfo> queryWrapper5 = new QueryWrapper<>();
            queryWrapper5.eq("paper_id",paperId);
            queryWrapper5.eq("paper_type",2);
            queryWrapper5.eq("student_id",students.getStudentId());
            queryWrapper5.eq("type",2);
            queryWrapper5.eq("question_id",examBlankGroup.getFillBlankId());
            ExamAnswerinfo examAnswerinfo = examAnswerinfoService.getOne(queryWrapper5);

            blankAnswerVos.add(new BlankAnswerVo(fillBlankQuestion,examAnswerinfo));
        }

        //代码答案
        List<ExerciseAnswerVo> exerciseAnswerVos = new ArrayList<>();
        for(ExamCodeGroup examCodeGroup:examCodeGroups){
            ExerciseQuestion exerciseQuestion = exerciseQuestionService.getById(examCodeGroup.getCodeOrExerciseId());

            QueryWrapper<ExamAnswerinfo> queryWrapper6 = new QueryWrapper<>();
            queryWrapper6.eq("paper_id",paperId);
            queryWrapper6.eq("paper_type",2);
            queryWrapper6.eq("student_id",students.getStudentId());
            queryWrapper6.eq("type",3);
            queryWrapper6.eq("question_id",examCodeGroup.getCodeOrExerciseId());
            ExamAnswerinfo examAnswerinfo = examAnswerinfoService.getOne(queryWrapper6);

            exerciseAnswerVos.add(new ExerciseAnswerVo(exerciseQuestion,examAnswerinfo));
        }


        model.addAttribute("singleAnswerVo",singleAnswerVos);
        model.addAttribute("blankAnswerVo",blankAnswerVos);
        model.addAttribute("exerciseAnswerVo",exerciseAnswerVos);


        return "system/students/exam/answer_detail";
    }



}
