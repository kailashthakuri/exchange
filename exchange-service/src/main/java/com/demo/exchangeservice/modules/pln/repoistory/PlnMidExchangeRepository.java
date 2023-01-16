package com.demo.exchangeservice.modules.pln.repoistory;

import com.demo.exchangeservice.modules.pln.entity.PlnExchangeKey;
import com.demo.exchangeservice.modules.pln.entity.PlnMidExchange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlnMidExchangeRepository extends JpaRepository<PlnMidExchange, PlnExchangeKey> {
}
