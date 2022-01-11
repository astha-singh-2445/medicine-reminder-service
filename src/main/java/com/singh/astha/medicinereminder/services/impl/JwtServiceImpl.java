package com.singh.astha.medicinereminder.services.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.singh.astha.medicinereminder.dtos.JwtPayload;
import com.singh.astha.medicinereminder.services.JwtService;
import com.singh.astha.medicinereminder.utils.AppProperties;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.security.KeyFactory;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Service
public class JwtServiceImpl implements JwtService {

    private final JWTVerifier jwtVerifier;

    private final AppProperties appProperties;

    public JwtServiceImpl(AppProperties appProperties) {
        this.appProperties = appProperties;
        Algorithm rsaAlgorithm = getRSAAlgorithm();
        jwtVerifier = JWT.require(rsaAlgorithm).build();
    }

    @SneakyThrows
    private Algorithm getRSAAlgorithm() {
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] publicKeyBytes = decoder.decode(appProperties.getPublicKey());
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        RSAPublicKey rsaPublicKey = (RSAPublicKey) keyFactory.generatePublic(new X509EncodedKeySpec(publicKeyBytes));
        return Algorithm.RSA512(rsaPublicKey, null);

    }

    @Override
    public JwtPayload verifyAndDecodeToken(String authHeader) {
        if (authHeader.startsWith("Bearer ")) {
            try {
                String jwt = authHeader.substring(7);
                DecodedJWT decodedJWT = jwtVerifier.verify(jwt);
                return JwtPayload.builder()
                        .userId(decodedJWT.getClaim("userId").asLong())
                        .roles(decodedJWT.getClaim("roles").asList(String.class))
                        .build();
            } catch (Exception e) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid Token");
            }
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Authorization Header must not be null and must start be Bearer");
    }
}
