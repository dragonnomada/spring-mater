package com.example.supertodoapp.service;

import com.example.supertodoapp.entity.Permission;
import com.example.supertodoapp.repository.PermissionRepository;
import org.springframework.stereotype.Service;

@Service
public class PermissionService {

    private final PermissionRepository permissionRepository;

    public PermissionService(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    public Iterable<Permission> installAll() {
        permissionRepository.save(Permission.builder()
                .name("todo-all")
                .description("View all Todos")
                .code("todo:all")
                .build());
        permissionRepository.save(Permission.builder()
                .name("todo-details")
                .description("View details of some Todo")
                .code("todo:details")
                .build());
        permissionRepository.save(Permission.builder()
                .name("todo-add")
                .description("Add new Todo")
                .code("todo:add")
                .build());
        permissionRepository.save(Permission.builder()
                .name("todo-edit")
                .description("Edit some Todo")
                .code("todo:edit")
                .build());
        permissionRepository.save(Permission.builder()
                .name("todo-delete")
                .description("Delete some Todo")
                .code("todo:delete")
                .build());

        return permissionRepository.findAll();
    }

    public Iterable<Permission> all() {
        return permissionRepository.findAll();
    }

}
