package com.navneet.jeasy.constants;

import lombok.Getter;

/**
 * This enum contains rule ids for rule name
 * @author navneetprabhakar
 */
@Getter
public enum Constants {
    SCORE_CARD_RULE(1),
    REMARK_RULE(2);
    private Integer ruleId;

    Constants(Integer ruleId){
        this.ruleId=ruleId;
    }


}
