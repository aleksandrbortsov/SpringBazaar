package com.springbazaar.service;

import com.springbazaar.domain.Person;
import com.springbazaar.domain.Role;
import com.springbazaar.domain.User;
import com.springbazaar.domain.util.type.RoleType;
import com.springbazaar.exception.UserAuthenticationException;
import com.springbazaar.repository.PersonRepository;
import com.springbazaar.repository.RoleRepository;
import com.springbazaar.repository.UserRepository;
import com.vaadin.ui.Notification;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PersonRepository personRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           PersonRepository personRepository,
                           BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.personRepository = personRepository;

        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public User saveOrUpdate(User userForm, Person personForm, List<String> roleForm) {
        LOGGER.debug("Save/Update User Service started");
        if (isUsernameExist(userForm.getUsername())) {
            String errorMessage = "User with username " + userForm.getUsername() + " already exist.";
            LOGGER.error(errorMessage);
            Notification.show("Authentication error", errorMessage, Notification.Type.ERROR_MESSAGE);
            throw new UserAuthenticationException(errorMessage);
        }
        Set<Role> roles = new HashSet<>();
        for (String roleItem : roleForm) {
            Role userRole = roleRepository.findByRoleType(RoleType.findByStringType(StringUtils.upperCase(roleItem)));
            if (userRole == null) {
                userRole = new Role(RoleType.findByStringType(StringUtils.upperCase(roleItem)));
            }
            roles.add(userRole);
        }
        //TODO encode pass
//        userForm.setPassword(bCryptPasswordEncoder.encode(userForm.getPassword()));

        personForm.setUser(userForm);
        personForm.setEmail(userForm.getUsername());
        userForm.setPerson(personForm);
        personRepository.save(personForm);
        userForm.setAuthorities(roles);
        roleRepository.save(roles);
        userRepository.save(userForm);
        LOGGER.debug(userForm.toString() + " has been registered");
        return userForm;
    }

    @Override
    public boolean isUsernameExist(String username) {
        return userRepository.findByUsername(username) != null;
    }

    @Override
    public User saveOrUpdate(User user) {
        userRepository.save(user);
        return user;
    }

    @Override
    public User getUserById(BigInteger id) {
        return userRepository.findOne(id);
    }

    @Override
    //TODO sort out
    @Secured("ROLE_ADMIN")
    public void delete(User user) {
        userRepository.delete(user);
    }
}
