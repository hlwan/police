package com.task.police.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.Serializable;

/**
 * H.L.Wan 595710104@qq.com
 * Suzhou
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponseResult <T> implements Serializable {


    private static final long serialVersionUID = 5593447497979032307L;

    /**
     * 结果信息
     */
    protected T target = null;

    /**
     * 错误编码,编码规则参考 http://192.168.8.50:8090/pages/viewpage.action?pageId=2491274#id-错误描述和响应-错误编码
     */
    private String code;


    public static <T> ResponseEntity<ApiResponseResult<T>> send(HttpStatus status, T obj, String code) {
        ApiResponseResult<T> response = new ApiResponseResult<T>();
        response.setTarget(obj);
        response.setCode(code);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        headers.set("content-type", "application/json; charset=UTF-8");
        return new ResponseEntity<>(response, headers, status);
    }

    /**
     * 获取结果信息集合
     *
     * @return 结果信息集合
     */
    public T getTarget() {
        return target;
    }

    /**
     * 设置结果信息集合
     *
     * @param target 结果信息集合
     */
    public void setTarget(T target) {
        this.target = target;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
