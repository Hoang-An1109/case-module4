package com.codegym.shopyy.dto.request;

import com.codegym.shopyy.entities.Category;
import com.codegym.shopyy.entities.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubCategoryRequestDto {

    private Long id;

    private String name;

    private Long category;

    private List<Product> products;
}
