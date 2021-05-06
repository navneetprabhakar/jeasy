package com.navneet.jeasy.models;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author navneetprabhakar
 */
@Data
@ApiModel
public class RuleEngineResponse {
    @ApiModelProperty(notes = "Grade received")
    private String grade;
    @ApiModelProperty(notes = "Total marks obtained")
    private Integer marksObtained;
    @ApiModelProperty(notes = "Total marks in exam")
    private Integer total;
    @ApiModelProperty(notes = "Percentage obtained")
    private Double percentage;
    @ApiModelProperty(notes = "Teacher's remark")
    private String remarks;
}
