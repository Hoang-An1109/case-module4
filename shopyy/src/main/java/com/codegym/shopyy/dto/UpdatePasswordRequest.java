package com.codegym.shopyy.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UpdatePasswordRequest {
    private String currentPassword;

    private String newPassword;

    private String confirmPassword;
}
