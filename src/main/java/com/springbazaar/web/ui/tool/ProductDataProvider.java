package com.springbazaar.web.ui.tool;

import com.springbazaar.domain.Product;
import com.springbazaar.service.ProductService;
import com.vaadin.data.provider.AbstractBackEndDataProvider;
import com.vaadin.data.provider.Query;

import java.util.stream.Stream;

public class ProductDataProvider extends AbstractBackEndDataProvider<Product, String> {
    private final ProductService productService;

    public ProductDataProvider(ProductService productService) {
        this.productService = productService;
    }

    @Override
    protected Stream<Product> fetchFromBackEnd(Query<Product, String> query) {
        return productService.listByPerson(query.getFilter().orElse(null),
                query.getLimit(),
                query.getOffset(),
                query.getSortOrders()).stream();
    }

    @Override
    protected int sizeInBackEnd(Query<Product, String> query) {
        return productService.count(query.getFilter().orElse(null));
    }
}
