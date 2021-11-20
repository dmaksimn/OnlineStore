package com.dmaksimn.store.repository;

import com.dmaksimn.store.domain.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer> {
    /**
     * Find some category
     */
    List<ProductCategory> findByTypeInOrderByTypeAsc(List<Integer> categoryTypes);
    /**
     *  Find all category
     */
    List<ProductCategory> findAllByOrderByType();
    /**
     * Find one category
      */
    ProductCategory findByType(Integer categoryType);
}
