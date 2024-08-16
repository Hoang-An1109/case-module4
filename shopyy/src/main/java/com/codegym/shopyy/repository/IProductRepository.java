package com.codegym.shopyy.repository;


import com.codegym.shopyy.entities.Product;
import com.codegym.shopyy.entities.SubCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface IProductRepository extends PagingAndSortingRepository<Product, Long> {

    Page<Product> findByName(String name, Pageable pageable);

    Page<Product> findAllBySubCategory(SubCategory subCategory, Pageable pageable);

    Page<Product> findAllByOrderByPriceAsc(Pageable pageable);

    Page<Product> findAllByOrderByPriceDesc(Pageable pageable);

    Page<Product> findAllByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable);

    Optional<Product> findById(Long id);

    Product save(Product product);

    void deleteById(Long id);


    @Query("SELECT p FROM Product p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
                "OR LOWER(p.description) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(p.subCategory.name) LIKE LOWER(CONCAT('%', :keyword, '%'))")
        Page<Product> searchProducts(@Param("keyword") String keyword, Pageable pageable);

}
