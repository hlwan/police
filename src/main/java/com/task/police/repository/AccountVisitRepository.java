package com.task.police.repository;

import com.task.police.domain.AccountVisit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface AccountVisitRepository extends JpaRepository<AccountVisit,String>,QuerydslPredicateExecutor<AccountVisit> {
}
