package com.example.jwtdemo;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

@RestController
public class DemoController {

    @GetMapping("/token/new")
    public String tokenNew() {
        Algorithm algorithm = Algorithm.HMAC256("secret");

        String jwtToken = JWT.create()
                .withIssuer("NomadaCode")
                .withSubject("Nomada Code")
                .withClaim("user", "test")
                //.withClaim("a", 123)
                //.withClaim("b", false)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(new Date().getTime() + 50000L))
                .withPayload(Map.of("a", 123, "b", false))
                .withJWTId(UUID.randomUUID()
                        .toString())
                .withNotBefore(new Date(new Date().getTime() + 10000L))
                .sign(algorithm);

        return jwtToken;
    }

    @GetMapping("/token/verify")
    public Boolean tokenVerify(@RequestParam String token) {
        Algorithm algorithm = Algorithm.HMAC256("secret");

        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer("NomadaCode")
                .build();

        try {
            DecodedJWT jwt = verifier.verify(token);
            System.out.println(jwt.getToken());
            System.out.println(jwt.getHeader());
            System.out.println(jwt.getPayload());
            System.out.println(jwt.getSignature());
            System.out.println(jwt.getClaim("user").asString());
            int a = jwt.getClaim("a").as(Integer.class);
            assert(a == 123);
            System.out.println(a * 2);
            boolean b = jwt.getClaim("b").as(Boolean.class);
            assert(b == false);
            System.out.println(!b);
            return true;
        } catch (JWTVerificationException e) {
            e.printStackTrace();
            return false;
        }
    }

}
