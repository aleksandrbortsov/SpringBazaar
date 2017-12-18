package com.springbazaar.web.view;


import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.annotation.Resource;

@SpringUI(path = "/login")
@Theme("valo")
public class LoginUI extends UI {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginUI.class);

    @Resource(name = "UserDetailsServiceImpl")
    private UserDetailsService userDetailsService;

    private TextField user = new TextField("User:");
    private PasswordField password = new PasswordField("Password:");
    private Button loginButton = new Button("Login", this::loginButtonClick);

    @Override
    protected void init(VaadinRequest request) {
        setSizeFull();

        user.setWidth("300px");
        user.setRequiredIndicatorVisible(true);

        password.setWidth("300px");
        password.setRequiredIndicatorVisible(true);
        password.setValue("");

        VerticalLayout fields = new VerticalLayout(user, password, loginButton);
        fields.setCaption("Please login to access the application");
        fields.setSpacing(true);
        fields.setMargin(new MarginInfo(true, true, true, false));
        fields.setSizeUndefined();

        VerticalLayout uiLayout = new VerticalLayout(fields);
        uiLayout.setSizeFull();
        uiLayout.setComponentAlignment(fields, Alignment.MIDDLE_CENTER);
        setFocusedComponent(user);

        setContent(uiLayout);
    }

    public void loginButtonClick(Button.ClickEvent e) {
        //authorize/authenticate user
        //tell spring that my user is authenticated and dispatch to my mainUI
        userDetailsService.loadUserByUsername(user.getValue());
    }
}
