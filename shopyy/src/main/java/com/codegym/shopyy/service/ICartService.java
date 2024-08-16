package com.codegym.shopyy.service;

import com.codegym.shopyy.dto.AddToCartDto;
import com.codegym.shopyy.dto.CartDto;
import com.codegym.shopyy.entities.User;

public interface ICartService {
    CartDto addToCart(User user, AddToCartDto addToCartDto);
    CartDto getCartForUser(User user);
    CartDto updateCartItemQuantity(User user, AddToCartDto updateCartDto);
    CartDto removeCartItem(User user, Long productId);
    CartDto removeAllCartItems(User user);
}
