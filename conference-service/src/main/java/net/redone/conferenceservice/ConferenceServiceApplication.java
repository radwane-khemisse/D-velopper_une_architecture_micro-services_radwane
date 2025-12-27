package net.redone.conferenceservice;

import net.redone.conferenceservice.entities.Conference;
import net.redone.conferenceservice.entities.ConferenceType;
import net.redone.conferenceservice.entities.Review;
import net.redone.conferenceservice.repositories.ConferenceRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class ConferenceServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConferenceServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner seedConferences(ConferenceRepository conferenceRepository) {
        return args -> {
            if (conferenceRepository.count() > 0) {
                return;
            }
            List<String> titres = List.of(
                    "Microservices Patterns",
                    "Cloud Native Architecture",
                    "API Design in Practice",
                    "Spring Cloud Essentials",
                    "Resilience and Fault Tolerance"
            );
            List<ConferenceType> types = List.of(
                    ConferenceType.ACADEMIC,
                    ConferenceType.COMMERCIAL
            );
            Random random = new Random();
            for (int i = 0; i < 5; i++) {
                Conference conference = Conference.builder()
                        .titre(titres.get(i))
                        .type(types.get(random.nextInt(types.size())))
                        .date(LocalDate.now().plusDays(i))
                        .duree(60 + i * 15)
                        .nombreInscrits(50 + i * 20)
                        .score(3.5 + (i * 0.3))
                        .keynoteId((long) (i + 1))
                        .build();
                for (int j = 0; j < 5; j++) {
                    Review review = Review.builder()
                            .date(LocalDate.now().plusDays(i))
                            .texte("Review " + (j + 1) + " for " + titres.get(i))
                            .stars(1 + random.nextInt(5))
                            .conference(conference)
                            .build();
                    conference.getReviews().add(review);
                }
                conferenceRepository.save(conference);
            }
        };
    }
}
