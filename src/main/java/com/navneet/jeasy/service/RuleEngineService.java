package com.navneet.jeasy.service;

import com.navneet.jeasy.models.RuleEngineRequest;
import com.navneet.jeasy.models.RuleEngineResponse;

/**
 * This interface executes the jeasy rules
 * @author navneetprabhakar
 */
public interface RuleEngineService {
    /**
     * This method executes rules
     * @param request: @{@link RuleEngineRequest}
     * @return response: @{@link RuleEngineResponse}
     */
    RuleEngineResponse executeRule(RuleEngineRequest request);
}
