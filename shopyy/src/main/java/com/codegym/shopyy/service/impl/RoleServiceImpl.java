package com.codegym.shopyy.service.impl;

import com.codegym.shopyy.dto.RoleDto;
import com.codegym.shopyy.entities.Role;
import com.codegym.shopyy.repository.IRoleRepository;
import com.codegym.shopyy.service.IRoleService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Transactional
@ComponentScan(basePackageClasses = ModelMapper.class)
public class RoleServiceImpl implements IRoleService {
    private final IRoleRepository IRoleRepository;

    private final ModelMapper modelMapper;

    public RoleServiceImpl(IRoleRepository IRoleRepository, ModelMapper modelMapper) {
        this.IRoleRepository = IRoleRepository;
        this.modelMapper = modelMapper;
    }
    @Override
    public Iterable<RoleDto> findAll() {
        Iterable<Role> entities = IRoleRepository.findAll();
        return StreamSupport.stream(entities.spliterator(), true)
                .map(entity -> modelMapper.map(entity, RoleDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<RoleDto> findById(Long id) {
        Role entity = IRoleRepository.findById(id).orElse(null);
        return Optional.ofNullable(modelMapper.map(entity, RoleDto.class));    }

    @Override
    public void save(RoleDto roleDto) {
        Role role = modelMapper.map(roleDto, Role.class);
        IRoleRepository.save(role);
    }

    @Override
    public void remove(Long id) {
        IRoleRepository.deleteById(id);
    }
}
