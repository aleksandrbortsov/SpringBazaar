package com.springbazaar.repository;

import com.springbazaar.domain.Person;
import com.springbazaar.domain.Product;
import com.springbazaar.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.math.BigInteger;
import java.util.List;

public interface ProductRepository extends CrudRepository<Product, BigInteger>, ProductRepositoryCustom {
    List<Product> findAllByPerson(Person person);
}
