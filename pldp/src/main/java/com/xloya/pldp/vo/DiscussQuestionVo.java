package com.xloya.pldp.vo;

import com.xloya.pldp.entity.Admins;
import com.xloya.pldp.entity.DiscussQuestion;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
public class DiscussQuestionVo extends DiscussQuestion {
    private static final long serialVersionUID = 1L;

    private Integer page = 1;
    private Integer limit = 10;

    private Integer[] ids;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private String startTime;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private String endTime;
}
