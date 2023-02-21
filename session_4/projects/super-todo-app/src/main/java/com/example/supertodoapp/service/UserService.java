package com.example.supertodoapp.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.supertodoapp.entity.Permission;
import com.example.supertodoapp.entity.User;
import com.example.supertodoapp.entity.UserPermissions;
import com.example.supertodoapp.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final UserPermissionsService userPermissionsService;

    public UserService(UserRepository userRepository, UserPermissionsService userPermissionsService) {
        this.userRepository = userRepository;
        this.userPermissionsService = userPermissionsService;
    }

    public Iterable<User> generateRandomUsers(int count) {
        Random random = new Random();
        for (int i = 0; i < count; i++) {
            int code = Math.abs(random.nextInt() % 10000);

            userRepository.save(User.builder()
                    .username(String.format("user_%d", code))
                    .fullname(String.format("Random User %d", code))
                    .email(String.format("random.user_%d@test.com", code))
                    .password(String.format("ru$%d", code))
                    .picture(String.format("https://picsum.photos/id/%d/400", code % 500))
                    .active(true)
                    .created(Timestamp.valueOf(LocalDateTime.now()))
                    .logged(false)
                    .build());
        }
        return userRepository.findAll();
    }

    @Transactional
    public User signIn(String username, String password) {
        System.out.printf("SignIn username=%s, password=%s %n", username, password);

        // TODO: Buscar al usuario con `username` y `password`
        User user = userRepository.searchUserByUsernameAndPassword(username, password).orElseThrow();

        System.out.printf("User=%s", user.toString());

        //long id = user.orElseThrow().getId();

        // TODO: Marcar que el usuario ha iniciado sesión (`logged`)
        //userRepository.updateUserLogged(id, true);
        user.setLogged(true);

        // TODO: * Recuperar los permisos del usuario
        Set<Permission> permissions = new HashSet<>();

        try {
            UserPermissions userPermissions = userPermissionsService.getUserPermissions(username);
            permissions = userPermissions.getPermissions();
        } catch (Exception e) {
            //
        }

        // TODO: Crear un token para el usuario
        Algorithm algorithm = Algorithm.HMAC256("todo-app-secret");

        String token = JWT.create()
                .withIssuer("SuperTodoApp")
                .withSubject("Super Todo App Server")
                .withClaim("username", username)
                .withClaim("permissions", permissions.stream().map(permission -> permission.getCode()).toList())
                // TODO: * Agregar los permisos del usuario al token
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(new Date().getTime() + 60L * 60L * 1000L))
                .withJWTId(UUID.randomUUID().toString())
                .sign(algorithm);

        // TODO: Actualizar el token del usuario
        //userRepository.updateUserToken(id, token);
        user.setToken(token);

        //return userRepository.findById(id).orElseThrow();
        user.setUpdated(Timestamp.valueOf(LocalDateTime.now()));
        return userRepository.save(user);

    }

    public User signOut(String token) {

        // TODO: Buscar al usuario por token
        User user = userRepository.searchUserByToken(token).orElseThrow();

        //long id = user.orElseThrow().getId();

        // TODO: Marcar que el usuario ha cerrado sesión (`logged`)
        //userRepository.updateUserLogged(id, false);
        user.setLogged(false);

        // TODO: Actualizar el token del usuario
        //userRepository.updateUserToken(id, "");
        user.setToken(null);

        //return userRepository.findById(id).orElseThrow();
        user.setUpdated(Timestamp.valueOf(LocalDateTime.now()));
        return userRepository.save(user);

    }

    public User resetSignOut(String username) {

        // TODO: Buscar al usuario por token
        User user = userRepository.searchUserByUsername(username).orElseThrow();

        //long id = user.orElseThrow().getId();

        // TODO: Marcar que el usuario ha cerrado sesión (`logged`)
        //userRepository.updateUserLogged(id, false);
        user.setLogged(false);

        // TODO: Actualizar el token del usuario
        //userRepository.updateUserToken(id, "");
        user.setToken(null);

        //return userRepository.findById(id).orElseThrow();
        user.setUpdated(Timestamp.valueOf(LocalDateTime.now()));
        return userRepository.save(user);

    }

}
