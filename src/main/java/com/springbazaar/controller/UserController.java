package com.springbazaar.controller;

import com.springbazaar.domain.FullName;
import com.springbazaar.domain.Person;
import com.springbazaar.domain.Role;
import com.springbazaar.domain.User;
import com.springbazaar.repository.PersonRepository;
import com.springbazaar.repository.RoleRepository;
import com.springbazaar.service.UserService;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;

@SpringComponent
@UIScope
public class UserController {

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
        for (String roleItem: roleForm) {
            Role userRole = roleRepository.findByName(roleItem);
            if (userRole == null) {
                userRole = new Role(roleItem);
            }

            FullName personFullName = new FullName(personForm.getFullName().getFirstName(),
                    personForm.getFullName().getMiddleName(),
                    personForm.getFullName().getLastName());
            Person newPerson = new Person(personFullName);

            User newUser = new User(userForm.getLogin(), userForm.getPassword());

            newPerson.setUser(newUser);
            newUser.setPerson(newPerson);
            personRepository.save(newPerson);
            newUser.setRole(userRole);
            userService.saveOrUpdate(newUser);
        }
    }

    public Person check(User user) {
        return null;
    }

    public User saveOrUpdateUser(User user) {
        return userService.saveOrUpdate(user);
    }
}
