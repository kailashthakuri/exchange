package com.demo.exchangeservice.modules.pln.repoistory;

import com.demo.exchangeservice.modules.pln.entity.PlnExchange;
import com.demo.exchangeservice.modules.pln.entity.PlnExchangeKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PlnExchangeRepository extends JpaRepository<PlnExchange, PlnExchangeKey> {
}
