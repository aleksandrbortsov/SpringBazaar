package com.springbazaar.repository;

import com.springbazaar.domain.Person;
import org.springframework.data.repository.CrudRepository;

import java.math.BigInteger;

public interface PersonRepository extends CrudRepository<Person, BigInteger> {
}
