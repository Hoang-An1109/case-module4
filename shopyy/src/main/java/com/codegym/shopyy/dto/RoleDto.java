package com.codegym.shopyy.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoleDto {

    private Long id;

    private String name;
}
