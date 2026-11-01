package com.smartcamera.resource;

import org.springframework.web.bind.annotation.*;
import com.smartcamera.util.Auth;
import com.smartcamera.db.UserDAO;
import com.smartcamera.model.User;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping("/api")
public class AuthResource {

    private final UserDAO userDAO = new UserDAO();
    private static String mockPin = null;

    @PostMapping("/register")
    public Map<String, String> register(@RequestBody Map<String, String> body)
            throws SQLException {
        String email = body.get("email");
        String password = body.get("password");

        if (email == null || password == null || password.length() < 8) {
            throw new RuntimeException("Invalid input");
        }

        String hashed = Auth.hashPassword(password);
        userDAO.insertUser(email, hashed, "resident");

        mockPin = String.format("%04d", new Random().nextInt(10000));
        System.out.println("\nMOCK SMS SENT TO PHONE");
        System.out.println("\nYour Login PIN is: " + mockPin);

        System.out.println();
        return Map.of("message", "User Registered! Check server console for the PIN");
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> body)
            throws SQLException {
        String email = body.get("email");
        String password = body.get("password");
        String pin = body.get("pin");

        User user = userDAO.findUserByEmail(email);

        if (user == null) {
            throw new RuntimeException("Invalid credentials");
        }

        if (user.getLockedUntil() != null && user.getLockedUntil().isAfter(LocalDateTime.now())) {
            throw new RuntimeException("Account Locked, Try agian later.");
        }

        if (!Auth.verifyPassword(password, user.getPassword())) {
            int attempts = user.getAttempts() + 1;
            LocalDateTime lockUntil = attempts >= 5
                    ? LocalDateTime.now().plusMinutes(15)
                    : null;

            userDAO.updateFailedAttempts(user.getUserId(), attempts, lockUntil);
            throw new RuntimeException("Invalid credentials");
        }

        if (pin == null || !pin.equals(mockPin)) {
            throw new RuntimeException("Invalid PIN");
        }

        //reset
        userDAO.updateFailedAttempts(user.getUserId(), 0, null);
        String token = Auth.generateToken(user.getUserId(), user.getRole().toString());

        return Map.of("token", token);
    }

}
