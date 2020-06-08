package com.xloya.pldp.vo;

import com.xloya.pldp.entity.Admins;
import com.xloya.pldp.entity.ExamPaperinfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ExamPaperInfoVo extends ExamPaperinfo {
    private static final long serialVersionUID = 1L;

    private Integer page = 1;
    private Integer limit = 10;

    private Integer[] ids;
}
