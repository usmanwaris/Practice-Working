package com.uog.employee.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uog.employee.model.Employee;
import com.uog.employee.repository.employeeRepository;

@RestController
@RequestMapping("/api/employees")
public class employeeController {
	
	@Autowired
	private employeeRepository employeerepository;
	
	@GetMapping("")
	private List<Employee> getAllEmployees() {
		return employeerepository.findAll();
	}
	
	@GetMapping("/{id}")
	private Employee getEmployee(@PathVariable Long id) {
		return employeerepository.findOne(id);
	}
	
	@GetMapping("/name/{name}")
	private Employee getEmployeeByName(@PathVariable String name) {
		return employeerepository.findbyName(name);
	}
	
	@PostMapping("")
	private Employee addEmployee(@RequestBody Employee employee) {		
		return employeerepository.save(employee);
	}
	
	@DeleteMapping("/{id}")
	private String deletEmployee(@PathVariable Long id) {
		//employeerepository.deleteById(id);
		employeerepository.delete(id);
		return "Deleted";	
	}
	
	@PutMapping("/{id}") 
	private Employee putEmployee(@RequestBody Employee newEmployee, @PathVariable Long id) {
		if (newEmployee != null) {
			  employeerepository.findOne(id).setEMPLOYEE_AGE(newEmployee.getEMPLOYEE_AGE());
			  employeerepository.findOne(id).setEMPLOYEE_NAME(newEmployee.getEMPLOYEE_NAME());
			  employeerepository.findOne(id).setISACTIVE(newEmployee.getISACTIVE());
		  }
		  return employeerepository.findOne(id);
		  
	  }
	  @GetMapping("/active")
		private List<Employee> getActive() {
			return employeerepository.findActive();
		}
}

