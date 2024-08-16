package com.codegym.shopyy.converter;

import com.codegym.shopyy.dto.request.SubCategoryRequestDto;
import com.codegym.shopyy.entities.SubCategory;

public interface ISubCategoryConverter {

    SubCategory dtoToEntity(SubCategoryRequestDto subCategoryRequestDto);
}
