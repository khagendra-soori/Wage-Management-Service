package com.soori.wagemanagement.repository;

import com.soori.wagemanagement.entity.Component;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ComponentRepository extends JpaRepository<Component, Long> {

    Optional<Component> findByComponentName(String componentName);

    Component findComponentByComponentName(String componentName);
}
