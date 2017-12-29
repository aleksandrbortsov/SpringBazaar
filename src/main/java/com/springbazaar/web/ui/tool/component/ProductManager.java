package com.springbazaar.web.ui.tool.component;

import com.springbazaar.domain.Product;
import com.springbazaar.service.ProductService;
import com.springbazaar.web.ui.tool.ProductDataProvider;
import com.springbazaar.web.ui.tool.SharedTag;
import com.springbazaar.web.ui.tool.component.editor.ProductEditor;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.*;

public class ProductManager extends CustomComponent {

    final DeleteWithConfirmButton deleteButton = new DeleteWithConfirmButton("Delete",
            "Are you sure to delete selected product(s)?", null);
    private final Button addButton = new Button("Add");
    private final Button editButton = new Button("Edit");
    private final ProductGrid productGrid = new ProductGrid("All products");
    private Product selectedProduct;


    public ProductManager(ProductDataProvider productProvider,
                          ProductService productService) {
        productGrid.setDataProvider(productProvider);

        productGrid.asSingleSelect().addValueChangeListener(selectionEvent -> {
            selectedProduct = selectionEvent.getValue();
        });
        productGrid.addSelectionListener(selectionEvent -> {
            deleteButton.setEnabled(selectedProduct != null);
            editButton.setEnabled(selectedProduct != null);
        });

        Button.ClickListener yesListener = new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                productService.delete(selectedProduct);
                productGrid.getDataProvider().refreshAll();
                Notification.show("Product " + selectedProduct.getCaption() + " has been deleted",
                        Notification.Type.TRAY_NOTIFICATION);
                cancelProductSelection();
            }
        };
        deleteButton.setEnabled(false);
        deleteButton.setYesListener(yesListener);

        addButton.addClickListener(event -> {
            VaadinSession.getCurrent().setAttribute(SharedTag.EDIT_PRODUCT_TAG, null);
            getUI().getPage().setLocation(ProductEditor.NAME);
        });
        addButton.setIcon(VaadinIcons.PLUS);

        editButton.setEnabled(false);
        editButton.setIcon(VaadinIcons.EDIT);
        editButton.addClickListener(event -> {
            Product editProduct = productGrid.getSelectedItems().iterator().next();
            // exchange parameters between UI
            VaadinSession.getCurrent().setAttribute(SharedTag.EDIT_PRODUCT_TAG, editProduct);
            getUI().getPage().setLocation(ProductEditor.NAME);
        });

        HorizontalLayout buttonsLayout = new HorizontalLayout(addButton, editButton, deleteButton);

        VerticalLayout mainLayout = new VerticalLayout(productGrid, buttonsLayout);
        setCompositionRoot(mainLayout);
    }


    private void cancelProductSelection() {
        selectedProduct = null;
        editButton.setEnabled(false);
        deleteButton.setEnabled(false);
    }
}
