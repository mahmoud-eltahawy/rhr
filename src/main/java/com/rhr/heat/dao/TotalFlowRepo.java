package com.rhr.heat.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rhr.heat.model.TotalFlow;

public interface TotalFlowRepo 
	extends JpaRepository<TotalFlow, Long> {
}
