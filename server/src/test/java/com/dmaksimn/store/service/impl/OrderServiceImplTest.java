package com.dmaksimn.store.service.impl;

import com.dmaksimn.store.domain.OrderMain;
import com.dmaksimn.store.domain.ProductInOrder;
import com.dmaksimn.store.domain.ProductInfo;
import com.dmaksimn.store.enums.OrderStatusEnum;
import com.dmaksimn.store.repository.OrderRepository;
import com.dmaksimn.store.repository.ProductInfoRepository;
import com.dmaksimn.store.service.ProductService;
import com.dmaksimn.store.exception.AppException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.HashSet;
import java.util.Set;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductInfoRepository productInfoRepository;

    @Mock
    private ProductService productService;

    @InjectMocks
    private OrderServiceImpl orderService;
    private OrderMain orderMain;
    private ProductInfo productInfo;

    @Before
    public void setUp() {
        orderMain = new OrderMain();
        orderMain.setId(1L);
        orderMain.setStatus(OrderStatusEnum.NEW.getCode());
        ProductInOrder productInOrder = new ProductInOrder();
        productInOrder.setId(1L);
        productInOrder.setCount(10);
        Set<ProductInOrder> set = new HashSet<>();
        set.add(productInOrder);
        orderMain.setProducts(set);
        productInfo = new ProductInfo();
        productInfo.setStock(10);
    }

    @Test
    public void finishSuccessTest() {
        when(orderRepository.findById(orderMain.getId())).thenReturn(orderMain);
        OrderMain orderMainReturn = orderService.finish(orderMain.getId());
        assertThat(orderMainReturn.getId(), is(orderMain.getId()));
        assertThat(orderMainReturn.getStatus(), is(OrderStatusEnum.FINISHED.getCode()));
    }

    @Test(expected = AppException.class)
    public void finishStatusCanceledTest() {
        orderMain.setStatus(OrderStatusEnum.CANCELED.getCode());
        when(orderRepository.findById(orderMain.getId())).thenReturn(orderMain);
        OrderMain orderMainReturn = orderService.finish(orderMain.getId());
        assertThat(orderMainReturn.getId(), is(orderMain.getId()));
        assertThat(orderMainReturn.getStatus(), is(OrderStatusEnum.FINISHED.getCode()));
    }

    @Test(expected = AppException.class)
    public void finishStatusFinishedTest() {
        orderMain.setStatus(OrderStatusEnum.FINISHED.getCode());
        when(orderRepository.findById(orderMain.getId())).thenReturn(orderMain);
        OrderMain orderMainReturn = orderService.finish(orderMain.getId());
        assertThat(orderMainReturn.getId(), is(orderMain.getId()));
        assertThat(orderMainReturn.getStatus(), is(OrderStatusEnum.FINISHED.getCode()));
    }

    @Test
    public void cancelSuccessTest() {
        when(orderRepository.findById(orderMain.getId())).thenReturn(orderMain);
        when(productInfoRepository.findById(orderMain.getProducts().iterator().next().getOrderMain().getId())).thenReturn(java.util.Optional.ofNullable(productInfo));
        OrderMain orderMainReturn = orderService.cancel(orderMain.getId());
        assertThat(orderMainReturn.getId(), is(orderMain.getId()));
        assertThat(orderMainReturn.getStatus(), is(OrderStatusEnum.CANCELED.getCode()));
        assertThat(orderMainReturn.getProducts().iterator().next().getCount(), is(10));
    }

    @Test
    public void cancelNoProduct() {
        when(orderRepository.findById(orderMain.getId())).thenReturn(orderMain);
        orderMain.setProducts(new HashSet<>());
        OrderMain orderMainReturn = orderService.cancel(orderMain.getId());
        assertThat(orderMainReturn.getId(), is(orderMain.getId()));
        assertThat(orderMainReturn.getStatus(), is(OrderStatusEnum.CANCELED.getCode()));
    }

    @Test(expected = AppException.class)
    public void cancelStatusCanceledTest() {
        orderMain.setStatus(OrderStatusEnum.CANCELED.getCode());
        when(orderRepository.findById(orderMain.getId())).thenReturn(orderMain);
        orderService.cancel(orderMain.getId());
    }

    @Test(expected = AppException.class)
    public void cancelStatusFinishTest() {
        orderMain.setStatus(OrderStatusEnum.FINISHED.getCode());
        when(orderRepository.findById(orderMain.getId())).thenReturn(orderMain);
        orderService.cancel(orderMain.getId());
    }
}
