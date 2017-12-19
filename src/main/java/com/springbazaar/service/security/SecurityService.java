package com.springbazaar.service.security;

public interface SecurityService {
    String findLoggedInUsername();

    void login(String username, String password);
}
