package com.shop.online.config;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.FluentConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class FlywayConfig  {

    private static final Logger logger = LoggerFactory.getLogger(FlywayConfig.class);

    @Value("${app.flyway.repair}")
    private boolean isFlywayRepair;

    @Value("${spring.flyway.table}")
    private String flywayTable;

    @Bean
    public FlywayMigrationStrategy flywayMigrationStrategy(DataSource dataSource) {
        return flyway -> {
            logger.info("-----Custom Flyway Migration Strategy (skip version check)-----");

            FluentConfiguration configuration = Flyway.configure()
                    .dataSource(dataSource)
                    .baselineOnMigrate(true)
                    .table(flywayTable)
                    .ignoreMigrationPatterns("*:pending");

            Flyway customFlyway = configuration.load();

            if (isFlywayRepair) {
                logger.info("-----Running Flyway Repair-----");
                customFlyway.repair();
            } else {
                logger.info("-----Running Flyway Migrate-----");
                customFlyway.migrate();
            }
        };
    }
}
