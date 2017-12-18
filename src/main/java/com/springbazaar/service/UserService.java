package com.springbazaar.service;

import com.springbazaar.domain.User;

import java.math.BigInteger;

public interface UserService {

    User saveOrUpdate(User user);

    User getUserById(BigInteger id);

    void delete(User user);
}
