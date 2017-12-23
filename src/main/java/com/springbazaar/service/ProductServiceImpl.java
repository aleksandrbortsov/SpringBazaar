package com.springbazaar.service;

import com.springbazaar.domain.Person;
import com.springbazaar.domain.Product;
import com.springbazaar.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> listAll() {
        List<Product> products = new ArrayList<>();
        productRepository.findAll().forEach(products::add);
        return products;
    }

    @Override
    public List<Product> listAllByPerson(Person person) {
        return productRepository.findAllByPerson(person);
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
}
