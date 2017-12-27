package com.springbazaar.web.ui.tool.component;

import com.springbazaar.domain.Product;
import com.springbazaar.service.ProductService;
import com.springbazaar.web.ui.tool.SharedTag;
import com.springbazaar.web.ui.tool.editor.ProductEditor;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.*;

import java.util.List;

public class ProductContainer extends CustomComponent {
    private final Button addProductButton = new Button("Add");
    private final Button editProductButton = new Button("Edit");
    private final Button deleteProductButton = new Button("Delete");
    private final Grid<Product> grid = new Grid<>("All products");
    private Product selectedProduct;


    public ProductContainer(List<Product> products, ProductService productService) {
        grid.setItems(products);
//        grid.getEditor().setEnabled(true);
        grid.addColumn(Product::getCaption).setCaption("Caption");
        grid.addColumn(Product::getDescription).setCaption("Description");
        //.setEditorComponent(new TextField(), Product::setDescription);
        grid.addColumn(Product::getPrice).setCaption("Price");

        grid.setSelectionMode(Grid.SelectionMode.SINGLE);

        grid.asSingleSelect().addValueChangeListener(selectionEvent -> {
            selectedProduct = selectionEvent.getValue();

        });
        grid.addSelectionListener(selectionEvent -> {
            deleteProductButton.setEnabled(selectedProduct != null);
            editProductButton.setEnabled(selectedProduct != null);
        });

        addProductButton.addClickListener(event -> {
            VaadinSession.getCurrent().setAttribute(SharedTag.EDIT_PRODUCT_TAG, null);
            getUI().getPage().setLocation(ProductEditor.NAME);
        });
        addProductButton.setIcon(VaadinIcons.PLUS);

        editProductButton.setEnabled(false);
        editProductButton.setIcon(VaadinIcons.EDIT);
        editProductButton.addClickListener(event -> {
            Product editProduct = grid.getSelectedItems().iterator().next();
            // exchange parameters between UI
            VaadinSession.getCurrent().setAttribute(SharedTag.EDIT_PRODUCT_TAG, editProduct);
            getUI().getPage().setLocation(ProductEditor.NAME);
        });

        deleteProductButton.setEnabled(false);
        deleteProductButton.setIcon(VaadinIcons.TRASH);
        deleteProductButton.addClickListener(event -> {
            products.remove(selectedProduct);
            productService.delete(selectedProduct);
            grid.getDataProvider().refreshAll();
            Notification.show("Product " + selectedProduct.getCaption() + " has been deleted",
                    Notification.Type.TRAY_NOTIFICATION);
            selectedProduct = null;
            editProductButton.setEnabled(false);
            deleteProductButton.setEnabled(false);
        });

        HorizontalLayout buttonsLayout = new HorizontalLayout(addProductButton, editProductButton, deleteProductButton);

        VerticalLayout mainLayout = new VerticalLayout(grid, buttonsLayout);
        setCompositionRoot(mainLayout);
    }

}
