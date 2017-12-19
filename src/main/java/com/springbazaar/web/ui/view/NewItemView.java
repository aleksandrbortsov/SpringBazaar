package com.springbazaar.web.ui.view;

import com.springbazaar.service.ProductService;
import com.vaadin.data.HasValue;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

@SpringView(name = NewItemView.VIEW_NAME)
@UIScope
public class NewItemView extends VerticalLayout implements View {
    public static final String VIEW_NAME = "add-new-product";

    private final ProductService productService;

    private TextField itemCaption = new TextField("Caption");
    private TextArea itemDescription = new TextArea("Description");
    private TextField itemPrice = new TextField("Start price");
    private Button addItemButton = new Button("ADD");

    @Autowired
    public NewItemView(ProductService productService) {
        this.productService = productService;
    }

    @PostConstruct
    void init() {
        itemCaption.setWidth(25.0f, Unit.PERCENTAGE);
        itemCaption.setMaxLength(25);
        itemCaption.setRequiredIndicatorVisible(true);
        updateCaption(itemCaption, 0);

        itemDescription.setWidth(25.0f, Unit.PERCENTAGE);
        itemDescription.setRows(10);
        itemDescription.setMaxLength(2000);
        updateCaption(itemDescription, 0);
        itemDescription.addValueChangeListener(getStringValueChangeListener());

        itemPrice.setWidth(25.0f, Unit.PERCENTAGE);
        itemPrice.setMaxLength(25);
        updateCaption(itemPrice, 0);
        itemPrice.addValueChangeListener(getStringValueChangeListener());

        addItemButton.addClickListener((Button.ClickListener) clickEvent -> {

            getUI().getNavigator().navigateTo(DefaultView.VIEW_NAME);

        });
        VerticalLayout fields = new VerticalLayout(itemCaption, itemDescription, itemPrice, addItemButton);
        fields.setSizeFull();

        addComponent(fields);
    }

    private HasValue.ValueChangeListener<String> getStringValueChangeListener() {
        return valueChangeEvent ->
                updateCaption(((AbstractTextField) valueChangeEvent.getComponent()), valueChangeEvent.getValue().length());
    }

    private void updateCaption(AbstractTextField textField, final int textLength) {
        final StringBuilder builder = new StringBuilder();
        builder.append(String.valueOf(textLength));
        if (textField.getMaxLength() >= 0) {
            builder.append("/").append(textField.getMaxLength());
        }
        builder.append(" characters");
        textField.setCaption(builder.toString());
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        // This view is constructed in the init() method()
    }
}
