package com.springbazaar.web.ui.editor;

import com.springbazaar.domain.Person;
import com.springbazaar.domain.Product;
import com.springbazaar.domain.User;
import com.springbazaar.service.PersonService;
import com.springbazaar.service.ProductService;
import com.springbazaar.service.UserService;
import com.springbazaar.web.ui.MainUI;
import com.springbazaar.web.ui.WelcomeUI;
import com.vaadin.annotations.Theme;
import com.vaadin.data.BeanValidationBinder;
import com.vaadin.data.Binder;
import com.vaadin.data.converter.StringToBigDecimalConverter;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;

@SpringUI(path = ProductEditor.NAME)
@Theme("valo")
public class ProductEditor extends MainUI {
    public static final String NAME = "/productEditor";
    private final Binder<Product> productBeanValidationBinder = new BeanValidationBinder<>(Product.class);
    @Autowired
    private ProductService productService;
    @Autowired
    private PersonService personService;
    @Autowired
    private UserService userService;
    private TextField caption = new TextField("Caption");
    private TextArea description = new TextArea("Description");
    private TextField startPrice = new TextField("Start price");
    private TextField imageUrl = new TextField("Image");
    private Button addProductButton = new Button("ADD");

    public ProductEditor() {
        caption.setWidth(500, Unit.PIXELS);
        caption.setMaxLength(50);
        caption.setRequiredIndicatorVisible(true);
        productBeanValidationBinder.forField(caption)
                .bind("caption");

        description.setWidth(500, Unit.PIXELS);
        description.setRows(10);
        description.setMaxLength(500);
        productBeanValidationBinder.forField(description)
                .bind("description");


        startPrice.setWidth(100, Unit.PIXELS);
        productBeanValidationBinder.forField(startPrice)
                .withConverter(new StringToBigDecimalConverter("Could not convert value"))
                .bind("price");

        addProductButton.setEnabled(false);
        productBeanValidationBinder.addStatusChangeListener(statusChangeEvent ->
                addProductButton.setEnabled(productBeanValidationBinder.isValid()));
        addProductButton.setIcon(VaadinIcons.FILE_ADD);
        addProductButton.addClickListener((Button.ClickListener) clickEvent -> {
            User user = getCurrentUser();
            if (user == null) return;

            Calendar instance = Calendar.getInstance();
            instance.getTime();
            User userById = userService.getUserById(new BigInteger("1"));
            Product newProduct = new Product(caption.getValue(),
                    description.getValue(),
                    new BigDecimal("".equals(startPrice.getValue()) ? "0" : startPrice.getValue()),
                    imageUrl.getValue(),
                    instance.getTime(),
                    userById.getId());

            Person person = personService.getById(user.getPerson().getId());

            newProduct.setPerson(person);
            productService.saveOrUpdate(newProduct);

            getPage().setLocation(WelcomeUI.NAME);
        });

        VerticalLayout fields = new VerticalLayout(caption, description, imageUrl, startPrice, addProductButton);
        fields.setSpacing(true);
        fields.setMargin(new MarginInfo(true, true, true, false));
        fields.setSizeUndefined();

        VerticalLayout uiLayout = new VerticalLayout(fields);
        uiLayout.setSizeFull();
        uiLayout.setComponentAlignment(fields, Alignment.TOP_LEFT);

        setContent(uiLayout);
    }

    public String getPageName() {
        return NAME;
    }
}
