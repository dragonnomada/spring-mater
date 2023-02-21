package com.example.supertodoapp.repository;

import com.example.supertodoapp.entity.User;
import com.example.supertodoapp.entity.UserPermissions;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserPermissionsRepository extends CrudRepository<UserPermissions, Long> {

    Optional<UserPermissions> findByUser(User user);

}
