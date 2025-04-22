package com.learn.productservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.learn.productservice.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {

	
	Optional<Product>  findByProductNameAndPrice(String productName,int price);
	
	List<Product> findByPriceGreaterThanEqual(int price);
	
	List<Product>  findByProductName(String productName);
	
}
