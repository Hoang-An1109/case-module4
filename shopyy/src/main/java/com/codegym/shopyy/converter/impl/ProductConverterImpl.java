package com.codegym.shopyy.converter.impl;

import com.codegym.shopyy.converter.IProductConverter;
import com.codegym.shopyy.dto.request.ProductRequestDto;
import com.codegym.shopyy.entities.Product;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductConverterImpl implements IProductConverter {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductConverterImpl.class);

    @Override
    public Product dtoToEntity(ProductRequestDto productRequestDto) {
        LOGGER.debug("ProductConverterImpl -> dtoToEntity");
        Product product = new Product();
        BeanUtils.copyProperties(productRequestDto, product);
        return product;
    }
}
