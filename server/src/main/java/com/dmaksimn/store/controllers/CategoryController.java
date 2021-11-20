package com.dmaksimn.store.controllers;

import com.dmaksimn.store.domain.ProductCategory;
import com.dmaksimn.store.domain.ProductInfo;
import com.dmaksimn.store.httpservice.response.CategoryPage;
import com.dmaksimn.store.service.CategoryService;
import com.dmaksimn.store.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @Autowired
    ProductService productService;

    /**
     * Show products in category
     * @param type
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/category/{type}")
    public CategoryPage showOneCategoryType(@PathVariable("type") Integer type,
                                             @RequestParam(value = "page", defaultValue = "1") Integer page,
                                             @RequestParam(value = "size", defaultValue = "3") Integer size) {

        ProductCategory cat = categoryService.findByCategoryType(type);
        PageRequest request = PageRequest.of(page - 1, size);
        Page<ProductInfo> productInCategory = productService.findAllInCategory(type, request);
        var tmp = new CategoryPage("", productInCategory);
        tmp.setCategory(cat.getName());
        return tmp;
    }
}
