package com.dc.base.exception;

import com.dc.base.em.ErrorMesgEnum;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * @author Danpil
 * @Description 业务异常类
 * @date 2018-11-13 13:46
 */
public class BusinessException extends RuntimeException {
    @ApiModelProperty("异常状态码")
    int code;
    @ApiModelProperty("异常消息")
    String message;

    public BusinessException(int code, String message) {
        this.code = code;
        this.message = message;
    }
    public BusinessException(){}
    public BusinessException(String message) {
        this.message = message;
    }
    public BusinessException(ErrorMesgEnum em){
        this.code=em.getCode();
        this.message=em.getMesg();
    }
    public void setMessage(ErrorMesgEnum em){
        this.code=em.getCode();
        this.message=em.getMesg();
    }
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
