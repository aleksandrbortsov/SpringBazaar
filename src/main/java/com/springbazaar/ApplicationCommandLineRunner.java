package com.springbazaar;

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

//@Component
public class ApplicationCommandLineRunner implements CommandLineRunner {
    public static final Logger LOGGER = LoggerFactory.getLogger(ApplicationCommandLineRunner.class);
    private final DataSource dataSource;
    private final RoleRepository roleRepository;
    private final UserService userService;
    private final UserRepository userRepository;
    private final PersonRepository personRepository;
    private final ApplicationContext applicationContext;

    @Autowired
    public ApplicationCommandLineRunner(ApplicationContext applicationContext,
                                        PersonRepository personRepository,
                                        UserRepository userRepository,
                                        UserService userService,
                                        RoleRepository roleRepository,
                                        DataSource dataSource) {
        this.applicationContext = applicationContext;
        this.personRepository = personRepository;
        this.userRepository = userRepository;
        this.userService = userService;
        this.roleRepository = roleRepository;
        this.dataSource = dataSource;
    }

    @Override
    public void run(String... args) {
//        inspectLoadedBeans();

        System.out.println("DataSource = " + dataSource);

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
