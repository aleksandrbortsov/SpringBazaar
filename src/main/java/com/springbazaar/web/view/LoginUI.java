package com.springbazaar.web.view;

import com.vaadin.annotations.Theme;
import com.vaadin.event.ShortcutAction;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.vaadin.spring.security.shared.VaadinSharedSecurity;

@SpringUI(path = "/login")
@Theme("valo")
public class LoginUI extends UI {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginUI.class);

    private TextField username = new TextField("Username");
    private PasswordField passwordField = new PasswordField("Password");
    private CheckBox rememberMe = new CheckBox("Remember me");
    private Button loginButton = new Button("Login");
    private Label loginFailedLabel;
    private Label loggedOutLabel;

    private VaadinSharedSecurity vaadinSecurity;

    @Autowired
    public LoginUI(VaadinSharedSecurity vaadinSecurity) {
        this.vaadinSecurity = vaadinSecurity;
    }

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        getPage().setTitle("Vaadin Security Demo Login");

        FormLayout loginForm = new FormLayout();
        loginForm.setSizeUndefined();
        loginButton.addStyleName(ValoTheme.BUTTON_PRIMARY);
        loginButton.setDisableOnClick(true);
        loginButton.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        loginButton.addClickListener(clickEvent -> login());
        loginForm.addComponent(username);
        loginForm.addComponent(passwordField);
        loginForm.addComponent(rememberMe);
        loginForm.addComponent(loginButton);

        VerticalLayout loginLayout = new VerticalLayout();
        loginLayout.setSpacing(true);
        loginLayout.setSizeUndefined();

        if (vaadinRequest.getParameter("logout") != null) {
            loggedOutLabel = new Label("You have been logged out!");
            loggedOutLabel.addStyleName(ValoTheme.LABEL_SUCCESS);
            loggedOutLabel.setSizeUndefined();
            loginLayout.addComponent(loggedOutLabel);
            loginLayout.setComponentAlignment(loggedOutLabel, Alignment.BOTTOM_CENTER);
        }

        loginLayout.addComponent(loginFailedLabel = new Label());
        loginLayout.setComponentAlignment(loginFailedLabel, Alignment.BOTTOM_CENTER);
        loginFailedLabel.setSizeUndefined();
        loginFailedLabel.addStyleName(ValoTheme.LABEL_FAILURE);
        loginFailedLabel.setVisible(false);

        loginLayout.addComponent(loginForm);
        loginLayout.setComponentAlignment(loginForm, Alignment.TOP_CENTER);

        VerticalLayout rootLayout = new VerticalLayout(loginLayout);
        rootLayout.setSizeFull();
        rootLayout.setComponentAlignment(loginLayout, Alignment.MIDDLE_CENTER);
        setContent(rootLayout);
        setSizeFull();
    }

    private void login() {
        try {
            vaadinSecurity.login(username.getValue(), passwordField.getValue(), rememberMe.getValue());
        } catch (AuthenticationException ex) {
            username.focus();
            username.selectAll();
            passwordField.setValue("");
            loginFailedLabel.setValue(String.format("Login failed: %s", ex.getMessage()));
            loginFailedLabel.setVisible(true);
            if (loggedOutLabel != null) {
                loggedOutLabel.setVisible(false);
            }
        } catch (Exception ex) {
            Notification.show("An unexpected error occurred", ex.getMessage(), Notification.Type.ERROR_MESSAGE);
            LOGGER.error("Unexpected error while logging in", ex);
        } finally {
            loginButton.setEnabled(true);
        }
    }
}
