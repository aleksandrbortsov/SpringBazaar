package com.springbazaar.web.view;

import com.vaadin.annotations.Theme;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.annotation.SpringViewDisplay;
import com.vaadin.spring.navigator.SpringNavigator;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.vaadin.spring.security.VaadinSecurity;

import javax.annotation.PostConstruct;

@Theme("valo")
@SpringViewDisplay
@SpringUI(path = "/")
public class MainUI extends UI implements ViewDisplay {

    private final ApplicationContext applicationContext;
    private final VaadinSecurity vaadinSecurity;
    private final SpringViewProvider springViewProvider;
    private final SpringNavigator springNavigator;

    private Panel applicationViewDisplay;

    @Autowired
    public MainUI(ApplicationContext applicationContext,
                  VaadinSecurity vaadinSecurity,
                  SpringViewProvider springViewProvider,
                  SpringNavigator springNavigator) {
        this.applicationContext = applicationContext;
        this.vaadinSecurity = vaadinSecurity;
        this.springViewProvider = springViewProvider;
        this.springNavigator = springNavigator;
    }

    @PostConstruct
    public void init() {
        springNavigator.setErrorView(ErrorView.class);
        springViewProvider.setAccessDeniedViewClass(AccessDeniedView.class);
        applicationViewDisplay = new Panel();
        applicationViewDisplay.setSizeFull();
    }

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        final VerticalLayout root = new VerticalLayout();
        root.setSizeFull();
        setContent(root);

        final CssLayout navigationBar = new CssLayout();
        navigationBar.addStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
        navigationBar.addComponent(createNavigationButton("Login",
                LoginView.VIEW_NAME));
        navigationBar.addComponent(createNavigationButton("Register",
                RegistrationView.VIEW_NAME));
        root.addComponent(navigationBar);

        applicationViewDisplay = new Panel();
        applicationViewDisplay.setSizeFull();
        root.addComponent(applicationViewDisplay);
        root.setExpandRatio(applicationViewDisplay, 1.0f);
    }

    private Button createNavigationButton(String caption, final String viewName) {
        Button button = new Button(caption);
        button.addStyleName(ValoTheme.LINK_SMALL);
        button.addClickListener(event -> getUI().getNavigator().navigateTo(viewName));
        return button;
    }

    @Override
    public void showView(View view) {
        applicationViewDisplay.setContent((Component) view);
    }

}
