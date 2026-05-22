package com.ecommerce.user.config;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MongoDbChecker {

    private final MongoTemplate mongoTemplate;

    @PostConstruct
    public void checkDatabase() {
        log.info("MongoDB database used: {}", mongoTemplate.getDb().getName());
    }
}