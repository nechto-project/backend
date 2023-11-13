package ru.bachelors.project.nechto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class NechtoApplication {

    public static void main(String[] args) {
        SpringApplication.run(NechtoApplication.class, args);
    }

}
