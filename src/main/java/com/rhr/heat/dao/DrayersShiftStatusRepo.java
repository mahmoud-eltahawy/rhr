package com.rhr.heat.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rhr.heat.model.DrayersShiftStatus;

@Repository
public interface DrayersShiftStatusRepo 
	extends JpaRepository<DrayersShiftStatus, Long> {
}