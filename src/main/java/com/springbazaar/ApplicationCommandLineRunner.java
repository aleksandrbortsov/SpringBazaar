package com.springbazaar;

import com.springbazaar.domain.Person;
import com.springbazaar.domain.Product;
import com.springbazaar.domain.User;
import com.springbazaar.domain.util.FullName;
import com.springbazaar.domain.util.type.RoleType;
import com.springbazaar.service.PersonService;
import com.springbazaar.service.ProductService;
import com.springbazaar.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

import static java.lang.System.exit;

@Component
public class ApplicationCommandLineRunner implements CommandLineRunner {
    public static final Logger LOGGER = LoggerFactory.getLogger(ApplicationCommandLineRunner.class);
    private static final String DELIMITER = "------------------------------------>";
    private final DataSource dataSource;
    private final UserService userService;
    private final PersonService personService;
    private final ProductService productService;
    private final ApplicationContext applicationContext;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public ApplicationCommandLineRunner(BCryptPasswordEncoder passwordEncoder,
                                        ApplicationContext applicationContext,
                                        UserService userService,
                                        DataSource dataSource, PersonService personService, ProductService productService) {
        this.passwordEncoder = passwordEncoder;
        this.applicationContext = applicationContext;
        this.personService = personService;
        this.userService = userService;
        this.dataSource = dataSource;
        this.productService = productService;
    }

    @Override
    public void run(String... args) {
//        inspectLoadedBeans();
        System.out.println(String.format("%1$tF %1$tT %2$-4s DataSource = " + dataSource,
                Calendar.getInstance().getTime(), DELIMITER));

        createTestData();

        System.out.println(String.format("%1$tF %1$tT %2$-4s Done!",
                Calendar.getInstance().getTime(), DELIMITER));
        exit(0);
    }

    private void createTestData() {
        try {
            FullName personFullName = new FullName("First Admin Name",
                    "Middle Admin Name",
                    "Last Admin Name");
            Person adminPerson = new Person(personFullName);
            User adminUser = new User("admin@email.com", passwordEncoder.encode("admin@email.com"));
            userService.saveOrUpdate(adminUser, adminPerson, Collections.singletonList(RoleType.ROLE_ADMIN.toString()));


            personFullName = new FullName("First Seller Name",
                    "Middle Seller Name",
                    "Last Seller Name");
            Person sellerPerson = new Person(personFullName);
            User sellerUser = new User("seller@email.com", passwordEncoder.encode("seller@email.com"));
            userService.saveOrUpdate(sellerUser, sellerPerson, Collections.singletonList(RoleType.ROLE_SELLER.toString()));

            for (int i = 0; i < 5; i++) {
                Random obj = new Random(i);
                long randomPrice = obj.nextInt(1000);
                Product newProduct = new Product("Product #" + i,
                        "description product",
                        new BigDecimal(randomPrice),
                        null,
                        Calendar.getInstance().getTime(),
                        new BigInteger("1"));
                Person person = personService.getById(sellerPerson.getId());
                newProduct.setPerson(person);
                productService.saveOrUpdate(newProduct);
            }

            personFullName = new FullName("First Buyer Name",
                    "Middle Buyer Name",
                    "Last Buyer Name");
            Person buyerPerson = new Person(personFullName);
            User buyerUser = new User("buyer@email.com", passwordEncoder.encode("buyer@email.com"));
            userService.saveOrUpdate(buyerUser, buyerPerson, Collections.singletonList(RoleType.ROLE_BUYER.toString()));

            List<Product> products = productService.listAll();

            printDataToConsole(adminUser, sellerUser, buyerUser, products);

        } finally {
            System.out.println("Creation test data completed. Comment annotation @Component of ApplicationCommandLineRunner.class");
            exit(0);
        }
    }

    private void printDataToConsole(User adminUser, User sellerUser, User buyerUser, List<Product> products) {
        System.out.println("Creation test data started");
        System.out.println("Admin user created: " + adminUser.toString());
        System.out.println("Seller user created: " + sellerUser.toString());
        System.out.println("Product list:");
        for (Product product: products) {
            System.out.println(product.toString());
        }
        System.out.println("Buyer user created: " + buyerUser.toString());
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
