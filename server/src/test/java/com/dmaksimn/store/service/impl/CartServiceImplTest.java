package com.dmaksimn.store.service.impl;

import com.dmaksimn.store.domain.Cart;
import com.dmaksimn.store.domain.ProductInOrder;
import com.dmaksimn.store.domain.User;
import com.dmaksimn.store.exception.AppException;
import com.dmaksimn.store.repository.CartRepository;
import com.dmaksimn.store.repository.OrderRepository;
import com.dmaksimn.store.repository.ProductInOrderRepository;
import com.dmaksimn.store.service.ProductService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.Set;

@RunWith(SpringRunner.class)
public class CartServiceImplTest {

    @InjectMocks
    private CartServiceImpl cartService;

    @Mock
    private ProductService productService;

    @Mock
    private ProductInOrderRepository productInOrderRepository;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private OrderRepository orderRepository;
    private User user;
    private ProductInOrder productInOrder;
    private Set<ProductInOrder> set;
    private Cart cart;

    @Before
    public void setUp() {
        user = new User();
        cart = new Cart();
        user.setEmail("email@email.com");
        user.setName("Name");
        user.setPhone(123);
        user.setAddress("Address Test");
        productInOrder = new ProductInOrder();
        productInOrder.setId(1L);
        productInOrder.setCount(10);
        set = new HashSet<>();
        set.add(productInOrder);
        cart.setProducts(set);
        user.setCart(cart);
    }

    @Test
    public void mergeLocalCartTest() {
        cartService.mergeLocalCart(set, user);
        Mockito.verify(cartRepository, Mockito.times(1)).save(cart);
        Mockito.verify(productInOrderRepository, Mockito.times(1)).save(productInOrder);
    }

    @Test
    public void mergeLocalCartTwoProductTest() {
        ProductInOrder productInOrder2 = new ProductInOrder();
        productInOrder2.setId(1L);
        productInOrder2.setCount(10);
        user.getCart().getProducts().add(productInOrder2);
        cartService.mergeLocalCart(set, user);
        Mockito.verify(cartRepository, Mockito.times(1)).save(cart);
        Mockito.verify(productInOrderRepository, Mockito.times(1)).save(productInOrder);
        Mockito.verify(productInOrderRepository, Mockito.times(1)).save(productInOrder2);
    }

    @Test
    public void mergeLocalCartNoProductTest() {
        user.getCart().setProducts(new HashSet<>());
        cartService.mergeLocalCart(set, user);
        Mockito.verify(cartRepository, Mockito.times(1)).save(cart);
        Mockito.verify(productInOrderRepository, Mockito.times(1)).save(productInOrder);
    }

    @Test
    public void deleteTest() {
        cartService.deleteProductInCart("1", user);
        Mockito.verify(productInOrderRepository, Mockito.times(1)).deleteById(productInOrder.getId());
    }

    @Test(expected = AppException.class)
    public void deleteNoProductTest() {
        cartService.deleteProductInCart("", user);
    }

    @Test(expected = AppException.class)
    public void deleteNoUserTest() {
        cartService.deleteProductInCart("1", null);
    }

    @Test
    public void checkoutTest() {
        cartService.checkoutOrder(user);
        Mockito.verify(productInOrderRepository, Mockito.times(1)).save(productInOrder);
    }
}
