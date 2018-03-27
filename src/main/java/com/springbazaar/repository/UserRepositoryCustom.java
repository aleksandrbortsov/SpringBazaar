package com.springbazaar.repository;

import com.springbazaar.domain.util.LoginAttempts;

public interface UserRepositoryCustom {
    void updateFailAttempts(String username);

    void resetFailAttempts(String username);

    LoginAttempts getLoginAttempts(String username);
}
