package com.springbazaar.web.ui.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import javax.annotation.PostConstruct;

@SpringView(name = AccessDeniedView.VIEW_NAME)
@UIScope
public class AccessDeniedView extends VerticalLayout implements View {
    public static final String VIEW_NAME = "/accessDenied";

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }

    @PostConstruct
    public void init() {
        Label label = new Label("this is AccessDeniedView.");
        label.addStyleName(ValoTheme.LABEL_FAILURE);
        addComponent(label);
    }
}
