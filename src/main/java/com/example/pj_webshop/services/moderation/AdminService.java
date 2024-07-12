package com.example.pj_webshop.services.moderation;

import com.example.pj_webshop.entities.authentication.Administration;
import com.example.pj_webshop.repositories.moderation.AdminRepository;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminService {

    private AdminRepository adminRepository;

    public String login(String username, String passwordHash) {
        Optional<Administration> adminOpt = adminRepository.findByUsername(username);

        if (adminOpt.isEmpty()) {
            System.out.println("Admin not found");
            return null;
        }

        System.out.println("Password hash from frontend: " + passwordHash);
        System.out.println("Password hash from db: " + adminOpt.get().getPasswordHash());

        if (adminOpt.get().getPasswordHash().equals(passwordHash)) {
            return generateJwtToken(adminOpt.get());
        }

        return null;
    }

    @Autowired
    public void setAdminRepository(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Contract(pure = true)
    private @NotNull String generateJwtToken(@NotNull Administration admin) {
        return admin.getPasswordHash();
    }
}
