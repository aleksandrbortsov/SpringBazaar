package com.springbazaar.controller;

import com.springbazaar.domain.User;
import com.springbazaar.service.UserService;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

@SpringComponent
@UIScope
public class UserController {


    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    public String saveOrUpdateUser(User user) {

        User savedUser = userService.saveOrUpdate(user);

        return "redirect:/register/show/";
    }
}
