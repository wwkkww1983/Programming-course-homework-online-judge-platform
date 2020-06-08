package com.xloya.pldp.controller.AdminController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xloya.pldp.common.DataGridView;
import com.xloya.pldp.common.ResultObj;
import com.xloya.pldp.entity.Admins;
import com.xloya.pldp.entity.Notice;
import com.xloya.pldp.service.NoticeService;
import com.xloya.pldp.vo.NoticeVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

@Controller
@RequestMapping("/admins")
public class NoticeManageController {

    @Autowired
    NoticeService noticeService;

    /**
     * 跳转到公告管理页面
     */
    @RequestMapping("/toNoticeInfoManager")
    public String toNoticeManager() {
        return "system/admins/noticemanage/notice_info_manager";
    }

    /**
     * 公告信息查询
     */
    @RequestMapping("/showNoticeList")
    @ResponseBody
    public DataGridView showNoticeList(NoticeVo noticeVo) {
        IPage<Notice> page = new Page<>(noticeVo.getPage(), noticeVo.getLimit());
        QueryWrapper<Notice> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(noticeVo.getTitle()),"title",noticeVo.getTitle());
        queryWrapper.like(StringUtils.isNotBlank(noticeVo.getOperName()),"oper_name",noticeVo.getOperName());
        queryWrapper.ge(StringUtils.isNotBlank(noticeVo.getStartTime()), "create_time", noticeVo.getStartTime());
        queryWrapper.le(StringUtils.isNotBlank(noticeVo.getEndTime()), "create_time", noticeVo.getEndTime());
        this.noticeService.page(page, queryWrapper);
        return new DataGridView(page.getTotal(), page.getRecords());
    }

    /**
     * 前10条公告信息查询
     */
    @RequestMapping("/showTenNoticeList")
    @ResponseBody
    public DataGridView showTenNoticeList(@RequestParam("page")Long pages,@RequestParam("limit")Long limit) {
        IPage<Notice> page = new Page<>(pages, limit);
        QueryWrapper<Notice> queryWrapper = new QueryWrapper<>();
        this.noticeService.page(page, queryWrapper);
        queryWrapper.orderByDesc("create_time");
        return new DataGridView(page.getTotal(), page.getRecords());
    }

    /**
     * 添加公告
     */
    @RequestMapping("/addNotice")
    @ResponseBody
    public ResultObj addNotice(NoticeVo noticeVo,HttpServletRequest httpServletRequest){
        try {
            NoticeVo noticeVo1 = noticeVo;
            Admins admins = (Admins) httpServletRequest.getSession().getAttribute("user");
            noticeVo1.setOperName(admins.getName());
            noticeService.save(noticeVo1);
            return ResultObj.ADD_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.ADD_ERROR;
        }
    }


    /**
     * 修改公告
     */
    @RequestMapping("/updateNotice")
    @ResponseBody
    public ResultObj updateNotice(NoticeVo noticeVo) {
        try {
            this.noticeService.updateById(noticeVo);
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.UPDATE_ERROR;
        }
    }

    /**
     * 删除公告
     */
    @RequestMapping("/deleteNotice")
    @ResponseBody
    public ResultObj deleteNotice(Integer id){
        try {
            this.noticeService.removeById(id);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }

    /**
     * 批量删除公告
     */
    @RequestMapping("/batchDeleteNotice")
    @ResponseBody
    public ResultObj batchDeleteNotice(NoticeVo noticeVo) {
        try {
            Collection<Serializable> idList = new ArrayList<Serializable>();
            for (Integer id : noticeVo.getIds()) {
                idList.add(id);
            }
            this.noticeService.removeByIds(idList);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }



}
