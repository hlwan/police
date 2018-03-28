package com.task.police.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.querydsl.core.types.Predicate;
import com.task.police.HttpClient;
import com.task.police.domain.Account;
import com.task.police.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class AccountService {

    private static final Logger log= LoggerFactory.getLogger(AccountService.class);

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountVisitService accountVisitService;

    @Value("${url.main}")
    private String mainUrl;

    @Value("${hour.begin}")
    private String hourBegin;

    @Value("${hour.end}")
    private String hourEnd;

    @Scheduled(cron = "${visit.schedule}")
    public void schedule(){
        LocalTime localTime=LocalTime.now();
        int start=0;int end=0;
        try{
            start=Integer.parseInt(hourBegin);
        }catch (Exception e){
            log.error("开始小时信息解析失败，置为默认的7点",e);
            start=7;
        }
        try{
            end=Integer.parseInt(hourEnd);
        }catch (Exception e){
            log.error("结束小时信息解析失败，置为默认的19点",e);
            end=19;
        }

        if(localTime.getHour()<start || localTime.getHour()>end){
            log.info("只在"+start +"-"+end+"点范围内处理，时间未到，不做处理");
            return ;
        }
        List<Account> accounts=accountRepository.findAll();
        accounts.forEach(account -> {
            log.info("开始处理:{}",account.getId());
            HttpClient httpClient=new HttpClient();
            String response=accountVisitService.sendLoginRequest(account,httpClient);
            try{
                sendMainRequest(account,response,httpClient);
            }catch (Exception e){
                log.info("请求首页异常:{}",account.getId());
            }
            log.info("结束处理:{}",account.getId());

        });
    }

    public void sendMainRequest(Account account,String response,HttpClient httpClient) throws Exception{
        log.info("开始请求首页:{}",account.getId());
        JSONArray jsonArray= JSON.parseArray(response);
        String userId=jsonArray.getJSONObject(0).getJSONObject("data").getString("userId");
        String url=mainUrl+userId;
        HttpClient.HttpMessage httpMessage=new HttpClient.HttpMessage();
        httpMessage.addParameter("login_id",account.getId().replace("@jsciq.gov.cn",""));
        httpMessage.addParameter("login_pwd",account.getPassword());
        httpMessage.addHeader("Content-Type","application/x-www-form-urlencoded");
        httpMessage.addHeader("Referer","http://10.32.0.113/os/login.jsp");
        httpMessage.addHeader("Origin","http://10.32.0.113");
        httpMessage.addHeader("Host","10.32.0.113");
        httpMessage.addHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
        httpMessage.addHeader("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36");
        try{
            String result=httpClient.post(url,httpMessage,true);
            accountVisitService.saveAfterMain(account,url,result,200);
        }catch (Exception e){
            log.error("接口响应异常",e);
            accountVisitService.saveAfterMain(account,url,e.getMessage(),500);
        }
    }


    public Page<Account> findAccount(Predicate predicate, Pageable pageable){
        return accountRepository.findAll(predicate,pageable);
    }

    public Account findOne(String id){
        return accountRepository.getOne(id);
    }

    @Transactional
    public Account saveAccount(Account account){
        return  accountRepository.saveAndFlush(account);
    }

    @Transactional
    public void delete(String id){
        accountRepository.deleteById(id);
    }

}
