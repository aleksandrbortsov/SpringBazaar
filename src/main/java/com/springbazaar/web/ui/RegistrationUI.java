package com.springbazaar.web.ui;

import com.springbazaar.domain.Person;
import com.springbazaar.domain.User;
import com.springbazaar.domain.util.FullName;
import com.springbazaar.domain.util.type.RoleType;
import com.springbazaar.service.UserService;
import com.springbazaar.service.security.SecurityService;
import com.vaadin.annotations.Theme;
import com.vaadin.data.BeanValidationBinder;
import com.vaadin.data.Validator;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Sizeable;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@SpringUI(path = RegistrationUI.NAME)
@Theme("valo")
public class RegistrationUI extends UI {
    public static final String NAME = "/registration";
    private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationUI.class);
    public static final String PREFIX_ROLE = "ROLE_";

    private static int tabIndex = 0;
    private final TextField userFirstName = new TextField();
    private final TextField userMiddleName = new TextField();
    private final TextField userLastName = new TextField();
    private final Label userRoleLabel = new Label("Select role");
    private final CheckBox userRoleSeller = new CheckBox("Seller");
    private final CheckBox userRoleBuyer = new CheckBox("Buyer");
    private final TextField username = new TextField();
    private final PasswordField password = new PasswordField();
    private final PasswordField passwordConfirm = new PasswordField();
    private final Label validationStatus = new Label();
    private final Button registrationButton = new Button("CREATE AN ACCOUNT");
    private final UserService userService;
    private final SecurityService securityService;
    private BeanValidationBinder<User> userBeanValidationBinder = new BeanValidationBinder<>(User.class);
    private BeanValidationBinder<FullName> fullNameBeanValidationBinder = new BeanValidationBinder<>(FullName.class);

    @Autowired
    public RegistrationUI(UserService userService, SecurityService securityService) {
        this.userService = userService;
        this.securityService = securityService;
    }

    @Override
    protected void init(VaadinRequest request) {
        VerticalLayout leftLayout = new VerticalLayout();
        settingAndLayoutField(leftLayout, userFirstName, "First name");
        fullNameBeanValidationBinder.forField(userFirstName)
                .asRequired("Please provide your name")
                .withValidator(new StringLengthValidator("Name must be at least 2 characters long",
                        2, null))
                .bind(FullName::getFirstName, FullName::setFirstName);

        settingAndLayoutField(leftLayout, userMiddleName, "Middle name");
        fullNameBeanValidationBinder.forField(userMiddleName)
                .bind(FullName::getMiddleName, FullName::setMiddleName);

        settingAndLayoutField(leftLayout, userLastName, "Last name");
        fullNameBeanValidationBinder.forField(userLastName)
                .asRequired("Please provide your surname")
                .withValidator(new StringLengthValidator("Surname must be at least 2 characters long",
                        2, null))
                .bind(FullName::getLastName, FullName::setLastName);

        fullNameBeanValidationBinder.setBean(new FullName());

        leftLayout.addComponent(userRoleLabel);
        userRoleSeller.setTabIndex(tabIndex);
        tabIndex++;
        userRoleBuyer.setTabIndex(tabIndex);
        HorizontalLayout roleLayout = new HorizontalLayout(userRoleSeller, userRoleBuyer);
        leftLayout.addComponent(roleLayout);

        VerticalLayout rightLayout = new VerticalLayout();
        settingAndLayoutField(rightLayout, username, "Email");
        userBeanValidationBinder.forField(username)
                .asRequired("Email may not be empty")
                .withValidator(new EmailValidator("Not a valid email address"))
                .bind(User::getUsername, User::setUsername);

        settingAndLayoutField(rightLayout, password, "Password");
        userBeanValidationBinder.forField(password)
                .asRequired("Password may not be empty")
                .withValidator(new StringLengthValidator("Password must be at least 7 characters long",
                        7, null))
                .bind(User::getPassword, User::setPassword);

        settingAndLayoutField(rightLayout, passwordConfirm, "Confirm your password");
        userBeanValidationBinder.forField(passwordConfirm)
                .asRequired("Please confirm your password")
                .bind(User::getPassword, (user, password) -> {
                });

        userBeanValidationBinder.withValidator(Validator.from(user -> {
            if (password.isEmpty() || passwordConfirm.isEmpty()) {
                return true;
            } else {
                return Objects.equals(password.getValue(),
                        passwordConfirm.getValue());
            }
        }, "Entered password and confirmation password must match"));


        userBeanValidationBinder.setStatusLabel(validationStatus);

        userBeanValidationBinder.setBean(new User());


        registrationButton.setEnabled(false);
        userBeanValidationBinder.addStatusChangeListener(
                event -> registrationButton.setEnabled( (userBeanValidationBinder.isValid()&fullNameBeanValidationBinder.isValid())));
        fullNameBeanValidationBinder.addStatusChangeListener(
                event -> registrationButton.setEnabled(
                        (userBeanValidationBinder.isValid()&fullNameBeanValidationBinder.isValid()))
        );

        registrationButton.setTabIndex(tabIndex);
        registrationButton.setIcon(VaadinIcons.USER_CHECK);
        registrationButton.addClickListener((Button.ClickListener) clickEvent -> {
            List<String> roles = addRoleFromForm(userRoleSeller, userRoleBuyer);
            User user = userBeanValidationBinder.getBean();
            Person person = new Person(fullNameBeanValidationBinder.getBean());
            userService.saveOrUpdate(user, person, roles);
            securityService.login(username.getValue(), password.getValue());
            LOGGER.debug("New User " + user + " has been registered");
            Notification.show("User " + user.getUsername() + " has been registered",
                    Notification.Type.TRAY_NOTIFICATION);
            getPage().setLocation("/welcome");

        });
        rightLayout.addComponent(validationStatus);
        rightLayout.addComponent(registrationButton);

        HorizontalLayout registrationForm = new HorizontalLayout(leftLayout, rightLayout);
        registrationForm.setMargin(true);
        registrationForm.addStyleName("outlined");
        registrationForm.setSizeUndefined();

        VerticalLayout rootUi = new VerticalLayout(registrationForm);
        rootUi.setSizeFull();
        rootUi.setComponentAlignment(registrationForm, Alignment.MIDDLE_CENTER);
        setContent(rootUi);
    }

    private List<String> addRoleFromForm(CheckBox userRoleSeller, CheckBox userRoleBuyer) {
        List<String> roles = new ArrayList<>();
        if (userRoleSeller.getValue()) {
            roles.add(PREFIX_ROLE + userRoleSeller.getCaption());
        }
        if (userRoleBuyer.getValue()) {
            roles.add(PREFIX_ROLE + userRoleBuyer.getCaption());
        }
        roles.add(RoleType.ROLE_USER.toString());
        return roles;
    }

    private void settingAndLayoutField(VerticalLayout layout, TextField field, String caption) {
        field.setWidth(25.0f, Sizeable.Unit.PERCENTAGE);
        field.setTabIndex(tabIndex);
        tabIndex++;
        field.setCaption(caption);
        field.setMaxLength(25);
        layout.addComponent(field);
    }
}
