package ru.job4j.accident;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Просто класс для генерации пароля.
 */
public class GenerateInitPass {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String password = encoder.encode("secret");
        System.out.println(password);
        System.out.println(encoder.matches("secret", "$2a$10$0QsWntJoBmQYhVqNITKJouxylz/b/N2NQwfhECZ0IXIlwFT4tW8.a"));
    }
}
