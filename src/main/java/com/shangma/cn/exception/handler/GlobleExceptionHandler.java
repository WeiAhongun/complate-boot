package com.shangma.cn.exception.handler;

import com.shangma.cn.common.http.AxiosResult;
import com.shangma.cn.common.http.AxiosStatus;
import com.shangma.cn.exception.LoginTimeExprieException;
import com.shangma.cn.exception.TokenVerifyAndAuthorizationException;
import com.shangma.cn.exception.UplaodException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@RestControllerAdvice
public class GlobleExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public AxiosResult<Map<String, String>> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        Map<String, String> map = new HashMap<>();
        if (bindingResult.hasFieldErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            fieldErrors.forEach(i -> {
                String field = i.getField();
                String defaultMessage = i.getDefaultMessage();
                map.put(field, defaultMessage);
            });
        }
        return AxiosResult.error(AxiosStatus.FORM_VALID_FAIL, map);
    }
    @ExceptionHandler(UplaodException.class)
    public AxiosResult<Void> methodArgumentNotValidExceptionHandler(UplaodException e) {
        return AxiosResult.error(e.getAxiosStatus());
    }

    @ExceptionHandler(TokenVerifyAndAuthorizationException.class)
    public AxiosResult<Void> tokenVerifyAndAuthorizationExceptionHandler(TokenVerifyAndAuthorizationException e) {
        return AxiosResult.error(e.getAxiosStatus());
    }

    @ExceptionHandler(LoginTimeExprieException.class)
    public AxiosResult<Void> LoginTimeExprieExceptionHandler(LoginTimeExprieException e) {
        return AxiosResult.error(e.getAxiosStatus());
    }
}
