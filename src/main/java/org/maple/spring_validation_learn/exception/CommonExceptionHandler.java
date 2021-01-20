package org.maple.spring_validation_learn.exception;

import org.maple.spring_validation_learn.common.ReturnCode;
import org.maple.spring_validation_learn.common.ReturnMsg;
import org.maple.spring_validation_learn.common.ReturnResult;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Iterator;

@RestControllerAdvice
public class CommonExceptionHandler {

    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ReturnResult<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        ReturnResult<Object> ret = new ReturnResult<>();
        BindingResult bindingResult = ex.getBindingResult();
        StringBuilder sb = new StringBuilder();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            sb.append(fieldError.getField()).append("：").append(fieldError.getDefaultMessage()).append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        String msg = sb.toString();
        ret.setMessage(ReturnMsg.PARAMS_VALIDATE_FAILED + "," + msg, ReturnCode.PARAMS_VALIDATE_FAILED);
        return ret;
    }

    @ExceptionHandler({ConstraintViolationException.class})
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ReturnResult<Object> handleConstraintViolationException(ConstraintViolationException ex) {
        ReturnResult<Object> ret = new ReturnResult<>();
        Iterator<ConstraintViolation<?>> iterator = ex.getConstraintViolations().iterator();
        StringBuilder sb = new StringBuilder();
        while (iterator.hasNext()) {
            sb.append(iterator.next().getMessage()).append(";");
        }
        sb.deleteCharAt(sb.length() - 1);
        ret.setMessage(ReturnMsg.PARAMS_VALIDATE_FAILED + "," + sb.toString(), ReturnCode.PARAMS_VALIDATE_FAILED);
        return ret;
    }
}
