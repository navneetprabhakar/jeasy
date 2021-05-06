package com.navneet.jeasy.service.impl;

import com.navneet.jeasy.models.RuleEngineRequest;
import com.navneet.jeasy.models.RuleEngineResponse;
import com.navneet.jeasy.models.WorkflowProcess;
import com.navneet.jeasy.config.RuleRegistry;
import com.navneet.jeasy.service.RuleEngineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * This class implements @{@link RuleEngineService}
 * @author navneetprabhakar
 */
@Service
public class RuleEngineServiceImpl implements RuleEngineService {

    @Autowired
    private RuleRegistry ruleRegistry;

    @Override
    public RuleEngineResponse executeRule(RuleEngineRequest request) {
        WorkflowProcess process= new WorkflowProcess(request, new RuleEngineResponse());
        ruleRegistry.fireRules(process);
        return process.getResponse();
    }
}
