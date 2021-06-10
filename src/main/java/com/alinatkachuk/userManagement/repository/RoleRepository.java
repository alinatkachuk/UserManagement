package com.alinatkachuk.userManagement.repository;

import com.alinatkachuk.userManagement.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository <Role, Long> {

    List<Role> findAll();

    Optional<Role> findByName (String name);

}
