package net.redone.keynoteservice;

import net.redone.keynoteservice.entities.Keynote;
import net.redone.keynoteservice.repositories.KeynoteRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.Random;

@EnableDiscoveryClient
@SpringBootApplication
public class KeynoteServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(KeynoteServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner seedKeynotes(KeynoteRepository keynoteRepository) {
        return args -> {
            if (keynoteRepository.count() > 0) {
                return;
            }
            List<String> noms = List.of("Benali", "Haddad", "El Amrani", "Mansouri", "Ziani");
            List<String> prenoms = List.of("Amina", "Yassine", "Nadia", "Khalid", "Sara");
            List<String> fonctions = List.of("Professor", "Engineer", "Researcher", "Architect", "Consultant");
            Random random = new Random();
            for (int i = 0; i < 5; i++) {
                int idx = random.nextInt(noms.size());
                String nom = noms.get(idx);
                String prenom = prenoms.get(idx);
                String fonction = fonctions.get(idx);
                String email = (prenom + "." + nom).toLowerCase().replace(" ", "") + "@example.com";
                keynoteRepository.save(Keynote.builder()
                        .nom(nom)
                        .prenom(prenom)
                        .email(email)
                        .fonction(fonction)
                        .build());
            }
        };
    }
}
