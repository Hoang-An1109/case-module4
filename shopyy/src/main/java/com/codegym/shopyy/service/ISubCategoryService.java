package com.codegym.shopyy.service;

import com.codegym.shopyy.dto.request.SubCategoryRequestDto;
import com.codegym.shopyy.dto.response.ResponsePage;
import com.codegym.shopyy.entities.Product;
import com.codegym.shopyy.entities.SubCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Optional;


public interface ISubCategoryService {

    Page<SubCategory> findByName(Pageable pageable, String name);

    Page<SubCategory> findAll(Pageable pageable);

    Optional<SubCategory> findById(Long id);

    ResponsePage save(SubCategoryRequestDto subCategoryRequestDto);

    void deleteById(Long id);

    Iterable<Product> findProductsBySubCategory(SubCategory subCategory);

}
