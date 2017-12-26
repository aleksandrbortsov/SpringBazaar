package com.springbazaar.web.ui;

import com.springbazaar.domain.User;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

public class MainUI extends UI {

    public User getCurrentUser() {
        if (isUserAnonymous()) {
            return null;
        } else {
            return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }
    }

    public static boolean isUserAnonymous() {
        return SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken;
    }

    @Override
    protected void init(VaadinRequest vaadinRequest) {
//TODO create navigator
    }
}
