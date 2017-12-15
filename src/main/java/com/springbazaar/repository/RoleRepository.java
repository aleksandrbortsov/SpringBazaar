package com.springbazaar.repository;

import com.springbazaar.domain.Role;
import com.springbazaar.domain.type.RoleType;
import org.springframework.data.repository.CrudRepository;

import java.math.BigInteger;

public interface RoleRepository extends CrudRepository<Role, BigInteger> {
    Role findByRoleType(RoleType roleType);
}
