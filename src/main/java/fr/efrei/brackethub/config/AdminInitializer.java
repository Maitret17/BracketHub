package fr.efrei.brackethub.config;

import fr.efrei.brackethub.data.Player;
import fr.efrei.brackethub.data.Role;
import fr.efrei.brackethub.repository.PlayerRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AdminInitializer {

    @Bean
    CommandLineRunner createAdmin(
            PlayerRepository playerRepository,
            PasswordEncoder passwordEncoder) {

        return args -> {

            if (playerRepository.findByUsername("admin").isEmpty()) {

                Player admin = new Player(
                        "Admin",
                        "Administrator",
                        0,
                        "admin",
                        passwordEncoder.encode("123"),
                        Role.ADMIN
                );
                playerRepository.save(admin);
            }
        };
    }
}
