package com.springbazaar.repository;

import com.springbazaar.domain.Role;
import org.springframework.data.repository.CrudRepository;

import java.math.BigInteger;

public interface RoleRepository extends CrudRepository<Role, BigInteger> {
    Role findByName(String name);
}
