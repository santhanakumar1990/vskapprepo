package com.vsk.product.entity.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vsk.product.entity.Product;

public interface UserRepository extends JpaRepository<Product, Long> {

	public Optional<Product> findBySerialno(String serialno);

}
