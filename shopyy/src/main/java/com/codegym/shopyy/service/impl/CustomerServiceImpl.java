package com.codegym.shopyy.service.impl;

import com.codegym.shopyy.entities.Customer;
import com.codegym.shopyy.entities.Role;
import com.codegym.shopyy.entities.User;
import com.codegym.shopyy.dto.RegistrationDto;
import com.codegym.shopyy.repository.ICustomerRepository;
import com.codegym.shopyy.repository.IRoleRepository;
import com.codegym.shopyy.repository.IUserRepository;
import com.codegym.shopyy.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements ICustomerService {
    @Autowired
    ICustomerRepository customerRepository;

    @Autowired
    IUserRepository userRepository;

    @Autowired
    IRoleRepository roleRepository;

    @Override
    public List<Customer> getAllCustomer() {
        return customerRepository.findAll();
    }

    @Override
    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id).orElse(null);
    }

    @Override
    public void updateCustomer(Customer customer) {
        customerRepository.save(customer);
    }

    @Override
    public void saveCustomer(RegistrationDto registrationDto) {
        // Tạo đối tượng User
        User user = new User();
        user.setUsername(registrationDto.getUsername());
        String hashedPassword = BCrypt.hashpw(registrationDto.getPassword(), BCrypt.gensalt(10));
        user.setPassword(hashedPassword);

        // Thiết lập vai trò cho User
        Role userRole = roleRepository.findByName("ROLE_USER");
        user.getRoles().add(userRole);

        // Lưu đối tượng User
        userRepository.save(user);

        // Tạo đối tượng Customer
        Customer customer = new Customer();
        customer.setFullName(registrationDto.getFullName());
        customer.setUsername(registrationDto.getUsername());
        customer.setEmail(registrationDto.getEmail());
        customer.setPhoneNumber(registrationDto.getPhoneNumber());
        customer.setGender(registrationDto.getGender());
        customer.setDob(registrationDto.getDob());
        customer.setAvatar(registrationDto.getAvatar());

        // Thiết lập vai trò cho Customer
        Role customerRole = roleRepository.findByName("ROLE_CUSTOMER");
        customer.setRole(customerRole);

        // Lưu đối tượng Customer
        customerRepository.save(customer);
    }

    @Override
    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }

    @Override
    public Customer getCustomerByEmail(String email) {
        return customerRepository.findByEmail(email).orElse(null);
    }

    @Override
    public Customer getCustomerByUsername(String username) {
        return customerRepository.findByUsername(username).orElse(null);
    }
}
