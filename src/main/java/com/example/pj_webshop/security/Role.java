package com.example.pj_webshop.security;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {

    CLIENT, ADMIN;

    @Contract(pure = true)
    @Override
    public @NotNull String getAuthority() {
        return name();
    }
}
