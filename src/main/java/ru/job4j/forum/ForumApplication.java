package ru.job4j.forum;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import ru.job4j.forum.repository.UserRepository;

import javax.sql.DataSource;

@SpringBootApplication
@EnableJpaRepositories(basePackageClasses = UserRepository.class)
public class ForumApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(ForumApplication.class);
    }

    @Bean
    public SpringLiquibase liquibase(DataSource dataSource) {
        var liquibase = new SpringLiquibase();
        liquibase.setChangeLog("classpath:liquibase-changeLog.xml");
        liquibase.setDataSource(dataSource);
        return liquibase;
    }

    public static void main(String[] args) {
        SpringApplication.run(ForumApplication.class, args);
    }
}
