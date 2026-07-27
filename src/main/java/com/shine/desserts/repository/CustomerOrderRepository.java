package com.shine.desserts.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shine.desserts.model.CustomerOrder;

public interface CustomerOrderRepository extends JpaRepository<CustomerOrder, Long> {
    List<CustomerOrder> findByPhoneOrderByCreatedAtDesc(String phone);
}