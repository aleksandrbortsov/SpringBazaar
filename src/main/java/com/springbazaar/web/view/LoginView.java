package com.springbazaar.web.view;

import com.springbazaar.controller.UserController;
import com.springbazaar.domain.Person;
import com.springbazaar.domain.User;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

@SpringView(name = LoginView.VIEW_NAME)
public class LoginView extends MainVerticalView {
    public static final String VIEW_NAME = "";

    private UserController userController;

    @Autowired
    public LoginView(UserController userController) {
        this.userController = userController;
    }

    @PostConstruct
    void init() {
        VerticalLayout rootLayout = new VerticalLayout();

        final TextField userLogin = new TextField();
        settingAndLayoutField(rootLayout, userLogin, "Email", true);

        final PasswordField userPassword = new PasswordField();
        settingAndLayoutField(rootLayout, userPassword, "Password", true);

        final Button okButton = new Button("LOGIN");
        okButton.setTabIndex(tabIndex);
        okButton.addClickListener((Button.ClickListener) clickEvent -> {
           needFillFlag=false;
           checkFilledMandatoryFields();
            if (!needFillFlag) {
                User user = new User(userLogin.getValue(), userPassword.getValue());
                Person person = userController.check(user);
                if (person != null) {
                    Notification.show(person.getShortName() + ", welcome to Spring Bazaar!",
                            Notification.Type.TRAY_NOTIFICATION);
                } else {
                    Notification.show("Username/Email or Password is incorrect",
                            Notification.Type.ERROR_MESSAGE);

                }
            }
        });
        rootLayout.addComponent(okButton);
        addComponent(rootLayout);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        // This view is constructed in the init() method()
    }
}

