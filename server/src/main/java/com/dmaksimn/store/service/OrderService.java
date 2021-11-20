package com.dmaksimn.store.service;

import com.dmaksimn.store.domain.OrderMain;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    Page<OrderMain> findAll(Pageable pageable);
    Page<OrderMain> findByStatus(Integer status, Pageable pageable);
    OrderMain findOne(Long id);
    OrderMain finish(Long id);
    OrderMain cancel(Long id);
    Page<OrderMain> findByClientEmail(String email, Pageable pageable);
}
