package com.springbazaar.web.view;


import com.springbazaar.domain.User;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

@SpringView(name = NewProductView.VIEW_NAME)
public class NewProductView extends VerticalLayout implements View {
    public static final String VIEW_NAME = "add-new-product";



    @PostConstruct
    void init() {
        // Create the custom layout and set it as a component in
        // the current layout
        VerticalLayout root = new VerticalLayout();

        // Create components and bind them to the location tags
        // in the custom layout.

        final TextField userLogin = new TextField();
        userLogin.setWidth(25.0f, Unit.PERCENTAGE);
        userLogin.setCaption("Username or Email*");

        userLogin.setMaxLength(25);
        updateCaption(userLogin, 0);
        userLogin.addValueChangeListener(valueChangeEvent ->
                updateCaption(((TextField) valueChangeEvent.getComponent()), valueChangeEvent.getValue().length()));
        root.addComponent(userLogin);

        final PasswordField userPassword = new PasswordField();
        userPassword.setWidth(25.0f, Unit.PERCENTAGE);
        userPassword.setCaption("Password*");
        userPassword.setMaxLength(25);
        updateCaption(userPassword, 0);
        userPassword.addValueChangeListener(valueChangeEvent ->
                updateCaption(((PasswordField) valueChangeEvent.getComponent()), valueChangeEvent.getValue().length()));
        root.addComponent(userPassword);

        final Button okButton = new Button("LOGIN");
        okButton.addClickListener((Button.ClickListener) clickEvent -> {

        });
        root.addComponent(okButton);

        addComponent(root);
    }

    private void updateCaption(TextField textField, final int textLength) {
        final StringBuilder builder = new StringBuilder();
        builder.append(String.valueOf(textLength));
        if (textField.getMaxLength() >= 0) {
            builder.append("/").append(textField.getMaxLength());
        }
        builder.append(" characters");
        textField.setCaption(textField.getCaption() + builder.toString());
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        // This view is constructed in the init() method()
    }
}
