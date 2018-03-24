package com.task.police.service;

import com.querydsl.core.types.Predicate;
import com.task.police.HttpClient;
import com.task.police.domain.Account;
import com.task.police.domain.AccountVisit;
import com.task.police.repository.AccountVisitRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.UUID;

@Service
public class AccountVisitService {

    private static final Logger log= LoggerFactory.getLogger(AccountService.class);

    @Value("${url.login}")
    private String loginUrl;

    @Autowired
    private AccountVisitRepository accountVisitRepository;

    @Transactional
    public void saveAfterMain(Account account, String url, String response, int status){
        log.info("记录首页结果:{}",account.getId());
        AccountVisit accountVisit=new AccountVisit();
        accountVisit.setUrl(url);
        accountVisit.setId(UUID.randomUUID().toString());
        accountVisit.setAccountId(account.getId());
        accountVisit.setCreateTime(new Date());
        accountVisit.setResponse(response);
        accountVisit.setStatus(status);
        accountVisitRepository.saveAndFlush(accountVisit);
    }

    @Transactional
    public String sendLoginRequest(Account account,HttpClient httpClient) {
        try{
            Thread.sleep(((int)(Math.random()*10000)));
        }catch (Exception e){
        }
        log.info("开始登录:{}",account.getId());
        AccountVisit accountVisit=new AccountVisit();
        Date now=new Date();
        accountVisit.setUrl(loginUrl.replace("_time",now.getTime()+""));
        accountVisit.setId(UUID.randomUUID().toString());
        accountVisit.setAccountId(account.getId());
        accountVisit.setCreateTime(now);
        HttpClient.HttpMessage httpMessage=new HttpClient.HttpMessage();
        httpMessage.addParameter("login_id",account.getId());
        httpMessage.addParameter("login_pwd",account.getPassword());
        httpMessage.addHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
        httpMessage.addHeader("Referer","http://10.32.0.113/os/login.jsp");
        httpMessage.addHeader("Origin","http://10.32.0.113");
        httpMessage.addHeader("Host","10.32.0.113");
        httpMessage.addHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
        httpMessage.addHeader("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36");
        try{
            String response= httpClient.post(accountVisit.getUrl(),httpMessage,false);
            accountVisit.setResponse(response);
            accountVisit.setStatus(200);
        }catch (Exception e){
            accountVisit.setStatus(500);
            accountVisit.setResponse(e.getMessage());
        }
        accountVisitRepository.saveAndFlush(accountVisit);
        log.info("完成登录:{}",account.getId());
        return accountVisit.getResponse();
    }

    public Page<AccountVisit> findAllVisit(Predicate predicate, Pageable pageable){
        return accountVisitRepository.findAll(predicate,pageable);
    }
}
