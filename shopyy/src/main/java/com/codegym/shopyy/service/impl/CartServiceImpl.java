package com.codegym.shopyy.service.impl;

import com.codegym.shopyy.dto.AddToCartDto;
import com.codegym.shopyy.dto.CartDto;
import com.codegym.shopyy.dto.CartDtoConverter;
import com.codegym.shopyy.entities.Cart;
import com.codegym.shopyy.entities.CartItem;
import com.codegym.shopyy.entities.Product;
import com.codegym.shopyy.entities.User;
import com.codegym.shopyy.repository.CartRepository;
import com.codegym.shopyy.repository.IProductRepository;
import com.codegym.shopyy.repository.IUserRepository;
import com.codegym.shopyy.service.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
public class CartServiceImpl implements ICartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private IProductRepository productRepository;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private CartDtoConverter cartDtoConverter;

    @Override
    @Transactional
    public CartDto addToCart(User user, AddToCartDto addToCartDto) {
        Product product = productRepository.findById(addToCartDto.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (addToCartDto.getQuantity() <= 0) {
            throw new RuntimeException("Quantity must be greater than 0");
        }

        if (addToCartDto.getQuantity() > product.getQuantity()) {
            throw new RuntimeException("Quantity must be less than or equal to product's quantity");
        }

        Cart cart = user.getCart();
        if (cart == null) {
            cart = new Cart();
            cart.setUser(user);
            cart = cartRepository.save(cart);
            user.setCart(cart);
            userRepository.save(user);
        }

        CartItem cartItem = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getId().equals(product.getId()))
                .findFirst()
                .orElse(null);

        int quantityToAdd = addToCartDto.getQuantity();

        if (cartItem == null) {
            cartItem = new CartItem();
            cartItem.setProduct(product);
            cartItem.setQuantity(addToCartDto.getQuantity());
            cart.addCartItem(cartItem);
        } else {
            int newQuantity = cartItem.getQuantity() + addToCartDto.getQuantity();
            if (newQuantity > product.getQuantity()) {
                throw new RuntimeException("Quantity must be less than or equal to product's quantity");
            }
            cartItem.setQuantity(newQuantity);
        }

        product.setQuantity(product.getQuantity() - quantityToAdd);
        productRepository.save(product);

        Cart savedCart = cartRepository.save(cart);
        return cartDtoConverter.convertToDto(savedCart);
    }

    @Override
    public CartDto getCartForUser(User user) {
        Cart cart = user.getCart();
        if (cart == null) {
            cart = new Cart();
            cart.setUser(user);
            user.setCart(cart);
            cart = cartRepository.save(cart);
        }
        return cartDtoConverter.convertToDto(cart);
    }

    @Override
    @Transactional
    public CartDto updateCartItemQuantity(User user, AddToCartDto updateCartDto) {
        Cart cart = user.getCart();
        if (cart == null) {
            throw new RuntimeException("Cart not found for user");
        }

        Product product = productRepository.findById(updateCartDto.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        CartItem cartItem = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getId().equals(product.getId()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Product not found in cart"));

        int quantityDifference = updateCartDto.getQuantity() - cartItem.getQuantity();

        int availableStockQuantity = product.getQuantity() + cartItem.getQuantity();
        if (updateCartDto.getQuantity() > availableStockQuantity) {
            throw new RuntimeException("Not enough stock available. Maximum available quantity is " + availableStockQuantity);
        }

        cartItem.setQuantity(updateCartDto.getQuantity());
        product.setQuantity(product.getQuantity() - quantityDifference);

        productRepository.save(product);
        Cart savedCart = cartRepository.save(cart);
        return cartDtoConverter.convertToDto(savedCart);
    }

    @Override
    @Transactional
    public CartDto removeCartItem(User user, Long productId) {
        Cart cart = user.getCart();
        if (cart == null) {
            throw new RuntimeException("Cart not found for user");
        }

        CartItem cartItem = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Product not found in cart"));

        Product product = cartItem.getProduct();
        product.setQuantity(product.getQuantity() + cartItem.getQuantity());
        productRepository.save(product);

        cart.getCartItems().remove(cartItem);
        Cart savedCart = cartRepository.save(cart);
        return cartDtoConverter.convertToDto(savedCart);
    }

    @Override
    @Transactional
    public CartDto removeAllCartItems(User user) {
        Cart cart = user.getCart();
        if (cart == null) {
            throw new RuntimeException("Cart not found for user");
        }

        for (CartItem cartItem : new ArrayList<>(cart.getCartItems())) {
            Product product = cartItem.getProduct();
            product.setQuantity(product.getQuantity() + cartItem.getQuantity());
            productRepository.save(product);
        }

        cart.getCartItems().clear();
        Cart savedCart = cartRepository.save(cart);
        return cartDtoConverter.convertToDto(savedCart);
    }
}
