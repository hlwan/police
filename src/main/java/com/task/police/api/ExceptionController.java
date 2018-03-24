package com.task.police.api;

import com.task.police.exceptions.TaskException;
import com.task.police.utils.ApiResponseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * H.L.Wan 595710104@qq.com
 * Suzhou
 */
@ControllerAdvice
public class ExceptionController {

    private final static Logger log = LoggerFactory.getLogger(ExceptionController.class);

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity handleNotReadableException(HttpMessageNotReadableException e,
                                                                                HttpServletRequest request,
                                                                                HttpServletResponse response) throws IOException {
        String message=e.getRootCause().getMessage();
        message=message.substring(0,message.indexOf(","));
        return handleException(new TaskException(message), request,response);
    }


    //不做页面跳转
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handleValidationException(MethodArgumentNotValidException e,
                                                                               HttpServletRequest request,
                                                                               HttpServletResponse response) throws IOException{
//        return internalHandling(HttpStatus.BAD_REQUEST,
//                e.getBindingResult().getAllErrors().get(0).getDefaultMessage(), request, response);
        return handleException(new TaskException(e.getBindingResult().getAllErrors().get(0).getDefaultMessage()), request,response);
    }

    //不做页面跳转
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity handleConstraintViolationException(ConstraintViolationException e,
                                                                                        HttpServletRequest request,
                                                                                        HttpServletResponse response) throws IOException{
//        return internalHandling(HttpStatus.BAD_REQUEST,
//                e.getBindingResult().getAllErrors().get(0).getDefaultMessage(), request, response);
        return handleException(new TaskException(e.getConstraintViolations().iterator().next().getMessage()),request,response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity handleException(Exception e, HttpServletRequest request, HttpServletResponse response) throws IOException{
        return handleException(new TaskException(e.getMessage(),e),request,response);
    }

    @ExceptionHandler(TaskException.class)
    public ResponseEntity handleException(TaskException e, HttpServletRequest request, HttpServletResponse response) throws IOException{
        log.error("Exception:"+e.getMessage(),e);
        return handleException(e.getEntity(),request,response);
    }

    public ResponseEntity handleException(TaskException.ExceptionEntity content, HttpServletRequest request, HttpServletResponse response) throws IOException{
        if(request.getHeader("Accept").equals("application/json")){
            return ApiResponseEntity.sendFail(content);
        }else{
            response.sendRedirect(request.getContextPath()+request.getServletPath()+"/err?msg="+ URLEncoder.encode(content.getMsg(),"UTF-8"));
        }
        return null;
    }

    public  ResponseEntity<String> handleException(String content, HttpServletRequest request, HttpServletResponse response) throws IOException{
        if(request.getHeader("Accept").equals("application/json")){
            return ApiResponseEntity.sendFail(content);
        }else{
            response.sendRedirect(request.getContextPath()+request.getServletPath()+"/err?msg="+ URLEncoder.encode(content,"UTF-8"));
        }
        return null;
    }

//    public ResponseEntity<String> handleException(String content, HttpServletRequest request, HttpServletResponse response) throws IOException{
//        if(request.getHeader("Accept").equals("application/json")){
//            response.setStatus(500);
//            //return ApiResponseEntity.send(HttpStatus.INTERNAL_SERVER_ERROR,content);
//            response.setHeader("content-type","application/json;charset=utf-8");
//            response.getWriter().print(content);
//            response.getWriter().flush();
//        }else{
//            response.sendRedirect(request.getContextPath()+request.getServletPath()+"/err?msg="+ URLEncoder.encode(content,"UTF-8"));
//        }
//        return null;
//    }
}
