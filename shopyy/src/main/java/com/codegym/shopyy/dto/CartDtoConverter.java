package com.codegym.shopyy.dto;

import com.codegym.shopyy.entities.Cart;
import com.codegym.shopyy.entities.CartItem;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class CartDtoConverter {

    public CartDto convertToDto(Cart cart) {
        CartDto dto = new CartDto();
        dto.setId(cart.getId());
        dto.setItems(cart.getCartItems().stream()
                .map(this::convertToCartItemDto)
                .collect(Collectors.toList()));
        return dto;
    }

    public CartItemDto convertToCartItemDto(CartItem item) {
        CartItemDto dto = new CartItemDto();
        dto.setProductId(item.getProduct().getId());
        dto.setProductName(item.getProduct().getName());
        dto.setStockQuantity(item.getQuantity());
        dto.setPrice(item.getProduct().getPrice());
        dto.setImageUrl(item.getProduct().getImg());
        return dto;
    }
}
