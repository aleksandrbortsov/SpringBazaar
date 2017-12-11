package com.springbazaar.controller;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @RequestMapping("/")
    public String redirectToList(){
        return "redirect:/register/user_form";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String saveOrUpdateProduct(BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            return "register/user_form";
        }

//        Product savedProduct = productService.saveOrUpdateProductForm(productForm);

        return "redirect:/register/show/";
    }
}
