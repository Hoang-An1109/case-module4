package com.codegym.shopyy.converter;

import com.codegym.shopyy.dto.request.ProductRequestDto;
import com.codegym.shopyy.entities.Product;

public interface IProductConverter {

    Product dtoToEntity(ProductRequestDto productRequestDto);
}
