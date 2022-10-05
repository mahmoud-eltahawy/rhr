package com.rhr.heat.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rhr.heat.model.WorkDay;

@Repository
public interface WorkdayRepo 
	extends JpaRepository<WorkDay, Long> {
}
