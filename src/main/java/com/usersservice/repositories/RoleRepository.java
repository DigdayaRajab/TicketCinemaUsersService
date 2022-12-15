package com.usersservice.repositories;

import com.usersservice.entities.Role;
import com.usersservice.model.enumerations.ERoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(ERoles name);
}
