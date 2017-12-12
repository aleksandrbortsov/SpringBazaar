package com.springbazaar.repository;

import com.springbazaar.domain.Product;
import org.springframework.data.repository.CrudRepository;

import java.math.BigInteger;

public interface ProductRepository extends CrudRepository<Product, BigInteger>, ProductRepositoryCustom {
}
