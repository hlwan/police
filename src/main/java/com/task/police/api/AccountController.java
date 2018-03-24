package com.task.police.api;

import com.querydsl.core.types.Predicate;
import com.task.police.domain.Account;
import com.task.police.domain.QAccount;
import com.task.police.exceptions.TaskException;
import com.task.police.service.AccountService;
import com.task.police.utils.ApiResponseEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Controller
@RequestMapping("/dashboard/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping
    public ResponseEntity<Account> save(String account, String password){
        if(StringUtils.isEmpty(account)||StringUtils.isEmpty(password)){
            throw new RuntimeException("用户名密码不可为空");
        }
        return null;
    }

    @GetMapping("/list")
    public String list(){
        return "account/list";
    }

    @GetMapping("/search")
    public ResponseEntity<Page<Account>> search(@QuerydslPredicate(root = Account.class)Predicate predicate, Pageable pageable,String accountName){
        if(!StringUtils.isEmpty(accountName)){
            predicate= QAccount.account.name.contains(accountName).and(predicate);
        }
        Pageable p= PageRequest.of(pageable.getPageNumber(),pageable.getPageSize(),new Sort(Sort.Direction.DESC,"updateTime"));
        return ApiResponseEntity.sendSuccess(accountService.findAccount(predicate, p));
    }

    @GetMapping("/detailPage/{id}")
    public String detailPage(@PathVariable("id") String id, Model model){
        model.addAttribute("id",id);
        return "account/detail";
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<Account> detail(@PathVariable("id") String id){
        return ApiResponseEntity.sendSuccess(accountService.findOne(id));
    }

    @GetMapping("/addPage")
    public String addPage(){
        return "account/add";
    }

    @PostMapping("/add")
    public ResponseEntity<Account> add(@RequestBody  Account account){
        if(StringUtils.isEmpty(account.getId())){
            throw new TaskException("用户账户不能为空");
        }
        account.setId(account.getId().trim());
        if(!account.getId().endsWith("@jsciq.gov.cn")){
            account.setId(account.getId()+"@jsciq.gov.cn");
        }
        account.setCreateTime(new Date());
        account.setUpdateTime(new Date());
        return ApiResponseEntity.sendSuccess(accountService.saveAccount(account));
    }

    @GetMapping("/editPage/{id}")
    public String editPage(@PathVariable("id")String id,Model model){
        model.addAttribute("id",id);
        return "account/edit";
    }

    @PostMapping("/edit")
    public ResponseEntity<Account> edit(@RequestBody Account account){
        if(StringUtils.isEmpty(account.getId())){
            throw new TaskException("账户不能为空");
        }
        Account exist=accountService.findOne(account.getId());
        if(exist==null){
            throw new TaskException("账户不存在");
        }
        BeanUtils.copyProperties(account,exist,"id","createTime","updateTime");
        exist.setUpdateTime(new Date());
        return ApiResponseEntity.sendSuccess(accountService.saveAccount(exist));
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<Integer> delete(@PathVariable("id") String id){
        accountService.delete(id);
        return ApiResponseEntity.sendSuccess(1);

    }


}
