package com.rhr.heat.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rhr.heat.model.ProblemDetail;

@Repository
public interface ProblemDetailsRepo 
	extends JpaRepository<ProblemDetail, Long> {
}
