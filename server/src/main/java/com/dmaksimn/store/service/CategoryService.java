package com.dmaksimn.store.service;

import com.dmaksimn.store.domain.ProductCategory;
import java.util.List;

public interface CategoryService {
    List<ProductCategory> findAll();
    ProductCategory findByCategoryType(Integer type);
    List<ProductCategory> findByCategoryTypeIn(List<Integer> typeList);
    ProductCategory save(ProductCategory productCategory);
}
