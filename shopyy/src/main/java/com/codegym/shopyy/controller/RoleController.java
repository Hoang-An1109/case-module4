package com.codegym.shopyy.controller;

import com.codegym.shopyy.dto.RoleDto;
import com.codegym.shopyy.service.IRoleService;
import com.codegym.shopyy.service.ISecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(value = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/roles")
public class RoleController {
    @Autowired
    private IRoleService roleService;

    @Autowired
    private ISecurityService securityService;

    @GetMapping
    public ResponseEntity<?> getRoles() {
//        @RequestHeader("Authorization") final String authToken
//        if (!securityService.isAuthenticated() && !securityService.isValidToken(authToken)) {
//            return new ResponseEntity<String>("Responding with unauthorized error. Message - {}", HttpStatus.UNAUTHORIZED);
//        }
        List<RoleDto> roleDtos = (List<RoleDto>) roleService.findAll();
        if (roleDtos.isEmpty()) {
            return new ResponseEntity<List<RoleDto>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(roleDtos, HttpStatus.OK);
    }
}
