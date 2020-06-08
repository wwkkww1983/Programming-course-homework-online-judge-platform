package com.xloya.pldp.vo;

import com.xloya.pldp.entity.CodeQuestion;
import com.xloya.pldp.entity.FillBlankQuestion;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class CodeQuestionVo extends CodeQuestion {
    private static final long serialVersionUID = 1L;

    private Integer page = 1;
    private Integer limit = 10;

    private Integer[] ids;
}
