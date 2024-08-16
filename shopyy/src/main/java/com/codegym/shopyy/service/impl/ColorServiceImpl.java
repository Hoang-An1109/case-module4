package com.codegym.shopyy.service.impl;

import com.codegym.shopyy.entities.Color;
import com.codegym.shopyy.repository.IColorRepository;
import com.codegym.shopyy.service.IColorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ColorServiceImpl implements IColorService {

    @Autowired
    private IColorRepository colorRepository;

    @Override
    public Iterable<Color> findAll() {
        return colorRepository.findAll();
    }

    @Override
    public Color save(Color color) {
        return colorRepository.save(color);
    }

    @Override
    public Optional<Color> findById(Long id) {
        return colorRepository.findById(id);
    }

    @Override
    public void remove(Long id) {
        colorRepository.deleteById(id);
    }
}
