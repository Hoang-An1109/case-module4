package com.codegym.shopyy.dto.request;

import com.codegym.shopyy.entities.SubCategory;
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
public class CategoryRequestDto {

    private Long id;

    private String name;

    private List<SubCategory> subCategories;


}
