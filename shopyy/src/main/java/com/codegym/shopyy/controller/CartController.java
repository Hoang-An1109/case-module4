package com.codegym.shopyy.controller;

import com.codegym.shopyy.dto.AddToCartDto;
import com.codegym.shopyy.dto.CartDto;
import com.codegym.shopyy.dto.CartDtoConverter;
import com.codegym.shopyy.entities.User;
import com.codegym.shopyy.service.ICartService;
import com.codegym.shopyy.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin("*")
public class CartController {

    @Autowired
    private ICartService cartService;

    @Autowired
    private IUserService userService;

    @Autowired
    private CartDtoConverter cartDtoConverter;

    @PostMapping("/add")
    public ResponseEntity<?> addToCart(@RequestBody AddToCartDto addToCartDto) {
        try {
            User user = userService.getCurrentUser();
            CartDto updateCart = cartService.addToCart(user, addToCartDto);
            return ResponseEntity.ok(updateCart);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getCart() {
        try {
            User user = userService.getCurrentUser();
            CartDto cartDto = cartService.getCartForUser(user);
            return ResponseEntity.ok(cartDto);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateCartItem(@RequestBody AddToCartDto updatedCartDto) {
        try {
            User user = userService.getCurrentUser();
            CartDto updateCart = cartService.updateCartItemQuantity(user, updatedCartDto);
            return ResponseEntity.ok(updateCart);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/remove")
    public ResponseEntity<?> removeCartItem (@RequestBody AddToCartDto removeCartItemDto) {
        try {
            User user = userService.getCurrentUser();
            CartDto updateCart = cartService.removeCartItem(user, removeCartItemDto.getProductId());
            return ResponseEntity.ok(updateCart);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/remove-all")
    public ResponseEntity<?> removeAllCartItems() {
        try {
            User user = userService.getCurrentUser();
            CartDto updateCart = cartService.removeAllCartItems(user);
            return ResponseEntity.ok(updateCart);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
