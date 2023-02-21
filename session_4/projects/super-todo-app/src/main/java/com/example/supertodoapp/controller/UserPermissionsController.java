package com.example.supertodoapp.controller;

import com.example.supertodoapp.entity.UserPermissions;
import com.example.supertodoapp.service.UserPermissionsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user/permissions")
@CrossOrigin
public class UserPermissionsController {

    private final UserPermissionsService userPermissionsService;

    public UserPermissionsController(UserPermissionsService userPermissionsService) {
        this.userPermissionsService = userPermissionsService;
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserPermissions> getUserPermissions(@PathVariable String username) {
        try {
            return ResponseEntity.ok(userPermissionsService.getUserPermissions(username));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/{username}/add")
    public ResponseEntity<UserPermissions> addUserPermission(@PathVariable String username, @RequestParam String permissionName) {
        try {
            return ResponseEntity.ok(userPermissionsService.addPermissionToUser(username, permissionName));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/{username}/remove")
    public ResponseEntity<UserPermissions> removeUserPermission(@PathVariable String username, @RequestParam String permissionName) {
        try {
            return ResponseEntity.ok(userPermissionsService.removePermissionToUser(username, permissionName));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }


}
