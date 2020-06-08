package com.xloya.pldp.vo;

import com.xloya.pldp.entity.CodeDiscuss;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class CodeDiscussVo extends CodeDiscuss {
    private static final long serialVersionUID = 1L;

    private Integer page = 1;
    private Integer limit = 10;

    private Integer[] ids;

    private String studentName;
}
