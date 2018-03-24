package com.task.police.utils;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.Serializable;

/**
 * H.L.Wan 595710104@qq.com
 * Suzhou
 * 结果类
 */
public class ApiResponseEntity implements Serializable {

    private static final long serialVersionUID = 4425372968541587317L;

    /**
     * 返回成功的结果，状态码为200
     * @param obj
     * @param <T>
     * @return
     */
    public static <T> ResponseEntity<T> sendSuccess(T obj) {
        return send(HttpStatus.OK,obj);
    }

    /**
     * 返回成功的结果，状态码为200
     * @param obj
     * @param <T>
     * @return
     */
    public static <T> ResponseEntity<T> sendFail(T obj) {
        return send(HttpStatus.INTERNAL_SERVER_ERROR,obj);
    }

    private static <T> ResponseEntity<T> send(HttpStatus status,T obj) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        headers.set("content-type", "application/json;charset=UTF-8");
        return new ResponseEntity<>(obj, headers,status);
    }


}
