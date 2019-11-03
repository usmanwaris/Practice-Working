package com.uog.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.uog.product.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{
	@Query(value="select * from TBLPRODUCT WHERE ISACTIVE='Y'", nativeQuery = true)
	List<Product> findActive();
}