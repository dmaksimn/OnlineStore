package com.dmaksimn.store.service.impl;

import com.dmaksimn.store.domain.ProductInOrder;
import com.dmaksimn.store.domain.User;
import com.dmaksimn.store.repository.ProductInOrderRepository;
import com.dmaksimn.store.service.ProductInOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class ProductInOrderServiceImpl implements ProductInOrderService {
    @Autowired
    ProductInOrderRepository productInOrderRepository;

    @Override
    @Transactional
    public void update(String itemId, Integer quantity, User user) {
        Optional<ProductInOrder> updateOrder = user.getCart().getProducts().stream().filter(e -> itemId.equals(e.getProduct_id())).findFirst();
        updateOrder.ifPresent(productInOrder -> {
            productInOrder.setCount(quantity);
            productInOrderRepository.save(productInOrder);
        });
    }
    @Override
    public ProductInOrder findOne(String itemId, User user) {
        Optional<ProductInOrder> findOneOrder = user.getCart().getProducts().stream().filter(e -> itemId.equals(e.getProduct_id())).findFirst();
        AtomicReference<ProductInOrder> resultFindOneProductInOrder = new AtomicReference<>();
        findOneOrder.ifPresent(resultFindOneProductInOrder::set);
        return resultFindOneProductInOrder.get();
    }
}
