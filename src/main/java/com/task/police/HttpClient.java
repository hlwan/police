package com.task.police;

import com.alibaba.fastjson.JSON;
import com.mysema.commons.lang.Assert;
import org.apache.http.*;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.*;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class HttpClient {

    private static final Logger log= LoggerFactory.getLogger(HttpClient.class);

    /**
     * the request executor service for this client.
     */
    private FutureRequestExecutionService requestExecutorService;

    private org.apache.http.client.HttpClient httpClient;

    public static final ResponseHandler<String> RESPONSE_HANDLER=new BasicResponseHandler();

    public HttpClient() {
        this.httpClient=HttpClients.createDefault();
        this.requestExecutorService =new FutureRequestExecutionService(httpClient, Executors.newSingleThreadExecutor());
    }

    public String post(String url,final HttpMessage httpMessage,boolean close) throws Exception {
        log.info("http request info .url:{},message:{}",url,httpMessage.getMessage());
        try {
            final HttpPost request = new HttpPost(url);
            if(httpMessage!=null){
                for (int i = 0; i < httpMessage.getHeaders().size(); i++) {
                    request.addHeader(httpMessage.getHeaders().get(i));
                }
            }
            if(!StringUtils.isEmpty(httpMessage.getMessage())){
                 StringEntity entity = new StringEntity(httpMessage.getMessage(),"UTF-8");
                 entity.setContentType("application/json");
                request.setEntity(entity);
            }else{
                if(httpMessage.getParams()!=null){
                    List<NameValuePair> formparams = new ArrayList<>();
                    formparams.addAll(httpMessage.getParams());
                    HttpEntity reqEntity = new UrlEncodedFormEntity(formparams, "utf-8");
                    request.setEntity(reqEntity);
                }
            }
            String result=RESPONSE_HANDLER.handleResponse(httpClient.execute(request));
            log.info("response:{}",result);
            return result;
        } catch (final Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        } finally {
            if(close){
                this.destroy();
            }
        }
    }

    public void postAsync(String url,HttpMessage httpMessage, ResponseHandler responseHandler) throws Exception{
        Assert.notNull(responseHandler,"ResponseHandler can not be null");
        try {
            log.info("http request info .url:{},message:{}",url, JSON.toJSONString(httpMessage));
            final HttpPost request = new HttpPost(url);
            if(httpMessage!=null){
                for (int i = 0; i < httpMessage.getHeaders().size(); i++) {
                    request.addHeader(httpMessage.getHeaders().get(i));
                }
            }
            if(!StringUtils.isEmpty(httpMessage.getMessage())){
                final StringEntity entity = new StringEntity(httpMessage.getMessage(),"UTF-8");
                entity.setContentType("application/json");
                request.setEntity(entity);
            }else{
                if(httpMessage.getParams()!=null){
                    List<NameValuePair> formparams = new ArrayList<>();
                    formparams.addAll(httpMessage.getParams());
                    HttpEntity reqEntity = new UrlEncodedFormEntity(formparams, "utf-8");
                    request.setEntity(reqEntity);
                }
            }
            this.requestExecutorService.execute(request,
                    HttpClientContext.create(), resppnse->{
                        Object s=responseHandler.handleResponse(resppnse);
                        log.info("response:{}",s);
                        HttpClient.this.destroy();
                        return s;
                    });
        } catch (final Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }

    public String get(String  url, HttpMessage httpMessage,boolean close) throws Exception{
        try{
            log.info("http request info .url:{},message:{}",url, JSON.toJSONString(httpMessage));
            final HttpGet request = new HttpGet(url);
            if(httpMessage!=null){
                if(httpMessage.getHeaders()!=null)
                    for (int i = 0; i < httpMessage.getHeaders().size(); i++) {
                        request.addHeader(httpMessage.getHeaders().get(i));
                    }
            }
            String result=RESPONSE_HANDLER.handleResponse(httpClient.execute(request));
            log.info("response:{}",result);
            return result;
        }catch (Exception e){
            log.error("请求异常",e);
            throw e;
        }finally {
            if(close){
                this.destroy();
            }
        }
    }

    public void getAsync(String url, HttpMessage httpMessage, ResponseHandler responseHandler) {
        Assert.notNull(responseHandler,"ResponseHandler can not be null");
        try{
            log.info("http request info .url:{},message:{}",url, JSON.toJSONString(httpMessage));
            final HttpGet request = new HttpGet(url);
            if(httpMessage!=null){
                if(httpMessage.getHeaders()!=null)
                    for (int i = 0; i < httpMessage.getHeaders().size(); i++) {
                        request.addHeader(httpMessage.getHeaders().get(i));
                    }
            }
            this.requestExecutorService.execute(request,HttpClientContext.create(), resppnse->{
                Object s=responseHandler.handleResponse(resppnse);
                HttpClient.this.destroy();
                return s;
            });
        }catch (Exception e){
            log.error("请求异常",e);
            throw e;
        }
    }

    /**
     * Shutdown the executor service and close the http client.
     *
     * @throws Exception if the executor cannot properly shut down
     */
    public void destroy() {
        log.info("关闭 executor service");
        try{
            this.requestExecutorService.close();
        }catch (Exception e){
        }
    }


    public static class HttpMessage{

        private String message;

        private List<Header> headers;

        private List<BasicNameValuePair> params;

        public List<BasicNameValuePair> getParams() {
            return params;
        }

        public void setParams(List<BasicNameValuePair> params) {
            this.params = params;
        }

        public List<Header> getHeaders() {
            return headers;
        }

        public void setHeaders(List<Header> headers) {
            this.headers = headers;
        }


        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public void addHeader(String name,String value){
            if(headers==null){
                headers=new ArrayList<>();
            }
            headers.add(new BasicHeader(name,value));
        }

        public void addParameter(String name,String value){
            if(params==null){
                params=new ArrayList<>();
            }
            params.add(new BasicNameValuePair(name,value));
        }
    }
}
