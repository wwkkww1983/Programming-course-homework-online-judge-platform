package com.xloya.pldp.vo;

import com.xloya.pldp.entity.ExamAnswerinfo;
import com.xloya.pldp.entity.ExamPaperinfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ExamAnswerInfoVo extends ExamAnswerinfo {
    private static final long serialVersionUID = 1L;

    private Integer page = 1;
    private Integer limit = 10;

    private Integer[] ids;
}
