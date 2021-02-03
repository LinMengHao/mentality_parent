package com.lmh.eduService.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PsychologistQuery {
    @ApiModelProperty(value = "心理医生姓名")
    private String name;

    @ApiModelProperty(value = "头衔 1高级心理医生 2首席心理医生")
    private Integer level;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "电话号码")
    private String phoneNumber;

    @ApiModelProperty(value = "身份证号码")
    private String identityCard;
}
