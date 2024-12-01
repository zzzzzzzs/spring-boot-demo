package com.zzzzzzzs.globalexcept.handler;

import com.zzzzzzzs.base.response.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.stream.Collectors;


@Slf4j
@RestControllerAdvice //对Controller增强,并返回json格式字符串
public class GlobalExceptionHandler {

    /**
     * 捕获参数校验异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public CommonResponse<Object> exception(MethodArgumentNotValidException e, HttpServletRequest request) {
        String error = e.getBindingResult().getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(";"));
        log.warn("request warn!! method:{} uri:{} error:{}", request.getMethod(), request.getRequestURI(), error);

        return CommonResponse.builder().code(-1).message(error).build();
    }

    /**
     * 捕获参数校验异常
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public CommonResponse<Object> exception(IllegalArgumentException e, HttpServletRequest request) {
        log.warn("request warn!! method:{} uri:{} error:{}", request.getMethod(), request.getRequestURI(), e.getMessage());
        return CommonResponse.builder().code(-1).message(e.getMessage()).build();
    }

    /**
     * 捕获Exception异常,并自定义返回数据
     */
    @ExceptionHandler(Exception.class)
    public CommonResponse<Object> exception(Exception e, HttpServletRequest request) {
        log.error("request error!! method:{} uri:{}", request.getMethod(), request.getRequestURI());
        log.error(getExceptionDetail(e));
        return CommonResponse.builder().code(-1).message(request.getMethod() + " " + request.getRequestURI() + " " + e.getMessage()).build();
    }

    /**
     * 获取代码报错详细位置信息
     */
    public String getExceptionDetail(Exception e) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(e.getClass()).append(System.getProperty("line.separator"));
        stringBuilder.append(e.getLocalizedMessage()).append(System.getProperty("line.separator"));
        StackTraceElement[] arr = e.getStackTrace();
        for (StackTraceElement stackTraceElement : arr) {
            stringBuilder.append(stackTraceElement.toString()).append(System.getProperty("line.separator"));
        }
        return stringBuilder.toString();
    }
}