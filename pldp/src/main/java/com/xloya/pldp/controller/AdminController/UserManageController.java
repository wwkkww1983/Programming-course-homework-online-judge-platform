package com.xloya.pldp.controller.AdminController;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.xloya.pldp.common.ResultObj;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xloya.pldp.common.DataGridView;
import com.xloya.pldp.entity.Admins;
import com.xloya.pldp.entity.Classes;
import com.xloya.pldp.entity.Students;
import com.xloya.pldp.entity.Teachers;
import com.xloya.pldp.service.AdminsService;
import com.xloya.pldp.service.ClassesService;
import com.xloya.pldp.service.StudentsService;
import com.xloya.pldp.service.TeachersService;
import com.xloya.pldp.vo.AdminsVo;
import com.xloya.pldp.vo.ClassesVo;
import com.xloya.pldp.vo.StudentsVo;
import com.xloya.pldp.vo.TeachersVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
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
public class UserManageController {

    @Autowired
    StudentsService studentsService;

    @Autowired
    TeachersService teachersService;

    @Autowired
    AdminsService adminsService;

    @Autowired
    ClassesService classesService;

    /**
     * 跳转到学生信息管理页面
     */
    @RequestMapping("/toStudentInfoManager")
    public String toStudentInfoManager() {
        return "system/admins/usermanage/student_info_manager";
    }


