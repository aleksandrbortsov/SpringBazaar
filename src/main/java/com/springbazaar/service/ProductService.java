package com.springbazaar.service;

import com.springbazaar.domain.Person;
import com.springbazaar.domain.Product;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.QuerySortOrder;

import java.math.BigInteger;
import java.util.List;

public interface ProductService extends DataProvider<Product, String> {

    List<Product> listAll();

    List<Product> listByPerson(Person person);

    Product getById(BigInteger id);

    Product saveOrUpdate(Product product);

    void deleteById(BigInteger id);

    void delete(Product product);

    void setPerson(Person person);

}
