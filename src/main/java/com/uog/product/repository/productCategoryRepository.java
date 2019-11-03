package com.uog.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.uog.product.model.ProductCategory;

public interface productCategoryRepository extends JpaRepository<ProductCategory, Long>{
	@Query(value="select * from TBLPRODUCTCATEGORY WHERE ISACTIVE='Y'", nativeQuery = true)
	List<ProductCategory> findActive();
}