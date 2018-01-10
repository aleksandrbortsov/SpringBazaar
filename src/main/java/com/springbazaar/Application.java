package com.springbazaar;

import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication // same as @Configuration @EnableAutoConfiguration @ComponentScan
@Slf4j
public class Application {
    public static final String DB_POSTGRES_UPDATE_LOCATION = "/db/postgres/update";
    public static final String DB_MYSQL_UPDATE_LOCATION = "/db/mysql/update";

//    @Bean
//    MyBean myBean() {
//        return new MyBean();
//    }

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Application.class);
        // Customizing SpringApplication same as application.properties
//        app.setBannerMode(Banner.Mode.OFF);
        ApplicationContext context = app.run(args);

        setFlywayRollingState(context);
    }

    private static void setFlywayRollingState(ApplicationContext context) {
        List<String> activeProfiles = Arrays.asList(context.getEnvironment().getActiveProfiles());
        Flyway flyway = context.getBean(Flyway.class);
        String flywayState = getFlywayState(context, flyway);

        if (flywayState.equals("update")) {
            if (activeProfiles.contains("mysql")) {
                flyway.setLocations(DB_MYSQL_UPDATE_LOCATION);
            } else {
                flyway.setLocations(DB_POSTGRES_UPDATE_LOCATION);
            }
            flyway.migrate();
        }
    }

    private static String getFlywayState(ApplicationContext context, Flyway flyway) {
        String table = flyway.getTable();
        DataSource dataSource = context.getBean(DataSource.class);
        if (isFlywayTableExist(table, dataSource)) {
            return "update";
        } else {
            return "init";
        }
    }

    private static boolean isFlywayTableExist(String table, DataSource dataSource) {
        try {
            Connection connection = dataSource.getConnection();
            DatabaseMetaData metaData = connection.getMetaData();
            String[] types = {"TABLE"};
            ResultSet rs = metaData.getTables(null, null, "%", types);
            while (rs.next()) {
                if (table.equals(rs.getString("TABLE_NAME"))) {
                    return true;
                }
            }
        } catch (SQLException e) {
            log.error("Error while connect to Database: " + e.getMessage());
        }
        return false;
    }

//    check implementation FlywayMigrationStrategy from @SpringBootConfiguration
//    and example @Value
//    @Profile("postgres")
//    private static class MyBean implements FlywayMigrationStrategy {
//
//        @Value("${app.title}")
//        private String appTitle;
//
//        public void startApplication() {
//            System.out.printf("-- running application: %s --%n", appTitle);
//
//        }
//
//        public static final String INIT_LOCATION = "/db/postgres/init";
//        public static final String UPDATE_LOCATION = "/db/postgres/update";
//
//        @Override
//        public void migrate(Flyway flyway) {
//            flyway.getBaselineVersion();
//            flyway.setLocations(INIT_LOCATION);
//
//            boolean baselineOnMigrate = flyway.isBaselineOnMigrate();
//
//            if (baselineOnMigrate) {
//                flyway.setLocations(UPDATE_LOCATION);
//                flyway.migrate();
//            } else {
//                flyway.migrate();
//            }
//        }
//    }
}
