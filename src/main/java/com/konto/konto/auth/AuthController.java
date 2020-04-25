package com.konto.konto.auth;

import com.konto.konto.auth.jwt.JwtAuthService;
import com.konto.konto.openBankingApi.OpenBankingAuth;
import com.konto.konto.user.User;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
@AllArgsConstructor
@RequestMapping(path = "/api/auth")
public class AuthController {

    private final JwtAuthService jwtAuthService;

    @ResponseBody
    @PostMapping("/app")
    public ResponseEntity<User> appAuth(@RequestBody User user, HttpServletResponse res) {
        return ResponseEntity.ok(jwtAuthService.authenticate(user, res));
    }
}
