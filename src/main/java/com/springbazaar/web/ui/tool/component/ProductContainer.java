package com.springbazaar.web.ui.tool.component;

import com.springbazaar.domain.Product;
import com.springbazaar.service.ProductService;
import com.springbazaar.web.ui.tool.SharedTag;
import com.springbazaar.web.ui.tool.editor.ProductEditor;
import com.vaadin.data.HasValue;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.*;
import com.vaadin.ui.components.grid.HeaderRow;
import com.vaadin.ui.renderers.NumberRenderer;
import com.vaadin.ui.themes.ValoTheme;

import java.text.DecimalFormat;

public class ProductContainer extends CustomComponent {
    private final Button addButton = new Button("Add");
    private final Button editButton = new Button("Edit");
    private final Grid<Product> grid = new Grid<>("All products");
    private Product selectedProduct;


    public ProductContainer(ListDataProvider productProvider,
//                            ProductDataProvider productProvider,
                            ProductService productService) {
        final SafeDeleteButton deleteButton = new SafeDeleteButton("Delete",
                "Are you sure to delete selected product(s)?", null);
        Button.ClickListener yesListener = new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                productProvider.getItems().remove(selectedProduct);
                productService.delete(selectedProduct);
                grid.getDataProvider().refreshAll();
                Notification.show("Product " + selectedProduct.getCaption() + " has been deleted",
                        Notification.Type.TRAY_NOTIFICATION);
                selectedProduct = null;
                editButton.setEnabled(false);
                deleteButton.setEnabled(false);
            }
        };
        deleteButton.setEnabled(false);
        deleteButton.setYesListener(yesListener);

        DecimalFormat dollarFormat = new DecimalFormat("$##,##0.00");

        grid.addColumn(Product::getId).setCaption("#");
        grid.addColumn(Product::getCaption).setCaption("Caption").setId("ProductCaptionColumn");
        grid.addColumn(Product::getDescription).setCaption("Description");
        grid.addColumn(Product::getPrice, new NumberRenderer(dollarFormat)).setCaption("Price");
        grid.setSizeFull();
        grid.setSelectionMode(Grid.SelectionMode.SINGLE);

        grid.asSingleSelect().addValueChangeListener(selectionEvent -> {
            selectedProduct = selectionEvent.getValue();

        });
        grid.addSelectionListener(selectionEvent -> {
            deleteButton.setEnabled(selectedProduct != null);
            editButton.setEnabled(selectedProduct != null);
        });
        HeaderRow headerRow = grid.appendHeaderRow();
        TextField captionFilter = getFilterField();
        captionFilter.addValueChangeListener(this::onNameFilterTextChange);

        headerRow.getCell("ProductCaptionColumn").setComponent(captionFilter);

        grid.setDataProvider(productProvider);

        addButton.addClickListener(event -> {
            VaadinSession.getCurrent().setAttribute(SharedTag.EDIT_PRODUCT_TAG, null);
            getUI().getPage().setLocation(ProductEditor.NAME);
        });
        addButton.setIcon(VaadinIcons.PLUS);

        editButton.setEnabled(false);
        editButton.setIcon(VaadinIcons.EDIT);
        editButton.addClickListener(event -> {
            Product editProduct = grid.getSelectedItems().iterator().next();
            // exchange parameters between UI
            VaadinSession.getCurrent().setAttribute(SharedTag.EDIT_PRODUCT_TAG, editProduct);
            getUI().getPage().setLocation(ProductEditor.NAME);
        });


        HorizontalLayout buttonsLayout = new HorizontalLayout(addButton, editButton, deleteButton);

        VerticalLayout mainLayout = new VerticalLayout(grid, buttonsLayout);
        setCompositionRoot(mainLayout);
    }

    private void onNameFilterTextChange(HasValue.ValueChangeEvent<String> event) {
        ListDataProvider<Product> dataProvider = (ListDataProvider<Product>) grid.getDataProvider();
        dataProvider.setFilter(Product::getCaption, s -> caseInsensitiveContains(s, event.getValue()));
    }

    private Boolean caseInsensitiveContains(String where, String what) {
        return where.toLowerCase().contains(what.toLowerCase());
    }

    private TextField getFilterField() {
        TextField filter = new TextField("");
        filter.setWidth("100%");
        filter.addStyleName(ValoTheme.TEXTFIELD_TINY);
        filter.setPlaceholder("Filter");
        return filter;
    }

}
