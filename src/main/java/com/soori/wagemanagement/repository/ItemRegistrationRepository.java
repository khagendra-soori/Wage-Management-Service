package com.soori.wagemanagement.repository;

import com.soori.wagemanagement.entity.ItemRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ItemRegistrationRepository extends JpaRepository<ItemRegistration, Long> {
    Optional<ItemRegistration> findByItemName(String itemName);
}
