package fr.efrei.brackethub.web;

import fr.efrei.brackethub.data.Player;
import fr.efrei.brackethub.data.Role;
import fr.efrei.brackethub.repository.PlayerRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final PlayerRepository playerRepository;
    private final PasswordEncoder passwordEncoder;
    public AuthController(PlayerRepository playerRepository, PasswordEncoder passwordEncoder) {
        this.playerRepository = playerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Player player) {
        if (playerRepository.findByUsername(player.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body(Map.of("message", "Username already exists"));
        }
        player.setPassword(passwordEncoder.encode(player.getPassword()));
        if (player.getRole() == null) {
            player.setRole(Role.PLAYER);
        }
        Player savedPlayer = playerRepository.save(player);
        return ResponseEntity.ok(Map.of("message", "Registration successful", "id", savedPlayer.getId(), "username", savedPlayer.getUsername()));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        Player player = playerRepository.findByUsername(request.username()).orElse(null);
        if (player == null || !passwordEncoder.matches(request.password(), player.getPassword())) {
            return ResponseEntity.status(401).body(Map.of("message", "Invalid username or password"));
        }
        return ResponseEntity.ok(
                Map.of(
                        "message", "Login successful",
                        "id", player.getId(),
                        "username", player.getUsername(),
                        "role", player.getRole().name()
                )
        );
    }

    public record LoginRequest(String username, String password) {
    }
}