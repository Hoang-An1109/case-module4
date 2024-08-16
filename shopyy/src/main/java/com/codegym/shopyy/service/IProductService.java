package com.codegym.shopyy.service;

import com.codegym.shopyy.dto.request.ProductRequestDto;
import com.codegym.shopyy.dto.response.ResponsePage;
import com.codegym.shopyy.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.Optional;

public interface IProductService {

    Page<Product> findByName(Pageable pageable, String keyword);

    Page<Product> findAll(Pageable pageable);

    Page<Product> findAllByOrderByPriceAsc(Pageable pageable);

    Page<Product> findAllByOrderByPriceDesc(Pageable pageable);

    Page<Product> findAllByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable);

    Optional<Product> findById(Long id);

    ResponsePage save(ProductRequestDto productRequestDto);

    void deleteById(Long id);

    Page<Product> searchProducts(String keyword, Pageable pageable);


}
