package com.codegym.shopyy.converter.impl;

import com.codegym.shopyy.converter.ICategoryConverter;
import com.codegym.shopyy.dto.request.CategoryRequestDto;
import com.codegym.shopyy.entities.Category;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CategoryConverterImpl implements ICategoryConverter {

    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryConverterImpl.class);


    @Override
    public Category dtoEntity(CategoryRequestDto categoryRequestDto) {
        LOGGER.debug("CategoryConverterImpl -> dtoToEntity");
        Category category = new Category();
        BeanUtils.copyProperties(categoryRequestDto, category);
        return category;
    }
}
