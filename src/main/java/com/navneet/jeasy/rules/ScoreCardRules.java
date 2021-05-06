package com.navneet.jeasy.rules;

import com.navneet.jeasy.models.WorkflowProcess;
import org.jeasy.rules.annotation.Action;
import org.jeasy.rules.annotation.Condition;
import org.jeasy.rules.annotation.Priority;
import org.jeasy.rules.annotation.Rule;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

/**
 * This rule calculates Grade, marks obtained, total marks and percentage as per request
 * @author navneetprabhakar
 */
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Component
@Rule(name = "Score Card", description = "Generate Score Card")
public class ScoreCardRules implements RuleTemplate {

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
        if(null!=process &&
                null!=process.getRequest() && !CollectionUtils.isEmpty(process.getRequest().getMarksheet())){
            process.getResponse().setMarksObtained(process.getRequest().getMarksheet().stream().mapToInt(e-> e.getMarks()).sum());
            process.getResponse().setTotal(process.getRequest().getMarksheet().stream().mapToInt(e-> e.getTotalMarks()).sum());
            process.getResponse().setPercentage(((process.getResponse().getMarksObtained()*1.0)/(process.getResponse().getTotal() *1.0))*100.0);
            return true;
        }
        return false;
    }

    @Action
    @Override
    public void then() {
        if(process.getResponse().getPercentage()>=90.0){
            process.getResponse().setGrade("O");
        }else if(process.getResponse().getPercentage()>=80.0){
            process.getResponse().setGrade("A");
        }else if(process.getResponse().getPercentage()>=70.0){
            process.getResponse().setGrade("B");
        }else if(process.getResponse().getPercentage()>=60.0){
            process.getResponse().setGrade("C");
        }else if(process.getResponse().getPercentage()>=50.0){
            process.getResponse().setGrade("D");
        }else if(process.getResponse().getPercentage()>=40.0){
            process.getResponse().setGrade("E");
        }else if(process.getResponse().getPercentage()>=33.0){
            process.getResponse().setGrade("P");
        }else{
            process.getResponse().setGrade("F");
        }
    }

    @Priority
    @Override
    public int getPriority(){
        return sequence;
    }

}
