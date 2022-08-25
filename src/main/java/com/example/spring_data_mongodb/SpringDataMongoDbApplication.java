package com.example.spring_data_mongodb;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static com.example.spring_data_mongodb.Gender.MALE;

@SpringBootApplication
public class SpringDataMongoDbApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringDataMongoDbApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(
            StudentRepository repository) {

        return args -> {

            Address address = new Address(
                    "Bulgaria",
                    "Strelcha",
                    "4530"
            );

            String email = "nikolai.lulchev@gmail.com";

            Student student = new Student(
                    "Nikolay",
                    "Lulchev",
                    email,
                    MALE,
                    address,
                    List.of("Computer Science", "Maths"),
                    BigDecimal.TEN,
                    LocalDateTime.now()
            );

            repository.findStudentByEmail(email)
                    .ifPresentOrElse(s -> System.out.println(s + " already exist"), () -> {
                        System.out.println("Inserting student " + student);
                        repository.insert(student);
                    });
        };
    }
}
