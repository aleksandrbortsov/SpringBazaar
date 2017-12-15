package com.springbazaar.repository;

import com.springbazaar.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.math.BigInteger;

public interface UserRepository extends CrudRepository<User, BigInteger> {
    User findByUsername(String username);
}
