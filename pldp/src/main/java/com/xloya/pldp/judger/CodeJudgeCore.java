package com.xloya.pldp.judger;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.xloya.pldp.common.ResultObj;
import com.xloya.pldp.entity.*;
import com.xloya.pldp.service.CodeJudgementService;
import com.xloya.pldp.service.CodeQuestionService;
import com.xloya.pldp.service.CodeSubmitService;
import com.xloya.pldp.service.StudentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class CodeJudgeCore {
    @Autowired
    CodeJudgementService codeJudgementService;

    @Autowired
    CodeQuestionService codeQuestionService;

    @Autowired
    CodeSubmitService codeSubmitService;

    @Autowired
    StudentsService studentsService;

    public static final int init=0,ac=1,wrongAnswer=2,compileError=8;
    private int result;
    private String errorMsg;
    private double accuracy;

    public ResultObj executeCode(String code, Integer language, Integer codeId, Integer studentId){

        CodeQuestion codeQuestion = codeQuestionService.getById(codeId);
        codeQuestion.setSubmitNum(codeQuestion.getSubmitNum()+1);

        UpdateWrapper<CodeQuestion> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",codeId);

        CodeSubmit codeSubmit = new CodeSubmit();
        codeSubmit.setInput(code);
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        codeSubmit.setTime(simpleDateFormat.format(date.getTime()));
        codeSubmit.setCodeId(codeId);
        codeSubmit.setStudentId(studentId);


        ResultObj resultObj=null;
        String path="H:/"+studentId+"/";
        new File(path).mkdir();
        String compile=null;
        String execute=null;
        errorMsg = null;
        result=init;  //0是初始状态，1为ac，2为答案有错误，8为编译错误
        int state = init;

        File file = null;

        switch(language){
            case 1: //c语言
                file = new File(path+"Main.c");
                compile="gcc "+path+"Main.c -o "+path+"Main";
                execute=path+"Main.exe";
                break;
            case 2: //java
                file = new File(path+"Main.java");
                compile="javac "+path+"Main.java";
                execute="java -classpath "+path+" Main";
                break;
            case 3: //python
                file = new File(path+"Main.py");
                compile=null;
                execute="python "+path+"Main.py";
                break;
        }

        OutputStream outputStream = null;
        try{
            outputStream = new FileOutputStream(file);
            byte[] bytes= code.getBytes();  //读取输出流中的字节
            outputStream.write(bytes);     //写入文件
        }catch(IOException e) {
            e.printStackTrace();
            errorMsg = "写文件失败！";
            codeSubmit.setState(0.0);
            codeSubmitService.save(codeSubmit);
            codeQuestionService.update(codeQuestion,updateWrapper);
            return new ResultObj(-1,errorMsg);
        }
        if(outputStream!=null) {
            try {
                outputStream.close();  //关闭输出文件流
            }catch(IOException el) {
                errorMsg = "关闭文件输出流失败！";
                codeSubmit.setState(0.0);
                codeQuestionService.update(codeQuestion,updateWrapper);
                return new ResultObj(-1,errorMsg);
            }
        }
        try {
            //开始编译
            Process process = null;
            if(compile!=null){
                process = Runtime.getRuntime().exec(compile);
                try {
                    boolean res = process.waitFor(20000, TimeUnit.MILLISECONDS);
                    if (!res) {
                        process.destroy();
                        errorMsg = "编译超时";
                        result = state = compileError;
                        codeQuestionService.update(codeQuestion,updateWrapper);
                        codeSubmit.setState(0.0);
                        codeSubmitService.save(codeSubmit);
                        return new ResultObj(-1,errorMsg);
                    } else {
                        errorMsg = getErrors(process.getErrorStream());
                        if (errorMsg.indexOf("错误") != -1 || errorMsg.indexOf("Error") != -1 || errorMsg.indexOf("error") != -1 || errorMsg.indexOf("undefined") != -1) {//过滤warning
                            result = state = compileError;
                            codeQuestionService.update(codeQuestion,updateWrapper);
                            codeSubmit.setState(0.0);
                            codeSubmitService.save(codeSubmit);
                            return new ResultObj(-1,errorMsg);
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        //开始执行
        if(state!=compileError) {
            QueryWrapper<CodeJudgement> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("code_id", codeId);
            List<CodeJudgement> codeJudgementList = codeJudgementService.list(queryWrapper);

            int correct = 0;
            for (CodeJudgement codeJudgement : codeJudgementList) {
                String input = codeJudgement.getTestInput();
                process = Runtime.getRuntime().exec(execute);

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                PrintWriter writer = new PrintWriter(process.getOutputStream());
                writer.println(input);
                writer.flush();
                writer.close();
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String output = new String();
                String out = null;
                int i = 0;
                try {
                    while ((out = reader.readLine()) != null) {
                        if (i > 0) {
                            output += (" " + out);
                        } else {
                            output += out;
                        }
                        i++;
                    }
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(output.equals(codeJudgement.getTestOutput()))
                    correct++;
            }

            if (correct == codeJudgementList.size()) {
                result = state = ac;
                codeQuestion.setCorrectNum(codeQuestion.getCorrectNum()+1);

                //给用户加积分
                QueryWrapper<CodeSubmit> queryWrapper1 = new QueryWrapper<>();
                queryWrapper1.eq("code_id",codeId);
                queryWrapper1.eq("student_id",studentId);
                List<CodeSubmit> codeSubmitList = codeSubmitService.list(queryWrapper1);
                boolean flag = true;
                Double accept = new Double("1.0");
                for(CodeSubmit codeSubmit1:codeSubmitList){
                    if(Double.compare(codeSubmit1.getState(),accept)==0){
                        flag = false;
                        break;
                    }
                }
                if(flag==true){
                    UpdateWrapper<Students> updateWrapper1 = new UpdateWrapper<>();
                    updateWrapper1.eq("student_id",studentId);
                    QueryWrapper<Students> queryWrapper2 = new QueryWrapper<>();
                    queryWrapper2.eq("student_id",studentId);
                    Students students = studentsService.getOne(queryWrapper2);
                    students.setPoints(students.getPoints()+codeQuestion.getDegree()*10);
                    studentsService.update(students,updateWrapper1);
                }
            }
            if (correct < codeJudgementList.size())
                result = state = wrongAnswer;

            accuracy = (double) correct / codeJudgementList.size();
            codeSubmit.setState(accuracy);
            resultObj = new ResultObj(state,"您的程序通过率为" + accuracy*100 + "%");

        }

        }catch (Exception e){
            e.printStackTrace();
        }
        codeSubmitService.save(codeSubmit);
        codeQuestionService.update(codeQuestion,updateWrapper);
        return resultObj;
    }

    private String getErrors(InputStream in) {
        BufferedReader reader=new BufferedReader(cn.hutool.core.io.IoUtil.getReader(in,"GBK"));
        StringBuffer content=new StringBuffer();
        String line="";
        try {
            while((line=reader.readLine())!=null){
                int end=line.lastIndexOf("/");
                if(end!= -1) {
                    line=line.substring(end, line.length());
                }
                content.append(line);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content.toString();
    }
}
