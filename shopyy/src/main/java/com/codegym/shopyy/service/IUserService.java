package com.codegym.shopyy.service;

import com.codegym.shopyy.dto.UserDto;
import com.codegym.shopyy.entities.User;
import com.codegym.shopyy.model.dto.UpdatePasswordRequest;
import java.util.List;
import java.util.Optional;

public interface IUserService {
    List<UserDto> getUsers();
    UserDto getUserById(Long userId);
    Iterable<UserDto> findAll();
    Optional<UserDto> findById(Long id);
    void save(UserDto userDto);
    void remove(Long id);
    boolean changePassword(UpdatePasswordRequest request);
    User getCurrentUser();
}
