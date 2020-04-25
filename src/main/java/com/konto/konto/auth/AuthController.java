package com.konto.konto.auth;

import com.konto.konto.auth.jwt.JwtAuthService;
import com.konto.konto.openBankingApi.OpenBankingAuth;
import com.konto.konto.user.User;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@AllArgsConstructor
@RequestMapping(path = "/api/auth")
public class AuthController {

    private final JwtAuthService jwtAuthService;

    @ResponseBody
    @GetMapping("/app")
    public ResponseEntity<User> appAuth(@RequestBody User user) {
        return ResponseEntity.ok(jwtAuthService.authenticate(user));
    }
}
