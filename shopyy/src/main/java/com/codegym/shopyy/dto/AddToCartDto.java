package com.codegym.shopyy.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddToCartDto {

    @JsonProperty("productId")
    private Long productId;

    @JsonProperty("quantity")
    private Integer quantity;
}
