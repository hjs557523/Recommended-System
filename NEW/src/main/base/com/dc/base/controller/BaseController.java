package com.dc.base.controller;

import com.dc.base.contants.BaseContants;
import com.dc.base.em.ErrorMesgEnum;
import com.dc.base.exception.BusinessException;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;

/**
 * @author Danpil
 * @Description TODO
 * @date 2018-11-12 15:47
 */
@Controller
@ResponseBody
public class BaseController {
    private static Logger log = Logger.getLogger(BaseController.class);
    protected HttpServletRequest request;
    protected HttpServletResponse response;
    protected HttpSession session;

    @InitBinder
    public void initHttpParams(HttpServletRequest req, HttpServletResponse res) {
        request = req;
        response = res;
        session = req.getSession();
    }

    /**
     * @return void
     * @title:<h3> baseModel参数添加前缀 <h3>
     * @author: Danpil
     * @date: 2018-8-16 17:07
     * @params [binder]
     **/
    @InitBinder("baseModel")
    public void initBinder1(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("baseModel.");
    }
/**
 * @title:<h3> 统一处理业务异常 <h3>
 * @author: Danpil
 * @date: 2018-11-13 14:50
 * @params [ex]
 * @return com.dc.base.exception.BusinessException
 **/
    @ExceptionHandler(BusinessException.class)
    public BusinessException businessException(BusinessException ex) {
        if(ex.getCode()==0){//业务异常默认错误状态码
            ex.setCode(1000);
        }
        log.error(ex.getMessage(), ex);
        response.setStatus(ex.getCode());
        return ex;
    }
/**
 * @title:<h3> 统一处理SQL异常 <h3>
 * @author: Danpil
 * @date: 2018-11-13 14:50
 * @params [ex]
 * @return com.dc.base.exception.BusinessException
 **/
    @ExceptionHandler({SQLException.class})
    public BusinessException SQLException(SQLException ex) {
        BusinessException bex = new BusinessException(ErrorMesgEnum.SQL_ERROR);
        bex.setStackTrace(ex.getStackTrace());
        return businessException(bex);
    }

/**
 * @title:<h3> 统一处理系统异常 <h3>
 * @author: Danpil
 * @date: 2018-11-13 14:50
 * @params [ex]
 * @return com.dc.base.exception.BusinessException
 **/
    @ExceptionHandler(Exception.class)
    public BusinessException exception(Exception ex) {
        BusinessException bex = new BusinessException();
        bex.setStackTrace(ex.getStackTrace());
        if("org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator".equals(ex.getStackTrace()[0].getClassName())){
            bex.setMessage(ErrorMesgEnum.SQL_ERROR);
        }else{
            bex.setMessage(ErrorMesgEnum.SYS_ERROR);
        }
        return businessException(bex);
    }

}
