package com.springbazaar.web.ui.tool.component;

import com.springbazaar.web.ui.RegistrationUI;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.ExternalResource;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Link;

public class RegisterLink extends CustomComponent {

    public RegisterLink() {
        Link registerLink = new Link("Register", new ExternalResource(RegistrationUI.NAME));
        registerLink.setIcon(VaadinIcons.USER_STAR);
        setCompositionRoot(registerLink);
    }
}
