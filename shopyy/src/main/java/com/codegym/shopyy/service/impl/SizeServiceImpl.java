package com.codegym.shopyy.service.impl;

import com.codegym.shopyy.entities.Size;
import com.codegym.shopyy.repository.ISizeRepository;
import com.codegym.shopyy.service.ISizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SizeServiceImpl implements ISizeService {

    @Autowired
    private ISizeRepository sizeRepository;


    @Override
    public Iterable<Size> findAll() {
        return sizeRepository.findAll();
    }
}
