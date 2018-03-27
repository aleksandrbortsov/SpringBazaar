package com.springbazaar.service.security;

import com.springbazaar.domain.util.LoginAttempts;
import com.springbazaar.repository.UserRepositoryCustom;
import com.vaadin.ui.Notification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component("authenticationProvider")
@Slf4j
public class LimitLoginAuthenticationProvider extends DaoAuthenticationProvider {
    private final UserRepositoryCustom userRepositoryCustom;

    @Autowired
    public LimitLoginAuthenticationProvider(UserRepositoryCustom userRepositoryCustom) {
        this.userRepositoryCustom = userRepositoryCustom;
    }

    @Autowired
    @Qualifier("userDetailsServiceImpl")
    @Override
    public void setUserDetailsService(UserDetailsService userDetailsService) {
        super.setUserDetailsService(userDetailsService);
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        try {
            Authentication auth = super.authenticate(authentication);
            //login success, reset the user_attempts
            userRepositoryCustom.resetFailAttempts(authentication.getName());
            return auth;

        } catch (BadCredentialsException e) {
            //invalid login, update to user_attempts
            userRepositoryCustom.updateFailAttempts(authentication.getName());
            throw e;

        } catch (LockedException e) {
            //this user is locked!
            String error;
            LoginAttempts loginAttempts = userRepositoryCustom.getLoginAttempts(authentication.getName());

            if (loginAttempts != null) {
                Date lastAttempts = loginAttempts.getLastModified();
                error = "User account " + authentication.getName() + " is locked!" +
                        " Last Attempts : " + lastAttempts;
            } else {
                error = e.getMessage();
            }
            log.error(error);
            Notification.show("Authentication error",
                    error,
                    Notification.Type.ERROR_MESSAGE);
            throw new LockedException(error);
        }
    }
}
