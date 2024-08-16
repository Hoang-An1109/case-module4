package com.codegym.shopyy.service.impl;

import com.codegym.shopyy.converter.ICategoryConverter;
import com.codegym.shopyy.dto.request.CategoryRequestDto;
import com.codegym.shopyy.dto.response.ResponsePage;
import com.codegym.shopyy.entities.Category;
import com.codegym.shopyy.entities.SubCategory;
import com.codegym.shopyy.repository.ICategoryRepository;
import com.codegym.shopyy.repository.ISubCategoryRepository;
import com.codegym.shopyy.service.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements ICategoryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryServiceImpl.class);

    @Autowired
    private ICategoryConverter categoryConverter;

    @Autowired
    private ICategoryRepository categoryRepository;

    @Autowired
    private ISubCategoryRepository subCategoryRepository;

    @Override
    public Page<Category> findByName(Pageable pageable, String name) {
        return categoryRepository.findByName(name, pageable );
    }

    @Override
    public Iterable<Category> findAll(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    @Override
    public Iterable<SubCategory> findSubCategoriesByCategory(Category category) {
        return subCategoryRepository.findAllByCategory(category, PageRequest.of(0, Integer.MAX_VALUE)).getContent();
    }

    @Override
    public Optional<Category> findById(Long id) {
        return categoryRepository.findById(id);
    }

    @Override
    public ResponsePage save(CategoryRequestDto categoryRequestDto) {
        LOGGER.info("CategoryServiceImpl -> save invoked!!! ");
        Category category = categoryConverter.dtoEntity(categoryRequestDto);
        try {
            categoryRepository.save(category);
            return ResponsePage.builder()
                    .data(null)
                    .message("category created successfully")
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
    @Transactional
    public void deleteById(Long id) {
        categoryRepository.deleteSubCategoriesByCategoryId(id);
        categoryRepository.deleteById(id);
    }

}
