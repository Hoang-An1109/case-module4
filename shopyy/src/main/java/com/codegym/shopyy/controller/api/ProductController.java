package com.codegym.shopyy.controller.api;

import com.codegym.shopyy.constant.PageConstant;
import com.codegym.shopyy.dto.request.ProductRequestDto;
import com.codegym.shopyy.dto.response.ResponsePage;
import com.codegym.shopyy.entities.Product;
import com.codegym.shopyy.service.impl.ProductServiceImpl;
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

import java.util.Optional;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "*")
public class ProductController {

    @Autowired
    private ProductServiceImpl productService;

    @GetMapping
    public ResponseEntity<Page<Product>> homeProduct(@RequestParam(defaultValue = "", required = false) String search,
                                                     @PageableDefault(page = PageConstant.DEFAULT_PAGE, size = PageConstant.PAGE_SIZE) Pageable pageable) {

        Page<Product> productPage;
        if (!search.isEmpty()) {
            productPage = productService.findByName(pageable, search);
        } else {
            productPage = productService.findAll(pageable);
        }

        if (productPage.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(productPage, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponsePage> addProduct(@RequestBody ProductRequestDto productRequestDto) {
        ResponsePage responsePage = productService.save(productRequestDto);
        return new ResponseEntity<>(responsePage, responsePage.getStatus());
    }

    @PutMapping("{id}")
    public ResponseEntity<ResponsePage> saveProduct(@RequestBody ProductRequestDto productRequestDto) {
        Optional<Product> productOptional = productService.findById(productRequestDto.getId());
        if (productOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }


        Product product = productOptional.get();
        if (Optional.ofNullable(product).isPresent()) {
            product.setName(productRequestDto.getName());
            product.setPrice(productRequestDto.getPrice());
            product.setDescription(productRequestDto.getDescription());
            product.setQuantity(productRequestDto.getQuantity());
            product.setImg(productRequestDto.getImg());
//            product.setSubCategory(productRequestDto.getSubCategory());
            product.setColors(productRequestDto.getColors());
            product.setSizes(productRequestDto.getSizes());
        }

        ResponsePage responsePage = productService.save(productRequestDto);
        return new ResponseEntity<>(responsePage, responsePage.getStatus());
    }


    @GetMapping("/{id}")
    public ResponseEntity<Optional<Product>> findByIdProduct(@PathVariable Long id) {
        Optional<Product> productOptional = productService.findById(id);
        return new ResponseEntity<>(productOptional, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        Optional<Product> optionalProduct = productService.findById(id);
        if (optionalProduct.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        productService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<Product>> searchProducts(@RequestParam String keyword, @PageableDefault(
            page = PageConstant.DEFAULT_PAGE,
            size = PageConstant.PAGE_SIZE) Pageable pageable) {
        Page<Product> productPage = productService.searchProducts(keyword, pageable);

        if (productPage.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(productPage, HttpStatus.OK);
    }

    @GetMapping("/price")
    public ResponseEntity<Page<Product>> priceProduct(@RequestParam(name = "value") String value, Pageable pageable) {

        Page<Product> productPage;

        if (value.equalsIgnoreCase("ASC")) {
            productPage = productService.findAllByOrderByPriceAsc(pageable);
        } else if (value.equalsIgnoreCase("DESC")) {
            productPage = productService.findAllByOrderByPriceDesc(pageable);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(productPage, HttpStatus.OK);
    }


}