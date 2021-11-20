package com.dmaksimn.store.repository;

import com.dmaksimn.store.domain.ProductInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductInfoRepository extends JpaRepository<ProductInfo, Long> {

    ProductInfo findById(long id);
    /**
     * Find onsale product
     */
    Page<ProductInfo> findAllByStatusOrderByIdAsc(Integer status, Pageable pageable);
    /**
     * Find product in one category
      */
    Page<ProductInfo> findAllByCategoryTypeOrderByIdAsc(Integer type, Pageable pageable);

    Page<ProductInfo> findAllByOrderById(Pageable pageable);
}
