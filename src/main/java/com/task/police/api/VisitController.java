package com.task.police.api;

import com.querydsl.core.types.Predicate;
import com.task.police.domain.AccountVisit;
import com.task.police.service.AccountVisitService;
import com.task.police.utils.ApiResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dashboard/visit")
public class VisitController {

    @Autowired
    private AccountVisitService accountService;

    @GetMapping("/search")
    public ResponseEntity<Page<AccountVisit>> list(@QuerydslPredicate(root = AccountVisit.class)Predicate predicate, Pageable pageable){
        Pageable p=  PageRequest.of(pageable.getPageNumber(),pageable.getPageSize(),new Sort(Sort.Direction.DESC,"createTime"));
        return ApiResponseEntity.sendSuccess(accountService.findAllVisit(predicate, p));
    }

    @GetMapping("/list")
    public String listPage(){
        return "visit/list";
    }
}
