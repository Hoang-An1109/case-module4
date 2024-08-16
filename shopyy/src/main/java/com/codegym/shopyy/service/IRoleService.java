package com.codegym.shopyy.service;


import com.codegym.shopyy.dto.RoleDto;

import java.util.Optional;

public interface IRoleService {
    Iterable<RoleDto> findAll();
    Optional<RoleDto> findById(Long id);
    void save(RoleDto roleDto);
    void remove(Long id);
}
