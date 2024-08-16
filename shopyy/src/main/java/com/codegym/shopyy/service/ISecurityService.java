package com.codegym.shopyy.service;

public interface ISecurityService {
    boolean isAuthenticated();
    boolean isValidToken(String token);
}
