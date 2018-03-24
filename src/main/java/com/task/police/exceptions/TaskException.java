package com.task.police.exceptions;

import java.io.Serializable;

/**
 * H.L.Wan 595710104@qq.com
 * Suzhou
 */
public class TaskException extends RuntimeException {

    private ExceptionEntity entity;

    public TaskException(String msg){
        this(msg,(String)null);

    }

    public TaskException(String msg, String url){
        super(msg);
        ExceptionEntity entity=new ExceptionEntity(msg,url);
        this.entity=entity;
    }

    public TaskException(String msg, Exception e){
        super(msg,e);
        ExceptionEntity entity=new ExceptionEntity(msg,null);
        this.entity=entity;
    }

    public TaskException(String msg, String url, Exception e){
        super(msg,e);
        ExceptionEntity entity=new ExceptionEntity(msg,url);
        this.entity=entity;
    }

    public TaskException(Exception e){
        this(e.getMessage(),e);
    }

    public ExceptionEntity getEntity() {
        return entity;
    }

    public void setEntity(ExceptionEntity entity) {
        this.entity = entity;
    }

    public static class ExceptionEntity implements Serializable {

        private String msg;

        private String url;

        public ExceptionEntity(String msg){
            this(msg,null);
        }

        public ExceptionEntity(String msg,String url){
            this.msg=msg;
            this.url=url;
        }

        public String getMsg() {
            return msg;
        }

        public String getUrl() {
            return url;
        }

        public String getError(){
            return msg;
        }

        public String getInitialPreviewThumbTags(){
            return "";
        }

    }

}
