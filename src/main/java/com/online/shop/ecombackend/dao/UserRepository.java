package com.online.shop.ecombackend.dao;

import com.online.shop.ecombackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,String> {

    Optional<User> findByNameIgnoreCase(String name);

    Optional<User> findAllById(String id);

    Optional<User> findAllByEmailIgnoreCase(String email);

}
