package com.springbazaar.web.ui;


import com.springbazaar.service.security.SecurityService;
import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@SpringUI(path = AccessDeniedUI.NAME)
@Theme("valo")
public class AccessDeniedUI extends UI {
    public static final String NAME="/accessDenied";
    private static final Logger LOGGER = LoggerFactory.getLogger(AccessDeniedUI.class);

    @Autowired
    private SecurityService securityService;

    Label label = new Label("this is AccessDeniedView.");

    @Override
    protected void init(VaadinRequest request) {

        label.addStyleName(ValoTheme.LABEL_FAILURE);


        VerticalLayout uiLayout = new VerticalLayout(label);
        uiLayout.setSizeFull();



        setContent(uiLayout);
    }
}
