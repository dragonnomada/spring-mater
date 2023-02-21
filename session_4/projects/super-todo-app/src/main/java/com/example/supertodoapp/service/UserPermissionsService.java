package com.example.supertodoapp.service;

import com.example.supertodoapp.entity.Permission;
import com.example.supertodoapp.entity.User;
import com.example.supertodoapp.entity.UserPermissions;
import com.example.supertodoapp.repository.PermissionRepository;
import com.example.supertodoapp.repository.UserPermissionsRepository;
import com.example.supertodoapp.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserPermissionsService {

    private final UserRepository userRepository;
    private final PermissionRepository permissionRepository;
    private final UserPermissionsRepository userPermissionsRepository;

    public UserPermissionsService(UserRepository userRepository, PermissionRepository permissionRepository, UserPermissionsRepository userPermissionsRepository) {
        this.userRepository = userRepository;
        this.permissionRepository = permissionRepository;
        this.userPermissionsRepository = userPermissionsRepository;
    }

    public UserPermissions getUserPermissions(String username) throws Exception {
        User user = userRepository.searchUserByUsername(username)
                .orElseThrow(() -> new Exception("No se encontró al usuario"));

        UserPermissions userPermissions = userPermissionsRepository.findByUser(user)
                .orElseThrow(() -> new Exception("No se encontraron los permisos del usuario"));

        return userPermissions;
    }

    public UserPermissions addPermissionToUser(String username, String permissionName) throws Exception {
        User user = userRepository.searchUserByUsername(username)
                .orElseThrow(() -> new Exception("No se encontró al usuario"));

        Permission permission = permissionRepository.findPermissionByName(permissionName)
                .orElseThrow(() -> new Exception("No se encontró el permiso"));

        try {
            UserPermissions userPermissions = userPermissionsRepository.findByUser(user)
                    .orElseThrow(() -> new Exception("No se encontraron los permisos del usuario"));

            Set<Permission> permissions = userPermissions.getPermissions();

            permissions.add(permission);

            userPermissions.setPermissions(permissions);

            return userPermissionsRepository.save(userPermissions);
        } catch (Exception e) {
            Set<Permission> permissions = new HashSet<>();

            permissions.add(permission);

            UserPermissions userPermissions = UserPermissions.builder()
                    .user(user)
                    .permissions(permissions)
                    .build();

            return userPermissionsRepository.save(userPermissions);
        }

    }

    public UserPermissions removePermissionToUser(String username, String permissionName) throws Exception {
        User user = userRepository.searchUserByUsername(username)
                .orElseThrow(() -> new Exception("No se encontró al usuario"));

        Permission permission = permissionRepository.findPermissionByName(permissionName)
                .orElseThrow(() -> new Exception("No se encontró el permiso"));

        UserPermissions userPermissions = userPermissionsRepository.findByUser(user)
                .orElseThrow(() -> new Exception("No se encontraron los permisos del usuario"));

        Set<Permission> permissions = userPermissions.getPermissions();

        permissions.remove(permission);

        userPermissions.setPermissions(permissions);

        return userPermissionsRepository.save(userPermissions);

    }

}
