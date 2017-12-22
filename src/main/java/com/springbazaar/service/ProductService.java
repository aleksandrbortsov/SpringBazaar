package com.springbazaar.service;

import com.springbazaar.domain.Person;
import com.springbazaar.domain.Product;

import java.math.BigInteger;
import java.util.List;

public interface ProductService {

    List<Product> listAll();

    List<Product> listAllByPerson(Person person);

    Product getById(BigInteger id);

    Product saveOrUpdate(Product product);

    void deleteById(BigInteger id);
}
