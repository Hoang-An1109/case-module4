package com.codegym.shopyy.service.impl;

import com.codegym.shopyy.model.dto.UpdatePasswordRequest;
import com.codegym.shopyy.dto.UserDto;
import com.codegym.shopyy.entities.User;
import com.codegym.shopyy.repository.IUserRepository;
import com.codegym.shopyy.service.IUserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {
    private final IUserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<UserDto> getUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public UserDto getUserById(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public Iterable<UserDto> findAll() {
        Iterable<User> entities = userRepository.findAll();
        return StreamSupport.stream(entities.spliterator(), true)
                .map(entity -> modelMapper.map(entity, UserDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<UserDto> findById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return Optional.ofNullable(modelMapper.map(user, UserDto.class));
    }

    @Override
    public void save(UserDto userDto) {
        if (!userDto.getPassword().isEmpty()) {
            String hashedPassword = BCrypt.hashpw(userDto.getPassword(), BCrypt.gensalt(10));
            userDto.setPassword(hashedPassword);
        }
        User user = modelMapper.map(userDto, User.class);
        userRepository.save(user);
    }

    @Override
    public void remove(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public boolean changePassword(UpdatePasswordRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        if (username != null) {
            User user = userRepository.findByUsername(username);
            if (passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
                user.setPassword(passwordEncoder.encode(request.getNewPassword()));
                userRepository.save(user);
                return true;
            }
        }
        return false;
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new SecurityException("You are not authenticated");
        }

        String username = authentication.getName();
        return userRepository.findByUsername(username);
    }
}
