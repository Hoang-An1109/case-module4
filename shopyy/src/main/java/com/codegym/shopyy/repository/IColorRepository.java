package com.codegym.shopyy.repository;

import com.codegym.shopyy.entities.Color;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IColorRepository extends JpaRepository<Color, Long> {
}
