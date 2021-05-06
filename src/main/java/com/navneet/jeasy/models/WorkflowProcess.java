package com.navneet.jeasy.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author navneetprabhakar
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkflowProcess {

    private RuleEngineRequest request;
    private RuleEngineResponse response;
}
