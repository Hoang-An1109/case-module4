package com.codegym.shopyy.controller;

import com.codegym.shopyy.entities.Customer;
import com.codegym.shopyy.security.JwtTokenProvider;
import com.codegym.shopyy.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(value = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    @Autowired
    ICustomerService customerService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers() {
        return new ResponseEntity<>(customerService.getAllCustomer(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(customerService.getCustomerById(id), HttpStatus.OK);
    }

    @PutMapping("/update-by-username")
    public ResponseEntity<?> updateCustomer(@RequestBody Customer customer, @RequestHeader("Authorization") String bearerToken){
        String token = bearerToken.substring(7);
        String username = jwtTokenProvider.getUsernameFromJWT(token);

        Customer existingCustomer = customerService.getCustomerByUsername(username);
        if (existingCustomer == null) {
            return new ResponseEntity<>("Customer not found" ,HttpStatus.NOT_FOUND);
        }

        existingCustomer.setFullName(customer.getFullName());
        existingCustomer.setPhoneNumber(customer.getPhoneNumber());
        existingCustomer.setGender(customer.getGender());
        existingCustomer.setDob(customer.getDob());
        existingCustomer.setAvatar(customer.getAvatar());

        customerService.updateCustomer(existingCustomer);
        return new ResponseEntity<>(existingCustomer, HttpStatus.OK);
    }
}
