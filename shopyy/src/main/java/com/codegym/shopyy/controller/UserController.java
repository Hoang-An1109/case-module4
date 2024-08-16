package com.codegym.shopyy.controller;

import com.codegym.shopyy.dto.UserDto;
import com.codegym.shopyy.model.dto.UpdatePasswordRequest;
import com.codegym.shopyy.service.ISecurityService;
import com.codegym.shopyy.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(value = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private IUserService userService;

    @Autowired
    private ISecurityService securityService;

    @GetMapping
    public ResponseEntity<?> getUsers(@RequestHeader("Authorization") final String authToken) {
        if (!securityService.isAuthenticated() && !securityService.isValidToken(authToken)) {
            return new ResponseEntity<String>("Responding with unauthorized error. Message - {}", HttpStatus.UNAUTHORIZED);
        }

        List<UserDto> userDtos  =  userService.getUsers();
        if (userDtos.isEmpty()) {
            return new ResponseEntity<List<UserDto>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(userDtos, HttpStatus.OK);
    }

    @PutMapping("/update-password")
    public ResponseEntity<?> updatePassword(@RequestBody UpdatePasswordRequest request,
                                            @RequestHeader("Authorization") final String authToken){
        if (!securityService.isAuthenticated() && !securityService.isValidToken(authToken)) {
            return new ResponseEntity<String>("Responding with unauthorized error. Message - {}", HttpStatus.UNAUTHORIZED);
        }
        boolean isChanged = userService.changePassword(request);
        if(isChanged){
            return ResponseEntity.ok().body("Password changed successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("failed to change password");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable("id") Long id,
                                    @RequestHeader("Authorization") final String authToken) {
        if (!securityService.isAuthenticated() && !securityService.isValidToken(authToken)) {
            return new ResponseEntity<String>("Responding with unauthorized error. Message - {}", HttpStatus.UNAUTHORIZED);
        }
        UserDto userDto = userService.getUserById(id);
        if (userDto == null) {
            return new ResponseEntity<UserDto>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserDto userDto) {
//        if (userService.getUserByEmail(userDto.getEmail())!= null) {
//            return new ResponseEntity<>("Email đã tồn tại!", HttpStatus.CONFLICT);
//        }
        userService.save(userDto);
        return new ResponseEntity<>("Tạo tài khoản thành công!", HttpStatus.CREATED);
    }
}
