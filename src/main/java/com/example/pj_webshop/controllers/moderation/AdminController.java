package com.example.pj_webshop.controllers.moderation;

import com.example.pj_webshop.components.jwt.JwtUtil;
import com.example.pj_webshop.entities.authentication.Administration;
import com.example.pj_webshop.entities.authentication.TokenResponse;
import com.example.pj_webshop.services.moderation.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;
    private final JwtUtil jwtUtil;

    @Autowired
    public AdminController(AdminService adminService, JwtUtil jwtUtil) {
        this.adminService = adminService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Administration admin) {
        String token = adminService.login(admin.getUsername(), admin.getPasswordHash());
        if (token != null) {
            String jwt = jwtUtil.generateToken(admin.getUsername());
            return ResponseEntity.ok().body(new TokenResponse(jwt));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                    .body("{\"error\":\"Invalid login credentials\"}");
        }
    }
}
