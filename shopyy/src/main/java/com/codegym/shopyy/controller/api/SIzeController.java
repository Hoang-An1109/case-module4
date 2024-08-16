package com.codegym.shopyy.controller.api;

import com.codegym.shopyy.entities.Size;
import com.codegym.shopyy.service.impl.SizeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sizes")
@CrossOrigin(origins = "*")
public class SIzeController {

    @Autowired
    private SizeServiceImpl sizeService;

    @GetMapping
    public ResponseEntity<Iterable<Size>> homeSize() {
        Iterable<Size> sizes = sizeService.findAll();
        if (!sizes.iterator().hasNext()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(sizes, HttpStatus.OK);
    }
}
