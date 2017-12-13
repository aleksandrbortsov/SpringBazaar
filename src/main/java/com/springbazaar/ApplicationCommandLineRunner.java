package com.springbazaar;

import com.springbazaar.domain.FullName;
import com.springbazaar.domain.Person;
import com.springbazaar.domain.Role;
import com.springbazaar.domain.User;
import com.springbazaar.repository.PersonRepository;
import com.springbazaar.repository.RoleRepository;
import com.springbazaar.repository.UserRepository;
import com.springbazaar.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.Arrays;

import static java.lang.System.exit;

@Component
public class ApplicationCommandLineRunner implements CommandLineRunner {
    public static final Logger LOGGER = LoggerFactory.getLogger(ApplicationCommandLineRunner.class);
    @Autowired
    DataSource dataSource;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PersonRepository personRepository;
    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public void run(String... args) {
//        inspectLoadedBeans();

        System.out.println("DataSource = " + dataSource);

        Role sellerRole = roleRepository.findByName("seller");
        if (sellerRole == null) {
            sellerRole = new Role("seller");
        }

        FullName personFullName = new FullName("Petr", "Petrovich", "Petrov");
        Person newPerson = new Person();
        newPerson.setFullName(personFullName);

        User newUser = new User();
        newUser.setLogin("Ivan");
        newUser.setPassword("123");
        newPerson.setUser(newUser);
        newUser.setPerson(newPerson);
        personRepository.save(newPerson);
        newUser.setRole(sellerRole);
        userService.saveOrUpdate(newUser);


//        roleRepository.delete(sellerRole.getId());
//        personRepository.delete(newPerson.getId());
        userRepository.delete(newUser.getId());
        System.out.println("deleted person = " + newPerson.getFullName());

//        System.out.println("User has been added, id = " + newUser.getId());


//        personRepository.save(newPerson);
//        System.out.println("newPerson = " + newPerson);


//        userService.delete(newUser);
//        System.out.println("User has been deleted, id = " + newUser.getId());
//        System.out.println("\n1.findAll()...");
//        for (Person role : personRepository.findAll()) {
//            System.out.println(role);
//        }

        System.out.println("Done!");
        exit(0);
    }

    private void inspectLoadedBeans() {
        LOGGER.debug("Let's inspect the beans provided by Spring Boot:");
        System.out.println("Let's inspect the beans provided by Spring Boot:");

        String[] beanNames = applicationContext.getBeanDefinitionNames();
        Arrays.sort(beanNames);
        for (String beanName : beanNames) {
            LOGGER.debug(beanName);
            System.out.println("Bean FullName = " + beanName);
        }
    }
}
