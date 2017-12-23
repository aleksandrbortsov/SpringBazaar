package com.springbazaar.web.security;

import com.vaadin.server.ErrorEvent;
import com.vaadin.server.ErrorHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;

public class SecurityErrorHandler implements ErrorHandler {
    private static Logger LOGGER = LoggerFactory.getLogger(SecurityErrorHandler.class);

    @Override
    public void error(ErrorEvent errorEvent) {
        LOGGER.error("Error handler caught exception {}", errorEvent.getThrowable().getClass().getName());

        if (errorEvent.getThrowable() instanceof AccessDeniedException
                || errorEvent.getThrowable().getCause() instanceof AccessDeniedException) {
            //TODO SecurityErrorHandler
        } else {
            // TODO handle other exceptions a bit more graciously than this
            errorEvent.getThrowable().printStackTrace();
        }
    }
}
