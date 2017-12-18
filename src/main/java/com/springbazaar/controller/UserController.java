package com.springbazaar.controller;

import com.springbazaar.domain.FullName;
import com.springbazaar.domain.Person;
import com.springbazaar.domain.Role;
import com.springbazaar.domain.User;
import com.springbazaar.domain.type.RoleType;
import com.springbazaar.repository.PersonRepository;
import com.springbazaar.repository.RoleRepository;
import com.springbazaar.service.UserService;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringComponent
@UIScope
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    private UserService userService;
    private RoleRepository roleRepository;
    private PersonRepository personRepository;

    @Autowired
    public UserController(UserService userService,
                          RoleRepository roleRepository,
                          PersonRepository personRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
        this.personRepository = personRepository;
    }

    public void addNewUser(User userForm, Person personForm, List<String> roleForm) {
        LOGGER.debug("addNewUser start");
        Set<Role> roles = new HashSet<>();
        for (String roleItem : roleForm) {
            Role userRole = roleRepository.findByRoleType(RoleType.findByStringType(StringUtils.upperCase(roleItem)));
            if (userRole == null) {
                userRole = new Role(RoleType.findByStringType(StringUtils.upperCase(roleItem)));
            }
            roles.add(userRole);
        }
        FullName personFullName = new FullName(personForm.getFullName().getFirstName(),
                personForm.getFullName().getMiddleName(),
                personForm.getFullName().getLastName());
        Person newPerson = new Person(personFullName);

        User newUser = new User(userForm.getUsername(), userForm.getPassword());

        newPerson.setUser(newUser);
        newUser.setPerson(newPerson);
        personRepository.save(newPerson);
        newUser.setRoles(roles);
        userService.saveOrUpdate(newUser);
    }

    public Person check(User user) {
        return null;
    }

    public User saveOrUpdateUser(User user) {
        return userService.saveOrUpdate(user);
    }
}
