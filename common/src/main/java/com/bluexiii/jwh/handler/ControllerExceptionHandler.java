package com.bluexiii.jwh.handler;

import com.bluexiii.jwh.dto.ApiError;
import com.bluexiii.jwh.exception.AlreadyExistsException;
import com.bluexiii.jwh.exception.BusinessException;
import com.bluexiii.jwh.exception.ResourceNotFoundException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Iterator;

/**
 * 异常处理
 * Created by bluexiii on 16/9/6.
 */
@ControllerAdvice
public class ControllerExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND) //404 Not Found 未找到
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseBody
    public ApiError handleRunTimeException(ResourceNotFoundException ex) {
        ex.printStackTrace();
        return new ApiError(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.value(), ex.getMessage());
    }

    @ResponseStatus(HttpStatus.CONFLICT) //409 Conflict 已存在
    @ExceptionHandler(AlreadyExistsException.class)
    @ResponseBody
    public ApiError handleRunTimeException(AlreadyExistsException ex) {
        ex.printStackTrace();
        return new ApiError(HttpStatus.CONFLICT.value(), HttpStatus.CONFLICT.value(), ex.getMessage());
    }


    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY) //422 Unprocessable Entity 控制器参数校验异常
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ApiError handleRunTimeException(MethodArgumentNotValidException ex) {
        ex.printStackTrace();
        StringBuilder sb = new StringBuilder("参数验证失败,数量:").append(ex.getBindingResult().getErrorCount());

        Iterator iterator = ex.getBindingResult().getAllErrors().iterator();
        while (iterator.hasNext()) {
            ObjectError error = (ObjectError) iterator.next();
            DefaultMessageSourceResolvable argument = (DefaultMessageSourceResolvable) error.getArguments()[0];
            String errorParameter = argument.getDefaultMessage();
            String errorMessage = error.getDefaultMessage();
            sb.append(" [名称:").append(errorParameter).append(" 描述:").append(errorMessage).append("] ");
        }
        return new ApiError(HttpStatus.UNPROCESSABLE_ENTITY.value(), HttpStatus.UNPROCESSABLE_ENTITY.value(), sb.toString(), null, null);
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY) //422 Unprocessable Entity 服务参数校验异常
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public ApiError handleRunTimeException(ConstraintViolationException ex) {
        ex.printStackTrace();
        StringBuilder sb = new StringBuilder("参数验证失败,数量:").append(ex.getConstraintViolations().size());
        Iterator iterator = ex.getConstraintViolations().iterator();
        while (iterator.hasNext()) {
            ConstraintViolation error = (ConstraintViolation) iterator.next();
            String errorParameter = error.getPropertyPath().toString();
            String errorMessage = error.getMessage();
            sb.append(" [名称:").append(errorParameter).append(" 描述:").append(errorMessage).append("] ");
        }
        return new ApiError(HttpStatus.UNPROCESSABLE_ENTITY.value(), HttpStatus.UNPROCESSABLE_ENTITY.value(), sb.toString(), null, null);
    }



    @ResponseStatus(HttpStatus.BAD_REQUEST) //400 Bad Request 801 SQL异常
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseBody
    public ApiError handleRunTimeException(DataIntegrityViolationException ex) {
        ex.printStackTrace();
        return new ApiError(HttpStatus.BAD_REQUEST.value(), 801, "SQL执行异常", ex.getMessage(), null);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST) //400 Bad Request 通用业务逻辑异常
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    public ApiError handleRunTimeException(HttpMessageNotReadableException ex) {
        ex.printStackTrace();
        return new ApiError(HttpStatus.BAD_REQUEST.value(), 802, "请求参数无法识别",ex.getMessage(),null);
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST) //400 Bad Request 通用业务逻辑异常
    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public ApiError handleRunTimeException(BusinessException ex) {
        ex.printStackTrace();
        return new ApiError(HttpStatus.BAD_REQUEST.value(), ex.getCode(), ex.getMessage());
    }



    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) //500 Internal Server Error 其它异常
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ApiError handleRunTimeException(Exception ex) {
        ex.printStackTrace();
        return new ApiError(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
    }

}