package com.dmaksimn.store.service.impl;

import com.dmaksimn.store.domain.Cart;
import com.dmaksimn.store.domain.OrderMain;
import com.dmaksimn.store.domain.ProductInOrder;
import com.dmaksimn.store.domain.User;
import com.dmaksimn.store.enums.ResultEnum;
import com.dmaksimn.store.exception.AppException;
import com.dmaksimn.store.repository.CartRepository;
import com.dmaksimn.store.repository.OrderRepository;
import com.dmaksimn.store.repository.ProductInOrderRepository;
import com.dmaksimn.store.repository.UserRepository;
import com.dmaksimn.store.service.CartService;
import com.dmaksimn.store.service.ProductService;
import com.dmaksimn.store.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    ProductService productService;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ProductInOrderRepository productInOrderRepository;
    @Autowired
    CartRepository cartRepository;
    @Autowired
    UserService userService;

    @Override
    public Cart getCartUser(User user) {
        return user.getCart();
    }

    @Override
    @Transactional
    public void mergeLocalCart(Collection<ProductInOrder> productInOrders, User user) {
        Cart finalCart = user.getCart();
        productInOrders.forEach(productInOrder -> {
            Set<ProductInOrder> set = finalCart.getProducts();
            Optional<ProductInOrder> old = set.stream().filter(e -> e.getId().equals(productInOrder.getProduct_id())).findFirst();
            ProductInOrder prod;
            if (old.isPresent()) {
                prod = old.get();
                prod.setCount(productInOrder.getCount() + prod.getCount());
            } else {
                prod = productInOrder;
                prod.setCart(finalCart);
                finalCart.getProducts().add(prod);
            }
            productInOrderRepository.save(prod);
        });
        cartRepository.save(finalCart);

    }

    @Override
    @Transactional
    public void deleteProductInCart(String itemId, User user) {
        if(itemId.equals("") || user == null) {
            throw new AppException(ResultEnum.ORDER_STATUS_ERROR);
        }
        Optional<ProductInOrder> deleteProduct = user.getCart().getProducts().stream().filter(e -> itemId.equals(e.getProduct_id())).findFirst();
        deleteProduct.ifPresent(productInOrder -> {
            productInOrder.setCart(null);
            productInOrderRepository.deleteById(productInOrder.getId());
        });
    }

    @Override
    @Transactional
    public void checkoutOrder(User user) {

        /**
         * Create new order
          */
        OrderMain order = new OrderMain(user);
        orderRepository.save(order);
        /**
         * clear cart's foreign key & set order's foreign key & decrease stock
         */
        user.getCart().getProducts().forEach(productInOrder -> {
            productInOrder.setCart(null);
            productInOrder.setOrderMain(order);
            productService.decreaseStock(productInOrder.getProduct_id(), productInOrder.getCount());
            productInOrderRepository.save(productInOrder);
        });
    }
}
