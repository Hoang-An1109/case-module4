package com.codegym.shopyy.service.impl;

import com.codegym.shopyy.converter.ISubCategoryConverter;
import com.codegym.shopyy.dto.request.SubCategoryRequestDto;
import com.codegym.shopyy.dto.response.ResponsePage;
import com.codegym.shopyy.entities.Product;
import com.codegym.shopyy.entities.SubCategory;
import com.codegym.shopyy.repository.IProductRepository;
import com.codegym.shopyy.repository.ISubCategoryRepository;
import com.codegym.shopyy.service.ISubCategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;


@Service
public class SubCategoryServiceImpl implements ISubCategoryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SubCategoryServiceImpl.class);

    @Autowired
    private ISubCategoryRepository subCategoryRepository;

    @Autowired
    private IProductRepository productRepository;

    @Autowired
    private ISubCategoryConverter subCategoryConverter;

    @Override
    public Page<SubCategory> findByName(Pageable pageable, String name) {
        return subCategoryRepository.findByName(pageable, name);
    }

    @Override
    public Page<SubCategory> findAll(Pageable pageable) {
        return subCategoryRepository.findAll(pageable);
    }


    @Override
    public Optional<SubCategory> findById(Long id) {
        return subCategoryRepository.findById(id);
    }



    @Override
    public ResponsePage save(SubCategoryRequestDto subCategoryRequestDto) {

        LOGGER.info("SubCategoryServiceImpl -> save invoked!!!");
        SubCategory subCategory = subCategoryConverter.dtoToEntity(subCategoryRequestDto);
        try {
            subCategoryRepository.save(subCategory);
            return ResponsePage.builder()
                    .data(null)
                    .message("subcategory create successfully")
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
        subCategoryRepository.deleteProductsBySubCategoryId(id);
        subCategoryRepository.deleteById(id);
    }

    @Override
    public Iterable<Product> findProductsBySubCategory(SubCategory subCategory) {
        return productRepository.findAllBySubCategory(subCategory, PageRequest.of(0, Integer.MAX_VALUE)).getContent();
    }

}
