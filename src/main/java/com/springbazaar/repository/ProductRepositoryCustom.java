package com.springbazaar.repository;

import com.springbazaar.domain.Product;
import com.vaadin.data.provider.QuerySortOrder;

import java.math.BigInteger;
import java.util.List;

public interface ProductRepositoryCustom {
    List<Product> fetchProductsOfPerson(BigInteger personId,
                                        String productCaptionFilter,
                                        int limit,
                                        int offset,
                                        List<QuerySortOrder> sortOrders);

    int countProducts(BigInteger personId, String productCaptionFilter);
}
