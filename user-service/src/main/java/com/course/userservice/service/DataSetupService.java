package com.course.userservice.service;

import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.boot.CommandLineRunner;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;


@Slf4j
//@Service
@RequiredArgsConstructor
public class DataSetupService implements CommandLineRunner {

    @Value("classpath:h2/init.sql")
    private Resource initSql;

    private final Environment environment;
    private final R2dbcEntityTemplate entityTemplate;

    @Override
    public void run(String... args) throws Exception {
        final var profile = environment.getActiveProfiles()[0];
        if (profile.equals("local")) {
            String query = StreamUtils
                    .copyToString(initSql.getInputStream(), StandardCharsets.UTF_8);

            log.info(query);

            this.entityTemplate
                    .getDatabaseClient()
                    .sql(query)
                    .then()
                    .subscribe();
        } else {
            log.info("No script execute, profile: {}", profile);
        }

    }

}
