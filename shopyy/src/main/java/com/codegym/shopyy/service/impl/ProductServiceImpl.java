package com.codegym.shopyy.service.impl;

import com.codegym.shopyy.converter.IProductConverter;
import com.codegym.shopyy.dto.request.ProductRequestDto;
import com.codegym.shopyy.dto.response.ResponsePage;
import com.codegym.shopyy.entities.Product;
import com.codegym.shopyy.repository.IProductRepository;
import com.codegym.shopyy.service.IProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class ProductServiceImpl implements IProductService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Autowired
    private IProductConverter productConverter;

    @Autowired
    private IProductRepository productRepository;

    @Override
    public Page<Product> findByName(Pageable pageable, String keyword) {
        return productRepository.findByName(keyword, pageable);
    }

    @Override
    public Page<Product> findAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public Page<Product> findAllByOrderByPriceAsc(Pageable pageable) {
        return productRepository.findAllByOrderByPriceAsc(pageable);
    }

    @Override
    public Page<Product> findAllByOrderByPriceDesc(Pageable pageable) {
        return productRepository.findAllByOrderByPriceDesc(pageable);
    }

    @Override
    public Page<Product> findAllByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable) {
        return productRepository.findAllByPriceBetween(minPrice, maxPrice, pageable);
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }


    @Override
    public ResponsePage save(ProductRequestDto productRequestDto) {

        LOGGER.info("ComputerServiceImpl -> save invoked!!!");
        Product product = productConverter.dtoToEntity(productRequestDto);

        try {
            productRepository.save(product);
            return ResponsePage.builder()
                    .data(null)
                    .message("product created successfully")
                    .status(HttpStatus.OK)
                    .build();
        } catch (Exception e) {
            return ResponsePage.builder()
                    .data(null)
                    .message(e.getMessage())
                    .status(HttpStatus.BAD_REQUEST)
                    .build();
        }
    }


    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }


//    public Page<Product> findBySubCategoryName(String subCategoryName, Pageable pageable) {
//        return productRepository.findBySubCategoryName(subCategoryName, pageable);
//    }

    @Override
    public Page<Product> searchProducts(String keyword, Pageable pageable) {
        return productRepository.searchProducts(keyword, pageable);
    }
}
