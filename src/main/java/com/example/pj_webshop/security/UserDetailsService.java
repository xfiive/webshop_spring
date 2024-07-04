package com.example.pj_webshop.security;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
    private static final Map<String, UserDetails> users = new HashMap<>();

    static {
        users.put("user", new UserDetails("user", "{noop}password", List.of(Role.CLIENT)));
        users.put("admin", new UserDetails("admin", "{noop}password", List.of(Role.ADMIN)));
    }

    @Override
    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (!users.containsKey(username)) {
            throw new UsernameNotFoundException("User not found");
        }
        return users.get(username);
    }
}
