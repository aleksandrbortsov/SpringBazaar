package com.springbazaar.service.security;

import com.springbazaar.web.ui.RegistrationUI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SecurityServiceImpl implements SecurityService {
    private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationUI.class);
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsService userDetailsService;
//    @Autowired
//    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public String findLoggedInUsername() {
        Object user = SecurityContextHolder.getContext().getAuthentication().getDetails();
        if (user instanceof UserDetails) {
            return ((UserDetails) user).getUsername();
        }
        return null;
    }

    @Override
    public boolean login(String username, String password) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());

        authenticationManager.authenticate(token);

        if (token.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(token);
            LOGGER.debug("Auto login {} successfully!", username);
            return true;
        } else {
            LOGGER.debug("Authenticate username {} failed!", username);
            return false;
        }
    }
}
