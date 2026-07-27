package com.shine.desserts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shine.desserts.model.Dessert;

@Repository
public interface DessertRepository extends JpaRepository<Dessert, Long> {
}