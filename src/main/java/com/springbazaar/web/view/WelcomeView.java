package com.springbazaar.web.view;


import com.springbazaar.domain.Person;
import com.springbazaar.domain.User;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

@SpringView(name = WelcomeView.VIEW_NAME)
public class WelcomeView extends MainVerticalView {
    public static final String VIEW_NAME = "welcome";


    @PostConstruct
    void init() {
        VerticalLayout rootLayout = new VerticalLayout();
        final Label userLogin = new Label("Welcome to Spring Bazaar!");
        addComponent(rootLayout);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        // This view is constructed in the init() method()
    }
}

