package com.example.supertodoapp.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.supertodoapp.entity.Permission;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SecurityService {

    private final UserService userService;
    private final UserPermissionsService userPermissionsService;

    public SecurityService(UserService userService, UserPermissionsService userPermissionsService) {
        this.userService = userService;
        this.userPermissionsService = userPermissionsService;
    }

    public String verifyPermission(String token, String permissionCode) throws Exception {

        Algorithm algorithm = Algorithm.HMAC256("todo-app-secret");

        DecodedJWT jwt = JWT.require(algorithm).build().verify(token);

        String username = jwt.getClaim("username").asString();
        List<String> permissionCodes = jwt.getClaim("permissions").asList(String.class);

        boolean isPresent = false;

        for (String code : permissionCodes) {
            if (code.equals(permissionCode)) {
                isPresent = true;
                break;
            }
        }

        if (!isPresent) {
            throw new Exception("El usuario no tiene el permiso");
        }

        return username;

    }

}
