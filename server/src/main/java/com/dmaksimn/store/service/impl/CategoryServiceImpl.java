package com.dmaksimn.store.service.impl;

import com.dmaksimn.store.domain.ProductCategory;
import com.dmaksimn.store.enums.ResultEnum;
import com.dmaksimn.store.exception.AppException;
import com.dmaksimn.store.repository.ProductCategoryRepository;
import com.dmaksimn.store.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    ProductCategoryRepository productCategoryRepository;

    @Override
    public List<ProductCategory> findAll() {
        List<ProductCategory> res = productCategoryRepository.findAllByOrderByType();
//        resultFindAllOrderByCategoryType.sort(Comparator.comparing(ProductCategory::getType));
        return res;
    }

    @Override
    public ProductCategory findByCategoryType(Integer type) {
        ProductCategory res = productCategoryRepository.findByType(type);
        if (res == null) throw new AppException(ResultEnum.CATEGORY_NOT_FOUND);
        return res;
    }

    @Override
    public List<ProductCategory> findByCategoryTypeIn(List<Integer> typeList) {
        List<ProductCategory> res = productCategoryRepository.findByTypeInOrderByTypeAsc(typeList);
//        FindByCategoryTypeIn.sort(Comparator.comparing(ProductCategory::getType));
        return res;
    }

    @Override
    @Transactional
    public ProductCategory save(ProductCategory productCategory) {
        return productCategoryRepository.save(productCategory);
    }
}
