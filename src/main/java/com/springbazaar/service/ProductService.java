package com.springbazaar.service;

import com.springbazaar.domain.Person;
import com.springbazaar.domain.Product;
import com.vaadin.data.provider.QuerySortOrder;

import java.math.BigInteger;
import java.util.List;

public interface ProductService {

    List<Product> listAll();

    List<Product> listByPerson(Person person);

    List<Product> listByPerson(String personIfFilter,
                               int limit,
                               int offset,
                               List<QuerySortOrder> sortOrders);

    Product getById(BigInteger id);

    Product saveOrUpdate(Product product);

    void deleteById(BigInteger id);

    void delete(Product product);

    int count(String filter);
}
