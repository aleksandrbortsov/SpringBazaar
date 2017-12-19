package com.springbazaar.web.ui;


import com.springbazaar.domain.FullName;
import com.springbazaar.domain.Person;
import com.springbazaar.domain.User;
import com.springbazaar.service.UserService;
import com.springbazaar.service.security.SecurityService;
import com.vaadin.annotations.Theme;
import com.vaadin.server.Sizeable;
import com.vaadin.server.UserError;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@SpringUI(path = "/registration")
@Theme("valo")
@UIScope
public class RegistrationUI extends UI {
    private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationUI.class);

    private static int tabIndex = 0;
    private final List<TextField> mandatoryFields = new ArrayList<>();
    private final TextField userFirstName = new TextField();
    private final TextField userMiddleName = new TextField();
    private final TextField userLastName = new TextField();
    private final Label userRoleLabel = new Label("Select role");
    private final CheckBox userRoleSeller = new CheckBox("Seller");
    private final CheckBox userRoleBuyer = new CheckBox("Buyer");
    private final TextField username = new TextField();
    private final PasswordField password = new PasswordField();
    private final PasswordField passwordRepeat = new PasswordField();
    private final UserService userService;
    private final SecurityService securityService;
    private boolean needFillFlag = false;


    @Autowired
    public RegistrationUI(UserService userService, SecurityService securityService) {
        this.userService = userService;
        this.securityService = securityService;
    }

    @Override
    protected void init(VaadinRequest request) {
        VerticalLayout leftLayout = new VerticalLayout();

        settingAndLayoutField(leftLayout, userFirstName, "First name", true);
        settingAndLayoutField(leftLayout, userMiddleName, "Middle name", false);
        settingAndLayoutField(leftLayout, userLastName, "Last name", true);


        leftLayout.addComponent(userRoleLabel);
        userRoleSeller.setTabIndex(tabIndex);
        tabIndex++;
        userRoleBuyer.setTabIndex(tabIndex);
        HorizontalLayout roleLayout = new HorizontalLayout(userRoleSeller, userRoleBuyer);
        leftLayout.addComponent(roleLayout);

        VerticalLayout rightLayout = new VerticalLayout();
        settingAndLayoutField(rightLayout, username, "Email", true);
        settingAndLayoutField(rightLayout, password, "Password", true);
        settingAndLayoutField(rightLayout, passwordRepeat, "Repeat password", true);

        final Button okButton = new Button("CREATE AN ACCOUNT");
        okButton.setTabIndex(tabIndex);
        okButton.addClickListener((Button.ClickListener) clickEvent -> {
            needFillFlag = false;
            checkFilledMandatoryFields();
            checkSelectedRoles(userRoleLabel, userRoleSeller, userRoleBuyer);
            List<String> roles = addRoleFromForm(userRoleSeller, userRoleBuyer);
            checkParity(password, passwordRepeat);
            if (!needFillFlag) {
                User user = new User(username.getValue(), password.getValue());
                Person person = new Person(new FullName(userFirstName.getValue(),
                        userMiddleName.getValue(),
                        userLastName.getValue()));
                userService.saveOrUpdate(user, person, roles);
                securityService.login(username.getValue(), password.getValue());
                LOGGER.debug("New User " + user + " has been registered");
                Notification.show("New User has been registered", Notification.Type.TRAY_NOTIFICATION);

                getPage().setLocation("/welcome");
            }
        });
        rightLayout.addComponent(okButton);

        HorizontalLayout registrationForm = new HorizontalLayout(leftLayout, rightLayout);

        VerticalLayout root = new VerticalLayout(registrationForm);
        setContent(root);
    }

    private List<String> addRoleFromForm(CheckBox userRoleSeller, CheckBox userRoleBuyer) {
        List<String> roles = new ArrayList<>();
        if (userRoleSeller.getValue()) {
            roles.add(userRoleSeller.getCaption());
        }
        if (userRoleBuyer.getValue()) {
            roles.add(userRoleBuyer.getCaption());
        }
        return roles;
    }

    private void settingAndLayoutField(VerticalLayout layout, TextField field, String caption, boolean isRequired) {
        field.setWidth(25.0f, Sizeable.Unit.PERCENTAGE);
        field.setTabIndex(tabIndex);
        tabIndex++;
        field.setCaption(caption);
        field.setMaxLength(25);
        field.setRequiredIndicatorVisible(isRequired);
        if (isRequired) {
            mandatoryFields.add(field);
        }
        layout.addComponent(field);
    }

    private void checkParity(PasswordField userPassword, PasswordField userPasswordRepeat) {
        if (!userPassword.getValue().equals(userPasswordRepeat.getValue())) {
            needFillFlag = true;
            userPasswordRepeat.setComponentError(new UserError("Please check that the password is repeated correctly."));
        }
    }

    private void checkFilledMandatoryFields() {
        for (TextField field : mandatoryFields) {
            if (StringUtils.isEmpty(field.getValue())) {
                updateCaption(field);
                needFillFlag = true;
            }
        }
    }

    private void checkSelectedRoles(Label userRoleLabel, CheckBox userRoleSeller, CheckBox userRoleBuyer) {
        if (!userRoleSeller.getValue()
                && !userRoleBuyer.getValue()) {
            needFillFlag = true;
            userRoleLabel.setComponentError(new UserError("Please select role(s)"));
        }
    }

    private void updateCaption(TextField textField) {
        textField.setPlaceholder("This mandatory field");
        textField.setComponentError(new UserError("Please fill this field"));
        Notification.show("Please fill mandatory fields with *", Notification.Type.TRAY_NOTIFICATION);
    }
}
