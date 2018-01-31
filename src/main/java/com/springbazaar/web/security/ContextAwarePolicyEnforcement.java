package com.springbazaar.web.security;

import com.vaadin.ui.Notification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class ContextAwarePolicyEnforcement {
    private final PolicyEnforcement policy;

    @Autowired
    public ContextAwarePolicyEnforcement(PolicyEnforcement policy) {
        this.policy = policy;
    }

    public void checkPermission(Object resource, String permission) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        Map<String, Object> environment = new HashMap<>();
        environment.put("time", new Date());

        if (!policy.check(auth.getPrincipal(), resource, permission, environment)) {
            log.error(permission + " has been denied for user " + auth.getName());
            Notification.show("Authorization error",
                    "This action denied for " + auth.getName(),
                    Notification.Type.ERROR_MESSAGE);
            throw new AccessDeniedException("Access is denied");
        }
    }
}
