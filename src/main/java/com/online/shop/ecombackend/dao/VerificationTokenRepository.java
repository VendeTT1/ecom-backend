package com.online.shop.ecombackend.dao;

import com.online.shop.ecombackend.model.User;
import com.online.shop.ecombackend.model.VerficationToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VerificationTokenRepository extends JpaRepository<VerficationToken, Long > {
    Optional<VerficationToken> findByToken(String token);

    void deleteByUser(User user);

    List<VerficationToken> findByUser_IdOrderByIdDesc(Long id);


}
