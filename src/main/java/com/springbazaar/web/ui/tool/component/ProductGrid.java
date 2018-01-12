package com.springbazaar.web.ui.tool.component;

import com.springbazaar.domain.Product;
import com.springbazaar.service.ProductService;
import com.vaadin.data.HasValue;
import com.vaadin.data.provider.ConfigurableFilterDataProvider;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.components.grid.HeaderRow;
import com.vaadin.ui.renderers.HtmlRenderer;
import com.vaadin.ui.renderers.NumberRenderer;
import com.vaadin.ui.themes.ValoTheme;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Locale;

public class ProductGrid extends Grid<Product> {
    public static final String PRODUCT_CAPTION_COLUMN_ID = "ProductCaptionColumnId";
    public static final String PRODUCT_ID_COLUMN_ID = "ProductIdColumnId";
    private ConfigurableFilterDataProvider<Product, Void, String> productProvider;

    public ProductGrid(String caption, ProductService productService) {
        setSizeFull();
        setCaption(caption);

        productProvider = productService.withConfigurableFilter();
        setDataProvider(productProvider);

        setSelectionMode(Grid.SelectionMode.SINGLE);

        DecimalFormat dollarFormat = new DecimalFormat("$##,##0.00");
        addColumn(Product::getId)
                .setCaption("#")
                .setId(PRODUCT_ID_COLUMN_ID)
                .setStyleGenerator(product -> "v-align-center");
        addColumn(product -> {
                    BigDecimal price = product.getPrice();
                    return getLightIconHtml(price) + " "
                            + product.getCaption();
                }, new HtmlRenderer()
        )
                .setCaption("Caption")
                .setId(PRODUCT_CAPTION_COLUMN_ID)
                .setSortProperty("caption");
        addColumn(Product::getDescription)
                .setCaption("Description");
        addColumn(Product::getPrice, new NumberRenderer(dollarFormat))
                .setCaption("Price")
                .setStyleGenerator(product -> "v-align-right");

        HeaderRow headerRow = appendHeaderRow();
        TextField captionFilter = getFilterField();
        headerRow.getCell(PRODUCT_CAPTION_COLUMN_ID).setComponent(captionFilter);

        Label totalProductsLabel = new Label();
        //TODO get dynamic count items
        totalProductsLabel.setValue("total: ");
        headerRow.getCell(PRODUCT_ID_COLUMN_ID).setComponent(totalProductsLabel);
    }

    private String getLightIconHtml(BigDecimal availability) {
        String color = "";
        if (availability.intValue() == 0) {
            color = "#32d31b";
        } else if (availability.intValue() <= 500) {
            color = "#eef128";
        } else if (availability.intValue() > 500) {
            color = "#de3915";
        }
        return "<span class=\"v-icon\" style=\"font-family: "
                + VaadinIcons.WALLET.getFontFamily() + ";color:" + color
                + "\">&#x"
                + Integer.toHexString(VaadinIcons.WALLET.getCodepoint())
                + ";</span>";
    }

    private TextField getFilterField() {
        TextField filter = new TextField("");
        filter.setWidth("100%");
        filter.addStyleName(ValoTheme.TEXTFIELD_TINY);
        filter.setPlaceholder("Filter");
        filter.setValueChangeMode(ValueChangeMode.LAZY);
        filter.addValueChangeListener(e -> {
            productProvider.setFilter(e.getValue());
        });

        return filter;
    }

    //In-memory filtering text field
//    private void onNameFilterTextChange(HasValue.ValueChangeEvent<String> event) {
//        ProductDataProvider dataProvider = (ProductDataProvider) this.getDataProvider();
//        dataProvider.setFilter(Product::getCaption, s -> caseInsensitiveContains(s, event.getValue()));
//    }
//
//    private Boolean caseInsensitiveContains(String where, String what) {
//        Locale locale = UI.getCurrent().getLocale();
//        return where.toLowerCase(locale).contains(what.toLowerCase(locale));
//    }
}