    /**
     * 学生信息查询
     */
    @RequestMapping("/showStudentList")
    @ResponseBody
    public DataGridView showStudentList(StudentsVo studentsVo) {
        IPage<Students> page = new Page<>(studentsVo.getPage(), studentsVo.getLimit());
        QueryWrapper<Students> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(studentsVo.getName()),"name",studentsVo.getName());
        queryWrapper.eq(studentsVo.getStudentId()!=null,"student_id",studentsVo.getStudentId());
        queryWrapper.like(studentsVo.getIdCard()!=null,"id_card",studentsVo.getIdCard());
        this.studentsService.page(page, queryWrapper);
        return new DataGridView(page.getTotal(), page.getRecords());
    }

    /**
     * 添加学生信息
     */
    @RequestMapping("/addStudent")
    @ResponseBody
    public ResultObj addStudent(StudentsVo studentsVo){
        try {
            QueryWrapper<Students> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("student_id",studentsVo.getStudentId()).or().eq("id_card",studentsVo.getIdCard());
            QueryWrapper<Classes> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("class_id",studentsVo.getClassId());
            if(null!=studentsService.getOne(queryWrapper)||classesService.getOne(queryWrapper1)==null)
                return ResultObj.ADD_ERROR;
            studentsService.save(studentsVo);
            return ResultObj.ADD_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.ADD_ERROR;
        }
    }


    /**
     * 修改学生信息
     */
    @RequestMapping("/updateStudent")
    @ResponseBody
    public ResultObj updateStudent(StudentsVo studentsVo) {
        try {
            QueryWrapper<Classes> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("class_id",studentsVo.getClassId());
            if(classesService.getOne(queryWrapper)==null)
                return ResultObj.UPDATE_ERROR;
            this.studentsService.updateById(studentsVo);
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.UPDATE_ERROR;
        }
    }

    /**
     * 删除学生信息
     */
    @RequestMapping("/deleteStudent")
    @ResponseBody
    public ResultObj deleteStudent(Integer id){
        try {
            this.studentsService.removeById(id);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }

    /**
     * 批量删除学生信息
     */
    @RequestMapping("/batchDeleteStudent")
    @ResponseBody
    public ResultObj batchDeleteStudent(StudentsVo studentsVo) {
        try {
            Collection<Serializable> idList = new ArrayList<Serializable>();
            for (Integer id : studentsVo.getIds()) {
                idList.add(id);
            }
            this.studentsService.removeByIds(idList);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }


    /**
     * 跳转到教师信息管理页面
     */
    @RequestMapping("/toTeacherInfoManager")
    public String toTeacherInfoManager() {
        return "system/admins/usermanage/teacher_info_manager";
    }

    /**
     * 教师信息查询
     */
    @RequestMapping("/showTeacherList")
    @ResponseBody
    public DataGridView showTeacherList(TeachersVo teachersVo) {
        IPage<Teachers> page = new Page<>(teachersVo.getPage(), teachersVo.getLimit());
        QueryWrapper<Teachers> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(teachersVo.getName()),"name",teachersVo.getName());
        queryWrapper.eq(teachersVo.getTeacherId()!=null,"teacher_id",teachersVo.getTeacherId());
        queryWrapper.like(teachersVo.getIdCard()!=null,"id_card",teachersVo.getIdCard());
        this.teachersService.page(page, queryWrapper);
        return new DataGridView(page.getTotal(), page.getRecords());
    }

    /**
     * 添加教师信息
     */
    @RequestMapping("/addTeacher")
    @ResponseBody
    public ResultObj addTearcher(TeachersVo teachersVo){
        try {
            QueryWrapper<Teachers> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("teacher_id",teachersVo.getTeacherId()).or().eq("id_card",teachersVo.getIdCard());
            if(null!=teachersService.getOne(queryWrapper))
                return ResultObj.ADD_ERROR;
            teachersService.save(teachersVo);
            return ResultObj.ADD_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.ADD_ERROR;
        }
    }


    /**
     * 修改教师信息
     */
    @RequestMapping("/updateTeacher")
    @ResponseBody
    public ResultObj updateTeacher(TeachersVo teachersVo) {
        try {
            this.teachersService.updateById(teachersVo);
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.UPDATE_ERROR;
        }
    }

    /**
     * 删除教师信息
     */
    @RequestMapping("/deleteTeacher")
    @ResponseBody
    public ResultObj deleteTeacher(Integer id){
        try {
            this.teachersService.removeById(id);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }

    /**
     * 批量删除教师信息
     */
    @RequestMapping("/batchDeleteTeacher")
    @ResponseBody
    public ResultObj batchDeleteTeacher(TeachersVo teachersVo) {
        try {
            Collection<Serializable> idList = new ArrayList<Serializable>();
            for (Integer id : teachersVo.getIds()) {
                idList.add(id);
            }
            this.teachersService.removeByIds(idList);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }


    /**
     * 跳转到管理员信息管理页面
     */
    @RequestMapping("/toAdminInfoManager")
    public String toAdminInfoManager() {
        return "system/admins/usermanage/admin_info_manager";
    }

    /**
     * 管理员信息查询
     */
    @RequestMapping("/showAdminList")
    @ResponseBody
    public DataGridView showAdminList(AdminsVo adminsVo) {
        IPage<Admins> page = new Page<>(adminsVo.getPage(), adminsVo.getLimit());
        QueryWrapper<Admins> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(adminsVo.getName()),"name",adminsVo.getName());
        queryWrapper.eq(adminsVo.getAdminId()!=null,"admin_id",adminsVo.getAdminId());
        queryWrapper.like(adminsVo.getIdCard()!=null,"id_card",adminsVo.getIdCard());
        this.adminsService.page(page, queryWrapper);
        return new DataGridView(page.getTotal(), page.getRecords());
    }

    /**
     * 添加管理员信息
     */
    @RequestMapping("/addAdmin")
    @ResponseBody
    public ResultObj addAdmin(AdminsVo adminsVo){
        try {
            QueryWrapper<Admins> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("admin_id",adminsVo.getAdminId()).or().eq("id_card",adminsVo.getIdCard());
            if(null!=adminsService.getOne(queryWrapper))
                return ResultObj.ADD_ERROR;
            adminsService.save(adminsVo);
            return ResultObj.ADD_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.ADD_ERROR;
        }
    }

    /**
     * 修改管理员信息
     */
    @RequestMapping("/updateAdmin")
    @ResponseBody
    public ResultObj updateAdmin(AdminsVo adminsVo) {
        try {
            this.adminsService.updateById(adminsVo);
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.UPDATE_ERROR;
        }
    }

    /**
     * 删除管理员信息
     */
    @RequestMapping("/deleteAdmin")
    @ResponseBody
    public ResultObj deleteAdmin(Integer id){
        try {
            this.adminsService.removeById(id);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }

    /**
     * 批量删除管理员信息
     */
    @RequestMapping("/batchDeleteAdmin")
    @ResponseBody
    public ResultObj batchDeleteAdmin(AdminsVo adminsVo) {
        try {
            Collection<Serializable> idList = new ArrayList<Serializable>();
            for (Integer id : adminsVo.getIds()) {
                idList.add(id);
            }
            this.adminsService.removeByIds(idList);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }


    /**
     * 跳转到班级信息管理页面
     */
    @RequestMapping("/toClassInfoManager")
    public String toClassInfoManager() {
        return "system/admins/usermanage/class_info_manager";
    }

    /**
     * 班级信息查询
     */
    @RequestMapping("/showClassList")
    @ResponseBody
    public DataGridView showClassList(ClassesVo classesVo) {
        IPage<Classes> page = new Page<>(classesVo.getPage(), classesVo.getLimit());
        QueryWrapper<Classes> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(classesVo.getClassName()),"class_name",classesVo.getClassName());
        queryWrapper.eq(classesVo.getClassId()!=null,"class_id",classesVo.getClassId());
        queryWrapper.eq(classesVo.getTeacherId()!=null,"teacher_id",classesVo.getTeacherId());
        this.classesService.page(page, queryWrapper);
        return new DataGridView(page.getTotal(), page.getRecords());
    }

    /**
     * 添加班级信息
     */
    @RequestMapping("/addClass")
    @ResponseBody
    public ResultObj addClass(ClassesVo classesVo){
        try {
            QueryWrapper<Classes> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("class_id",classesVo.getClassId());
            QueryWrapper<Teachers> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("teacher_id",classesVo.getTeacherId());
            if(null!=classesService.getOne(queryWrapper)||teachersService.getOne(queryWrapper1)==null)
                return ResultObj.ADD_ERROR;
            classesService.save(classesVo);
            return ResultObj.ADD_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.ADD_ERROR;
        }
    }

    /**
     * 修改班级信息
     */
    @RequestMapping("/updateClass")
    @ResponseBody
    public ResultObj updateClass(ClassesVo classesVo) {
        try {
            QueryWrapper<Teachers> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("teacher_id",classesVo.getTeacherId());
            if(teachersService.getOne(queryWrapper)==null)
                return ResultObj.UPDATE_ERROR;
            this.classesService.updateById(classesVo);
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.UPDATE_ERROR;
        }
    }

    /**
     * 删除班级信息
     */
    @RequestMapping("/deleteClass")
    @ResponseBody
    public ResultObj deleteClass(Integer id){
        try {
            Students students = new Students();
            students.setClassId(0);
            Classes classes = classesService.getById(id);
            UpdateWrapper<Students> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("class_id",classes.getClassId());
            studentsService.update(students,updateWrapper);
            this.classesService.removeById(id);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }

    /**
     * 批量删除班级信息
     */
    @RequestMapping("/batchDeleteClass")
    @ResponseBody
    public ResultObj batchDeleteClass(ClassesVo classesVo) {
        try {
            Collection<Serializable> idList = new ArrayList<Serializable>();
            for (Integer id : classesVo.getIds()) {
                idList.add(id);
                Students students = new Students();
                students.setClassId(0);
                Classes classes = classesService.getById(id);
                UpdateWrapper<Students> updateWrapper = new UpdateWrapper<>();
                updateWrapper.eq("class_id",classes.getClassId());
                studentsService.update(students,updateWrapper);
            }
            this.classesService.removeByIds(idList);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }

}
