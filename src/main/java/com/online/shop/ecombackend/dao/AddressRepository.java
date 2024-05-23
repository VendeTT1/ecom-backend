package com.online.shop.ecombackend.dao;

import com.online.shop.ecombackend.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findByUser_Id(Long id);
}
