package com.konto.konto.config;

import com.konto.konto.auth.jwt.JwtAuthProvider;
import com.konto.konto.auth.jwt.JwtAuthorizationFilter;
import com.konto.konto.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebConfig extends WebSecurityConfigurerAdapter {

    private final JwtAuthProvider jwtAuthProvider;
    private final UserService userService;
    @Value("${app.jwt-secret}")
    private String jwtSecret;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/auth/app", "/api/user/register")
                .permitAll()
                .anyRequest()
                .fullyAuthenticated()
                .and()
                .addFilter(new JwtAuthorizationFilter(authenticationManager(), userService))
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(jwtAuthProvider);
    }
}

