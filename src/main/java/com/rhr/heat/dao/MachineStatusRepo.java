package com.rhr.heat.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rhr.heat.model.MachineStatus;

@Repository
public interface MachineStatusRepo 
	extends JpaRepository<MachineStatus, Long> {
}
