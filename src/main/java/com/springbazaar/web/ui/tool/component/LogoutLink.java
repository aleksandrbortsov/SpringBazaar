package com.springbazaar.web.ui.tool.component;

import com.springbazaar.web.ui.MainUI;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.ExternalResource;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Link;

public class LogoutLink extends CustomComponent {

    public LogoutLink() {
        Link logoutLink = new Link("Logout", new ExternalResource("/logout"));
        logoutLink.setIcon(VaadinIcons.SIGN_OUT);
        setCompositionRoot(logoutLink);
    }

    public void updateVisibility() {
        setVisible(!MainUI.isUserAnonymous());
    }
}
