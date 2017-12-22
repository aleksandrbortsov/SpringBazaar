package com.springbazaar.service.security;

public interface SecurityService {
    String findLoggedInUsername();

    boolean login(String username, String password);
}
