package com.springbazaar.service;

import com.springbazaar.domain.Person;
import com.springbazaar.domain.Product;
import com.springbazaar.repository.ProductRepository;
import com.springbazaar.repository.ProductRepositoryCustom;
import com.vaadin.data.provider.QuerySortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;
    private ProductRepositoryCustom productRepositoryCustom;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, ProductRepositoryCustom productRepositoryCustom) {
        this.productRepository = productRepository;
        this.productRepositoryCustom = productRepositoryCustom;
    }

    @Override
    public List<Product> listAll() {
        List<Product> products = new ArrayList<>();
        productRepository.findAll().forEach(products::add);
        return products;
    }

    public List<Product> listByPerson(Person person) {
        return productRepository.findAllByPerson(person);
    }

    @Override
    public List<Product> listByPerson(String personIdFilter,
                                      int limit,
                                      int offset,
                                      List<QuerySortOrder> sortOrders) {
        return productRepositoryCustom.fetchProductsOfPerson(personIdFilter, limit, offset, sortOrders);
    }

    @Override
    public Product getById(BigInteger id) {
        return productRepository.findOne(id);
    }

    @Override
    public Product saveOrUpdate(Product product) {
        return productRepository.save(product);
    }

    @Override
    public void deleteById(BigInteger id) {
        productRepository.delete(id);
    }

    @Override
    public void delete(Product product) {
        productRepository.delete(product);
    }

    @Override
    public int count(String filter) {
        return productRepositoryCustom.countProducts(filter);
    }
}
