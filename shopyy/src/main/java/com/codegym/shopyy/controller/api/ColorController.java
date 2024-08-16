package com.codegym.shopyy.controller.api;

import com.codegym.shopyy.entities.Color;
import com.codegym.shopyy.service.impl.ColorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/colors")
@CrossOrigin(origins = "*")
public class ColorController {

    @Autowired
    private ColorServiceImpl colorService;

    @GetMapping
    public ResponseEntity<Iterable<Color>> homeColor() {
        Iterable<Color> colors = colorService.findAll();
        if (!colors.iterator().hasNext()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(colors, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Color> addColor(@RequestBody Color color) {
        return new ResponseEntity<>(colorService.save(color), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Color> updateColor(@RequestBody Color color, @PathVariable Long id) {
        Optional<Color> colorOptional =  colorService.findById(id);
        if (colorOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        color.setId(id);
        return new ResponseEntity<>(colorService.save(color), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Color>> findByIdColor(@PathVariable Long id) {
        Optional<Color> colorOptional =  colorService.findById(id);
        return new ResponseEntity<>(colorOptional, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteColor(@PathVariable Long id) {
        Optional<Color> colorOptional = colorService.findById(id);
        if (colorOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        colorService.remove(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
