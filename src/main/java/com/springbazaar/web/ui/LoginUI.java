package com.springbazaar.web.ui;


import com.springbazaar.service.security.SecurityService;
import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@SpringUI(path = "/login")
@Theme("valo")
@UIScope
public class LoginUI extends UI {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginUI.class);

    @Autowired
    private SecurityService securityService;

    private TextField username = new TextField("Username:");
    private PasswordField password = new PasswordField("Password:");
    private Button loginButton = new Button("Login", this::loginButtonClick);

    @Override
    protected void init(VaadinRequest request) {
        setSizeFull();

        username.setWidth("300px");
        username.setRequiredIndicatorVisible(true);


        password.setWidth("300px");
        password.setRequiredIndicatorVisible(true);
        password.setValue("");


        VerticalLayout fields = new VerticalLayout(username, password, loginButton);
        fields.setCaption("Please login to access the application");
        fields.setSpacing(true);
        fields.setMargin(new MarginInfo(true, true, true, false));
        fields.setSizeUndefined();

        VerticalLayout uiLayout = new VerticalLayout(fields);
        uiLayout.setSizeFull();
        uiLayout.setComponentAlignment(fields, Alignment.MIDDLE_CENTER);
        setFocusedComponent(username);

        setContent(uiLayout);
    }

    public void loginButtonClick(Button.ClickEvent e) {
        //authorize/authenticate user
        //tell spring that my user is authenticated and dispatch to my mainUI
//        UserDetails userDetails = userDetailsService.loadUserByUsername(username.getValue());

//        Authentication auth = new UsernamePasswordAuthenticationToken(username.getValue(), password.getValue());
//        Authentication authenticated = daoAuthenticationProvider.authenticate(auth);
//        SecurityContextHolder.getContext().setAuthentication(authenticated);

        securityService.login(username.getValue(), password.getValue());

        //redirect to main application
        getPage().setLocation("/welcome");
    }
}
