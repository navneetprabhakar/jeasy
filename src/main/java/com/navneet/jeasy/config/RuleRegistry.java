package com.navneet.jeasy.config;

import com.navneet.jeasy.constants.Constants;
import com.navneet.jeasy.exception.SystemException;
import com.navneet.jeasy.models.WorkflowProcess;
import com.navneet.jeasy.rules.RemarkRules;
import com.navneet.jeasy.rules.RuleTemplate;
import com.navneet.jeasy.rules.ScoreCardRules;
import lombok.extern.log4j.Log4j2;
import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.api.RulesEngine;
import org.jeasy.rules.core.DefaultRulesEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.HashMap;

/**
 * This class acts as the Rule Registry providing the flexibility of rules to be executed
 * @author navneetprabhakar
 */
@Service
@Log4j2
public class RuleRegistry {

    private static final HashMap<Integer, Class> ruleRegister = new HashMap<>();

    @Autowired
    private ApplicationContext applicationContext;

    /**
     * This method loads rule in memory Map to be fetched later
     */
    @PostConstruct
    public void loadRules(){
        ruleRegister.put(Constants.SCORE_CARD_RULE.getRuleId(), ScoreCardRules.class);
        ruleRegister.put(Constants.REMARK_RULE.getRuleId(), RemarkRules.class);
    }

    /**
     * This method returns the Rule class as per the rule id provided.
     * @param ruleId : @{@link Integer} Rule Id that is to be returned
     * @param process : @{@link WorkflowProcess} contains both rule request and response objects
     * @param sequence : @{@link Integer} Sequence/ Priority order of rule
     * @return rule: @{@link RuleTemplate}
     */
    private RuleTemplate prepareRules(Integer ruleId, WorkflowProcess process, Integer sequence){
        RuleTemplate rule=(RuleTemplate)applicationContext.getBean(ruleRegister.get(ruleId));
        rule.loadRequest(process, sequence);
        return rule;
    }

    /**
     * This method registers rule are per the request rule ids and fires all rules
     * @param process : @{@link WorkflowProcess}
     */
    public void fireRules(WorkflowProcess process) {
        Facts facts = new Facts();
        Rules rules = new Rules();
        if (null!=process.getRequest() &&
                !CollectionUtils.isEmpty(process.getRequest().getRuleIds())) {
            int sequence=1;
            for (Integer ruleId : process.getRequest().getRuleIds()) {
                if (ruleRegister.containsKey(ruleId)) {
                    RuleTemplate rule=prepareRules(ruleId, process, sequence++);
                    rules.register(rule);
                } else {
                    log.error("Invalid Rule ID: {}", ruleId);
                    throw new SystemException("Invalid Rule ID:"+ruleId, HttpStatus.UNPROCESSABLE_ENTITY);
                }
            }
            RulesEngine rulesEngine = new DefaultRulesEngine();
            rulesEngine.fire(rules, facts);
        }else {
            log.info("Empty Rule ID list provided, no rules executed.");
            throw new SystemException("Empty Rule ID list provided, no rules executed.", HttpStatus.UNPROCESSABLE_ENTITY);
        }

    }
}
