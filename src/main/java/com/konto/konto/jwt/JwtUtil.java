package com.konto.konto.jwt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.konto.konto.openBankingApi.OpenBankingAuth;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import lombok.experimental.UtilityClass;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

@UtilityClass
public class JwtUtil {

    private final JWSAlgorithm algorithm = JWSAlgorithm.HS256;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public JWSObject getJwsObject(String payload, String secret){
        try {
            JWSObject jwsObject = new JWSObject(new JWSHeader(algorithm), new Payload(payload));
            jwsObject.sign(new MACSigner(secret));
            return jwsObject;
        } catch (JOSEException e) {
           throw new DataIntegrityViolationException(e.getMessage());
        }

    }

    public OpenBankingAuth getUserOpenBankingAuth(){
        JWSObject jws = (JWSObject) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        try {
            return objectMapper.readValue(jws.getPayload().toString(), OpenBankingAuth.class);
        } catch (JsonProcessingException e) {
            throw new DataIntegrityViolationException(e.getMessage());
        }
    }

    public boolean verifyJwtToken(JWSObject jwt, String secret){
        try {
            JWSVerifier verifier = new MACVerifier(secret);
            return jwt.verify(verifier);
        } catch (JOSEException ignored) {
            return false;
        }
    }

}
