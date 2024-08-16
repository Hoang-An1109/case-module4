package com.codegym.shopyy.repository;

import com.codegym.shopyy.entities.Category;
import com.codegym.shopyy.entities.SubCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface ISubCategoryRepository extends PagingAndSortingRepository<SubCategory, Long> {

    Page<SubCategory> findByName(Pageable pageable, String name);

    Page<SubCategory> findAllByCategory(Category category, Pageable pageable);

    Optional<SubCategory> findById(Long id);

    SubCategory save(SubCategory subCategory);

    void deleteById(Long id);

    @Modifying
    @Query("DELETE FROM Product p WHERE p.subCategory.id = :subCategoryId")
    void deleteProductsBySubCategoryId(@Param("subCategoryId") Long subCategoryId);

}
