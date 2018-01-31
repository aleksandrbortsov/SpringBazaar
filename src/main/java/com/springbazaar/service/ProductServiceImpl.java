package com.springbazaar.service;

import com.springbazaar.domain.Person;
import com.springbazaar.domain.Product;
import com.springbazaar.repository.ProductRepository;
import com.springbazaar.repository.ProductRepositoryCustom;
import com.vaadin.data.provider.AbstractBackEndDataProvider;
import com.vaadin.data.provider.Query;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Service
@Slf4j
public class ProductServiceImpl extends AbstractBackEndDataProvider<Product, String> implements ProductService {
    @Getter
    @Setter
    private Person person;

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
    public Product getById(BigInteger id) {
        return productRepository.findOne(id);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_SELLER')")
    public Product saveOrUpdate(Product product) {
        return productRepository.save(product);
    }

    @Override
    public void deleteById(BigInteger id) {
        productRepository.delete(id);
    }

    @Override
    //TODO sort out with SpEL
    @PreAuthorize("product.person.user == authentication.principal")
    public void delete(Product product) {
        productRepository.delete(product);
    }

    @Override
    @Transactional
    protected Stream<Product> fetchFromBackEnd(Query<Product, String> query) {
        if (getFilter(query).isEmpty()) {
            return productRepository.findAllByPerson(getPerson()).stream();
        }
        return
                productRepositoryCustom.fetchProductsOfPerson(getPerson().getId(),
                        getFilter(query),
                        query.getLimit(),
                        query.getOffset(),
                        query.getSortOrders()
                ).stream();
    }

    private String getFilter(Query<Product, String> query) {
        return query.getFilter().orElse("");
    }

    @Override
    @Transactional
    protected int sizeInBackEnd(Query<Product, String> query) {
        return productRepositoryCustom.countProducts(getPerson().getId(), getFilter(query));
    }

}
