package com.xloya.pldp.controller.TeacherController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xloya.pldp.common.DataGridView;
import com.xloya.pldp.common.ResultObj;
import com.xloya.pldp.entity.*;
import com.xloya.pldp.service.*;
import com.xloya.pldp.vo.*;;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;


@Controller
@RequestMapping("/teachers")
public class HomeworkController {


    @Autowired
    ExamPaperinfoService examPaperinfoService;

    @Autowired
    ExamScoreinfoService examScoreinfoService;

    @Autowired
    ExamAnswerinfoService examAnswerinfoService;

    @Autowired
    ExamBlankGroupService examBlankGroupService;

    @Autowired
    ExamSingleGroupService examSingleGroupService;

    @Autowired
    ExamCodeGroupService examCodeGroupService;

    @Autowired
    ClassesService classesService;

    @Autowired
    CoursesService coursesService;

    @Autowired
    StudentsService studentsService;

    @Autowired
    CourseContentService courseContentService;

    @Autowired
    SingleSelectQuestionService singleSelectQuestionService;

    @Autowired
    FillBlankQuestionService fillBlankQuestionService;

    @Autowired
    ExerciseQuestionService exerciseQuestionService;



    /**
     * 跳转到课程作业发布情况页面
     */
    @RequestMapping("/toHomeworkPushInfo")
    public String toHomeworkPushInfo() {
        return "system/teachers/homework/homework_push";
    }



    /**
     * 课程作业发布信息查询
     */
    @RequestMapping("/showHomeworkList")
    @ResponseBody
    public DataGridView showHomeworkList(HttpServletRequest httpServletRequest,ExamPaperInfoVo examPaperInfoVo) {
        Teachers teachers = (Teachers) httpServletRequest.getSession().getAttribute("user");
        IPage<ExamPaperinfo> page = new Page<>(examPaperInfoVo.getPage(), examPaperInfoVo.getLimit());
        QueryWrapper<ExamPaperinfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(examPaperInfoVo.getPaperName()),"paper_name",examPaperInfoVo.getPaperName());
        queryWrapper.eq(examPaperInfoVo.getCourseId()!=null,"course_id",examPaperInfoVo.getCourseId());
        queryWrapper.like(StringUtils.isNotBlank(examPaperInfoVo.getChapter()),"chapter",examPaperInfoVo.getChapter());
        queryWrapper.eq(examPaperInfoVo.getClassId()!=null,"class_id",examPaperInfoVo.getClassId());
        //类型1为作业
        queryWrapper.eq("type",1);
        queryWrapper.eq("teacher_id",teachers.getTeacherId());

