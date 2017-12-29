package com.springbazaar.domain.util;

import com.vaadin.data.provider.QuerySortOrder;
import com.vaadin.shared.data.sort.SortDirection;

import java.util.List;

public class SortStringGenerator {
    public static String generate(List<QuerySortOrder> sortOrders) {
        String sortString = "";
        int i = 0;
        if (sortOrders != null && !sortOrders.isEmpty()) {
            for (QuerySortOrder sortOrder : sortOrders) {
                String order = getOrder(sortOrder.getDirection());
                if (i == 0) {
                    sortString = "order by lower(" + sortOrder.getSorted() + ") " + order;
                } else {
                    sortString = sortString + ", lower(" + sortOrder.getSorted() + ") " + order;
                }
                i++;
            }
        }
        return sortString;
    }
    private static String getOrder(SortDirection sortDirection) {
        String order = "asc";
        if (sortDirection == SortDirection.DESCENDING) {
            order = "desc";
        }
        return order;
    }
}
