package com.navneet.jeasy.rules;

import com.navneet.jeasy.models.WorkflowProcess;

/**
 * This interface acts as the template for all rules in the project.
 * This also helps in the Factory design pattern implementation using @{@link com.navneet.jeasy.config.RuleRegistry}
 * @author navneetprabhakar
 */
public interface RuleTemplate {

    /**
     * This method loads request and execution sequence priority in the rule
     * @param process : @{@link WorkflowProcess} contains request and response objects
     * @param sequence : Sequence priority
     */
    void loadRequest(WorkflowProcess process, Integer sequence);

    /**
     * This method is implemented for @{@link org.jeasy.rules.annotation.Condition} in jeasy framework
     * @return boolean: whether rule passed or not
     */
    boolean when();

    /**
     * This method is implemented for @{@link org.jeasy.rules.annotation.Action}
     */
    void then();

    /**
     * This method is implemented for @{@link org.jeasy.rules.annotation.Priority}
     * @return priority: sequence priority of rule
     */
    int getPriority();
}
