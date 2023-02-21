package com.example.supertodoapp.repository;

import com.example.supertodoapp.entity.Permission;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PermissionRepository extends CrudRepository<Permission, Long> {

    Optional<Permission> findPermissionByCode(String code);
    Optional<Permission> findPermissionByName(String name);

}
