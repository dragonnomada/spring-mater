package com.example.supertodoapp.repository;

import com.example.supertodoapp.entity.User;
import jakarta.transaction.Transactional;
import lombok.extern.java.Log;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Transactional
public interface UserRepository extends CrudRepository<User, Long> {

    @Query(value = "SELECT u FROM User u WHERE u.username=:username")
    Optional<User> searchUserByUsername(@Param("username") String username);
    @Query(value = "SELECT u FROM User u WHERE u.username=:username AND u.password=:password AND u.active")
    Optional<User> searchUserByUsernameAndPassword(@Param("username") String username, @Param("password") String password);
    @Query("SELECT u FROM User u WHERE u.token=:token AND u.active AND u.logged")
    Optional<User> searchUserByToken(@Param("token") String token);
    @Modifying
    @Query(value = "UPDATE User u SET u.logged = :isLogged, u.updated = now() WHERE u.id = :id AND u.active", nativeQuery = true)
    int updateUserLogged(@Param("id") Long id, @Param("isLogged") Boolean isLogged);
    @Modifying
    @Query(value = "UPDATE User u SET u.logged = false, u.active = :isActive, u.updated = now() WHERE u.id = :id", nativeQuery = true)
    int updateUserActive(@Param("id") Long id, @Param("isActive") Boolean isActive);
    @Modifying
    @Query(value = "UPDATE User u SET u.token = :token, u.updated = now() WHERE u.id = :id AND u.active", nativeQuery = true)
    int updateUserToken(@Param("id") Long id, @Param("token") String token);

}
