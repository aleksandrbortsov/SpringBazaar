package com.springbazaar.configuration;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.MigrationVersion;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("mysql")
public class MySqlFlywayMigrationStrategy implements FlywayMigrationStrategy {
    @Override
    public void migrate(Flyway flyway) {
//        flyway.clean();

        flyway.migrate();

//        MigrationVersion baselineVersion = flyway.getBaselineVersion();
//        if ("1".equals(baselineVersion.getVersion())) {
//            flyway.migrate();
//        } else {
//            flyway.baseline();
//        }
    }
}


