package com.xloya.pldp.vo;

import com.xloya.pldp.entity.ExerciseJudgement;
import com.xloya.pldp.entity.ExerciseQuestion;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ExerciseJudgementVo extends ExerciseJudgement {
    private static final long serialVersionUID = 1L;

    private Integer page = 1;
    private Integer limit = 10;

    private Integer[] ids;
}
