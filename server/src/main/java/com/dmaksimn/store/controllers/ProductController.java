package com.dmaksimn.store.controllers;

import com.dmaksimn.store.domain.ProductInfo;
import com.dmaksimn.store.service.CategoryService;
import com.dmaksimn.store.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin
@RestController
public class ProductController {
    @Autowired
    CategoryService categoryService;
    @Autowired
    ProductService productService;

    /**
     * Show All Categories
     */
    @GetMapping("/product")
    public Page<ProductInfo> findAllProduct(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                            @RequestParam(value = "size", defaultValue = "3") Integer size) {
        PageRequest request = PageRequest.of(page - 1, size);
        return productService.findAll(request);
    }

    @GetMapping("/product/{id}")
    public ProductInfo showOneProduct(@PathVariable("id") long id) {
        return productService.findById(id);
    }

    @PostMapping("/seller/product/new")
    private ResponseEntity createNewProduct(@Valid @RequestBody ProductInfo product,
                                            BindingResult bindingResult) {
        ProductInfo productIdExists = productService.findById(product.getId());
        if (productIdExists != null) {
            bindingResult
                    .rejectValue("productId", "error.product",
                            "There is already a product with the code provided");
        }
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult);
        }
        return ResponseEntity.ok(productService.save(product));
    }

    @PutMapping("/seller/product/{id}/edit")
    public ResponseEntity editProduct(@PathVariable("id") int id,
                                      @Valid @RequestBody ProductInfo product,
                                      BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult);
        }
        if (id!=(product.getId())) {
            return ResponseEntity.badRequest().body("Id Not Matched");
        }
        return ResponseEntity.ok(productService.update(product));
    }

    @DeleteMapping("/seller/product/{id}/delete")
    public ResponseEntity deleteProduct(@PathVariable("id") long id) {
        productService.delete(id);
        return ResponseEntity.ok().build();
    }
}
