package com.codegym.shopyy.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegistrationDto {
    private String fullName;
    private String username;
    private String email;
    private String phoneNumber;
    private String gender;
    private Date dob;
    private String avatar;
    private String password;
}
