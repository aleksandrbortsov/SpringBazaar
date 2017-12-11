package com.springbazaar.service;

import com.springbazaar.domain.Product;

import java.math.BigInteger;
import java.util.List;

public interface ProductService {

    List<Product> listAll();

    Product getById(BigInteger id);

    Product saveOrUpdate(Product product);

    void delete(BigInteger id);
}
