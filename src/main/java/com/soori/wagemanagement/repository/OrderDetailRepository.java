package com.soori.wagemanagement.repository;

import com.soori.wagemanagement.entity.Department;
import com.soori.wagemanagement.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
}
