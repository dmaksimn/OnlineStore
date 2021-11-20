package com.dmaksimn.store.repository;

import com.dmaksimn.store.domain.ProductInOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductInOrderRepository extends JpaRepository<ProductInOrder, Long> {
}
