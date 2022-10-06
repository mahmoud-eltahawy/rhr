package com.rhr.heat.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rhr.heat.model.Shift;
import com.rhr.heat.model.ShiftId;

@Repository
public interface ShiftRepo 
	extends JpaRepository<Shift, ShiftId> {
}
