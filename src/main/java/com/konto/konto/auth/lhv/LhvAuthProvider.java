package com.konto.konto.auth.lhv;

import com.konto.konto.auth.User;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACVerifier;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class LhvAuthProvider implements AuthenticationProvider {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Override
    public Authentication authenticate(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        JWSObject jwt = (JWSObject) authentication.getCredentials();

        try {
            JWSVerifier verifier = new MACVerifier(jwtSecret);
            if(jwt.verify(verifier)){
                return new UsernamePasswordAuthenticationToken(user, jwt, authentication.getAuthorities());
            }
        } catch (JOSEException ignored) {

        }
        throw new BadCredentialsException("Token is not valiud");
    }

    @Override
    public boolean supports(Class<?> auth) {
        return auth.equals(UsernamePasswordAuthenticationToken.class);
    }

    private String getApiKey(){
        return "3tyDGoLFzN9@Zf@NxGNcsoE!S9!@#kgpA6k7rrQNXBuvR9GQTa@vxxzu&VeKh4r*z32DoGEQfHs^AF86e@5d4qEV2tA7VWq@EKmMxvojG62zcP3AWMbprWPwgAF#F4@j";
    }
}
