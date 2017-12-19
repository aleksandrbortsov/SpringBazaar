package com.springbazaar.web.ui;

import com.springbazaar.domain.User;
import com.springbazaar.web.ui.view.NewItemView;
import com.vaadin.annotations.Theme;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.annotation.SpringViewDisplay;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.spring.navigator.SpringNavigator;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.annotation.PostConstruct;

@Theme("valo")
@SpringViewDisplay
@SpringUI(path = "/welcome")
@UIScope
public class WelcomeUI extends UI implements ViewDisplay {

    private final CssLayout navigationBar = new CssLayout();
    private final ApplicationContext applicationContext;
    private final SpringViewProvider springViewProvider;
    private final SpringNavigator springNavigator;
    private Label loggedUsername = new Label("Username");
    private Panel applicationViewDisplay;

    @Autowired
    public WelcomeUI(ApplicationContext applicationContext,
                     SpringViewProvider springViewProvider,
                     SpringNavigator springNavigator) {
        this.applicationContext = applicationContext;

        this.springViewProvider = springViewProvider;
        this.springNavigator = springNavigator;
    }

    public static WelcomeUI getInstance() {
        return (WelcomeUI) UI.getCurrent();
    }

    @PostConstruct
    public void init() {
//        springNavigator.setErrorView(ErrorView.class);
//        springViewProvider.setAccessDeniedViewClass(AccessDeniedView.class);
        applicationViewDisplay = new Panel();
        applicationViewDisplay.setSizeFull();
    }

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        final VerticalLayout root = new VerticalLayout();
        root.setSizeFull();
        setContent(root);

        User currentUser = WelcomeUI.getInstance().getCurrentUser();
        loggedUsername.setValue("Welcome, " +
                (currentUser != null ? currentUser.getPerson().getShortName() : ""));
        VerticalLayout navigationCaption = new VerticalLayout(loggedUsername);

        HorizontalLayout navigationButtons = new HorizontalLayout(createNavigationButton("Add item",
                NewItemView.VIEW_NAME));


        navigationBar.addStyleName(ValoTheme.LINK_SMALL);
        navigationBar.addComponent(navigationCaption);
        navigationBar.addComponent(navigationButtons);
//        navigationBar.addComponent(createNavigationButton("Register",
//                RegistrationView.VIEW_NAME));
        root.addComponent(navigationBar);

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

    private User getCurrentUser() {
        if (isUserAnonymous()) {
            return null;
        } else {
            return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }
    }

    private boolean isUserAnonymous() {
        return SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken;
    }
}