        queryWrapper.ge(StringUtils.isNotBlank(examPaperInfoVo.getStartTime()), "start_time", examPaperInfoVo.getStartTime());
        queryWrapper.le(StringUtils.isNotBlank(examPaperInfoVo.getEndTime()), "end_time", examPaperInfoVo.getEndTime());
        this.examPaperinfoService.page(page, queryWrapper);
        httpServletRequest.getSession().setAttribute("user",teachers);
        return new DataGridView(page.getTotal(), page.getRecords());
    }


    /**
     * 添加课程作业
     */
    @RequestMapping("/addHomework")
    @Transactional
    @ResponseBody
    public ResultObj addHomework(HttpServletRequest httpServletRequest, ExamPaperInfoVo examPaperInfoVo){
        try {
            //存作业信息
            Teachers teachers = (Teachers) httpServletRequest.getSession().getAttribute("user");
            if(teachers==null)
                return ResultObj.ADD_ERROR;
            QueryWrapper<Classes> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("teacher_id",teachers.getTeacherId());
            queryWrapper.eq("class_id",examPaperInfoVo.getClassId());

            QueryWrapper<Courses> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("course_id",examPaperInfoVo.getCourseId());

            QueryWrapper<CourseContent> queryWrapper2 = new QueryWrapper<>();
            queryWrapper2.eq("course_id",examPaperInfoVo.getCourseId());
            queryWrapper2.eq("chapter",examPaperInfoVo.getChapter());

            String starttime = examPaperInfoVo.getStartTime();
            String endtime = examPaperInfoVo.getEndTime();

            if(null==classesService.getOne(queryWrapper))
                return new ResultObj(-1,"添加失败，不是教师本人班级");

            if(coursesService.getOne(queryWrapper1)==null)
                return new ResultObj(-1,"添加失败，课程不存在");

            if(starttime.compareTo(endtime)>0)
                return new ResultObj(-1,"开始日期大于结束日期");

            if(courseContentService.list(queryWrapper2).size()==0)
                return new ResultObj(-1,"添加失败，课程章不存在");

            //类型1是作业
            examPaperInfoVo.setType(1);
            examPaperInfoVo.setTeacherId(teachers.getTeacherId());
            Date date = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            examPaperInfoVo.setCreateTime(simpleDateFormat.format(date.getTime()));



            //生成作业
            int singleCount = examPaperInfoVo.getSingleCount();
            int singleScore = examPaperInfoVo.getSingleScore();
            int blankCount = examPaperInfoVo.getBlankCount();
            int blankScore = examPaperInfoVo.getBlankScore();
            int codeCount = examPaperInfoVo.getCodeCount();
            int codeScore = examPaperInfoVo.getCodeScore();

            QueryWrapper<SingleSelectQuestion> queryWrapper3 = new QueryWrapper<>();
            queryWrapper3.eq("course_id",examPaperInfoVo.getCourseId());
            queryWrapper3.eq("chapter",examPaperInfoVo.getChapter());
            List<SingleSelectQuestion> selectQuestionList = singleSelectQuestionService.list(queryWrapper3);
            if(selectQuestionList.size()<singleCount)
                return new ResultObj(-1,"添加失败，所设单选题数大于题库题目数");

            QueryWrapper<FillBlankQuestion> queryWrapper4 = new QueryWrapper<>();
            queryWrapper4.eq("course_id",examPaperInfoVo.getCourseId());
            queryWrapper4.eq("chapter",examPaperInfoVo.getChapter());
            List<FillBlankQuestion> fillBlankQuestionList = fillBlankQuestionService.list(queryWrapper4);
            if(fillBlankQuestionList.size()<blankCount)
                return new ResultObj(-1,"添加失败，所设填空数大于题库题目数");

            QueryWrapper<ExerciseQuestion> queryWrapper5 = new QueryWrapper<>();
            queryWrapper5.eq("course_id",examPaperInfoVo.getCourseId());
            queryWrapper5.eq("chapter",examPaperInfoVo.getChapter());
            List<ExerciseQuestion> exerciseQuestionList = exerciseQuestionService.list(queryWrapper5);
            if(exerciseQuestionList.size()<codeCount)
                return new ResultObj(-1,"添加失败，所设代码数大于题库题目数");


            //随机组题
            Random random = new Random();

            //singlebool 是章节范围内题目总列表
            boolean[] singlebool = new boolean[selectQuestionList.size()];
            int singlenow = 0;
            for(int i = 0;i<singleCount;i++){
                /*得到singleCount个不同的随机数*/
                do{
                    singlenow = random.nextInt(selectQuestionList.size());
                }while(singlebool[singlenow]);
                singlebool[singlenow] = true;
            }

            List<SingleSelectQuestion> selectQuestionResults = new ArrayList<>();
            for(int i=0;i<singlebool.length;i++){
                if(singlebool[i]){
                    selectQuestionResults.add(selectQuestionList.get(i));
                }
            }

            boolean[] blankbool = new boolean[fillBlankQuestionList.size()];
            int blanknow = 0;
            for(int i = 0;i<blankCount;i++){
                do{
                    blanknow = random.nextInt(fillBlankQuestionList.size());
                }while(blankbool[blanknow]);
                blankbool[blanknow] = true;
            }

            List<FillBlankQuestion> fillBlankQuestionResults = new ArrayList<>();
            for(int i=0;i<blankbool.length;i++){
                if(blankbool[i]){
                    fillBlankQuestionResults.add(fillBlankQuestionList.get(i));
                }
            }


            boolean[] codebool = new boolean[exerciseQuestionList.size()];
            int codenow = 0;
            for(int i = 0;i<codeCount;i++){
                do{
                    codenow = random.nextInt(exerciseQuestionList.size());
                }while(codebool[codenow]);
                codebool[codenow] = true;
            }

            List<ExerciseQuestion> exerciseQuestionResults = new ArrayList<>();
            for(int i=0;i<codebool.length;i++){
                if(codebool[i]){
                    exerciseQuestionResults.add(exerciseQuestionList.get(i));
                }
            }

            examPaperinfoService.save(examPaperInfoVo);


            //将组题结果存入数据库
            Integer paperid = examPaperInfoVo.getId();

            for(SingleSelectQuestion singleSelectQuestion:selectQuestionResults){
                ExamSingleGroup examSingleGroup = new ExamSingleGroup();
                examSingleGroup.setSingleSelectId(singleSelectQuestion.getId());
                examSingleGroup.setPaperId(paperid);
                examSingleGroup.setScore(singleScore);
                examSingleGroup.setPaperType(1);
                examSingleGroupService.save(examSingleGroup);
            }


            for(FillBlankQuestion fillBlankQuestion:fillBlankQuestionResults){
                ExamBlankGroup examBlankGroup = new ExamBlankGroup();
                examBlankGroup.setFillBlankId(fillBlankQuestion.getId());
                examBlankGroup.setPaperId(paperid);
                examBlankGroup.setScore(blankScore);
                examBlankGroup.setPaperType(1);
                examBlankGroupService.save(examBlankGroup);
            }


            for(ExerciseQuestion exerciseQuestion:exerciseQuestionResults){
                ExamCodeGroup examCodeGroup = new ExamCodeGroup();
                examCodeGroup.setCodeOrExerciseId(exerciseQuestion.getId());
                examCodeGroup.setPaperId(paperid);
                examCodeGroup.setScore(codeScore);
                examCodeGroup.setPaperType(1);
                examCodeGroupService.save(examCodeGroup);
            }


            //分发作业

            QueryWrapper<Students> squeryWrapper = new QueryWrapper<>();
            squeryWrapper.eq("class_id",examPaperInfoVo.getClassId());

            List<Students> studentsList = studentsService.list(squeryWrapper);

            for(Students students:studentsList){
                ExamScoreinfo examScoreinfo = new ExamScoreinfo();
                examScoreinfo.setStudentId(students.getStudentId());
                examScoreinfo.setPaperId(paperid);
                examScoreinfo.setPaperType(1);
                examScoreinfo.setState(0);
                examScoreinfo.setScore(0);
                examScoreinfoService.save(examScoreinfo);
            }



            httpServletRequest.getSession().setAttribute("user",teachers);

            return ResultObj.ADD_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.ADD_ERROR;
        }
    }


    /**
     * 修改课程作业
     */

    @RequestMapping("/updateHomework")
    @ResponseBody
    public ResultObj updateHomework(HttpServletRequest httpServletRequest,ExamPaperInfoVo examPaperInfoVo) {
        try {
            Teachers teachers = (Teachers) httpServletRequest.getSession().getAttribute("user");
            if(teachers==null)
                return ResultObj.ADD_ERROR;
            QueryWrapper<Courses> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("course_id",examPaperInfoVo.getCourseId());

            QueryWrapper<Classes> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("class_id",examPaperInfoVo.getClassId());
            queryWrapper1.eq("teacher_id",teachers.getTeacherId());

            QueryWrapper<CourseContent> queryWrapper2 = new QueryWrapper<>();
            queryWrapper2.eq("course_id",examPaperInfoVo.getCourseId());
            queryWrapper2.eq("chapter",examPaperInfoVo.getChapter());

            ExamPaperinfo examPaperinfo = examPaperinfoService.getById(examPaperInfoVo.getId());
            Date date = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date oldtime = simpleDateFormat.parse(examPaperinfo.getStartTime());

            if(coursesService.getOne(queryWrapper)==null||classesService.getOne(queryWrapper1)==null||
            courseContentService.list(queryWrapper2).size()==0||oldtime.getTime()<date.getTime())
                return ResultObj.UPDATE_ERROR;

            String starttime = examPaperInfoVo.getStartTime();
            String endtime = examPaperInfoVo.getEndTime();
            if(starttime.compareTo(endtime)>0)
                return ResultObj.UPDATE_ERROR;
            this.examPaperinfoService.updateById(examPaperInfoVo);
            httpServletRequest.getSession().setAttribute("user",teachers);
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.UPDATE_ERROR;
        }
    }


    /**
     * 删除课程作业
     */

    @RequestMapping("/deleteHomework")
    @ResponseBody
    public ResultObj deleteHomework(Integer id){
        try {
            QueryWrapper<ExamAnswerinfo> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("paper_id",id);
            queryWrapper.eq("paper_type",1);
            examAnswerinfoService.remove(queryWrapper);

            QueryWrapper<ExamScoreinfo> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("paper_id",id);
            queryWrapper1.eq("paper_type",1);
            examScoreinfoService.remove(queryWrapper1);

            QueryWrapper<ExamSingleGroup> queryWrapper2 = new QueryWrapper<>();
            queryWrapper2.eq("paper_id",id);
            queryWrapper2.eq("paper_type",1);
            examSingleGroupService.remove(queryWrapper2);

            QueryWrapper<ExamBlankGroup> queryWrapper3 = new QueryWrapper<>();
            queryWrapper3.eq("paper_id",id);
            queryWrapper3.eq("paper_type",1);
            examBlankGroupService.remove(queryWrapper3);

            QueryWrapper<ExamCodeGroup> queryWrapper4 = new QueryWrapper<>();
            queryWrapper4.eq("paper_id",id);
            queryWrapper4.eq("paper_type",1);
            examCodeGroupService.remove(queryWrapper4);

            this.examPaperinfoService.removeById(id);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }

    /**
     * 批量删除课程作业
     */

    @RequestMapping("/batchDeleteHomework")
    @ResponseBody
    public ResultObj batchDeleteHomework(ExamPaperInfoVo examPaperInfoVo) {
        try {
            Collection<Serializable> idList = new ArrayList<Serializable>();
            for (Integer id : examPaperInfoVo.getIds()) {
                idList.add(id);

                QueryWrapper<ExamAnswerinfo> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("paper_id",id);
                queryWrapper.eq("paper_type",1);
                examAnswerinfoService.remove(queryWrapper);

                QueryWrapper<ExamScoreinfo> queryWrapper1 = new QueryWrapper<>();
                queryWrapper1.eq("paper_id",id);
                queryWrapper1.eq("paper_type",1);
                examScoreinfoService.remove(queryWrapper1);

                QueryWrapper<ExamSingleGroup> queryWrapper2 = new QueryWrapper<>();
                queryWrapper2.eq("paper_id",id);
                queryWrapper2.eq("paper_type",1);
                examSingleGroupService.remove(queryWrapper2);

                QueryWrapper<ExamBlankGroup> queryWrapper3 = new QueryWrapper<>();
                queryWrapper3.eq("paper_id",id);
                queryWrapper3.eq("paper_type",1);
                examBlankGroupService.remove(queryWrapper3);

                QueryWrapper<ExamCodeGroup> queryWrapper4 = new QueryWrapper<>();
                queryWrapper4.eq("paper_id",id);
                queryWrapper4.eq("paper_type",1);
                examCodeGroupService.remove(queryWrapper4);
            }
            this.examPaperinfoService.removeByIds(idList);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }


    /**
     * 跳转到课程作业学生完成情况页面
     */
    @RequestMapping("/toHomeworkCompleteInfo")
    public String toHomeworkCompleteInfo() {
        return "system/teachers/homework/homework_complete";
    }


    /**
     * 学生作业完成情况查询
     */
    @RequestMapping("/showHomeworkScoreList")
    @ResponseBody
    public DataGridView showHomeworkScoreList(HttpServletRequest httpServletRequest,ExamScoreInfoVo examScoreInfoVo) {
        Teachers teachers = (Teachers) httpServletRequest.getSession().getAttribute("user");
        QueryWrapper<ExamPaperinfo> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("type",1);
        queryWrapper1.eq("teacher_id",teachers.getTeacherId());


        QueryWrapper<ExamScoreinfo> queryWrapper = new QueryWrapper<>();
        if(examScoreInfoVo.getPaperId()!=null||examScoreInfoVo.getStudentId()!=null) {
            queryWrapper1.eq(examScoreInfoVo.getPaperId() != null,"id",examScoreInfoVo.getPaperId());
            List<ExamPaperinfo> list = examPaperinfoService.list(queryWrapper1);
            if(list.size()==0)
                return  new DataGridView(Long.valueOf(0), null);
            queryWrapper.eq("paper_type",1);
            queryWrapper.eq(examScoreInfoVo.getPaperId() != null, "paper_id", examScoreInfoVo.getPaperId());
            queryWrapper.eq(examScoreInfoVo.getStudentId() != null, "student_id", examScoreInfoVo.getStudentId());
        }else{
            List<ExamPaperinfo> list = examPaperinfoService.list(queryWrapper1);
            if(list.size()!=0) {
                for (ExamPaperinfo examPaperinfo : list) {
                    queryWrapper.or().eq("paper_id", examPaperinfo.getId());
                }
            }else
                return  new DataGridView(Long.valueOf(0), null);
        }
        IPage<ExamScoreinfo> page = new Page<>(examScoreInfoVo.getPage(), examScoreInfoVo.getLimit());
        this.examScoreinfoService.page(page, queryWrapper);
        httpServletRequest.getSession().setAttribute("user",teachers);
        return new DataGridView(page.getTotal(), page.getRecords());
    }


    /**
     * 添加考生作业情况
     */
    @RequestMapping("/addHomeworkScore")
    @ResponseBody
    public ResultObj addHomeworkScore(HttpServletRequest httpServletRequest,ExamScoreInfoVo examScoreInfoVo) {
        try {
            Teachers teachers = (Teachers) httpServletRequest.getSession().getAttribute("user");
            QueryWrapper<Students> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("student_id",examScoreInfoVo.getStudentId());

            QueryWrapper<ExamPaperinfo> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("id",examScoreInfoVo.getPaperId());
            queryWrapper1.eq("teacher_id", teachers.getTeacherId());
            ExamPaperinfo examPaperinfo = examPaperinfoService.getOne(queryWrapper1);
            if(studentsService.getOne(queryWrapper)==null|| examPaperinfo==null)
                return ResultObj.ADD_ERROR;
            /*
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            examScoreInfoVo.setLeftTime((simpleDateFormat.parse(examPaperinfo.getEndTime()).getTime()
                    -simpleDateFormat.parse(examPaperinfo.getStartTime()).getTime())/1000);
                    */

            httpServletRequest.getSession().setAttribute("user",teachers);
            this.examScoreinfoService.save(examScoreInfoVo);
            return ResultObj.ADD_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.ADD_ERROR;
        }
    }

    /**
     * 修改学生作业完成情况
     */
    @RequestMapping("/updateHomeworkScore")
    @ResponseBody
    public ResultObj updateHomeworkScore(ExamScoreInfoVo examScoreInfoVo) {
        try {

            if(examScoreInfoVo.getState()==1)
                return ResultObj.UPDATE_ERROR;
            this.examScoreinfoService.updateById(examScoreInfoVo);
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.UPDATE_ERROR;
        }
    }

    /**
     * 删除学生作业情况信息
     */
    @RequestMapping("/deleteHomeworkScore")
    @ResponseBody
    public ResultObj deleteHomeworkScore(Integer id){
        try {
            this.examScoreinfoService.removeById(id);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }


    /**
     * 跳转到课程作业答案页面
     */
    @RequestMapping("/toHomeworkAnswer")
    public String toHomeworkAnswer(HttpServletRequest httpServletRequest, @RequestParam("id")Integer paperId,
                                   @RequestParam("studentId")Integer studentId,Model model) {
        List<ExamSingleGroup> examSingleGroups = new ArrayList<>();
        List<ExamBlankGroup> examBlankGroups = new ArrayList<>();
        List<ExamCodeGroup> examCodeGroups = new ArrayList<>();
        QueryWrapper<ExamSingleGroup> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("paper_id",paperId);
        queryWrapper1.eq("paper_type",1);
        examSingleGroups = examSingleGroupService.list(queryWrapper1);

        QueryWrapper<ExamBlankGroup> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("paper_id",paperId);
        queryWrapper2.eq("paper_type",1);
        examBlankGroups = examBlankGroupService.list(queryWrapper2);

        QueryWrapper<ExamCodeGroup> queryWrapper3 = new QueryWrapper<>();
        queryWrapper3.eq("paper_id",paperId);
        queryWrapper3.eq("paper_type",1);
        examCodeGroups = examCodeGroupService.list(queryWrapper3);

        //单选答案
        List<SingleAnswerVo> singleAnswerVos = new ArrayList<>();
        for(ExamSingleGroup examSingleGroup:examSingleGroups){
            SingleSelectQuestion singleSelectQuestion = singleSelectQuestionService.getById(examSingleGroup.getSingleSelectId());
            QueryWrapper<ExamAnswerinfo> queryWrapper4 = new QueryWrapper<>();
            queryWrapper4.eq("paper_id",paperId);
            queryWrapper4.eq("paper_type",1);
            queryWrapper4.eq("student_id",studentId);
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
            queryWrapper5.eq("paper_type",1);
            queryWrapper5.eq("student_id",studentId);
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
            queryWrapper6.eq("paper_type",1);
            queryWrapper6.eq("student_id",studentId);
            queryWrapper6.eq("type",3);
            queryWrapper6.eq("question_id",examCodeGroup.getCodeOrExerciseId());
            ExamAnswerinfo examAnswerinfo = examAnswerinfoService.getOne(queryWrapper6);

            exerciseAnswerVos.add(new ExerciseAnswerVo(exerciseQuestion,examAnswerinfo));
        }

        model.addAttribute("singleAnswerVo",singleAnswerVos);
        model.addAttribute("blankAnswerVo",blankAnswerVos);
        model.addAttribute("exerciseAnswerVo",exerciseAnswerVos);


        return "system/teachers/homework/answer_detail";
    }



    /**
     * 跳转到课程作业整体情况页面
     */
    @RequestMapping("/toAllHomework")
    public String toAllHomework(HttpServletRequest httpServletRequest, @RequestParam("id")Integer paperId, Model model) {
        ExamPaperinfo examPaperinfo = examPaperinfoService.getById(paperId);
        //卷面总分
        Integer paperTotalScore = examPaperinfo.getSingleCount()*examPaperinfo.getSingleScore()+
                examPaperinfo.getBlankCount()*examPaperinfo.getBlankScore()+
                examPaperinfo.getCodeCount()*examPaperinfo.getCodeScore();

        QueryWrapper<Students> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("class_id",examPaperinfo.getClassId());
        List<Students> studentsList = studentsService.list(queryWrapper);

        //总人数，未完成人数，正在完成人数，已完成人数，全班总分
        Integer totalPeople = studentsList.size();
        Integer NotDoNum = 0;
        Integer DoingNum = 0;
        Integer DoneNum = 0;
        Integer totalScore = 0;
        for(Students students:studentsList){
            QueryWrapper<ExamScoreinfo> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("paper_id",paperId);
            queryWrapper1.eq("paper_type",1);
            queryWrapper1.eq("student_id",students.getStudentId());
            ExamScoreinfo examScoreinfo = examScoreinfoService.getOne(queryWrapper1);
            if(examScoreinfo.getState()==0)
                NotDoNum++;
            else if(examScoreinfo.getState()==1)
                DoingNum++;
            else
                DoneNum++;
            totalScore+=examScoreinfo.getScore();
        }

        //全班平均分
        Double avgScore = (double) totalScore/totalPeople;


        List<ExamSingleGroup> examSingleGroups = new ArrayList<>();
        List<ExamBlankGroup> examBlankGroups = new ArrayList<>();
        List<ExamCodeGroup> examCodeGroups = new ArrayList<>();
        QueryWrapper<ExamSingleGroup> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("paper_id",paperId);
        queryWrapper1.eq("paper_type",1);
        examSingleGroups = examSingleGroupService.list(queryWrapper1);

        QueryWrapper<ExamBlankGroup> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("paper_id",paperId);
        queryWrapper2.eq("paper_type",1);
        examBlankGroups = examBlankGroupService.list(queryWrapper2);

        QueryWrapper<ExamCodeGroup> queryWrapper3 = new QueryWrapper<>();
        queryWrapper3.eq("paper_id",paperId);
        queryWrapper3.eq("paper_type",1);
        examCodeGroups = examCodeGroupService.list(queryWrapper3);

        //单选答案
        List<SingleAnswerVo> singleAnswerVos = new ArrayList<>();
        for(ExamSingleGroup examSingleGroup:examSingleGroups){
            SingleSelectQuestion singleSelectQuestion = singleSelectQuestionService.getById(examSingleGroup.getSingleSelectId());
            QueryWrapper<ExamAnswerinfo> queryWrapper4 = new QueryWrapper<>();
            queryWrapper4.eq("paper_id",paperId);
            queryWrapper4.eq("paper_type",1);
            queryWrapper4.eq("type",1);
            queryWrapper4.eq("question_id",examSingleGroup.getSingleSelectId());
            List<ExamAnswerinfo> examAnswerinfos = examAnswerinfoService.list(queryWrapper4);

            //选择A的人数，B的人数，C的人数，D的人数，未选择的人数，全班该题总分
            Integer optionANum = 0;
            Integer optionBNum = 0;
            Integer optionCNum = 0;
            Integer optionDNum = 0;
            Integer optionNullNum = 0;
            Integer singleTotalScore = 0;

            for(ExamAnswerinfo examAnswerinfo:examAnswerinfos){
                singleTotalScore+=examAnswerinfo.getScore();
                if(examAnswerinfo.getAnswer().equals("A"))
                    optionANum++;
                else if(examAnswerinfo.getAnswer().equals("B"))
                    optionBNum++;
                else if(examAnswerinfo.getAnswer().equals("C"))
                    optionCNum++;
                else if(examAnswerinfo.getAnswer().equals("D"))
                    optionDNum++;
            }

            optionNullNum = totalPeople-optionANum-optionBNum-optionCNum-optionDNum;
            //全班该题平均分
            double avgSingleScore = (double)singleTotalScore/totalPeople;


            SingleAnswerVo singleAnswerVo = new SingleAnswerVo(singleSelectQuestion);
            singleAnswerVo.setAvgScore(String.format("%.2f",avgSingleScore));
            singleAnswerVo.setOptionAPercent(String.format("%.2f",(double)optionANum/totalPeople));
            singleAnswerVo.setOptionBPercent(String.format("%.2f",(double)optionBNum/totalPeople));
            singleAnswerVo.setOptionCPercent(String.format("%.2f",(double)optionCNum/totalPeople));
            singleAnswerVo.setOptionDPercent(String.format("%.2f",(double)optionDNum/totalPeople));
            singleAnswerVo.setOptionNullPercent(String.format("%.2f",(double)optionNullNum/totalPeople));

            singleAnswerVos.add(singleAnswerVo);
        }

        //填空答案
        List<BlankAnswerVo> blankAnswerVos = new ArrayList<>();
        for(ExamBlankGroup examBlankGroup:examBlankGroups){
            FillBlankQuestion fillBlankQuestion = fillBlankQuestionService.getById(examBlankGroup.getFillBlankId());
            QueryWrapper<ExamAnswerinfo> queryWrapper5 = new QueryWrapper<>();
            queryWrapper5.eq("paper_id",paperId);
            queryWrapper5.eq("paper_type",1);
            queryWrapper5.eq("type",2);
            queryWrapper5.eq("question_id",examBlankGroup.getFillBlankId());

            List<ExamAnswerinfo> examAnswerinfos = examAnswerinfoService.list(queryWrapper5);
            Integer blankTotalScore = 0;
            for(ExamAnswerinfo examAnswerinfo:examAnswerinfos){
                blankTotalScore+=examAnswerinfo.getScore();

            }
            //全班该题平均分
            double avgBlankScore = (double)blankTotalScore/totalPeople;
            BlankAnswerVo blankAnswerVo = new BlankAnswerVo(fillBlankQuestion);
            blankAnswerVo.setAvgScore(String.format("%.2f",avgBlankScore));
            blankAnswerVos.add(blankAnswerVo);
        }


        //代码答案
        List<ExerciseAnswerVo> exerciseAnswerVos = new ArrayList<>();
        for(ExamCodeGroup examCodeGroup:examCodeGroups){
            ExerciseQuestion exerciseQuestion = exerciseQuestionService.getById(examCodeGroup.getCodeOrExerciseId());

            QueryWrapper<ExamAnswerinfo> queryWrapper6 = new QueryWrapper<>();
            queryWrapper6.eq("paper_id",paperId);
            queryWrapper6.eq("paper_type",1);
            queryWrapper6.eq("type",3);
            queryWrapper6.eq("question_id",examCodeGroup.getCodeOrExerciseId());
            List<ExamAnswerinfo> examAnswerinfos = examAnswerinfoService.list(queryWrapper6);

            Integer codeTotalScore = 0;
            for(ExamAnswerinfo examAnswerinfo:examAnswerinfos){
                codeTotalScore+=examAnswerinfo.getScore();
            }
            //全班代码题平均分
            double avgCodeScore = (double)codeTotalScore/totalPeople;
            ExerciseAnswerVo examAnswerinfo = new ExerciseAnswerVo(exerciseQuestion);
            examAnswerinfo.setAvgScore(String.format("%.2f",avgCodeScore));
            exerciseAnswerVos.add(examAnswerinfo);
        }

        model.addAttribute("paperTotalScore",paperTotalScore);
        model.addAttribute("totalPeople",totalPeople);
        model.addAttribute("NotDoNum",NotDoNum);
        model.addAttribute("DoingNum",DoingNum);
        model.addAttribute("DoneNum",DoneNum);
        model.addAttribute("avgScore",String.format("%.2f",avgScore));
        model.addAttribute("singleAnswerVo",singleAnswerVos);
        model.addAttribute("blankAnswerVo",blankAnswerVos);
        model.addAttribute("exerciseAnswerVo",exerciseAnswerVos);


        return "system/teachers/homework/homework_detail";
    }
}
