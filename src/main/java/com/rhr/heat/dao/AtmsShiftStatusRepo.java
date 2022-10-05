package com.rhr.heat.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rhr.heat.model.AtmsShiftStatus;

@Repository
public interface AtmsShiftStatusRepo 
	extends JpaRepository<AtmsShiftStatus, Long> {
}
