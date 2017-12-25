package com.springbazaar.web.ui;

import com.springbazaar.domain.Product;
import com.springbazaar.domain.User;
import com.springbazaar.service.ProductService;
import com.springbazaar.web.ui.editor.ProductEditor;
import com.vaadin.annotations.Theme;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Theme("valo")
@SpringUI(path = WelcomeUI.NAME)
public class WelcomeUI extends MainUI {
    public static final String NAME = "/welcome";
    private final ProductService productService;
    private final Label loggedUsername = new Label("Username");
    private final Button addProductButton = new Button("Add");
    private final Button editProductButton = new Button("Edit");
    private final Button deleteProductButton = new Button("Delete");
    private final Grid<Product> grid = new Grid<>("All products");
    private Product selectedProduct;

    @Autowired
    public WelcomeUI(ProductService productService) {
        this.productService = productService;
    }

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        Page.getCurrent().setTitle("Spring Bazaar Application");

        User currentUser = getCurrentUser();
        loggedUsername.setValue("Welcome, " +
                (currentUser != null ? currentUser.getPerson().getShortName() : "") + "!");

        List<Product> product = new ArrayList<>();
        if (currentUser != null) {
            product.addAll(productService.listAllByPerson(currentUser.getPerson()));
        }
        grid.setItems(product);
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
            VaadinSession.getCurrent().setAttribute("editProduct", null);
            getPage().setLocation(ProductEditor.NAME);
        });
        addProductButton.setIcon(VaadinIcons.PLUS);

        editProductButton.setEnabled(false);
        editProductButton.setIcon(VaadinIcons.EDIT);
        editProductButton.addClickListener(event -> {
            Product editProduct = grid.getSelectedItems().iterator().next();
            // move parameters between UI
            VaadinSession.getCurrent().setAttribute("editProduct", editProduct);
            getPage().setLocation(ProductEditor.NAME);
        });

        deleteProductButton.setEnabled(false);
        deleteProductButton.setIcon(VaadinIcons.TRASH);
        deleteProductButton.addClickListener(event -> {
            product.remove(selectedProduct);
            productService.delete(selectedProduct);
            grid.getDataProvider().refreshAll();
            Notification.show("Product " + selectedProduct.getCaption() + " has been deleted",
                    Notification.Type.TRAY_NOTIFICATION);
            selectedProduct = null;
            editProductButton.setEnabled(false);
            deleteProductButton.setEnabled(false);
        });

        HorizontalLayout buttons = new HorizontalLayout(addProductButton, editProductButton, deleteProductButton);
        VerticalLayout fields = new VerticalLayout(loggedUsername, grid, buttons);
        fields.setSpacing(true);
        fields.setMargin(new MarginInfo(true, true, true, false));
        fields.setSizeUndefined();

        VerticalLayout uiLayout = new VerticalLayout(fields);
        uiLayout.setSizeFull();
        uiLayout.setComponentAlignment(fields, Alignment.TOP_LEFT);

        setContent(uiLayout);
    }
}
