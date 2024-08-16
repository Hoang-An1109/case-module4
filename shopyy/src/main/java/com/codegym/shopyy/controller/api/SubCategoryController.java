package com.codegym.shopyy.controller.api;

import com.codegym.shopyy.constant.PageConstant;
import com.codegym.shopyy.dto.request.SubCategoryRequestDto;
import com.codegym.shopyy.dto.response.ResponsePage;
import com.codegym.shopyy.entities.Product;
import com.codegym.shopyy.entities.SubCategory;
import com.codegym.shopyy.service.impl.SubCategoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/subCategory")
@CrossOrigin(origins = "*")
public class SubCategoryController {

    @Autowired
    private SubCategoryServiceImpl subCategoryService;


    @GetMapping
    public ResponseEntity<Page<SubCategory>> homeSubCategory(@RequestParam(defaultValue = "", required = false) String search, Pageable pageable) {
        Page<SubCategory> subCategoryPage;
        if (!search.isEmpty()) {
            subCategoryPage = subCategoryService.findByName(pageable, search);
        } else {
            subCategoryPage = subCategoryService.findAll(pageable);
        }

        if (subCategoryPage.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(subCategoryPage, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponsePage> addSub(@RequestBody SubCategoryRequestDto subCategoryRequestDto) {
        ResponsePage responsePage = subCategoryService.save(subCategoryRequestDto);
        return new ResponseEntity<>(responsePage, responsePage.getStatus());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponsePage> updateSub(@RequestBody SubCategoryRequestDto subCategoryRequestDto) {
        Optional<SubCategory> subCategoryOptional =  subCategoryService.findById(subCategoryRequestDto.getId());
        if (subCategoryOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        SubCategory subCategory =  subCategoryOptional.get();
        if (Optional.ofNullable(subCategory).isPresent()) {
            subCategory.setName(subCategoryRequestDto.getName());
//            subCategory.setCategory(subCategoryRequestDto.getCategory());
        }

        ResponsePage responsePage = subCategoryService.save(subCategoryRequestDto);
        return new ResponseEntity<>(responsePage, responsePage.getStatus());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<SubCategory>> findByIdSubCategory(@PathVariable Long id) {
        Optional<SubCategory> subCategoryOptional =  subCategoryService.findById(id);
        return new ResponseEntity<>(subCategoryOptional, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubCategory(@PathVariable Long id) {
        Optional<SubCategory> existingSubCategory = subCategoryService.findById(id);
        if (existingSubCategory.isPresent()) {
            subCategoryService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @PostMapping("/search")
    public ResponseEntity<Map<SubCategory, Iterable<Product>>> searchSubCategory(@PageableDefault(page = PageConstant.DEFAULT_PAGE) Pageable pageable,
                                                                                 @RequestParam("search") Optional<String> search) {
        Map<SubCategory, Iterable<Product>> result = new HashMap<>();
        Page<SubCategory> subCategoryPage;
        if (search.isPresent()) {
            subCategoryPage = subCategoryService.findByName(pageable, search.get());
            if (!subCategoryPage.hasContent()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        } else {
            subCategoryPage = subCategoryService.findAll(pageable);
        }

        for (SubCategory subCategory : subCategoryPage) {
            Iterable<Product> products = subCategoryService.findProductsBySubCategory(subCategory);
            result.put(subCategory, products);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
