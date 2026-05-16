package com.example.restapih2.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.restapih2.Entity.Product;

public interface ProductRepository extends JpaRepository<Product,Long> {
  // JpaRepository already gives us: save(), findAll(), findById(), deleteById()
  
} 
