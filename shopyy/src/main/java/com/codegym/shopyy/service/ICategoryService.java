package com.codegym.shopyy.service;

import com.codegym.shopyy.dto.request.CategoryRequestDto;
import com.codegym.shopyy.dto.response.ResponsePage;
import com.codegym.shopyy.entities.Category;
import com.codegym.shopyy.entities.SubCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Optional;

public interface ICategoryService {

    Page<Category> findByName(Pageable pageable, String name);

    Iterable<Category> findAll(Pageable pageable);

    Iterable<SubCategory> findSubCategoriesByCategory(Category category);

    Optional<Category> findById(Long id);

    ResponsePage save(CategoryRequestDto categoryRequestDto);

    void deleteById(Long id);

}
