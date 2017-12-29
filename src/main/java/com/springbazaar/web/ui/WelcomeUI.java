package com.springbazaar.web.ui;

import com.springbazaar.domain.User;
import com.springbazaar.service.ProductService;
import com.springbazaar.web.ui.tool.ProductDataProvider;
import com.springbazaar.web.ui.tool.component.LogoutLink;
import com.springbazaar.web.ui.tool.component.ProductManager;
import com.vaadin.annotations.Theme;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;

@Theme("valo")
@SpringUI(path = WelcomeUI.NAME)
public class WelcomeUI extends MainUI {
    public static final String NAME = "/welcome";
    private final Label loggedUsername = new Label("Username");
    private final LogoutLink logoutLink = new LogoutLink();
    private ProductDataProvider productProvider;
//    private ListDataProvider productProvider;
    private final ProductService productService;

    @Autowired
    public WelcomeUI(ProductService productService) {
        this.productService = productService;
    }

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        Page.getCurrent().setTitle("Spring Bazaar Application");

        User currentUser = getCurrentUser();
        if (currentUser != null) {
            loggedUsername.setValue("Welcome, " + currentUser.getPerson().getShortName() + "!");
            logoutLink.updateVisibility();
            productProvider =
                    new ProductDataProvider(productService);
//            productProvider =
//                    new ListDataProvider<>(productService.listByPerson(currentUser.getPerson()));

        }
        final HorizontalLayout topLayout = new HorizontalLayout(loggedUsername, logoutLink);

        /*Tab Sheet*/
        final TabSheet mainTabSheet = new TabSheet();
        mainTabSheet.setSizeFull();
        mainTabSheet.addTab(new ProductManager(productProvider, productService), "Products");
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
}
