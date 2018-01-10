package com.springbazaar;

import com.springbazaar.domain.Person;
import com.springbazaar.domain.Product;
import com.springbazaar.domain.User;
import com.springbazaar.domain.util.FullName;
import com.springbazaar.domain.util.type.RoleType;
import com.springbazaar.service.PersonService;
import com.springbazaar.service.ProductService;
import com.springbazaar.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

import static java.lang.System.exit;

//@Component
public class ApplicationCommandLineRunner implements CommandLineRunner {
    private static final String DELIMITER = "------------------------------------>";
    private final DataSource dataSource;
    private final UserService userService;
    private final PersonService personService;
    private final ProductService productService;
    private final ApplicationContext applicationContext;

    @Autowired
    public ApplicationCommandLineRunner(ApplicationContext applicationContext,
                                        UserService userService,
                                        DataSource dataSource, PersonService personService, ProductService productService) {
        this.applicationContext = applicationContext;
        this.personService = personService;
        this.userService = userService;
        this.dataSource = dataSource;
        this.productService = productService;
    }

    @Override
    public void run(String... args) {
        inspectLoadedBeans();
        System.out.println(String.format("%1$tF %1$tT %2$-4s DataSource = " + dataSource,
                Calendar.getInstance().getTime(), DELIMITER));

//        createProducts(personService.getById(new BigInteger("2")), 20000);

        createTestData();

        System.out.println(String.format("%1$tF %1$tT %2$-4s Done!",
                Calendar.getInstance().getTime(), DELIMITER));
        exit(0);
    }

    private void createTestData() {
        try {
            Person newPerson = getPerson("First Admin Name", "Middle Admin Name", "Last Admin Name");
            createUser("admin@email.com",
                    "admin@email.com",
                    newPerson,
                    RoleType.ROLE_ADMIN.toString());

            newPerson = getPerson("First Seller Name", "Middle Seller Name", "Last Seller Name");
            createUser("seller@email.com",
                    "seller@email.com",
                    newPerson,
                    RoleType.ROLE_SELLER.toString());

            createProducts(newPerson, 50);

            newPerson = getPerson("First Buyer Name", "Middle Buyer Name", "Last Buyer Name");
            createUser("buyer@email.com",
                    "buyer@email.com",
                    newPerson,
                    RoleType.ROLE_BUYER.toString());


            List<Product> products = productService.listAll();
            List<User> users = userService.listAll();
            printDataToConsole(users, products);

        } finally {
            System.out.println("Creation test data completed. Comment annotation @Component of ApplicationCommandLineRunner.class");
            exit(0);
        }
    }

    private void createUser(String username, String password, Person person, String roles) {
        User user = new User(username, password);
        userService.saveOrUpdate(user, person, Collections.singletonList(roles));
    }

    private Person getPerson(String first, String middle, String last) {
        FullName personFullName = new FullName(first, middle, last);
        return new Person(personFullName);
    }

    private void createProducts(Person person, int count) {
        for (int i = 1; i < count; i++) {
            Random obj = new Random(i);
            long randomPrice = obj.nextInt(1000);
            Product newProduct = new Product("Product #" + i,
                    "product belongs to " + person.getShortName(),
                    new BigDecimal(randomPrice),
                    null,
                    Calendar.getInstance().getTime(),
                    person.getId());
            newProduct.setPerson(person);
            productService.saveOrUpdate(newProduct);
        }
    }

    private void printDataToConsole(List<User> users, List<Product> products) {
        System.out.println("Creation test data started");
        for (User user : users) {
            System.out.println("User created: {}" + user.toString());
        }
        System.out.println("Product list:");
        for (Product product : products) {
            System.out.println(product.toString());
        }
    }

    private void inspectLoadedBeans() {
        System.out.println("Let's inspect the beans provided by Spring Boot:");

        String[] beanNames = applicationContext.getBeanDefinitionNames();
        Arrays.sort(beanNames);
        for (String beanName : beanNames) {
            System.out.println(String.format("%1$tF %1$tT %2$-4s Bean FullName = " + beanName,
                    Calendar.getInstance().getTime(), DELIMITER));
        }
    }
}
