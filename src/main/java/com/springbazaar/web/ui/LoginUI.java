package com.springbazaar.web.ui;

import com.springbazaar.service.security.SecurityService;
import com.vaadin.annotations.Theme;
import com.vaadin.event.ShortcutAction;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;

@SpringUI(path = LoginUI.NAME)
@Theme("valo")
public class LoginUI extends UI {
    public static final String NAME="/login";

    private final SecurityService securityService;

    private final TextField username = new TextField("Username:");
    private final PasswordField password = new PasswordField("Password:");
    private final CheckBox rememberMe = new CheckBox("Remember me");
    private final Button loginButton = new Button("Login", this::loginButtonClick);

    @Autowired
    public LoginUI(SecurityService securityService) {
        this.securityService = securityService;
    }

    @Override
    protected void init(VaadinRequest request) {
        FormLayout loginForm = new FormLayout();
        loginForm.setMargin(true);
        loginForm.addStyleName("outlined");
        loginForm.setSizeUndefined();

        username.setWidth(100.0f, Unit.PERCENTAGE);
        username.setRequiredIndicatorVisible(true);
        loginForm.addComponent(username);

        password.setWidth(100.0f, Unit.PERCENTAGE);
        password.setRequiredIndicatorVisible(true);
        loginForm.addComponent(password);

        loginForm.addComponent(rememberMe);
        loginButton.setIcon(VaadinIcons.KEY);
        loginButton.setDisableOnClick(true);
        loginButton.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        loginForm.addComponent(loginButton);

        setFocusedComponent(username);
        VerticalLayout rootUi = new VerticalLayout(loginForm);
        rootUi.setSizeFull();
        rootUi.setComponentAlignment(loginForm, Alignment.MIDDLE_CENTER);

        setContent(rootUi);
    }

    private void loginButtonClick(Button.ClickEvent e) {
        if (securityService.login(username.getValue(), password.getValue())) {
            getPage().setLocation("/welcome");
        }
    }
}
