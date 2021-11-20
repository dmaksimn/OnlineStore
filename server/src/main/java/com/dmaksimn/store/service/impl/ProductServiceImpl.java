package com.dmaksimn.store.service.impl;

import com.dmaksimn.store.domain.ProductInfo;
import com.dmaksimn.store.enums.ProductStatusEnum;
import com.dmaksimn.store.enums.ResultEnum;
import com.dmaksimn.store.exception.AppException;
import com.dmaksimn.store.repository.ProductInfoRepository;
import com.dmaksimn.store.service.CategoryService;
import com.dmaksimn.store.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductInfoRepository productInfoRepository;
    @Autowired
    CategoryService categoryService;

    @Override
    @Cacheable("products")
    public ProductInfo findById(long id) {
        return productInfoRepository.findById(id);
    }

    @Override
    public Page<ProductInfo> findUpAll(Pageable pageable) {
        return productInfoRepository.findAllByStatusOrderByIdAsc(ProductStatusEnum.UP.getCode(), pageable);
    }

    @Override
    @Cacheable(value = "products")
    public Page<ProductInfo> findAll(Pageable pageable) {
        return productInfoRepository.findAllByOrderById(pageable);
    }

    @Override
    public Page<ProductInfo> findAllInCategory(Integer type, Pageable pageable) {
        return productInfoRepository.findAllByCategoryTypeOrderByIdAsc(type, pageable);
    }

    @Override
    @Transactional
    public void increaseStock(long id, int amount) {
        ProductInfo productInfo = findById(id);
        if (productInfo == null) throw new AppException(ResultEnum.PRODUCT_NOT_EXIST);
        int update = productInfo.getStock() + amount;
        productInfo.setStock(update);
        productInfoRepository.save(productInfo);
    }

    @Override
    @Transactional
    public void decreaseStock(long id, int amount) {
        ProductInfo productInfo = findById(id);
        if (productInfo == null) throw new AppException(ResultEnum.PRODUCT_NOT_EXIST);
        int update = productInfo.getStock() - amount;
        if (update <= 0) throw new AppException(ResultEnum.PRODUCT_NOT_ENOUGH);
        productInfo.setStock(update);
        productInfoRepository.save(productInfo);
    }

    @Override
    @Transactional
    public ProductInfo offSale(long id) {
        ProductInfo productInfo = findById(id);
        if (productInfo == null) throw new AppException(ResultEnum.PRODUCT_NOT_EXIST);
        if (productInfo.getStatus() == ProductStatusEnum.DOWN.getCode()) {
            throw new AppException(ResultEnum.PRODUCT_STATUS_ERROR);
        }
        productInfo.setStatus(ProductStatusEnum.DOWN.getCode());
        return productInfoRepository.save(productInfo);
    }

    @Override
    @Transactional
    public ProductInfo onSale(long id) {
        ProductInfo productInfo = findById(id);
        if (productInfo == null) throw new AppException(ResultEnum.PRODUCT_NOT_EXIST);
        if (productInfo.getStatus() == ProductStatusEnum.UP.getCode()) {
            throw new AppException(ResultEnum.PRODUCT_STATUS_ERROR);
        }
        productInfo.setStatus(ProductStatusEnum.UP.getCode());
        return productInfoRepository.save(productInfo);
    }

    @Override
    public ProductInfo update(ProductInfo productInfo) {
        categoryService.findByCategoryType(productInfo.getCategoryType());
        if (productInfo.getStatus() > 1) {
            throw new AppException(ResultEnum.PRODUCT_STATUS_ERROR);
        }
        return productInfoRepository.save(productInfo);
    }

    @Override
    @CacheEvict(value = "product", allEntries = true)
    public ProductInfo save(ProductInfo productInfo) {
        return update(productInfo);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "product", key = "#id"),
            @CacheEvict(value = "product", key = "'all'")
    })
    public void delete(long id) {
        ProductInfo productInfo = findById(id);
        if (productInfo == null) throw new AppException(ResultEnum.PRODUCT_NOT_EXIST);
        productInfoRepository.delete(productInfo);
    }

    /**
     * Method for test purposes
     */
    @Override
    @CacheEvict(value = "product", allEntries = true)
    public void removeCache() {
    }
}
