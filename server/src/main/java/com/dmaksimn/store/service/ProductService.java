package com.dmaksimn.store.service;

import com.dmaksimn.store.domain.ProductInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    ProductInfo findById(long id);
    /**
     *  All selling products
     */
    Page<ProductInfo> findUpAll(Pageable pageable);
    /**
     *  All products
     */
    Page<ProductInfo> findAll(Pageable pageable);
    /**
     * All products in a category
     */
    Page<ProductInfo> findAllInCategory(Integer type, Pageable pageable);
    /**
     *  Increase stock
     */
    void increaseStock(long id, int amount);
    /**
     *  Decrease stock
     */
    void decreaseStock(long id, int amount);

    ProductInfo offSale(long id);
    ProductInfo onSale(long id);
    ProductInfo update(ProductInfo productInfo);
    ProductInfo save(ProductInfo productInfo);
    void delete(long id);

    void removeCache();
}
