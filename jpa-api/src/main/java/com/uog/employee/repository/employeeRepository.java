package com.uog.employee.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.uog.employee.model.Employee;

public interface employeeRepository extends JpaRepository<Employee, Long>{
	@Query(value="select * from TBLEMPLOYEE where ISACTIVE='Y'", nativeQuery = true)
	List<Employee> findActive();
	
	@Query(value="select * from TBLEMPLOYEE where EMPLOYEE_NAME=:name", nativeQuery = true)
	Employee findbyName(@Param("name") String studentName);
}
