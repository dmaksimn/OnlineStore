package com.dmaksimn.store.service.impl;

import com.dmaksimn.store.domain.ProductCategory;
import com.dmaksimn.store.repository.ProductCategoryRepository;
import com.dmaksimn.store.exception.AppException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class CategoryServiceImplTest {

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Mock
    private ProductCategoryRepository productCategoryRepository;

    @Test
    public void findByCategoryTypeTest() {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setId(1L);
        Mockito.when(productCategoryRepository.findByType(Math.toIntExact(productCategory.getId()))).thenReturn(productCategory);
        categoryService.findByCategoryType(Math.toIntExact(productCategory.getId()));
        Mockito.verify(productCategoryRepository, Mockito.times(1)).findByType(Math.toIntExact(productCategory.getId()));
    }

    @Test(expected = AppException.class)
    public void findByCategoryTypeExceptionTest() {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setId(1L);
        Mockito.when(productCategoryRepository.findByType(Math.toIntExact(productCategory.getId()))).thenReturn(null);
        categoryService.findByCategoryType(Math.toIntExact(productCategory.getId()));
    }
}
