package com.codegym.shopyy.controller;

import com.codegym.shopyy.dto.RegistrationDto;
import com.codegym.shopyy.payload.request.LoginRequest;
import com.codegym.shopyy.payload.response.ForbiddenResponse;
import com.codegym.shopyy.payload.response.LoginResponse;
import com.codegym.shopyy.security.JwtTokenProvider;
import com.codegym.shopyy.service.ICustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(value = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider tokenProvider;

    @Autowired
    private ICustomerService customerService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(), loginRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String token = tokenProvider.generateToken(authentication);
            return new ResponseEntity<>(new LoginResponse("Đăng nhập thành công!", token), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new LoginResponse("Đăng nhập thất bại!", null), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegistrationDto registrationDto) {
        customerService.saveCustomer(registrationDto);
        return ResponseEntity.ok("User registered successfully");
    }

    @GetMapping("/access-denied")
    public ResponseEntity<?> getAccessDenied() {
        return new ResponseEntity<>(new ForbiddenResponse("Không có quyền truy cập!"), HttpStatus.FORBIDDEN);
    }
}
