package com.springbazaar.web.ui;

import com.springbazaar.domain.Person;
import com.springbazaar.domain.Product;
import com.springbazaar.domain.User;
import com.springbazaar.service.PersonService;
import com.springbazaar.service.ProductService;
import com.springbazaar.web.ui.editor.ProductEditor;
import com.vaadin.annotations.Theme;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Theme("valo")
@SpringUI(path = WelcomeUI.NAME)
public class WelcomeUI extends MainUI {
    public static final String NAME = "/welcome";
    private final ProductService productService;
    private final PersonService personService;
    private final Label loggedUsername = new Label("Username");
    private final Button addProductButton = new Button("Add");
    private final Button editProductButton = new Button("Edit");
    private final Button deleteProductButton = new Button("Delete");
    private final Grid<Product> grid = new Grid<>("All products");

    private Product selectedProduct;

    @Autowired
    public WelcomeUI(ProductService productService, PersonService personService) {
        this.productService = productService;
        this.personService = personService;
    }

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        Page.getCurrent().setTitle("Spring Bazaar Application");

        User currentUser = getCurrentUser();
        loggedUsername.setValue("Welcome, " +
                (currentUser != null ? currentUser.getPerson().getShortName() : ""));

        List<Product> product = new ArrayList<>();
//        TODO revert when security will done
//        product.addAll(productService.listAllByPerson(currentUser.getPerson()));
        Person person = personService.getById(new BigInteger("1"));

        product.addAll(productService.listAllByPerson(person));
        grid.setItems(product);
        grid.getEditor().setEnabled(true);
        grid.addColumn(Product::getCaption).setCaption("Caption")
                .setEditorComponent(new TextField(), Product::setCaption);
        grid.addColumn(Product::getDescription).setCaption("Description")
                .setEditorComponent(new TextField(), Product::setDescription);
        grid.addColumn(Product::getPrice).setCaption("Price");
        grid.setSelectionMode(Grid.SelectionMode.SINGLE);

        grid.asSingleSelect().addValueChangeListener(selectionEvent -> {
            selectedProduct = selectionEvent.getValue();

        });
        grid.addSelectionListener(selectionEvent -> {
            deleteProductButton.setEnabled(selectedProduct != null);
            editProductButton.setEnabled(selectedProduct != null);
        });

        addProductButton.addClickListener(event -> getPage().setLocation(ProductEditor.NAME));
        addProductButton.setIcon(VaadinIcons.PLUS);

        editProductButton.setEnabled(false);
        editProductButton.setIcon(VaadinIcons.EDIT);
        editProductButton.addClickListener(event -> {
            //TODO Edit current item
            ProductEditor productEditor = new ProductEditor();
//            ((Product) grid.getSelectedItems().iterator().next())
            getPage().setLocation(productEditor.getPageName());
        });

        deleteProductButton.setEnabled(false);
        deleteProductButton.setIcon(VaadinIcons.TRASH);
        deleteProductButton.addClickListener(event -> {
            product.remove(selectedProduct);
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
