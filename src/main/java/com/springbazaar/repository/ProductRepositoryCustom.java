package com.springbazaar.repository;

import com.springbazaar.domain.Product;
import com.vaadin.data.provider.QuerySortOrder;

import java.util.List;

public interface ProductRepositoryCustom {
    List<Product> fetchProductsOfPerson(String personFilter,
                                        int limit,
                                        int offset,
                                        List<QuerySortOrder> sortOrders);

    int countProducts(String filter);
}
