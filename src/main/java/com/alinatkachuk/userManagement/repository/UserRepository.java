package com.alinatkachuk.userManagement.repository;

import com.alinatkachuk.userManagement.entity.Role;
import com.alinatkachuk.userManagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserName(String userName);

    List<User> findAllByOrderByUserNameAsc();

    List <User> findUsersByRole (Set<Role> role);

    Boolean existsByUserName(String userName);

    List<User> findAll();
}
