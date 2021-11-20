package com.dmaksimn.store.service.impl;

import com.dmaksimn.store.domain.OrderMain;
import com.dmaksimn.store.domain.ProductInOrder;
import com.dmaksimn.store.domain.ProductInfo;
import com.dmaksimn.store.enums.OrderStatusEnum;
import com.dmaksimn.store.enums.ResultEnum;
import com.dmaksimn.store.exception.AppException;
import com.dmaksimn.store.repository.OrderRepository;
import com.dmaksimn.store.repository.ProductInOrderRepository;
import com.dmaksimn.store.repository.ProductInfoRepository;
import com.dmaksimn.store.repository.UserRepository;
import com.dmaksimn.store.service.OrderService;
import com.dmaksimn.store.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProductInfoRepository productInfoRepository;

    @Autowired
    ProductService productService;

    @Autowired
    ProductInOrderRepository productInOrderRepository;

    @Override
    public Page<OrderMain> findAll(Pageable pageable) {
        return orderRepository.findAllByOrderByStatusAscCreateTimeDesc(pageable);
    }

    @Override
    public Page<OrderMain> findByStatus(Integer status, Pageable pageable) {
        return orderRepository.findAllByStatusOrderByCreateTimeDesc(status, pageable);
    }

    @Override
    public OrderMain findOne(Long id) {
        OrderMain orderMain = orderRepository.findById(id);
        if(orderMain == null) {
            throw new AppException(ResultEnum.ORDER_NOT_FOUND);
        }
        return orderMain;
    }

    @Override
    @Transactional
    public OrderMain finish(Long id) {
        OrderMain orderMain = findOne(id);
        if(!orderMain.getStatus().equals(OrderStatusEnum.NEW.getCode())) {
            throw new AppException(ResultEnum.ORDER_STATUS_ERROR);
        }
        orderMain.setStatus(OrderStatusEnum.FINISHED.getCode());
        orderRepository.save(orderMain);
        return orderRepository.findById(id);
    }

    @Override
    @Transactional
    public OrderMain cancel(Long id) {
        OrderMain orderMain = findOne(id);
        if(!orderMain.getStatus().equals(OrderStatusEnum.NEW.getCode())) {
            throw new AppException(ResultEnum.ORDER_STATUS_ERROR);
        }
        orderMain.setStatus(OrderStatusEnum.CANCELED.getCode());
        orderRepository.save(orderMain);
//        /**
//         *  Restore Stock
//         */
        Iterable<ProductInOrder> products = orderMain.getProducts();
        for(ProductInOrder productInOrder : products) {
            ProductInfo productInfo = productInfoRepository.findById(productInOrder.getProduct_id());
                if (productInfo != null) {
                    productService.increaseStock(productInOrder.getProduct_id(), productInOrder.getCount());
                }
        }
        return orderRepository.findById(id);
    }

    @Override
    public Page<OrderMain> findByClientEmail(String email, Pageable pageable) {
        return orderRepository.findAllByEmailOrderByStatusAscCreateTimeDesc(email, pageable);
    }
}
