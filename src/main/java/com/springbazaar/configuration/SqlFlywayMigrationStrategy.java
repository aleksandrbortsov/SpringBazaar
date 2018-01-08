package com.springbazaar.configuration;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.MigrationVersion;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

//@Configuration
//@Profile("mysql") use application-mysql.properties
public class SqlFlywayMigrationStrategy implements FlywayMigrationStrategy {
    public static final String INIT_LOCATION = "/db/mysql/init";
    public static final String UPDATE_LOCATION = "/db/mysql/update";

    @Override
    public void migrate(Flyway flyway) {
        flyway.setLocations(INIT_LOCATION);
        MigrationVersion baselineVersion = flyway.getBaselineVersion();

        if ("init".equals(baselineVersion.getVersion())) {
            flyway.setLocations(UPDATE_LOCATION);
            flyway.migrate();
        } else {
            flyway.migrate();
        }
    }
}


