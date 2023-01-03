package com.mapofzones.calculations.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.mapofzones.calculations.repository")
public class MongoConfig  {

}