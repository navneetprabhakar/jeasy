package com.navneet.jeasy.rules;

import com.navneet.jeasy.models.WorkflowProcess;
import org.jeasy.rules.annotation.Action;
import org.jeasy.rules.annotation.Condition;
import org.jeasy.rules.annotation.Priority;
import org.jeasy.rules.annotation.Rule;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * This rule generates remarks based on Score Grade from @{@link ScoreCardRules}
 * @author navneetprabhakar
 */
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Component
@Rule(name = "Remark Rule", description = "Generate remarks as per Score Card")
public class RemarkRules implements RuleTemplate{

    private WorkflowProcess process;
    private Integer sequence;

    @Override
    public void loadRequest(WorkflowProcess process, Integer sequence) {
        this.process=process;
        this.sequence=sequence;
    }

    @Condition
    @Override
    public boolean when() {
        return null!=process && null!=process.getResponse() && StringUtils.hasText(process.getResponse().getGrade());
    }

    @Action
    @Override
    public void then() {
        if(process.getResponse().getGrade().equals("O")){
            process.getResponse().setRemarks("Outstanding Effort");
        }else if(process.getResponse().getGrade().equals("A")){
            process.getResponse().setRemarks("Very good Effort");
        }else if(process.getResponse().getGrade().equals("B")){
            process.getResponse().setRemarks("Good Effort");
        }else if(process.getResponse().getGrade().equals("C")){
            process.getResponse().setRemarks("Admirable");
        }else if(process.getResponse().getGrade().equals("D")){
            process.getResponse().setRemarks("Can be improved");
        }else if(process.getResponse().getGrade().equals("E")){
            process.getResponse().setRemarks("Needs improvement");
        }else if(process.getResponse().getGrade().equals("P")){
            process.getResponse().setRemarks("Barely passed");
        }else{
            process.getResponse().setRemarks("Failed");
        }
    }

    @Priority
    @Override
    public int getPriority() {
        return sequence;
    }
}
