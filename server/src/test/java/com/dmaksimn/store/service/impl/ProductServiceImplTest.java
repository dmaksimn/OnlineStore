package com.dmaksimn.store.service.impl;

import com.dmaksimn.store.domain.ProductInfo;
import com.dmaksimn.store.enums.ProductStatusEnum;
import com.dmaksimn.store.repository.ProductInfoRepository;
import com.dmaksimn.store.exception.AppException;
import com.dmaksimn.store.service.CategoryService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class ProductServiceImplTest {

    @InjectMocks
    private ProductServiceImpl productService;

    @Mock
    private ProductInfoRepository productInfoRepository;

    @Mock
    private CategoryService categoryService;

    private ProductInfo productInfo;

    @Before
    public void setUp() {
        productInfo = new ProductInfo();
        productInfo.setId(1L);
        productInfo.setStock(10);
        productInfo.setStatus(1);
    }

    @Test
    public void increaseStockTest() {
        when(productInfoRepository.findById(productInfo.getId())).thenReturn(java.util.Optional.ofNullable(productInfo));
        productService.increaseStock(1, 10);
        Mockito.verify(productInfoRepository, Mockito.times(1)).save(productInfo);
    }

    @Test(expected = AppException.class)
    public void increaseStockExceptionTest() {
        productService.increaseStock(1, 10);
    }

    @Test
    public void decreaseStockTest() {
        when(productInfoRepository.findById(productInfo.getId())).thenReturn(java.util.Optional.ofNullable(productInfo));
        productService.decreaseStock(1, 9);
        Mockito.verify(productInfoRepository, Mockito.times(1)).save(productInfo);
    }

    @Test(expected = AppException.class)
    public void decreaseStockValueLesserEqualTest() {
        when(productInfoRepository.findById(productInfo.getId())).thenReturn(java.util.Optional.ofNullable(productInfo));
        productService.decreaseStock(1, 10);
    }

    @Test(expected = AppException.class)
    public void decreaseStockExceptionTest() {
        productService.decreaseStock(1, 10);
    }

    @Test
    public void offSaleTest() {
        productInfo.setStatus(ProductStatusEnum.UP.getCode());
        when(productInfoRepository.findById(productInfo.getId())).thenReturn(java.util.Optional.ofNullable(productInfo));
        productService.offSale(1);
        Mockito.verify(productInfoRepository, Mockito.times(1)).save(productInfo);
    }

    @Test(expected = AppException.class)
    public void offSaleStatusDownTest() {
        productInfo.setStatus(ProductStatusEnum.DOWN.getCode());
        when(productInfoRepository.findById(productInfo.getId())).thenReturn(java.util.Optional.ofNullable(productInfo));
        productService.offSale(1);
    }

    @Test(expected = AppException.class)
    public void offSaleProductNullTest() {
        when(productInfoRepository.findById(productInfo.getId())).thenReturn(null);
        productService.offSale(1);
    }

    @Test
    public void onSaleTest() {
        productInfo.setStatus(ProductStatusEnum.DOWN.getCode());
        when(productInfoRepository.findById(productInfo.getId())).thenReturn(java.util.Optional.ofNullable(productInfo));
        productService.onSale(1);
        Mockito.verify(productInfoRepository, Mockito.times(1)).save(productInfo);
    }

    @Test(expected = AppException.class)
    public void onSaleStatusUpTest() {
        productInfo.setStatus(ProductStatusEnum.UP.getCode());
        when(productInfoRepository.findById(productInfo.getId())).thenReturn(java.util.Optional.ofNullable(productInfo));
        productService.onSale(1);
    }

    @Test(expected = AppException.class)
    public void onSaleProductNullTest() {
        when(productInfoRepository.findById(productInfo.getId())).thenReturn(null);
        productService.offSale(1);
    }

    @Test
    public void updateTest() {
        productService.update(productInfo);
        Mockito.verify(productInfoRepository, Mockito.times(1)).save(productInfo);
    }

    @Test(expected = AppException.class)
    public void updateProductStatusBiggerThenOneTest() {
        productInfo.setStatus(2);
        productService.update(productInfo);
    }

    @Test
    public void deleteTest() {
        when(productInfoRepository.findById(productInfo.getId())).thenReturn(java.util.Optional.ofNullable(productInfo));
        productService.delete(1);
        Mockito.verify(productInfoRepository, Mockito.times(1)).delete(productInfo);
    }

    @Test(expected = AppException.class)
    public void deleteProductNullTest() {
        productService.delete(1);
    }
}
