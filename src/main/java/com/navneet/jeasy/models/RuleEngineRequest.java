package com.navneet.jeasy.models;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author navneetprabhakar
 */
@Data
@ApiModel
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RuleEngineRequest {
    @ApiModelProperty(notes = "List of marks subject wise")
    private List<DataSet> marksheet;
    @ApiModelProperty(notes = "List of rule ids to be executed")
    private List<Integer> ruleIds;

    /**
     * Data object for subject wise marks
     */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class DataSet{
        @ApiModelProperty(notes = "Name of the subject")
        private String subject;
        @ApiModelProperty(notes = "Marks obtained in the subject")
        private Integer marks;
        @ApiModelProperty(notes = "Total Marks in the subject")
        private Integer totalMarks;
    }
}
