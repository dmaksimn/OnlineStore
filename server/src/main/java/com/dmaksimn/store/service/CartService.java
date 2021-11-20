package com.dmaksimn.store.service;

import com.dmaksimn.store.domain.Cart;
import com.dmaksimn.store.domain.ProductInOrder;
import com.dmaksimn.store.domain.User;

import java.util.Collection;

public interface CartService {
    Cart getCartUser(User user);
    void mergeLocalCart(Collection<ProductInOrder> productInOrders, User user);
    void deleteProductInCart(String itemId, User user);
    void checkoutOrder(User user);
}
