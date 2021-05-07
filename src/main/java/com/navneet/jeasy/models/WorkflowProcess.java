package com.navneet.jeasy.models;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author navneetprabhakar
 */
@Data
@AllArgsConstructor
public class WorkflowProcess {

    private RuleEngineRequest request;
    private RuleEngineResponse response;
}
