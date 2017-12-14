package com.springbazaar.web.view;

import com.springbazaar.controller.UserController;
import com.springbazaar.domain.FullName;
import com.springbazaar.domain.Person;
import com.springbazaar.domain.User;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.UserError;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@SpringView(name = RegistrationView.VIEW_NAME)
public class RegistrationView extends MainVerticalView {
    public static final String VIEW_NAME = "registration";
    private final UserController userController;

    @Autowired
    public RegistrationView(UserController userController) {
        this.userController = userController;
    }

    @PostConstruct
    void init() {
        VerticalLayout leftLayout = new VerticalLayout();

        final TextField userFirstName = new TextField();
        settingAndLayoutField(leftLayout, userFirstName, "First name", true);

        final TextField userMiddleName = new TextField();
        settingAndLayoutField(leftLayout, userMiddleName, "Middle name", false);

        final TextField userLastName = new TextField();
        settingAndLayoutField(leftLayout, userLastName, "Last name", true);

        Label userRoleLabel = new Label("Select role");
        leftLayout.addComponent(userRoleLabel);

        CheckBox userRoleSeller = new CheckBox("Seller");
        userRoleSeller.setTabIndex(tabIndex);
        tabIndex++;
        CheckBox userRoleBuyer = new CheckBox("Buyer");
        userRoleBuyer.setTabIndex(tabIndex);
        HorizontalLayout roleLayout = new HorizontalLayout(userRoleSeller, userRoleBuyer);

        leftLayout.addComponent(roleLayout);

        VerticalLayout rightLayout = new VerticalLayout();

        final TextField userLogin = new TextField();
        settingAndLayoutField(rightLayout, userLogin, "Email", true);

        final PasswordField userPassword = new PasswordField();
        settingAndLayoutField(rightLayout, userPassword, "Password", true);
        final PasswordField userPasswordRepeat = new PasswordField();
        settingAndLayoutField(rightLayout, userPasswordRepeat, "Repeat password", true);

        final Button okButton = new Button("CREATE AN ACCOUNT");
        okButton.setTabIndex(tabIndex);
        okButton.addClickListener((Button.ClickListener) clickEvent -> {
            needFillFlag = false;
            checkFilledMandatoryFields();
            checkSelectedRoles(userRoleLabel, userRoleSeller, userRoleBuyer);
            List<String> roles = addRoleFromForm(userRoleSeller, userRoleBuyer);
            checkParity(userPassword, userPasswordRepeat);
            if (!needFillFlag) {
                User user = new User(userLogin.getValue(), userPassword.getValue());
                Person person = new Person(new FullName(userFirstName.getValue(),
                        userMiddleName.getValue(),
                        userLastName.getValue()));
                userController.addNewUser(user, person, roles);
                Notification.show("New User has been registered", Notification.Type.TRAY_NOTIFICATION);
                getUI().getNavigator().navigateTo(WelcomeView.VIEW_NAME);
            }
        });
        rightLayout.addComponent(okButton);
        HorizontalLayout leftRightLayout = new HorizontalLayout(leftLayout, rightLayout);

        VerticalLayout rootLayout = new VerticalLayout(leftRightLayout);

        addComponent(rootLayout);
    }

    private void checkParity(PasswordField userPassword, PasswordField userPasswordRepeat) {
        if (!userPassword.getValue().equals(userPasswordRepeat.getValue())) {
            needFillFlag = true;
            userPasswordRepeat.setComponentError(new UserError("Please check that the password is repeated correctly."));
        }
    }

    private void checkSelectedRoles(Label userRoleLabel, CheckBox userRoleSeller, CheckBox userRoleBuyer) {
        if (!userRoleSeller.getValue()
                && !userRoleBuyer.getValue()) {
            needFillFlag = true;
            userRoleLabel.setComponentError(new UserError("Please select role(s)"));
        }
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

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        // This view is constructed in the init() method()
    }
}
