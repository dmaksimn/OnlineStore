package com.dmaksimn.store.repository;

import com.dmaksimn.store.domain.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Integer> {
}
