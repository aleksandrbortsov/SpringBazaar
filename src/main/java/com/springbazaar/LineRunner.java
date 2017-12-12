package com.springbazaar;

import com.springbazaar.domain.Person;
import com.springbazaar.domain.Role;
import com.springbazaar.domain.User;
import com.springbazaar.repository.PersonRepository;
import com.springbazaar.repository.RoleRepository;
import com.springbazaar.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.math.BigInteger;
import java.util.Arrays;

import static java.lang.System.exit;

@Component
public class LineRunner implements CommandLineRunner {
    public static final Logger LOGGER = LoggerFactory.getLogger(LineRunner.class);

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    DataSource dataSource;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserService userService;

    @Autowired
    PersonRepository personRepository;

    @Override
    public void run(String... args) {
//        inspectLoadedBeans();
        Role role = new Role("test");
        roleRepository.save(role);
        System.out.println("added role = " + role);

        roleRepository.delete(role.getId());
        System.out.println("deleted role = " + role);

        Role sellerRole = roleRepository.findOne(new BigInteger("1"));
        User user = new User();
        user.setLogin("Ivan");
        user.setPassword("123");
//        user.setRole(sellerRole);
        Person person = personRepository.findOne(new BigInteger("1"));
        user.setPerson(person);
        userService.saveOrUpdate(user);
        System.out.println("User has been added, id = " + user.getId());

        userService.delete(user);
        System.out.println("User has been deleted, id = " + user.getId());
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
            System.out.println("Bean Name = " + beanName);
        }
    }
}
