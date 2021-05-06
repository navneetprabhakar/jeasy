package com.navneet.jeasy.controller;

import com.navneet.jeasy.models.RuleEngineRequest;
import com.navneet.jeasy.models.RuleEngineResponse;
import com.navneet.jeasy.service.RuleEngineService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * This controller contains APIs for jeasy rule execution
 * @author navneetprabhakar
 */
@RestController
@RequestMapping(value = "/v1")
@Api(value = "This controller runs the Jeasy rule engine")
public class RuleEngineController {

    @Autowired
    private RuleEngineService ruleEngineService;

    /**
     * This API executes rules
     * @param request: @{@link RuleEngineRequest}
     * @return response: @{@link RuleEngineResponse}
     */
    @ApiOperation(value = "This API executes rules")
    @PostMapping("execute")
    public RuleEngineResponse executeRule(@RequestBody RuleEngineRequest request){
        return ruleEngineService.executeRule(request);
    }
}
