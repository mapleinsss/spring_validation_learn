package org.maple.spring_validation_learn.common;


public class ReturnResult<T> {

    private Integer code = ReturnCode.SUCCESS_MSG;
    private String message = ReturnMsg.SUCCESS;
    private T result;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message, Integer code) {
        this.message = message;
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        if (result == null) {
            return;
        }
        this.result = result;
    }

}
