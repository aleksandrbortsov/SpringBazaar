package com.springbazaar.web.view;

import com.vaadin.annotations.Theme;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.annotation.SpringViewDisplay;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

@Theme("valo")
@SpringViewDisplay
@SpringUI(path = "/")
public class MainUI extends UI implements ViewDisplay {

    private Panel applicationViewDisplay;

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
