package com.codegym.shopyy.repository;

import com.codegym.shopyy.entities.Role;
import com.codegym.shopyy.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IUserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    @Query(nativeQuery = false,
            value = "SELECT u.roles FROM User u " +
                    "WHERE u.username = :username")
    List<Role> findRolesByUsername(@Param("username") String username);

    @Modifying
    @Query("UPDATE User u SET u.password = :newPassword WHERE u.username = :username")
    void updatePassword(@Param("username") String username, @Param("newPassword") String newPassword);
}
