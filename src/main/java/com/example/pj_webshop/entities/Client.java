package com.example.pj_webshop.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Entity
@Table(name = "clients")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int clientId;

    private String firstName;
    private String lastName;
    private String phone;
    private String email;

    @Contract(pure = true)
    public Client(@NotNull Client other) {
        this.clientId = other.clientId;
        this.firstName = other.firstName;
        this.lastName = other.lastName;
        this.phone = other.phone;
        this.email = other.email;
    }

    public boolean isEmailValid() {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(this.email);
        return matcher.matches();
    }

    public Client copy() {
        return new Client(this);
    }
}
