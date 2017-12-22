package com.springbazaar.service;

import com.springbazaar.domain.Person;
import com.springbazaar.domain.User;

import java.math.BigInteger;
import java.util.List;

public interface UserService {

    User saveOrUpdate(User user, Person personForm, List<String> roleForm);

    User saveOrUpdate(User user);

    User getUserById(BigInteger id);

    void delete(User user);

    boolean isUsernameExist(String username);
}
