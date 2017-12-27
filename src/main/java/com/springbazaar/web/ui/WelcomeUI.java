package com.springbazaar.web.ui;

import com.springbazaar.domain.Product;
import com.springbazaar.domain.User;
import com.springbazaar.service.ProductService;
import com.springbazaar.web.ui.tool.component.LogoutLink;
import com.springbazaar.web.ui.tool.component.ProductContainer;
import com.vaadin.annotations.Theme;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Theme("valo")
@SpringUI(path = WelcomeUI.NAME)
public class WelcomeUI extends MainUI {
    public static final String NAME = "/welcome";
    private final Label loggedUsername = new Label("Username");
    private final LogoutLink logoutLink = new LogoutLink();
    private final ProductService productService;

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
        logoutLink.updateVisibility();
        final HorizontalLayout topLayout = new HorizontalLayout(loggedUsername, logoutLink);

        List<Product> products = getProductItems(currentUser);

        /*Tab Sheet*/
        final TabSheet mainTabSheet = new TabSheet();
        mainTabSheet.setSizeFull();
        mainTabSheet.addTab(new ProductContainer(products, productService), "Products");
//TODO Orders tab
        final Label label = new Label("all orders table", ContentMode.HTML);
        label.setWidth(100.0f, Unit.PERCENTAGE);
        final VerticalLayout layout = new VerticalLayout(label);
        layout.setMargin(true);
        mainTabSheet.addTab(label, "Orders");
        /*Tab Sheet*/

        VerticalLayout uiLayout = new VerticalLayout(topLayout, mainTabSheet);
        uiLayout.setSizeFull();
        VerticalLayout rootLayout = new VerticalLayout(uiLayout);
        rootLayout.setComponentAlignment(uiLayout, Alignment.TOP_LEFT);
        setContent(rootLayout);
    }

    private List<Product> getProductItems(User currentUser) {
        List<Product> products = new ArrayList<>();
        if (currentUser != null) {
            products.addAll(productService.listAllByPerson(currentUser.getPerson()));
        }
        return products;
    }

}
