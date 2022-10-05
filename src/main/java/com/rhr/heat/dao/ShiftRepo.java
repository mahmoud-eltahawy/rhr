package com.rhr.heat.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rhr.heat.model.Shift;

@Repository
public interface ShiftRepo 
	extends JpaRepository<Shift, Long> {
}
