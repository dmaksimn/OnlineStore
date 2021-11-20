package com.dmaksimn.store.service;

import com.dmaksimn.store.domain.ProductInOrder;
import com.dmaksimn.store.domain.User;

public interface ProductInOrderService {
    void update(String itemId, Integer quantity, User user);
    ProductInOrder findOne(String itemId, User user);
}
