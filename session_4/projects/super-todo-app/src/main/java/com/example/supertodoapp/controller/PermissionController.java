package com.example.supertodoapp.controller;

import com.example.supertodoapp.entity.Permission;
import com.example.supertodoapp.service.PermissionService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/permission")
@CrossOrigin
public class PermissionController {

    private final PermissionService permissionService;

    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @GetMapping("/")
    public Iterable<Permission> all() {
        return permissionService.all();
    }

    @GetMapping("/install")
    public Iterable<Permission> install() {
        return permissionService.installAll();
    }

}
