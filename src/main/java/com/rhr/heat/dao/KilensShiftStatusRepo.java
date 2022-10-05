package com.rhr.heat.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rhr.heat.model.KilensShiftStatus;

@Repository
public interface KilensShiftStatusRepo 
	extends JpaRepository<KilensShiftStatus, Long> {
}
