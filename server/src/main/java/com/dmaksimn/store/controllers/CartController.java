package com.dmaksimn.store.controllers;

import com.dmaksimn.store.domain.Cart;
import com.dmaksimn.store.domain.ProductInOrder;
import com.dmaksimn.store.domain.User;
import com.dmaksimn.store.form.ItemForm;
import com.dmaksimn.store.repository.ProductInOrderRepository;
import com.dmaksimn.store.service.CartService;
import com.dmaksimn.store.service.ProductInOrderService;
import com.dmaksimn.store.service.ProductService;
import com.dmaksimn.store.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collection;
import java.util.Collections;

@CrossOrigin
@RestController
@RequestMapping("/cart")
public class CartController {
    CartService cartService;
    UserService userService;
    ProductService productService;
    ProductInOrderService productInOrderService;
    ProductInOrderRepository productInOrderRepository;

    @PostMapping("")
    public ResponseEntity<Cart> mergeCart(@RequestBody Collection<ProductInOrder> productInOrders, Principal principal) {
        User user = userService.findOneUserByEmail(principal.getName());
        try {
            cartService.mergeLocalCart(productInOrders, user);
        } catch (Exception e) {
            ResponseEntity.badRequest().body("Merge Cart Failed");
        }
        return ResponseEntity.ok(cartService.getCartUser(user));
    }

    @GetMapping("")
    public Cart getCart(Principal principal) {
        User user = userService.findOneUserByEmail(principal.getName());
        return cartService.getCartUser(user);
    }

    @PostMapping("/add")
    public boolean addToCart(@RequestBody ItemForm form, Principal principal) {
        var productInfo = productService.findById(form.getId());
        try {
            mergeCart(Collections.singleton(new ProductInOrder(productInfo, form.getQuantity())), principal);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @PutMapping("/{itemId}")
    public ProductInOrder modifyItem(@PathVariable("itemId") String itemId, @RequestBody Integer quantity, Principal principal) {
        User user = userService.findOneUserByEmail(principal.getName());
         productInOrderService.update(itemId, quantity, user);
        return productInOrderService.findOne(itemId, user);
    }

    @DeleteMapping("/{itemId}")
    public void deleteItem(@PathVariable("itemId") String itemId, Principal principal) {
        User user = userService.findOneUserByEmail(principal.getName());
         cartService.deleteProductInCart(itemId, user);
         // flush memory into DB
    }

    @PostMapping("/checkout")
    public ResponseEntity checkoutProduct(Principal principal) {
        User user = userService.findOneUserByEmail(principal.getName());
        cartService.checkoutOrder(user);
        return ResponseEntity.ok(null);
    }
}
