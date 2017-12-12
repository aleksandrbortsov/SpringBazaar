package com.springbazaar.SpringBazaar;

import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;

@SpringUI(path = "/register/user_form.html")
public class ApplicationUI extends UI {
    @Override
    protected void init(VaadinRequest vaadinRequest) {


        // Create the custom layout and set it as a component in
        // the current layout
        CustomLayout sample = new CustomLayout();

        // Create components and bind them to the location tags
        // in the custom layout.
        final TextField username = new TextField();
        username.setWidth(100.0f, Unit.PERCENTAGE);
        sample.addComponent(username, "username");

        final PasswordField password = new PasswordField();
        password.setWidth(100.0f, Unit.PERCENTAGE);
        sample.addComponent(password, "password");

        final Button ok = new Button("Login");

        sample.addComponent(ok, "okbutton");

        setContent(sample);
    }
}
