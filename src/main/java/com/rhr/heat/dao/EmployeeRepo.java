package com.rhr.heat.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rhr.heat.model.Employee;

@Repository
public interface EmployeeRepo 
	extends JpaRepository<Employee, Long> {
}
