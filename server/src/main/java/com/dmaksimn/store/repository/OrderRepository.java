package com.dmaksimn.store.repository;

import com.dmaksimn.store.domain.OrderMain;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderMain, Integer> {
    OrderMain findById(Long id);

    Page<OrderMain> findAllByStatusOrderByCreateTimeDesc(Integer status, Pageable pageable);

    Page<OrderMain> findAllByOrderByStatusAscCreateTimeDesc(Pageable pageable);
    Page<OrderMain> findAllByEmailOrderByStatusAscCreateTimeDesc(String email, Pageable pageable);

}
