package com.codegym.shopyy.converter;

import com.codegym.shopyy.dto.request.CategoryRequestDto;
import com.codegym.shopyy.entities.Category;

public interface ICategoryConverter {

    Category dtoEntity(CategoryRequestDto categoryRequestDto);
}
