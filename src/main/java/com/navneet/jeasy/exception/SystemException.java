package com.navneet.jeasy.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

/**
 * This class acts as System Exception
 * @author navneetprabhakar
 */
@Getter
@Setter
@AllArgsConstructor
public class SystemException extends RuntimeException{

    private String message;
    private HttpStatus status;
}